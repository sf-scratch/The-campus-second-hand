import {reqGetUserList, reqAdminLogin, reqGetUserInfo, reqAdminLogout} from '@/api'

export default{
    state: {
        userData:{},
    },
    mutations: {
        //用户登录成功
        USERLOGIN(state,userLogin){
            state.userData = userLogin
        },
        //清除本地数据
        CLEAR(state){
            state.userData = {}
        },
        //获取用户信息
        GETUSERINFO(state,getUserInfo){
            state.userData = getUserInfo
        },
    },
    actions: {
        //管理员登录
        async AdminLogin({commit}, admin){
            let result = await reqAdminLogin(admin);
            console.log(result)
            commit('USERLOGIN',result.data)
            return result
        },
        //管理员注销登录
        async adminLogout({commit}){
            await reqAdminLogout()
            await commit("CLEAR")
        },
        //获取用户信息
        async getUserInfo({commit}){
            let result = await reqGetUserInfo()
            if (result.state === 200){
                commit('GETUSERINFO',result.data)
            }
            console.log('getUserInfo:',result)
            return result
        },
        //获取用户列表
        async getUserList({commit}){
            let result = await reqGetUserList();
            if (result.state === 200){
                await commit("CATEGORYLIST",result.data)
            }
        }
    },
    getters:{

    },
}
