<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区域-->
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddCateDialog">添加分类</el-button>
        </el-col>
      </el-row>
      <!--  表格区域-->
<!--      v-loading="!cateList.length"-->
<!--      element-loading-text="拼命加载中"-->
      <tree-table
                  :columns="cateColumns"
                  :data="cateList"
                  :expand-type="false"
                  :selection-type="false"
                  :show-index="true"
                  :show-row-hover="true"
                  border
                  class="tree-table"
                  index-text="#">
        <!--排序-->
        <template slot="order" slot-scope="scope">
          <el-tag v-if="!scope.row.fatherId" size="mini">一级分类</el-tag>
          <el-tag v-else size="mini" type="success">二级分类</el-tag>
        </template>
        <!--操作-->
        <template slot="opt" slot-scope="scope">
          <el-button icon="el-icon-edit" size="mini" type="primary" @click="showEditDialog(scope.row.fatherId,scope.row.id)">编辑
          </el-button>
          <el-button icon="el-icon-delete" size="mini" type="danger" @click="removeCateById(scope.row.fatherId,scope.row.id)">删除
          </el-button>
        </template>
      </tree-table>

      <!-- 添加分类的对话框 -->
      <el-dialog :visible.sync="addCateDialogVisible" title="添加分类" @close="addCateDialogClosed">
        <el-form ref="addCateFormRef" :model="addCateForm" :rules="addCateFormRules" label-width="100px">
          <el-form-item label="分类名称: " prop="name">
            <el-input v-model="addCateForm.name"></el-input>
          </el-form-item>
          <el-form-item label="父级分类: ">
            <el-select v-model="addCateForm.fatherId" placeholder="请选择">
              <el-option label="请选择" value="请选择"></el-option>
              <el-option
                  v-for="item in parentCateList"
                  :key="item.categoryId"
                  :label="item.categoryName"
                  :value="item.categoryId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addCateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCate">确 定</el-button>
        </div>
      </el-dialog>

      <!-- 修改分类的对话框 -->
      <el-dialog :visible.sync="editCateDialogVisible" title="修改分类" @close="editCateDialogClosed">
        <el-form ref="editCateFormRef" :model="editCateForm" label-width="100px">
          <el-form-item label="分类名称: " prop="name">
            <el-input v-if="editCateForm.categoryName" v-model="editCateForm.categoryName"></el-input>
            <el-input v-else v-model="editCateForm.typeName"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editCateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editCate">确 定</el-button>
        </div>
      </el-dialog>

    </el-card>
  </div>
</template>

<script>
import {
  reqGetGoodsCategoryTree, reqGetGoodsCategoryInfo, reqGetGoodsTypeInfo, reqUpdateGoodsCategoryName,
  reqUpdateGoodsTypeName, reqAddGoodsCategory, reqAddGoodsType, reqGetCategoryListInGoodsCategory,
  reqDeleteGoodsCategory, reqDeleteGoodsType
} from '@/api'
export default {
  name: "Category",
  data(){
    return{
      // 显示添加分类的对话框
      addCateDialogVisible:false,
      // 选中的父级分类的Id
      selectedKeys: '',
      //父级分类列表信息
      parentCateList:[],
      // 添加分类的表单数据对象
      addCateForm: {
        // 将要添加的分类的名称
        name: '',
        // 父级分类的Id
        fatherId: '请选择',
      },
      // 添加分类表单的校验规则对象
      addCateFormRules: {
        name: [{
          required: true,
          message: '请输入分类名称',
          trigger: 'blur'
        }]
      },
      // 显示修改分类的对话框
      editCateDialogVisible:false,
      // 修改分类的表单数据对象
      editCateForm: {},
      // 商品分类的数据列表, 默认为空
      cateList: [],
      // 为table指定列的定义
      cateColumns: [
        {
          label: '分类名称',
          prop: 'name'
        },
        {
          label: '分类创建时间',
          prop: 'createTime'
        },
        {
          label: '分类修改时间',
          prop: 'updateTime'
        },
        {
          label: '分类级别',
          // 表示, 将当前列定义为模板列
          type: 'template',
          // 表示当前列使用模板名称
          template: 'order'
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
    this.getCateList()
  },
  methods:{
    /**
     * 获取所有商品分类
     */
    async getCateList () {
      let result = await reqGetGoodsCategoryTree()
      this.cateList = result.data
    },
    /**
     * 获取父级分类的数据列表
     */
    async getParentCateList () {
      let result = await reqGetCategoryListInGoodsCategory()
      this.parentCateList = result.data
    },
    /**
     * 点击按钮, 展示
     */
    showAddCateDialog () {
      // 先获取父级分类的数据列表
      this.getParentCateList()
      // 再展示出对话框
      this.addCateDialogVisible = true
    },
    /**
     * 点击按钮, 添加新的分类
     */
    async addCate () {
      this.$refs.addCateFormRef.validate(async valid => {
        if (!valid) return
        const {fatherId, name} = this.addCateForm
        try {
          if (fatherId === '请选择'){
            await reqAddGoodsCategory({'categoryName':name})
          }else {
            await reqAddGoodsType({'categoryId':fatherId,'typeName':name})
          }
          await this.getCateList()
          this.addCateDialogVisible = false
        }catch (e) {
          console.log(e.message)
        }
      })
    },
    /**
     * 监听对话框的关闭事件, 重置表单数据
     */
    addCateDialogClosed () {
      this.$refs.addCateFormRef.resetFields()
      this.addCateForm.name = ''
      this.addCateForm.fatherId = '请选择'
    },
    /**
     * 展现修改对话框的显示与隐藏
     * @param fatherId
     * @param id
     */
    async showEditDialog (fatherId, id) {
      let result
      try {
        if (fatherId){
          result = await reqGetGoodsTypeInfo({id})
        }else {
          result = await reqGetGoodsCategoryInfo({id})
        }
      }catch (e) {
        console.log(e.message)
      }
      this.editCateForm = result.data
      this.editCateDialogVisible = true
    },
    /**
     * 根据分类id删除分类
     * @param fatherId 上一级分类id
     * @param id 当前分类id
     */
    async removeCateById (fatherId, id) {
      // 弹框询问用户是否直接删除数据
      await this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          if (fatherId){
            await reqDeleteGoodsType({'typeId':id})
          }else {
            await reqDeleteGoodsCategory({'categoryId':id})
          }
          await this.getCateList()
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
     * 编辑分类
     */
    async editCate () {
      this.$refs.editCateFormRef.validate(async valid => {
        if (!valid) {
          return
        }
        if (this.editCateForm.typeId){
          await reqUpdateGoodsTypeName({
            'typeName':this.editCateForm.typeName,'typeId':this.editCateForm.typeId
          })
        }else {
          await reqUpdateGoodsCategoryName({
            'categoryName':this.editCateForm.categoryName,'categoryId':this.editCateForm.categoryId})
        }
        await this.getCateList()
        this.editCateDialogVisible = false
      })
    },
    /**
     * 监听 修改分类对话框的关闭事件
     */
    editCateDialogClosed () {
      this.$refs.editCateFormRef.resetFields()
    },
  },
}
</script>

<style scoped>

</style>
