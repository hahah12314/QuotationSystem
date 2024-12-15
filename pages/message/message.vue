<template>
  <view class="container_wrap">
    <view class="message_setting">
      <view class="setting_items" v-for="(status, index) in messageStatus" :key="index"
        @click="handleStatusChange(status.title)">
        <image class="setting_icon" :src="status.icon" mode="scaleToFill" />
        <view :class="{ 'active-status': status.isActive }" class="message_status">{{ status.title }}</view>
      </view>
    </view>
    <view class="message_list">
      <view class="message_item" v-for="(message, index) in filteredMessages" :key="index" @click="goToPage(message, index)"
        :class="{ 'selected': message.isSelected }">
        <image class="text_icon" :src="message.icon" mode="scaleToFill" />
        <view class="unread-dot" v-if="message.unreadCount > 0">
          <text class="unread-count">{{ message.unreadCount }}</text>
        </view>
        <view class="message_item_content">
          <text class="text_title">{{ message.title }}</text>
          <view class="text_detail">
            <text class="text_profile">{{ message.content }}</text>
            <text class="text_profile text_right">{{ message.date }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    const { safeAreaInsets } = uni.getSystemInfoSync();

    return {
      safeAreaInsets,
      messageStatus: [
        {
          title: "已读消息",
          icon: "/static/images/已读.png",
          isActive: false,
        },
        {
          title: "未读消息",
          icon: "/static/images/未读.png",
          isActive: true,
        }
      ],
      messages: [
        {
          title: "待审批消息",
          content: "进度跟踪消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 57,
          unreadCount: 0
        },
        {
          title: "审核员",
          content: "审核员消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 57,
          unreadCount: 0
        },
        {
          title: "报价员",
          content: "报价员消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 58,
          unreadCount: 0
        },
        {
          title: "成本管理员",
          content: "成本管理员消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 56,
          unreadCount: 0
        },
        {
          title: "高层决策者",
          content: "高层决策者消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 55,
          unreadCount: 0
        },
        {
          title: "系统管理员",
          content: "系统管理员消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 49,
          unreadCount: 0
        },
        {
          title: "超级管理员",
          content: "超级管理员消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 54,
          unreadCount: 0
        },
        {
          title: "仓储助手",
          content: "仓储助手消息",
          date: "2021-11-12",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
          roleId: 0,
          unreadCount: 0
        }
      ],
      userInfo: null,
      selectedStatus: "未读消息"
    };
  },
  onLoad() {
    const userInfo = uni.getStorageSync("userInfo");
    this.userInfo = userInfo;
    this.fetchMessages();
  },
  computed: {
    filteredMessages() {
      return this.messages.filter(message =>
        (this.selectedStatus === "未读消息" && message.unreadCount > 0) ||
        (this.selectedStatus === "已读消息" && message.unreadCount === 0)
      );
    }
  },
  methods: {
    handleStatusChange(title) {
      this.selectedStatus = title;
      this.messageStatus.forEach(status => {
        status.isActive = status.title === title;
      });
    },
    fetchMessages() {
      const apiEndpoint = '/messages/unread';
      this.messages.forEach((message) => {
        uni.$http.get(apiEndpoint, {
            receiverId: this.userInfo.roleId, // 当前用户的 roleId
            senderId: message.roleId // 消息的 roleId
          })
          .then(res => {
            message.unreadCount = res.data.data.length;
            console.log('获取消息成功', res);
          })
          .catch(err => {
            console.error(`获取消息失败，receiverId: ${this.userInfo.roleId}, senderId: ${message.roleId}`, err);
          });
      });
    },
    goToPage(message, index) {
      this.toggleReadStatus(index, message);
      const pages = [
        "/subpkg/message/processInfo/processInfo",
        "/subpkg/message/assistantInfo/assistantInfo",
      ];
      let url;
      if (message.roleId !== 0) {
        url = pages[0];
      } else {
        url = pages[1];
      }
      
      // 将 message 对象存储到本地存储中
      uni.setStorageSync('selectedMessage', JSON.stringify(message));

      if (url) {
        uni.navigateTo({
          url
        });
      }
    },
    async toggleReadStatus(index, message) {
      this.messages[index].isSelected = !this.messages[index].isSelected;
      this.messages[index].isRead = true;
      if (this.messages[index].unreadCount > 0) {
        this.messages[index].unreadCount = 0;
        
        // 更新消息状态到后端
        await uni.$http.get('/messages/markRead', {
            receiverId: this.userInfo.roleId, // 当前用户的 roleId
            senderId: this.messages[index].roleId // 消息的 roleId
          })
          .then(() => {
            console.log('消息状态更新成功');
          })
          .catch(err => {
            console.error('消息状态更新失败', err);
          });
      }
    }
  }
};
</script>
<style lang="less">
  .container_wrap {
    display: flex;
    flex-direction: column;
    background-color: #e7eaf4;
    padding: 20rpx;
    height: 100vh;
    /* 使背景颜色充满整个页面 */
    overflow-y: auto;
    /* 如果内容过多，产生滚动条 */
  }

  .message_setting {
    display: flex;
    justify-content: space-between;
    /* 靠右显示 */
    padding: 20rpx;
    border-radius: 10rpx;
    box-shadow: 0 2rpx 2rpx -1rpx rgba(0, 0, 0, 0.1);
  }

  .setting_items {
    display: flex;
    align-items: center;
    cursor: pointer;
    margin-left: 20rpx;
    /* 使设置项之间有间隔 */
  }

  .setting_icon {
    width: 50rpx;
    height: 50rpx;
    margin-right: 10rpx;
    border-radius: 5px;
  }

  .message_status {
    color: grey;
    font-size: 28rpx;
  }

  .active-status {
    color: rgba(87, 131, 253);
    font-weight: bold;
  }

  .message_list {
    border-radius: 10rpx;
    box-shadow: 0 2rpx 2rpx -1rpx rgba(0, 0, 0, 0.1);
    overflow: hidden;
    padding: 10px;
  }

  .message_item {
    display: flex;
    align-items: center;
    padding: 20rpx;
    border-bottom: 1px solid #eee;
    transition: background-color 0.3s;
    position: relative;
    /* 使未读标记定位生效 */
  }

  .message_item:last-child {
    border-bottom: none;
  }

  .message_item:hover {
    background-color: #f3f5f9;
  }

  .text_icon {
    width: 80rpx;
    height: 80rpx;
    margin-right: 20rpx;
    border-radius: 5px;
    box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.1);
    /* 添加阴影 */
  }

  .unread-dot {
    position: absolute;
    top: 4px;
    left: 88rpx;
    width: 26rpx;
    height: 26rpx;
    background-color: red;
    border-radius: 50%;
    z-index: 10;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .unread-count {
    color: white;
    font-size: 14rpx;
    font-weight: bold;
  }

  .message_item_content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .selected {
    background-color: rgba(219, 227, 249);
    /* 选中时背景颜色变深 */
  }

  .text_title {
    font-weight: bold;
    font-size: 32rpx;
    margin-bottom: 10rpx;
  }

  .text_detail {
    display: flex;
    justify-content: space-between;
    font-size: 24rpx;
    color: #666;
  }

  .text_profile {
    flex: 1;
  }

  .text_right {
    margin-left: auto;
  }
</style>