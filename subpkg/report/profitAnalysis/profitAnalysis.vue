<template>
  <view class="profit-analysis">
    <view class="card-container">
      <view class="card">
        <view class="card-header">月度利润趋势</view>
        <view style="width: 650rpx; height: 750rpx ">
          <l-echart ref="monthlyProfitTrend" @finished="initMonthlyProfitTrend"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">利润来源分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="profitSourceDistribution" @finished="initProfitSourceDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">产品利润分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="productProfitDistribution" @finished="initProductProfitDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">客户利润贡献</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="customerProfitContribution" @finished="initCustomerProfitContribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">利润详情表</view>
        <view class="table-container">
          <uni-table :data="profitDetails" border stripe style="width: 100%">
            <uni-tr>
              <uni-th>日期</uni-th>
              <uni-th>订单号</uni-th>
              <uni-th>客户</uni-th>
              <uni-th>产品</uni-th>
              <uni-th>收入 (¥)</uni-th>
              <uni-th>成本 (¥)</uni-th>
              <uni-th>利润 (¥)</uni-th>
            </uni-tr>
            <uni-tr v-for="(item, index) in profitDetails" :key="index">
              <uni-td>{{ item.date }}</uni-td>
              <uni-td>{{ item.orderNumber }}</uni-td>
              <uni-td>{{ item.customer }}</uni-td>
              <uni-td>{{ item.product }}</uni-td>
              <uni-td>{{ item.revenue }}</uni-td>
              <uni-td>{{ item.cost }}</uni-td>
              <uni-td>{{ item.profit }}</uni-td>
            </uni-tr>
          </uni-table>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">其他图表</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="otherChart" @finished="initOtherChart"></l-echart>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import * as echarts from '@/static/echarts.min.js';

export default {
  name: 'ProfitAnalysis',
  data() {
    return {
      profitDetails: [
        { date: '2023-01-01', orderNumber: 'ORD12345', customer: '客户A', product: '产品A', revenue: 2000, cost: 1000, profit: 1000 },
        { date: '2023-01-02', orderNumber: 'ORD12346', customer: '客户B', product: '产品B', revenue: 2500, cost: 1500, profit: 1000 },
        { date: '2023-01-03', orderNumber: 'ORD12347', customer: '客户C', product: '产品C', revenue: 3000, cost: 2000, profit: 1000 },
        { date: '2023-01-04', orderNumber: 'ORD12348', customer: '客户D', product: '产品A', revenue: 3500, cost: 2500, profit: 1000 },
        { date: '2023-01-05', orderNumber: 'ORD12349', customer: '客户E', product: '产品B', revenue: 4000, cost: 3000, profit: 1000 },
        { date: '2023-01-06', orderNumber: 'ORD12350', customer: '客户F', product: '产品C', revenue: 4500, cost: 3500, profit: 1000 },
        { date: '2023-01-07', orderNumber: 'ORD12351', customer: '客户G', product: '产品A', revenue: 5000, cost: 4000, profit: 1000 },
        { date: '2023-01-08', orderNumber: 'ORD12352', customer: '客户H', product: '产品B', revenue: 5500, cost: 4500, profit: 1000 },
        { date: '2023-01-09', orderNumber: 'ORD12353', customer: '客户I', product: '产品C', revenue: 6000, cost: 5000, profit: 1000 },
        { date: '2023-01-10', orderNumber: 'ORD12354', customer: '客户J', product: '产品A', revenue: 6500, cost: 5500, profit: 1000 }
      ]
    };
  },
  methods: {
    async initMonthlyProfitTrend() {
      const chart = await this.$refs.monthlyProfitTrend.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['总收入', '总成本', '总利润'] },
        xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
        yAxis: { type: 'value' },
        series: [
          { name: '总收入', type: 'line', stack: '总量', data: [20000, 25000, 30000, 35000, 40000, 45000] },
          { name: '总成本', type: 'line', stack: '总量', data: [10000, 15000, 20000, 25000, 30000, 35000] },
          { name: '总利润', type: 'line', stack: '总量', data: [10000, 10000, 10000, 10000, 10000, 10000] }
        ],
        areaStyle: {}
      });
    },
    async initProfitSourceDistribution() {
      const chart = await this.$refs.profitSourceDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [{
          name: '利润来源',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 20000, name: '产品A' },
            { value: 15000, name: '产品B' },
            { value: 15000, name: '产品C' },
            { value: 10000, name: '产品D' },
            { value: 5000, name: '产品E' }
          ]
        }]
      });
    },
    async initProductProfitDistribution() {
      const chart = await this.$refs.productProfitDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'value' },
        yAxis: { type: 'category', data: ['产品A', '产品B', '产品C', '产品D', '产品E'] },
        series: [{ data: [20000, 15000, 15000, 10000, 5000], type: 'bar' }]
      });
    },
    async initCustomerProfitContribution() {
      const chart = await this.$refs.customerProfitContribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['客户A', '客户B', '客户C', '客户D', '客户E'] },
        xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
        yAxis: { type: 'value' },
        series: [
          { name: '客户A', type: 'bar', stack: '总量', data: [1000, 1000, 1000, 1000, 1000, 1000] },
          { name: '客户B', type: 'bar', stack: '总量', data: [1000, 1000, 1000, 1000, 1000, 1000] },
          { name: '客户C', type: 'bar', stack: '总量', data: [1000, 1000, 1000, 1000, 1000, 1000] },
          { name: '客户D', type: 'bar', stack: '总量', data: [1000, 1000, 1000, 1000, 1000, 1000] },
          { name: '客户E', type: 'bar', stack: '总量', data: [1000, 1000, 1000, 1000, 1000, 1000] }
        ]
      });
    },
    async initOtherChart() {
      const chart = await this.$refs.otherChart.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'item' },
        xAxis: { type: 'value' },
        yAxis: { type: 'value' },
        series: [
          {
            name: '散点图',
            type: 'scatter',
            data: [
              [10, 20],
              [20, 30],
              [30, 40],
              [40, 50],
              [50, 60],
              [60, 70],
              [70, 80],
              [80, 90],
              [90, 100]
            ]
          }
        ]
      });
    }
  }
};
</script>

<style scoped>
.profit-analysis {
  padding: 10px;
  background-color: #e7eaf4;
}

.card-container {
  margin-bottom: 20px;
}

.card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.card-header {
  background-color: #f8f8f8;
  padding: 10px;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}

.table-container {
  padding: 10px;
}

.uni-table {
  width: 100%;
  border-collapse: collapse;
}

.uni-tr {
  border-bottom: 1px solid #ddd;
}

.uni-th,
.uni-td {
  padding: 8px;
  text-align: center;
}

.uni-th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.uni-td {
  color: #333;
}
</style>