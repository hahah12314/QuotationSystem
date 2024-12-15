<template>
  <view class="container">
    <view class="content-container">
      <!-- 标题 -->
      <view class="title">个人信息</view>
      <!-- 头像修改 -->
      <view class="avatar-container">
        <image :src="updateForm.avatar || require('@/static/user1.png')" class="avatar-image" @click="chooseImage"></image>
        <view class="avatar-text">上传头像</view>
      </view>
      <view class="input-container">
        <!-- 输入框：账号 -->
        <view class="input-group">
          <text class="input-label">账号:</text>
          <input type="text" v-model="updateForm.username" class="input" disabled />
        </view>
      </view>
      <view class="input-container">
        <!-- 输入框：姓名 -->
        <view class="input-group">
          <text class="input-label">姓名:</text>
          <input type="text" v-model="updateForm.name" class="input" />
        </view>
      </view>
      <!-- 修改密码 -->
      <view class="input-container">
        <!-- 输入框：密码 -->
        <view class="input-group">
          <text class="input-label">邮箱号:</text>
          <input type="text" v-model="updateForm.email" class="input" />
        </view>
      </view>
      <view class="input-container">
        <!-- 输入框：确认密码 -->
        <view class="input-group">
          <text class="input-label">手机号:</text>
          <input type="text" v-model="updateForm.mobile" class="input" />
        </view>
      </view>
      <!-- 提交修改 -->
      <button class="login-button" @click="handleSubmit">提交</button>
    </view>
  </view>
</template>
<script>
export default {
  data() {
    return {
      updateForm: {
        
      },
    };
  },
  onLoad() {
    // 数据回显
    const userInfo = uni.getStorageSync("userInfo");
    this.updateForm=userInfo
    this.updateForm.avatar = userInfo.avatar || require('@/static/user1.png');
    
  },
  methods: {
    chooseImage() {
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          const tempFilePaths = res.tempFilePaths;
          this.uploadAvatar(tempFilePaths[0]);
        }
      });
    },
    uploadAvatar(filePath) {
      uni.showLoading({
        title: '上传中...',
      });
      const token = uni.getStorageSync('token');
      uni.uploadFile({
        url: "http://localhost:8086/wanshu/upload", // 后端接口地址
        header: {
          Authorization: token
        },
        filePath: filePath,
        name: 'file', // 文件对应的 key
        formData: {
          // 其他需要传递的参数
        },
        success: (uploadRes) => {
          try {
            const response = JSON.parse(uploadRes.data);
            if (response.data) {
               const filename = response.data.split("//").pop()
              this.updateForm.avatar = `http://so9r96ky6.hn-bkt.clouddn.com/images//${filename}`;
              uni.showToast({
                title: "头像上传成功",
                icon: 'success',
                duration: 2000
              });
            } else {
              uni.showToast({
                title: "上传失败",
                icon: 'none',
                duration: 2000
              });
            }
          } catch (error) {
            uni.showToast({
              title: "上传失败",
              icon: 'none',
              duration: 2000
            });
            console.error("解析响应失败:", error);
          }
        },
        fail: (err) => {
          uni.showToast({
            title: "上传失败",
            icon: 'none',
            duration: 2000
          });
          console.error("上传失败:", err);
        },
        complete: () => {
          uni.hideLoading();
        }
      });
    },
    async handleSubmit() {
      try {
        // 更新个人信息
        const updateResponse = await uni.$http.post("/sys/sysUser/update", this.updateForm);
        if (updateResponse.data.code === "SUCCESS") {
          uni.showToast({
            duration:2000,
            title:'更新成功'
          })
          uni.removeStorageSync("userInfo");
          uni.setStorageSync("userInfo",this.updateForm)
          
          
          uni.reLaunch({
            url: '/pages/my/my'
          })
        } else {
          uni.showToast({
            title: updateResponse.data.msg,
            icon: 'none',
            duration: 2000
          });
          return; // 如果失败，不继续执行后面的代码
        }
      } catch (error) {
        uni.showToast({
          title: "提交失败",
          icon: 'none',
          duration: 2000
        });
        console.error("错误信息:", error); // 输出错误信息，以便调试
      }
    },
    
    
  }
}
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #e7eaf4;
  height: 100vh;
  box-sizing: border-box;
}

.content-container {
  position: relative;
  top: 10%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 90%;
}

.title {
  font-size: 30px;
  font-weight: bold;
  color: #2d65ff;
  position: relative;
}

.input-container {
  width: 90%;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  height: 60px;
  background-color: #e3e8f7; /* 背景颜色可以根据需要调整 */
  border-radius: 30px; /* 圆角半径 */
}

.input-group {
  display: flex;
  align-items: center;
}

.input-label {
  width: 85px;
  box-sizing: border-box;
  margin-left: 20px;
  margin-right: 5px;
  color: #888;
}

.avatar-text {
  margin-top: 7px;
  color: #888;
}

.input {
  width: 200px;
  flex: 1;
  padding: 5px;
  border: none; /* 去掉边框 */
  border-radius: 6px; /* 圆角半径 */
  font-size: 14px;
  color: #999;
  transition: all 0.3s ease;
}

.login-button {
  margin-top: 30px;
  width: 90%;
  height: 60px;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: #ffffff;
  background-color: #235eff;
  border-radius: 30px;
  transition: background-color 0.3s, transform 0.2s ease-in-out;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.avatar-container {
  position: relative;
  margin: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
}

.avatar-image {
  width: 110px;
  height: 110px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  cursor: pointer;
}
</style>