<template>
  <view class="container">
    <view class="uni-container">
      <!-- 查询表单 -->
      <view class="filter-bar">
        <input type="text" class="filter-input" placeholder="材料规格" v-model="dataForm.specification" />
       
        <picker mode="selector" :range="sortOptions" :value="filters.sortBy" @change="onSortChange" class="filter-select">
          <view class="filter-select-view">排序: {{ sortOptions[filters.sortBy] }}</view>
        </picker>
        <button class="filter-button" @click="getDataList">查询</button>
             
      </view>

      <!-- 数据表格 -->
      <view class="card-container">
        <view class="worker-card" v-for="(item, index) in nowDataList" :key="item.id">
          <view class="card-header">
            <text class="worker-name">{{ item.specification }}</text>
            <text class="worker-index"># {{   index + 1 }}</text>
          </view>
          <view class="card-body">
            <text>数割(M): {{ item.numCut }}</text>
            <text>下料费: {{ item.cutFee }}</text>
            <text>费用小计: {{ item.totalCutCost }}</text>
          </view>
          <!-- <view class="card-footer">
            <button class="custom-button edit" @click="handleEdit(item)">编辑</button>
            <button class="custom-button delete" @click="handleDelete(item)">删除</button>
          </view> -->
        </view>
      </view>

      <!-- 分页 -->
      <uni-section title="分页" type="line" padding>
        <view class="pagination">
          <button :disabled="pageIndex === 1" @click="prevPage">上一页</button>
          <text>{{ pageIndex }} / {{ totalPage }} ({{ totalItem }} 条)</text>
          <button :disabled="pageIndex === totalPage" @click="nextPage">下一页</button>
        </view>
      </uni-section>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        specification: "",
      },
      dataDialogForm: {
        id: "",
        detailId: "",
        specification: "",
        numCut: "",
        cutFee: "",
        totalCutCost: "",
      },
      originalDataList: [],
      filteredDataList: [],
      nowDataList: [],
      pageSize: 5,
      pageIndex: 1,
      totalPage: 0,
      totalItem: 0,
      dialogFormVisible: false,
      filters: {
        sortBy: 0,
      },
      sortOptions: ["默认", "费用小计升序", "费用小计降序"],
    };
  },
  methods: {
    // 获取所有数据
    async fetchAllData() {
      this.originalDataList = [];
      this.pageIndex = 1;
      let currentPage = 1;
      let totalPages = 1;
      try {
        while (currentPage <= totalPages) {
          const res = await uni.$http.get("/cutting-cost/list", { pageIndex: currentPage, pageSize: 1000 } );
          if (res.statusCode === 200 && res.data.data) {
            const data = res.data.data;
            this.originalDataList = this.originalDataList.concat(data.list);
            totalPages = data.totalPage;
            currentPage++;
          } else {
            throw new Error("数据请求失败");
          }
        }
        this.applyFilters();
      } catch (error) {
        console.error("获取所有数据时出错:", error);
        uni.showToast({ title: "获取数据列表时发生错误", icon: "error" });
      }
    },
    // 应用过滤条件
    applyFilters() {
      this.pageIndex = 1;
      this.filteredDataList = this.originalDataList.filter(item => {
        const specMatch = item.specification.includes(this.dataForm.specification.trim());
        return specMatch;
      });

      if (this.filters.sortBy > 0) {
        const sortFunctions = {
          1: (a, b) => a.totalCutCost - b.totalCutCost,
          2: (a, b) => b.totalCutCost - a.totalCutCost,
        };
        this.filteredDataList.sort(sortFunctions[this.filters.sortBy]);
      }

      this.totalItem = this.filteredDataList.length; // 更新总条数
      this.totalPage = Math.ceil(this.totalItem / this.pageSize); // 更新总页数
      this.updatePaginatedData();
    },
    // 更新分页数据
    updatePaginatedData() {
      const start = (this.pageIndex - 1) * this.pageSize;
      const end = this.pageIndex * this.pageSize;
      this.nowDataList = this.filteredDataList.slice(start, end);
    },
    // 分页按钮
    prevPage() {
      if (this.pageIndex > 1) {
        this.pageIndex--;
        this.updatePaginatedData();
      }
    },
    nextPage() {
      if (this.pageIndex < this.totalPage) {
        this.pageIndex++;
        this.updatePaginatedData();
      }
    },
    // 获取数据
    async getDataList() {
      this.applyFilters();
    },
    // 打开新增/编辑弹窗
    openDialog() {
      this.dialogFormVisible = true;
      this.dataDialogForm = {
        id: "",
        detailId: "",
        specification: "",
        numCut: "",
        cutFee: "",
        totalCutCost: "",
      };
    },
    handleEdit(row) {
      this.dataDialogForm = { ...row };
      this.dialogFormVisible = true;
    },
    async handleDelete(row) {
      uni.showModal({
        title: "提示",
        content: "此操作将永久删除该信息, 是否继续?",
        confirmText: "确定",
        cancelText: "取消",
        success: async (res) => {
          if (res.confirm) {
            try {
              await uni.$http.delete("/cutting-cost/delete", { data: { id: row.id } });
              uni.showToast({ title: "删除成功", icon: "success" });
              this.getDataList(); // 重新获取数据列表
            } catch (error) {
              console.error("删除失败:", error);
              uni.showToast({ title: "删除失败", icon: "error" });
            }
          } else if (res.cancel) {
            uni.showToast({ title: "已取消删除", icon: "none" });
          }
        },
      });
    },
    // 提交新增或编辑数据
    async submitData() {
      const url = this.dataDialogForm.id ? "/cutting-cost/update" : "/cutting-cost/save";
      try {
        await uni.$http.post(url, this.dataDialogForm);
        uni.showToast({ title: this.dataDialogForm.id ? "编辑成功" : "新增成功", icon: "success" });
        this.dialogFormVisible = false;
        this.getDataList();
      } catch (error) {
        console.error("提交失败", error);
        uni.showToast({ title: "提交失败", icon: "error" });
      }
    },
    // 排序选择变化
    onSortChange(e) {
      this.filters.sortBy = parseInt(e.detail.value);
      this.applyFilters();
    },
  },
  onLoad() {
    this.fetchAllData();
  },
};
</script>

<style lang="scss">
.container {
  height: 100vh; /* 默认高度为视口高度 */
  background-color: #e7eaf4;
  overflow-y: auto; /* 如果内容超过视口高度，显示滚动条 */
}

.uni-container {
  padding: 10px;
  .pagination {
    margin-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .pagination button {
    margin: 0 10px;
  }
}

.filter-bar {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 15px;
}

.filter-input {
  flex: 1 1 200px;
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #fff;
  border-radius: 20px;
  height: 20px;
  background-color: #fff;
  color: #333;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.filter-button {
  font-size: 14px;
  color: #fff;
  background-color: #409eff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.filter-button:hover {
  background-color: #3a8ee6;
}

.filter-select {
  flex: 1 1 100px;
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  color: #333;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.filter-select-view {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.worker-card {
  width: 100%;
  padding: 15px;
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

    .worker-name {
      font-size: 16px;
      font-weight: bold;
    }

    .worker-index {
      font-size: 12px;
      color: #888;
    }
  }

  .card-body {
    display: flex;
    flex-direction: column;
    gap: 5px;

    text {
      font-size: 14px;
      color: #555;
    }
  }

  .card-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;

    .custom-button {
      font-size: 14px;
      border: none;
      border-radius: 5px;
      color: #fff;
      cursor: pointer;
    }

    .edit {
      background-color: #409eff;
    }

    .delete {
      background-color: #ff4d4f;
    }
  }
}
</style>