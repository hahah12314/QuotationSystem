import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import commonCustomer from '@/components/pages/customer/commonCustomer.vue'
import customerList from '@/components/pages/customer/customerList.vue'
import allOrder from '@/components/pages/order/allOrder.vue'
import myOrder from '@/components/pages/order/myOrder.vue'
import firstPage from '@/components/pages/FirstPage.vue'
import Login from '@/components/common/Login.vue'
import SysMenu from '@/components/pages/sys/SysMenu.vue'
import SysRole from '@/components/pages/sys/SysRole.vue'
import SysUser from '@/components/pages/sys/SysUser.vue'
import SysLog from '@/components/pages/sys/SysLog.vue'
import materialDetail from '@/components/pages/cost/materialDetail'
import materialManage from '@/components/pages/cost/materialManage'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    redirect: '/firstPage'
    ,
    children: [
      {
        path: '/firstPage',
        name: 'firstPage',
        component: firstPage
      },
      {
        path: '/commonCustomer',
        name: 'commonCustomer',
        component: commonCustomer
      },
      {
        path: '/customerList',
        name: 'customerList',
        component: customerList
      },
      {
        path: '/allOrder',
        name: 'allOrder',
        component: allOrder
      },
      {
        path: '/myOrder',
        name: 'myOrder',
        component: myOrder
      },
      {
        path: '/sysMenu',
        name: 'sysMenu',
        component: SysMenu
      },
      {
        path: '/sysRole',
        name: 'sysRole',
        component: SysRole
      },
      {
        path: '/sysUser',
        name: 'sysUser',
        component: SysUser
      },
      {
        path: '/sysLog',
        name: 'sysLog',
        component: SysLog
      },
      {
        path: '/materialManage',
        name: 'materialManage',
        component: materialManage
      },
      {
        path: '/materialDetail',
        name: 'materialDetail',
        component: materialDetail
      },
      {
        path: '/materialQuotation',
        name: 'materialQuotation',
        component: () => import('@/components/pages/quotation/MaterialQuotation')
      },
      {
        path: '/quotationReview',
        name: 'quotationReview',
        component: () => import('@/components/pages/quotation/QuotationReview')
      }
    ]
  },
  {
    path:'/login',
    name:'login',
    component:Login

  }

]

const router = new VueRouter({
  routes
})

export default router
