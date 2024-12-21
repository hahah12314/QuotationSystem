// main.js
import Vue from 'vue';
import App from './App';
import { $http } from '@escook/request-miniprogram';
import store from './store';

Vue.prototype.$store = store;  // 将store挂载到Vue实例
uni.$http = $http;

// $http.baseUrl = 'http://localhost:8086/wanshu'; // 替换为你的基础URL
$http.baseUrl = 'https://jw.hnuedu.top/api'; // 替换为你的基础URL


// 请求拦截器
$http.beforeRequest = function(options) {
  uni.showLoading({
    title: '数据加载中...',
  });
  const token = uni.getStorageSync('token');
  if (token) {
    options.header = {
      ...options.header,
      Authorization: token // 添加 Token 到请求头
    };
  }
};

// 响应拦截器
$http.afterRequest = function(res) {
  uni.hideLoading();
  if (res.statusCode === 401) {
    uni.reLaunch({ url: '/subpkg/login/login' });
  } else if (res.statusCode !== 200) {
    uni.showToast({
      title: '请求失败，请重试',
      icon: 'none'
    });
  }
};

// 全局异常处理
Vue.config.errorHandler = (err, vm, info) => {
  console.error('捕获到错误:', err);
  console.error('错误信息:', info);
  
  // 对于 TypeError，可以进行特定处理
  if (err instanceof TypeError) {
    // 不做任何处理，或显示友好的提示
    uni.showToast({
      title: '出现轻微错误，但不影响功能',
      icon: 'none'
    });
  } else {
    // 对其他类型的错误进行处理
    uni.showToast({
      title: '发生了一个错误，请稍后再试',
      icon: 'none'
    });
  }
};

// #ifndef VUE3
Vue.config.productionTip = false;
App.mpType = 'app';
const app = new Vue({
  ...App,
  onLaunch() {
    const token = uni.getStorageSync('token');
    if (!token) {
      uni.reLaunch({
        url: '/subpkg/login/login'
      });
    }
  }
});
app.$mount();
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue';
export function createApp() {
  const app = createSSRApp(App);
  app.mixin({
    onLaunch() {
      const token = uni.getStorageSync('token');
      if (!token) {
        uni.reLaunch({
          url: '/subpkg/login/login'
        });
      }
    }
  });
  return {
    app
  };
}
// #endif
