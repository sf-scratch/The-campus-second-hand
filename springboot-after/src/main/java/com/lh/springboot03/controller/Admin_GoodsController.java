package com.lh.springboot03.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.*;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;
import com.lh.springboot03.service.Admin_goodsService;
import com.lh.springboot03.service.GoodsService;
import com.lh.springboot03.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/admin/goods")
public class Admin_GoodsController extends BaseController{
    @Autowired
    Admin_goodsService admin_goodsService;
    @Autowired
    GoodsService goodsService;
    //获取分类树形数据
    @RequestMapping("/getGoodsCategoryTree")
    public JsonResult<List<GoodsCategoryTree>> getGoodsCategoryList(){
        JsonResult<List<GoodsCategoryTree>> result = new JsonResult<>();
        List<GoodsCategoryTree> goodsCategoryTree = admin_goodsService.getGoodsCategoryTree();
        result.setData(goodsCategoryTree);
        result.setState(OK);
        result.setMessage("分类树形数据获取成功");
        return result;
    }

    //获取所有分类信息
    @RequestMapping("/getCategoryListInGoodsCategory")
    public JsonResult<List<GoodsCategory>> getCategoryListInGoodsCategory(){
        JsonResult<List<GoodsCategory>> result = new JsonResult<>();
        List<GoodsCategory> categoryList = admin_goodsService.getCategoryListInGoodsCategory();
        result.setState(OK);
        result.setMessage("分类信息列表获取成功");
        result.setData(categoryList);
        return result;
    }

    //查询分类信息
    @RequestMapping("/getGoodsCategoryInfo")
    public JsonResult<GoodsCategory> getGoodsCategoryInfo(@RequestParam("id") Integer id){
        JsonResult<GoodsCategory> result = new JsonResult<>();
        GoodsCategory goodsCategory = admin_goodsService.getGoodsCategoryInfoById(id);
        result.setData(goodsCategory);
        result.setState(OK);
        result.setMessage("分类信息获取成功");
        return result;
    }

    //查询种类信息
    @RequestMapping("/getGoodsTypeInfo")
    public JsonResult<GoodsType> getGoodsTypeInfo(@RequestParam("id") Integer id){
        JsonResult<GoodsType> result = new JsonResult<>();
        GoodsType goodsType = admin_goodsService.getGoodsTypeInfoById(id);
        result.setData(goodsType);
        result.setState(OK);
        result.setMessage("种类信息获取成功");
        return result;
    }

    //更改商品分类名称
    @RequestMapping("/updateGoodsCategoryName")
    public JsonResult<GoodsType> updateGoodsCategoryName(@RequestParam("categoryName") String categoryName,
                                                         @RequestParam("categoryId") Integer categoryId){
        JsonResult<GoodsType> result = new JsonResult<>();
        admin_goodsService.updateGoodsCategoryName(categoryId,categoryName);
        result.setState(OK);
        result.setMessage("商品分类名称更改成功");
        return result;
    }

    //更改商品种类名称
    @RequestMapping("/updateGoodsTypeName")
    public JsonResult<GoodsType> updateGoodsTypeName(@RequestParam("typeName") String typeName,
                                                     @RequestParam("typeId") Integer typeId){
        JsonResult<GoodsType> result = new JsonResult<>();
        admin_goodsService.updateGoodsTypeName(typeId,typeName);
        result.setState(OK);
        result.setMessage("商品种类名称更改成功");
        return result;
    }

    //添加商品分类与种类
    @RequestMapping("/addGoodsCategory")
    public JsonResult<GoodsType> addGoodsCategory(@RequestParam("categoryName") String categoryName){
        JsonResult<GoodsType> result = new JsonResult<>();
        admin_goodsService.addGoodsCategory(categoryName);
        result.setState(OK);
        result.setMessage("商品分类插入成功(一级分类)");
        return result;
    }

