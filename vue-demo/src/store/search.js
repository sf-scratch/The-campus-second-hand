import {reqGetGoodsInfoToShow, reqSearchGoodsInfoList} from '@/api'
import {Message} from "element-ui";


export default{
    state: {
        goodsInfo:{},
    },
    mutations: {
        //将搜索结果放入仓库
        SEARCHGOODSINFO(state,goodsInfo){
            state.goodsInfo = goodsInfo
        },
        //清除goodsInfo的数据
        CLEARGOODSINFO(state){
            state.goodsInfo = {}
        }
    },
    actions: {
        //查询对应商品
        async searchGoodsInfoList({commit},params){
            let result = await reqSearchGoodsInfoList(params)
            if (result.state === 200){
                commit('SEARCHGOODSINFO',result.data)
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
    },
    getters:{

    },
}
