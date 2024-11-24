<template>
    <div class="order-stats">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">前5名客户订单情况</div>
                    <el-table :data="top5Customers" border stripe style="width: 100%">
                        <el-table-column prop="customer" label="客户名称" width="200" />
                        <el-table-column prop="totalOrders" label="总订单量" width="150" />
                        <el-table-column prop="orderValue" label="总金额 (¥)" width="200" />
                        <el-table-column prop="averageValue" label="平均金额 (¥)" width="200" />
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">前5名客户订单金额占比</div>
                    <div id="pie-chart" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">前5名客户订单情况（堆叠条形图）</div>
                    <div id="stacked-bar-chart" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">客户总订单量每月变化情况</div>
                    <div id="stacked-area-chart" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import * as echarts from 'echarts';

    export default {
        name: "OrderStats",
        data() {
            return {
                // 自定义示例数据
                orders: [
                    { customer: '客户A', totalOrders: 100, orderValue: 50000, averageValue: 500 },
                    { customer: '客户B', totalOrders: 80, orderValue: 40000, averageValue: 500 },
                    { customer: '客户C', totalOrders: 70, orderValue: 35000, averageValue: 500 },
                    { customer: '客户D', totalOrders: 60, orderValue: 30000, averageValue: 500 },
                    { customer: '客户E', totalOrders: 50, orderValue: 25000, averageValue: 500 },
                    { customer: '客户F', totalOrders: 40, orderValue: 20000, averageValue: 500 },
                    { customer: '客户G', totalOrders: 30, orderValue: 15000, averageValue: 500 },
                    { customer: '客户H', totalOrders: 20, orderValue: 10000, averageValue: 500 },
                    { customer: '客户I', totalOrders: 10, orderValue: 5000, averageValue: 500 },
                    { customer: '客户J', totalOrders: 5, orderValue: 2500, averageValue: 500 }
                ],
                customers: [
                    { customer: '客户A', monthlyOrders: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100] },
                    { customer: '客户B', monthlyOrders: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50] },
                    { customer: '客户C', monthlyOrders: [3, 6, 9, 12, 15, 18, 21, 24, 27, 30] },
                    { customer: '客户D', monthlyOrders: [2, 4, 6, 8, 10, 12, 14, 16, 18, 20] },
                    { customer: '客户E', monthlyOrders: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] }
                ],
                months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月']
            };
        },
        computed: {
            top5Customers() {
                return this.orders
                    .slice()
                    .sort((a, b) => b.totalOrders - a.totalOrders)
                    .slice(0, 5);
            },
            top5CustomersForPie() {
                return this.orders
                    .slice()
                    .sort((a, b) => b.orderValue - a.orderValue)
                    .slice(0, 5);
            }
        },
        mounted() {
            this.initPieChart();
            this.initStackedBarChart();
            this.initStackedAreaChart();
        },
        methods: {
            initPieChart() {
                const chartDom = document.getElementById('pie-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: '订单金额',
                            type: 'pie',
                            radius: '50%',
                            data: this.top5CustomersForPie.map(item => ({
                                value: item.orderValue,
                                name: item.customer
                            })),
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            },
                            label: {
                                show: true,
                                formatter: '{b}: {c} ({d}%)'
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            initStackedBarChart() {
                const chartDom = document.getElementById('stacked-bar-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['总订单量', '总金额', '平均金额']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value'
                    },
                    yAxis: {
                        type: 'category',
                        data: this.top5Customers.map(item => item.customer)
                    },
                    series: [
                        {
                            name: '总订单量',
                            type: 'bar',
                            stack: 'total',
                            data: this.top5Customers.map(item => item.totalOrders),
                            label: {
                                show: true,
                                position: 'right',
                                offset: [0, -10]
                            }
                        },
                        {
                            name: '总金额',
                            type: 'bar',
                            stack: 'total',
                            data: this.top5Customers.map(item => item.orderValue),
                            label: {
                                show: true,
                                position: 'right',
                                offset: [0, 10]
                            }
                        },
                        {
                            name: '平均金额',
                            type: 'bar',
                            stack: 'total',
                            data: this.top5Customers.map(item => item.averageValue),
                            label: {
                                show: true,
                                position: 'right',
                                offset: [0, 20]
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            initStackedAreaChart() {
                const chartDom = document.getElementById('stacked-area-chart');
                const myChart = echarts.init(chartDom);
                const option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            label: {
                                backgroundColor: '#6a7985'
                            }
                        }
                    },
                    legend: {
                        data: this.customers.map(item => item.customer)
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: this.months
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: this.customers.map(item => ({
                        name: item.customer,
                        type: 'line',
                        stack: '总量',
                        areaStyle: {},
                        data: item.monthlyOrders,
                        label: {
                            show: true,
                            position: 'top'
                        }
                    }))
                };

                myChart.setOption(option);
            }
        }
    };
</script>

<style scoped>
    .order-stats {
        
    }

    .el-row {
        margin-bottom: 20px;
    }

    .el-card {
        height: 100%;
    }

    .chart {
        width: 100%;
        height: 400px;
    }
</style>