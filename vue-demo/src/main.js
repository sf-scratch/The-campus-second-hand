import Vue from 'vue'
import App from './App.vue'
//三级联动组件，全局组件
import TypeNav from '@/components/TypeNav'
Vue.component(TypeNav.name,TypeNav)

//引入路由
import router from './router'
import axios from 'axios'
// Vue.use(axios)
//引入仓库
import store from "./store";

import ElementUI from 'element-ui';
Vue.use(ElementUI);
import 'element-ui/lib/theme-chalk/index.css';

import VueCookie from 'vue-cookies'
Vue.use(VueCookie)

import VueCropper from 'vue-cropper'
Vue.use(VueCropper)

import  mavonEditor  from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

import SlideVerify from 'vue-monoplasty-slide-verify';
Vue.use(SlideVerify);

Vue.config.productionTip = false



new Vue({
  render: h => h(App),
  //组件上都会拥有$route,$router属性
  router,
  //组件实例身上会拥有$store属性
  store
}).$mount('#app')
