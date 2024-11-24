<template>
  <view class="container">
    <!-- 头部 -->
    <view class="header">
      <view class="header-left">
        <image src="@/static/icon/首页图标.png" class="header-icon" />
        金威报价系统
      </view>
      <view class="header-right" @click="toggleTodos">
        待办事项
        <view class="notification-dot" v-if="pendingTasks > 0"></view>
        <view class="notification-count" v-if="pendingTasks > 0">{{ pendingTasks }}</view>
      </view>
    </view>

    <!-- 主体内容 -->
    <view class="main">
      <!-- 报价管理 -->
      <view class="section">
        <view class="section-title">报价管理</view>
        <view class="section-content">
          <view class="icon-button" @click="goToPage('报价单管理')">
            <image src="@/static/icon/报价单管理.png" class="icon" />
            <text>报价单管理</text>
          </view>

          <view class="icon-button" @click="goToPage('报价审核')">
            <image src="@/static/icon/报价单审核.png" class="icon" />
            <text>报价审核</text>
          </view>
        </view>
      </view>

      <!-- 成本管理 -->
      <view class="section">
        <view class="section-title">成本管理</view>
        <view class="section-content">
          <view class="icon-button" @click="goToPage('物料管理')">
            <image src="@/static/icon/物料管理.png" class="icon" />
            <text>物料管理</text>
          </view>
          <view class="icon-button" @click="goToPage('原材料管理')">
            <image src="@/static/icon/原材料管理.png" class="icon" />
            <text>原材料管理</text>
          </view>
        </view>
      </view>
      <!-- 费用管理 -->
      <view class="section">
        <view class="section-title">费用管理</view>
        <view class="section-content">
          <view class="icon-button" @click="goToPage('单件加工费用')">
            <image src="@/static/icon/单件加工费用.png" class="icon" />
            <text>单件加工费用</text>
          </view>
          <view class="icon-button" @click="goToPage('数割费用')">
            <image src="@/static/icon/数割费用.png" class="icon" />
            <text>数割费用</text>
          </view>
          <view class="icon-button" @click="goToPage('原材料费用')">
            <image src="@/static/icon/原材料费用.png" class="icon" />
            <text>原材料费用</text>
          </view>
          <view class="icon-button" @click="goToPage('表面处理单件费用')">
            <image src="@/static/icon/表面处理单件费用.png" class="icon" />
            <text>表面处理单件费用</text>
          </view>
          <view class="icon-button" @click="goToPage('喷涂单件费用')">
            <image src="@/static/icon/喷涂单件费用.png" class="icon" />
            <text>喷涂单件费用</text>
          </view>
        </view>
      </view>

      <!-- 工种管理 -->
      <view class="section">
        <view class="section-title">工种管理</view>
        <view class="section-content">
          <view class="icon-button" @click="goToPage('工人管理')">
            <image src="@/static/icon/工人管理.png" class="icon" />
            <text>工人管理</text>
          </view>
          <view class="icon-button" @click="goToPage('工时管理')">
            <image src="@/static/icon/工时管理.png" class="icon" />
            <text>工时管理</text>
          </view>
        </view>
      </view>

      <!-- 统计报表 -->
      <view class="section">
        <view class="section-title">统计报表</view>
        <view class="section-content">
          <view class="icon-button" @click="goToPage('利润分析')">
            <image src="@/static/icon/利润分析.png" class="icon" />
            <text>利润分析</text>
          </view>
          <view class="icon-button" @click="goToPage('订单分析')">
            <image src="@/static/icon/公司订单分析.png" class="icon" />
            <text>订单分析</text>
          </view>
          <view class="icon-button" @click="goToPage('客户分析')">
            <image src="@/static/icon/费用分析.png" class="icon" />
            <text>客户分析</text>
          </view>
        </view>
      </view>
    </view>
    <!-- 待办事项弹窗 -->
    <view class="todos-popup" v-if="showTodos">
      <view class="popup-content">
        <view class="popup-header">
          <text class="popup-title">待办事项</text>
          
        </view>
        <view class="popup-body">
          <view class="todo-item" v-for="(item, index) in todos" :key="index" @click="goToPage2(item.page)">
            <image src="/static/logo.png" class="todo-icon" />
            <text class="todo-text">{{ item.title }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        showTodos: false,
        pendingTasks: 5, // 示例数据
        todos: [{
            title: '待处理报价订单',
            page: '待处理报价订单',
            icon: '待处理报价订单.png'
          },
          {
            title: '待审核报价单',
            page: '待审核报价单',
            icon: '待审核报价单.png'
          },
          // 可以继续添加更多待办事项
        ],
        menuList: []
      };
    },
    onLoad() {
      uni.$http.get('/sys/sysMenu/getNowMenuChecked')
        .then(res => {
          this.menuList = res.data.data;
          console.log('start', this.menuList);
        });
    },
    methods: {
      goToPage2(){
        uni.reLaunch({
          url:'/pages/message/message'
        })
      },
      toggleTodos() {
        this.showTodos = !this.showTodos;
      },
      goToPage(pageName) {
        this.showTodos = false;
        const pages = {
          "报价单管理": "/subpkg/quotation/quotationManagement/quotationManagement",
          "报价审核": "/subpkg/quotation/quotationReview/quotationReview",
          "工时管理": "/subpkg/worker/timeManagement/timeManagement",
          "工人管理": "/subpkg/worker/workerManagement/workerManagement",
          "物料管理": "/subpkg/cost/materialManagement/materialManagement",
          "原材料管理": "/subpkg/cost/rawMaterialManagement/rawMaterialManagement",
          "单件加工费用": "/subpkg/expense/singleProcessExpense/singleProcessExpense",
          "数割费用": "/subpkg/expense/cutExpense/cutExpense",
          "原材料费用": "/subpkg/expense/rawMaterialExpense/rawMaterialExpense",
          "表面处理单件费用": "/subpkg/expense/surfaceTreatmentExpense/surfaceTreatmentExpense",
          "喷涂单件费用": "/subpkg/expense/paintingExpense/paintingExpense",
          "利润分析": "/subpkg/report/profitAnalysis/profitAnalysis",
          "订单分析": "/subpkg/report/orderAnalysis/orderAnalysis",
          "客户分析": "/subpkg/report/customerAnalysis/customerAnalysis"
        };
        const url = pages[pageName];
        console.log(url, pageName);
        if (url) {
          const isPathAllowed = this.menuList.some(item => {
            if (item.name === pageName) {
              return true;
            }
            if (item.children && Array.isArray(item.children)) {
              return item.children.some(child => child.name === pageName);
            }
            return false;
          });
          if (isPathAllowed) {
            uni.navigateTo({
              url
            });
          } else {
            uni.showToast({
              title: '无此操作权限',
              icon: "none",
            });
          }
        } else {
          uni.showToast({
            title: `功能未实现：${pageName}`,
            icon: "none",
          });
        }
      }
    }
  };
