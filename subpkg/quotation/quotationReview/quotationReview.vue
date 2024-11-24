<template>
  <view class="container">
    <view class="uni-container">
      <!-- 报价单筛选部分 -->
      <view class="filter-bar">
        <picker mode="selector" :range="paymentMethodOptions" range-key="label" @change="onPaymentMethodChange">
          <view class="filter-input">
            {{ filters.paymentMethod ? filters.paymentMethod : '请选择支付方式' }}
          </view>
        </picker>
        <button class="filter-button" @click="applyFilters">查询</button>
      </view>

      <!-- 报价单卡片展示 -->
      <view class="card-container">
        <view class="quotation-card" v-for="(quotation, index) in nowDataList" :key="quotation.quotationId">
          <view class="card-header">
            <text class="quotation-name"> {{ quotation.company.name || '无' }} </text>
            <text class="quotation-index"># {{ (pageIndex - 1) * pageSize + index + 1 }}</text>
          </view>
          <view class="card-body">
            <text>公司负责人: {{ quotation.company.responsiblePerson || '无' }}</text>
            <text>联系方式: {{ quotation.company.contactInfo || '无' }}</text>
            <text>客户名称: {{ quotation.customer.name || '无' }}</text>
            <text>客户联系方式: {{ quotation.customer.contactInfo || '无' }}</text>
            <text>客户邮箱: {{ quotation.customer.email || '无' }}</text>
            <text>客户地址: {{ quotation.customer.address || '无' }}</text>
            <text>支付方式: {{ quotation.customer.paymentMethod || '无' }}</text>
            <text>状态: 未审核</text>
          </view>
          <view class="card-footer">
            <button class="custom-button edit" @click="fetchQuotationInfo(quotation)">审核</button>
          </view>
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
        paymentMethod: ''
      },
      paymentMethodOptions: [
        { label: '全部', value: '' },
        { label: '现金', value: '现金' },
        { label: '信用卡', value: '信用卡' },
        { label: '支付宝', value: '支付宝' },
        { label: '银行转账', value: '银行转账' },
        { label: '微信支付', value: '微信支付' }
      ]
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
          const res = await uni.$http.get('/quotations/audit', { pageIndex: currentPage, pageSize: 1000 });
          if (res.statusCode === 200 && res.data.data) {
            const data = res.data.data;
            this.originalDataList = this.originalDataList.concat(data);
            totalPages = Math.ceil(data.length / 1000);
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
      this.filteredDataList = this.originalDataList.filter(quotation => {
        const paymentMethodMatch = !this.filters.paymentMethod || (quotation.customer && quotation.customer.paymentMethod === this.filters.paymentMethod);
        return paymentMethodMatch;
      });
      this.totalItem = this.filteredDataList.length; // 更新总条数
      this.totalPage = Math.ceil(this.totalItem / this.pageSize); // 更新总页数
      this.updatePaginatedData();
    },
    updatePaginatedData() {
      const start = (this.pageIndex - 1) * this.pageSize;
      const end = this.pageIndex * this.pageSize;
      this.nowDataList = this.filteredDataList.slice(start, end);
      console.log('aa',this.nowDataList)
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
    fetchQuotationInfo(quotation) {
      // 将数据存储到本地缓存
      uni.setStorageSync('audit', JSON.stringify(quotation));
       console.log('ttt','bbb',quotation,this.nowDataList)
       uni.navigateTo({
         url:'/subpkg/quotation/auditQuotation/auditQuotation'
       })
    },
    onPaymentMethodChange(e) {
      this.filters.paymentMethod = this.paymentMethodOptions[e.detail.value].value;
    }
  },
  onLoad() {
    this.fetchAllData();
  }
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
    justify-content: space-between;
   
    margin-bottom: 15px;
  }
  picker{
    width: 70%;
  }

  .filter-input {
    width: 100%;
   
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
    margin: 0;
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

  .quotation-card {
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

      .quotation-name {
        font-size: 16px;
        font-weight: bold;
      }

      .quotation-index {
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
        padding: 5px 10px;
      }

      .edit {
        background-color: #409eff;
      }

      .delete {
        background-color: #ff4d4f;
      }

      .info {
        background-color: #20a0ff;
      }
    }
  }
</style>