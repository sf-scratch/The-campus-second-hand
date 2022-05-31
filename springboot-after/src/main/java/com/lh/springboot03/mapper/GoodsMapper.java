package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.bean.GoodsCategoryList;
import com.lh.springboot03.bean.GoodsDetail;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    List<GoodsCategoryList> selectGoodsCategoryList();

    List<GoodsCategoryTree> selectGoodsCategoryTree();

    GoodsDetail getGoodsDetailById(@Param("id") Integer id);

    List<Goods> getCollectedList(@Param("uid") Integer uid);

//    List<Goods> getAllByOwnerId(@Param("uid") Integer uid);
    List<GoodsDetail> getGoodsDetailByWaitCheck();
}
