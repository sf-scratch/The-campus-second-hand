package com.lh.springboot03.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.*;
import com.lh.springboot03.bean_admin.UserActiveInfo;
import com.lh.springboot03.mapper.*;
import com.lh.springboot03.service.GoodsService;
import com.lh.springboot03.service.exception.*;
import com.lh.springboot03.utils.WebSocketInstruct;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.Future;



@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private WarnMapper warnMapper;
    @Autowired
    private Admin_UserMapper admin_userMapper;
    @Autowired
    private Admin_GoodsMapper admin_goodsMapper;
    //获取商品分类信息
    @Override
    public List<GoodsCategoryList> getGoodsCategoryList() {
        return goodsMapper.selectGoodsCategoryList();
    }

    //根据接收的分页数据，获取已通过审核的商品的分页信息
    @Override
    public Page<Goods> getGoodsListByPage(int currentPage,int size) {
        Page<Goods> goodsPage = new Page<>(currentPage,size);
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
//        List<String> list = Arrays.asList(new String[]{"create_time"});
//        List<String> list = Collections.singletonList("create_data");
        //调用getRecords，取出集合数据
        goodsMapper.selectPage(goodsPage, goodsQueryWrapper.ge("status",0).orderByDesc("create_date "));

//        System.out.println("获取当前页:"+goodsPage.getCurrent());
//        System.out.println("获取总记录数:"+goodsPage.getTotal());
//        System.out.println("获取每页的条数:"+goodsPage.getSize());
//        System.out.println("获取每页数据的集合:"+goodsPage.getRecords());
//        System.out.println("获取总页数:"+goodsPage.getPages());
//        System.out.println("是否存在下一页:"+goodsPage.hasNext());
//        System.out.println("是否存在上一页:"+goodsPage.hasPrevious());
        return goodsPage;
    }


    @Override
    public Page<Goods> searchGoodsListByPage(Integer currentPage, Integer size, String keyword,
                                             Integer categoryId, Integer typeId) {
        Page<Goods> goodsPage = new Page<>(currentPage,size);
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        if (keyword != null){
            goodsQueryWrapper.like("name", "%" + keyword + "%");
        }
        if (categoryId != null){
            goodsQueryWrapper.eq("category_id",categoryId);
        }else if (typeId != null){
            goodsQueryWrapper.eq("type_id",typeId);
        }

        //调用getRecords，取出集合数据
        goodsMapper.selectPage(goodsPage, goodsQueryWrapper.ge("status",0).orderByDesc("create_date "));
        return goodsPage;
    }


    //根据商品id获取信息
    @Override
    public Goods getGoodsById(Integer goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null){
            throw new GoodsNotFoundException("没有找到该商品的信息");
        }
        return goods;
    }

    //根据商品id获取详细信息
    @Override
    public GoodsDetail getGoodsDetailById(Integer goodsId) {
        GoodsDetail goodsDetail = goodsMapper.getGoodsDetailById(goodsId);
        if (goodsDetail == null){
            throw new GoodsNotFoundException("没有找到该商品的信息");
        }
        return goodsDetail;
    }

    @Override
    public void addGoodsState(Integer goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        Integer status = goods.getStatus();
        if (status >= 0){
            goods.setStatus(status+1);
            goodsMapper.updateById(goods);
        }
    }


    //添加商品信息
    @Override
    public void releaseGoods(Goods goods) {
        int result = goodsMapper.insert(goods);
        if (result != 1){
            throw new GoodsReleaseException("商品发布失败");
        }

        //获取用户活跃信息
        UserActiveInfo userActiveInfo = new UserActiveInfo();
        userActiveInfo.setCurrentMouthRegisterUser(admin_userMapper.getCurrentMouthRegisterUser());
        userActiveInfo.setUserSum(admin_userMapper.selectCount(null));
        userActiveInfo.setCurrentMouthReleaseGoodsSum(admin_goodsMapper.getCurrentMouthReleaseGoodsSum());
        userActiveInfo.setCurrentDayReleaseGoodsSum(admin_goodsMapper.getCurrentDayReleaseGoodsSum());
        userActiveInfo.setGoodsSum(admin_goodsMapper.selectCount(null));

        Collection<Session> sessions = WebSocketUtil.ADMIN_ONLINE_SESSION.values();
        for (Session session : sessions) {
            // getAsyncRemote()和getBasicRemote()异步与同步
            RemoteEndpoint.Async async = session.getAsyncRemote();
            //发送消息
            WebSocketInstruct<UserActiveInfo> socketInstruct = new WebSocketInstruct<>();
            socketInstruct.setInstruct(2);
            socketInstruct.setMessage("用户活跃量数据更新");
            socketInstruct.setData(userActiveInfo);
            Future<Void> future = async.sendText(JSONObject.toJSONString(socketInstruct));
            boolean done = future.isDone();
            System.out.println("服务器发送消息给管理员端" + session.getId() + "的消息:" + socketInstruct + "，状态为:" + done);
        }
    }

    //更改商品信息
    @Override
    public void updateReleaseGoods(Goods goods, Integer uid) {
        LambdaUpdateWrapper<Goods> wrapper = new LambdaUpdateWrapper<>();
        //image2，image3，有值则设置值，为null则设为null
        wrapper.set(Goods::getImage2, goods.getImage2());
        wrapper.set(Goods::getImage3, goods.getImage3());
        wrapper.eq(Goods::getOwnerId, uid);
        wrapper.eq(Goods::getId, goods.getId());
        int update = goodsMapper.update(goods, wrapper);
        if (update != 1){
            throw new GoodsReleaseException("商品更新失败");
        }
    }

    //收藏相关
    //根据用户id获取该用户收藏列表
    @Override
    public List<Goods> getCollectsByUid(Integer uid) {
        return goodsMapper.getCollectedList(uid);
    }

    //根据用户id和商品id判断用户是否收藏
    @Override
    public Boolean checkIsCollected(Integer uid, Integer goodsId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uid).eq("goods_id",goodsId);
        Integer count = collectMapper.selectCount(queryWrapper);
        return count > 0;
    }


    //用户 收藏/取消收藏 商品
    @Override
    public Integer changeCollectionStatus(Integer uid, Integer goodsId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uid).eq("goods_id",goodsId);
        int delete = collectMapper.delete(queryWrapper);
        if (delete <= 0){
            collectMapper.insert(new Collect(uid, goodsId, new Date()));
        }
        return delete;
    }

    //用户 取消收藏 商品
    @Override
    public void cancelCollection(Integer uid, Integer goodsId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uid).eq("goods_id",goodsId);
        int delete = collectMapper.delete(queryWrapper);
        if (delete <= 0){
            throw new CancelCollectionException("频繁操作");
        }
    }

    @Override
    public List<Goods> getAllHoldGoodsByUid(Integer uid) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id",uid).orderByDesc("create_date ");
        return goodsMapper.selectList(queryWrapper);
    }

    @Override
    public void cancelReleaseGoods(Integer uid, Integer goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id",uid).eq("id",goodsId);
        Goods goods = new Goods();
        goods.setStatus(-2);
        int update = goodsMapper.update(goods, queryWrapper);
        if (update < 1){
            throw new CancelReleaseGoodsException("重复操作");
        }
    }

    @Override
    public void confirmSoldGoods(Integer uid, Integer goodsId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id",uid).eq("id",goodsId);
        Goods goods = new Goods();
        goods.setStatus(-1);
        int update = goodsMapper.update(goods, queryWrapper);
        if (update < 1){
            throw new CancelReleaseGoodsException("重复操作");
        }
    }

    @Override
    public void addGoodsWarn(GoodsWarn goodsWarn) {
        QueryWrapper<GoodsWarn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warn_name",goodsWarn.getWarnName());
        List<GoodsWarn> goodsWarns = warnMapper.selectList(queryWrapper);
        if (goodsWarns.size() != 0){
            throw new InsertGoodsWarnException("商品警告名称重复");
        }
        goodsWarn.setWarnCreate(new Date());
        goodsWarn.setWarnUpdate(new Date());
        int insert = warnMapper.insert(goodsWarn);
        if (insert != 1){
            throw new InsertGoodsWarnException("商品警告插入异常");
        }
    }

    @Override
    public List<GoodsWarn> getGoodsWarnList() {
        return warnMapper.selectList(null);
    }

    @Override
    public GoodsWarn getGoodsWarnByWarnId(Integer warnId) {
        return warnMapper.selectById(warnId);
    }

    @Override
    public void deleteGoodsWarnByWarnId(Integer warnId) {
        int delete = warnMapper.deleteById(warnId);
        if (delete != 1){
            throw new DeleteGoodsWarnException("商品警告删除异常");
        }
    }

    @Override
    public void updateGoodsWarnByWarnId(GoodsWarn goodsWarn) {
        QueryWrapper<GoodsWarn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("warn_name",goodsWarn.getWarnName());
        List<GoodsWarn> goodsWarns = warnMapper.selectList(queryWrapper);
        if (goodsWarns.size() != 0 && !goodsWarns.get(0).getWarnId().equals(goodsWarn.getWarnId())){
            throw new UpdateGoodsWarnException("商品警告名称重复");
        }
        goodsWarn.setWarnUpdate(new Date());
        int update = warnMapper.updateById(goodsWarn);
        if (update != 1){
            throw new UpdateGoodsWarnException("商品警告修改异常");
        }
    }

    @Override
    public void deleteGoodsByOwnerIdAndId(Integer ownerId, Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if (!goods.getOwnerId().equals(ownerId)){
            throw new DeleteGoodsException("商品删除异常(非持有者)");
        }
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id",ownerId).eq("id",id);
        int delete = goodsMapper.delete(queryWrapper);
        if (delete != 1){
            throw new DeleteGoodsException("商品删除异常");
        }
    }


}
