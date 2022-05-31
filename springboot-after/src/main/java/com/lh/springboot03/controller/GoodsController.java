package com.lh.springboot03.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.*;
import com.lh.springboot03.service.GoodsService;
import com.lh.springboot03.service.UserService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import com.lh.springboot03.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    //注：前缀加/home 表示无需登录即可请求到的接口
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;

    //获取分类数据
    @RequestMapping("/home/getGoodsCategoryList")
    public JsonResult<List<GoodsCategoryList>> getGoodsCategoryList(){
        JsonResult<List<GoodsCategoryList>> result = new JsonResult<>();
        List<GoodsCategoryList> categoryList = goodsService.getGoodsCategoryList();
        result.setData(categoryList);
        result.setState(OK);
        result.setMessage("你可真是个小天才");
        return result;
    }


    //获取分页数据
    @RequestMapping("/home/getGoodsInfoByPage")
    public JsonResult<Page<Goods>> getGoodsInfoByPage(@RequestParam("currentPage") Integer currentPage,
                                                      @RequestParam("size") Integer size){
        JsonResult<Page<Goods>> result = new JsonResult<>();
        Page<Goods> goodsPage = goodsService.getGoodsListByPage(currentPage, size);
        result.setState(OK);
        result.setMessage("分页数据获取成功");
        result.setData(goodsPage);
        return result;
    }

    //根据商品id查询对应商品详细信息
    @RequestMapping("/home/selectGoodsByGoodsId")
    public JsonResult<GoodsDetail> selectGoodsByGoodsId(@RequestParam("goodsId") Integer goodsId,
                                                        @RequestParam(value = "view", required = false) Integer view,
                                                        HttpServletRequest request){
        JsonResult<GoodsDetail> result = new JsonResult<>();
        System.out.println("view："+view);
        if (view != null){
            goodsService.addGoodsState(goodsId);
        }
        //获取商品详细信息
        GoodsDetail goodsDetail = goodsService.getGoodsDetailById(goodsId);

        //由于拦截器放行了获取商品信息的url，因此需判断是否是登录状态
        String access_token = TokenUtil.getCookieFromRequest(request, "access_token");
        //检查请求中是否有token
        if (access_token != null){
            //查询登录信息
            User user = userService.getTokenValueDirect(access_token);
            if (user != null){
                //查询该用户是否收藏此商品
                Boolean isCollected = goodsService.checkIsCollected(user.getUid(), goodsId);
                goodsDetail.setCollect(isCollected);
            }
        }
        result.setState(OK);
        result.setMessage("商品查询成功");
        result.setData(goodsDetail);
        return result;
    }

    //根据信息查询对应商品数据
    @RequestMapping("/home/searchGoodsInfoList")
    public JsonResult<Page<Goods>> searchGoodsInfoList(@RequestParam("currentPage") Integer currentPage,
                                                       @RequestParam("size") Integer size,
                                                       @RequestParam(value = "keyword",required = false) String keyword,
                                                       @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                       @RequestParam(value = "typeId",required = false) Integer typeId
    ){
//        System.out.println("currentPage:"+currentPage);
//        System.out.println("size:"+size);
//        System.out.println("keyword:"+keyword);
//        System.out.println("categoryId:"+categoryId);
//        System.out.println("typeId:"+typeId);
//        System.out.println();
        JsonResult<Page<Goods>> result = new JsonResult<>();
        Page<Goods> goodsPage = goodsService.searchGoodsListByPage(currentPage, size, keyword, categoryId, typeId);
        result.setState(OK);
        result.setMessage("数据查询获取成功");
        result.setData(goodsPage);
        return result;
    }


    //根据商品id查询商品详细信息
    @RequestMapping("/selectGoodsByGoodsId")
    public JsonResult<GoodsDetail> selectGoodsByGoodsId(@RequestParam("goodsId") Integer goodsId){
        JsonResult<GoodsDetail> result = new JsonResult<>();
        GoodsDetail goodsDetail = goodsService.getGoodsDetailById(goodsId);
        result.setState(OK);
        result.setMessage("商品信息获取成功");
        result.setData(goodsDetail);
        return result;
    }

    //接收用户上传的封面
    @RequestMapping("/receiveCoverImage")
    public JsonResult<String> receiveCoverImage(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        String ImageSaveAddress = UploadFileUtil.saveFile(file, "goodsCoverImages");
        return new JsonResult<>(OK, "商品封面上传成功", ImageSaveAddress);
    }
    //接收用户上传的图片
    @RequestMapping("/receiveImage")
    public JsonResult<String> receiveImage(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        String ImageSaveAddress = UploadFileUtil.saveFile(file, "goodsImages");
        return new JsonResult<>(OK, "商品图片上传成功", ImageSaveAddress);
    }

    //发布商品
    @RequestMapping("/releaseGoods")
    public JsonResult<Goods> releaseGoods(@RequestBody Goods goods, HttpServletRequest request){
        JsonResult<Goods> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goods.setStatus(-4);
        goods.setOwnerId(user.getUid());
        goods.setCreateDate(new Date());
        goodsService.releaseGoods(goods);
        result.setState(OK);
        result.setMessage("发布成功，等待审核");
        return result;
    }

    //更新发布的商品
    @RequestMapping("/updateReleaseGoods")
    public JsonResult<Goods> updateReleaseGoods(@RequestBody Goods goods, HttpServletRequest request){
        JsonResult<Goods> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goods.setStatus(-4);
        goods.setOwnerId(user.getUid());
        goods.setCreateDate(new Date());
        goodsService.updateReleaseGoods(goods, user.getUid());
        result.setState(OK);
        result.setMessage("更新成功，等待审核");
        return result;
    }

    //获取收藏列表
    @RequestMapping("/getCollectList")
    public JsonResult<List<Goods>> getCollectList(HttpServletRequest request){
        JsonResult<List<Goods>> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        List<Goods> collects = goodsService.getCollectsByUid(user.getUid());
        result.setState(OK);
        result.setMessage("收藏列表获取成功");
        result.setData(collects);
        return result;
    }

    //用户收藏或取消收藏
    @RequestMapping("/changeCollectionStatus")
    public JsonResult<Void> changeCollectionStatus(HttpServletRequest request,
                                                   @RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        Integer integer = goodsService.changeCollectionStatus(user.getUid(), goodsId);
        if (integer <= 0){
            result.setMessage("收藏成功");
        }else {
            result.setMessage("已取消收藏");
        }
        result.setState(OK);
        return result;
    }

    //用户收藏或取消收藏
    @RequestMapping("/cancelCollection")
    public JsonResult<Void> cancelCollection(HttpServletRequest request,
                                                   @RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goodsService.cancelCollection(user.getUid(), goodsId);
        result.setState(OK);
        result.setMessage("已取消收藏");
        return result;
    }

    //获取用户持有的所有商品
    @RequestMapping("/getAllHoldGoods")
    public JsonResult<List<Goods>> getAllHoldGoods(HttpServletRequest request){
        JsonResult<List<Goods>> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        List<Goods> goodsList = goodsService.getAllHoldGoodsByUid(user.getUid());
        result.setState(OK);
        result.setMessage("已获取持有的商品");
        result.setData(goodsList);
        return result;
    }

    //用户取消发布商品
    @RequestMapping("/cancelReleaseGoods")
    public JsonResult<Void> cancelReleaseGoods(HttpServletRequest request,
                                               @RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goodsService.cancelReleaseGoods(user.getUid(), goodsId);
        result.setState(OK);
        result.setMessage("已取消发布该商品");
        return result;
    }

    //用户确认已售出商品
    @RequestMapping("/confirmSoldGoods")
    public JsonResult<Void> confirmSoldGoods(HttpServletRequest request,
                                               @RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goodsService.confirmSoldGoods(user.getUid(), goodsId);
        result.setState(OK);
        result.setMessage("该商品标记为已售出");
        return result;
    }

    //获取商品警告信息列表
    @RequestMapping("/getGoodsWarnList")
    public JsonResult<List<GoodsWarn>> getGoodsWarnList(){
        List<GoodsWarn> goodsWarnList = goodsService.getGoodsWarnList();
        JsonResult<List<GoodsWarn>> result = new JsonResult<>();
        result.setData(goodsWarnList);
        result.setState(OK);
        result.setMessage("商品警告列表获取成功");
        return result;
    }

    //用户删除商品信息
    @RequestMapping("/deleteGoodsByGoodsId")
    public JsonResult<Void> deleteGoodsByGoodsId(Integer goodsId, HttpServletRequest request){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        goodsService.deleteGoodsByOwnerIdAndId(user.getUid(),goodsId);
        result.setState(OK);
        result.setMessage("商品删除成功");
        return result;
    }
}
