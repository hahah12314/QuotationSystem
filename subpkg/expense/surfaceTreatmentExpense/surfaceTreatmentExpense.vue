<template>
  <view class="container">
    <view class="uni-container">
      <!-- 表面处理筛选部分 -->
      <view class="filter-bar">
        <input type="text" class="filter-input" placeholder="请输入材料规格" v-model="filters.specification" />
        <picker mode="selector" :range="sortOptions" :value="filters.sortBy" @change="onSortChange" class="filter-select">
          <view class="filter-select-view">排序: {{ sortOptions[filters.sortBy] }}</view>
        </picker>
        <button class="filter-button" @click="applyFilters">查询</button>
      </view>

      <!-- 表面处理卡片展示 -->
      <view class="card-container">
        <view class="treatment-card" v-for="(item, index) in nowDataList" :key="item.id">
          <view class="card-header">
            <text class="treatment-specification">{{ item.specification }}</text>
            <text class="treatment-index">#{{ (pageIndex - 1) * pageSize + index + 1 }}</text>
          </view>
          <view class="card-body">
            <text>镀锌重量: {{ item.zincWeight }}</text>
            <text>镀锌费用: {{ item.zincFee }}</text>
            <text>淬火重量: {{ item.temperingWeight }}</text>
            <text>淬火费用: {{ item.temperingFee }}</text>
            <text>砂光重量: {{ item.sandWeight }}</text>
            <text>砂光费用: {{ item.sandFee }}</text>
            <text>QPQ重量: {{ item.qpqWeight }}</text>
            <text>QPQ费用: {{ item.qpqFee }}</text>
            <text>总涂层费用: {{ item.totalCoatingCost }}</text>
          </view>
         <!-- <view class="card-footer">
            <button class="custom-button edit" @click="handleEdit(item)">编辑</button>
            <button class="custom-button delete" @click="handleDelete(item)">删除</button>
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
      totalItem: 0,
      filters: {
        specification: '',
        sortBy: 0,
      },
      sortOptions: ['默认', '总涂层费用升序', '总涂层费用降序'],
    };
  },
  methods: {
    async fetchAllData() {
      this.originalDataList = [];
      this.pageIndex = 1;
      let currentPage = 1;
      let totalPages = 1;
      try {
        while (currentPage <= totalPages) {
          const res = await uni.$http.get('/surface-treatment/list', { pageIndex: currentPage, pageSize: 1000 });
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
      this.filteredDataList = this.originalDataList.filter(item => {
        const specMatch = item.specification.includes(this.filters.specification.trim());
        return specMatch;
      });

      if (this.filters.sortBy > 0) {
        const sortFunctions = {
          1: (a, b) => a.totalCoatingFee - b.totalCoatingFee,
          2: (a, b) => b.totalCoatingFee - a.totalCoatingFee,
        };
        this.filteredDataList.sort(sortFunctions[this.filters.sortBy]);
      }

      this.totalItem = this.filteredDataList.length;
      this.totalPage = Math.ceil(this.totalItem / this.pageSize);
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
    handleEdit(item) {
      // 编辑逻辑
      console.log('编辑:', item);
    },
    handleDelete(item) {
      // 删除逻辑
      console.log('删除:', item);
    },
  },
  onLoad() {
    this.fetchAllData();
  },
};
</script>

<style lang="scss">
.container {
  height: 100vh;
  background-color: #e7eaf4;
  overflow-y: auto;
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
.treatment-card {
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
    .treatment-specification {
      font-size: 16px;
      font-weight: bold;
    }
    .treatment-index {
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