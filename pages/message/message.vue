<template>
  <view class="container_wrap">
    <view class="message_setting">
      <view class="setting_items" v-for="(status, index) in messageStatus" :key="index" @click="handleStatusChange(status.title)">
        <image class="setting_icon" :src="status.icon" mode="scaleToFill" />
        <view :class="{ 'active-status': status.isActive }" class="message_status">{{ status.title }}</view>
      </view>
    </view>
    <view class="message_list">
      <view class="message_item" v-for="(message, index) in messages" :key="index" @click="toggleReadStatus(index)" :class="{ 'selected': message.isSelected }">
        <image class="text_icon" :src="message.icon" mode="scaleToFill" />
        <view  class="unread-dot" v-if="!message.isRead"></view>
        
        <view class="message_item_content" >
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
          title: "全部已读",
          icon: "/static/images/已读.png",
          isActive: false,
        },
        {
          title: "全部未读",
          icon: "/static/images/未读.png",
          isActive: true,
        }
      ],
      messages: [
        {
          title: "审批消息",
          content: "盘点审批消息",
          date: "2021-21-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
        },
        {
          title: "进度消息",
          content: "进度跟踪消息",
          date: "2021-11-10",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
        },
        {
          title: "仓储助手",
          content: "仓储助手消息",
          date: "2021-11-12",
          icon: "/static/images/dog.jpg",
          isRead: false,
          isSelected: false,
        }
      ]
    };
  },
  methods: {
    handleStatusChange(title) {
      if (title === "全部未读") {
        this.markAllAsUnread();
        this.messageStatus[0].isActive = false;
        this.messageStatus[1].isActive = true;
      } else if (title === "全部已读") {
        this.markAllAsRead();
        this.messageStatus[0].isActive = true;
        this.messageStatus[1].isActive = false;
      }
    },
    markAllAsRead() {
      this.messages.forEach(message => {
        message.isRead = true;
      });
    },
    markAllAsUnread() {
      this.messages.forEach(message => {
        message.isRead = false;
      });
    },
    toggleReadStatus(index) {
      this.messages[index].isSelected = !this.messages[index].isSelected;
      if (!this.messages[index].isRead) {
        this.messages[index].isRead = !this.messages[index].isRead;
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
  height: 100vh; /* 使背景颜色充满整个页面 */
  overflow-y: auto; /* 如果内容过多，产生滚动条 */
}

.message_setting {
  display: flex;
  justify-content: flex-end; /* 靠右显示 */

  padding: 20rpx;
  border-radius: 10rpx;
  box-shadow: 0 2rpx 2rpx -1rpx rgba(0, 0, 0, 0.1);
}

.setting_items {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-left: 20rpx; /* 使设置项之间有间隔 */
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
  position: relative; /* 使未读标记定位生效 */
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
  
  box-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.unread-dot {
  position: absolute;
  top: 4px;
  left: 88rpx;
  width: 20rpx;
  height: 20rpx;
  background-color: red;
  border-radius: 50%;
  z-index: 10;
}

.message_item_content {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.selected {
  background-color: rgba(219, 227, 249); /* 选中时背景颜色变深 */
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