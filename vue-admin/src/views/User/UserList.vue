<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图区域-->
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 搜索与添加区域 -->
          <el-input class="input-with-select" clearable placeholder="请输入内容" v-model="queryInfo.query"
                    v-on:keydown.enter.native="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
<!--        <el-col :span="4">-->
<!--          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>-->
<!--        </el-col>-->
      </el-row>
    </el-card>

    <!--  用户列表区域-->
    <el-table v-loading="loading"
              element-loading-text="拼命加载中"
              :data="userList"
              :stripe="true"
              border
              style="width: 100%">
<!--      <el-table-column label="#"-->
<!--                       type="index">-->
<!--      </el-table-column>-->
      <el-table-column label="用户id"
                       width="70"
                       prop="uid">
      </el-table-column>
      <el-table-column label="用户名"
                       prop="username" width="100">
      </el-table-column>
      <el-table-column label="电话"
                       prop="phone" width="120">
      </el-table-column>
      <el-table-column label="邮箱"
                       prop="email">
      </el-table-column>
      <el-table-column label="QQ"
                       prop="qq" width="110">
      </el-table-column>
      <el-table-column label="性别"
                       prop="gender" width="50">
        <template slot-scope="scope">
          <span v-if="scope.row.gender === 1" style="color:blue">男</span>
          <span v-else style="color: red">女</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间"
                       prop="createdTime" width="160">
      </el-table-column>
      <el-table-column label="修改时间"
                       prop="modifiedTime" width="160">
      </el-table-column>
      <el-table-column label="修改人"
                       prop="modifiedUser" width="100">
      </el-table-column>
      <el-table-column label="标记删除" width="80">
        <!-- 作用域插槽渲染状态这一列 -->
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.isDelete"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
              @change="userStateChanged(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="130">
        <template slot-scope="scope">
          <!-- 修改按钮 -->
          <el-tooltip :enterable="false" class="item" content="修改用户" effect="dark" placement="top">
            <el-button icon="el-icon-edit" size="mini" type="primary"
                       @click="showEditDialog(scope.row.uid)"></el-button>
          </el-tooltip>

          <!-- 删除按钮-->
          <el-tooltip :enterable="false" class="item" content="删除用户" effect="dark" placement="top">
            <el-button icon="el-icon-delete" size="mini" type="danger"
                       @click="removeUserById(scope.row.uid)"></el-button>
          </el-tooltip>

          <!-- 分配角色按钮-->
<!--          <el-tooltip :enterable="false" class="item" content="分配角色" effect="dark" placement="top">-->
<!--            <el-button icon="el-icon-setting" size="mini" type="warning" @click="setRole(scope.row)"></el-button>-->
<!--          </el-tooltip>-->
        </template>
      </el-table-column>
    </el-table>

    <!--分页区域-->
    <el-pagination
        :current-page="queryInfo.pageNum"
        :page-size="this.queryInfo.pageSize"
        :page-sizes="[6, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>

    <!--修改用户对话框-->
    <el-dialog
        :visible.sync="editDialogVisible"
        title="修改用户"
        width="35%"
        @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form ref="editFormRef" :model="editForm" :rules="addFormRules" label-width="70px">
        <!-- prop=username 对应了 addFormRules中的username校验规则-->
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="editForm.qq"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="editForm.phone" w></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="editForm.avatar"></el-input>
          <el-image style="width: 120px" :src="editForm.avatar"></el-image>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="editForm.message"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="editForm.gender" :label="1">男</el-radio>
          <el-radio v-model="editForm.gender" :label="0">女</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="editDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="editUserInfo">确 定</el-button>
  </span>
    </el-dialog>



  </div>
</template>

