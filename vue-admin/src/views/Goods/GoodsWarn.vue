<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品警告</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区域-->
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddWarnDialog">添加警告</el-button>
        </el-col>
      </el-row>
      <!--  表格区域-->
      <tree-table
          :columns="warnColumns"
          :data="warnList"
          :expand-type="false"
          :selection-type="false"
          :show-index="true"
          :show-row-hover="true"
          border
          class="tree-table"
          index-text="#">
        <!--排序-->
<!--        <template slot="order" slot-scope="scope">-->
<!--          <el-tag v-if="!scope.row.fatherId" size="mini">一级分类</el-tag>-->
<!--          <el-tag v-else size="mini" type="success">二级分类</el-tag>-->
<!--        </template>-->
        <!--操作-->
        <template slot="opt" slot-scope="scope">
          <el-button icon="el-icon-edit" size="mini" type="primary" @click="showEditDialog(scope.row.warnId)">编辑
          </el-button>
          <el-button icon="el-icon-delete" size="mini" type="danger" @click="removeWarnById(scope.row.warnId)">删除
          </el-button>
        </template>
      </tree-table>

      <!-- 添加警告的对话框 -->
      <el-dialog :visible.sync="addWarnDialogVisible" title="添加分类" @close="addWarnDialogClosed">
        <el-form ref="addWarnFormRef" :model="addWarnForm" :rules="addWarnFormRules" label-width="100px">
          <el-form-item label="警告名称: " prop="warnName">
            <el-input v-model="addWarnForm.warnName"></el-input>
          </el-form-item>
          <el-form-item label="警告详情: " prop="warnDetail">
            <el-input v-model="addWarnForm.warnDetail"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addWarnDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addWarn">确 定</el-button>
        </div>
      </el-dialog>

      <!-- 修改分类的对话框 -->
      <el-dialog :visible.sync="editWarnDialogVisible" title="修改警告信息" @close="editWarnDialogClosed">
        <el-form ref="editWarnFormRef" :model="editWarnForm" label-width="100px">
          <el-form-item label="警告名称: " prop="warnName">
            <el-input v-model="editWarnForm.warnName"></el-input>
          </el-form-item>
          <el-form-item label="警告详细: " prop="warnDetail">
            <el-input v-model="editWarnForm.warnDetail"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editWarnDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editWarn">确 定</el-button>
        </div>
      </el-dialog>

    </el-card>
  </div>
</template>

<script>
import {
  reqAddGoodsWarn,
  reqGetGoodsWarnList,
  reqGetGoodsWarnByWarnId,
  reqDeleteGoodsWarnByWarnId,
  reqUpdateGoodsWarnByWarnId
} from '@/api'
export default {
  name: "GoodsWarn",
  data(){
    return{
      // 商品警告的数据列表, 默认为空
      warnList: [],
      // 显示添加商品警告的对话框
      addWarnDialogVisible:false,
      // 显示修改商品警告的对话框
      editWarnDialogVisible:false,
      // 添加警告的表单数据对象
      addWarnForm: {
        warnId: '',
        warnName: '',
        warnDetail: '',
        warnCreate: '',
        warnUpdate: '',
      },
      addWarnFormRules: {
        warnName: [{
          required: true,
          message: '请输入警告名称',
          trigger: 'blur'
        }],
        warnDetail: [{
          required: true,
          message: '请输入警告详情',
          trigger: 'blur'
        }],
      },
      // 修改警告的表单数据对象
      editWarnForm: {},
      // 为table指定列的定义
      warnColumns: [
        {
          label: '警告ID',
          prop: 'warnId'
        },
        {
          label: '警告名称',
          prop: 'warnName'
        },
        {
          label: '警告详细',
          prop: 'warnDetail'
        },
        {
          label: '警告创建时间',
          prop: 'warnCreate'
        },
        {
          label: '警告修改时间',
          prop: 'warnUpdate'
        },
        {
          label: '操作',
          // 表示, 将当前列定义为模板列
          type: 'template',
          // 表示当前列使用模板名称
          template: 'opt'
        },
      ],
    }
  },
  created () {
    this.getWarnList()
  },
  methods:{
    /**
     * 获取所有商品警告
     */
    async getWarnList () {
      let result = await reqGetGoodsWarnList()
      this.warnList = result.data
    },
    /**
     * 展现修改对话框的显示与隐藏
     * @param warnId
     */
    async showEditDialog (warnId) {
      try {
        let result = await reqGetGoodsWarnByWarnId({warnId})
        this.editWarnForm = result.data
        this.editWarnDialogVisible = true
      }catch (e) {
        console.log(e.message)
      }
    },
    /**
     * 根据分类id删除分类
     * @param warnId 警告id
     */
    async removeWarnById (warnId) {
      // 弹框询问用户是否直接删除数据
      await this.$confirm('此操作将永久删除该警告, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await reqDeleteGoodsWarnByWarnId({warnId})
          await this.getWarnList()
        }catch (e) {
          console.log(e.message)
        }
      }).catch(async () => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /**
     * 点击按钮, 展示
     */
    showAddWarnDialog () {
      // 先获取父级分类的数据列表
      // this.getParentCateList()
      // 再展示出对话框
      this.addWarnDialogVisible = true
    },
    /**
     * 点击按钮, 添加新的警告
     */
    async addWarn () {
      this.$refs.addWarnFormRef.validate(async valid => {
        if (!valid) return
        const {warnName, warnDetail} = this.addWarnForm
        try {
          await reqAddGoodsWarn({warnName,warnDetail})
          this.addWarnDialogVisible = false
          await this.getWarnList()
        }catch (e) {
          console.log(e.message)
        }
      })
    },
    /**
     * 监听对话框的关闭事件, 重置表单数据
     */
    addWarnDialogClosed () {
      this.$refs.addWarnFormRef.resetFields()
      this.addWarnForm = {}
    },
    /**
     * 编辑警告
     */
    async editWarn () {
      this.$refs.editWarnFormRef.validate(async valid => {
        if (!valid) {
          return
        }
        try {
          const {warnName,warnDetail,warnId} = this.editWarnForm
          await reqUpdateGoodsWarnByWarnId({warnId,warnName,warnDetail})
          await this.getWarnList()
          this.editWarnDialogVisible = false
        }catch (e) {
          console.log(e.message)
        }
      })
    },
    /**
     * 监听 修改警告对话框的关闭事件
     */
    editWarnDialogClosed () {
      this.$refs.editWarnFormRef.resetFields()
      this.editWarnForm = {}
    },
  },
}
</script>

<style scoped>

</style>