    //添加商品分类与种类
    @RequestMapping("/addGoodsType")
    public JsonResult<GoodsType> addGoodsType(@RequestParam("typeName") String typeName,
                                              @RequestParam("categoryId") Integer categoryId){
        JsonResult<GoodsType> result = new JsonResult<>();
        admin_goodsService.addGoodsType(typeName, categoryId);
        result.setState(OK);
        result.setMessage("商品种类插入成功(二级分类)");
        return result;
    }

    //删除一级分类
    @RequestMapping("/deleteGoodsCategory")
    public JsonResult<Integer> deleteGoodsCategory(@RequestParam("categoryId") Integer categoryId){
        JsonResult<Integer> result = new JsonResult<>();
        Integer child = admin_goodsService.selectCategoryChild(categoryId);
        if (child > 0){
            result.setState(4444);
            result.setMessage("该一级分类存在子分类(二级分类),请先删除子分类");
            result.setData(child);
            return result;
        }
        admin_goodsService.deleteGoodsCategory(categoryId);
        result.setState(OK);
        result.setMessage("(一级分类)删除成功");
        return result;
    }

    //删除一级分类
    @RequestMapping("/deleteGoodsType")
    public JsonResult<Void> deleteGoodsType(@RequestParam("typeId") Integer typeId){
        JsonResult<Void> result = new JsonResult<>();
        admin_goodsService.deleteGoodsType(typeId);
        result.setState(OK);
        result.setMessage("(二级分类)删除成功");
        return result;
    }

    //根据信息查询对应商品数据
    @RequestMapping("/searchGoodsInfoList")
    public JsonResult<Page<Goods>> searchGoodsInfoList(@RequestParam("currentPage") Integer currentPage,
                                                       @RequestParam("size") Integer size,
                                                       @RequestParam(value = "keyword",required = false) String keyword,
                                                       @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                       @RequestParam(value = "typeId",required = false) Integer typeId,
                                                       @RequestParam(value = "status",required = false) Integer status,
                                                       @RequestParam(value = "ownerId",required = false) Integer ownerId
    ){
//        System.out.println("currentPage:"+currentPage);
//        System.out.println("size:"+size);
//        System.out.println("keyword:"+keyword);
//        System.out.println("categoryId:"+categoryId);
//        System.out.println("typeId:"+typeId);
//        System.out.println("status:"+status);
//        System.out.println("ownerId:"+ownerId);
        JsonResult<Page<Goods>> result = new JsonResult<>();
        Page<Goods> goodsPage = admin_goodsService.searchGoodsListByPage(currentPage, size, keyword, categoryId, typeId, status, ownerId);
        result.setState(OK);
        result.setMessage("数据查询成功");
        result.setData(goodsPage);
//        System.out.println("获取当前页:"+goodsPage.getCurrent());
//        System.out.println("获取总记录数:"+goodsPage.getTotal());
//        System.out.println("获取每页的条数:"+goodsPage.getSize());
//        System.out.println("获取每页数据的集合:"+goodsPage.getRecords());
//        System.out.println("获取总页数:"+goodsPage.getPages());
//        System.out.println("是否存在下一页:"+goodsPage.hasNext());
//        System.out.println("是否存在上一页:"+goodsPage.hasPrevious());
        return result;
    }

    //根据商品id删除商品信息
    @RequestMapping("/deleteGoodsById")
    public JsonResult<Void> deleteGoodsById(@RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        admin_goodsService.deleteGoodsById(goodsId);
        result.setState(OK);
        result.setMessage("商品删除成功");
        return result;
    }

    //获取未审核商品信息
    @RequestMapping("/getGoodsDetailByWaitCheck")
    public JsonResult<List<GoodsDetail>> getGoodsDetailByWaitCheck(){
        JsonResult<List<GoodsDetail>> result = new JsonResult<>();
        List<GoodsDetail> goodsDetails = admin_goodsService.getGoodsDetailByWaitCheck();
        result.setData(goodsDetails);
        result.setState(OK);
        result.setMessage("待审核商品信息获取成功");
        return result;
    }

