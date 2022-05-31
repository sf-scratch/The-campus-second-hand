//对API接口进行统一管理

import requests from "./request";

//发请求:axios发请求返回结果Promise对象

//给注册邮箱发送验证码
export const reqSendCodeToRegisterEmail = (email)=>{
    return requests({url:'/emails/registerByEmail', params:{'registerEmail':email}, method:'post'})
}

//验证用户名是否可用
export const reqCheckUsername = (username)=>{
    return requests({url:'/users/verifyUsername', params:{'username':username}, method:'post'})
}

//注册用户
export const reqRegisterUser = (userData)=>{
    console.log('registerUser:',userData)
    return requests({url:'/users/register', data:userData, method:'post'})
}

//根据cookie中的access_token 获取用户信息
export const reqGetUserInfo = ()=>{
    return requests({url:'/users/getUserInfo',method:'get'})
}

//请求登录
export const reqLogin = (data)=>{
    console.log(data)
    return requests({url:'/users/login', data, method:'post'})
}

//退出登录
export const reqLogout = ()=>{
    return requests({url:'/users/logout',method:'get'})
}

//修改密码
export const reqUpdatePassword = (data)=>{
    return requests({url:'/users/update', data, method:'post'})
}

//修改个人信息
export const reqUpdateUserInfo = (data)=>{
    return requests({url:'/users/change_info', data, method:'post'})
}

//修改绑定邮箱1（向原始邮箱发送验证码）
export const reqUpdateEmailBind_1 = ()=>{
    return requests({url:'/emails/waitTwoMin', params:{"onlyQuery":-2}, method:'get'})
}

//验证原始邮箱接收的验证码
export const reqUpdateEmailBind_1_checkCode = (code)=>{
    return requests({url:'/users/change_email_1', params:{"verificationCode":code}, method:'get'})
}

//修改绑定邮箱2（向指定邮箱发送验证码）
export const reqUpdateEmailBind_2 = (email)=>{
    return requests({url:'/emails/waitTwoMin', params:{"onlyQuery":-3,"newEmail":email}, method:'get'})
}

//验证指定邮箱接收的验证码
export const reqUpdateEmailBind_2_checkCode = (code)=>{
    return requests({url:'/users/change_email_2', params:{"verifyCodeToChange":code}, method:'get'})
}

//找回密码，向指定邮箱发送验证码
export const reqFindPasswordByEmail = (params)=>{
    return requests({url:'/emails/findPasswordByEmail', params, method:'get'})
}

//找回密码，提交表单
export const reqFindPassword = (data)=>{
    return requests({url:'/users/findPassword', data, method:'post'})
}

//发布商品
export const reqReleaseGoods = (params)=>{
    return requests({url:'/goods/releaseGoods', data:params, method:'post'})
}

//用户更新已发布的商品信息
export const reqUpdateReleaseGoods = (params)=>{
    return requests({url:'/goods/updateReleaseGoods', data:params, method:'post'})
}

//更改收藏状态
export const reqChangeCollectionStatus = (params)=>{
    return requests({url:'/goods/changeCollectionStatus', params:params, method:'post'})
}

//取消收藏
export const reqCancelCollection = (params)=>{
    return requests({url:'/goods/cancelCollection', params:params, method:'post'})
}

//获取用户收藏列表
export const reqGetCollectList = ()=>{
    return requests({url:'/goods/getCollectList', method:'get'})
}

//获取用户持有的所有商品
export const reqGetAllHoldGoods = ()=>{
    return requests({url:'/goods/getAllHoldGoods', method:'get'})
}

//用户取消发布商品
export const reqCancelReleaseGoods = (params)=>{
    return requests({url:'/goods/cancelReleaseGoods', params:params, method:'post'})
}

//用户确认商品已售出
export const reqConfirmSoldGoods = (params)=>{
    return requests({url:'/goods/confirmSoldGoods', params:params, method:'post'})
}

//根据商品id查询对应商品信息
export const reqSelectGoodsByGoodsIdSimple = (params)=>{
    return requests({url:'/goods/selectGoodsByGoodsId', params:params, method:'post'})
}

//用户评论商品
export const reqSendComment = (params)=>{
    return requests({url:'/comment/sendComment', params:params, method:'post'})
}

//用户回复评论
export const reqReplyComment = (data)=>{
    return requests({url:'/comment/replyComment', data:data, method:'post'})
}

//用户回复评论
export const reqGetUnreadMessageNum = ()=>{
    return requests({url:'/comment/getUnreadMessageNum', method:'get'})
}

//获取回复该用户的消息
export const reqGetReplyMessageByUid = ()=>{
    return requests({url:'/comment/getReplyMessageByUid', method:'get'})
}

//获取该用户拥有商品的评论
export const reqGetOwnGoodsComment = ()=>{
    return requests({url:'/comment/getOwnGoodsComment', method:'get'})
}

//获取系统通知列表
export const reqSystemMessageList = ()=>{
    return requests({url:'/systemMessage/getSystemMessageList', method:'get'})
}

//发布求购信息
export const reqReleasePurchaseInfo = (data)=>{
    return requests({url:'/purchaseInfo/releasePurchaseInfo', data:data, method:'post'})
}

//分页获取求购信息
export const reqGetPurchaseInfoList = (params)=>{
    return requests({url:'/purchaseInfo/getPurchaseInfoList', params:params, method:'post'})
}

//获取商品警告列表
export const reqGetGoodsWarnList = ()=>{
    return requests({url:'/goods/getGoodsWarnList', method:'get'})
}

//根据商品id删除对应商品
export const reqDeleteGoodsByGoodsId = (params)=>{
    return requests({url:'/goods/deleteGoodsByGoodsId', params:params, method:'post'})
}

//========================================以下为无需登录即可访问的接口========================================

//获取商品列表
export const reqGetCategoryList = ()=>{
    return requests({url:'/goods/home/getGoodsCategoryList', method:'get'})
}

//获取分页数据
export const reqGetGoodsInfoToShow = (params)=>{
    return requests({url:'/goods/home/getGoodsInfoByPage', params:params, method:'post'})
}

//根据信息查询对应商品
export const reqSearchGoodsInfoList = (params)=>{
    return requests({url:'/goods/home/searchGoodsInfoList', params:params, method:'post'})
}

//根据信息查询对应商品
export const reqSelectGoodsByGoodsId = (params)=>{
    return requests({url:'/goods/home/selectGoodsByGoodsId', params:params, method:'post'})
}

//获取商品评论
export const reqGetGoodsComment = (params)=>{
    return requests({url:'/comment/home/getGoodsComment', params:params, method:'post'})
}


