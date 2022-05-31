package com.lh.springboot03.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.bean_admin.UserActiveInfo;
import com.lh.springboot03.service.Admin_UserService;
import com.lh.springboot03.service.UserService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*", methods = {})
@RestController
@RequestMapping("/admin/users")
public class Admin_UserController extends BaseController {
    @Autowired
    Admin_UserService admin_userService;
    @Autowired
    UserService userService;


    @RequestMapping("/login")
    public JsonResult<Admin> login(@RequestBody Admin admin, HttpServletResponse response) {
        JsonResult<Admin> result = new JsonResult<>();
        String access_token = UUID.randomUUID().toString();
        Admin login = admin_userService.login(admin.getName(), admin.getPassword(), access_token);
//        登录成功后，cookie中存入数据
        Cookie cookie = new Cookie("access_token_admin", access_token);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("access_token_admin" + cookie.getName() + "--" + cookie.getValue() + "--" + cookie.getComment());
        result.setState(OK);
        result.setMessage("登录成功");
        result.setData(login);
        return result;
    }

    @RequestMapping("/logout")
    public JsonResult<Void> logout(HttpServletRequest request) {
        JsonResult<Void> result = new JsonResult<>();
        String token = TokenUtil.getAdminTokenFromRequest(request);
        Admin admin = TokenUtil.getAdminFromRequest(request);
        admin_userService.logout(token,admin.getId());
        result.setState(OK);
        result.setMessage("成功退出登录");
        return result;
    }

    @RequestMapping("/getUserInfo")
    public JsonResult<Admin> getUserInfo(HttpServletRequest request) {
        JsonResult<Admin> result = new JsonResult<>();
        Admin admin = TokenUtil.getAdminFromRequest(request);
        result.setData(admin);
        result.setMessage("管理员信息成功获取");
        result.setState(200);
        return result;
    }

    @RequestMapping("/getUserList")
    public JsonResult<List<User>> getUserList(HttpServletRequest request) {
        JsonResult<List<User>> result = new JsonResult<>();
        List<User> userList = admin_userService.getUserList();
        result.setData(userList);
        result.setState(OK);
        result.setMessage("用户列表获取成功");
        return result;
    }

    @RequestMapping("/getUserListByPage")
    public JsonResult<Page<User>> getUserListByPage(@RequestParam(name="currentPage") Integer currentPage,
                                                    @RequestParam(name="size") Integer size,
                                                    @RequestParam(required=false, name="keyword") String keyword) {
        JsonResult<Page<User>> result = new JsonResult<>();
        Page<User> userPage = admin_userService.searchUserListByPage(currentPage, size, keyword);
        result.setData(userPage);
        result.setState(OK);
        result.setMessage("用户分页列表获取成功");
        return result;
    }

    @RequestMapping("/updateIsDelete")
    public JsonResult<Void> updateIsDelete(@RequestParam("uid") Integer uid,
                                           @RequestParam("isDelete") Integer isDelete,
                                           HttpServletRequest request) {
        JsonResult<Void> result = new JsonResult<>();
        Admin admin = TokenUtil.getAdminFromRequest(request);
        admin_userService.updateIsDelete(uid, isDelete, admin.getName());
        result.setState(OK);
        result.setMessage("更改用户是否删除成功");
        return result;
    }

    @RequestMapping("/selectUserByUid")
    public JsonResult<User> selectUserByUid(@RequestParam("uid") Integer uid){
        JsonResult<User> result = new JsonResult<>();
        User user = userService.selectUserByUid(uid);
        result.setData(user);
        result.setMessage("用户信息获取成功");
        result.setState(OK);
        return result;
    }


    @RequestMapping("/updateUserInfo")
    public JsonResult<User> updateUserInfo(@RequestBody User user,HttpServletRequest request){
        JsonResult<User> result = new JsonResult<>();
        Admin admin = TokenUtil.getAdminFromRequest(request);
        //设置更新的user（属性置空表示，将不进行修改）
        user.setModifiedUser("admin_"+admin.getName());
        user.setModifiedTime(new Date());
        user.setCreatedTime(null);
        user.setCreatedUser(null);
        user.setUsername(null);
        user.setPassword(null);
        user.setSalt(null);
        admin_userService.updateUserInfo(user);
        result.setData(user);
        result.setMessage("用户信息更新成功");
        result.setState(OK);
        return result;
    }

    @RequestMapping("/deleteUserByUid")
    public JsonResult<Void> deleteUserByUid(@Param("uid") Integer uid){
        JsonResult<Void> result = new JsonResult<>();
        admin_userService.deleteUserByUid(uid);
        result.setMessage("该用户已永久删除");
        result.setState(OK);
        return result;
    }

    @RequestMapping("/getUserActiveInfo")
    public JsonResult<UserActiveInfo> getUserActiveInfo(){
        JsonResult<UserActiveInfo> result = new JsonResult<>();
        UserActiveInfo userActiveInfo = admin_userService.getUserActiveInfo();
        result.setData(userActiveInfo);
        result.setMessage("用户活跃信息获取成功");
        result.setState(OK);
        return result;
    }


}
