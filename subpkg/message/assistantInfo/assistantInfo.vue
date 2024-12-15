<template>
  <view class="container">
    <!-- 通知栏 -->
    <!-- 通知栏 -->
    <view class="notification-bar">
      <view v-for="(notification, index) in notifications" :key="index"
        :class="['notification-item', { 'read': notification.read }]"
        @click="handleNotificationClick(notification, index)" v-if="notification.auditStatus === 0">
        <view class="notification-header">
          <text class="company">🏢 {{ notification.company.name }}</text>
          <text class="status"
            :class="{'approved': notification.auditStatus === 1, 'pending': notification.auditStatus === 0}">
            状态: {{ notification.auditStatus === 1 ? '已审核' : '未审核' }}
          </text>
    
        </view>
        <view class="notification-content">
          <text class="payment-method">💳 支付方式: {{notification.customer.paymentMethod}}</text>
          <text class="customer">👤 客户: {{ notification.customer.name }}</text>
        </view>
      </view>
    </view>
    <!-- <view class="notification-bar">
      <view v-for="(notification, index) in notifications" :key="index"
        :class="['notification-item', { 'read': notification.read }]"
        @click="handleNotificationClick(notification, index)">
        <text>{{ notification.text }}</text>
      </view>
    </view> -->

    <!-- 消息展示区 -->
    <view class="messages">
      <view class="message-item" v-for="(msg, index) in messages" :key="index"
        :class="{'sent': msg.type === 'sent', 'received': msg.type === 'received'}">
        <text>{{ msg.text }}</text>
      </view>
    </view>

    <!-- 输入框区域 -->
    <view class="input-area">
      <!-- 输入框 -->
      <textarea v-model="message" class="input-box" placeholder="请输入消息..."  auto-height="true"></textarea>
      <!-- 发送按钮 -->
      <button @click="sendMessage" class="send-btn">发送</button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      message: "", // 输入框的绑定数据
      messages: [], // 存储所有的消息
      notifications: [{
        name: '',
        text: "暂待开发，更多功能请移至网页端",
        status: null,
        read: false, // 默认未读
      }],
      params: {
        pageSize: 100,
        pageIndex: 1
      }
    };
  },
  onLoad(){
    this.getNotificationList();
  },
  methods: {
    sendMessage() {
      if (this.message.trim()) {
        // 添加发送的消息到消息数组
        this.messages.push({
          text: this.message,
          type: "sent", // 表示发送的消息
        });
        this.message = ""; // 清空输入框
      } else {
        console.log("请输入有效的消息");
      }
    },
    handleNotificationClick(notification, index) {
      // 处理通知点击事件，标记为已读
      this.notifications[index].read = true;
      console.log("点击的通知:", notification.text);
    },
    
    async getNotificationList() {
      const res = await uni.$http.get('/quotations/list', this.params);
      console.log(res.data);
      if (res.data.code === "SUCCESS") {
        this.notifications = res.data.data.list;
        this.notifications.push({
        name: '',
        text: "暂待开发，更多功能请移至网页端",
        status: null,
        read: false, // 默认未读
      })
        // 计算未审核订单的数量
        const unapprovedCount = this.notifications.filter(notification => notification.auditStatus === 0).length;
        //将未审核的数量存入本地存储
        uni.setStorageSync("unapprovedCount", unapprovedCount);
      } else {
        uni.showToast({
          title: res.data.msg,
          icon: 'none',
          duration: 2000
        });
      }
      }
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #e7eaf4;
  overflow-y: auto; /* 如果内容过多，产生滚动条 */
}

.notification-bar {
  padding: 10px;
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.notification-item {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}
 .notification-bar {
    padding: 15px;
    border-bottom: 1px solid #ddd;
  }

  .notification-item {
    margin-bottom: 15px;
    padding: 15px;
    background: linear-gradient(145deg, #ffffff, #f1f3f8);
    border: 1px solid #e0e4ed;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    cursor: pointer;
  }

  .notification-item:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15);
  }

  .notification-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .notification-header .company {
    font-size: 18px;
    font-weight: 600;
    color: #333;
  }

  .notification-header .status {
    font-size: 16px;
    font-weight: 500;
    padding: 2px 8px;
    border-radius: 5px;
    text-align: center;
    white-space: nowrap;
  }

  .notification-header .status.approved {
    background-color: #dff6dd;
    color: #4caf50;
  }

  .notification-header .status.pending {
    background-color: #fce4ec;
    color: #e91e63;
  }

  .notification-content {
    margin-top: 5px;
  }

  .notification-content .payment-method,
  .notification-content .customer {
    font-size: 15px;
    color: #555;
    line-height: 1.6;
    margin-left: 10px;
  }

  .notification-content .payment-method::before,
  .notification-content .customer::before {
    content: "•";
    margin-right: 6px;
    color: #999;
  }

 .notification-item {
    font-size: 20px;
  }
.notification-item:hover {
  background-color: #f1f1f1;
  transform: scale(1.05);
  /* 放大效果 */
}

.messages {
  display: flex;
  flex-direction: column;
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.message-item {
  max-width: 80%;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  word-wrap: break-word; /* 长单词自动换行 */
  white-space: pre-wrap; /* 保持输入的换行符 */
}

.sent {
  float: right;
  background-color: #ffffff;
  /* 发送的消息背景色 */
  align-self: flex-end;
  /* 右对齐 */
}

.received {
  background-color: #f1f1f1;
  /* 接收的消息背景色 */
  align-self: flex-start;
  /* 左对齐 */
}

.input-area {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #fff;
  box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.1);
}

.input-box {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 20px;
  font-size: 15px;
  color: #333;
}

.send-btn {
  height: 40px;
  width: 80px;
  background-color: #235eff;
  color: #fff;
  font-size: 14px;
  border-radius: 20px;
  margin-left: 10px;
  border: none;
  cursor: pointer;
}

.send-btn:hover {
  background-color: #1e4bb8;
}
</style>
