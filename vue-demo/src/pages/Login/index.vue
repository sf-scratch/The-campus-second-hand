<template>
  <div class="login-container">
    <!-- 登录 -->
    <div class="login-wrap">
      <div class="login">
        <div class="loginform">
          <ul class="tab clearFix">
<!--            <li>-->
<!--              <a href="javascript:void(0);" style="border-right: 0;">扫描登录</a>-->
<!--            </li>-->
            <li>
              <a href="#" class="current">账户登录</a>
            </li>
          </ul>

          <div class="content">
            <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0px" size="medium ">
              <div class="input-text clearFix">
<!--                <span></span>-->
                <el-form-item prop="username">
                  <el-col :span="2">
                    <i class="el-icon-user"></i>
                  </el-col>
                  <el-col :span="22">
                    <el-input maxlength = "20" type="text" placeholder="请输入用户名" v-model="loginForm.username"></el-input>
                  </el-col>
                </el-form-item>
              </div>
              <div class="input-text clearFix">
<!--                <span class="pwd"></span>-->
                <el-form-item prop="password">
                  <el-col :span="2">
                    <i class="el-icon-lock"></i>
                  </el-col>
                  <el-col :span="22">
                    <el-input maxlength = "20" type="password" placeholder="请输入密码" v-model="loginForm.password"></el-input>
                  </el-col>
                </el-form-item>
              </div>
              <div class="setting clearFix">
                <label class="checkbox inline">
                  <router-link to="/register">立即注册</router-link>
                </label>
                <router-link to="/forgetPassword"><span class="forget">忘记密码？</span></router-link>
              </div>
              <button class="btn" @click.prevent="openSlideVerify">登&nbsp;&nbsp;录</button>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <el-dialog
        title="登录验证" :visible.sync="dialogVisible" width="360px" center top="200px" @opened="onReset">
      <slide-verify
          :l="42"
          :r="10"
          :w="310"
          :h="155"
          :imgs="picArray"
          :show="false"
          slider-text="向右滑动完成验证"
          ref="slideverify"
          @success="onSuccess"
          @fail="onFail"
          @refresh="onRefresh"
      ></slide-verify>
    </el-dialog>

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
import { Message } from "element-ui";
import {mapState} from "vuex";
import store from "@/store";

let socket;
export default {
  name: 'login',
  data(){
    return {
      loginForm:{
        username:'',
        password:'',
      },
      rules:{
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
        ],
      },
      picArray:[
        require("@/pages/Login/images/1.jpg"),
        require("@/pages/Login/images/2.jpg"),
        require("@/pages/Login/images/3.jpg"),
        require("@/pages/Login/images/4.jpg"),
        require("@/pages/Login/images/5.jpg"),
      ],
      dialogVisible:false,
    }
  },
  computed:{
    ...mapState({
      userDate:(state)=>{
        return state.user.userDate
      },
    }),
  },
  created() {

  },
  methods:{
    onSuccess() {//往父级传递验证通过的函数
      console.log('成功,提交')
      this.userLogin()
    },
    onReset() {//重置图片验证组件
      console.log('onReset')
      this.$refs.slideverify.reset();
    },
    onFail() {
      console.log('onFail')
    },
    onRefresh() {
      console.log('onRefresh')
    },
    async connectWebSocket(){
      let uid = this.userDate.uid;
      let _this = this
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      }else {
        console.log("您的浏览器支持WebSocket");
        // let socketUrl = "ws://192.168.43.253:8888/websocket/" + uid;
        let socketUrl = "ws://120.76.136.205:8888/websocket/" + uid;
        if (socket != null) {
          socket.close();
          socket = null;
        }

        /**
         * 开启一个websocket服务
         */
        socket = new WebSocket(socketUrl);
        await store.commit('SAVEWEBSOCKET',socket)


        /**  以下为websocket的回调事件  * /


        /**
         *打开事件
         */
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        /**
         * 浏览器端收消息，获得从服务端发送过来的文本消息
         * @param msg 服务器端传的数据
         */
        socket.onmessage = async function (msg) {
          let webSocketInstruct = JSON.parse(msg.data)
          console.log("收到数据====",webSocketInstruct)
          let instruct = webSocketInstruct.instruct
          let data = webSocketInstruct.data
          if (instruct === 1){
            //用户有新未读消息：data存未读消息数
            await store.commit('GETUNREADNUM',data)
          }else if (instruct === 3){
            //系统发出系统消息：data存发出的消息数据
            if (data.contentType === 0){
              _this.$notify({
                title: webSocketInstruct.data.title,
                message: webSocketInstruct.data.content,
                duration: 0
              });
            }else {
              _this.$notify({
                title: data.title,
                dangerouslyUseHTMLString: true,
                message: data.htmlContent,
                duration: 0
              });
            }
          }
        };
        /**
         * 关闭事件
         */
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        /**
         * 发生了错误事件
         */
        socket.onerror = function () {
          console.log("websocket发生了错误");
        }
      }
    },
    openSlideVerify(){
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          this.dialogVisible = true
        } else {
          console.log('表单有误')
        }
      })
    },
    async userLogin(){
      try {
        const {username,password} = this.loginForm
        let result = await this.$store.dispatch('userLogin',{username,password})
        await Message({
          message: '欢迎您 ' +result.data.username,
          type: 'success'
        });
        this.dialogVisible = false
        await this.$router.push("/home")
        await this.connectWebSocket()
        await this.$store.dispatch('getUnreadMessageNum')
      }catch (e){
        console.log(e.message)
        this.dialogVisible = false
      }
    },
  },
  beforeDestroy() {
    this.dialogVisible = false
  }
}
</script>

