<template>
    <div class="customer-growth">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">每月增长率趋势</div>
                    <div id="growth-rate-chart" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">客户地区分布</div>
                    <div id="region-distribution-chart" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">每月新增客户数</div>
                    <div id="new-customers-chart" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">每月总客户数</div>
                    <div id="total-customers-chart" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import * as echarts from 'echarts';
    
    export default {
        name: "CustomerGrowth",
        data() {
            return {
                internalData: {
                    dates: ['2023-01', '2023-02', '2023-03', '2023-04', '2023-05', '2023-06'],
                    growthRates: [2.5, 3.0, 3.5, 4.0, 4.5, 5.0],
                    regionDistribution: [
                        { value: 335, name: '华东' },
                        { value: 310, name: '华南' },
                        { value: 234, name: '华北' },
                        { value: 135, name: '华中' },
                        { value: 1548, name: '其他' }
                    ],
                    newCustomersData: [200, 250, 300, 350, 400, 450],
                    totalCustomersData: [1000, 1250, 1550, 1900, 2300, 2750]
                }
            };
        },
        mounted() {
            this.initGrowthRateChart();
            this.initRegionDistributionChart();
            this.initNewCustomersChart();
            this.initTotalCustomersChart();
        },
        methods: {
            initGrowthRateChart() {
                const chartDom = document.getElementById('growth-rate-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: { text: "每月增长率趋势", left: "center" },
                    tooltip: { trigger: "axis" },
                    xAxis: { type: "category", data: this.internalData.dates },
                    yAxis: { type: "value" },
                    series: [
                        {
                            name: "增长率",
                            type: "line",
                            data: this.internalData.growthRates,
                        },
                    ],
                };
    
                myChart.setOption(option);
            },
            initRegionDistributionChart() {
                const chartDom = document.getElementById('region-distribution-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: { text: "客户地区分布", left: "center" },
                    tooltip: { trigger: "item" },
                    legend: { orient: "vertical", left: "left" },
                    series: [
                        {
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
                        },
                    ],
                };
    
                myChart.setOption(option);
            },
            initNewCustomersChart() {
                const chartDom = document.getElementById('new-customers-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: { text: "每月新增客户数", left: "center" },
                    tooltip: { trigger: "axis" },
                    xAxis: { type: "category", data: this.internalData.dates },
                    yAxis: { type: "value" },
                    series: [
                        {
                            name: "新增客户数",
                            type: "bar",
                            data: this.internalData.newCustomersData,
                        },
                    ],
                };
    
                myChart.setOption(option);
            },
            initTotalCustomersChart() {
                const chartDom = document.getElementById('total-customers-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: { text: "每月总客户数", left: "center" },
                    tooltip: { trigger: "axis" },
                    xAxis: { type: "category", data: this.internalData.dates },
                    yAxis: { type: "value" },
                    series: [
                        {
                            name: "总客户数",
                            type: "scatter",
                            data: this.internalData.totalCustomersData,
                        },
                    ],
                };
    
                myChart.setOption(option);
            }
        }
    };
    </script>
    <style scoped>
        .customer-growth {
            
        }
        
        .chart {
            margin-top: 20px;
            height: 300px;
        }
        </style>