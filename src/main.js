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
// 添加 Axios 的拦截器
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
  
  if (to.name !== 'login' && !username) next({ name: 'login' })
  else next()
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
