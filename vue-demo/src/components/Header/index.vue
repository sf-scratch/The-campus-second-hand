<template>
  <div class="myStyle">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item  index="1" @click="toHome">二手工坊</el-menu-item>

      <el-menu-item  index="8" v-if="JSON.stringify(this.userDate)!=='{}'" @click="toPurchaseInfoList">求购信息</el-menu-item>
      <el-tooltip class="item" effect="light" content="登录即可查看求购信息" placement="bottom" v-else transition="">
        <el-menu-item  index="8">求购信息</el-menu-item>
      </el-tooltip>

      <el-menu-item  index="2" v-if="JSON.stringify(this.userDate)!=='{}'" @click="toSystemAnnouncement">系统公告</el-menu-item>
      <el-tooltip class="item" effect="light" content="登录即可查看公告" placement="bottom" v-else>
        <el-menu-item  index="2">系统公告</el-menu-item>
      </el-tooltip>

      <el-menu-item  index="3" v-if="isShow" @click="toLogin">登录</el-menu-item>
      <el-menu-item  index="3" v-else @click="toUserInfo">
        <el-avatar :size="45" :src="avatar"></el-avatar>
      </el-menu-item>

      <el-menu-item  index="4" v-if="isShow" @click="toRegister">免费注册</el-menu-item>
      <el-menu-item  index="4" v-else @click="logout">
        <p>退出</p>
      </el-menu-item>

      <el-menu-item  index="5">
        <el-input style="width:400px;" size="small" placeholder="输入搜索内容" v-model="keyword" @keydown.enter.native="goSearch" @clear="clearSearchInput" clearable></el-input>
      </el-menu-item>
      <el-menu-item  index="5">
        <el-button type="primary" icon="el-icon-search" size="mini" @click.prevent="goSearch">搜索</el-button>
      </el-menu-item>

