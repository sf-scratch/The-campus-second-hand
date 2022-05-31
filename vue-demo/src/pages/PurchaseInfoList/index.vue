<template>
  <div class="cart-complete-wrap">
    <div class="cart-complete">
      <div class="goods" v-for="(purchaseInfo) in purchaseInfoList.records" :key="purchaseInfo.id">
        <div class="left-good" @click="purchaseInfoDetail(purchaseInfo)">
          <div class="right-info">
            <p class="title">{{purchaseInfo.title}}</p>
            <p class="attr">
              发布者：{{purchaseInfo.userName}}
              发布时间：{{purchaseInfo.createDate}}
            </p>
          </div>
        </div>
      </div>
    </div>


    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="size"
          :total="purchaseInfoList.total">
      </el-pagination>
    </div>

    <el-drawer
        :visible.sync="drawer"
        :with-header="false">
      <h1 style="text-align: center">{{drawerTitle}}</h1>
      <mavon-editor defaultOpen="preview" :ishljs="true" :toolbarsFlag='false' :zIndex="0" :subfield="false" ref=md :value="drawerContent"></mavon-editor>
    </el-drawer>
  </div>
</template>

<script>
import {reqGetPurchaseInfoList} from '@/api'
import "mavon-editor/dist/css/index.css"
import {Message} from "element-ui";
export default {
  name: "purchaseInfoList",
  data () {
    return {
      purchaseInfoList:[],
      currentPage:1,
      size:10,
      drawer:false,
      drawerTitle:'',
      drawerContent:'',
    }
  },
  computed: {

  },
  created() {
    this.getPurchaseInfoList()
  },
  methods: {
    async handleSizeChange(val) {
      this.size = val
      await this.getPurchaseInfoList()
    },
    async handleCurrentChange(val) {
      this.currentPage = val
      await this.getPurchaseInfoList()
    },
    async getPurchaseInfoList(){
      let result = await this.$store.dispatch('getPurchaseInfoList',{
        'currentPage':this.currentPage,
        'size':this.size,
      })
      if (result.state === 200){
        this.$message({
          message: result.message,
          type: 'success'
        });
        this.purchaseInfoList = result.data
      }else {
        Message({
          message: result.message,
          type: 'warning'
        });
      }
    },
    async toPurchaseInfoDetail(purchaseInfoId){
      let location = {'name':'purchaseInfoDetail'}
      location.params = {purchaseInfoId}
      await this.$router.push(location)
    },
    purchaseInfoDetail(purchaseInfo){
      this.drawer = true
      this.drawerTitle = purchaseInfo.title
      this.drawerContent = purchaseInfo.content
    },
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
        cursor: pointer;

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
