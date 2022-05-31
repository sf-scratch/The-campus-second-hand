<template>
    <div class="login">
      <div class="content">
        <header class="logo center">
          <img alt
               src="../../assets/images/login-logo.png" />
        </header>
        <div class="form-con">
            <Form :rules="rules" ref="loginForm" :model="loginForm">
              <FormItem prop="name">
                <Input placeholder="请输入用户名" v-model="loginForm.name"></Input>
              </FormItem>
              <FormItem prop="password">
                <Input placeholder="请输入密码" type="password" v-model="loginForm.password" v-on:keydown.enter.native="submitLogin('loginForm')"></Input>
              </FormItem>
              <!-- <FormItem prop="code">
                <Input class="code-input" placeholder="请输入验证码" v-model="formData.code"></Input>
                <img :src="codeUrl" @click="verificationCode" class="codeUrl" v-if="codeUrl" />
              </FormItem> -->
              <FormItem class="remember">
                <Checkbox>记住密码</Checkbox>
              </FormItem>
              <FormItem>
                <Button long type="primary" @click="submitLogin('loginForm')">登录</Button>
              </FormItem>
               <div class="other-way">
<!--                <p class="inline" style="float:left">其他方式登陆(账号：demo/123456)</p>-->
                <div class="inline align" style="float:right">
                  <img alt class="marginLeft" src="@/assets/images/login-taobao.png" />
                  <img alt class="marginLeft" src="@/assets/images/login-alipay.png" />
                  <img alt class="marginLeft" src="@/assets/images/login-sina.png" />
                </div>
              </div>
            </Form>
        </div>
        <footer class="center footerDesc">二手交易系统后台管理</footer>
      </div>
    </div>
</template>

<script>
import store from "@/store";

let socket
export default {
  name: "login",
  data(){
    return {
      loginForm:{
        name:'',
        password:'',
      },
      rules:{
        name:[{required:true,message:'请输入账号',trigger:'blur'}],
        password:[{required:true,message:'请输入密码',trigger:'blur'}],
      },
    }
  },
  methods:{
    submitLogin(form) {
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          let {name, password} = this.loginForm
          try {
            let result = await this.$store.dispatch('AdminLogin', {name, password})
            await store.dispatch('getUserActiveInfo')
            await this.connectWebSocket(result.data)
            await this.$router.push("/home")
          }catch (e){
            console.log(e.message)
          }
        } else {
          console.log('error submit!!');
        }
      });
    },
    connectWebSocket(userDate){
      let id = userDate.id;
      let _this = this
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        console.log("您的浏览器支持WebSocket");
        let socketUrl = "ws://120.76.136.205:8888/websocketAdmin/" + id;
        if (socket != null) {
          socket.close();
          socket = null;
        }
        //开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        //浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = async function (msg) {
          let webSocketInstruct = JSON.parse(msg.data)
          console.log("收到数据====",webSocketInstruct)
          if (webSocketInstruct.instruct === 2){
            await store.commit('USERACTIVE',webSocketInstruct.data)
          }else if (webSocketInstruct.instruct === 4){

          }
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误");
        }
      }
    },
  },
}
</script>

<style lang="less">
.center {
  text-align: center;
}

.login {
  font-family: Arial, "PingFang SC", "Microsoft YaHei";
  width: 100%;
  height: 100%;
  background: url(../../assets/images/login-bg.jpg) no-repeat fixed;
  background-size: cover;

  .content {
    width: 424px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -56%)
  }
  .ivu-card {
    background: rgba(255, 255, 255, 0.95);
    border-radius: 10px;
    box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
  }
  .form-con {
    margin: 32px 0;
    width: 424px;
    background: #fff;
    padding: 32px 32px;
    border-radius: 8px;
    .ivu-input {
      border: 1px solid #E8E8EE;
      border-radius: 4px;
      font-size: 16px;
      color: #525252;
      padding: 0 20px;
      &:focus {
        border: 1px solid #0097F6;
        box-shadow: none;
      }
    }
    .ivu-form-item-error-tip {
      height: 30px;
      line-height: 30px;
      padding: 0;
      color: #f66;
      top:90%;
    }
    .ivu-form-item {
      margin-bottom: 22px;
    }
    .remember {
      margin: -10px 0 10px;
    }
    .ivu-input,
    .ivu-btn {
      height: 48px;
    }
    .ivu-btn {
      font-size: 16px;
    }
    .ivu-input-group-prepend {
      padding: 4px 15px;
    }
    .code-input {
      width: 172px;
    }
    .codeUrl {
      height: 80%;
      position: absolute;
      z-index: 3;
      top: 0;
      bottom: 0;
      margin: auto;
      right: 24px;
      border-radius: 0 4px 4px 0;
      cursor: pointer;
    }
  }
  .login-tip {
    font-size: 10px;
    text-align: center;
    color: #c3c3c3;
  }
  .footerDesc {
    font-family: "MicrosoftYaHei";
    color: #A6A6A8;
    font-size: 14px;
  }
  .otherWay {
    font-size: 14px;
    font-family: "Microsoft YaHei";
    .inline {
      display: inline-block;
    }
    .align {
      vertical-align: middle;
    }
    .marginLeft {
      margin-left: 20px;
      float:right;
    }
  }
  .remember {
    font-size: 14px;
  }
}
.draw {
  position: fixed;
  width: 1px;
  z-index: 99999;
  line-height: 1px;
  pointer-events: none;
}

@keyframes floatOne {
  0% {
    opacity: 1;
  }

  50% {
    opacity: 1;
  }

  100% {
    opacity: 0;
    transform: translate3D(0, -20px, 0) scale(5) rotate(45deg);
  }
}
</style>
