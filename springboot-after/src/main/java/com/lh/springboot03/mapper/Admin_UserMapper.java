package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.springboot03.bean.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface Admin_UserMapper extends BaseMapper<User> {
    @Select("SELECT COUNT(*) FROM t_user WHERE DATEDIFF(created_time,DATE_ADD(CURDATE(),INTERVAL - DAY(CURDATE())+1 DAY)) < DAYOFMONTH(NOW()) AND DATEDIFF(created_time,DATE_ADD(CURDATE(),INTERVAL - DAY(CURDATE())+1 DAY)) + 1 > 0;")
    Integer getCurrentMouthRegisterUser();
}
