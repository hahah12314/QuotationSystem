<template>
  <view class="container">
    <view class="uni-container">
      <!-- 喷涂成本筛选部分 -->
      <view class="filter-bar">
        <input type="text" class="filter-input" placeholder="请输入材料规格" v-model="filters.specification" />
        <picker mode="selector" :range="sortOptions" :value="filters.sortBy" @change="onSortChange" class="filter-select">
          <view class="filter-select-view">排序: {{ sortOptions[filters.sortBy] }}</view>
        </picker>
        <button class="filter-button" @click="applyFilters">查询</button>
      </view>

      <!-- 材料卡片展示 -->
      <view class="card-container">
        <view class="material-card" v-for="(material, index) in nowDataList" :key="material.id">
          <view class="card-header">
            <text class="material-specification">{{ material.specification }}</text>
            <text class="material-index">#{{ (pageIndex - 1) * pageSize + index + 1 }}</text>
          </view>
          <view class="card-body">
            <text>酸洗/磷化重量: {{ material.acidWashWeight }}</text>
            <text>酸洗/磷化金额: {{ material.acidWashCost }}</text>
            <text>喷塑面积: {{ material.sprayPlasticArea }}</text>
            <text>喷塑金额: {{ material.sprayPlasticCost }}</text>
            <text>电泳面积: {{ material.electroswimmingArea }}</text>
            <text>电泳金额: {{ material.electroswimmingCost }}</text>
            <text>底漆面积: {{ material.primerArea }}</text>
            <text>底漆金额: {{ material.primerCost }}</text>
            <text>面漆面积: {{ material.topcoatArea }}</text>
            <text>面漆金额: {{ material.topcoatCost }}</text>
            <text>总喷涂费用: {{ material.totalPaintingCost }}</text>
          </view>
         <!-- <view class="card-footer">
            <button class="custom-button edit" @click="handleEdit(material)">编辑</button>
            <button class="custom-button delete" @click="handleDelete(material)">删除</button>
          </view> -->
        </view>
      </view>

      <!-- 分页 -->
      <uni-section title="分页" type="line" padding>
        <div class="pagination">
          <button :disabled="pageIndex === 1" @click="prevPage">上一页</button>
          <span>{{ pageIndex }} / {{ totalPage }} ({{ totalItem }} 条)</span>
          <button :disabled="pageIndex === totalPage" @click="nextPage">下一页</button>
        </div>
      </uni-section>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      originalDataList: [],
      filteredDataList: [],
      nowDataList: [],
      pageSize: 5,
      pageIndex: 1,
      totalPage: 0,
      totalItem: 0, // 总条数
      filters: {
        specification: '',
        sortBy: 0,
      },
      sortOptions: ['默认', '总喷涂费用升序', '总喷涂费用降序'],
    };
  },
  methods: {
    async fetchAllData() {
      // 您的获取数据逻辑保持不变
      this.originalDataList = [];
      this.pageIndex = 1;
      let currentPage = 1;
      let totalPages = 1;
      try {
        while (currentPage <= totalPages) {
          const res = await uni.$http.get('/painting-cost/list', { pageIndex: currentPage, pageSize: 1000 });
          if (res.statusCode === 200 && res.data.data) {
            const data = res.data.data;
            this.originalDataList = this.originalDataList.concat(data.list);
            totalPages = data.totalPage;
            currentPage++;
          } else {
            throw new Error('数据请求失败');
          }
        }
        this.applyFilters();
      } catch (error) {
        console.error('获取所有数据时出错:', error);
        uni.showToast({ title: '获取数据列表时发生错误', icon: 'error' });
      }
    },
    applyFilters() {
      this.pageIndex = 1;
      this.filteredDataList = this.originalDataList.filter(material => {
        const specMatch = material.specification.includes(this.filters.specification.trim());
        return specMatch;
      });
      if (this.filters.sortBy > 0) {
        const sortFunctions = {
          1: (a, b) => a.totalPaintingCost - b.totalPaintingCost,
          2: (a, b) => b.totalPaintingCost - a.totalPaintingCost,
        };
        this.filteredDataList.sort(sortFunctions[this.filters.sortBy]);
      }
      this.totalItem = this.filteredDataList.length; // 更新总条数
      this.totalPage = Math.ceil(this.totalItem / this.pageSize); // 更新总页数
      this.updatePaginatedData();
    },
    updatePaginatedData() {
      const start = (this.pageIndex - 1) * this.pageSize;
      const end = this.pageIndex * this.pageSize;
      this.nowDataList = this.filteredDataList.slice(start, end);
    },
    changePage() {
      this.updatePaginatedData();
    },
    prevPage() {
      if (this.pageIndex > 1) {
        this.pageIndex--;
        this.changePage();
      }
    },
    nextPage() {
      if (this.pageIndex < this.totalPage) {
        this.pageIndex++;
        this.changePage();
      }
    },
    onSortChange(e) {
      this.filters.sortBy = parseInt(e.detail.value);
      this.applyFilters();
    },
    handleEdit(material) {
      // 编辑逻辑
      console.log('编辑材料:', material);
    },
    handleDelete(material) {
      // 删除逻辑
      console.log('删除材料:', material);
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
.filter-select {
  flex: 1 1 150px;
  border: 1px solid #fff;
  height: 35px;
  border-radius: 35px;
  background-color: #fff;
  color: #666;
  transition: border-color 0.3s, box-shadow 0.3s;
}
.filter-select-view {
  padding: 8px 12px;
  font-size: 14px;
  color: #666;
  width: 100%;
  text-align: left;
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
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}
.material-card {
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
    .material-specification {
      font-size: 16px;
      font-weight: bold;
    }
    .material-index {
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