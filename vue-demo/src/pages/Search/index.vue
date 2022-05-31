<template>
  <div @mouseleave="leaveShow" class="myPage">
    <TypeNav ref="typeNav"></TypeNav>
    <div class="temp"></div>
    <div class="myStyle">
      <Rank v-bind:goodsInfosList="goodsInfo"></Rank>
    </div>
  </div>
</template>

<script>
import Rank from '@/pages/Rank'
import {mapState} from "vuex";
export default {
  name: "search",
  components:{
    Rank,
  },
  computed:{
    ...mapState({
      goodsInfo:(state)=>{
        return state.search.goodsInfo
      }
    }),
  },
  methods:{
    leaveShow(){
      this.$refs.typeNav.leaveShow()
    },
  },
  mounted() {
    //这里{"current":1}的原因是用了element的分页功能
    if (JSON.stringify(this.$store.state.search.goodsInfo) === '{"current":1}'){
      //通过路由传参接受的数据 查询goodsInfo的数据 (异常未处理)
      this.$store.dispatch('searchGoodsInfoList',{
        currentPage:this.$store.state.home.defaultCurrentPage,
        size:this.$store.state.home.defaultSize,
        keyword:this.$route.params.keyword,
        categoryId:this.$route.query.categoryId,
        typeId:this.$route.query.typeId,
      })
    }
  },
}
</script>

<style scoped>
.myPage {
  width: 100%;
  height: 1000px;
}
</style>
