<template>
  <view class="container">
    <!-- 消息展示区 -->
    <scroll-view class="messages" scroll-y="true" :scroll-into-view="scrollToView">
      <view
        class="message-item"
        v-for="(msg, index) in messages"
        :key="index"
        :class="{'sent': msg.senderId == userInfo.roleId, 'received': msg.senderId != userInfo.roleId}">
        
        <!-- 接收方消息：头像在左边 -->
        <view v-if="msg.senderId !== userInfo.roleId" class="message-content-container received">
          <!-- 头像 -->
          <view class="avatar-container">
            <image class="avatar" src="/static/images/dog.jpg"></image>
          </view>
          <!-- 消息内容 -->
          <view class="message-content">
            <text>{{ msg.content }}</text>
            <text class="timestamp">{{ formatTimestamp(msg.createdAt) }}</text>
          </view>
          
        </view>

        <!-- 发送方消息：头像在右边 -->
        <view v-else class="message-content-container sent">
          <!-- 头像 -->
          <view class="avatar-container">
            <image class="avatar" :src="userInfo.avatar || require('static/user1.png')"></image>
          </view>
          <!-- 消息内容 -->
          <view class="message-content">
            <text>{{ msg.content }}</text>
            <text class="timestamp">{{ formatTimestamp(msg.createdAt) }}</text>
          </view>
          
        </view>
      </view>
    </scroll-view>

    <!-- 输入框区域 -->
    <view class="input-area">
      <textarea v-model="message" class="input-box" placeholder="请输入消息..." auto-height="true"></textarea>
      <button @click="sendMessage" class="send-btn">发送</button>
    </view>
  </view>
</template>


<script>
export default {
  data() {
    return {
      selectedMessage: null,
      message: "", // 输入框的绑定数据
      messages: [], // 存储所有的消息
      scrollToView: '',
      userInfo: null
    };
  },
  onLoad() {
    // 从本地存储中获取 userInfo
    const userInfo = uni.getStorageSync("userInfo");
    this.userInfo = userInfo;
    console.log('myav',this.userInfo)
    // 从本地存储中获取 selectedMessage
    const messageStr = uni.getStorageSync('selectedMessage');
    if (messageStr) {
      this.selectedMessage = JSON.parse(messageStr);
      console.log('接收到的消息:', this.selectedMessage);
    }

    // 设置导航栏标题
    uni.setNavigationBarTitle({
      title: this.selectedMessage.title || '消息详情',
      success: function() {
        console.log('导航栏标题设置成功');
      },
      fail: function(err) {
        console.error('导航栏标题设置失败', err);
      }
    });

    // 获取已读消息
    this.fetchReadMessages();
  },
  methods: {
    sendMessage() {
      if (this.message.trim()) {
        // 添加发送的消息到消息数组
        const newMessage = {
          content: this.message,
          senderId: this.userInfo.roleId,
          receiverId: this.selectedMessage.roleId,
          relatedQuoteId: this.selectedMessage.relatedQuoteId,
          isRead: false,
          createdAt: this.getCurrentTime(),
          updatedAt: this.getCurrentTime()
        };
        this.messages.push(newMessage);
        this.scrollToBottom();
        this.message = ""; // 清空输入框

        // 发送消息到后端
        this.sendToBackend(newMessage);
      } else {
        console.log("请输入有效的消息");
      }
    },
    fetchReadMessages() {
      const receiverId = this.userInfo.roleId;
      const senderId = this.selectedMessage.roleId;
      uni.$http.get('/messages/read', {
      
          receiverId: receiverId,
          senderId: senderId
       
      })
      .then(response => {
        const readMessages = response.data.data.map(msg => ({
          ...msg,
          type: msg.senderId === this.userInfo.roleId ? "sent" : "received"
        }));
        this.messages = readMessages;
        this.scrollToBottom();
      })
      .catch(error => {
        console.error('获取已读消息失败:', error);
      });
    },
    getCurrentTime() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    formatTimestamp(timestamp) {
      const date = new Date(timestamp);
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${hours}:${minutes} ${date.getHours() >= 12 ? 'PM' : 'AM'}`;
    },
    scrollToBottom() {
      this.scrollToView = `msg-${this.messages.length - 1}`;
    },
    sendToBackend(message) {
      uni.$http.post('/messages/send', message)
      .then(response => {
        console.log('消息发送成功:', response);
      })
      .catch(error => {
        console.error('消息发送失败:', error);
      });
    },
  },
};
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #e7eaf4;
}

.messages {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.message-item {
  display: flex;
  max-width: 95%;
  margin-bottom: 10px;
}

.sent {
  flex-direction: row-reverse; /* 头像在右边，消息内容在左边 */
  align-self: flex-start;
}

.received {
  flex-direction: row; /* 头像在左边，消息内容在右边 */
  align-self: flex-end;
  
}

.avatar-container {
  width: 50px;
  margin: 0 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.message-content-container {
  display: flex;
  align-items: center;
}

.message-content {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  word-wrap: break-word;
  white-space: pre-wrap;
}

.sent .message-content {
  background-color: #dcf8c6;
}

.received .message-content {
  background-color: #f1f1f1;
}

.timestamp {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
  align-self: flex-end;
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
  margin-right: 10px;
}

.send-btn {
  height: 40px;
  width: 80px;
  background-color: #235eff;
  color: #fff;
  font-size: 14px;
  border-radius: 20px;
  border: none;
  cursor: pointer;
}

.send-btn:hover {
  background-color: #1e4bb8;
}
</style>



