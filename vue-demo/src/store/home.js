import {reqGetCategoryList, reqGetPurchaseInfoList} from '@/api'

export default{
    state: {
        categoryList:[],
        dynamicTags:['起','飞'],
        purchaseInfoList:[],
        defaultCurrentPage:1,
        defaultSize:8,
    },
    mutations: {
        CATEGORYLIST(state,categoryList){
            state.categoryList = categoryList
        },
        PURCHASEINFOLIST(state,purchaseInfoList){
            state.purchaseInfoList = purchaseInfoList
        },
    },
    actions: {
        //通过API里的接口函数调用，向服务器发请求，获取服务器数据
        async getCategoryList({commit}){
            let result = await reqGetCategoryList();
            if (result.state === 200){
                await commit("CATEGORYLIST",result.data)
            }
        },
        async getPurchaseInfoList({commit},params){
            let result = await reqGetPurchaseInfoList(params)
            if (result.state === 200){
                await commit("PURCHASEINFOLIST",result.data.records)
            }
            return result
        },
    },
    getters:{

    },
}
