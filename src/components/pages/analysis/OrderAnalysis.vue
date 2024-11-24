<template>
    <div class="order-analysis">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">新增订单数量趋势</div>
                    <div id="new-orders-trend" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">订单状态分布</div>
                    <div id="order-status-distribution" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">订单金额分布</div>
                    <div id="order-value-distribution" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">订单物料分布</div>
                    <div id="order-material-distribution" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">订单类型分布</div>
                    <div id="order-type-distribution" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">订单详情表</div>
                    <el-table :data="orderDetails" border stripe style="width: 100%">
                        <el-table-column prop="date" label="日期" width="150" />
                        <el-table-column prop="orderNumber" label="订单号" width="150" />
                        <el-table-column prop="customer" label="客户" width="150" />
                        <el-table-column prop="amount" label="金额 (¥)" width="150" />
                        <el-table-column prop="status" label="状态" width="150" />
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import * as echarts from 'echarts';

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
        mounted() {
            this.initCharts();
        },
        methods: {
            initCharts() {
                // 新增订单数量趋势 - 堆叠面积图
                const newOrdersTrendChart = echarts.init(document.getElementById('new-orders-trend'));
                newOrdersTrendChart.setOption({
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

                // 订单状态分布 - 横向条形图
                const orderStatusDistributionChart = echarts.init(document.getElementById('order-status-distribution'));
                orderStatusDistributionChart.setOption({
                    tooltip: { trigger: 'axis' },
                    xAxis: { type: 'value' },
                    yAxis: { type: 'category', data: ['已支付', '待支付', '已取消', '已退款'] },
                    series: [{ data: [335, 310, 234, 135], type: 'bar' }]
                });

                // 订单金额分布 - 单轴散点图
                const orderValueDistributionChart = echarts.init(document.getElementById('order-value-distribution'));
                orderValueDistributionChart.setOption({
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

                // 订单物料分布 - 饼图
                const orderMaterialDistributionChart = echarts.init(document.getElementById('order-material-distribution'));
                orderMaterialDistributionChart.setOption({
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

                // 订单类型分布 - 堆叠柱状图
                const orderTypeDistributionChart = echarts.init(document.getElementById('order-type-distribution'));
                orderTypeDistributionChart.setOption({
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
        padding: 20px;
    }

    .el-row {
        margin-bottom: 20px;
    }

    .el-card {
        height: 300px;
    }

    .chart {
        height: 240px;
    }
</style>