<script>
import {reqGetUserListByPage,reqUpdateIsDelete,reqSelectUserByUid,reqUpdateUserInfo,reqDeleteUserByUid} from '@/api'
export default {
  name: "UserList",
  data(){
    // 验证邮箱的规则
    let checkEmail = (rule, value, cb) => {
      // 验证邮箱的正则表达式
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
      if (!value){
        // 管理员可设置邮箱为空
        return cb()
      }
      if (regEmail.test(value)) {
        // 合法的邮箱
        return cb()
      }
      cb(new Error('请输入合法的邮箱!'))
    }
    // 验证手机号的规则
    let checkMobile = (rule, value, cb) => {
      const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (regMobile.test(value)) {
        // 合法的手机号
        return cb()
      }
      cb(new Error('请输入合法的手机号!'))
    }
    return {
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        // 当前的页数
        pageNum: 1,
        pageSize: 6
      },
      total: 0,
      //更改用户信息窗口是否显示
      editDialogVisible:false,
      userList:[],
      loading:false,
      // 查询到的用户信息对象
      editForm: {
        uid: '',
        username: '',
        qq: '',
        email: '',
        phone: '',
        message: '',
        gender:'',
        avatar:'',
      },
      // 添加表单的验证规则对象
      addFormRules: {
        email: [{
          required: false,
          message: '请输入邮箱',
          trigger: 'blur'
        }, {
          validator: checkEmail,
          trigger: 'blur'
        }],
        mobile: [
          {
            required: false,
            message: '请输入手机号',
            trigger: 'blur'
          }, {
            validator: checkMobile,
            trigger: 'blur'
          }
        ]
      },
    }
  },
  created() {
    this.getUserList()
  },
  methods:{
    //获取用户列表
    async getUserList(){
      this.loading = true
      try {
        let result = await reqGetUserListByPage({
          'currentPage':this.queryInfo.pageNum,'size':this.queryInfo.pageSize,'keyword':this.queryInfo.query});
        this.userList = result.data.records
        this.total = result.data.total
      }catch (e) {
        console.log(e.message)
      }finally {
        this.loading = false
      }
    },
    /**
     * 监听 pagesize 改变的事件
     * @param newSize
     */
    async handleSizeChange (newSize) {
      // console.log(newSize)
      this.queryInfo.pageSize = newSize
      await this.getUserList()
    },
    /**
     * 监听 页面值 改变的事件
     * @param newPage
     */
    handleCurrentChange (newPage) {
      // console.log(newPage)
      this.queryInfo.pageNum = newPage
      this.getUserList()
    },
    //用户状态改变（是否删除标记）
    async userStateChanged (userInfo) {
      let message
      if (userInfo.isDelete === 1){
        message = '此操作将使该用户无法登录, 是否继续?'
      }else {
        message = '此操作将使该用户能正常登录, 是否继续?'
      }
      this.$confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await reqUpdateIsDelete({'uid':userInfo.uid, 'isDelete':userInfo.isDelete})
          await this.getUserList()
        }catch (e) {
          console.log(e.message)
        }
      }).catch(() => {
        userInfo.isDelete = userInfo.isDelete === 0 ? 1 : 0
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    /**
     * 展示编辑用户的对话框
     * @param id
     */
    async showEditDialog (id) {
      try {
        let result = await reqSelectUserByUid({'uid':id})
        this.editForm = result.data
        this.editDialogVisible = true
      }catch (e) {
        console.log(e.message())
      }
    },
    /**
     * 修改用户信息并提交
     */
    async editUserInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) {
          return
        }
        const {uid, username, qq, email, phone, message, gender, avatar} = this.editForm
        // 可以发送修改用户的网络请求
        try {
          await reqUpdateUserInfo({uid, username, qq, email, phone, message, gender, avatar})
          // 先隐藏修改用户的对话框
          this.editDialogVisible = false
          // 再重新获取用户列表, 来刷新页面
          await this.getUserList()
        }catch (e) {
          console.log(e.message)
        }
      })
    },
    /**
     * 根据Id删除对应的用户信息
     * @param id
     */
    async removeUserById (id) {
      // console.log(id)
      // 弹框询问用户是否删除数据
      await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          await reqDeleteUserByUid({'uid':id})
          await this.getUserList()
        }catch (e) {
          console.log(e.message)
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      })
    },
    /**
     * 监听修改用户对话框的关闭事件
     */
    editDialogClosed () {
      // 当第一次打开修改表单, 若触发数据校验提示, 在第二次打开还会出来
      // 这时候就是需要使用下面这行代码重置修改表单
      this.$refs.editFormRef.resetFields()
    },
  },
}
</script>

<style scoped>

</style>
