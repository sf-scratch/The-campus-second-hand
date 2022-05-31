<template>
  <div class="register-container">
    <div class="register">
      <h3>找回密码
        <span class="go">我有账号，去<router-link to="/login">登录</router-link>
        </span>
      </h3>
      <div class="temp"></div>
      <div class="myRegister">
        <el-steps :active="findPassword.activeProcess" finish-status="success" align-center>
          <el-step title="确认邮箱"></el-step>
          <el-step title="重置密码"></el-step>
          <el-step title="完成"></el-step>
        </el-steps>
        <div v-if="findPassword.activeProcess === 0"
             style="width: 350px; margin: auto; padding-top: 70px">
          <el-input v-model="forgetPasswordForm.email" placeholder="请输入绑定的邮箱"></el-input>
          <button class="btn" @click="next1">确&nbsp;&nbsp;认</button>
        </div>
        <div v-else-if="findPassword.activeProcess === 1" style="width: 400px; margin: auto; padding-top: 20px">
          <el-form :model="forgetPasswordForm" :rules="forgetPasswordFormRules"
                   ref="forgetPasswordForm" label-width="100px" size="small">
            <el-form-item prop="newPassword_1" label="新密码:">
              <el-input maxlength = "16" type="password" v-model="forgetPasswordForm.newPassword_1"
                        placeholder="请输入你的新密码"></el-input>
            </el-form-item>
            <el-form-item prop="newPassword_2" label="确认密码:">
              <el-input maxlength = "16" type="password" v-model="forgetPasswordForm.newPassword_2"
                        placeholder="请输入确认密码"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱:">
<!--              <el-input maxlength = "30" type="text" v-model="forgetPasswordForm.email"-->
<!--                        placeholder="请输入邮箱"></el-input>-->
              <a>{{forgetPasswordForm.email}}</a>
            </el-form-item>
            <el-form-item prop="code" label="验证码:">
              <el-col :span="12">
                <el-input type="text" v-model="forgetPasswordForm.code" :maxlength="6" placeholder="请输入验证码"></el-input>
              </el-col>
              <el-col :span="7">
                <el-button type="info" plain size="small" v-if="findPassword.canClick" @click="sendVerify">
                  {{ findPassword.codeButtonValue }}</el-button>
                <el-button type="info" plain size="small" v-else>{{ findPassword.codeButtonValue }}</el-button>
              </el-col>
            </el-form-item>
            <button class="btn2" @click="next2">确&nbsp;&nbsp;认</button>
          </el-form>
        </div>
        <div v-else>
          <div style="width: 200px; margin: auto;padding-top: 60px">
            <h2 style="color: #67c23a"><i class="el-icon-success"></i>密码重置成功！</h2>
          </div>
          <div style="width: 260px; margin: auto;padding-top: 20px">
            <a>请牢记设置的新密码</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="success" @click="toLogin">去登录</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {reqFindPasswordByEmail, reqFindPassword} from '@/api'
import {Message} from "element-ui";
export default {
  name: "ForgetPassword",
  data(){
    return{
      findPassword:{
        activeProcess:0,
        remainTime:-1,
        codeButtonValue:'获取验证码',
        canClick:true,
      },
      forgetPasswordForm:{
        email:'',
        newPassword_1:'',
        newPassword_2:'',
        code:'',
      },
      forgetPasswordFormRules:{
        newPassword_1: [
          {required: true, message: '新密码不能为空', trigger: 'blur'},
        ],
        newPassword_2: [
          {required: true, message: '确认密码不能为空', trigger: 'blur'},
        ],
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
        ],
        code: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ],
      },
    }
  },
  methods:{
    //确定邮箱
    async next1(){
      let email = this.forgetPasswordForm.email
      if (email === ''){
        await this.$alert('输入的邮箱为空', '提示');
        return
      }
      let verify = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
      if (!verify.test(email)) {
        await this.$alert('邮箱格式错误, 请重新输入', '提示');
        return
      }
      try {
        this.findPassword.activeProcess = 1
      }catch (e) {
        console.log(e.message)
      }
    },
    //提交表单
    next2(){
      this.$refs['forgetPasswordForm'].validate(async (valid) => {
        if (valid) {
          try {
            let result = await reqFindPassword({
              'email':this.forgetPasswordForm.email,
              'password':this.forgetPasswordForm.newPassword_2,
              'code':this.forgetPasswordForm.code,
            })
            console.log(result)
            if (result.state === 200){
              Message({
                message: result.message,
                type: 'success'
              });
              this.findPassword.activeProcess = 3
            }else {
              Message({
                message: result.message,
                type: 'warning'
              });
            }
          } catch (e) {
            console.log(e.message)
          }
        } else {
          console.log('表单有误')
        }
      })
    },
    //获取验证码
    async sendVerify(){
      this.findPassword.canClick = false
      let result = await reqFindPasswordByEmail({'registerEmail':this.forgetPasswordForm.email})
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
      }
      this.findPassword.remainTime = result.data
      this.openTimer()
    },
    //定时器
    openTimer(){
      setTimeout(() => {
        this.findPassword.remainTime--
        if (this.findPassword.remainTime >= 0) {
          this.findPassword.codeButtonValue = this.findPassword.remainTime + '秒后可再次获取'
          this.openTimer() ;
        }else {
          this.findPassword.codeButtonValue = '获取验证码'
          this.findPassword.canClick = true
        }
      }, 1000);
    },
    toLogin(){
      this.$router.push("/login")
    },
  },
}
</script>

<style scoped lang="less">
.temp {
  width: 600px;
  height: 50px;
  margin: 0 auto;
}

.myRegister {
  width: 600px;
  height: 445px;
//border: 1px solid rgb(223, 223, 223);
  margin: 0 auto;
  .btn {
    background-color: #4cb9fc;
    padding: 6px;
    border-radius: 0;
    font-size: 16px;
    font-family: 微软雅黑,serif;
    word-spacing: 4px;
    border: 1px solid #4cb9fc;
    color: #fff;
    width: 100%;
    height: 36px;
    margin-top: 25px;
    outline: none;
  }
  .btn2 {
    background-color: #4cb9fc;
    padding: 6px;
    border-radius: 0;
    font-size: 16px;
    font-family: 微软雅黑,serif;
    word-spacing: 4px;
    border: 1px solid #4cb9fc;
    color: #fff;
    width: 100%;
    height: 36px;
    margin-top: 0;
    outline: none;
  }
}

.copyright {
  width: 1200px;
  margin: 0 auto;
  text-align: center;
  line-height: 24px;

ul {
li {
  display: inline-block;
  border-right: 1px solid #e4e4e4;
  padding: 0 20px;
  margin: 15px 0;
}
}
}

.register-container {
  width: 100%;
.register {
  width: 1200px;
  height: 445px;
  border: 1px solid rgb(223, 223, 223);
  margin: 0 auto;

h3 {
  background: #ececec;
  margin: 0;
  padding: 6px 15px;
  color: #333;
  border-bottom: 1px solid #dfdfdf;
  font-size: 20.04px;
  line-height: 30.06px;

span {
  font-size: 14px;
  float: right;

a {
  color: #1b92e1;
}
}
}
}
}
</style>
