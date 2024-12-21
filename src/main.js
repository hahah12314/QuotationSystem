import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$http = axios
axios.defaults.baseURL = "http://localhost:8086/wanshu"
// axios.defaults.baseURL = "/api/"
// 添加 Axios 的拦截器
// 创建一个全局的消息提示方法
Vue.prototype.$globalMessage = function (type, message) {
  this.$message[type](message);
};

// 全局 Vue 错误处理
Vue.config.errorHandler = function (err, vm, info) {
  console.error('Vue 错误:', err, vm, info);
  Vue.prototype.$globalMessage('error', '发生了一个错误，请稍后再试');
};

// 全局 JavaScript 错误处理
window.onerror = function (message, source, lineno, colno, error) {
  console.error('JavaScript 错误:', message, source, lineno, colno, error);
  Vue.prototype.$globalMessage('error', '发生了一个错误，请稍后再试');
};
axios.interceptors.request.use(config => {
  // 每次发送请求我们都携带token信息
  var token = sessionStorage.getItem('token')
  config.headers['Authorization'] = token // 请求头带上Token
  return config;
}, error => {
  return Promise.reject(error);
})
/** 使用router钩子函数来处理 */
router.beforeEach((to, from, next) => {
  const username = sessionStorage.getItem('username')

  const menuList = JSON.parse(localStorage.getItem('menuList')) || []

  console.log(to, menuList)
  // 检查用户是否登录
  if ((to.name !== 'login' && !username) && to.name !== 'register') {
    next({ name: 'login' })
  } else if (to.name == 'login') {
    next()
  }
  else {
    // 检查用户是否有权限访问目标页面

    let hasPermission = false;

    for (let item of menuList) {
      if (item.path === to.path) {
        hasPermission = true;
        break;
      }
      if (item.children && item.children.length > 0) {
        for (let child of item.children) {
          if (child.path === to.path) {
            hasPermission = true;
            break;
          }
        }
      }
    }
    if (hasPermission || to.name == 'home' || menuList.length==0) {
      next()
    } else {
      next(false) // 阻止跳转
      Vue.prototype.$globalMessage('error', '您没有权限访问该页面')
    }
  }
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
