<template>
  <div class="myPage">
    <el-tabs v-model="activeName" type="height: 200px;" @tab-click="handleClick" tab-position="right">
      <el-tab-pane label="修改密码" name="first">
        <div class="temp"></div>
        <div class="myStyle">
          <div class="panel panel-default">
            <div class="panel-body">
              <!--修改密码表单开始-->
              <el-form :model="passwordFrom" :rules="rules" ref="passwordFrom" label-width="100px" size="small">
                <el-form-item :inline="true" prop="oldPassword" label="原密码:">
                  <el-col :span="17">
                    <el-input maxlength = "20" type="password" placeholder="请输入原密码" v-model="passwordFrom.oldPassword"></el-input>
                  </el-col>
                </el-form-item>
                <el-form-item :inline="true" prop="newPassword" label="新密码:">
                  <el-col :span="17">
                    <el-input maxlength = "20" type="password" placeholder="请输入新密码" v-model="passwordFrom.newPassword"></el-input>
                  </el-col>
                </el-form-item>
                <el-form-item :inline="true" prop="secondPassword" label="确认密码:">
                  <el-col :span="17">
                    <el-input maxlength = "20" type="password" placeholder="请再次输入新密码" v-model="passwordFrom.secondPassword"></el-input>
                  </el-col>
                  <el-col :span="7">{{ messageerror.secondPassword }}</el-col>
                </el-form-item>
                <el-form-item prop="agree">
                  <el-button @click.prevent="submit('passwordFrom')">确认修改</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="修改绑定" name="second">
        <div class="temp"></div>
        <div class="myStyle">
          <el-steps :active="changeBind.activeProcess" finish-status="success">
            <el-step title="步骤 1"></el-step>
            <el-step title="步骤 2"></el-step>
            <el-step title="步骤 3"></el-step>
          </el-steps>
          <el-form label-width="100px" size="small">
            <el-form-item>
              <div v-if="changeBind.activeProcess === 0">
                <p>当前邮箱:{{changeBind.curEmail}}</p>
                <el-form-item :inline="true" label="验证码:">
                  <el-col :span="17">
                    <el-input maxlength = "6" type="text" placeholder="请输入验证码" v-model="changeBind.code1"></el-input>
                  </el-col>
                  <el-col :span="7">
                    <el-button type="info" plain size="small" @click.prevent="sendCode_1" v-if="can_sendCode_1"
                               :disabled="changeBind.disabled1">{{changeBind.buttonName_1}}</el-button>
                    <el-button type="info" plain size="small" v-else
                               :disabled="changeBind.disabled1">{{changeBind.buttonName_1}}</el-button>
                  </el-col>
                </el-form-item>
                <el-button @click="next1" v-if="can_next1">{{changeBind.buttonName}}</el-button>
                <el-button v-else>{{changeBind.buttonName}}</el-button>
              </div>
              <div v-if="changeBind.activeProcess === 1">
                <el-form-item :inline="true" label="邮箱:">
                  <el-col :span="17">
                    <el-input maxlength = "20" type="text" placeholder="请输入绑定邮箱" v-model="changeBind.afterEmail"></el-input>
                  </el-col>
                </el-form-item>
                <el-button @click="next2" v-if="can_next2">{{changeBind.buttonName}}</el-button>
                <el-button v-else>{{changeBind.buttonName}}</el-button>
              </div>
              <div v-if="changeBind.activeProcess === 2">
                <el-form-item :inline="true" label="验证码:">
                  <el-col :span="17">
                    <el-input maxlength = "6" type="text" placeholder="请输入获取的验证码" v-model="changeBind.code2"></el-input>
                  </el-col>
                </el-form-item>
                <el-button @click="next3" v-if="can_next3">{{ changeBind.buttonName }}</el-button>
                <el-button v-else>{{ changeBind.buttonName }}</el-button>
              </div>

            </el-form-item>
          </el-form>

        </div>

      </el-tab-pane>
      <el-tab-pane label="个人资料" name="third" @click="getUserInfoForm">
        <div class="temp2"></div>
        <div class="myStyle2">
          <el-form :model="userInfoForm" :rules="userInfoFormRules" ref="userInfoForm" label-width="100px" size="mini">
            <el-form-item :inline="true" prop="username" label="用户名:">
              <el-col :span="17">
                <p>{{userInfoForm.username}}</p>
              </el-col>
            </el-form-item>
            <el-form-item :inline="true" prop="phone" label="电话号码:">
              <el-col :span="17">
                <el-input maxlength = "20" type="text" placeholder="请输入电话号码" v-model="userInfoForm.phone"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item :inline="true" prop="qq" label="QQ号码:">
              <el-col :span="17">
                <el-input maxlength = "20" type="text" placeholder="请输入QQ号码" v-model="userInfoForm.qq"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item :inline="true" prop="message" label="个人信息:">
              <el-col :span="17">
                <el-input
                    type="textarea"
                    resize="none"
                    :autosize="{ minRows: 7, maxRows: 7}"
                    placeholder="请输入内容"
                    v-model="userInfoForm.message"
                    maxlength="80"
                    show-word-limit
                >
                </el-input>
              </el-col>
            </el-form-item>
            <el-form-item :inline="true" prop="gender" label="性别:">
              <el-col :span="17">
                <el-radio v-model="userInfoForm.gender" :label="1" name="gender">男</el-radio>
                <el-radio v-model="userInfoForm.gender" :label="0" name="gender">女</el-radio>
              </el-col>
              <el-col :span="7">{{ messageerror.secondPassword }}</el-col>
            </el-form-item>
            <el-form-item prop="agree">
              <el-button @click.prevent="submit2('userInfoForm')">提交</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="上传头像" name="fourth">
        <div class="temp3"></div>
        <div class="myStyle3">
          <el-form :model="passwordFrom" :rules="rules" ref="passwordFrom" label-width="100px" size="small">
            <el-form-item label="当前头像">
              <img v-if="imageUrl" :src="imageUrl" class="avatar" alt="" @click="dialogVisible = true">
              <el-button v-else type="text" @click="dialogVisible = true">上传头像</el-button>
              <p>（点击图片更改）</p>
              <el-dialog
                  title="头像设置"
                  :visible.sync="dialogVisible"
                  width="30%"
                  :before-close="handleClose">
                <vueCropper
                    style="width:100%;height:300px"
                    ref="cropper"
                    :img="options.img"
                    :autoCrop="options.autoCrop"
                    :fixedBox="options.fixedBox"
                    :canMoveBox="options.canMoveBox"
                    :autoCropWidth="options.autoCropWidth"
                    :autoCropHeight="options.autoCropHeight"
                    :centerBox="options.centerBox"
                >
                </vueCropper>
                <span slot="footer" class="dialog-footer">
              <el-button type="primary" size="small" slot="right">
                <label class="btn" for="uploads">选择图片</label>
                <input type="file" id="uploads" style="position:absolute; clip:rect(0 0 0 0);"
                       accept="image/png, image/jpeg, image/gif, image/jpg" @change="selectImg($event)">
              </el-button>
              <el-button type="primary" size="small" @click="cut('blob')">上传<i class="el-icon-upload el-icon--right"></i></el-button>
              <el-button type="primary" size="small" @click="handleClose">取消</el-button>
          </span>
              </el-dialog>
            </el-form-item>
          </el-form>
        </div>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import {Message} from "element-ui";
