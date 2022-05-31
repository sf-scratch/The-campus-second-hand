package com.lh.springboot03.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.bean.GoodsCategoryList;
import com.lh.springboot03.bean.GoodsDetail;
import com.lh.springboot03.bean.GoodsWarn;

import java.util.List;

public interface GoodsService {
    //获取商品分类二级联动数据
    List<GoodsCategoryList> getGoodsCategoryList();

    //分页查询
    Page<Goods> getGoodsListByPage(int current, int size);

    //根据商品id获取信息
    Goods getGoodsById(Integer goodsId);

    //根据商品id获取商品详细信息
    GoodsDetail getGoodsDetailById(Integer goodsId);

    //根据商品id增加浏览数
    void addGoodsState(Integer goodsId);

    //用户发布商品
    void releaseGoods(Goods goods);

    //用户更改商品信息
    void updateReleaseGoods(Goods goods, Integer uid);

    /**
     * 根据信息查询已通过审核的商品，并分页处理
     * @param currentPage 当前页
     * @param size 每页数据量
     * @param keyword 关键字
     * @param categoryId 分类id
     * @param typeId 类型id
     * @return 分页信息
     */
    Page<Goods> searchGoodsListByPage(Integer currentPage, Integer size, String keyword,
                                      Integer categoryId, Integer typeId);

    //根据用户id获取收藏信息
    List<Goods> getCollectsByUid(Integer uid);

    //查询用户是否收藏该商品
    Boolean checkIsCollected(Integer uid, Integer goodsId);

    //用户 收藏/取消收藏 商品
    Integer changeCollectionStatus(Integer uid, Integer goodsId);

    //取消收藏（单独的取消收藏，防止频繁操作的影响）
    void cancelCollection(Integer uid, Integer goodsId);

    //根据用户id获取其持有商品
    List<Goods> getAllHoldGoodsByUid(Integer uid);

    //用户取消发布商品
    void cancelReleaseGoods(Integer uid, Integer goodsId);

    //用户确认已售出商品
    void confirmSoldGoods(Integer uid, Integer goodsId);

    //增加商品警告
    void addGoodsWarn(GoodsWarn goodsWarn);

    //获取商品警告列表
    List<GoodsWarn> getGoodsWarnList();

    //根据商品警告id获取警告信息
    GoodsWarn getGoodsWarnByWarnId(Integer warnId);

    //根据商品警告id删除警告信息
    void deleteGoodsWarnByWarnId(Integer warnId);

    //根据商品警告id更改警告信息
    void updateGoodsWarnByWarnId(GoodsWarn goodsWarn);

    //根据持有者id和商品id删除商品
    void deleteGoodsByOwnerIdAndId(Integer ownerId, Integer id);
}
