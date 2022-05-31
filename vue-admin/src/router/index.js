import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import Home from '../views/home/home'
import Login from '../views/Login'
import MyIndex from '../views/MyIndex'
import UserList from '../views/User/UserList'
import Category from '../views/Goods/Category'
import GoodsList from '../views/Goods/GoodsList'
import GoodsVerify from '../views/Goods/GoodsVerify'
import Empty from '../views/Goods/Empty'
import GoodsWarn from '../views/Goods/GoodsWarn'
import ReleaseMessage from '../views/SystemMessage/ReleaseMessage'
import PurchaseInfoList from '../views/PurchaseInfo/PurchaseInfoList'
//引入store
import store from '@/store'

import { Message } from "element-ui";

//解决NavigationDuplicated报错问题
let changePush = VueRouter.prototype.push;
let changeReplace = VueRouter.prototype.replace;
//重写push
VueRouter.prototype.push = function (location,resolve,reject){
  if (resolve && reject){
    changePush.call(this,location,resolve,reject);
  }else {
    changePush.call(this,location,() =>{},() =>{})
  }
}
//重写replace
VueRouter.prototype.replace = function (location,resolve,reject){
  if (resolve && reject){
    changeReplace.call(this,location,resolve,reject);
  }else {
    changeReplace.call(this,location,() =>{},() =>{})
  }
}


const routes = [
  {
    path: '/',
    redirect: '/home',
    component: MyIndex,
    children: [
      {
        path: '/home',
        component: Home
      },
      {
        path: '/about',
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
      },
      {
        path: '/userList',
        component: UserList
      },
      {
        path: '/category',
        component: Category
      },
      {
        path: '/goodsList',
        component: GoodsList
      },
      {
        path: '/goodsVerify',
        component: GoodsVerify
      },
      {
        path: '/empty',
        component: Empty
      },
      {
        path: '/goodsWarn',
        component: GoodsWarn
      },
      {
        path: '/releaseMessage',
        component: ReleaseMessage
      },
      {
        path: '/purchaseInfoList',
        component: PurchaseInfoList
      },
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
]


const router = new VueRouter({
  routes
})


//全局守卫
router.beforeEach( async(to,from,next)=>{
  let token = Vue.$cookies.get('access_token_admin')
  // console.log('token',token)
  //判断是否登录过
  if (token){
    let userData = store.state.user.userData
    // console.log('userDate',JSON.stringify(userData))
    //判断是否需要获取信息
    if (JSON.stringify(userData) === "{}"){
      // console.log('发送请求，获取信息',userData)
      let userInfo = await store.dispatch('getUserInfo')
      //判断登录是否过期
      if (userInfo.state === 4010){
        //登录过期，删除cookie中的access_token
        Vue.$cookies.remove('access_token_admin')
        next('/login')
      }else {
        await store.dispatch('getUserActiveInfo')
      }
    }
    if (to.path === '/login' || to.path === '/register'){
      next('/home')
    }else {
      next()
    }
  }else {
    if (to.path !== '/login'){
      next('/login')
    }
    next()
  }
})

export default router
