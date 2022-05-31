import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import Home from '@/pages/Home'
import Search from '@/pages/Search'
import Login from '@/pages/Login'
import ForgetPassword from '@/pages/Login/ForgetPassword'
import Register from '@/pages/Register'
import UserInfo from '@/pages/UserInfo'
import ReleaseGoods from '@/pages/ReleaseGoods'
import showGoodsInfo from '@/pages/ReleaseGoods/showGoodsInfo'
import GoodsDetailInfo from '@/pages/GoodsDetailInfo'
import CollectedList from '@/pages/CollectedList'
import MyGoods from '@/pages/MyGoods'
import MessageCenter from '@/pages/MessageCenter'
import ReplyComment from '@/pages/MessageCenter/ReplyComment/Comment'
import GoodsComment from '@/pages/MessageCenter/GoodsComment/Comment'
import SystemAnnouncement from '@/pages/SystemAnnouncement'
import ReleaseRequirementInfo from '@/pages/ReleaseRequirementInfo'
import PurchaseInfoList from '@/pages/PurchaseInfoList'
import PurchaseInfoDetail from '@/pages/PurchaseInfoDetail'
//引入store
import store from '@/store'

import { Message,Notification } from "element-ui";



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


const router = new VueRouter({
  // mode: 'history',
  // base: process.env.BASE_URL,
  routes:[
    {
      path:"/",
      redirect:'/home'
    },
    {
      path:"/home",
      component:Home,
      meta:{show:true},
      name: 'home',
      props:($route)=>{
        return {
          currentPage:$route.params.currentPage,
          size:$route.params.size,
        }
      },
    },
    {
      // path:"/search/:keyword/:currentPage/:size?",
      path:"/search/:keyword?",
      component:Search,
      meta:{show:true},
      name:"search",
    },
    {
      path:"/login",
      component:Login,
      meta:{show:false},
    },
    {
      path:"/forgetPassword",
      component:ForgetPassword,
      meta:{show:false},
    },
    {
      path:"/register",
      component:Register,
      meta:{show:false},
    },
    {
      path:"/userInfo",
      component:UserInfo,
      meta:{show:true},
    },
    {
      path:"/ReleaseGoods",
      component:ReleaseGoods,
      meta:{show:true},
    },
    {
      path:"/releaseRequirementInfo",
      component:ReleaseRequirementInfo,
      meta:{show:true},
    },
    {
      path: "/goodsDetailInfo/:goodsId",
      component: GoodsDetailInfo,
      meta: {show: true},
      name: "goodsDetailInfo",
    },
    {
      path: "/collectedList",
      component: CollectedList,
      meta: {show: true},
    },
    {
      path: "/myGoods",
      component: MyGoods,
      meta: {show: true},
    },
    {
      path: "/showGoodsInfo",
      component: showGoodsInfo,
      meta: {show: true},
    },
    {
      path: "/messageCenter",
      component: MessageCenter,
      meta: {show: true},
    },
    {
      path: "/replyComment",
      component: ReplyComment,
      meta: {show: true},
    },
    {
      path: "/goodsComment",
      component: GoodsComment,
      meta: {show: true},
    },
    {
      path: "/systemAnnouncement",
      component: SystemAnnouncement,
      meta: {show: false},
    },
    {
      path: "/purchaseInfoList",
      component: PurchaseInfoList,
      meta: {show: false},
    },
    {
      path: "/purchaseInfoDetail/:purchaseInfoId",
      component: PurchaseInfoDetail,
      meta: {show: true},
      name: "purchaseInfoDetail",
    },
  ]
})

let socket
let connectWebSocket = async function(uid) {
  if (typeof (WebSocket) == "undefined") {
    console.log("您的浏览器不支持WebSocket");
  } else {
    console.log("您的浏览器支持WebSocket");
    // let socketUrl = "ws://192.168.43.253:8888/websocket/" + uid;
    let socketUrl = "ws://120.76.136.205:8888/websocket/" + uid;
    if (socket != null) {
      socket.close();
      socket = null;
    }

    /**
     * 开启一个websocket服务
     */
    socket = new WebSocket(socketUrl);
    await store.commit('SAVEWEBSOCKET', socket)


    /**  以下为websocket的回调事件  * /


     /**
     *打开事件
     */
    socket.onopen = function () {
      console.log("websocket已打开");
    };
    /**
     * 浏览器端收消息，获得从服务端发送过来的文本消息
     * @param msg 服务器端传的数据
     */
    socket.onmessage = async function (msg) {
      let webSocketInstruct = JSON.parse(msg.data)
      console.log("收到数据====", webSocketInstruct)
      let instruct = webSocketInstruct.instruct
      let data = webSocketInstruct.data
      if (instruct === 1) {
        //用户有新未读消息：data存未读消息数
        await store.commit('GETUNREADNUM', data)
      } else if (instruct === 3) {
        //系统发出系统消息：data存发出的消息数据
        if (data.contentType === 0) {
          Notification({
            title: webSocketInstruct.data.title,
            message: webSocketInstruct.data.content,
            duration: 0
          });
        } else {
          Notification({
            title: data.title,
            dangerouslyUseHTMLString: true,
            message: data.htmlContent,
            duration: 0
          });
        }
      }
    };
    /**
     * 关闭事件
     */
    socket.onclose = function () {
      console.log("websocket已关闭");
    };
    /**
     * 发生了错误事件
     */
    socket.onerror = function () {
      console.log("websocket发生了错误");
    }
  }
}
//全局守卫
router.beforeEach( async(to,from,next)=>{
  let token = Vue.$cookies.get('access_token')
  //判断是否登录过
  if (token){
    let userDate = await store.state.user.userDate
    //判断是否需要获取信息
    if (JSON.stringify(userDate) === "{}"){
      // console.log('发送请求，获取信息',userDate)
      let result = await store.dispatch('getUserInfo')
      //判断登录是否过期
      if (result.state === 4010){
        //登录过期，删除cookie中的access_token
        Vue.$cookies.remove('access_token')
        Message({
          message: '登录已过期，请重新登陆',
          type: 'warning',
        });
        next('/login')
      }else if (result.state === 200) {
        //登录未过期，获取用户未读消息数
        await store.dispatch('getUnreadMessageNum')

        //检查vuex中是否存在websocket，不存在则进行连接
        let websocket = store.state.user.websocket
        console.log('websocket',websocket)
        if (JSON.stringify(websocket) === '{}'){
          await connectWebSocket(result.data.uid)
        }else {
          //检查websocket连接是否正常，不正常则重连
          if (websocket.readyState === 3){
            await connectWebSocket(userDate.uid)
          }
        }
      }
    }else {
      //检查websocket连接是否正常，不正常则重连
      if (store.state.user.websocket.readyState === 3){
        await connectWebSocket(userDate.uid)
      }
    }
    if (to.path === '/login' || to.path === '/register'){
      next('/home')
    }else {
      next()
    }
  }else {
    if (to.path === '/userInfo' || to.path === '/releaseGoods'){
      next('/home')
    }
    next()
  }
})


export default router
