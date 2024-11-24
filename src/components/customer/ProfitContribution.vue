<template>
    <div class="profit-contribution">
      <el-card>
        <div slot="header">客户利润贡献</div>
        <el-table :data="data.profitData" border stripe style="width: 100%">
          <el-table-column prop="customer" label="客户名称" width="200" />
          <el-table-column prop="profit" label="利润贡献 (¥)" width="200" />
          <el-table-column prop="profitRate" label="利润占比 (%)" width="150" />
        </el-table>
        <div class="chart">
          <echarts :options="chartOptions" style="height: 300px;" />
        </div>
      </el-card>
    </div>
  </template>
  
  <script>
  import * as echarts from "echarts";
  
  export default {
    name: "ProfitContribution",
    props: {
      data: {
        type: Object,
        required: true,
      },
    },
    computed: {
      chartOptions() {
        return {
          title: { text: "利润贡献分布", left: "center" },
          tooltip: { trigger: "item" },
          legend: { bottom: 10, left: "center" },
          series: [
            {
              name: "利润占比",
              type: "pie",
              radius: "50%",
              data: this.data.profitData.map((item) => ({
                value: item.profit,
                name: item.customer,
              })),
            },
          ],
        };
      },
    },
  };
  </script>
  
  <style scoped>
  .profit-contribution {
    padding: 20px;
  }
  .chart {
    margin-top: 20px;
  }
  </style>
  