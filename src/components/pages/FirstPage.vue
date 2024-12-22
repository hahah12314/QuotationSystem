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
        <el-card class="manager-card" style="height:110px">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/quotationReview')"
              :class="{ 'has-permission': permissionMap['/quotationReview'], 'no-permission': !permissionMap['/quotationReview'] }">
              <div class="card-icon">
                <img src="../../assets/icon/报价单审核.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/quotationReview'] ? '#409EFF' : '' }">
                报价审核
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialQuotation')"
              :class="{ 'has-permission': permissionMap['/materialQuotation'], 'no-permission': !permissionMap['/materialQuotation'] }">
              <div class="card-icon">
                <img src="../../assets/icon/报价单管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/materialQuotation'] ? '#409EFF' : '' }">
                报价单管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/addQuotation')"
              :class="{ 'has-permission': permissionMap['/addQuotation'], 'no-permission': !permissionMap['/addQuotation'] }">
              <div class="card-icon">
                <img src="../../assets/icon/新增报价.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/addQuotation'] ? '#409EFF' : '' }">
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
        <el-card class="manager-card" style="height:110px">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/materialManage')"
              :class="{ 'has-permission': permissionMap['/materialManage'], 'no-permission': !permissionMap['/materialManage'] }">
              <div class="card-icon">
                <img src="../../assets/icon/物料管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/materialManage'] ? '#409EFF' : '' }">
                物料管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialDetail')"
              :class="{ 'has-permission': permissionMap['/materialDetail'], 'no-permission': !permissionMap['/materialDetail'] }">
              <div class="card-icon">
                <img src="../../assets/icon/原材料管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/materialDetail'] ? '#409EFF' : '' }">
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
        <el-card class="manager-card" style="height:110px">
          <div class="manager-card-content">
            <div class="card-content" @click="handleClick('/workerManagement')"
              :class="{ 'has-permission': permissionMap['/workerManagement'], 'no-permission': !permissionMap['/workerManagement'] }">
              <div class="card-icon">
                <img src="../../assets/icon/工人管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/workerManagement'] ? '#409EFF' : '' }">
                工人管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/hourManagement')"
              :class="{ 'has-permission': permissionMap['/hourManagement'], 'no-permission': !permissionMap['/hourManagement'] }">
              <div class="card-icon">
                <img src="../../assets/icon/工时管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/hourManagement'] ? '#409EFF' : '' }">
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
            <div class="card-content" @click="handleClick('/processCost')"
              :class="{ 'has-permission': permissionMap['/processCost'], 'no-permission': !permissionMap['/processCost'] }">
              <div class="card-icon">
                <img src="../../assets/icon/单件加工费用.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/processCost'] ? '#409EFF' : '' }">
                单间加工费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/cuttingCost')"
              :class="{ 'has-permission': permissionMap['/cuttingCost'], 'no-permission': !permissionMap['/cuttingCost'] }">
              <div class="card-icon">
                <img src="../../assets/icon/数割费用.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/cuttingCost'] ? '#409EFF' : '' }">
                数割费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/materialCost')"
              :class="{ 'has-permission': permissionMap['/materialCost'], 'no-permission': !permissionMap['/materialCost'] }">
              <div class="card-icon">
                <img src="../../assets/icon/原材料费用.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/materialCost'] ? '#409EFF' : '' }">
                原材料费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sufaceTreatment')"
              :class="{ 'has-permission': permissionMap['/sufaceTreatment'], 'no-permission': !permissionMap['/sufaceTreatment'] }">
              <div class="card-icon">
                <img src="../../assets/icon/表面处理单件费用.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/sufaceTreatment'] ? '#409EFF' : '' }">
                表面处理单件费用
              </div>
            </div>
            <div class="card-content" @click="handleClick('/paintingCost')"
              :class="{ 'has-permission': permissionMap['/paintingCost'], 'no-permission': !permissionMap['/paintingCost'] }">
              <div class="card-icon">
                <img src="../../assets/icon/喷涂单件费用.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/paintingCost'] ? '#409EFF' : '' }">
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
            <div class="card-content" @click="handleClick('/sysRole')"
              :class="{ 'has-permission': permissionMap['/sysRole'], 'no-permission': !permissionMap['/sysRole'] }">
              <div class="card-icon">
                <img src="../../assets/icon/权限管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/sysRole'] ? '#409EFF' : '' }">
                权限管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysMenu')"
              :class="{ 'has-permission': permissionMap['/sysMenu'], 'no-permission': !permissionMap['/sysMenu'] }">
              <div class="card-icon">
                <img src="../../assets/icon/菜单管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/sysMenu'] ? '#409EFF' : '' }">
                菜单管理
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysLog')"
              :class="{ 'has-permission': permissionMap['/sysLog'], 'no-permission': !permissionMap['/sysLog'] }">
              <div class="card-icon">
                <img src="../../assets/icon/操作日志.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/sysLog'] ? '#409EFF' : '' }">
                操作日志
              </div>
            </div>
            <div class="card-content" @click="handleClick('/sysUser')"
              :class="{ 'has-permission': permissionMap['/sysUser'], 'no-permission': !permissionMap['/sysUser'] }">
              <div class="card-icon">
                <img src="../../assets/icon/用户管理.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/sysUser'] ? '#409EFF' : '' }">
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
            <div class="card-content" @click="handleClick('/customerAnalysis')"
              :class="{ 'has-permission': permissionMap['/customerAnalysis'], 'no-permission': !permissionMap['/customerAnalysis'] }">
              <div class="card-icon">
                <img src="../../assets/icon/费用分析.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/customerAnalysis'] ? '#409EFF' : '' }">
                客户分析
              </div>
            </div>
            <div class="card-content" @click="handleClick('/orderAnalysis')"
              :class="{ 'has-permission': permissionMap['/orderAnalysis'], 'no-permission': !permissionMap['/orderAnalysis'] }">
              <div class="card-icon">
                <img src="../../assets/icon/公司订单分析.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/orderAnalysis'] ? '#409EFF' : '' }">
                订单分析
              </div>
            </div>
            <div class="card-content" @click="handleClick('/profitAnalysis')"
              :class="{ 'has-permission': permissionMap['/profitAnalysis'], 'no-permission': !permissionMap['/profitAnalysis'] }">
              <div class="card-icon">
                <img src="../../assets/icon/利润分析.png" alt="">
              </div>
              <div class="card-text" :style="{ color: permissionMap['/profitAnalysis'] ? '#409EFF' : '' }">
                利润分析
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row gutter="20">
      <el-col :span="12">
        <el-card class="box-card1 announcements">
          <h3>系统公告</h3>
          <div v-for="item in systemAnnouncements" :key="item.id" class="announcement-item"
            @click="showAnnouncementDetails(item)">
            <div class="announcement-title">{{ item.title }}</div>
            <div class="announcement-meta">
              <i :class="`el-icon-${item.icon}`"></i>
              <span>{{ item.date }}</span>
            </div>
          </div>
        </el-card>
        <!-- 详细公告的弹窗 -->
        <el-dialog title="公告详情" :visible.sync="dialogVisible" width="50%" class="announcement-dialog">
          <div class="announcement-dialog-header">
            <h4>{{ selectedAnnouncement.title }}</h4>
            <div class="announcement-date">{{ selectedAnnouncement.date }}</div>
          </div>
          <div class="announcement-details">
            <!-- 动态渲染内容 -->
            <div v-if="selectedAnnouncement.content" v-for="(section, index) in selectedAnnouncement.content"
              :key="index" class="content-section">
              <h5 v-if="section.title">{{ section.title }}</h5>
              <p v-if="section.description">{{ section.description }}</p>
              <ul v-if="section.points && section.points.length">
                <li v-for="(point, idx) in section.points" :key="idx">{{ point }}</li>
              </ul>
            </div>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </span>
        </el-dialog>
      </el-col>

      <el-col :span="12">
        <el-card class="box-card recent-activities">
          <h3>近期活动</h3>
          <div v-for="activity in recentActivities" :key="activity.id" class="activity">
            <div class="activity-content">
              <div class="activity-left">
                用户 {{ activity.username }}<span style="margin-left: 20px;"></span>{{ activity.operation }}
              </div>
              <div class="activity-right">
                {{ activity.createDate }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-row gutter="20" style="margin-top: 15px;">
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
          {
            id: 1,
            title: "报价系统更新通知",
            date: "2024-12-20",
            content: [
              {
                title: "更新内容",
                description: "金威报价系统现已上线以下新功能：",
                points: [
                  "报价单管理模块：支持批量导入和导出报价单数据。",
                  "详细统计分析功能：新增客户分析和利润分析视图。",
                  "用户体验优化：改进界面布局，提高系统操作的便捷性。"
                ]
              },
              {
                title: "注意事项",
                description: "请尽快熟悉新功能，如有疑问请联系系统管理员。"
              }
            ],
            icon: "info"
          },
          {
            id: 2,
            title: "节假日维护公告",
            date: "2024-12-15",
            content: [
              {
                title: "维护安排",
                description: "系统将在以下时间进行例行维护：",
                points: [
                  "维护时间：2024年12月25日 00:00 - 06:00",
                  "维护范围：服务器硬件升级及数据库优化",
                  "影响范围：系统可能短时间无法正常使用"
                ]
              },
              {
                title: "致谢",
                description: "我们将尽快完成维护，感谢您的支持与理解。"
              }
            ],
            icon: "warning"
          },
          {
            id: 3,
            title: "用户指南与培训通知",
            date: "2024-12-18",
            content: [
              {
                title: "培训内容",
                description: "为帮助用户更好地使用系统，我们将开展以下课程：",
                points: [
                  "报价系统基础操作",
                  "报价单审核与管理流程",
                  "高级功能：自定义统计报表的生成"
                ]
              },
              {
                title: "注册方式",
                description: "请使用向管理员提前申请注册并登录培训平台。培训期间有答疑环节，期待您的参与。"
              }
            ],
            icon: "success"
          }
        ],

        dialogVisible: false,
        selectedAnnouncement: {},
        roleFunctions: [
          { role: '管理员', function: '管理所有数据' },
          { role: '报价员', function: '生成报价单' },
          { role: '审核员', function: '审核报价及材料' },
        ],
        recentActivities: [

        ],
        menuList: []
      };
    },
    mounted() {
      this.getMenuList();
      this.getUserInfo();
      this.initCharts();
      this.getRecentActivities()
    },
    computed: {
      permissionMap() {

        const flatMenuList = this.menuList.reduce((acc, item) => {
          if (item.children && item.children.length > 0) {
            acc.push(...item.children);
          }
          return acc;
        }, []);
        return flatMenuList.reduce((acc, menu) => {
          acc[menu.path] = true;
          return acc;
        }, {});
      },
    },
    methods: {
      showAnnouncementDetails(item) {
        this.selectedAnnouncement = item;
        this.dialogVisible = true;
      },
      getRecentActivities() {
        this.$http.get('/sys/sysLog/latest', { params: { limit: 5 } }).then(res => {
          this.recentActivities = res.data.data;
          console.log('recent activities', this.recentActivities)
        });
      },
      async getUserInfo() {
        const res = await this.$http.get('/sys/sysUser/getNowUser')
        this.userInfo = res.data.data
        console.log(res.data.data)


      },
      async getMenuList() {
        const res = await this.$http.get('/sys/sysMenu/getNowMenuChecked');
        console.log(res);
        this.menuList = res.data.data
        // 定义排序顺序
        const order = ['首页', '报价管理', '资源管理', '工种管理', '费用管理', '系统管理', '统计分析'];

        // 对 menuList 进行排序
        this.menuList = this.menuList.sort((a, b) => {
          return order.indexOf(a.name) - order.indexOf(b.name);
        });
      },
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

          tooltip: {},
          xAxis: { data: ['周一', '周二', '周三', '周四', '周五'] },
          yAxis: {},
          series: [{ name: '操作量', type: 'bar', data: [120, 200, 150, 80, 70] }]
        };
        weeklyChart.setOption(weeklyOption);

        const quoteChart = echarts.init(this.$refs.quoteChart);
        const quoteOption = {

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

    padding: 10px 20px;
  }


  .card-title {
    display: flex;
    align-items: center;
    margin-bottom: 15px;

    .card-title-text {
      font-weight: bold;
      font-size: 16px;
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
    height: 140px;

    .card-content {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-right: 10px;
      cursor: pointer;
      /* 鼠标指针变成小手 */
      transition: background-color 0.3s ease;
      /* 平滑过渡效果 */

      .card-icon {
        img {
          width: 40px;
          height: 40px;
        }


      }

      .card-text {
        font-size: 14px;
      }

      .card-text:hover {
        color: #b7f7c0;
      }
    }
  }

  .card-content.has-permission .card-icon {
    width: 40px;
    /* 调整容器大小 */
    height: 40px;
    /* 调整容器大小 */
    border: 1px solid #409EFF;
    /* 添加边框 */
    box-shadow: 0 0 10px rgba(64, 158, 255, 0.7);
    /* 添加阴影 */
    border-radius: 50%;
    /* 设置为圆形 */
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 4.5px;
  }

  .card-content.has-permission .card-icon img {
    width: 40px;
    /* 调整图片大小以适应边框 */
    height: 40px;
    /* 调整图片大小以适应边框 */
    border-radius: 50%;
    /* 设置为圆形 */
  }

  .user-card {
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    height: 120px;

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
  .calendar {
    background-color: #ffffff;
    border-radius: 10px;
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

    /* Ensure minimum height for consistency */
  }

  h3 {
    padding: 5px;
    font-size: 18px;
    color: #333;
    border-bottom: 2px solid #409EFF;

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
    height: 240px;
  }

  .el-calendar {
    height: 400px;
    overflow: auto;
  }

  .announcements,
  .recent-activities {
    height: 160px;
  }

  .announcement-item,
  .activity {
    margin-bottom: 5px;
    padding: 5px;
    border-bottom: 1px solid #f0f0f0;
    display: flex;
    justify-content: space-between;
    align-items: center;

    &:last-child {
      border-bottom: none;
    }

    font-size: 14px;
  }

  /* 悬停状态 */
  .announcement-item:hover {
    background-color: #f5f7fa;
    color: #409eff;
  }

  /* 点击状态 */
  .announcement-item:active {
    background-color: #409eff;
    color: #fff;
  }

  .recent-activities .activity-content {
    display: flex;
    justify-content: space-between;
    width: 100%;
  }

  .recent-activities .activity-left {
    flex: 1;
  }

  .recent-activities .activity-right {
    flex: 1;
    text-align: right;
  }

  .announcement-dialog-header {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    margin-bottom: 10px;
  }

  .announcement-date {
    font-size: 12px;
    color: #909399;
  }

  .content-section h5 {
    font-size: 16px;
    margin: 10px 0;
    color: #333;
  }

  .content-section p {
    font-size: 14px;
    line-height: 1.6;
  }

  .content-section ul {
    margin-left: 20px;
    list-style: disc;
  }


  .dialog-footer {
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>