    //商品通过审核
    @RequestMapping("/updateGoodsStatusToAdopt")
    public JsonResult<Void> updateGoodsStatusToAdopt(@RequestParam("goodsId") Integer goodsId){
        JsonResult<Void> result = new JsonResult<>();
        admin_goodsService.updateGoodsStatusToAdopt(goodsId);
        result.setState(OK);
        result.setMessage("商品已通过审核");
        Goods goods = goodsService.getGoodsById(goodsId);
        //修改该商品评论的信息
        admin_goodsService.updateCommentInfoByGoods(goods);
        return result;
    }

    //商品拒绝发布
    @RequestMapping("/updateGoodsStatusToRefuse")
    public JsonResult<Void> updateGoodsStatusToRefuse(@RequestParam("goodsId") Integer goodsId,
                                                      @RequestParam("warnMessage") String warnMessage){
        JsonResult<Void> result = new JsonResult<>();
        admin_goodsService.updateGoodsStatusToRefuse(goodsId,warnMessage);
        result.setState(OK);
        result.setMessage("商品已拒绝发布");
        return result;
    }

    //修改商品状态
    @RequestMapping("/updateGoodsStatusByGoodsId")
    public JsonResult<Void> updateGoodsStatusByGoodsId(@RequestParam("goodsId") Integer goodsId,
                                                       @RequestParam("goodsStatus") Integer goodsStatus){
        JsonResult<Void> result = new JsonResult<>();
        admin_goodsService.updateGoodsStatusByGoodsId(goodsId,goodsStatus);
        result.setState(OK);
        result.setMessage("商品状态修改成功");
        return result;
    }

    //增加商品警告信息
    @RequestMapping("addGoodsWarn")
    public JsonResult<Void> addGoodsWarn(@RequestBody GoodsWarn goodsWarn){
        JsonResult<Void> result = new JsonResult<>();
        goodsService.addGoodsWarn(goodsWarn);
        result.setState(OK);
        result.setMessage("商品警告添加成功");
        return result;
    }

    //获取商品警告信息列表
    @RequestMapping("getGoodsWarnList")
    public JsonResult<List<GoodsWarn>> getGoodsWarnList(){
        JsonResult<List<GoodsWarn>> result = new JsonResult<>();
        List<GoodsWarn> goodsWarnList = goodsService.getGoodsWarnList();
        result.setData(goodsWarnList);
        result.setState(OK);
        result.setMessage("商品警告列表获取成功");
        return result;
    }

    //根据商品警告id获取警告信息
    @RequestMapping("getGoodsWarnByWarnId")
    public JsonResult<GoodsWarn> getGoodsWarnByWarnId(@RequestParam("warnId") Integer warnId){
        JsonResult<GoodsWarn> result = new JsonResult<>();
        GoodsWarn goodsWarn = goodsService.getGoodsWarnByWarnId(warnId);
        result.setData(goodsWarn);
        result.setState(OK);
        result.setMessage("商品警告信息获取成功");
        return result;
    }

    //根据商品警告id删除警告信息
    @RequestMapping("deleteGoodsWarnByWarnId")
    public JsonResult<GoodsWarn> deleteGoodsWarnByWarnId(@RequestParam("warnId") Integer warnId){
        JsonResult<GoodsWarn> result = new JsonResult<>();
        goodsService.deleteGoodsWarnByWarnId(warnId);
        result.setState(OK);
        result.setMessage("商品警告信息删除成功");
        return result;
    }

    //根据商品警告id修改警告信息
    @RequestMapping("updateGoodsWarnByWarnId")
    public JsonResult<GoodsWarn> updateGoodsWarnByWarnId(@RequestBody GoodsWarn goodsWarn){
        JsonResult<GoodsWarn> result = new JsonResult<>();
        goodsService.updateGoodsWarnByWarnId(goodsWarn);
        result.setState(OK);
        result.setMessage("商品警告信息修改成功");
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
}
