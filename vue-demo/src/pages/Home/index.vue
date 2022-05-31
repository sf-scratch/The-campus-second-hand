<template>
  <div class="myPage">
    <TypeNav></TypeNav>
<!--    <ListContainer></ListContainer>-->
<!--    <div class="temp"></div>-->
    <div class="myStyle">
      <Rank v-bind:goodsInfosList="goodsInfo"></Rank>
    </div>
  </div>
</template>

<script>
//引入组件
import ListContainer from '@/pages/Home/ListContainer'
import Rank from '@/pages/Rank'
import {mapState} from 'vuex'

export default {
  name: "home",
  components:{
    ListContainer,
    Rank,
  },
  computed:{
    ...mapState({
      goodsInfo:(state)=>{
        return state.releaseGoods.goodsInfo
      }
    }),
  },
  mounted() {
    //获取goodsInfo的数据 (异常未处理)
    this.$store.dispatch('getGoodsInfoToShow',{
      currentPage:this.$store.state.home.defaultCurrentPage,
      size:this.$store.state.home.defaultSize,
    })
  },
}
</script>

<style scoped>
.myPage {
  width: 100%;
  height: 700px;
  /*background-image: url('../Login/images/11.jpg');*/
  /*background-repeat:no-repeat ;*/
  /*background-size:100% 100%;*/
  /*background-attachment: fixed;*/
}
.temp {
  width: 600px;
  height: 50px;
  margin: 0 auto;
}
.myStyle {
  width: 1200px;
  height: 650px;
  /*border: 1px solid rgb(223, 223, 223);*/
  margin: 0 auto;
}
</style>
