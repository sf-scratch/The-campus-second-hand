<template>
  <div class="detail">
    <!-- 商品分类导航 -->
    <TypeNav />

    <!-- 主要内容区域 -->
    <section class="con">
      <!-- 导航路径区域 -->
      <div class="conPoin">
        <span>{{goodsDetailInfo.categoryName}}</span>
        <span>{{goodsDetailInfo.typeName}}</span>
      </div>
      <!-- 主要内容区域 -->
      <div class="mainCon">
        <!-- 左侧放大镜区域 -->
        <div class="previewWrap">
          <!--放大镜效果+小图列表 -->
          <ImageList v-bind:images="[goodsDetailInfo.image1,goodsDetailInfo.image2,goodsDetailInfo.image3]"/>
        </div>
        <!-- 右侧选择区域布局 -->
        <div class="InfoWrap">
          <div class="goodsDetail">
            <h3 class="InfoName">{{goodsDetailInfo.name}}</h3>
            <p class="news">联系方式点击标签查看</p>
            <div class="priceArea">
              <div class="priceArea1">
                <div class="title">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</div>
                <div class="price">
                  <i>¥</i>
                  <em>{{goodsDetailInfo.price}}</em>
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原价¥{{goodsDetailInfo.originalPrice}}</span>
                </div>
                <div class="remark">
                  <i>浏览数</i>
                  <em style="padding-right: 5px">{{goodsDetailInfo.status}}</em>
                </div>
              </div>
              <div class="priceArea2">
                <div class="title">
                  <i>联系方式</i>
                </div>
                <div class="fixWidth">
                  <el-row>
                    <el-col :span="6">
                      <a href="javascript:0;" class="red-bg" @click="isShowQQ=!isShowQQ">QQ</a>
                      <em class="t-gray" v-if="isShowQQ">{{numberQQ}}</em>
                    </el-col>
                    <el-col :span="6">
                      <a href="javascript:0;" class="red-bg" @click="isShowWX=!isShowWX">微信</a>
                      <em class="t-gray" v-if="isShowWX">{{numberWX}}</em>
                    </el-col>
                    <el-col :span="12"></el-col>
                  </el-row>
                </div>
              </div>
            </div>
            <div class="support">
              <div class="supportArea">
                <div class="title">持有者</div>
                <div class="fixWidth">{{goodsDetailInfo.ownerName}}</div>
              </div>
              <div class="supportArea">
                <div class="title">发布日期</div>
                <div class="fixWidth">{{goodsDetailInfo.createDate}}</div>
              </div>
              <div class="supportArea">
                <div class="title">商品信息</div>
                <div class="fixWidth">{{goodsDetailInfo.content}}</div>
              </div>
              <el-button :icon="isStar" circle @click="collectGoods(goodsDetailInfo.id)">{{isCollected}}</el-button>
            </div>
          </div>
        </div>
      </div>
      <comment @doSend="doSend" @doChidSend="doChildSend" :avatar="userDate.avatar" :commentList="commentList" :authorId="goodsDetailInfo.ownerId" label="持有者" :commentNum="commentNum"></comment>
    </section>
  </div>
</template>

