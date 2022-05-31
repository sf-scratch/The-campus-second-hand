<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'login',
  data(){
    return {

    }
  },
  computed:{

  },
  created() {

  },
  methods:{

  },
}
</script>

<style lang="less">
.warp-card {
  margin-top: 10px;
  &:first-child {
    margin-top: 0;
  }
  .action-show {
    .ivu-btn {
      margin: 0 3px;
    }
  }
  .action-hide {
    .ivu-btn {
      margin: 0 3px;
      span {
        //display: none;
      }
    }
  }
  .add-form {
    padding-top: 15px;
  }
}
.areaSelect .areaBox .areaArrow.active {
  border-color: #689df6 !important;
}

.areaSelect .areaBox .areaInput:hover, .areaSelect .areaBox .areaInput:focus {
  border-color: #689df6 !important;
}

.areaSelect .areaBox .areaModal ul li.active[data-v-16a01167] {
  background: #fff !important;
  color: #689df6 !important;
}
.main{
  .side-menu-wrapper .ivu-menu-dark .ivu-menu .ivu-menu-submenu-has-parent-submenu{ padding-left: 41px; }
}





.size {
  width: 100%;
  height: 100%;
}
html,
body {
  .size;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
#app {
  .size;
}
</style>
