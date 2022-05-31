import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

//引入小仓库  (模块式存储数据)
import user from './user'
import home from './home'

//对外暴露Store的实例
export default new Vuex.Store({
  modules:{
    user,home
  }
})