<script>
  import {reqSendComment, reqGetGoodsComment, reqReplyComment, reqGetUnreadMessageNum} from '@/api'
  import ImageList from './ImageList/ImageList'
  import {mapState} from "vuex";
  // import comment from 'hbl-comment'
  import comment from './Comment/Comment.vue'
  export default {
    name: 'detail',
    components: {
      ImageList,comment
    },
    data(){
      return{
        isStar:'el-icon-star-off',
        isShowQQ:false,
        isShowWX:false,
        commentNum:0,
        commentList:[],
      }
    },
    created () {
      this.getCommentList()
    },
    computed:{
      // goodsDetailInfo:this.$store.state.releaseGoods.selectGoodsInfo
      // 使用 computed 能解决数据更新不及时
      ...mapState({
        goodsDetailInfo:(state)=>{
          return state.releaseGoods.selectGoodsInfo
        },
        userDate:(state)=>{
          return state.user.userDate
        }
      }),
      isCollected(){
        if (this.goodsDetailInfo.collect){
          this.isStar = 'el-icon-star-on'
          return '已收藏'
        }else {
          this.isStar = 'el-icon-star-off'
          return '未收藏'
        }
      },
      numberQQ(){
        if (this.goodsDetailInfo.contactQq){
          return this.goodsDetailInfo.contactQq
        }else {
          return '无'
        }
      },
      numberWX(){
        if (this.goodsDetailInfo.contactWx){
          return this.goodsDetailInfo.contactWx
        }else {
          return '无'
        }
      },
    },
    methods:{
      async collectGoods(goodsId){
        await this.$store.dispatch('changeCollectionStatus',{
          'goodsId':goodsId
        })
        if (this.isStar === 'el-icon-star-off'){
          this.isStar = 'el-icon-star-on'
          this.goodsDetailInfo.collect = true
        }else {
          this.isStar = 'el-icon-star-off'
          this.goodsDetailInfo.collect = false
        }
      },
      async getCommentList(){
        try {
          // let result1 = reqGetUnreadMessageNum()
          // console.log(result1)
          let result = await reqGetGoodsComment({'goodsId':this.$route.params.goodsId})
          let temp = result.data
          let list = []
          for (let i = 0; i < temp.length; i++){
            if (temp[i].parentId === '0'){
              temp[i].childrenList = []
              list.push(temp[i])
            }
          }
          for (let i = 0; i < temp.length; i++){
            let index = list.findIndex(item => item.id === temp[i].parentId);
            if (index >= 0){
              list[index].childrenList.push(temp[i])
            }
          }
          this.commentList = list
          this.commentNum = result.data.length
          this.$message({
            type: 'success',
            message: result.message
          });
        }catch (e) {
          console.log(e.message)
        }
      },
      async doSend(content){
        try {
          let visited = this.goodsDetailInfo.ownerId === this.userDate.uid ? '1' : '0'
          let result = await reqSendComment({
            'content':content,
            'goodsId':this.goodsDetailInfo.id,
            'visited':visited,
            'goodsCoverImage':this.goodsDetailInfo.coverImage,
            'goodsName':this.goodsDetailInfo.name,
            'ownerId':this.goodsDetailInfo.ownerId,
          })
          if (result.state === 200){
            this.$message({
              type: 'success',
              message: result.message
            });
            await this.getCommentList()
          }else if (result.state === 4010){
            this.$message({
              type: 'warning',
              message: result.message
            });
          }else {
            this.$message({
              type: 'error',
              message: result.message
            });
          }
        }catch (e) {
          console.log(e.message)
        }
      },
      async doChildSend(content,targetUser,parentId,goodsId,parentContent,targetContent,targetId){
        try {
          let visited = targetUser.id === this.userDate.uid ? '1' : '0'
          let result = await reqReplyComment(
              {content,targetUser,parentId,goodsId,visited,parentContent,targetContent,targetId})
          if (result.state === 200){
            this.$message({
              type: 'success',
              message: result.message
            });
            await this.getCommentList()
          }else if (result.state === 4010){
            this.$message({
              type: 'warning',
              message: result.message
            });
          }else {
            this.$message({
              type: 'error',
              message: result.message
            });
          }
        }catch (e) {
          console.log(e.message)
        }
      },
    },
    mounted() {
      if (JSON.stringify(this.$store.state.releaseGoods.selectGoodsInfo) === '{}'){
        try {
          this.$store.dispatch('selectGoodsByGoodsId',{
            goodsId:this.$route.params.goodsId,
          })
        }catch (e) {
          console.log(e.message)
        }
      }
    },
  }
</script>

