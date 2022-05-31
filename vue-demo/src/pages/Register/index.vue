<template>
  <div class="register-container">
    <!-- 注册内容 -->
    <div class="register">
      <h3>注册新用户
        <span class="go">我有账号，去<router-link to="/login">登录</router-link>
        </span>
      </h3>
      <div class="temp"></div>
      <div class="myRegister">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" size="small">
          <el-form-item :inline="true" prop="username" label="用户名:">
            <el-col :span="17">
              <el-input maxlength = "13" type="text" v-model="loginForm.username" placeholder="请输入你的用户名"
                        @blur="checkUsername"></el-input>
            </el-col>
            <el-col :span="7">
              <i class="el-icon-circle-check" v-show="show1"></i>
              <i class="el-icon-circle-close" v-show="show2">{{ messageerror.username }}</i>
            </el-col>
          </el-form-item>
          <el-form-item prop="email" label="邮箱:">
            <el-col :span="17">
              <el-input maxlength = "20" type="text" v-model="loginForm.email" placeholder="请输入你的邮箱"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item prop="code" label="验证码:">
            <el-col :span="12">
              <el-input type="text" v-model="loginForm.code" :maxlength="6" placeholder="请输入验证码"></el-input>
            </el-col>
            <el-col :span="7">
              <el-button type="info" plain size="small" @click.prevent="sendCode"
                         :disabled="disabled">{{ loginForm.codeButtonValue }}</el-button>
            </el-col>
          </el-form-item>
          <el-form-item prop="firPassword" label="登录密码:">
            <el-col :span="17">
              <el-input maxlength = "20" type="password" v-model="loginForm.firPassword" placeholder="请输入你的登录密码"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item prop="secPassword" label="确认密码:">
            <el-col :span="17">
              <el-input maxlength = "20" type="password" v-model="loginForm.secPassword" placeholder="请输入确认密码"
                        @blur="checkPassword"></el-input>
            </el-col>
            <el-col :span="7">{{ messageerror.secPassword }}</el-col>
          </el-form-item>
          <el-form-item prop="agree">
            <el-checkbox value="dddd" v-model="loginForm.agree"></el-checkbox>
            <span>同意协议并注册《用户协议》</span>
            <el-button @click.prevent="submit('loginForm')">完成注册</el-button>
          </el-form-item>
        </el-form>
      </div>

    </div>

    <!-- 底部 -->
    <div class="copyright">
      <ul>
        <li>关于我们</li>
        <li>联系我们</li>
        <li>联系客服</li>
        <li>商家入驻</li>
      </ul>
    </div>
  </div>
</template>

<script>

import { debounce } from "lodash";
import {mapState} from 'vuex'
import throttle from "lodash/throttle";
import {Message} from "element-ui";

export default {
  name: 'register',
  data() {
    let validatePass = (rule, value, callback) => {
      if (value !== this.loginForm.firPassword) {
        callback(new Error('两次输入密码不一致'));
      }else {
        callback();
      }
    };
    return {
      //点击能否触发该方法
      can_sendCode:true,
      show1: false,
      show2: false,
      disabled:false,
      loginForm: {
        username: '',
        email: '',
        code: '',
        firPassword: '',
        secPassword: '',
        agree: [],
        codeButtonValue: '获取验证码',
      },
      messageerror: {
        username: '',
        email: '',
        code: '',
        firPassword: '',
        secPassword: '',
        codeButtonValue: '',
        agree: '',
      },
      rules: {
        username: [
          {required: true, message: '账户不能为空', trigger: 'blur'},
        ],
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/,
            required: true,
            message: '邮箱格式错误',
            trigger: 'blur'
          },
        ],
        code: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ],
        firPassword: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 6, max: 20, message: '密码长度6-20字符', trigger: 'blur'},
        ],
        secPassword: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'},
        ],
        agree: [
          {required: true, message: '不同意无法注册', trigger: 'blur'},
        ],
      },
    }
  },
  methods: {
    async sendCode() {
      try {
        this.disabled = true
        await this.$store.dispatch('sendCodeToRegisterEmail', this.loginForm.email)
      } catch (e) {
        this.disabled = false
        console.log(e.message)
      }
      if (!this.$store.state.user.sendCodeToRegisterEmail) {
        this.loginForm.codeButtonValue = this.$store.state.user.codeRemainTime + '秒后可再次获取'
      }
    },
    //提交
    submit(form) {
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const {username, 'firPassword': password, 'code': verifyCodeToRegister} = this.loginForm
          try {
            await this.$store.dispatch('registerUser', {username, password, verifyCodeToRegister})
            await this.$router.replace("/login");
          } catch (e) {
            console.log(e.message)
          }
        } else {
          console.log('表单有误')
        }
      })
    },
    //检查用户名是否重复
    async checkUsername() {
      if (this.loginForm.username === '') return
      try {
        await this.$store.dispatch('verifyUsername', this.loginForm.username)
        this.show1 = true;
        this.show2 = false
      } catch (e) {
        this.messageerror.username = e.message
        this.show1 = false;
        this.show2 = true
      }
    },
    //检查两次输入密码是否一致
    checkPassword() {
      if (this.loginForm.firPassword === this.loginForm.secPassword) {
        this.messageerror.secPassword = ''
      } else {
        this.messageerror.secPassword = '密码不一致'
      }
    },
  },
  computed: {
    // ...mapState({
    //   codeRemainTime:(state)=>{
    //     return state.user.codeRemainTime
    //   },
    //   sendCodeToRegisterEmail:(state)=>{
    //     return state.user.sendCodeToRegisterEmail
    //   },
    // }),
    // codeRemainTime(){
    //   return this.$store.state.user.codeRemainTime
    // },
    // sendCodeToRegisterEmail(){
    //   return this.$store.state.user.sendCodeToRegisterEmail
    // },
  },
  watch: {
    '$store.state.user.codeRemainTime': {
      deep: true,
      handler: function () {
        let remainTime = this.$store.state.user.codeRemainTime
        if (remainTime <= 0) {
          this.loginForm.codeButtonValue = '获取验证码'
          this.$store.state.user.sendCodeToRegisterEmail = true
          this.disabled = false
        } else {
          this.loginForm.codeButtonValue = remainTime + '秒后可再次获取'
        }
      }

    },
  },
}
</script>

<style lang="less" scoped>
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
