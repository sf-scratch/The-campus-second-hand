package com.lh.springboot03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Function;
import com.lh.springboot03.bean.*;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;
import com.lh.springboot03.mapper.Admin_CategoryMapper;
import com.lh.springboot03.mapper.Admin_GoodsMapper;
import com.lh.springboot03.mapper.Admin_TypeMapper;
import com.lh.springboot03.mapper.GoodsMapper;
import com.lh.springboot03.repository.CommentRepository;
import com.lh.springboot03.service.Admin_goodsService;
import com.lh.springboot03.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class Admin_goodsServiceImpl implements Admin_goodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    Admin_CategoryMapper admin_categoryMapper;
    @Autowired
    Admin_TypeMapper admin_typeMapper;
    @Autowired
    Admin_GoodsMapper admin_goodsMapper;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<GoodsCategoryTree> getGoodsCategoryTree() {
        List<GoodsCategoryTree> goodsCategoryTrees = goodsMapper.selectGoodsCategoryTree();
        List<GoodsCategory> list = admin_categoryMapper.selectList(null);
        int size = list.size();
        if (goodsCategoryTrees.size() != size){
            HashSet<Integer> set = new HashSet<>();
            for (GoodsCategoryTree goodsCategoryTree : goodsCategoryTrees) {
                set.add(goodsCategoryTree.getId());
            }
            for (GoodsCategory goodsCategory : list) {
                if (!set.contains(goodsCategory.getCategoryId())){
                    GoodsCategoryTree goodsCategoryTree = new GoodsCategoryTree();
                    goodsCategoryTree.setId(goodsCategory.getCategoryId());
                    goodsCategoryTree.setName(goodsCategory.getCategoryName());
                    goodsCategoryTree.setCreateTime(goodsCategory.getCategoryCreate());
                    goodsCategoryTree.setUpdateTime(goodsCategory.getCategoryUpdate());
                    goodsCategoryTrees.add(goodsCategoryTree);
                }
            }
        }
        return goodsCategoryTrees;
    }

    @Override
    public List<GoodsCategory> getCategoryListInGoodsCategory() {
        return admin_categoryMapper.selectList(null);
    }

    @Override
    public GoodsCategory getGoodsCategoryInfoById(Integer CategoryId) {
        GoodsCategory goodsCategory = admin_categoryMapper.selectById(CategoryId);
        if (goodsCategory == null){
            throw new CategoryNotFoundException("未找到该分类信息");
        }
        return goodsCategory;
    }

    @Override
    public GoodsType getGoodsTypeInfoById(Integer typeId) {
        GoodsType goodsType = admin_typeMapper.selectById(typeId);
        if (goodsType == null){
            throw new CategoryNotFoundException("未找到该种类信息");
        }
        return goodsType;
    }

    @Override
    public void updateGoodsCategoryName(Integer categoryId, String categoryName) {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryId(categoryId);
        goodsCategory.setCategoryName(categoryName);
        goodsCategory.setCategoryUpdate(new Date());
        int update = admin_categoryMapper.updateById(goodsCategory);
        if (update != 1){
            throw new UpdateCategoryException("商品分类名称更改错误");
        }
    }

    @Override
    public void updateGoodsTypeName(Integer typeId, String typeName) {
        GoodsType goodsType = new GoodsType();
        goodsType.setTypeId(typeId);
        goodsType.setTypeName(typeName);
        goodsType.setTypeUpdate(new Date());
        int update = admin_typeMapper.updateById(goodsType);
        if (update != 1){
            throw new UpdateCategoryException("商品种类名称更改错误");
        }
    }

    @Override
    public void addGoodsCategory(String categoryName) {
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<>();
        Integer integer = admin_categoryMapper.selectCount(queryWrapper.eq("category_name", categoryName));
        if (integer >= 1){
            throw new GoodsCategoryNameDuplicatedException("该名称已存在(一级分类)");
        }
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryName(categoryName);
        goodsCategory.setCategoryCreate(new Date());
        goodsCategory.setCategoryUpdate(new Date());
        int insert = admin_categoryMapper.insert(goodsCategory);
        if (insert != 1){
            throw new InsertGoodsException("商品分类插入错误(一级分类)");
        }
    }

    @Override
    public void addGoodsType(String typeName, Integer categoryId) {
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();
        Integer integer = admin_typeMapper.selectCount(queryWrapper.eq("type_name", typeName));
        if (integer >= 1){
            throw new GoodsCategoryNameDuplicatedException("该名称已存在(二级分类)");
        }
        GoodsType goodsType = new GoodsType();
        goodsType.setCategoryId(categoryId);
        goodsType.setTypeName(typeName);
        goodsType.setTypeCreate(new Date());
        goodsType.setTypeUpdate(new Date());
        int insert = admin_typeMapper.insert(goodsType);
        if (insert != 1){
            throw new InsertGoodsException("商品种类插入错误(二级分类)");
        }
    }

    @Override
    public Integer selectCategoryChild(Integer categoryId) {
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();
        return admin_typeMapper.selectCount(queryWrapper.eq("category_id",categoryId));
    }

    @Override
    public void deleteGoodsCategory(Integer categoryId) {
        int delete = admin_categoryMapper.deleteById(categoryId);
        if (delete != 1){
            throw new DeleteGoodsCategoryException("商品分类："+categoryId+"删除异常(一级分类)");
        }
    }

    @Override
    public void deleteGoodsType(Integer typeId) {
        int delete = admin_typeMapper.deleteById(typeId);
        if (delete != 1){
            throw new DeleteGoodsTypeException("商品种类："+typeId+"删除异常(二级分类)");
        }
    }

    @Override
    public Page<Goods> searchGoodsListByPage(Integer currentPage, Integer size, String keyword, Integer categoryId, Integer typeId, Integer status, Integer ownerId) {
        Page<Goods> goodsPage = new Page<>(currentPage,size);
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        if (keyword != null){
            goodsQueryWrapper.like("name", "%" + keyword + "%");
        }
        if (ownerId != null){
            goodsQueryWrapper.eq("owner_id",ownerId);
        }
        if (status != null){
            if (status < 0){
                goodsQueryWrapper.eq("status",status);
            }else {
                goodsQueryWrapper.ge("status",status);
            }
        }
        if (categoryId != null){
            goodsQueryWrapper.eq("category_id",categoryId);
        }else if (typeId != null){
            goodsQueryWrapper.eq("type_id",typeId);
        }

        //调用getRecords，取出集合数据
        goodsMapper.selectPage(goodsPage, goodsQueryWrapper.orderByDesc("create_date"));
        return goodsPage;
    }

    @Override
    public void deleteGoodsById(Integer goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",goodsId);
        int delete = goodsMapper.delete(queryWrapper);
        if (delete != 1){
            throw new DeleteGoodsException("商品删除失败");
        }
    }

    @Override
    public List<GoodsDetail> getGoodsDetailByWaitCheck() {
        return goodsMapper.getGoodsDetailByWaitCheck();
    }

    @Override
    public void updateGoodsStatusToAdopt(Integer goodsId) {
        Goods selectById = goodsMapper.selectById(goodsId);
        if (selectById.getStatus() != -4){
            throw new UpdateGoodsStatusException("商品已受审");
        }
        Goods goods = new Goods();
        goods.setStatus(0);

        LambdaUpdateWrapper<Goods> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Goods::getWarnMessage, null);
        wrapper.eq(Goods::getId, goodsId);

        int update = goodsMapper.update(goods, wrapper);
        if (update != 1){
            throw new UpdateGoodsStatusException("id:" + goodsId + ",商品状态修改错误");
        }
    }

    @Override
    public void updateGoodsStatusToRefuse(Integer goodsId,String warnMessage) {
        Goods selectById = goodsMapper.selectById(goodsId);
        if (selectById.getStatus() != -4){
            throw new UpdateGoodsStatusException("商品已受审");
        }
        Goods goods = new Goods();
        goods.setStatus(-3);
        goods.setWarnMessage(warnMessage);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",goodsId);
        int update = goodsMapper.update(goods, queryWrapper);
        if (update != 1){
            throw new UpdateGoodsStatusException("id:" + goodsId + ",商品状态修改错误");
        }
    }

    @Override
    public void updateGoodsStatusByGoodsId(Integer goodsId, Integer goodsStatus) {
        Goods goods = new Goods();
        goods.setId(goodsId);
        goods.setStatus(goodsStatus);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",goodsId);
        int update = goodsMapper.update(goods, queryWrapper);
        if (update != 1){
            throw new UpdateGoodsStatusException("id:" + goodsId + ",商品状态修改错误");
        }
    }

    @Override
    public void updateCommentInfoByGoods(Goods goods) {
        Comment comment = new Comment();
        comment.setGoodsId(goods.getId().toString());
        Example<Comment> example = Example.of(comment);
        List<Comment> commentList = commentRepository.findAll(example);
        for (Comment comment1 : commentList) {
            comment1.setGoodsName(goods.getName());
            comment1.setGoodsCoverImage(goods.getCoverImage());
        }
        commentRepository.saveAll(commentList);
    }


}
