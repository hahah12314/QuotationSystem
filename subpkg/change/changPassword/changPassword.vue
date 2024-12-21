<template>
    <view class="container">
        <view class="content-container">
            <!-- 标题-->
            <view class="title">修改密码</view>
            <!-- 输入框：账号 -->
            <view class="input-container">
                <view class="input-group">
                    <text class="input-label">账号:</text>
                    <input type="text" v-model="updateForm.username" placeholder="请输入账号" class="input" disabled />
                </view>
            </view>
            <!-- 输入框：原密码 -->
            <view class="input-container">
                <view class="input-group">
                    <text class="input-label">原密码:</text>
                    <view class="input-content">
                      <input type="password" v-model="originalPassword" placeholder="请输入原密码" class="input" @blur="validateOriginalPassword" />
                      <view v-if="originalPasswordError" class="error-message">{{ originalPasswordError }}</view>
                    </view>
                </view>
            </view>
            <!-- 修改密码 -->
            <view class="input-container">
                <!-- 输入框：密码 -->
                <view class="input-group">
                    <text class="input-label">新密码:</text>
                    <view class="input-content">
                    <input type="password" v-model="newPassword" placeholder="请输入新密码" class="input" />
                    <view v-if="newPasswordError" class="error-message">{{ newPasswordError }}</view>
                    </view>
                </view>
            </view>
            <view class="input-container">
                <!-- 输入框：确认密码 -->
                <view class="input-group">
                    <text class="input-label">确认新密码:</text>                   
                    <view class="input-content">
                    <input type="password" v-model="rePassword" placeholder="请再次输入新密码" class="input" @blur="validateNewPasswords" />
                    <view v-if="confirmNewPasswordError" class="error-message">{{ confirmNewPasswordError }}</view>
                    </view>
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
                userId: -1,
                username: "",
                password: "",
            },
            newPassword:"",
            rePassword: "",
            originalPassword: "",
            originalPasswordError: "",
            newPasswordError: "",
            confirmNewPasswordError: ""
        };
    },
    onLoad() {
        // 找到userid
        const userInfo = uni.getStorageSync("userInfo");
        this.updateForm=userInfo
      
    },
    methods: {
        async handleSubmit() {
            try {
                // 校验原密码是否正确
                if (this.originalPasswordError) {
                    uni.showToast({
                        title: "原密码不正确",
                        icon: 'none',
                        duration: 2000
                    });
                    return;
                }

                // 校验密码是否一致
                if (this.newPassword !== this.rePassword) {
                    uni.showToast({
                        title: "两次密码输入不一致，请重新输入",
                        icon: 'none',
                        duration: 2000
                    });
                    return; // 如果密码不一致，阻止提交
                }

                // 更新用户密码
                this.updateForm.password=this.newPassword
                console.log("参数", this.updateForm);
                const updateResponse = await uni.$http.post("/sys/sysUser/update", this.updateForm);
                if (updateResponse.data.code === "SUCCESS") {
                    uni.showToast({
                      title:'密码更新成功'
                    })
                    uni.removeStorageSync("token");
                    uni.removeStorageSync("userInfo");
                    uni.reLaunch({
                        url: '/pages/login/login'
                    });
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
        async validateOriginalPassword() {
            if (!this.originalPassword) {
                this.originalPasswordError = '原密码不能为空';
                return;
            }
           

            try {
                const response = await uni.$http.post("/sys/sysUser/validatePassword",{
                        username: this.updateForm.username,
                        password: this.originalPassword
                    }
                );

                if (response.data.data === true) {
                    this.originalPasswordError = '';
                } else {
                    this.originalPasswordError = '原密码不正确';
                }
            } catch (error) {
                console.error('验证原密码失败:', error);
                this.originalPasswordError = '验证原密码失败';
            }
        },
        validateNewPasswords() {
            if (this.newPassword && this.rePassword) {
                if (this.newPassword === this.rePassword) {
                    this.newPasswordError = '';
                    this.confirmNewPasswordError = '';
                } else {
                    this.newPasswordError = '';
                    this.confirmNewPasswordError = '新密码和确认新密码不一致';
                }
            } else {
                this.newPasswordError = '';
                this.confirmNewPasswordError = '';
            }
        }
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
    top: 15%;
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
    margin: 20px;
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
    margin-left: 20px;
    margin-right: 5px;
    color: #888;
}

.input {
    width: 200px;
    flex: 1;
    padding: 5px;
    border: none; /* 去掉边框 */
    border-radius: 6px;
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
.input-content{
  display: flex;
  flex-direction: column;
}
.error-message {
    color: red;
    font-size: 20rpx;
    /* 确保错误消息显示在输入框下方 */
}
</style>