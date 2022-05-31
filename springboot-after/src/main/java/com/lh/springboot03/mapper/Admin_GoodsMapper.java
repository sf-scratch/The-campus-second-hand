package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.springboot03.bean.Goods;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface Admin_GoodsMapper extends BaseMapper<Goods> {
    @Select("SELECT COUNT(*) FROM goods WHERE DATEDIFF(create_date,DATE_ADD(CURDATE(),INTERVAL - DAY(CURDATE())+1 DAY)) < DAYOFMONTH(NOW()) AND DATEDIFF(create_date,DATE_ADD(CURDATE(),INTERVAL - DAY(CURDATE())+1 DAY)) + 1 > 0;")
    Integer getCurrentMouthReleaseGoodsSum();

    @Select("SELECT COUNT(*) FROM goods WHERE DATEDIFF(NOW(),create_date) = 0")
    Integer getCurrentDayReleaseGoodsSum();
}
