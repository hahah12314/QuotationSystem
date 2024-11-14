<template>
    <div id="app" class="home-page">
      <el-row gutter="20">
        <el-col :span="6">
          <el-card class="box-card1 user-card">
            <div class="card-content">
              <i class="el-icon-message"></i>
              <p>消息</p>
            </div>
            <div class="card-footer">
              <span>2023-10-01</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="box-card1 user-card">
            <div class="card-content">
              <i class="el-icon-circle-check"></i>
              <p>待办</p>
            </div>
            <div class="card-footer">
              <span>2023-10-01</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="box-card1 user-card">
            <div class="card-content">
              <i class="el-icon-user"></i>
              <p>我的</p>
            </div>
            <div class="card-footer">
              <span>2023-10-01</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="box-card1 user-card">
            <div class="card-content">
              <i class="el-icon-setting"></i>
              <p>设置</p>
            </div>
            <div class="card-footer">
              <span>2023-10-01</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
  
      <el-row style="margin-top: 20px;" gutter="20">
        <el-col :span="7">
          <el-card class="box-card common-functions">
            <h3>常用功能</h3>
            <el-list>
              <el-list-item class="function-item" @click="navigateTo('报价管理')">报价管理</el-list-item>
              <el-list-item class="function-item" @click="navigateTo('客户管理')">客户管理</el-list-item>
              <el-list-item class="function-item" @click="navigateTo('材料管理')">材料管理</el-list-item>
              <el-list-item class="function-item" @click="navigateTo('统计分析')">统计分析</el-list-item>
            </el-list>
          </el-card>
        </el-col>
  
        <el-col :span="7">
          <el-card class="box-card pending-tasks">
            <h3>待处理事项</h3>
            <el-table :data="pendingTasks" stripe>
              <el-table-column prop="task" label="任务"></el-table-column>
              <el-table-column prop="status" label="状态"></el-table-column>
            </el-table>
          </el-card>
        </el-col>
  
        <el-col :span="10">
          <el-card class="box-card calendar">
            <h3>行事历</h3>
            <el-calendar v-model="currentDate" @date-change="onDateChange"></el-calendar>
          </el-card>
        </el-col>
      </el-row>
  
      <el-row style="margin-top: 20px;" gutter="20">
        <el-col :span="12">
          <el-card class="box-card weekly-stats">
            <h3>本周操作统计</h3>
            <div ref="weeklyChart" class="chart-container"></div>
          </el-card>
        </el-col>
  
        <el-col :span="12">
          <el-card class="box-card quote-stats">
            <h3>报价统计</h3>
            <div ref="quoteChart" class="chart-container"></div>
          </el-card>
        </el-col>
      </el-row>
  
      <el-row gutter="20" style="margin-top: 20px;">
        <el-col :span="12">
          <el-card class="box-card1 announcements">
            <h3>系统公告</h3>
            <div v-for="item in systemAnnouncements" :key="item.id" class="announcement-item">
              <div>{{ item.title }}</div>
              <div><i :class="`el-icon-${item.icon}`">{{ item.date }}</i></div>
            </div>
          </el-card>
        </el-col>
  
        <el-col :span="12">
          <el-card class="box-card recent-activities">
            <h3>近期活动</h3>
            <el-list>
              <el-list-item v-for="activity in recentActivities" :key="activity.id">
                {{ activity.message }}
              </el-list-item>
            </el-list>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    name: 'HomePage',
    data() {
      return {
        currentDate: new Date(),
        todayEvents: [
          { id: 1, title: '项目会议', time: '14:00 - 15:00' },
          { id: 2, title: '材料审核', time: '10:00 - 11:00' },
        ],
        pendingTasks: [
          { task: '审核报价申请', status: '待处理' },
          { task: '更新客户信息', status: '已处理' },
          { task: '准备下周会议', status: '待处理' },
        ],
        systemAnnouncements: [
          { id: 1, date: '2022-10-21 09:00', title: '系统升级公告', icon: 'info' },
          { id: 2, date: '2022-10-22 10:00', title: '维护通知', icon: 'warning' },
        ],
        roleFunctions: [
          { role: '管理员', function: '管理所有数据' },
          { role: '报价员', function: '生成报价单' },
          { role: '审核员', function: '审核报价及材料' },
        ],
        recentActivities: [
          { id: 1, message: '用户张三更新了报价单' },
          { id: 2, message: '用户李四提交了材料申请' },
          { id: 3, message: '用户王五审核了报价申请' },
        ],
      };
    },
    mounted() {
      this.initCharts();
    },
    methods: {
      initCharts() {
        const weeklyChart = echarts.init(this.$refs.weeklyChart);
        const weeklyOption = {
          title: { text: '本周操作统计' },
          tooltip: {},
          xAxis: { data: ['周一', '周二', '周三', '周四', '周五'] },
          yAxis: {},
          series: [{ name: '操作量', type: 'bar', data: [120, 200, 150, 80, 70] }]
        };
        weeklyChart.setOption(weeklyOption);
  
        const quoteChart = echarts.init(this.$refs.quoteChart);
        const quoteOption = {
          title: { text: '报价统计' },
          tooltip: {},
          xAxis: { data: ['报价A', '报价B', '报价C', '报价D'] },
          yAxis: {},
          series: [{ name: '成交量', type: 'line', data: [500, 300, 400, 600] }]
        };
        quoteChart.setOption(quoteOption);
      },
      onDateChange(date) {
        console.log(`Selected date: ${date}`);
      },
      navigateTo(page) {
        console.log(`Navigating to ${page}`);
      },
    }
  };
  </script>
  
  <style scoped lang="less">
  .home-page {
    
  }
  
  .user-card {
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    height: 200px;
  
    .card-content {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      flex: 1;
    }
  
    .card-content i {
      font-size: 24px;
      color: #409eff;
      margin-bottom: 10px;
    }
  
    .card-content p {
      margin: 0;
      font-size: 16px;
    }
  
    .card-footer {
      background-color: #f5f7fa;
      padding: 10px;
      border-top: 1px solid #dcdfe6;
      font-size: 14px;
      color: #909399;
    }
  }
  
  .common-functions,
  .pending-tasks,
  .calendar,
  .weekly-stats,
  .quote-stats,
  .announcements,
  .recent-activities {
    background-color: #ffffff;
    border-radius: 10px;
    padding: 15px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    min-height: 250px;
    /* Ensure minimum height for consistency */
  }
  
  h3 {
    margin: 0 0 15px;
    font-size: 18px;
    color: #333;
    border-bottom: 2px solid #409EFF;
    padding-bottom: 5px;
  }
  
  .function-item {
    cursor: pointer;
    padding: 10px;
    border-bottom: 1px solid #f0f0f0;
    transition: background 0.2s;
  }
  
  .function-item:hover {
    background: #f5f7fa;
  }
  
  .event-list {
    margin-top: 10px;
  }
  
  .chart-container {
    height: 250px;
  }
  
  .el-calendar {
    height: 400px;
    overflow: auto;
  }
  
  .announcement-item {
    margin-bottom: 10px;
    padding: 10px;
    border-bottom: 1px solid #f0f0f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
  
    &:last-child {
      border-bottom: none;
    }
  }
  </style>