<template>
  <div class="dddd" style="overflow-y: auto">

    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-backtop></el-backtop>
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品审核</el-breadcrumb-item>
    </el-breadcrumb>
    <br>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>剩余待审核商品数：{{goodsList.length}}</el-breadcrumb-item>
    </el-breadcrumb>
    <el-descriptions class="margin-top" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-discount"></i>商品ID
        </template>{{goodsInfo.id}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          商品名称
        </template>{{goodsInfo.name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          商品分类
        </template>{{goodsInfo.categoryName}} | {{goodsInfo.typeName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          数量
        </template>{{goodsInfo.num}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-coin"></i>出售价格
        </template>{{goodsInfo.price}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-coin"></i>原价
        </template>{{goodsInfo.originalPrice}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>QQ号
        </template>{{goodsInfo.contactQq}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>微信号
        </template>{{goodsInfo.contactWx}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>持有者ID
        </template>{{goodsInfo.ownerId}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-time"></i>发布时间
        </template>{{goodsInfo.createDate}}
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions class="margin-top" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-edit-outline"></i>内容
        </template>{{goodsInfo.content}}
      </el-descriptions-item>
    </el-descriptions>

    <el-divider><i class="el-icon-picture"></i>封面</el-divider>

    <div style="width: 300px; clear: both; display: block; margin: auto">
      <el-image
          style="width: 300px; height: 300px"
          :src='urlList[0]'
          :preview-src-list="urlList">
      </el-image>
    </div>

    <el-divider><i class="el-icon-picture"></i>商品详细图片</el-divider>

    <div style="width: 700px; height: 300px; clear: both; display: block; margin: auto">
      <el-carousel trigger="click" height="300px" :autoplay="false">
        <el-carousel-item v-for="(image,index) in srcList" :key="index">
          <el-image style="height: 300px"
                    :src="image"
                    :preview-src-list="srcList">
          </el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

<!--    <el-backtop target="dddd"></el-backtop>-->
    <div>
      <el-card class="box-card">
        <el-row>
          <el-col :span="6">
            <el-button @click="lastIndex"><i class="el-icon-d-arrow-left"></i></el-button>
          </el-col>
          <el-col :span="8">
            <el-button icon="el-icon-error" type="danger" @click="goodsRefused(goodsInfo.id)">拒绝发布</el-button>
          </el-col>
          <el-col :span="9">
            <el-button icon="el-icon-success" type="success" @click="goodsApproved(goodsInfo.id)">审核通过</el-button>
          </el-col>
          <el-col :span="1">
            <el-button @click="nextIndex"><i class="el-icon-d-arrow-right"></i></el-button>
          </el-col>
        </el-row>

        <!-- 警告信息的对话框 -->
        <el-dialog :visible.sync="warnInfoDialogVisible" title="警告信息" @close="warnInfoDialogClosed">
          <el-checkbox-group v-model="checkList">
            <el-col :span="12" v-for="(goodsWarn) in goodsWarnList">
              <el-checkbox :label="goodsWarn.warnId">{{goodsWarn.warnName}}</el-checkbox>
            </el-col>
          </el-checkbox-group>
<!--          <el-form ref="warnInfoFormRef" :model="warnInfoForm" label-width="100px">-->
<!--            <el-form-item label="警告名称: " prop="warnName">-->
<!--              <el-input v-model="warnInfoForm.warnName"></el-input>-->
<!--            </el-form-item>-->
<!--            <el-form-item label="警告详细: " prop="warnDetail">-->
<!--              <el-input v-model="warnInfoForm.warnDetail"></el-input>-->
<!--            </el-form-item>-->
<!--          </el-form>-->
          <div slot="footer" class="dialog-footer">
            <el-button @click="warnInfoDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="warnInfo(goodsInfo.id)">确 定</el-button>
          </div>
        </el-dialog>
      </el-card>
    </div>

  </div>
</template>

<script>
import {
  reqGetGoodsDetailByWaitCheck,
  reqUpdateGoodsStatusToAdopt,
  reqUpdateGoodsStatusToRefuse,
  reqGetGoodsWarnList
} from '@/api'
// import {forEach} from "@/lib/util";
export default {
  name: "GoodsVerify",
  data() {
    return {
      // 显示警告信息的对话框
      warnInfoDialogVisible:false,
      urlList: [],
      srcList: [],
      // 待审核商品列表
      goodsList:[],
      // 商品详细信息
      goodsInfo: {
        id:'',
        name:'',
        categoryName:'',
        typeName:'',
        num:'',
        price:'',
        originalPrice:'',
        contactQq:'',
        createDate:'',
        contactWx:'',
        content:'',
        coverImage:'',
        image1:'',
        image2:'',
        image3:'',
      },
      // 当前数组位置
      currentIndex:0,
      goodsWarnList: [],
      checkList: [],
    }
  },
  created () {
     this.getWaitCheckGoodsList()
  },
  methods:{
    async getWaitCheckGoodsList(){
      let result = await reqGetGoodsDetailByWaitCheck()
      if (result.data.length === 0){
        await this.$router.push("/empty")
      }else {
        this.goodsList = result.data
        this.goodsInfo = result.data[0]
        this.setImages()
      }
    },
    //上一个商品信息
    lastIndex(){
      if (this.currentIndex > 0){
        this.goodsInfo = this.goodsList[--this.currentIndex]
        this.setImages()
      }
    },
    //下一个商品信息
    nextIndex(){
      if (this.currentIndex < this.goodsList.length - 1){
        this.goodsInfo = this.goodsList[++this.currentIndex]
        this.setImages()
      }
    },
    setImages(){
      this.urlList = [this.goodsInfo.coverImage]
      if (this.goodsInfo.image2){
        if (this.goodsInfo.image3){
          this.srcList = [this.goodsInfo.image1,this.goodsInfo.image2,this.goodsInfo.image3]
        }else {
          this.srcList = [this.goodsInfo.image1,this.goodsInfo.image2]
        }
      }else {
        this.srcList = [this.goodsInfo.image1]
      }
    },
    //从goodsList中删除当前页数据，替换页面数据
    async removeCurrentIndex(){
      this.goodsList.splice(this.currentIndex,1)
      let len = this.goodsList.length
      if (len === 0){
        await this.$router.push("/empty")
      }else {
        if (len === this.currentIndex) this.currentIndex--
        this.goodsInfo = this.goodsList[this.currentIndex]
        this.setImages()
      }
    },
    //商品通过审核
    async goodsApproved(goodsId){
      try {
        await reqUpdateGoodsStatusToAdopt({goodsId})
        await this.removeCurrentIndex()
      }catch (e) {
        console.log(e.message)
      }
    },
    //商品拒绝发布
    async goodsRefused(){
      try {
        let result = await reqGetGoodsWarnList()
        this.goodsWarnList = result.data
        this.warnInfoDialogVisible = true
      }catch (e) {
        console.log(e.message)
      }
    },
    /**
     * 编辑警告
     */
    async warnInfo (goodsId) {
      try {
        if (this.checkList.length === 0){
          this.$message({
            message: '至少选择一个',
            type: 'warning'
          });
          return
        }
        let warnMessage = JSON.stringify(this.checkList)
        warnMessage = warnMessage.replace('[','')
        warnMessage = warnMessage.replace(']','')
        await reqUpdateGoodsStatusToRefuse({goodsId,'warnMessage':warnMessage})
        await this.removeCurrentIndex()
        this.warnInfoDialogVisible = false
      }catch (e) {
        console.log(e.message)
      }
    },
    /**
     * 监听 修改警告对话框的关闭事件
     */
    warnInfoDialogClosed () {
      this.checkList = []
    },
  },

}
</script>

<style scoped lang="less">
//.dddd{
//  height: 100vh;  // 不必是100vh，只需要是该容器显示的最大高度即可
//  overflow-x: hidden;
//}
</style>
