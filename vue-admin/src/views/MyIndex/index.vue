<template>

  <!---->
  <el-container class="home-container">
    <!--头部区域-->
    <el-header>
      <div>
        <img width="20px" alt="" src="@/assets/logo.png">
        <span>二手工坊后台管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <!--页面主体区域-->
    <el-container>
      <!--侧边栏-->
      <el-aside v-bind:width="isCollapsed ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapsed">
          |||
        </div>
        <!--侧边栏菜单区域-->
        <el-menu
            :collapse="isCollapsed"
            :collapse-transition="false"
            :default-active="activePath"
            :router="true"
            :unique-opened="true"
            active-text-color="#409EFF"
            background-color="#333744"
            text-color="#fff">
          <!-- 一级菜单-->
          <el-submenu v-for="(item) in menuList" :key="item.id" v-bind:index="item.id + ''">
            <!-- 一级菜单的模板区域-->
            <template slot="title">
              <!-- 图标 -->
              <i v-bind:class="iconObj[item.id]"></i>
              <!-- 文本 -->
              <span>{{ item.authName }}</span>
            </template>

            <!-- 二级菜单 -->
            <el-menu-item v-for="(subItem) in item.children" :key="subItem.id" v-bind:index="'/' + subItem.path"
                          @click="saveNavState('/' + subItem.path)">
              <!-- 二级菜单的模板区域-->
              <template slot="title">
                <!-- 图标 -->
                <i v-bind:class="iconObj[subItem.id]"></i>
                <!-- 文本 -->
                <span>{{ subItem.authName }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--右侧内容主体-->
      <el-main ref="main">
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'MyIndex',
  data () {
    return {
      // 左侧菜单数据
      menuList: [],
      iconObj: {
        100: 'el-icon-s-data',
        101: 'el-icon-s-marketing',
        102: 'el-icon-picture-outline-round',
        200: 'el-icon-s-goods',
        201: 'el-icon-menu',
        202: 'el-icon-document',
        203: 'el-icon-search',
        204: 'el-icon-warning',
        300: 'el-icon-user-solid',
        301: 'el-icon-document',
        400: 'el-icon-message-solid',
        401: 'el-icon-edit',
        500: 'el-icon-shopping-cart-full',
        501: 'el-icon-document',
      },
      // 是否动态折叠
      isCollapsed: false,
      // 被激活的链接
      activePath: '',
    }
  },
  created () {
    // this.getMenuList()
    this.menuList = [
        {
          id:100,
          authName:'数据统计',
          path:'',
          children:[
            {
              id:101,
              authName:'图表',
              path:'home',
            },
            {
              id:102,
              authName:'列表',
              path:'about',
            },
          ]
        },
        {
          id:200,
          authName:'商品管理',
          path:'',
          children:[
            {
              id:201,
              authName:'商品分类',
              path:'category',
            },
            {
              id:202,
              authName:'商品列表',
              path:'goodsList',
            },
            {
              id:203,
              authName:'商品审核',
              path:'goodsVerify',
            },
            {
              id:204,
              authName:'商品警告',
              path:'goodsWarn',
            },
          ]
        },
      {
        id:500,
        authName:'求购管理',
        path:'',
        children:[
          {
            id:501,
            authName:'求购列表',
            path:'purchaseInfoList',
          },
        ]
      },
      {
        id:300,
        authName:'用户管理',
        path:'',
        children:[
          {
            id:301,
            authName:'用户列表',
            path:'userList',
          },
        ]
      },
      {
        id:400,
        authName:'公告管理',
        path:'',
        children:[
          {
            id:401,
            authName:'发布消息',
            path:'releaseMessage',
          },
        ]
      },
    ]
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  methods: {
    async logout () {
      //服务器发起退出登录的请求
      await this.$store.dispatch('adminLogout')
      //删除cookie中存储的access_token_admin
      await this.$cookies.remove('access_token_admin')
      await this.$router.push("/login");
    },
    /**
     * 获取所有的菜单
     */
    // async getMenuList () {
    //   const { data: res } = await this.$http('menus')
    //   console.log(res)
    //   if (res.meta.status !== 200) {
    //     return this.$message.error(res.meta.msg)
    //   } else {
    //     this.menuList = res.data
    //   }
    // },
    /**
     * 折叠侧边栏
     */
    toggleCollapsed () {
      this.isCollapsed = !this.isCollapsed
    },
    /**
     * 保存链接的激活状态
     */
    saveNavState (activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      //
      this.activePath = activePath
    }

  }

}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: #373D41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #333744;
  display: block;
  position: absolute;
  left: 0;
  top: 60px;
  bottom: 0;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #EAEDF1;
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #ffffff;
  text-align: center;
  letter-spacing: 0.2em;
  // cursor CSS 属性设置鼠标光标的类型（如果有），以在鼠标指针悬停在元素上时显示
  cursor: pointer;
}
</style>
