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
      },
      {
        path: '/addQuotation',
        name: 'addQuotation',
        component: () => import('@/components/pages/quotation/AddQuotation')
      },
      {
        path: '/processCost',
        name: 'processCost',
        component: () => import('@/components/pages/data/ProcessingCost')
      },
      {
        path: '/materialCost',
        name: 'materialCost',
        component: () => import('@/components/pages/data/materialCost')
      },
      {
        path: '/cuttingCost',
        name: 'cuttingCost',
        component: () => import('@/components/pages/data/cuttingCost')
      },
      {
        path: '/paintingCost',
        name: 'paintingCost',
        component: () => import('@/components/pages/data/paintingCost')
      },
      {
        path: '/sufaceTreatment',
        name: 'sufaceTreatment',
        component: () => import('@/components/pages/data/sufaceTreatment')
      },
      {
        path: '/orderAnalysis',
        name: 'orderAnalysis',
        component: () => import('@/components/pages/analysis/OrderAnalysis')
      },
      {
        path: '/customerAnalysis',
        name: 'customerAnalysis',
        component: () => import('@/components/pages/analysis/CustomerAnalysis')
      },
      {
        path: '/profitAnalysis',
        name: 'profitAnalysis',
        component: () => import('@/components/pages/analysis/ProfitAnalysis')
      },
      {
        path: '/cost/materialDetail/:id',
        name: 'materialDetail',
        component: () => import('@/components/detail/MaterialInfo')
      },
      {
        path: '/workerManagement',
        name: 'workerManagement',
        component: () => import('@/components/pages/worker/workerManage')
      },
      {
        path: '/hourManagement',
        name: 'hourManagement',
        component: () => import('@/components/pages/worker/hourManage')
      },
      {
        path: '/quoteGeneration',
        name: 'quoteGeneration',
        component: () => import('@/components/detail/QuotationInfo')
      },
      {
        path: '/auditQuotation',
        name: 'auditQuotation',
        component: () => import('@/components/pages/quotation/AuditQuotation')
      },
      {
        path: '/personInfo',
        name: 'personInfo',
        component: () => import('@/components/pages/person/personInfo')
      },

    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/components/common/Register')
  }

]

const router = new VueRouter({
  routes
})
// 全局处理 NavigationDuplicated 错误
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') {
      throw err;
    }
  });
};
export default router