<style lang="less" scoped>
p, span{
    font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
    color: #333;
}
.detail {
    width: 100%;
    .con {
      width: 1200px;
      margin: 15px auto 0;

      .conPoin {
        padding: 9px 15px 9px 0;

        &>span+span:before {
          content: "/\00a0";
          padding: 0 5px;
          color: #ccc;
        }
      }

      .mainCon {
        overflow: hidden;
        margin: 5px 0 15px;

        .previewWrap {
          float: left;
          width: 400px;
          position: relative;
        }

        .InfoWrap {
          width: 700px;
          float: right;

          .InfoName {
            font-size: 14px;
            line-height: 21px;
            margin-top: 15px;
          }

          .news {
            color: #e12228;
            margin-top: 15px;
          }

          .priceArea {
            background: #bbf0f5;
            padding: 7px;
            margin: 13px 0;

            .priceArea1 {
              overflow: hidden;
              line-height: 28px;
              margin-top: auto;

              .title {
                float: left;
                margin-right: 15px;
                margin-top: 10px;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
              }

              .price {
                float: left;
                color: #c81623;

                i {
                  font-size: 16px;
                }

                em {
                  font-size: 24px;
                  font-weight: 700;
                }

                span {
                  font-size: 12px;
                }
              }

              .remark {
                float: right;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
                color: #333;
              }
            }

            .priceArea2 {
              overflow: hidden;
              line-height: 28px;
              margin-top: 10px;

              .title {
                margin-right: 15px;
                float: left;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
                color: #333;
              }

              .fixWidth {
                width: 520px;
                float: left;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
                color: #333;

                .red-bg {
                  background: #c81623;
                  color: #fff;
                  padding: 3px;
                }

                .t-gray {
                  color: #999;
                }
              }
            }


          }

          .support {
            border-bottom: 1px solid #ededed;
            padding-bottom: 5px;

            .supportArea {
              overflow: hidden;
              line-height: 28px;
              margin-top: 10px;

              .title {
                margin-right: 15px;
                float: left;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
              }

              .fixWidth {
                width: 520px;
                float: left;
                color: #999;
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
              }
            }
          }

          .choose {
            .chooseArea {
              overflow: hidden;
              line-height: 28px;
              margin-top: 10px;

              dl {
                overflow: hidden;
                margin: 13px 0;

                dt {
                  margin-right: 15px;
                  float: left;
                }

                dd {
                  float: left;
                  margin-right: 5px;
                  color: #666;
                  line-height: 24px;
                  padding: 2px 14px;
                  border-top: 1px solid #eee;
                  border-right: 1px solid #bbb;
                  border-bottom: 1px solid #bbb;
                  border-left: 1px solid #eee;

                  &.active {
                    color: green;
                    border: 1px solid green;
                  }
                }
              }
            }

            .cartWrap {
              .controls {
                width: 48px;
                position: relative;
                float: left;
                margin-right: 15px;

                .itxt {
                  width: 38px;
                  height: 37px;
                  border: 1px solid #ddd;
                  color: #555;
                  float: left;
                  border-right: 0;
                  text-align: center;
                }

                .plus,
                .mins {
                  width: 15px;
                  text-align: center;
                  height: 17px;
                  line-height: 17px;
                  background: #f1f1f1;
                  color: #666;
                  position: absolute;
                  right: -8px;
                  border: 1px solid #ccc;
                }

                .mins {
                  right: -8px;
                  top: 19px;
                  border-top: 0;
                }

                .plus {
                  right: -8px;
                }
              }

              .add {
                float: left;

                a {
                  background-color: #e1251b;
                  padding: 0 25px;
                  font-size: 16px;
                  color: #fff;
                  height: 36px;
                  line-height: 36px;
                  display: block;
                }
              }
            }
          }
        }
      }
    }

    .product-detail {
      width: 1200px;
      margin: 30px auto 0;
      overflow: hidden;

      .aside {
        width: 210px;
        float: left;
        border: 1px solid #ccc;

        .tabWraped {
          height: 40px;

          h4 {
            border-top: 3px solid #fff;
            float: left;
            line-height: 37px;
            width: 105px;
            text-align: center;
            border-bottom: 1px solid #ccc;

            &.active {
              border-top: 3px solid #e1251b;
              border-bottom: 0;
              font-weight: normal;
            }
          }
        }

        .tabContent {
          padding: 10px;

          .tab-pane {
            display: none;

            &.active {
              display: block;
            }

            &:nth-child(1) {
              .partList {
                overflow: hidden;

                li {
                  width: 50%;
                  float: left;
                  border-bottom: 1px dashed #ededed;
                  line-height: 28px;
                }
              }

              .goodsList {
                &>li {
                  margin: 5px 0 15px;
                  border-bottom: 1px solid #ededed;
                  padding-bottom: 5px;

                  .list-wrap {
                    .p-img {
                      text-align: center;

                      img {
                        width: 152px;
                      }
                    }

                    .price {
                      font-size: 16px;
                      color: #c81623;
                    }

                    .operate {
                      text-align: center;
                      margin: 5px 0;

                      a {
                        background-color: transparent;
                        border: 1px solid #8c8c8c;
                        color: #8c8c8c;
                        display: inline-block;
                        padding: 2px 14px;
                        line-height: 18px;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      .detail {
        width: 980px;
        float: right;

        .fitting {
          border: 1px solid #ddd;
          margin-bottom: 15px;

          .kt {
            border-bottom: 1px solid #ddd;
            background: #f1f1f1;
            color: #333;
            padding: 5px 0 5px 15px;
          }

          .good-suits {
            height: 170px;
            padding-top: 10px;

            .master {
              width: 127px;
              height: 165px;
              text-align: center;
              position: relative;
              float: left;

              img {
                width: 87px;
              }

              p {
                color: #c81623;
                font-size: 16px;
                font-weight: 700;
              }

              i {
                position: absolute;
                top: 48px;
                right: -25px;
                font-size: 16px;
              }
            }

            .suits {
              width: 668px;
              height: 165px;
              float: left;

              .suitsItem {
                float: left;
                width: 127px;
                padding: 0 20px;
                text-align: center;

                img {
                  width: 120px;
                  height: 130px;
                }

                p {
                  font-size: 12px;
                }

                label {
                  display: block;
                  position: relative;

                  input {
                    vertical-align: middle;
                  }

                  span {
                    vertical-align: middle;
                  }
                }
              }
            }

            .result {
              border-left: 1px solid #ddd;
              width: 153px;
              height: 165px;
              padding-left: 20px;
              float: left;

              .num {
                font-size: 14px;
                margin-bottom: 10px;
                margin-top: 10px;
              }

              .price-tit {
                font-weight: bold;
                margin-bottom: 10px;
              }

              .price {
                color: #B1191A;
                font-size: 16px;
                margin-bottom: 10px;
              }

              .addshopcar {
                background-color: #e1251b;
                border: 1px solid #e1251b;
                padding: 10px 25px;
                font-size: 16px;
                color: #fff;
                display: inline-block;
                box-sizing: border-box;
              }
            }
          }
        }

        .intro {
          .tab-wraped {
            background: #ededed;
            // border: 1px solid #ddd;
            overflow: hidden;

            li {
              float: left;

              &+li>a {
                border-left: 1px solid #ddd;
              }

              &.active {
                a {
                  // border: 0;
                  background: #e1251b;
                  color: #fff;
                }
              }

              a {
                display: block;
                height: 40px;
                line-height: 40px;
                padding: 0 11px;
                text-align: center;
                color: #666;
                background: #fcfcfc;
                border-top: 1px solid #ddd;
                border-bottom: 1px solid #ddd;
              }
            }
          }

          .tab-content {
            .tab-pane {
              display: none;

              &.active {
                display: block;
              }

              &:nth-child(1) {
                .goods-intro {
                  padding-left: 10px;

                  li {
                    margin: 10px 0;
                  }
                }

                .intro-detail {
                  img {
                    width: 100%;
                  }
                }
              }
            }

          }
        }
      }
    }
  }
</style>
