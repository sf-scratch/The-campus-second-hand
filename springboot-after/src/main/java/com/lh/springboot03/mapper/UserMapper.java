package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.springboot03.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserMapper extends BaseMapper<User> {
//    插入user 用户的数据，返回受影响的行数
//    Integer insert(User user);

//    根据用户名查询用户数据
    User findByUsername(String username);

//    根据uid查询用户数据
    User findByUid(Integer uid);

    //查询邮箱是否已注册
    Integer verifyEmail(String email);

//    修改密码
    Integer updatePasswordByUid(@Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime,
                                @Param("uid") Integer uid);

//    上传头像
    Integer updateAvatarByUid(@Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime,
                              @Param("uid") Integer uid);

//    修改个人信息
    Integer updateInfoByUsername(@Param("username") String username,
                                 @Param("phone") String phone,
                                 @Param("qq") String qq,
                                 @Param("message") String message,
                                 @Param("gender") Integer gender,
                                 @Param("modifiedTime") Date modifiedTime);

//    修改modifiedUser和modifiedTime（用于验证码冷却）
    Integer updateModified(@Param("modifiedUser") String modifiedUser,
                           @Param("modifiedTime") Date modifiedTime,
                           @Param("uid") Integer uid
                           );

    Integer updateEmailByUid(@Param("uid") Integer uid,
                             @Param("email") String email);


}
