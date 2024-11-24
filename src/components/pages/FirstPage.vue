<template>
  <div id="app" class="home-page">
    <el-row gutter="20">
      <el-col :span="24">
        <div class="card-title">
          <div class="card-title-text">报价流程</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <div class="time-card">

          <QuoteTimeline :currentStage="currentStage" />
        </div>
      </el-col>

    </el-row>
    <el-row gutter="20">
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">报价管理</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/quotationReview')">
              <div class="card-icon">
                <img src="../../assets/icon/报价单审核.png" alt="">
              </div>
              <div class="card-text">
                报价审核
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialQuotation')">
              <div class="card-icon">
                <img src="../../assets/icon/报价单管理.png" alt="">
              </div>
              <div class="card-text">
                报价单管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/addQuotation')">
              <div class="card-icon">
                <img src="../../assets/icon/新增报价.png" alt="">
              </div>
              <div class="card-text">
                新增报价
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">资源管理</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/materialManage')">
              <div class="card-icon">
                <img src="../../assets/icon/物料管理.png" alt="">
              </div>
              <div class="card-text">
                物料管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialDetail')">
              <div class="card-icon">
                <img src="../../assets/icon/原材料管理.png" alt="">
              </div>
              <div class="card-text">
                材料管理
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">工种管理</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/workerManagement')">
              <div class="card-icon">
                <img src="../../assets/icon/工人管理.png" alt="">
              </div>
              <div class="card-text">
                工人管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/hourManagement')">
              <div class="card-icon">
                <img src="../../assets/icon/工时管理.png" alt="">
              </div>
              <div class="card-text">
                工时管理
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row gutter="20">
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">费用管理</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/processCost')">
              <div class="card-icon">
                <img src="../../assets/icon/单件加工费用.png" alt="">
              </div>
              <div class="card-text">
                单间加工费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/cuttingCost')">
              <div class="card-icon">
                <img src="../../assets/icon/数割费用.png" alt="">
              </div>
              <div class="card-text">
                数割费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialCost')">
              <div class="card-icon">
                <img src="../../assets/icon/原材料费用.png" alt="">
              </div>
              <div class="card-text">
                原材料费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sufaceTreatment')">
              <div class="card-icon">
                <img src="../../assets/icon/表面处理单件费用.png" alt="">
              </div>
              <div class="card-text">
                表面处理单件费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/paintingCost')">
              <div class="card-icon">
                <img src="../../assets/icon/喷涂单件费用.png" alt="">
              </div>
              <div class="card-text">
                喷涂单件费用
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">系统管理</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/sysRole')">
              <div class="card-icon">
                <img src="../../assets/icon/权限管理.png" alt="">
              </div>
              <div class="card-text">
                权限管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysMenu')">
              <div class="card-icon">
                <img src="../../assets/icon/菜单管理.png" alt="">
              </div>
              <div class="card-text">
                菜单管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysLog')">
              <div class="card-icon">
                <img src="../../assets/icon/操作日志.png" alt="">
              </div>
              <div class="card-text">
                操作日志
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysUser')">
              <div class="card-icon">
                <img src="../../assets/icon/用户管理.png" alt="">
              </div>
              <div class="card-text">
                用户管理
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <div class="card-title">
          <div class="card-title-text">统计分析</div>
          <i class="el-icon-arrow-right"></i>
        </div>
        <el-card class="manager-card">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/customerAnalysis')">
              <div class="card-icon">
                <img src="../../assets/icon/费用分析.png" alt="">
              </div>
              <div class="card-text">
                客户分析
              </div>
            </div>
            <div class="card-content" @click="handleClick('/orderAnalysis')">
              <div class="card-icon">
                <img src="../../assets/icon/公司订单分析.png" alt="">
              </div>
              <div class="card-text">
                订单分析
              </div>
            </div>
            <div class="card-content" @click="handleClick('/profitAnalysis')">
              <div class="card-icon">
                <img src="../../assets/icon/利润分析.png" alt="">
              </div>
              <div class="card-text">
                利润分析
              </div>
            </div>
          </div>
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
            <div v-for="activity in recentActivities" :key="activity.id">
              {{ activity.message }}
            </div>
          </el-list>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import * as echarts from 'echarts';
  import QuoteTimeline from '@/components/detail/QuoteTimeline'
  export default {
    name: 'HomePage',
    components: {
      QuoteTimeline
    },
    data() {
      return {
        currentStage: 7,
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
      hasPermission(path) {
        const menuListStr = localStorage.getItem('menuList');
        let menuList = [];
        if (menuListStr) {
          try {
            menuList = JSON.parse(menuListStr);
          } catch (error) {
            console.error('Failed to parse menuList:', error);
          }
        }
        const flatMenuList = menuList.reduce((acc, item) => {
          if (item.children && item.children.length > 0) {
            acc.push(...item.children);
          }
          return acc;
        }, []);
        console.log('tttes', flatMenuList);
        return flatMenuList.some(menu => menu.path === path);
      },
      handleClick(path) {
        if (this.hasPermission(path)) {
          this.$router.push(path);
        } else {
          this.$message({
            message: '您没有此操作权限',
            type: 'warning'
          });
        }
      },
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
    .time-card {
      margin-bottom: 10px;
    }
  }

  .card-title {
    display: flex;
    align-items: center;
    margin-bottom: 15px;

    .card-title-text {
      font-weight: bold;
      color: #000000;
      margin-right: 5px;
    }


  }

  .manager-card {
    margin-bottom: 20px;
  }

  .manager-card-content {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    height: 180px;

    .card-content {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-right: 20px;
      cursor: pointer;
      /* 鼠标指针变成小手 */
      transition: background-color 0.3s ease;
      /* 平滑过渡效果 */

      .card-icon {
        img {
          width: 60px;
          height: 60px;
        }
      }

      .card-text:hover {
        color: #409EFF;
      }
    }
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