</script>

<style lang="less">
  /* 页面整体样式 */
  .container {
    display: flex;
    flex-direction: column;
    background-color: #e7eaf4;
  }

  /* 头部样式 */
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
  }

  .header-left {
    display: flex;
    align-items: center;
    font-size: 18px;
    font-weight: bold;
  }

  .header-icon {
    width: 30px;
    height: 30px;
    margin-right: 10px;
  }

  .header-right {
    display: flex;
    align-items: center;
    font-size: 14px;
    cursor: pointer;
  }

  .notification-dot {
    width: 8px;
    height: 8px;
    background-color: red;
    border-radius: 50%;
    margin-left: 5px;
  }

  .notification-count {
    font-size: 12px;
    color: #ff4040;
    margin-left: 5px;
  }

  /* 各模块样式 */
  .section {
    background: #f3f5f9;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
    margin: 10px;
  }

  .section-title {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    margin-bottom: 15px;
  }

  .section-content {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-items: center;
  }

  .icon-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    cursor: pointer;
    transition: transform 0.2s;
  }

  .icon-button:hover {
    transform: scale(1.1);
  }

  .icon {
    width: 50px;
    height: 50px;
    margin-bottom: 8px;
  }

  .icon-button text {
    font-size: 14px;
    color: #333;
  }

  /* 待办事项弹窗样式 */
  .todos-popup {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .popup-content {
    background-color: #fff;
    border-radius: 8px;
    padding: 20px;
    width: 80%;
    max-width: 400px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
  }

  .popup-title {
    font-size: 18px;
    font-weight: bold;
    color: #333;
  }

  .close-button {
    cursor: pointer;
  }

  .close-icon {
    width: 20px;
    height: 20px;
  }

  .popup-body {
    max-height: 300px;
    overflow-y: auto;
  }

  .todo-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px;
    border-bottom: 1px solid #e0e0e0;
    cursor: pointer;
    transition: background-color 0.2s;
  }

  .todo-item:hover {
    background-color: #f3f5f9;
  }

  .todo-icon {
    width: 30px;
    height: 30px;
  }

  .todo-text {
    font-size: 14px;
    color: #333;
  }
</style>