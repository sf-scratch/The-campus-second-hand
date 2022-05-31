<template>
  <div>
    <el-form :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
      <h3 class="loginTitle">登录</h3>
      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item prop="code">
        <el-input type="text" auto-complete="false" v-model="loginForm.code" placeholder="更换验证码"></el-input>
<!--        <img :src="captchaUrl" alt="">-->

      </el-form-item>
      <el-checkbox v-model="checked">记住我</el-checkbox>
      <el-button type="primary" size="medium" round style="margin: -35px 0 0 620px; float:right;" @click="submitLogin()">登录</el-button>
    </el-form>
  </div>
</template>

<script>
import {postRequest} from "@/utils/api";

export default {
  name: "Login",
  data(){
    return{
      // captchaUrl:'',
      loginForm:{
        username:'admin',
        password:'123',
        code:'',
      },
      checked:true,
      rules:{
        username:[{required:true,message:'请输入用户名',trigger:'blur'}],
        password:[{required:true,message:'请输入密码',trigger:'blur'}],
        // code:[{required:true,message:'请输入验证码',trigger:'blur'}],
      }
    }
  },
  methods:{
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        console.log(this.loginForm.username,this.loginForm.password)
        if (valid) {
          postRequest('http://localhost:8888/users/login',{
            username:this.loginForm.username,
            password:this.loginForm.password
          }).then(resp=>{
            alert(JSON.parse(resp.data))
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.loginContainer{
  border-radius: 15px;
  background-clip: padding-box;
  margin: 188px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: white;
  border: 1px solid white;
  box-shadow: 0 0 25px olivedrab;
}
.loginTitle{
  margin: 0px auto 40px auto;
  text-align: center;
}
</style>