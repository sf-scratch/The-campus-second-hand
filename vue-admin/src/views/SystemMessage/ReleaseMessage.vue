<template>
  <div class="editor">
    <el-form label-width="80px" :model="commonMessage" :rules="commonMessageRules" ref="commonMessage">
      <el-form-item label="通知标题" prop="title">
        <el-input v-model="commonMessage.title" size="large" placeholder="请输入标题" maxlength="20" show-word-limit></el-input>
      </el-form-item>
      <el-form-item label="通知内容" prop="content">
        <el-input v-model="commonMessage.content" size="large" placeholder="请输入内容" maxlength="200" show-word-limit></el-input>
      </el-form-item>
      <el-button @click="releaseCommonMessage">发送</el-button>
    </el-form>

    <el-form label-width="80px" :model="bulletinBoardMessage" :rules="bulletinBoardMessageRules" ref="bulletinBoardMessage">
      <el-form-item label="公告标题" prop="title">
        <el-input v-model="bulletinBoardMessage.title" size="large" placeholder="请输入标题" maxlength="20" show-word-limit></el-input>
      </el-form-item>
      <el-form-item label="公告内容" prop="content">
        <mavon-editor v-model="bulletinBoardMessage.content" ref="md" @change="change"/>
      </el-form-item>
      <el-button @click="releaseBulletinBoardMessage">发送</el-button>
    </el-form>
  </div>
</template>

<script>
import {reqReleaseCommonMessage,reqReleaseBulletinBoardMessage} from '@/api'
export default {
  name: "ReleaseMessage",
  data(){
    return{
      commonMessage:{
        title:'',
        content:'',
      },
      bulletinBoardMessage:{
        title:'',
        content:'',
        htmlContent:'',
      },
      commonMessageRules:{
        title: [
          {required: true, message: '请输入通知标题', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入通知内容', trigger: 'blur'},
        ],
      },
      bulletinBoardMessageRules:{
        title: [
          {required: true, message: '请输入公告标题', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入公告内容', trigger: 'blur'},
        ],
      },
    }
  },
  methods:{
    async releaseCommonMessage () {
      this.$refs['commonMessage'].validate(async (valid) => {
        if (valid) {
          this.$confirm('该通知会立即发送给在线用户，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            try {
              let {title,content} = this.commonMessage
              await reqReleaseCommonMessage({title,content,'htmlContent':content})
              this.commonMessage = {
                title:'',
                content:'',
              }
            } catch (e) {
              console.log(e.message)
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发布'
            });
          });
        } else {
          console.log('表单有误')
        }
      })
    },
    async releaseBulletinBoardMessage () {
      this.$refs['bulletinBoardMessage'].validate(async (valid) => {
        if (valid) {
          this.$confirm('该公告会立即发送给在线用户，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            try {
              let {title,content,htmlContent} = this.bulletinBoardMessage
              await reqReleaseBulletinBoardMessage({title,content,htmlContent})
              this.bulletinBoardMessage = {
                title:'',
                content:'',
                htmlContent:'',
              }
            } catch (e) {
              console.log(e.message)
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发布'
            });
          });
        } else {
          console.log('表单有误')
        }
      })
    },
    change(value, render){
      // render 为 markdown 解析后的结果
      this.bulletinBoardMessage.htmlContent = render;
    },
  },
}
</script>

<style scoped>
.editor{
  width: 1000px;
  margin: auto;
}
</style>
