<template>
  <div class="cart-complete-wrap">
    <div class="cart-complete">
      <h3 v-show="isShow"><i class="sui-icon icon-pc-right"></i>已收藏商品</h3>
      <h3 v-show="!isShow"><i class="sui-icon icon-pc-right"></i>暂无收藏商品</h3>
      <div class="goods" v-for="(collectedInfo,index) in collectedList" :key="index">
        <div class="left-good">
          <div class="left-pic">
            <img :src="collectedInfo.image1" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{collectedInfo.name}}</p>
            <p class="attr">
              价格：{{collectedInfo.price}}
              数量：{{collectedInfo.num}}
              状态：{{currentStatus(collectedInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(collectedInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="toGoodsDetail(collectedInfo.id)">查看商品详情</a>
          <a href="javascript:0;" @click="collectGoods(collectedInfo.id,index)">取消收藏</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "collectedList",
  data(){
    return{
      collectedList:[],
    }
  },
  computed:{
    //是否有收藏
    isShow(){
      return JSON.stringify(this.collectedList) !== '[]';
    },
  },
  created() {
    this.getCollectedList()
  },
  methods:{
    async getCollectedList(){
      //只在登录状态才能获取收藏列表
      if (JSON.stringify(this.$store.state.user.userDate) !== '{}'){
        this.collectedList = await this.$store.dispatch('getCollectList')
      }
    },
    //限制商品描述content的字数显示
    checkContentLength(content){
      if (content.length > 25){
        return  content.slice(0,25) + '...'
      }
      return content
    },
    //商品状态
    currentStatus(status){
      if (status === -1){
        return '已售出'
      }else {
        return '暂未售出'
      }
    },
    //跳转到商品详细信息
    async toGoodsDetail(goodsId){
      let location = {'name':'goodsDetailInfo'}
      location.params = {'goodsId': goodsId}
      try {
        await this.$store.dispatch('selectGoodsByGoodsId',{
          goodsId:goodsId,
        })
      }catch (e) {
        console.log(e.message)
      }
      await this.$router.push(location)
    },
    //取消收藏
    async collectGoods(goodsId,index){
      await this.$store.dispatch('cancelCollection',{
        'goodsId':goodsId
      })
      this.collectedList.splice(index,1)
    },
  },
}
</script>

<style scoped lang="less">
.cart-complete-wrap {
  background-color: #f4f4f4;
  width: 100%;
  margin: 0 auto;

  .cart-complete {
    width: 1200px;
    margin: 0 auto;

    h3 {
      font-weight: 400;
      font-size: 16px;
      color: #6aaf04;
      padding-top: 15px;
      padding-bottom: 15px;
      margin: 0;

      .icon-pc-right {
        background-color: #fff;
        border: 2px solid #6aaf04;
        padding: 3px;
        margin-right: 8px;
        border-radius: 15px;
      }
    }

    .goods {
      overflow: hidden;
      padding: 10px 0;

      .left-good {
        float: left;

        .left-pic {
          border: 1px solid #dfdfdf;
          width: 60px;
          float: left;
          img {
            width: 60px;
            height: 60px;
          }
        }

        .right-info {
          color: #444;
          float: left;
          margin-left: 10px;
          p{
            font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
            color: #333;
          }

          .title {
            width: 100%;
            line-height: 28px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-size: 14px;
          }

          .attr {
            color: #aaa;
          }
        }
      }

      .right-gocart {
        float: right;

        a {
          padding: 7px 36px;
          font-size: 15px;
          line-height: 22px;
          color: #333;
          background-color: #eee;
          text-decoration: none;
          box-sizing: border-box;
          border: 1px solid #e1e1e1;
        }

        a:hover {
          background-color: #f7f7f7;
          border: 1px solid #eaeaea;
        }

        a:active {
          background-color: #e1e1e1;
          border: 1px solid #d5d5d5;
        }

        .btn-danger {
          background-color: #e1251b;
          color: #fff;
        }

        .btn-danger:hover {
          background-color: #e1251b;
        }
      }

    }
  }
}
</style>
