<template>
    <div class="material-preference">
        <el-row :gutter="20">
            <el-col :span="12">
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 表格视图 (物料名称)</div>
                    <el-table :data="materialPreferences" border stripe style="width: 100%">
                        <el-table-column prop="material" label="物料名称" width="120" />
                        <el-table-column prop="customerCount" label="选择客户数" width="120" />
                        <el-table-column prop="percentage" label="占比 (%)" width="120" />
                    </el-table>
                </div>
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 横向条形图 (物料名称)</div>
                    <div id="bar-chart-material" class="chart"></div>
                </div>
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 饼图 (物料名称)</div>
                    <div id="pie-chart-material" class="chart"></div>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 表格视图 (材料名称)</div>
                    <el-table :data="materialNamePreferences" border stripe style="width: 100%">
                        <el-table-column prop="materialName" label="材料名称" width="120" />
                        <el-table-column prop="customerCount" label="选择客户数" width="120" />
                        <el-table-column prop="percentage" label="占比 (%)" width="120" />
                    </el-table>
                </div>
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 横向条形图 (材料名称)</div>
                    <div id="bar-chart-material-name" class="chart"></div>
                </div>
                <div class="chart-container">
                    <div class="chart-title">客户物料偏好分析 - 饼图 (材料名称)</div>
                    <div id="pie-chart-material-name" class="chart"></div>
                </div>
            </el-col>
        </el-row>
        <div class="analysis-result">
            <h3>分析结果</h3>
            <p v-html="analysisResult"></p>
        </div>
    </div>
</template>

<script>
    import * as echarts from 'echarts';

    export default {
        name: "MaterialPreference",
        data() {
            return {
                materialPreferences: [
                    { material: '物料A', customerCount: 120, percentage: 30 },
                    { material: '物料B', customerCount: 80, percentage: 20 },
                    { material: '物料C', customerCount: 150, percentage: 37.5 },
                    { material: '物料D', customerCount: 50, percentage: 12.5 },
                ],
                materialNamePreferences: [
                    { materialName: '材料A', customerCount: 120, percentage: 30 },
                    { materialName: '材料B', customerCount: 80, percentage: 20 },
                    { materialName: '材料C', customerCount: 150, percentage: 37.5 },
                    { materialName: '材料D', customerCount: 50, percentage: 12.5 },
                ],
                analysisResult: ''
            };
        },
        mounted() {
            this.initBarChartMaterial();
            this.initPieChartMaterial();
            this.initBarChartMaterialName();
            this.initPieChartMaterialName();
            this.generateAnalysisResult();
        },
        methods: {
            initBarChartMaterial() {
                const chartDom = document.getElementById('bar-chart-material');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['选择客户数']
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
                        data: this.materialPreferences.map(item => item.material)
                    },
                    series: [
                        {
                            name: '选择客户数',
                            type: 'bar',
                            data: this.materialPreferences.map(item => item.customerCount),
                            label: {
                                show: true,
                                position: 'right'
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            initPieChartMaterial() {
                const chartDom = document.getElementById('pie-chart-material');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: '物料偏好',
                            type: 'pie',
                            radius: '50%',
                            data: this.materialPreferences.map(item => ({
                                value: item.customerCount,
                                name: item.material
                            })),
                            label: {
                                show: true,
                                formatter: '{b}: {c} ({d}%)'
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            initBarChartMaterialName() {
                const chartDom = document.getElementById('bar-chart-material-name');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['选择客户数']
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
                        data: this.materialNamePreferences.map(item => item.materialName)
                    },
                    series: [
                        {
                            name: '选择客户数',
                            type: 'bar',
                            data: this.materialNamePreferences.map(item => item.customerCount),
                            label: {
                                show: true,
                                position: 'right'
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            initPieChartMaterialName() {
                const chartDom = document.getElementById('pie-chart-material-name');
                const myChart = echarts.init(chartDom);
                const option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: '材料偏好',
                            type: 'pie',
                            radius: '50%',
                            data: this.materialNamePreferences.map(item => ({
                                value: item.customerCount,
                                name: item.materialName
                            })),
                            label: {
                                show: true,
                                formatter: '{b}: {c} ({d}%)'
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                myChart.setOption(option);
            },
            generateAnalysisResult() {
                let result = '';
                const sortedMaterialPreferences = [...this.materialPreferences].sort((a, b) => b.customerCount - a.customerCount);
                const sortedMaterialNamePreferences = [...this.materialNamePreferences].sort((a, b) => b.customerCount - a.customerCount);

                // 物料偏好分析
                result += '<strong>物料偏好分析：</strong><br>';
                result += `从以上数据可以看出，${sortedMaterialPreferences[0].material} 是最受欢迎的物料，有 ${sortedMaterialPreferences[0].customerCount} 位客户选择了它，占总客户的 ${sortedMaterialPreferences[0].percentage}%。<br>`;
                if (sortedMaterialPreferences.length > 1) {
                    result += `其次是 ${sortedMaterialPreferences[1].material}，有 ${sortedMaterialPreferences[1].customerCount} 位客户选择，占总客户的 ${sortedMaterialPreferences[1].percentage}%。<br>`;
                }
                if (sortedMaterialPreferences.length > 2) {
                    result += `${sortedMaterialPreferences[2].material} 和 ${sortedMaterialPreferences[3].material} 的选择客户数较少，分别为 ${sortedMaterialPreferences[2].customerCount} 和 ${sortedMaterialPreferences[3].customerCount}，占比分别为 ${sortedMaterialPreferences[2].percentage}% 和 ${sortedMaterialPreferences[3].percentage}%。<br>`;
                }

                // 材料偏好分析
                result += '<br><strong>材料偏好分析：</strong><br>';
                result += `从以上数据可以看出，${sortedMaterialNamePreferences[0].materialName} 是最受欢迎的材料，有 ${sortedMaterialNamePreferences[0].customerCount} 位客户选择了它，占总客户的 ${sortedMaterialNamePreferences[0].percentage}%。<br>`;
                if (sortedMaterialNamePreferences.length > 1) {
                    result += `其次是 ${sortedMaterialNamePreferences[1].materialName}，有 ${sortedMaterialNamePreferences[1].customerCount} 位客户选择，占总客户的 ${sortedMaterialNamePreferences[1].percentage}%。<br>`;
                }
                if (sortedMaterialNamePreferences.length > 2) {
                    result += `${sortedMaterialNamePreferences[2].materialName} 和 ${sortedMaterialNamePreferences[3].materialName} 的选择客户数较少，分别为 ${sortedMaterialNamePreferences[2].customerCount} 和 ${sortedMaterialNamePreferences[3].customerCount}，占比分别为 ${sortedMaterialNamePreferences[2].percentage}% 和 ${sortedMaterialNamePreferences[3].percentage}%。<br>`;
                }

                this.analysisResult = result;
            }
        }
    };
</script>

<style scoped>
    .material-preference {
        width: 100%;

    }

    .chart-container {
        padding: 10px;
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        border-radius: 4px;
        margin-bottom: 20px;
    }

    .chart-title {
        font-size: 18px;
        margin-bottom: 10px;
        font-weight: bold;
    }

    .chart {
        width: 100%;
        height: 300px;
    }

    .analysis-result {
        margin-top: 20px;
        padding: 10px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .analysis-result h3 {
        font-size: 16px;
        margin-bottom: 10px;
    }
</style>