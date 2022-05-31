package com.lh.springboot03.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.bean.GoodsCategory;
import com.lh.springboot03.bean.GoodsDetail;
import com.lh.springboot03.bean.GoodsType;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;

import java.util.List;

public interface Admin_goodsService {
    //获取商品分类二级联动树型数据
    List<GoodsCategoryTree> getGoodsCategoryTree();

    //获取商品分类表中所有信息
    List<GoodsCategory> getCategoryListInGoodsCategory();

    /**
     * 获取商品分类信息
     * @param categoryId 分类id
     * @return 分类信息
     */
    GoodsCategory getGoodsCategoryInfoById(Integer categoryId);

    /**
     * 获取商品种类信息
     * @param typeId 种类id
     * @return 种类信息
     */
    GoodsType getGoodsTypeInfoById(Integer typeId);

    /**
     * 修改分类名称
     * @param categoryId 分类id
     * @param categoryName 分类名称
     */
    void updateGoodsCategoryName(Integer categoryId, String categoryName);

    /**
     * 修改种类名称
     * @param typeId 种类id
     * @param typeName 种类名称
     */
    void updateGoodsTypeName(Integer typeId, String typeName);

    /**
     * 添加一级分类
     * @param categoryName 分类名称
     */
    void addGoodsCategory(String categoryName);

    /**
     * 添加二级分类
     * @param typeName 种类名称（二级分类名称）
     * @param categoryId 分类id（一级分类id）
     */
    void addGoodsType(String typeName, Integer categoryId);

    /**
     * 查询一级分类下包含的所有二级分类
     * @param categoryId 分类id
     */
    Integer selectCategoryChild(Integer categoryId);

    /**
     * 删除分类id对应分类
     * @param categoryId 分类id（一级分类id）
     */
    void deleteGoodsCategory(Integer categoryId);

    /**
     * 删除种类id对应分类
     * @param typeId 分类id（二级分类id）
     */
    void deleteGoodsType(Integer typeId);

    /**
     * 根据信息查询已通过审核的商品，并分页处理
     * @param currentPage 当前页
     * @param size 每页数据量
     * @param keyword 关键字
     * @param categoryId 分类id
     * @param typeId 类型id
     * @param status 商品状态
     * @param ownerId 持有者id
     * @return 分页信息
     */
    Page<Goods> searchGoodsListByPage(Integer currentPage, Integer size, String keyword,
                                      Integer categoryId, Integer typeId, Integer status, Integer ownerId);


    void deleteGoodsById(Integer goodsId);


    /**
     * 获取等待审核的商品详细信息列表
     * @return 商品详细信息列表
     */
    List<GoodsDetail> getGoodsDetailByWaitCheck();

    /**
     * 根据id改变商品状态，通过
     * @param goodsId 商品id
     */
    void updateGoodsStatusToAdopt(Integer goodsId);

    /**
     * 根据id改变商品状态，拒绝
     * @param goodsId 商品id
     */
    void updateGoodsStatusToRefuse(Integer goodsId,String warnMessage);

    /**
     * 根据id改变商品状态为对应的值
     * @param goodsId 商品id
     * @param goodsStatus 商品状态
     */
    void updateGoodsStatusByGoodsId(Integer goodsId, Integer goodsStatus);

    void updateCommentInfoByGoods(Goods goods);
}
