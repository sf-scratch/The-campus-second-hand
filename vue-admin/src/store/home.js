import {reqGetUserActiveInfo} from '@/api'

export default{
    state: {
        userActiveInfo:{},
    },
    mutations: {
        //更新用户活跃信息
        USERACTIVE(state,userActiveInfo){
            state.userActiveInfo = userActiveInfo
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
        //获取
        async getUserActiveInfo({commit}){
            let result = await reqGetUserActiveInfo();
            commit('USERACTIVE',result.data)
            return result
        },
    },
    getters:{

    },
}
