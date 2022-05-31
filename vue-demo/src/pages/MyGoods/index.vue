<template>
  <div class="cart-complete-wrap">
    <div class="cart-complete">
      <h3><i class="sui-icon icon-pc-right"></i>待审核的商品</h3>
      <div class="goods" v-for="(goodsInfo,index) in underAuditList" :key="goodsInfo.id">
        <div class="left-good">
          <div class="left-pic">
            <img :src="goodsInfo.coverImage" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{goodsInfo.name}}</p>
            <p class="attr">
              价格：{{goodsInfo.price}}
              数量：{{goodsInfo.num}}
              状态：{{currentStatus(goodsInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(goodsInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="cancelReleaseGoods(underAuditList,goodsInfo.id,goodsInfo.name,index,goodsInfo)">取消发布</a>
        </div>
      </div>
      <h3><i class="sui-icon icon-pc-right"></i>未过审的商品</h3>
      <div class="goods" v-for="(goodsInfo,index) in notPassAuditList" :key="goodsInfo.id">
        <div class="left-good">
          <div class="left-pic">
            <img :src="goodsInfo.coverImage" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{goodsInfo.name}}</p>
            <p class="attr">
              价格：{{goodsInfo.price}}
              数量：{{goodsInfo.num}}
              状态：{{currentStatus(goodsInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(goodsInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="showReason(goodsInfo.warnMessage)">未过审原因</a>
          <a href="javascript:0;" @click="restartReleaseGoods(goodsInfo.id)">重新编辑</a>
        </div>
      </div>
      <el-dialog
          title="原因"
          :visible.sync="dialogVisible"
          width="30%">
        <div v-for="(reason,index) in reasonList" :key="index">
          <span>{{index+1 + '.' + reason}}</span>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <h3><i class="sui-icon icon-pc-right"></i>已发布的商品</h3>
      <div class="goods" v-for="(goodsInfo,index) in passAuditList" :key="goodsInfo.id">
        <div class="left-good">
          <div class="left-pic">
            <img :src="goodsInfo.coverImage" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{goodsInfo.name}}</p>
            <p class="attr">
              价格：{{goodsInfo.price}}
              数量：{{goodsInfo.num}}
              状态：{{currentStatus(goodsInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(goodsInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="toGoodsDetail(goodsInfo.id)">商品详情</a>
          <a href="javascript:0;" @click="cancelReleaseGoods(passAuditList,goodsInfo.id,goodsInfo.name,index,goodsInfo)">取消发布</a>
          <a href="javascript:0;" @click="confirmSoldGoods(passAuditList,goodsInfo.id,goodsInfo.name,index,goodsInfo)">确认售出</a>
        </div>
      </div>
      <h3><i class="sui-icon icon-pc-right"></i>已出售的商品</h3>
      <div class="goods" v-for="(goodsInfo,index) in soldGoodsList" :key="goodsInfo.id">
        <div class="left-good">
          <div class="left-pic">
            <img :src="goodsInfo.coverImage" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{goodsInfo.name}}</p>
            <p class="attr">
              价格：{{goodsInfo.price}}
              数量：{{goodsInfo.num}}
              状态：{{currentStatus(goodsInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(goodsInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="deleteGoods(goodsInfo.id)">删除</a>
        </div>
      </div>
      <h3><i class="sui-icon icon-pc-right"></i>取消发布的商品</h3>
      <div class="goods" v-for="(goodsInfo,index) in cancelReleaseList" :key="goodsInfo.id">
        <div class="left-good">
          <div class="left-pic">
            <img :src="goodsInfo.coverImage" alt="">
          </div>
          <div class="right-info">
            <p class="title">{{goodsInfo.name}}</p>
            <p class="attr">
              价格：{{goodsInfo.price}}
              数量：{{goodsInfo.num}}
              状态：{{currentStatus(goodsInfo.status)}}
            </p>
            <p class="attr">商品描述：{{checkContentLength(goodsInfo.content)}}</p>
          </div>
        </div>
        <div class="right-gocart">
          <a href="javascript:0;" @click="restartReleaseGoods(goodsInfo.id)">编辑</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {reqGetGoodsWarnList, reqDeleteGoodsByGoodsId, reqConfirmSoldGoods} from '@/api'
import {Message} from "element-ui";
export default {
  name: "myGoods",
  data(){
    return{
      holdGoodsList:[],
      passAuditList:[],
      soldGoodsList:[],
      cancelReleaseList:[],
      notPassAuditList:[],
      underAuditList:[],
      goodsWarnList:[],
      reasonList:[],
      dialogVisible:false,
    }
  },
  computed:{
    //是否有发布的商品
    isShow(){
      return JSON.stringify(this.holdGoodsList) !== '[]';
    },
  },
  created() {
    this.getHoldGoodsList()
  },
  methods:{
    async getHoldGoodsList(){
      //只在登录状态才能获取持有物品列表
      if (JSON.stringify(this.$store.state.user.userDate) !== '{}'){
        this.holdGoodsList = await this.$store.dispatch('getAllHoldGoods')
        // 状态: -4:审核中 -3:未通过审核 -2:取消发布 -1:已出售 0~n:访问人数
        for(let item of this.holdGoodsList) {
          if (item.status >= 0){
            this.passAuditList.push(item)
          }else if (item.status === -1){
            this.soldGoodsList.push(item)
          }else if (item.status === -2){
            this.cancelReleaseList.push(item)
          }else if (item.status === -3){
            this.notPassAuditList.push(item)
          }else if (item.status === -4){
            this.underAuditList.push(item)
          }
        }
        let result = await reqGetGoodsWarnList()
        if (result.state === 200){
          this.goodsWarnList = result.data
        }
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
    //重新发布商品
    async restartReleaseGoods(goodsId){
      // await this.$store.dispatch('cancelCollection',{
      //   'goodsId':goodsId
      // })
      // this.collectedList.splice(index,1)
      await this.$store.dispatch('selectGoodsByGoodsIdSimple',{
        'goodsId':goodsId
      })
      await this.$router.push("/showGoodsInfo");
    },
    //取消发布
    async cancelReleaseGoods(list,goodsId,goodsName,index,goodsInfo){
      await this.$confirm(goodsName+'    将取消发布, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('cancelReleaseGoods',{
          'goodsId':goodsId
        })
        list.splice(index,1)
        this.cancelReleaseList.push(goodsInfo)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '操作已取消'
        });
      });
    },
    //确认商品已售出
    async confirmSoldGoods(list,goodsId,goodsName,index,goodsInfo){
      await this.$confirm(goodsName+'    将标记为已售出, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let result = await reqConfirmSoldGoods({'goodsId':goodsId})
        if (result.state === 200){
          Message({
            message: result.message,
            type: 'success'
          });
        }else {
          Message({
            message: result.message,
            type: 'warning'
          });
          return
        }
        list.splice(index,1)
        this.soldGoodsList.push(goodsInfo)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '操作已取消'
        });
      });
    },
    //显示未过审原因
    showReason(warnMessage){
      let result = []
      let list = warnMessage.split(',')
      let goodsWarnList = this.goodsWarnList
      list.forEach((id) => {
        goodsWarnList.forEach((goodsWarn) => {
          if (id === goodsWarn.warnId+''){
            result.push(goodsWarn.warnDetail)
          }
        })
      })
      this.reasonList = result
      this.dialogVisible = true
    },
    async deleteGoods(goodsId){
      let result = await reqDeleteGoodsByGoodsId({goodsId})
      if (result.state === 200){
        this.$message({
          message: result.message,
          type: 'success'
        });
        await this.getHoldGoodsList()
      }else {
        this.$message({
          message: result.message,
          type: 'warning'
        });
      }
    },
  },
  watch: {
    // 监听路由变化
    '$route.path': function () {
      // 页面重置到顶部
      this.scrollToTop()
    }
  }
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
