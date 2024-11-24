<template>
    <div class="profit-analysis">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">月度利润趋势</div>
                    <div id="monthly-profit-trend" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">利润来源分布</div>
                    <div id="profit-source-distribution" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">产品利润分布</div>
                    <div id="product-profit-distribution" class="chart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">客户利润贡献</div>
                    <div id="customer-profit-contribution" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card>
                    <div slot="header">利润详情表</div>
                    <el-table :data="profitDetails" border stripe style="width: 100%; height: 240px; overflow-y: auto;">
                        <el-table-column prop="date" label="日期" width="150" />
                        <el-table-column prop="orderNumber" label="订单号" width="150" />
                        <el-table-column prop="customer" label="客户" width="150" />
                        <el-table-column prop="product" label="产品" width="150" />
                        <el-table-column prop="revenue" label="收入 (¥)" width="150" />
                        <el-table-column prop="cost" label="成本 (¥)" width="150" />
                        <el-table-column prop="profit" label="利润 (¥)" width="150" />
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">其他图表</div>
                    <div id="other-chart" class="chart"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    import * as echarts from 'echarts';
    
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
        mounted() {
            this.initCharts();
        },
        methods: {
            initCharts() {
                // 月度利润趋势 - 堆叠面积图
                const monthlyProfitTrendChart = echarts.init(document.getElementById('monthly-profit-trend'));
                monthlyProfitTrendChart.setOption({
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
    
                // 利润来源分布 - 饼图
                const profitSourceDistributionChart = echarts.init(document.getElementById('profit-source-distribution'));
                profitSourceDistributionChart.setOption({
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
    
                // 产品利润分布 - 横向条形图
                const productProfitDistributionChart = echarts.init(document.getElementById('product-profit-distribution'));
                productProfitDistributionChart.setOption({
                    tooltip: { trigger: 'axis' },
                    xAxis: { type: 'value' },
                    yAxis: { type: 'category', data: ['产品A', '产品B', '产品C', '产品D', '产品E'] },
                    series: [{ data: [20000, 15000, 15000, 10000, 5000], type: 'bar' }]
                });
    
                // 客户利润贡献 - 堆叠柱状图
                const customerProfitContributionChart = echarts.init(document.getElementById('customer-profit-contribution'));
                customerProfitContributionChart.setOption({
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
    
                // 其他图表 - 散点图
                const otherChart = echarts.init(document.getElementById('other-chart'));
                otherChart.setOption({
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