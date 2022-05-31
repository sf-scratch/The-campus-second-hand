import {
    reqLogin, reqGetUserInfo, reqLogout, reqSendCodeToRegisterEmail,
    reqCheckUsername, reqRegisterUser, reqUpdatePassword, reqUpdateUserInfo,
    reqUpdateEmailBind_1, reqUpdateEmailBind_1_checkCode, reqUpdateEmailBind_2,
    reqUpdateEmailBind_2_checkCode, reqGetUnreadMessageNum, reqGetReplyMessageByUid,
    reqGetOwnGoodsComment
} from "@/api";
import { Message, Alert} from "element-ui";

export default{
    state: {
        userDate:{},
        codeRemainTime:-1,
        sendCodeToRegisterEmail:true,
        codeRemainTime_unbind_1:-1,
        sendCodeToUnbindEmail_1:true,
        unreadMessageNumList:[0,0],
        websocket:{},
    },
    mutations: {
        //验证码剩余时间减一
        subCodeRemainTime(state){
            state.codeRemainTime--
        },
        //更新页面验证码剩余时间
        changeCodeRemainTime(state,codeRemainTime){
            state.codeRemainTime = codeRemainTime
            state.sendCodeToRegisterEmail = false
        },
        //验证码剩余时间减一
        subCodeRemainTime_unbind_1(state){
            state.codeRemainTime_unbind_1--
        },
        //更新页面验证码剩余时间
        changeCodeRemainTime_unbind_1(state,codeRemainTime_unbind_1){
            state.codeRemainTime_unbind_1 = codeRemainTime_unbind_1
            state.sendCodeToUnbindEmail_1 = false
        },
        //用户登录
        USERLOGIN(state,userLogin){
            state.userDate = userLogin.data
        },
        //获取用户信息
        GETUSERINFO(state,getUserInfo){
            state.userDate = getUserInfo.data
        },
        //获取用户未读消息数
        GETUNREADNUM(state,data){
            state.unreadMessageNumList = data
        },
        //将unreadMessageNumList中的index位置为0
        CLEARUNREADNUMList_INDEX(state,index){
            state.unreadMessageNumList.splice(index,index+1,0)
        },
        //websocket连接成功后，将WebSocket存入vuex
        SAVEWEBSOCKET(state,data){
            state.websocket = data
        },
        //清除本地数据
        CLEAR(state){
            state.userDate = {}
        },
    },
    actions: {
        //获取注册验证码
        async sendCodeToRegisterEmail(context,email){
            //判断能否发送邮件
            if (!context.state.sendCodeToRegisterEmail){
                Message({
                    message: '验证码冷却中',
                    type: 'warning',
                });
                return
            }
            //发送请求
            let result = await reqSendCodeToRegisterEmail(email)
            console.log(result)

            //开启定时器定时 更改codeRemainTime
            let aa = function (){
                setTimeout(() => {
                    context.commit('subCodeRemainTime',result.data)
                    if (context.state.codeRemainTime >= 0) {
                        aa() ;
                    }
                }, 1000);
            }
            aa()

            //对请求的state判断
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                context.commit('changeCodeRemainTime',result.data)
                return result
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                if (result.state !== 6021)
                return Promise.reject(new Error('getCodeFail'))
            }
        },
        //获取解绑验证码_1
        async sendCodeToUnbindEmail_1(context){
            //判断能否发送邮件
            if (!context.state.sendCodeToUnbindEmail_1){
                Message({
                    message: '验证码冷却中',
                    type: 'warning'
                });
                return
            }

            //发送请求
            let result = await reqUpdateEmailBind_1()
            console.log(result)
            console.log(result.data[0])
            context.commit('changeCodeRemainTime_unbind_1',result.data[0])

            //开启定时器定时 更改codeRemainTime
            let aa = function (){
                setTimeout(() => {
                    context.commit('subCodeRemainTime_unbind_1',result.data[0])
                    if (context.state.codeRemainTime_unbind_1 >= 0) {
                        aa() ;
                    }
                }, 1000);
            }
            aa()

            //对请求的state判断
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('getCodeFail_1'))
            }
        },
        //校验解绑验证码
        async checkCode_UnbindEmail_1(context,code){
            let result = await reqUpdateEmailBind_1_checkCode(code)
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
                return Promise.reject(new Error('checkCode_UnbindEmail_1_Fail'))
            }
        },
        //向指定邮箱发送验证码
        async sendCodeToUnbindEmail_2(context,email){
            let result = await reqUpdateEmailBind_2(email)
            console.log(result)

            //对请求的state判断
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('getCodeFail_2'))
            }
        },
        //校验绑定验证码
        async checkCode_UnbindEmail_2(context,code){
            let result = await reqUpdateEmailBind_2_checkCode(code)
            //对请求的state判断
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('checkCode_UnbindEmail_2_Fail'))
            }
        },
        //验证用户名是否可用
        async verifyUsername({commit},username){
            let result = await reqCheckUsername(username)
            if (result.state !== 200){
                return Promise.reject(new Error('用户名已占用'))
            }
        },
        //用户注册
        async registerUser({commit},userData){
            let result = await reqRegisterUser(userData)
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
                return Promise.reject(new Error('registerFail'))
            }
        },
        //用户登录
        async userLogin({commit},data){
            let result = await reqLogin(data)
            if (result.state === 200){
                commit('USERLOGIN',result)
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('loginFail'))
            }
        },
        //获取用户信息
        async getUserInfo({commit}){
            let result = await reqGetUserInfo()
            if (result.state === 200){
                commit('GETUSERINFO',result)
            }
            console.log('getUserInfo:',result)
            return result
        },
        //获取用户未读消息数
        async getUnreadMessageNum({commit}){
            let result = await reqGetUnreadMessageNum()
            if (result.state === 200){
                commit('GETUNREADNUM',result.data)
            }
            return result
        },
        //获取回复该用户的所有消息
        async getReplyMessage({commit}){
            let result = await reqGetReplyMessageByUid()
            if (result.state === 200){
                commit('CLEARUNREADNUMList_INDEX',0)
                Message({
                    message: result.message,
                    type: 'success'
                });
            }
            return result;
        },
        //获取回复该用户的所有消息
        async getOwnGoodsComment({commit}){
            let result = await reqGetOwnGoodsComment()
            if (result.state === 200){
                commit('CLEARUNREADNUMList_INDEX',1)
                Message({
                    message: result.message,
                    type: 'success'
                });
            }
            return result;
        },
        //用户注销登录
        async userLogout({commit}){
            let result = await reqLogout()
            if (result.state === 200){
                commit("CLEAR")
            }
            Message({
                message: result.message,
                type: 'warning'
            });
        },
        //修改密码
        async updatePassword({commit},passwordForm){
            let result = await reqUpdatePassword(passwordForm)
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
                return Promise.reject(new Error('updatePasswordFail'))
            }
        },
        //修改个人信息
        async updateUserInfo({dispatch,commit},data){
            let result = await reqUpdateUserInfo(data)
            if (result.state === 200){
                commit("GETUSERINFO",result)
                Message({
                    message: result.message,
                    type: 'success'
                });
            }else {
                if (result.state === 4010){
                    commit("CLEAR")
                    return 4010
                }
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('updateUserInfoFail'))

            }
        }
    },
    getters:{

    },
}
