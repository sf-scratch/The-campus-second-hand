import {reqReleaseGoods,reqGetGoodsInfoToShow,reqSelectGoodsByGoodsId,
    reqChangeCollectionStatus,reqGetCollectList,reqCancelCollection,reqGetAllHoldGoods,
    reqCancelReleaseGoods,reqSelectGoodsByGoodsIdSimple,reqUpdateReleaseGoods
} from '@/api'
import {Message} from "element-ui";

export default{
    state: {
        goodsInfo:[],
        selectGoodsInfo:{},
        showInfoInShowGoodsInfo:{},
    },
    mutations: {
        GETFGOODSINFO(state,goodsInfo){
            state.goodsInfo = goodsInfo
        },
        SETSELECTGOODSINFO(state,goodsInfo){
            state.selectGoodsInfo = goodsInfo
        },
        CHANGESHOWINFO(state,goodsInfo){
            state.showInfoInShowGoodsInfo = goodsInfo
        },
    },
    actions: {
        //发布商品
        async releaseGoods({commit},params){
            let result = await reqReleaseGoods(params)
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
                return Promise.reject(new Error('releaseGoodsFail'))
            }
        },
        //更新发布的商品
        async updateReleaseGoods({commit},params){
            console.log(params)
            let result = await reqUpdateReleaseGoods(params)
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
                return Promise.reject(new Error('updateReleaseGoodsFail'))
            }
        },
        //获取商品信息列表
        async getGoodsInfoToShow({commit},params){
            let result = await reqGetGoodsInfoToShow(params)
            if (result.state === 200){
                commit('GETFGOODSINFO',result.data)
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('releaseGoodsFail'))
            }
        },
        //根据id查询商品信息
        async selectGoodsByGoodsId({commit},params){
            let result = await reqSelectGoodsByGoodsId(params)
            commit('SETSELECTGOODSINFO',result.data)
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
                return Promise.reject(new Error('selectGoodsFail'))
            }
        },
        //更改收藏状态
        async changeCollectionStatus({commit},params){
            let result = await reqChangeCollectionStatus(params)
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
                return Promise.reject(new Error('changeCollectionStatusFail'))
            }
        },
        //取消收藏
        async cancelCollection({commit},params){
            let result = await reqCancelCollection(params)
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
        },
        //获取用户收藏列表
        async getCollectList({commit}){
            let result = await reqGetCollectList()
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result.data
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('getCollectListFail'))
            }
        },
        //获取持有商品列表
        async getAllHoldGoods(){
            let result = await reqGetAllHoldGoods()
            if (result.state === 200){
                Message({
                    message: result.message,
                    type: 'success'
                });
                return result.data
            }else {
                Message({
                    message: result.message,
                    type: 'warning'
                });
                return Promise.reject(new Error('getAllHoldGoodsFail'))
            }
        },
        //用户取消发布商品
        async cancelReleaseGoods({commit},params){
            let result = await reqCancelReleaseGoods(params)
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
                return Promise.reject(new Error('cancelReleaseGoodsFail'))
            }
        },
        //根据商品id查询信息
        async selectGoodsByGoodsIdSimple({commit},params){
            let result = await reqSelectGoodsByGoodsIdSimple(params)
            commit('CHANGESHOWINFO',result.data)
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
                return Promise.reject(new Error('selectGoodsByGoodsIdSimpleFail'))
            }
        },
    },
    getters:{

    },
}
