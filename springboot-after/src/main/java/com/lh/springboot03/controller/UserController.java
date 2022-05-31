package com.lh.springboot03.controller;

import com.lh.springboot03.bean.User;
import com.lh.springboot03.controller.ex.*;
import com.lh.springboot03.service.EmailService;
import com.lh.springboot03.service.UserService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import com.lh.springboot03.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//解决跨域问题
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @RequestMapping("/aa")//vue的第一个连接
    public JsonResult<User> aa(HttpServletRequest request) {
        JsonResult<User> result = new JsonResult<>();
//        User user = userService.selectUserByUsername("彦祖");
//        result.setData(user);
//        System.out.println(request.getRequestURL());
//        System.out.println(request.getHeader("host"));
//        System.out.println(request.getRemoteHost());
        result.setMessage("成功访问");
        result.setState(200);
//        return result;

//        List<Comment> commentList = commentService.findCommentList();
//        for (Comment comment : commentList) {
//            System.out.println(comment);
//        }
//        Comment_obsolete comment=new Comment_obsolete();
//        comment.setContent("测试添加的数据");
//        comment.setPublishTime(new Date());
//        comment.setUserid("1001");
//        comment.setUsername("迷恋雷哥");
//        comment.setState("1");
//        comment.setLikeNum(0);
//        comment.setReplyNum(0);
//        comment.setParentId("0");
//        comment.setGoodsId("100000");
//        commentService.saveComment(comment);
        return result;
    }


    @RequestMapping("/getUserInfo")
    public JsonResult<User> getUserInfo(HttpServletRequest request) {
        JsonResult<User> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        result.setData(user);
        result.setMessage("成功获取");
        result.setState(200);
        return result;
    }

    @RequestMapping("/logout")
    public JsonResult<Void> logout(HttpServletRequest request) {
        JsonResult<Void> result = new JsonResult<>();
        String token = TokenUtil.getTokenFromRequest(request);
        User user = TokenUtil.getUserFromRequest(request);
        userService.logout(token,user.getUid());
        result.setState(OK);
        result.setMessage("成功退出登录");
        return result;
    }


    @RequestMapping("/register")
    public JsonResult<Void> register(@RequestBody Map<String, String> data, HttpServletRequest request) {
        User user = new User();
        user.setUsername(data.get("username"));
        user.setPassword(data.get("password"));
        JsonResult<Void> result = new JsonResult<>();
        String registerEmail = TokenUtil.getCookieFromRequest(request, "registerEmail");
        if (registerEmail == null) {
            result.setState(6022);
            result.setMessage("验证码过期");
            return result;
        }
        //检查邮箱是否已注册
        userService.verifyEmail(registerEmail);
        userService.register(user, data.get("verifyCodeToRegister"), registerEmail);
        result.setState(OK);
        result.setMessage("用户注册成功");
        return result;
    }

    @RequestMapping("/verifyUsername")
    public JsonResult<Void> verifyUsername(String username) {
        JsonResult<Void> result = new JsonResult<>();
        userService.verifyUsername(username);
        result.setState(OK);
        return result;
    }

    @RequestMapping("/login")
    public JsonResult<User> login(@RequestBody User user, HttpServletResponse response) {
        JsonResult<User> result = new JsonResult<>();
        String access_token = UUID.randomUUID().toString();
        User login = userService.login(user.getUsername(), user.getPassword(), access_token);

//        登录成功后，cookie中存入数据
        Cookie cookie = new Cookie("access_token", access_token);
//        Cookie cookie2 = new Cookie("username", login.getUsername());
//        Cookie cookie3 = new Cookie("uid", login.getUid().toString());
        /*
         * 为cookie设置路径,让每个请求都携带这个cookie,
         * 原因:在访问子路径时，会包含其父路径的Cookie,而在访问父路径时，不包含子路径的Cookie.
         */
        cookie.setPath("/");
//        cookie2.setPath("/");cookie3.setPath("/");

        response.addCookie(cookie);
//        response.addCookie(cookie2);
//        response.addCookie(cookie3);
        System.out.println("access_token" + cookie.getName() + "--" + cookie.getValue() + "--" + cookie.getComment());
//        System.out.println("username"+ cookie2.getName() + "--"+cookie2.getValue() +"--"+ cookie2.getComment());
        result.setState(OK);
        result.setMessage("登录成功");
        result.setData(login);
        return result;
    }


    @RequestMapping("/update")
    public JsonResult<Void> update(@RequestBody Map<String, String> data, HttpServletRequest request) {
        String token = TokenUtil.getTokenFromRequest(request);
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        Integer uid = user.getUid();
        //修改密码
        userService.updatePassword(uid, oldPassword, newPassword);
        //修改密码成功，下线该用户
        userService.logout(token,user.getUid());
        result.setMessage("密码修改成功");
        result.setState(OK);
        return result;
    }

    @RequestMapping("/findPassword")
    public JsonResult<User> findPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");
        String code = data.get("code");
        JsonResult<User> result = new JsonResult<>();
        User user = userService.selectUserByEmail(email);
        String verifyCodeToFindPassword = TokenUtil.getVerifyCodeToFindPassword(user.getUid());
        //检查验证码是否正确
        JsonResult<User> checkResult = check_verificationCode(code, verifyCodeToFindPassword);
        if (checkResult.getState() != OK) {
            return checkResult;
        }

        //修改密码,清除redis中相关验证码信息
        userService.findPassword(user, password);

        result.setMessage("密码修改成功");
        result.setState(OK);
        return result;
    }


    /*上传文件最大值*/
    public static final int AVATAR_MAX_SIZE = 13 * 1024 * 1024;
    /*限制上传文件的类型*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    //接收用户上传的头像
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        //上传头像图片，成功则返回访问路径
        String avatar = UploadFileUtil.saveFile(file, "avatar");

        User user = TokenUtil.getUserFromRequest(request);
        Integer uid = user.getUid();
        String username = user.getUsername();
        userService.changeAvatar(uid, avatar, username);
        //完成头像修改后将redis中的数据更新
        userService.renewTokenValue(TokenUtil.getTokenFromRequest(request),uid);
        //返回头像路径
        return new JsonResult<>(OK, "头像上传成功", avatar);
    }

    //根据用户名返回用户信息
    @RequestMapping("/queryUserByUsername")
    public JsonResult<User> queryUserByUsername(HttpServletRequest request) {
        JsonResult<User> jsonResult = new JsonResult<>();
        String username = TokenUtil.getCookieFromRequest(request, "username");
        User user = userService.selectUserByUsername(username);
        jsonResult.setData(user);
        jsonResult.setState(OK);
        return jsonResult;
    }


    //更改用户基本信息
    @RequestMapping("/change_info")
    public JsonResult<User> change_info(@RequestBody User user, HttpServletRequest request) {
        JsonResult<User> jsonResult = new JsonResult<>();
        User redisUser = TokenUtil.getUserFromRequest(request);
        String token = TokenUtil.getTokenFromRequest(request);

        String username = user.getUsername();
        String redisUsername = redisUser.getUsername();
        Integer uid = redisUser.getUid();
        //目前不允许改名
        if (!username.equals(redisUsername)) {
            jsonResult.setState(6008);
            jsonResult.setMessage("非法更改用户名");
            return jsonResult;
        }
        userService.changeInfo(redisUsername, user.getPhone(), user.getQq(), user.getMessage(), user.getGender(), token, uid);
        jsonResult.setState(OK);
        jsonResult.setMessage("用户信息更改成功");
        user.setUsername(redisUsername);
        jsonResult.setData(user);
        return jsonResult;
    }


    //更改邮箱，验证旧邮箱
    @RequestMapping("/change_email_1")
    public JsonResult<User> change_email_1(@RequestParam("verificationCode") String verificationCode,
                                           HttpServletRequest request) {
        JsonResult<User> jsonResult = new JsonResult<>();
        User redisUser = TokenUtil.getUserFromRequest(request);
        String verifyCodeToChange = TokenUtil.getVerifyCodeToChange(redisUser.getUid());
//        检验验证码
        JsonResult<User> result = check_verificationCode(verificationCode, verifyCodeToChange);
        if (result.getState() != OK) {
            return result;
        }
        userService.changeExpireTime(verifyCodeToChange, 5);
        jsonResult.setState(OK);
        jsonResult.setMessage("邮箱验证成功");
        return jsonResult;
    }

    //更改邮箱，验证新邮箱
    @RequestMapping("/change_email_2")
    public JsonResult<User> change_email_2(@RequestParam("verifyCodeToChange") String verifyCodeToChange, HttpServletRequest request) {
        JsonResult<User> jsonResult = new JsonResult<>();
        String token = TokenUtil.getTokenFromRequest(request);
        User redisUser = TokenUtil.getUserFromRequest(request);
//        检验验证码
        JsonResult<User> result = check_verificationCode(verifyCodeToChange, TokenUtil.getVerifyCodeToChange2(redisUser.getUid()));
        if (result.getState() != OK) {
            return result;
        }
        //修改邮箱，并删除token，以及更改绑定邮箱相关验证码
        userService.changeEmail(redisUser.getUid(), token);
        jsonResult.setState(OK);
        jsonResult.setMessage("用户邮箱更改成功");
        return jsonResult;
    }

    //判断验证码是否正确
    @RequestMapping("/check_verificationCode")
    public JsonResult<User> check_verificationCode(String verificationCode, String tokenVerifyName) {
        JsonResult<User> jsonResult = new JsonResult<>();
        //从redis中拿出对应验证码的值
        String verificationCodeRight = emailService.getVerifyCodeValue(tokenVerifyName);
        if (!verificationCodeRight.toUpperCase().equals(verificationCode.toUpperCase())) {
            jsonResult.setState(6010);
            jsonResult.setMessage("验证码错误");
            return jsonResult;
        }
        jsonResult.setState(OK);
        jsonResult.setMessage("验证码正确");
        return jsonResult;
    }

}
