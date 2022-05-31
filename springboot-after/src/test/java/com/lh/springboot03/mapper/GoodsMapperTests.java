package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.lh.springboot03.bean.Collect;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.bean.GoodsCategoryList;
import com.lh.springboot03.bean.GoodsDetail;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;
import com.lh.springboot03.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//测试类，不会随项目打包发送
@SpringBootTest
//表示启动这个单元测试类，参数未SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class GoodsMapperTests {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private GoodsService goodsService;

    @Test
    public void testGoodsMapper(){
//        Goods goods = new Goods();
//        goods.setName("66");
//        int result = goodsMapper.insert(goods);
//        int result = goodsMapper.insert(new Goods(null, "tupian1", "tupian2", "tupian3", 1,1, "奥里给", 100, 6.9,99.9, 0, "好吃又好玩", 16, new Date(), "qq.110","wx.119"));
//        System.out.println(result);
//        List<GoodsCategoryList> goodsCategoryLists = goodsMapper.selectGoodsCategoryList();
//        for (GoodsCategoryList goodsCategoryList : goodsCategoryLists) {
//            System.out.println(goodsCategoryList);
//        }
        goodsService.getGoodsListByPage(1, 2);
    }

    @Test
    public void getGoodsDetail(){
        GoodsDetail goodsDetailById = goodsMapper.getGoodsDetailById(3);
        System.out.println(goodsDetailById);
    }

    @Test
    public void getCollectsByUid(){
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",5);
        List<Collect> collects = collectMapper.selectList(queryWrapper);
        System.out.println(collects);
        for (Collect collect : collects) {
            System.out.println(collect);
        }
    }

    @Test
    public void checkIsCollected(){
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",1).eq("goods_id",0);
        Integer count = collectMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void getCollectedList(){
        List<Goods> collectedList = goodsMapper.getCollectedList(16);
        for (Goods goods : collectedList) {
            System.out.println(goods);
        }
    }

    @Test
    public void cancelReleaseGoods(){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id",16).eq("id",16);
        Goods goods = new Goods();
        goods.setStatus(-2);
        int update = goodsMapper.update(goods, queryWrapper);
        System.out.println(update);
    }

    @Test
    public void selectGoodsCategoryTree(){
        List<GoodsCategoryTree> goodsCategoryTrees = goodsMapper.selectGoodsCategoryTree();
        for (GoodsCategoryTree goodsCategoryTree : goodsCategoryTrees) {
            System.out.println(goodsCategoryTree);
        }
    }

    @Test
    public void getGoodsDetailByWaitCheck(){
        List<GoodsDetail> list = goodsMapper.getGoodsDetailByWaitCheck();
        for (GoodsDetail goodsDetail : list) {
            System.out.println(goodsDetail);
        }
    }
}
