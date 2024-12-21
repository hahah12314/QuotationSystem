<template>
  <view class="order-analysis">
    <view class="card-container">
      <view class="card">
        <view class="card-header">新增订单数量趋势</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="newOrdersTrend" @finished="initNewOrdersTrend"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">订单状态分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="orderStatusDistribution" @finished="initOrderStatusDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">订单金额分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="orderValueDistribution" @finished="initOrderValueDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">订单物料分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="orderMaterialDistribution" @finished="initOrderMaterialDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">订单类型分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="orderTypeDistribution" @finished="initOrderTypeDistribution"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">订单详情表</view>
        <view class="table-container">
          <uni-table :data="orderDetails" border stripe style="width: 100%">
            <uni-tr>
              <uni-th>日期</uni-th>
              <uni-th>订单号</uni-th>
              <uni-th>客户</uni-th>
              <uni-th>金额 (¥)</uni-th>
              <uni-th>状态</uni-th>
            </uni-tr>
            <uni-tr v-for="(item, index) in orderDetails" :key="index">
              <uni-td>{{ item.date }}</uni-td>
              <uni-td>{{ item.orderNumber }}</uni-td>
              <uni-td>{{ item.customer }}</uni-td>
              <uni-td>{{ item.amount }}</uni-td>
              <uni-td>{{ item.status }}</uni-td>
            </uni-tr>
          </uni-table>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import * as echarts from '@/static/echarts.min.js';

export default {
  name: 'OrderAnalysis',
  data() {
    return {
      orderDetails: [
        { date: '2023-01-01', orderNumber: 'ORD12345', customer: '客户A', amount: 1000, status: '已支付', material: '物料A' },
        { date: '2023-01-02', orderNumber: 'ORD12346', customer: '客户B', amount: 1500, status: '待支付', material: '物料B' },
        { date: '2023-01-03', orderNumber: 'ORD12347', customer: '客户C', amount: 2000, status: '已支付', material: '物料C' },
        { date: '2023-01-04', orderNumber: 'ORD12348', customer: '客户D', amount: 2500, status: '已支付', material: '物料A' },
        { date: '2023-01-05', orderNumber: 'ORD12349', customer: '客户E', amount: 3000, status: '待支付', material: '物料B' },
        { date: '2023-01-06', orderNumber: 'ORD12350', customer: '客户F', amount: 3500, status: '已支付', material: '物料C' },
        { date: '2023-01-07', orderNumber: 'ORD12351', customer: '客户G', amount: 4000, status: '已支付', material: '物料A' },
        { date: '2023-01-08', orderNumber: 'ORD12352', customer: '客户H', amount: 4500, status: '待支付', material: '物料B' },
        { date: '2023-01-09', orderNumber: 'ORD12353', customer: '客户I', amount: 5000, status: '已支付', material: '物料C' },
        { date: '2023-01-10', orderNumber: 'ORD12354', customer: '客户J', amount: 5500, status: '已支付', material: '物料A' }
      ]
    };
  },
  methods: {
    async initNewOrdersTrend() {
      const chart = await this.$refs.newOrdersTrend.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['已支付', '待支付', '已取消', '已退款'] },
        xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
        yAxis: { type: 'value' },
        series: [
          { name: '已支付', type: 'line', stack: '总量', data: [80, 100, 120, 140, 160, 180] },
          { name: '待支付', type: 'line', stack: '总量', data: [50, 60, 70, 80, 90, 100] },
          { name: '已取消', type: 'line', stack: '总量', data: [20, 30, 40, 50, 60, 70] },
          { name: '已退款', type: 'line', stack: '总量', data: [10, 20, 30, 40, 50, 60] }
        ],
        areaStyle: {}
      });
    },
    async initOrderStatusDistribution() {
      const chart = await this.$refs.orderStatusDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'value' },
        yAxis: { type: 'category', data: ['已支付', '待支付', '已取消', '已退款'] },
        series: [{ data: [335, 310, 234, 135], type: 'bar' }]
      });
    },
    async initOrderValueDistribution() {
      const chart = await this.$refs.orderValueDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'item' },
        xAxis: { type: 'value' },
        yAxis: { type: 'value' },
        series: [{
          name: '金额',
          type: 'scatter',
          data: [
            [1000, 1],
            [1500, 2],
            [2000, 3],
            [2500, 4],
            [3000, 5],
            [3500, 6],
            [4000, 7],
            [4500, 8],
            [5000, 9],
            [5500, 10]
          ]
        }]
      });
    },
    async initOrderMaterialDistribution() {
      const chart = await this.$refs.orderMaterialDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [{
          name: '订单物料',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 4, name: '物料A' },
            { value: 3, name: '物料B' },
            { value: 3, name: '物料C' }
          ]
        }]
      });
    },
    async initOrderTypeDistribution() {
      const chart = await this.$refs.orderTypeDistribution.init(echarts);
      chart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: ['标准订单', '定制订单', '紧急订单', '长期订单'] },
        xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
        yAxis: { type: 'value' },
        series: [
          { name: '标准订单', type: 'bar', stack: '总量', data: [80, 100, 120, 140, 160, 180] },
          { name: '定制订单', type: 'bar', stack: '总量', data: [50, 60, 70, 80, 90, 100] },
          { name: '紧急订单', type: 'bar', stack: '总量', data: [20, 30, 40, 50, 60, 70] },
          { name: '长期订单', type: 'bar', stack: '总量', data: [10, 20, 30, 40, 50, 60] }
        ]
      });
    }
  }
};
</script>

<style scoped>
.order-analysis {
  padding: 10px;
  background-color: #e7eaf4;
}

.card-container {
  margin-bottom: 20px;
}

.card {
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.chart {
  height: 240px;
  
}

.table-container {
  width: 100%;
}
</style>