<!--      <el-menu-item  index="6" v-if="JSON.stringify(this.userDate)!=='{}'">-->
<!--        <el-dropdown>-->
<!--          <span class="el-dropdown-link">消息中心</span>-->
<!--          <el-badge :value="unreadMessageNumList[0] + unreadMessageNumList[1]" :hidden="unreadMessageNumList[0] + unreadMessageNumList[1] === 0" :max="99" class="item"></el-badge>-->
<!--          <el-dropdown-menu slot="dropdown">-->
<!--            <el-dropdown-item @click.native="toReplyComment()">回复我的<el-badge :value="unreadMessageNumList[0]" :hidden="unreadMessageNumList[0] === 0" :max="99" class="item"></el-badge></el-dropdown-item>-->
<!--            <el-dropdown-item @click.native="toGoodsComment()">商品评论<el-badge :value="unreadMessageNumList[1]" :hidden="unreadMessageNumList[1] === 0" :max="99" class="item"></el-badge></el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
<!--        </el-dropdown>-->
<!--      </el-menu-item>-->
      <el-submenu index="6" v-if="JSON.stringify(this.userDate)!=='{}'">
        <template slot="title">消息中心<el-badge :value="unreadMessageNumList[0] + unreadMessageNumList[1]" :hidden="unreadMessageNumList[0] + unreadMessageNumList[1] === 0" :max="99" class="item"></el-badge></template>
        <el-menu-item index="2-1" @click.native="toReplyComment()">回复我的<el-badge :value="unreadMessageNumList[0]" :hidden="unreadMessageNumList[0] === 0" :max="99" class="item"></el-badge></el-menu-item>
        <el-menu-item index="2-2" @click.native="toGoodsComment()">商品评论<el-badge :value="unreadMessageNumList[1]" :hidden="unreadMessageNumList[1] === 0" :max="99" class="item"></el-badge></el-menu-item>
      </el-submenu>
      <el-tooltip class="item" effect="light" content="登录即可查看消息" placement="bottom" v-else transition="">
        <el-menu-item  index="6">消息中心</el-menu-item>
      </el-tooltip>

      <el-menu-item  index="7" v-if="JSON.stringify(this.userDate)!=='{}'" @click="toShowCollectList">收藏</el-menu-item>
      <el-tooltip class="item" effect="light" content="登录即可查看收藏" placement="bottom" v-else transition="">
        <el-menu-item  index="7">收藏</el-menu-item>
      </el-tooltip>

      <el-menu-item  index="9" v-if="JSON.stringify(this.userDate)!=='{}'" @click="toMyGoods">我的商品</el-menu-item>
      <el-tooltip class="item" effect="light" content="登录即可查看订单" placement="bottom" v-else transition="">
        <el-menu-item  index="9">我的商品</el-menu-item>
      </el-tooltip>

      <el-menu-item  index="10">
        <el-dropdown trigger="click" v-if="JSON.stringify(this.userDate)!=='{}'">
          <el-button class="el-dropdown-link" type="primary" icon="el-icon-edit">发布</el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="toReleaseGoods">闲置物品</el-dropdown-item>
            <el-dropdown-item @click.native="toReleaseRequirementInfo">求购信息</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-tooltip class="item" effect="light" content="登录即可发布" placement="bottom" v-else transition="">
          <el-button type="primary" icon="el-icon-edit" @click="toReleaseGoods">发布</el-button>
        </el-tooltip>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name:"",
  data(){
    return {
      activeIndex: '1',
      keyword:'',
      username:'',
      searchSomething:'',
      avatar:'',
    }
  },
  methods:{
    toHome(){
      this.keyword = ''
      this.$router.push({
        name:'home',
        // 跳转到home默认 参数值
        // params:{currentPage:1,size:6},
      });
    },
    toLogin(){
      this.$router.push("/login");
    },
    toRegister(){
      this.$router.push("/register");
    },
    toUserInfo(){
      this.$router.push("/userInfo");
    },
    toReleaseGoods(){
      if (JSON.stringify(this.userDate) === '{}'){
        this.toLogin()
        return
      }
      this.$router.push("/releaseGoods");
    },
    toReleaseRequirementInfo(){
      this.$router.push("/releaseRequirementInfo");
    },
    //转到收藏列表
    toShowCollectList(){
      this.$router.push("/collectedList")
    },
    //转到我的商品
    toMyGoods(){
      this.$router.push("/myGoods")
    },
    async logout(){
      await this.$store.dispatch('userLogout')
      this.$cookies.remove('access_token')
      await this.$router.push("/home");
    },
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
    },
    //搜索按钮回调函数，向search路由跳转
    async goSearch(){
      if (!this.keyword){
        return
      }
      //路由传参
      //1.字符串形式
      // this.$router.push('/search/' + this.keyword)
      //2.模板字符串
      // this.$router.push(`/search/${this.keyword}`)
      //3.对象写法（常用）
      let location = {name: "search",
        params:{
        'keyword':this.keyword,
        'currentPage':this.$store.state.home.defaultCurrentPage,
        'size':this.$store.state.home.defaultSize,
      }}
      if (this.$route.query){
        location.query = this.$route.query
      }
      //先获取信息再跳转，可以避免search的钩子函数的重复去获取信息
      await this.$store.dispatch('searchGoodsInfoList',{
        currentPage:this.$store.state.home.defaultCurrentPage,
        size:this.$store.state.home.defaultSize,
        keyword:this.keyword,
        categoryId:this.$route.query.categoryId,
        typeId:this.$route.query.typeId,
      })
      await this.$router.push(location)
    },
    goMessageCenter(activeName){
      this.$router.push({
        path: "/messageCenter", query:{activeName:activeName}
      })
    },
    toReplyComment(){
      this.$router.push("/replyComment")
    },
    toGoodsComment(){
      this.$router.push("/goodsComment")
    },
    toSystemAnnouncement(){
      this.$router.push("/systemAnnouncement")
    },
    toPurchaseInfoList(){
      this.$router.push("/purchaseInfoList")
    },
    async clearSearchInput(){
      let location = {'name':'search'}
      location.query = this.$route.query
      await this.$store.dispatch('searchGoodsInfoList',{
        currentPage:this.$store.state.home.defaultCurrentPage,
        size:this.$store.state.home.defaultSize,
        categoryId:location.query.categoryId,
        typeId:location.query.typeId,
      })
      await this.$router.push(location)
    },
  },
  computed:{
    ...mapState({
      userDate:(state)=>{
        return state.user.userDate
      },
      unreadMessageNumList:(state)=>{
        return state.user.unreadMessageNumList
      },
    }),
    isShow(){
      if (this.userDate){
        this.username = this.userDate.username
        if (!this.userDate.avatar){
          this.avatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        }else {
          this.avatar = this.userDate.avatar
        }
      }
      return JSON.stringify(this.userDate) ==='{}'
    },
  },
  mounted() {

  },
}
</script>

<style scoped lang="less">
.myStyle {
  width: 1480px;
  height: 60px;
  //border: 1px solid rgb(223, 223, 223);
  margin: 0 auto;
}

.header {
&>.top {
   background-color: #eaeaea;
   height: 30px;
   line-height: 30px;

.container {
  width: 1200px;
  margin: 0 auto;
  overflow: hidden;

.loginList {
  float: left;

p {
  float: left;
  margin-right: 10px;

.register {
  border-left: 1px solid #b3aeae;
  padding: 0 5px;
  margin-left: 5px;
}
}
}

.typeList {
  float: right;

a {
  padding: 0 10px;

&+a {
   border-left: 1px solid #b3aeae;
 }
}

}

}
}

&>.bottom {
   width: 1200px;
   margin: 0 auto;
   overflow: hidden;

.logoArea {
  float: left;

.logo {
img {
  margin: 25px 45px;
}
}
}

.searchArea {
  float: right;
  margin-top: 35px;

.searchForm {
  overflow: hidden;

input {
  box-sizing: border-box;
  width: 490px;
  height: 32px;
  padding: 0 4px;
  border: 2px solid #ea4a36;
  float: left;

&:focus {
   outline: none;
 }
}

button {
  height: 32px;
  width: 68px;
  background-color: #ea4a36;
  border: none;
  color: #fff;
  float: left;
  cursor: pointer;

&:focus {
   outline: none;
 }
}
}
}
}
}
</style>