<style lang="less" scoped>
ul, ol { list-style: none; }
.login-container {
  width: 100%;
  .login-wrap {
    height: 645px;
    //background-color: #e93854;
    background-image: url('./images/11.jpg');
    background-repeat:no-repeat ;
    background-size:100% 100%;
    background-attachment: fixed;

    .login {
      width: 1200px;
      height: 645px;
      margin: 0 auto;
      //background: url(./images/loginbg.png) no-repeat;
    }

    .loginform {
      width: 380px;
      height: 255px;
      box-sizing: border-box;
      background: #fff;
      float: right;
      top: 200px;
      position: relative;

      .tab {

        li {
          width: 100%;
          float: left;
          text-align: center;

          a {
            width: 100%;
            display: block;
            height: 15px;
            line-height: 50px;
            font-size: 20px;
            font-weight: 700;
            color: #333;
            border: 1px solid #ddd;
            box-sizing: border-box;
            text-decoration: none;

          }

          .current {
            border-bottom: none;
            border-top-color: #4cb9fc;
            color: #4cb9fc;
          }
        }
      }

      .content {
        width: 380px;
        height: 240px;
        box-sizing: border-box;
        border: 1px solid #ddd;
        border-top: none;
        padding: 18px;

        form {
          margin: 15px 0 18px 0;
          font-size: 12px;
          line-height: 18px;

          .input-text {
            //margin-bottom: 16px;

            span {
              float: left;
              width: 37px;
              height: 32px;
              border: 1px solid #ccc;
              //background: url(../../assets/images/icons.png) no-repeat -10px -201px;
              box-sizing: border-box;
              border-radius: 2px 0 0 2px;
            }

            .pwd {
              background-position: -72px -201px;
            }

            input {
              width: 302px;
              height: 32px;
              box-sizing: border-box;
              border: 1px solid #ccc;
              border-left: none;
              float: left;
              padding-top: 6px;
              padding-bottom: 6px;
              font-size: 14px;
              line-height: 22px;
              padding-right: 8px;
              padding-left: 8px;

              border-radius: 0 2px 2px 0;
              outline: none;
            }
          }

          .setting {
            label {
              float: left;
            }

            .forget {
              float: right;
            }
          }

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
        }

        .call {
          margin-top: 30px;

          ul {
            float: left;

            li {
              float: left;
              margin-right: 5px;
            }
          }

          .register {
            float: right;
            font-size: 15px;
            line-height: 38px;
          }

          .register:hover {
            color: #4cb9fc;
            text-decoration: underline;
          }
        }

      }
    }
  }

  .copyright {
    width: 1200px;
    margin: 0 auto;
    text-align: center;
    line-height: 20px;

    ul {
      li {
        display: inline-block;
        border-right: 1px solid #e4e4e4;
        padding: 0 20px;
        margin: 0;
        font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
        color: #333;
      }
    }
  }

}
</style>
