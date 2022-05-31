package com.lh.springboot03.controller;

import com.lh.springboot03.controller.ex.*;
import com.lh.springboot03.service.exception.*;
import com.lh.springboot03.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class BaseController {
    public static final int OK = 200;//操作成功状态码
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000);//用户名已经被占用
            result.setMessage(e.getMessage());
        } else if (e instanceof LoginExpirationException) {
            result.setState(4010);//登录已过期，请重新登录
            result.setMessage(e.getMessage());
        } else if (e instanceof InsertException){
            result.setState(5000);//请获取验证码/验证码错误
            result.setMessage(e.getMessage());
        } else if (e instanceof UserNotFoundException){
            result.setState(4001);//用户不存在
            result.setMessage(e.getMessage());
        } else if (e instanceof PasswordNotMatchException){
            result.setState(4002);//密码错误/新密码与旧密码相同/原密码错误
            result.setMessage(e.getMessage());
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);//文件为空
        } else if (e instanceof FileSizeException) {
            result.setState(6001);//文件超出限制,最大不超过13MB
        } else if (e instanceof FileTypeException) {
            result.setState(6002);//文件类型不支持
        } else if (e instanceof FileStateException) {
            result.setState(6003);//文件状态异常
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);//文件读写异常
        } else if (e instanceof UpdateException) {
            result.setState(6005);//更新用户头像产生未知异常
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateInfoException) {
            result.setState(6006);//更新用户信息产生未知异常/更新用户邮箱产生未知异常
            result.setMessage(e.getMessage());
        } else if (e instanceof EmailException) {
            result.setState(6007);//邮箱格式错误/邮箱信息错误
            result.setMessage(e.getMessage());
        } else if (e instanceof EmailDuplicatedException) {
            result.setState(6008);//该邮箱已被占用
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateModifiedException) {
            result.setState(6020);//验证信息预处理异常
            result.setMessage(e.getMessage());
        } else if (e instanceof VerifyCodeNotFindException) {
            result.setState(6030);//请获取验证码
            result.setMessage(e.getMessage());
        } else if (e instanceof GoodsReleaseException) {
            result.setState(7001);//商品发布失败
            result.setMessage(e.getMessage());
        } else if (e instanceof CancelCollectionException) {
            result.setState(7002);//频繁操作（取消收藏）
            result.setMessage(e.getMessage());
        } else if (e instanceof CancelReleaseGoodsException) {
            result.setState(7002);//重复操作（取消取消发布）
            result.setMessage(e.getMessage());
        } else if (e instanceof CategoryNotFoundException) {
            result.setState(7003);//未找到该分类信息/未找到该种类信息
            result.setMessage(e.getMessage());
        } else if (e instanceof InsertGoodsException) {
            result.setState(7004);//商品分类插入错误/未找到该种类信息
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateGoodsStatusException) {
            result.setState(7005);//商品已受审/商品状态修改错误
            result.setMessage(e.getMessage());
        } else if (e instanceof InsertGoodsWarnException) {
            result.setState(7006);//商品警告名称重复/商品警告插入异常
            result.setMessage(e.getMessage());
        } else if (e instanceof DeleteGoodsWarnException) {
            result.setState(7007);//商品警告删除异常
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateGoodsWarnException) {
            result.setState(7008);//商品警告名称重复/商品警告修改异常
            result.setMessage(e.getMessage());
        } else if (e instanceof DeleteGoodsException) {
            result.setState(7009);//商品删除异常/商品删除异常(非持有者)
            result.setMessage(e.getMessage());
        } else if (e instanceof DeleteGoodsCategoryException) {
            result.setState(7010);//商品分类："+categoryId+"删除异常(一级分类)
            result.setMessage(e.getMessage());
        } else if (e instanceof DeleteGoodsTypeException) {
            result.setState(7011);//商品种类："+typeId+"删除异常(二级分类)
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateIsDeleteException) {
            result.setState(8001);//更改用户是否删除异常
            result.setMessage(e.getMessage());
        } else if (e instanceof DeletePurchaseInfoException) {
            result.setState(9001);//求购信息删除异常
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdatePurchaseInfoStatusException) {
            result.setState(9002);//求购信息状态修改异常
            result.setMessage(e.getMessage());
        }
        return result;
    }

//    获取session 的uid
//    protected final Integer getuidFromSession(HttpSession session){
//        return Integer.valueOf(session.getAttribute("uid").toString());
//    }

//    获取session 的 username
//    protected final String getUsernameFromSession(HttpSession session){
//        return session.getAttribute("username").toString();
//    }
}
