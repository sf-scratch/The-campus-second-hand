<template>
  <div class="myPage">
    <div class="editor">
      <el-form :model="purchaseInfo" :rules="purchaseInfoRules" ref="purchaseInfo" label-width="0px">
        <el-form-item prop="title">
          <p>标题</p>
          <el-input v-model="purchaseInfo.title" size="large"
                    placeholder="请输入标题" maxlength="50" show-word-limit></el-input>
        </el-form-item>
        <el-form-item prop="content">
          <p>内容</p>
          <mavon-editor v-model="purchaseInfo.content" @imgAdd="imgAdd" ref="md" @change="change"/>
        </el-form-item>
      </el-form>
      <el-button @click="submit()">提交</el-button>
    </div>
  </div>
</template>

<script>
import {reqReleasePurchaseInfo} from '@/api'
import {Message} from "element-ui";
export default {
  name: "releaseRequirementInfo",
  data(){
    return {
      purchaseInfo:{
        title:'',
        content:'',
      },
      purchaseInfoRules:{
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
        ],
      },
      htmlContent:'',
    }
  },
  methods:{
    imgAdd(){
      console.log('目前不支持上传')
    },
    async submit(){
      this.$refs['purchaseInfo'].validate(async (valid) => {
        if (valid) {
          this.$confirm('请确认是否给出了联系方式?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            try {
              let result = await reqReleasePurchaseInfo({
                'title':this.purchaseInfo.title,
                'content':this.purchaseInfo.content,
                'htmlContent':JSON.stringify(this.htmlContent)})
              if (result.state === 200){
                this.$message({
                  message: result.message,
                  type: 'success'
                });
                this.purchaseInfo.title = ''
                this.purchaseInfo.content = ''
                this.htmlContent = ''
              }else {
                Message({
                  message: result.message,
                  type: 'warning'
                });
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
      this.htmlContent = render;
    },
  },
}
</script>

<style scoped lang="less">
.myPage{
  width: 1515px;
  margin: auto;
}
.editor{
  width: 1000px;
  margin: auto;
}
</style>
