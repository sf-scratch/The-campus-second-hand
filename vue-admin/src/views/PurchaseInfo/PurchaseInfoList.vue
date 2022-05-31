<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>求购管理</el-breadcrumb-item>
      <el-breadcrumb-item>求购列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 商品列表区域 -->
    <el-table v-loading="loading"
              :data="purchaseInfoList"
              :stripe="true"
              border
              element-loading-text="拼命加载中"
              width="100%">
      <el-table-column label="id"
                       width="60"
                       prop="id">
      </el-table-column>
      <el-table-column label="信息标题"
                       prop="title" width="200px">
      </el-table-column>
      <el-table-column label="内容"
                       prop="content">
      </el-table-column>
<!--      <el-table-column label="html"-->
<!--                       prop="htmlContent" width="100px">-->
<!--      </el-table-column>-->
      <el-table-column label="发布人ID"
                       prop="userId" width="80px">
      </el-table-column>
      <el-table-column label="发布人名称"
                       prop="userName" width="95px">
      </el-table-column>
      <el-table-column label="创建时间"
                       prop="createDate" width="160px">
      </el-table-column>
      <el-table-column label="状态" prop="goods_state" width="95px">
        <template slot-scope="scope">
          <el-dropdown placement="top-end">
            <el-link :underline="false">
              <el-tag v-if="scope.row.status === -4">待审核</el-tag>
              <el-tag v-else-if="scope.row.status === -3" type="danger">未通过</el-tag>
              <el-tag v-else-if="scope.row.status === -2" type="info">发布取消</el-tag>
              <el-tag v-else-if="scope.row.status === -1" type="warning">已售出</el-tag>
              <el-tag v-else type="success">访问:{{scope.row.status}}</el-tag>
            </el-link>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="scope.row.status!==-4" @click.native="updateStatus('待审核',-4,scope.row.id,scope.row.title)"><el-tag>待审核</el-tag></el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status!==-3" @click.native="updateStatus('未通过',-3,scope.row.id,scope.row.title)"><el-tag type="danger">未通过</el-tag></el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status!==-2" @click.native="updateStatus('发布取消',-2,scope.row.id,scope.row.title)"><el-tag type="info">发布取消</el-tag></el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status!==-1" @click.native="updateStatus('已售出',-1,scope.row.id,scope.row.title)"><el-tag type="warning">已售出</el-tag></el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status<0" @click.native="updateStatus('已发布',0,scope.row.id,scope.row.title)"><el-tag type="success">已发布</el-tag></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="130px">
        <template slot-scope="scope">
          <!-- 预览按钮 -->
          <el-tooltip :enterable="false" class="item" content="查看详细内容" effect="dark" placement="top">
            <el-button icon="el-icon-search" size="mini" type="primary"
                       @click="purchaseInfoDetail(scope.row.title,scope.row.content)"></el-button>
          </el-tooltip>

          <!-- 删除按钮 -->
          <el-tooltip :enterable="false" class="item" content="删除信息" effect="dark" placement="top">
            <el-button icon="el-icon-delete" size="mini" type="danger"
                       @click="removeGoodsById(scope.row.id,scope.row.title)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
        :visible.sync="drawer"
        :with-header="false">
      <h1 style="text-align: center">{{drawerTitle}}</h1>
      <mavon-editor defaultOpen="preview" :ishljs="true" :toolbarsFlag='false' :zIndex="0" :subfield="false" ref=md :value="drawerContent"></mavon-editor>
    </el-drawer>

    <!--分页区域-->
    <el-pagination
        :current-page="queryInfo.currentPage"
        :page-size="queryInfo.size"
        :page-sizes="[5,10,15,20]"
        :total="total"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
import {reqGetPurchaseInfoList, reqDeletePurchaseInfoById, reqUpdatePurchaseInfoStatusById} from '@/api'
export default {
  name: "PurchaseInfoList",
  data(){
    return{
      // 查询列表参数
      queryInfo: {
        keyword: '',
        currentPage: 1,
        size: 5,
        userId:'',
        status:'',
      },
      purchaseInfoList:[],
      loading:false,
      total:0,
      drawer:false,
      drawerTitle:'',
      drawerContent:'',
    }
  },
  created() {
    this.getPurchaseInfoList()
  },
  methods:{
    async getPurchaseInfoList(){
      try {
        let result = await reqGetPurchaseInfoList(this.queryInfo)
        this.purchaseInfoList = result.data.records
        this.total = result.data.total
      }catch (e) {
        console.log(e.message)
      }
    },
    /**
     * 删除该求购信息
     * @param purchaseInfoId 求购信息id
     * @param title 求购信息标题
     */
    async removeGoodsById (purchaseInfoId,title) {
      await this.$confirm('此操作将永久删除信息<'+purchaseInfoId+'>标题《'+title+'》的求购信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await reqDeletePurchaseInfoById({purchaseInfoId})
          if (this.total % this.queryInfo.size === 1){
            this.queryInfo.currentPage--
            await this.getPurchaseInfoList()
          }else {
            await this.getPurchaseInfoList()
          }
        }catch (e) {
          console.log(e.message)
        }
      }).catch(err => {
        return err
      })
    },
    //修改商品状态
    updateStatus(statusName,status,purchaseInfoId,title){
      this.$confirm('信息<'+purchaseInfoId+'>标题《' + title + '》状态改为<' + statusName + '>, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await reqUpdatePurchaseInfoStatusById({purchaseInfoId,status})
        await this.getPurchaseInfoList()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消修改'
        });
      });
    },
    purchaseInfoDetail(title,content){
      this.drawer = true
      this.drawerTitle = title
      this.drawerContent = content
    },
    //改变每页数量
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getPurchaseInfoList()
    },
    //跳转到页数
    handleCurrentChange (newPage) {
      this.queryInfo.currentPage = newPage
      this.getPurchaseInfoList()
    },
  },
}
</script>

<style scoped lang="less">

</style>
