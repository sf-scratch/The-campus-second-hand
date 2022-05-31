<template>
  <div style="width: 750px; margin: auto; padding-top: 30px">
    <h1 style="text-align: center">{{purchaseInfo.title}}</h1>
    <div v-html="JSON.parse(purchaseInfo.htmlContent)"></div>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "purchaseInfoDetail",
  data () {
    return {
      purchaseInfoId:0,
      purchaseInfo:{},
    }
  },
  computed: {
    ...mapState({
      purchaseInfoList:(state)=>{
        return state.home.purchaseInfoList
      },
    }),
  },
  created() {
    let id = this.purchaseInfoId = this.$route.params.purchaseInfoId
    let list = this.purchaseInfoList
    if (JSON.stringify(list) === '[]'){
      console.log('1')
    }else {
      list.forEach(info => {
        if (info.id === id){
          this.purchaseInfo = info
        }
      })
    }
  },
  methods:{
    getPurchaseInfoById(){

    },
  },
}
</script>

<style scoped>

</style>
