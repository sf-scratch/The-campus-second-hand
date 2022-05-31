<template>
  <div class="rank">
<!--    <div class="tab">-->
<!--      <div class="tab-tit clearfix">-->
<!--        <a href="javascript:0;" class="on">-->
<!--          <p class="img">-->
<!--            <i></i>-->
<!--          </p>-->
<!--          <p class="text">热卖排行</p>-->
<!--        </a>-->
<!--        <a href="javascript:0;">-->
<!--          <p class="img">-->
<!--            <i></i>-->
<!--          </p>-->
<!--          <p class="text">特价排行</p>-->
<!--        </a>-->
<!--        <a href="javascript:0;">-->
<!--          <p class="img">-->
<!--            <i></i>-->
<!--          </p>-->
<!--          <p class="text">新品排行</p>-->
<!--        </a>-->
<!--      </div>-->
<!--    </div>-->


<!--    <div class="content">-->
<!--    <ul>-->
<!--      <li v-for="(goodsInfos,index) in goodsInfosList" :key="index">-->
<!--        <div class="img-item" v-for="(goodsInfo) in goodsInfos" :key="goodsInfo.id">-->
<!--          <p class="tab-pic">-->
<!--            <a href="javascript:0;">-->
<!--              <img :src="goodsInfo.image1" alt=""/>-->
<!--            </a>-->
<!--          </p>-->
<!--          <div class="tab-info">-->
<!--            <div class="info-title">-->
<!--              <a href="#">{{goodsInfo.name}}</a>-->
<!--            </div>-->
<!--            <p class="info-price">{{goodsInfo.price}}</p>-->
<!--          </div>-->
<!--        </div>-->
<!--      </li>-->
<!--    </ul>-->
<!--  </div>-->


    <div class="content">
      <el-col :span="6" v-for="(goodsInfo) in goodsInfosList.records" :key="goodsInfo.id">
        <div class="img-item">
          <p class="tab-pic">
            <a href="javascript:0;" @click="toGoodsDetail(goodsInfo.id)">
              <img :src="goodsInfo.coverImage" alt="" :height="230" />
            </a>
          </p>
          <a style="float: right; padding-right: 8px"><i class="el-icon-view"></i>{{ goodsInfo.status }}</a>
          <div class="tab-info">
            <p class="info-price">¥{{goodsInfo.price}}</p>
            <div class="info-title">
              <a href="javascript:0;"  @click="toGoodsDetail(goodsInfo.id)">{{limitLength(goodsInfo.name)}}</a>
              <a style="float: right; padding-right: 8px">{{ goodsInfo.createDate.split(' ')[0] }}</a>
            </div>
          </div>
        </div>
      </el-col>
    </div>


    <div class="block">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="goodsInfosList.current"
          layout="total, prev, pager, next, jumper"
          :page-size="goodsInfosList.size"
          :total="goodsInfosList.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "rank",
  props: {
    goodsInfosList: {}
  },
  data() {
    return {

    };
  },
  methods: {
    async handleCurrentChange(val) {
      //根据categoryName是否有值，进行选择
      let temp
      if (this.$route.query.categoryName || this.$route.params.keyword){
        temp = 'searchGoodsInfoList'
      }else {
        temp = 'getGoodsInfoToShow'
      }
      await this.$store.dispatch(temp,{
        currentPage:val,
        size:this.goodsInfosList.size,
        keyword:this.$route.params.keyword,
        categoryId:this.$route.query.categoryId,
        typeId:this.$route.query.typeId,
      })
    },
    async toGoodsDetail(goodsId){
      let location = {'name':'goodsDetailInfo'}
      location.params = {'goodsId': goodsId}
      try {
        await this.$store.dispatch('selectGoodsByGoodsId',{
          'goodsId':goodsId,
          'view':goodsId,
        })
      }catch (e) {
        console.log(e.message)
      }
      await this.$router.push(location)
    },
    limitLength(text){
      if (text.length > 9){
        return  text.slice(0,9) + '...'
      }
      return text
    },
  },
  mounted() {
  },
}
</script>

<style scoped lang="less">
.rank {
  width: 1000px;
  margin: 0 auto;

  .tab {
    margin: 0 auto;
    overflow: hidden;
    width: 312px;

    .tab-tit {
      text-align: center;

      a {
        display: block;
        padding: 0 18px;
        float: left;
        text-decoration: none;
        font-size: 16px;
        color: #999;

        p {
          margin: 5px 0;
        }

        .img {
          i {
            width: 35px;
            height: 35px;
            display: block;
            background: url(./images/bg0.png);
            margin-left: 10px;
          }
        }

        .text {
          line-height: 28px;
        }
      }

      .on {
        color: #e60012;

        .img {
          i {
            background-position: -35px 0;
          }
        }
      }
    }
  }

  .content {
    overflow: hidden;
    padding: 10px;

    //ul {
    //  li {
    //    overflow: hidden;
        list-style: none;
        line-height: 18px;

        .img-item {
          border: 1px solid #409eff;
          width: 220px;
          float: left;
          overflow: hidden;
          margin: 0 12px 10px;
          background: #fff;
          a{
            font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
            color: #686363;
          }

          .tab-pic {
            width: 220px;
            height: 220px;
            overflow: hidden;
            text-align: center;
            //margin: 5px auto 18px;


            a {
              img {
                width: 220px;
                height: 220px;
              }
            }
          }

          .tab-info {
            background: #fafafa;

            .info-title {
              font-size: 10px;
              height: 27px;
              line-height: 28px;
              overflow: hidden;
              margin: 0 auto;
              padding-left: 10px;

              a {
                color: #333;
                text-decoration: none;
              }
            }

            .info-price {
              font-size: 15px;
              color: #e1251b;
              height: 20px;
              padding-left: 10px;
              display: block;
              line-height: 24px;
            }
          }
        }
      }
    }
</style>
