import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

//引入小仓库  (模块式存储数据)
import home from './home'
import search from './search'
import user from './user'
import releaseGoods from './releaseGoods'

//对外暴露Store的实例
export default new Vuex.Store({
  modules:{
    user,
    home,
    search,
    releaseGoods,
  }
})




// export default new Vuex.Store({
//   //仓库存储数据的地方
//   state: {},
//   //修改state的唯一手段
//   mutations: {},
//   //处理action，可以写业务逻辑，处理异步
//   actions: {},
//   //用于简化仓库数据，让组件获取仓库的数据更方便
//   getters:{},
//   modules: {}
// })
