//对API接口进行统一管理

import requests from "./request";

//发请求:axios发请求返回结果Promise对象

//管理员登录
export const reqAdminLogin = (data)=>{
    console.log(data)
    return requests({url:'/admin/users/login', data:data, method:'post'})
}

//退出登录
export const reqAdminLogout = ()=>{
    return requests({url:'/admin/users/logout',method:'get'})
}

//根据cookie中的access_token 获取用户信息
export const reqGetUserInfo = ()=>{
    return requests({url:'/admin/users/getUserInfo',method:'get'})
}

//获取用户列表
export const reqGetUserList = ()=>{
    return requests({url:'/admin/users/getUserList', method:'get'})
}

//分页获取用户列表
export const reqGetUserListByPage = (params)=>{
    return requests({url:'/admin/users/getUserListByPage', params, method:'post'})
}

//更改用户是否删除状态
export const reqUpdateIsDelete = (params)=>{
    return requests({url:'/admin/users/updateIsDelete', params, method:'post'})
}

//通过uid查询用户信息
export const reqSelectUserByUid = (params)=>{
    return requests({url:'/admin/users/selectUserByUid', params, method:'post'})
}

//修改用户信息
export const reqUpdateUserInfo = (data)=>{
    return requests({url:'/admin/users/updateUserInfo', data, method:'post'})
}

//删除用户
export const reqDeleteUserByUid = (params)=>{
    return requests({url:'/admin/users/deleteUserByUid', params, method:'post'})
}

//获取分类数据列表
export const reqGetGoodsCategoryList = ()=>{
    return requests({url:'/goods/home/getGoodsCategoryList', method:'get'})
}

//获取分类树形数据
export const reqGetGoodsCategoryTree = ()=>{
    return requests({url:'/admin/goods/getGoodsCategoryTree', method:'get'})
}

//根据id获取分类信息
export const reqGetGoodsCategoryInfo = (params)=>{
    return requests({url:'/admin/goods/getGoodsCategoryInfo', params, method:'post'})
}

//根据id获取种类信息
export const reqGetGoodsTypeInfo = (params)=>{
    return requests({url:'/admin/goods/getGoodsTypeInfo', params, method:'post'})
}

//更改分类名称
export const reqUpdateGoodsCategoryName = (params)=>{
    return requests({url:'/admin/goods/updateGoodsCategoryName', params, method:'post'})
}

//更改种类名称
export const reqUpdateGoodsTypeName = (params)=>{
    return requests({url:'/admin/goods/updateGoodsTypeName', params, method:'post'})
}

//获取所有一级分类信息
export const reqGetCategoryListInGoodsCategory = ()=>{
    return requests({url:'/admin/goods/getCategoryListInGoodsCategory', method:'get'})
}

//增加商品分类(一级分类)
export const reqAddGoodsCategory = (params)=>{
    return requests({url:'/admin/goods/addGoodsCategory', params, method:'post'})
}

//增加商品种类(二级分类)
export const reqAddGoodsType = (params)=>{
    return requests({url:'/admin/goods/addGoodsType', params, method:'post'})
}

//删除商品分类
export const reqDeleteGoodsCategory = (params)=>{
    return requests({url:'/admin/goods/deleteGoodsCategory', params, method:'post'})
}

//删除商品种类
export const reqDeleteGoodsType = (params)=>{
    return requests({url:'/admin/goods/deleteGoodsType', params, method:'post'})
}

//根据参数搜索商品信息
export const reqSearchGoodsInfoList = (params)=>{
    return requests({url:'/admin/goods/searchGoodsInfoList', params, method:'post'})
}

//根据商品id删除对应商品信息
export const reqDeleteGoodsById = (params)=>{
    return requests({url:'/admin/goods/deleteGoodsById', params, method:'post'})
}

//获取待审核商品信息
export const reqGetGoodsDetailByWaitCheck = ()=>{
    return requests({url:'/admin/goods/getGoodsDetailByWaitCheck', method:'get'})
}

//管理员判断审核通过，更改商品信息
export const reqUpdateGoodsStatusToAdopt = (params)=>{
    return requests({url:'/admin/goods/updateGoodsStatusToAdopt', params, method:'post'})
}

//管理员判断拒绝发布，更改商品信息
export const reqUpdateGoodsStatusToRefuse = (params)=>{
    return requests({url:'/admin/goods/updateGoodsStatusToRefuse', params, method:'post'})
}

//修改商品状态
export const reqUpdateGoodsStatusByGoodsId = (params)=>{
    return requests({url:'/admin/goods/updateGoodsStatusByGoodsId', params, method:'post'})
}

//获取商品详细信息信息
export const reqSelectGoodsByGoodsId = (params)=>{
    return requests({url:'/admin/goods/selectGoodsByGoodsId', params:params, method:'post'})
}

//获取用户活跃信息
export const reqGetUserActiveInfo = ()=>{
    return requests({url:'/admin/users/getUserActiveInfo', method:'get'})
}

//添加商品警告
export const reqAddGoodsWarn = (data)=>{
    return requests({url:'/admin/goods/addGoodsWarn', data, method:'post'})
}

//获取商品警告列表
export const reqGetGoodsWarnList = ()=>{
    return requests({url:'/admin/goods/getGoodsWarnList', method:'get'})
}

//根据id获取商品警告信息
export const reqGetGoodsWarnByWarnId = (params)=>{
    return requests({url:'/admin/goods/getGoodsWarnByWarnId', params, method:'post'})
}

//根据id删除商品警告信息
export const reqDeleteGoodsWarnByWarnId = (params)=>{
    return requests({url:'/admin/goods/deleteGoodsWarnByWarnId', params, method:'post'})
}

//根据id修改商品警告信息
export const reqUpdateGoodsWarnByWarnId = (data)=>{
    return requests({url:'/admin/goods/updateGoodsWarnByWarnId', data, method:'post'})
}

//给在线用户发送通知
export const reqReleaseCommonMessage = (data)=>{
    return requests({url:'/admin/systemMessage/releaseCommonSystemMessage', data, method:'post'})
}

//给在线用户发送通知
export const reqReleaseBulletinBoardMessage = (data)=>{
    return requests({url:'/admin/systemMessage/releaseBulletinBoardMessage', data, method:'post'})
}

//分页获取求购信息
export const reqGetPurchaseInfoList = (params)=>{
    return requests({url:'/admin/purchaseInfo/getPurchaseInfoList', params:params, method:'post'})
}

//删除求购信息
export const reqDeletePurchaseInfoById = (params)=>{
    return requests({url:'/admin/purchaseInfo/deletePurchaseInfoById', params:params, method:'post'})
}

//删除求购信息
export const reqUpdatePurchaseInfoStatusById = (params)=>{
    return requests({url:'/admin/purchaseInfo/updatePurchaseInfoStatusById', params:params, method:'post'})
}

