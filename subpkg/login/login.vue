<template>
  <view class="login-container">
    <!-- 登录框 -->
    <view class="login-title">
      <!-- 系统名称 -->
      <view class="title">金威报价系统</view>
      <!-- 系统副标题 -->
      <view class="subtitle">简单 · 智能 · 高效 · 安全</view>
    </view>
    <!-- 输入框：账号 -->
    <view class="input-container">
        <!-- 输入框：账号 -->
        <view class="input-group">
          <text class="input-label">账号:</text>
          <input 
            type="text" 
            v-model="username" 
            :placeholder="usernamePlaceholder" 
            @focus="clearPlaceholder('username')" 
            @blur="restorePlaceholder('username')" 
            class="input"
          />
        </view>
    
        
      </view>
      <view class="input-container">
        <!-- 输入框：密码 -->
        <view class="input-group">
          <text class="input-label">密码:</text>
          <input 
            type="password" 
            v-model="password" 
            :placeholder="passwordPlaceholder" 
            @focus="clearPlaceholder('password')" 
            @blur="restorePlaceholder('password')" 
            class="input"
          />
        </view>
      </view>
    <!--  <view class="remember-and-forget">
        <view class="remember">
          <checkbox class="remember-check" :checked="rememberMe" @change="onRememberMeChange" />
          <text class="remember-text">记住我</text>
        </view>
        <view class="forget">
          <text class="forget-text" @click="onForgetClick">忘记密码？</text>
        </view>
      </view> -->
      

    <!-- 登录按钮 -->
    <button class="login-button" @click="handleLogin">登录</button>

   
  </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        username: '',
              password: '',
              usernamePlaceholder: '请输入账号',
              passwordPlaceholder: '请输入密码'
      };
    },
    methods: {
      handleLogin() {
        if (!this.username || !this.password) {
          uni.showToast({
            title: '请输入账号和密码',
            icon: 'none'
          });
          return;
        }
        uni.showLoading({
          title: '登录中...'
        });
        const data = {
                username: this.username,
                password: this.password
              };
        
             uni.$http.post('/login', data)
                     .then(response => {
                       uni.hideLoading()
                       if (response.data.code === 200) {
                         console.log(response)
                         uni.setStorageSync('token', response.header.Authorization);
                         uni.setStorageSync('username', this.username);
                         uni.reLaunch({
                           url: '/pages/home/home'
                         });
                       } else {
                         uni.showToast({
                           title: response.data.msg,
                           icon: 'none',
                           duration: 2000
                         });
                       }
                     })
                     .catch(error => {
                       uni.hideLoading()
                       uni.showToast({
                         title: '登录失败，请检查网络或重试',
                         icon: 'none',
                         duration: 2000
                       });
                       console.error('登录失败:', error);
                     });
            
        
      },
      clearPlaceholder(inputName) {
            this[inputName + 'Placeholder'] = '';
          },
          restorePlaceholder(inputName) {
           if (this[inputName] === '') {
                   this[inputName + 'Placeholder'] = inputName === 'username' ? '请输入账号' : '请输入密码';
                 }
          },
      navigateToRegister() {
        uni.navigateTo({
          url: '/pages/register/register'
        });
      },
      navigateToForgotPassword() {
        uni.navigateTo({
          url: '/pages/forgot-password/forgot-password'
        });
      }
    }
  };
</script>

<style lang="less">
  .login-container {
    display: flex;

    align-items: center;
    flex-direction: column;
    height: 100vh;
    padding: 20px;
    background: linear-gradient(135deg, #eaedf6, #ebeeff);
    /* 温和渐变背景 */

    .login-title {
      margin-top: 40px;
      text-align: center;
    }

    .title {
      font-size: 40px;
      /* 增大标题字体 */
      font-weight: bold;
      color: #2d65ff;
      margin-bottom: 5px;
      /* 增加标题与副标题的间距 */
    }

    .subtitle {
      font-size: 18px;
      color: #888;
      /* 统一的字体颜色 */
      margin-bottom: 30px;
      /* 增加副标题与输入框的间距 */
    }

    .input-container {
      width: 90%;
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      height: 60px;
      background-color: #e3e8f7;
      /* 背景颜色可以根据需要调整 */
      border-radius: 30px;
      /* 圆角半径 */
    }

    .input-group {
      display: flex;
      align-items: center;
    }

    .input-label {
      margin-left: 20px;
      margin-right: 5px;
      color: #888;
    }


    .input {
      flex: 1;
      padding: 12px;
      border: none;
      /* 去掉边框 */
      border-radius: 6px;
      font-size: 14px;
      color: #999;
      transition: all 0.3s ease;
    }
    .remember-and-forget {
        display: flex;
        justify-content: space-between;
        color: #999;
    }
    .remember {
      
    }
    .forget {
       
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

   

    .login-button:active {
      background-color: #60bac2;
      transform: translateY(2px);
    }

    

    
  }
</style>