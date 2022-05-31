<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区域-->
    <el-card class="box-card">
      <el-row :gutter="8">
        <!-- 搜索与添加区域 -->
        <el-col :span="4">
          <el-input v-model="queryInfo.keyword" size="small" class="input-with-select" clearable placeholder="请输入商品名称"
                    @clear="getGoodsList" @keyup.enter.native="getGoodsList">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-input v-model="queryInfo.ownerId" size="small" class="input-with-select" clearable placeholder="请输入持有者ID"
                    @clear="getGoodsList" @keyup.enter.native="getGoodsList">
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-dropdown @command="handleCommand">
            <el-button type="primary" size="small">
              {{goodsStatus}}<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="-4"><el-tag>待审核</el-tag></el-dropdown-item>
              <el-dropdown-item :command="-3"><el-tag type="danger">未通过</el-tag></el-dropdown-item>
              <el-dropdown-item :command="-2"><el-tag type="info">发布取消</el-tag></el-dropdown-item>
              <el-dropdown-item :command="-1"><el-tag type="warning">已售出</el-tag></el-dropdown-item>
              <el-dropdown-item :command="0"><el-tag type="success">已发布</el-tag></el-dropdown-item>
              <el-dropdown-item command="">取消选中</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
        <el-col :span="1">
          <el-button size="small" icon="el-icon-search" @click="getGoodsList">搜索</el-button>
        </el-col>
<!--        <el-col :span="4">-->
<!--          <el-button type="primary" @click="goAddpage">添加商品</el-button>-->
<!--        </el-col>-->
      </el-row>

      <!-- 商品列表区域 -->
      <el-table v-loading="loading"
                :data="goodsList"
                :stripe="true"
                border
                element-loading-text="拼命加载中"
                width="100%">
        <el-table-column label="商品id"
                         width="70"
                         prop="id">
        </el-table-column>
        <el-table-column label="商品名称"
                         prop="name">
        </el-table-column>
        <el-table-column label="持有者id"
                         prop="ownerId" width="80px">
        </el-table-column>
        <el-table-column label="商品价格(元)"
                         prop="price" width="105px">
        </el-table-column>
        <el-table-column label="数量"
                         prop="num" width="60px">
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
                <el-dropdown-item v-if="scope.row.status!==-4" @click.native="updateStatus('待审核',-4,scope.row.id,scope.row.name)"><el-tag>待审核</el-tag></el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status!==-3" @click.native="updateStatus('未通过',-3,scope.row.id,scope.row.name)"><el-tag type="danger">未通过</el-tag></el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status!==-2" @click.native="updateStatus('发布取消',-2,scope.row.id,scope.row.name)"><el-tag type="info">发布取消</el-tag></el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status!==-1" @click.native="updateStatus('已售出',-1,scope.row.id,scope.row.name)"><el-tag type="warning">已售出</el-tag></el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status<0" @click.native="updateStatus('已发布',0,scope.row.id,scope.row.name)"><el-tag type="success">已发布</el-tag></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="130px">
          <template slot-scope="scope">
            <!-- 预览按钮 -->
            <el-tooltip :enterable="false" class="item" content="预览商品" effect="dark" placement="top">
              <el-button icon="el-icon-search" size="mini" type="primary"
                         @click="viewGoodsById(scope.row.id)"></el-button>
            </el-tooltip>

            <!-- 删除按钮 -->
            <el-tooltip :enterable="false" class="item" content="删除商品" effect="dark" placement="top">
              <el-button icon="el-icon-delete" size="mini" type="danger"
                         @click="removeGoodsById(scope.row.id,scope.row.name)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

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
    </el-card>



    <el-dialog  :visible.sync="dialogVisible" append-to-body width="1250px"
                style="padding: 0;" title="预览商品">
      <GoodsDetailInfo v-bind:goodsDetailInfo="goodsDetailInfo"/>
    </el-dialog>
  </div>
</template>

<script>
import {reqSearchGoodsInfoList, reqDeleteGoodsById, reqUpdateGoodsStatusByGoodsId,
  reqSelectGoodsByGoodsId} from '@/api'
import GoodsDetailInfo from '@/views/GoodsDetailInfo'
export default {
  name: "GoodsList",
  components: {
    GoodsDetailInfo
  },
  data () {
    return {
      // 查询列表参数
      queryInfo: {
        keyword: '',
        currentPage: 1,
        size: 5,
        categoryId:'',
        typeId:'',
        status:'',
        ownerId:'',
      },
      // 商品总数
      total: 0,
      // 商品列表
      goodsList: [],
      // 商品状态
      goodsStatus:'状态',
      loading:false,
      // 修改商品状态
      updateGoodsStatus:'',
      //商品详细信息
      goodsDetailInfo:{},
      //弹窗是否可见
      dialogVisible:false,
    }
  },
  created () {
    this.getGoodsList()
  },
  methods: {
    /**
     * 根据分页获取对应的商品列表
     */
    async getGoodsList () {
      /**
       * get请求, 参数上都加一个 params属性
       */
      this.loading = true
      try {
        let result = await reqSearchGoodsInfoList(this.queryInfo)
        this.goodsList = result.data.records
        this.total = result.data.total
      }catch (e) {
        console.log(e.message)
      }finally {
        this.loading = false
      }
    },
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getGoodsList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.currentPage = newPage
      this.getGoodsList()
    },
    //搜索下拉选项
    handleCommand(command){
      this.queryInfo.status = command
      if (command === -4){
        this.goodsStatus = '待审核'
      }else if (command === -3){
        this.goodsStatus = '未通过'
      }else if (command === -2){
        this.goodsStatus = '发布取消'
      }else if (command === -1){
        this.goodsStatus = '已售出'
      }else if (command === 0){
        this.goodsStatus = '已发布'
      }else {
        this.goodsStatus = '状态'
      }
    },
    //状态下拉选项
    // updateCommand(goodsStatus){
    //   this.updateGoodsStatus = goodsStatus
    // },
    //修改商品状态
    updateStatus(goodsStatusName,goodsStatus,goodsId,goodsName){
      this.$confirm('<' + goodsName + '>状态改为<' + goodsStatusName + '>, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await reqUpdateGoodsStatusByGoodsId({goodsId,goodsStatus})
        await this.getGoodsList()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消修改'
        });
      });
    },
    /**
     * @param goodsId 商品id
     * @param goodsName 商品名称
     */
    async removeGoodsById (goodsId,goodsName) {
      await this.$confirm('此操作将永久删除<'+goodsName+'>商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await reqDeleteGoodsById({goodsId})
          if (this.total % this.queryInfo.size === 1){
            this.queryInfo.currentPage--
            await this.getGoodsList()
          }else {
            await this.getGoodsList()
          }
        }catch (e) {
          console.log(e.message)
        }
      }).catch(err => {
        return err
      })
    },
    /**
     * @param goodsId 商品id
     */
    async viewGoodsById(goodsId){
      try {
        let result = await reqSelectGoodsByGoodsId({goodsId})
        this.goodsDetailInfo = result.data
        this.dialogVisible = true
      }catch (e) {
        console.log(e.message)
      }
    },
  },
}
</script>

<style scoped>

</style>
