<template>
  <view class="customer-growth">
    <view class="card-container">
      <view class="card">
        <view class="card-header">每月增长率趋势</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="growthRateChart" @finished="initGrowthRateChart"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">客户地区分布</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="regionDistributionChart" @finished="initRegionDistributionChart"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">每月新增客户数</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="newCustomersChart" @finished="initNewCustomersChart"></l-echart>
        </view>
      </view>
    </view>
    <view class="card-container">
      <view class="card">
        <view class="card-header">每月总客户数</view>
        <view style="width: 650rpx; height: 750rpx">
          <l-echart ref="totalCustomersChart" @finished="initTotalCustomersChart"></l-echart>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  import * as echarts from '@/uni_modules/lime-echart/static/echarts.min';

  export default {
    name: 'CustomerGrowth',
    data() {
      return {
        internalData: {
          dates: ['2023-01', '2023-02', '2023-03', '2023-04', '2023-05', '2023-06'],
          growthRates: [2.5, 3.0, 3.5, 4.0, 4.5, 5.0],
          regionDistribution: [{
              value: 335,
              name: '华东'
            },
            {
              value: 310,
              name: '华南'
            },
            {
              value: 234,
              name: '华北'
            },
            {
              value: 135,
              name: '华中'
            },
            {
              value: 1548,
              name: '其他'
            }
          ],
          newCustomersData: [200, 250, 300, 350, 400, 450],
          totalCustomersData: [1000, 1250, 1550, 1900, 2300, 2750]
        }
      };
    },
    methods: {
      async initGrowthRateChart() {
        const chart = await this.$refs.growthRateChart.init(echarts);
        chart.setOption({
          tooltip: {
            trigger: "axis"
          },
          xAxis: {
            type: "category",
            data: this.internalData.dates
          },
          yAxis: {
            type: "value"
          },
          series: [{
            name: "增长率",
            type: "line",
            data: this.internalData.growthRates,
          }, ],
        });
      },
      async initRegionDistributionChart() {
        const chart = await this.$refs.regionDistributionChart.init(echarts);
        chart.setOption({
          tooltip: {
            trigger: "item"
          },
          legend: {
            orient: "vertical",
            left: "left"
          },
          series: [{
            name: "地区",
            type: "pie",
            radius: "50%",
            data: this.internalData.regionDistribution,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          }, ],
        });
      },
      async initNewCustomersChart() {
        const chart = await this.$refs.newCustomersChart.init(echarts);
        chart.setOption({
          tooltip: {
            trigger: "axis"
          },
          xAxis: {
            type: "category",
            data: this.internalData.dates
          },
          yAxis: {
            type: "value"
          },
          series: [{
            name: "新增客户数",
            type: "bar",
            data: this.internalData.newCustomersData,
          }, ],
        });
      },
      async initTotalCustomersChart() {
        const chart = await this.$refs.totalCustomersChart.init(echarts);
        chart.setOption({
         
          tooltip: {
            trigger: "axis"
          },
          xAxis: {
            type: "category",
            data: this.internalData.dates
          },
          yAxis: {
            type: "value"
          },
          series: [{
            name: "总客户数",
            type: "scatter",
            data: this.internalData.totalCustomersData,
          }, ],
        });
      }
    }
  };
</script>

<style scoped>
  .customer-growth {
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
</style>