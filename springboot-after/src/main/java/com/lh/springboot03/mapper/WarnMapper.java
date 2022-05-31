package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.springboot03.bean.GoodsWarn;
import org.springframework.stereotype.Repository;

@Repository
public interface WarnMapper extends BaseMapper<GoodsWarn> {
}