import throttle from "lodash/throttle";
import axios from "axios";

export default {
  name: "userInfo",
  data() {
    let validatePass = (rule, value, callback) => {
      if (value !== this.passwordFrom.newPassword) {
        callback(new Error('两次输入密码不一致'));
      }else {
        callback();
      }
    };
    return {
      //点击能否触发该方法
      can_sendCode_1:true,
      can_next1:true,
      can_next2:true,
      can_next3:true,
      //头像裁剪
      dialogVisible:false,
      options:{
        img:this.$store.state.user.userDate.avatar,
        autoCrop:true,  //默认生成截图框
        fixedBox:true,  //固定截图框大小
        canMoveBox:false,    //截图框不能拖动
        autoCropWidth:200,  //截图框宽度
        autoCropHeight:200, //截图框高度
        centerBox:true,    //截图框被限制在图片里面
      },

      uploadAvatarUrl:'/api/users/change_avatar',
      imageUrl:this.$store.state.user.userDate.avatar,
      activeName: 'fourth',
      userDate:this.$store.state.user.userDate,
      passwordFrom:{
        oldPassword:'',
        newPassword:'',
        secondPassword:'',
      },
      changeBind:{
        activeProcess:0,
        buttonName:'下一步',
        curEmail:this.$store.state.user.userDate.email,
        buttonName_1:'获取验证码',
        code1:'',
        afterEmail:'',
        code2:'',
        disabled1:false,
        disabled2:false,
      },
      userInfoForm:{
        username:this.$store.state.user.userDate.username,
        phone:this.$store.state.user.userDate.phone,
        qq:this.$store.state.user.userDate.qq,
        message:this.$store.state.user.userDate.message,
        gender:this.$store.state.user.userDate.gender,
      },
      messageerror:{
        secondPassword:'',
      },
      rules: {
        oldPassword: [
          {required: true, message: '原密码不能为空', trigger: 'blur'},
        ],
        newPassword: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
        ],
        secondPassword: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'},
        ],
      },
      userInfoFormRules:{
        gender: [
          {required: true, message: '请选择性别', trigger: 'blur'},
        ],
      },
    };
  },
  methods: {
    //(修改绑定)获取步骤1中的验证码
    sendCode_1(){
      this.can_sendCode_1 = false
      try {
        this.changeBind.disabled1 = true
        this.$store.dispatch('sendCodeToUnbindEmail_1')
        if (!this.$store.state.user.sendCodeToRegisterEmail) {
          this.buttonName_1.codeButtonValue = this.$store.state.user.codeRemainTime_unbind_1 + '秒后可再次获取'
        }
      } catch (e) {
        console.log(e.message)
      }
    },
    //(修改绑定)提交验证码，进入步骤2
    async next1(){
      if (this.changeBind.code1 === ''){
        await this.$alert('验证码为空提示', '提示');
        return
      }
      this.can_next1 = false
      try {
        await this.$store.dispatch('checkCode_UnbindEmail_1',this.changeBind.code1)
        this.changeBind.activeProcess = 1
      }catch (e) {
        this.can_next1 = true
        console.log(e.message)
      }
    },
    //(修改绑定)获取新邮箱验证码
    async next2(){
      this.can_next2 = false
      try {
        await this.$store.dispatch('sendCodeToUnbindEmail_2',this.changeBind.afterEmail)
        this.changeBind.activeProcess = 2
        this.changeBind.buttonName = '完成'
      }catch (e) {
        this.can_next2 = true
        console.log(e.message)
      }
    },
    //(修改绑定)提交验证码，完成修改绑定邮箱
    async next3(){
      if (this.changeBind.code2 === ''){
        await this.$alert('验证码为空', '提示');
        return
      }
      this.can_next3 = false
      try {
        await this.$store.dispatch('checkCode_UnbindEmail_2',this.changeBind.code2)
        this.$cookies.remove('access_token')
        this.$store.commit("CLEAR")
        await this.$router.push("/login");
      }catch (e) {
        this.can_next3 = true
        console.log(e.message)
      }
    },
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    //获取userInfoForm的数据
    getUserInfoForm(){

    },
    //(修改密码)提交密码修改表单
    submit(form){
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const {oldPassword, newPassword} = this.passwordFrom
          try {
            await this.$store.dispatch('updatePassword', {oldPassword, newPassword})
            this.$cookies.remove('access_token')
            this.$store.commit("CLEAR")
            await this.$router.push("/login");
          } catch (e) {
            console.log(e.message)
          }
        } else {
          console.log('表单有误')
        }
      })
    },
    //(修改个人资料)提交个人信息表单
    submit2(form){
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const {username, phone, qq, message, gender} = this.userInfoForm
          let result = await this.$store.dispatch('updateUserInfo', {username,phone,qq,message,gender})
          if (result === 4010){
            await this.$router.push('/login')
          }
          try {
          } catch (e) {
            console.log(e.name)
          }
        } else {
          console.log('表单有误')
        }
      })
    },
    //选择封面图片
    selectImg (e) {
      let file = e.target.files[0]
      if (!/\.(jpg|jpeg|png|JPG|PNG)$/.test(e.target.value)) {
        this.$message({
          message: '图片类型要求：jpeg、jpg、png',
          type: "error"
        });
        return false
      }
      //转化为blob
      let reader = new FileReader()
      reader.onload = (e) => {
        let data
        if (typeof e.target.result === 'object') {
          data = window.URL.createObjectURL(new Blob([e.target.result]))
        } else {
          data = e.target.result
        }
        this.options.img = data
      }
      //转化为base64
      reader.readAsDataURL(file)
    },
    //确认截图,上传
    cut(){
      this.dialogVisible=false
      let formData = new FormData();
      this.$refs.cropper.getCropBlob(res=>{
        //res是裁剪后图片的bolb对象
        formData.append("file",res,'myAvatar.jpg');
        axios.post(this.uploadAvatarUrl,formData,
            {contentType: false, processData: false, headers:{'Content-Type': 'multipart/form-data'}}
        ).then(res=>{
          this.$store.dispatch('getUserInfo')
          this.imageUrl = res.data.data
          if (res.data.state === 200){
            Message({
              message: res.data.message,
              type: 'success'
            });
          }else {
            Message({
              message: res.data.message,
              type: 'warning'
            });
          }
        })
      })
    },
    //控制弹出层关闭
    handleClose(){
      this.dialogVisible=false
    },
  },
  watch: {
    //监听store中的 codeRemainTime_unbind_1
    '$store.state.user.codeRemainTime_unbind_1': {
      deep: true,
      handler: function () {
        let remainTime = this.$store.state.user.codeRemainTime_unbind_1
        if (remainTime <= 0) {
          this.changeBind.buttonName_1 = '获取验证码'
          this.$store.state.user.sendCodeToUnbindEmail_1 = true
        } else {
          this.changeBind.buttonName_1 = remainTime + '秒后可再次获取'
          this.changeBind.disabled1 = false;
        }
      }
    },
  },
  mounted() {
  },
};
</script>


<style scoped>
.myPage {
  width: 100%;
  height: 500px;
  margin: 0 auto;
}
.temp {
  width: 600px;
  height: 120px;
  margin: 0 auto;
}
.myStyle {
  width: 600px;
  height: 330px;
  /*border: 1px solid rgb(223, 223, 223);*/
  margin: 0 auto;
}
.temp2 {
  width: 600px;
  height: 20px;
  margin: 0 auto;
}
.myStyle2 {
  width: 600px;
  height: 430px;
  /*border: 1px solid rgb(223, 223, 223);*/
  margin: 0 auto;
}
.temp3 {
  width: 600px;
  height: 70px;
  margin: 0 auto;
}
.myStyle3 {
  width: 600px;
  height: 380px;
  /*border: 1px solid rgb(223, 223, 223);*/
  margin: 0 auto;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  /*width: 178px;*/
  height: 240px;
  display: block;
}

</style>
