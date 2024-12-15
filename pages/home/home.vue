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
				<view class="notification-dot" v-if="unapprovedCount > 0"></view>
				<view class="notification-count" v-if="unapprovedCount > 0">{{ unapprovedCount }}</view>
			</view>
		</view>

		<!-- 主体内容 -->
		<view class="main">
		    <!-- 报价管理 -->
		    <view class="section">
		        <view class="section-title">报价管理</view>
		        <view class="section-content">
		            <view class="icon-button" @click="goToPage('报价单管理')" :class="{ 'has-permission': hasPermission('报价单管理') }">
		                <image src="@/static/icon/报价单管理.png" class="icon" />
		                <text>报价单管理</text>
		            </view>
		            <view class="icon-button" @click="goToPage('报价审核')" :class="{ 'has-permission': hasPermission('报价审核') }">
		                <image src="@/static/icon/报价单审核.png" class="icon" />
		                <text>报价审核</text>
		            </view>
		        </view>
		    </view>
		
		    <!-- 成本管理 -->
		    <view class="section">
		        <view class="section-title">成本管理</view>
		        <view class="section-content">
		            <view class="icon-button" @click="goToPage('物料管理')" :class="{ 'has-permission': hasPermission('物料管理') }">
		                <image src="@/static/icon/物料管理.png" class="icon" />
		                <text>物料管理</text>
		            </view>
		            <view class="icon-button" @click="goToPage('材料管理')" :class="{ 'has-permission': hasPermission('材料管理') }">
		                <image src="@/static/icon/原材料管理.png" class="icon" />
		                <text>材料管理</text>
		            </view>
		        </view>
		    </view>
		
		    <!-- 费用管理 -->
		    <view class="section">
		        <view class="section-title">费用管理</view>
		        <view class="section-content">
		            <view class="icon-button" @click="goToPage('单件加工费用')" :class="{ 'has-permission': hasPermission('单件加工费用') }">
		                <image src="@/static/icon/单件加工费用.png" class="icon" />
		                <text>单件加工费用</text>
		            </view>
		            <view class="icon-button" @click="goToPage('数割费用')" :class="{ 'has-permission': hasPermission('数割费用') }">
		                <image src="@/static/icon/数割费用.png" class="icon" />
		                <text>数割费用</text>
		            </view>
		            <view class="icon-button" @click="goToPage('原材料费用')" :class="{ 'has-permission': hasPermission('原材料费用') }">
		                <image src="@/static/icon/原材料费用.png" class="icon" />
		                <text>原材料费用</text>
		            </view>
		            <view class="icon-button" @click="goToPage('表面处理单件费用')" :class="{ 'has-permission': hasPermission('表面处理单件费用') }">
		                <image src="@/static/icon/表面处理单件费用.png" class="icon" />
		                <text>表面处理单件费用</text>
		            </view>
		            <view class="icon-button" @click="goToPage('喷涂单件费用')" :class="{ 'has-permission': hasPermission('喷涂单件费用') }">
		                <image src="@/static/icon/喷涂单件费用.png" class="icon" />
		                <text>喷涂单件费用</text>
		            </view>
		        </view>
		    </view>
		
		    <!-- 工种管理 -->
		    <view class="section">
		        <view class="section-title">工种管理</view>
		        <view class="section-content">
		            <view class="icon-button" @click="goToPage('工人管理')" :class="{ 'has-permission': hasPermission('工人管理') }">
		                <image src="@/static/icon/工人管理.png" class="icon" />
		                <text>工人管理</text>
		            </view>
		            <view class="icon-button" @click="goToPage('工时管理')" :class="{ 'has-permission': hasPermission('工时管理') }">
		                <image src="@/static/icon/工时管理.png" class="icon" />
		                <text>工时管理</text>
		            </view>
		        </view>
		    </view>
		
		    <!-- 统计报表 -->
		    <view class="section">
		        <view class="section-title">统计报表</view>
		        <view class="section-content">
		            <view class="icon-button" @click="goToPage('利润分析')" :class="{ 'has-permission': hasPermission('利润分析') }">
		                <image src="@/static/icon/利润分析.png" class="icon" />
		                <text>利润分析</text>
		            </view>
		            <view class="icon-button" @click="goToPage('订单分析')" :class="{ 'has-permission': hasPermission('订单分析') }">
		                <image src="@/static/icon/公司订单分析.png" class="icon" />
		                <text>订单分析</text>
		            </view>
		            <view class="icon-button" @click="goToPage('客户分析')" :class="{ 'has-permission': hasPermission('客户分析') }">
		                <image src="@/static/icon/费用分析.png" class="icon" />
		                <text>客户分析</text>
		            </view>
		        </view>
		    </view>
		</view>
		<!-- 未读消息弹窗 -->
		<view class="todos-popup" v-if="showTodos">
			<view class="popup-content">
				<view class="popup-header">
					<text class="popup-title">未读消息</text>
          <view class="close-button" @click="showTodos=false">
          	×
          </view>
				</view>
				<view class="popup-body">
					<view class="todo-item" v-for="(item, index) in todos" :key="index" @click="goToMessage()">
						<image src="/static/logo.png" class="todo-icon" />
						<text class="todo-name">{{ roleList[item.senderId] }}</text>
            <text class="todo-text">{{ item.content }}</text>
          </view>
				</view>
			</view>
		</view>
		<!-- 代办弹窗 -->
		<!-- 弹窗 -->
		<view class="popup" v-if="showPopup">
			<view class="popup-content">
				<view class="popup-header">
					<text class="popup-title">您有 {{ unapprovedCount }} 条待审核消息</text>
					<view class="close-button" @click="closePopup">
						×
					</view>
				</view>
				<view class="popup-body">
					<text>请及时查看并处理待审核消息。</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showPopup: true, // 控制弹窗的显示与隐藏
				unapprovedCount: 0, // 存储未审核的消息数量
				safeAreaInsets: 0,
				showTodos: false,
				todos: [
				],
        userInfo:null,
				menuList: [],
        roleList:{
          49:'系统管理员',
      
          54:"超级管理员",
          55:"高层决策者",
          56:"成本管理员",
          57:"报价审核员",
          58:"报价员",
          59:"成本审核员",
      
          }
          
        
			};
		},
		onLoad() {
      const userInfo = uni.getStorageSync("userInfo");
      if(!userInfo){
        uni.showToast({
          icon:"none",
          title:"请先登录"
        })
        uni.reLaunch({
          url:"/subpkg/login/login"
        })
      }
      this.userInfo = userInfo;
      this.getUnreadMessage()
			uni.$http.get('/sys/sysMenu/getNowMenuChecked')
				.then(res => {
					this.menuList = res.data.data;
					console.log('start', this.menuList);
				});
			
		},
		methods: {
      getUnreadMessage(){
        uni.$http.get("/messages/getAll",{
          receiverId: this.userInfo.roleId
        }).then(res => {
            this.unapprovedCount = res.data.data.length;
            this.todos=res.data.data
            console.log('获取消息成功', res);
          })
          .catch(err => {
            console.error(`获取消息失败，receiverId: ${this.userInfo.roleId}`, err);
          });
       
        
      },
      hasPermission(pageName) {
                  return this.menuList.some(item => {
                      if (item.name === pageName) {
                          return true;
                      }
                      if (item.children && Array.isArray(item.children)) {
                          return item.children.some(child => child.name === pageName);
                      }
                      return false;
                  });
              },
			closePopup() {
				this.showPopup = false; // 关闭弹窗
			},
			goToMessage() {
				uni.reLaunch({
					url: '/pages/message/message'
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
					"材料管理": "/subpkg/cost/rawMaterialManagement/rawMaterialManagement",
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
		white-space: normal;
		/* 允许正常换行 */
		overflow-wrap: break-word;
		/* 在需要时自动换行 */
		word-wrap: break-word;
		/* 兼容性处理 */
		text-overflow: ellipsis;
		max-width: 80px;
		height: 42px;
	}
	
	/* 未读消息弹窗样式 */
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

	.popup {
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
		padding: 10px 10px 20px 20px;
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
		width: 30px;
		height: 30px;
		border-radius: 50%;
		font-size: 24px;
		text-align: center;
		border: none;
		cursor: pointer;
		display: flex;
		justify-content: center;
		align-items: center;
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
  .todo-name{
    font-weight: bold;
    font-size: 14px;
    color: #333;
  }

	.todo-text {
		font-size: 14px;
		color: #333;
     white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
	}
  .has-permission .icon {
      border-radius: 50%;
      border: 1px solid #409EFF;
      margin-bottom: 6px;
          /* 添加边框 */
          box-shadow: 0 0 10px rgba(64, 158, 255, 0.7);
  }
  
  .has-permission text {
      color: #007aff; /* 文字颜色可以根据需要调整 */
  }
</style>