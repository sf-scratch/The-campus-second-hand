<template>
  <div class="myPage">
    <div class="temp"></div>
    <div class="myStyle">
      <el-form :model="goodsInfoForm" :rules="goodsInfoFormRules" ref="goodsInfoForm" label-width="80px">
        <el-row>
          <el-col :span="14">
            <el-form-item label="商品名称" prop="name">
              <el-input v-model="goodsInfoForm.name" size="large"
                        placeholder="请输入商品名称(完整名称)" maxlength="20" show-word-limit></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item :inline="true" label-width="10px">
              <el-input v-model="goodsInfoForm.num" type="number" size="mini" placeholder="输入数量"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label-width="10px">
              <el-tag size="mini" type="info">默认为1</el-tag>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item size="mini" label="商品类别" prop="typeId">
          <div style="width: 550px;">
            <el-col :span="4" v-for="(category) in categoryList" :key="category.categoryId">
              <el-dropdown trigger="click">
                <el-button plain href="javascript:void(0);" :data-category-name="category.categoryName"
                           style="width: 80px" :data-category-id="category.categoryId">{{category.categoryName}}</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-for="(type) in category.types" :key="type.typeId"
                                    @click.native="selectCategory(category.categoryId,type.typeId,category.categoryName,type.typeName)">{{type.typeName}}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
          </div>
        </el-form-item>
        <el-row v-if="goodsInfoForm.typeName" >
          <el-col :span="2"><div class="grid-content Transparent"></div></el-col>
          <el-col :span="22">
            当前选择的分类：<el-tag size="mini">{{goodsInfoForm.categoryName}}->{{goodsInfoForm.typeName}}</el-tag>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="预计售价" size="mini" prop="price">
              <el-input v-model="goodsInfoForm.price" type="number" size="mini" placeholder="请输入售价" suffix-icon="el-icon-coin"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品原价" size="mini">
              <el-input v-model="goodsInfoForm.originalPrice" type="number" size="mini" placeholder="请输入原价" suffix-icon="el-icon-coin"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-tag size="mini" type="info">需真实信息，可不填</el-tag>
          </el-col>
        </el-row>
        <el-form-item size="mini" label="联系方式" prop="contact">
          <el-col :span="15">
            <el-input placeholder="请输入联系方式" v-model="goodsInfoForm.contact"
                      class="input-with-select" prefix-icon="el-icon-chat-dot-round" maxlength = "20">
              <el-select v-model="goodsInfoForm.contactType" slot="prepend" placeholder="请选择">
                <el-option label="QQ" :value="1"></el-option>
                <el-option label="微信" :value="2"></el-option>
              </el-select>
            </el-input>
          </el-col>
          <el-col :span="3" v-if="goodsInfoForm.contactType===1"><el-tag size="small">QQ</el-tag></el-col>
          <el-col :span="3" v-else><el-tag size="small">微信</el-tag></el-col>
          <el-col :span="6"><div class="grid-content Transparent"></div></el-col>
        </el-form-item>
        <el-form-item label="商品信息" prop="content">
          <el-col :span="17">
            <el-input
                type="textarea"
                resize="none"
                :autosize="{ minRows: 7, maxRows: 7}"
                placeholder="请输入商品的信息，以及个人的信息补充"
                v-model="goodsInfoForm.content"
                maxlength="100"
                show-word-limit
            >
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="封面图片" prop="coverImage">
          <img v-if="goodsInfoForm.coverImage" :src="goodsInfoForm.coverImage" class="avatar" alt="" @click="dialogVisible = true">
          <el-button type="text" @click="dialogVisible = true">上传封面</el-button>
          <el-dialog
              title="封面设置"
              :visible.sync="dialogVisible"
              width="30%"
              :before-close="handleClose">
            <vueCropper
                style="width:100%;height:300px"
                ref="cropper"
                :img="options.img"
                :autoCrop="options.autoCrop"
                :fixedBox="options.fixedBox"
                :canMoveBox="options.canMoveBox"
                :autoCropWidth="options.autoCropWidth"
                :autoCropHeight="options.autoCropHeight"
                :centerBox="options.centerBox"
            >
            </vueCropper>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" size="small" slot="right">
                <label class="btn" for="uploads">选择封面</label>
                <input type="file" id="uploads" style="position:absolute; clip:rect(0 0 0 0);"
                       accept="image/png, image/jpeg, image/gif, image/jpg" @change="selectImg($event)">
              </el-button>
              <el-button type="primary" size="small" @click="cut('blob')">上传<i class="el-icon-upload el-icon--right"></i></el-button>
              <el-button type="primary" size="small" @click="handleClose">取消</el-button>
          </span>
          </el-dialog>
        </el-form-item>
        <el-form-item label="商品图片" prop="fileList">
          <el-upload
              class="upload-demo"
              drag
              :action="uploadImageUrl"
              :limit="3"
              :on-exceed="excessNumber"
              :before-upload="beforeImageUpload"
              :on-success="uploadImageSuccess"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="goodsInfoForm.fileList"
              list-type="picture"
              multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过13MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item prop="agree">
          <el-button @click.prevent="submit('goodsInfoForm')">提交</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {Message} from "element-ui";
import axios from "axios";
export default {
  name: "showGoodsInfo",
  beforeCreate() {
    this.$router.afterEach(()=>{
      window.scrollTo(0,0)
    })
  },
  data(){
    return{
      dialogVisible:false,
      options:{
        img:'',
        autoCrop:true,  //默认生成截图框
        fixedBox:true,  //固定截图框大小
        canMoveBox:false,    //截图框不能拖动
        autoCropWidth:200,  //截图框宽度
        autoCropHeight:200, //截图框高度
        centerBox:false,    //截图框被限制在图片里面
      },
      uploadImageUrl:'/api/goods/receiveImage',
      uploadCoverImageUrl:'/api/goods/receiveCoverImage',
      goodsInfoForm:{
        name:'',
        num:'',
        categoryId:'',
        typeId:'',
        categoryName:'',
        typeName:'',
        contact:'',
        contactType:1,
        content:'',
        price:'',
        originalPrice:'',
        coverImage:'',
        fileList: [],
      },
      goodsInfoFormRules:{
        name: [
          {required: true, message: '请输入商品名称', trigger: 'blur'},
        ],
        typeId: [
          {required: true, message: '请选择商品分类', trigger: 'blur'},
        ],
        contact: [
          {required: true, message: '请输入联系方式', trigger: 'blur'},
        ],
        price: [
          {required: true, message: '请输入售价', trigger: 'blur'},
        ],
        fileList: [
          {required: true, message: '至少上传一张照片', trigger: 'blur'},
        ],
        coverImage: [
          {required: true, message: '请上传一张封面', trigger: 'blur'},
        ],
      },
    }
  },
  computed:{
    ...mapState({
      categoryList:(state)=>{
        return state.home.categoryList
      }
    }),
  },
  methods:{
    //提交商品发布表单
    submit(form){
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          let {
            name, num, categoryId, typeId, contact,
            contactType, content, price, originalPrice, id, coverImage
          } = this.goodsInfoForm
          let image1 = this.goodsInfoForm.fileList[0];
          let image2 = this.goodsInfoForm.fileList[1]; let image3 = this.goodsInfoForm.fileList[2]
          num = num === '' ? '1' : num;
          if (image1 !== undefined){
            if (image1.response !== undefined){
              image1 = image1.response.data
            }else {
              image1 = image1.url
            }
            if (image2 !== undefined){
              if (image2.response !== undefined){
                image2 = image2.response.data
              }else {
                image2 = image2.url
              }
              if (image3 !== undefined){
                if (image3.response !== undefined){
                  image3 = image3.response.data
                }else {
                  image3 = image3.url
                }
              }
            }
          }
          try {
            if (contactType === 1){
              await this.$store.dispatch('updateReleaseGoods', {name, num, categoryId, typeId,
                'contactQq':contact, content, price, originalPrice, image1, image2, image3, id, coverImage})
            }else {
              await this.$store.dispatch('updateReleaseGoods', {name, num, categoryId, typeId,
                'contactWx':contact, content, price, originalPrice, image1, image2, image3, id, coverImage})
            }
          } catch (e) {
            console.log(e.message)
          }finally {
            await this.$router.push("/myGoods");
          }
        } else {
          console.log('表单有误')
        }
      })
    },
    //上传数量超出限制
    excessNumber() {
      Message({
        message: '最多上传3张图片',
        type: 'warning',
      });
    },
    //上传图片之前进行校验
    beforeImageUpload(file){
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 13;
      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG格式 或 PNG格式 !');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 13MB!');
      }
      return isJPG && isLt2M;
    },
    //上传图片成功
    async uploadImageSuccess(result, file, fileList){
      this.goodsInfoForm.fileList = fileList
      if (result.state === 200){
        Message({
          message: result.message,
          type: 'success'
        });
      }else {
        Message({
          message: result.message,
          type: 'warning'
        });
      }
      console.log(this.goodsInfoForm.fileList)
    },
    //移除图片
    handleRemove(file, fileList) {
      this.goodsInfoForm.fileList = fileList
    },
    //预览
    handlePreview(file) {
      console.log('handlePreview',file);
    },
    //选择商品分类
    selectCategory(categoryId,typeId,categoryName,typeName){
      this.goodsInfoForm.categoryId = categoryId
      this.goodsInfoForm.typeId = typeId
      this.goodsInfoForm.categoryName = categoryName
      this.goodsInfoForm.typeName = typeName
    },
    //选择封面图片
    selectImg (e) {
      let file = e.target.files[0]
      if (!/\.(jpg|jpeg|png|JPG|PNG)$/.test(e.target.value)) {
        this.$message({
          message: '图片类型要求：jpeg、jpg、png',
          type: "error"
        });
        return false
      }
      //转化为blob
      let reader = new FileReader()
      reader.onload = (e) => {
        let data
        if (typeof e.target.result === 'object') {
          data = window.URL.createObjectURL(new Blob([e.target.result]))
        } else {
          data = e.target.result
        }
        this.options.img = data
      }
      //转化为base64
      reader.readAsDataURL(file)
    },
    //确认截图,上传
    cut(){
      this.dialogVisible=false
      let formData = new FormData();
      this.$refs.cropper.getCropBlob(res=>{
        //res是裁剪后图片的bolb对象
        formData.append("file",res,'myCover.jpg');
        axios.post(this.uploadCoverImageUrl,formData,
            {contentType: false, processData: false, headers:{'Content-Type': 'multipart/form-data'}}
        ).then(res=>{
          this.goodsInfoForm.coverImage = res.data.data
          if (res.data.state === 200){
            Message({
              message: res.data.message,
              type: 'success'
            });
          }else {
            Message({
              message: res.data.message,
              type: 'warning'
            });
          }
        })
      })
    },
    //控制弹出层关闭
    handleClose(){
      this.dialogVisible=false
    },
  },
  mounted() {
    let showInfoInShowGoodsInfo = this.$store.state.releaseGoods.showInfoInShowGoodsInfo
    this.goodsInfoForm.name = showInfoInShowGoodsInfo.name
    this.goodsInfoForm.id = showInfoInShowGoodsInfo.id
    this.goodsInfoForm.num = showInfoInShowGoodsInfo.num
    this.goodsInfoForm.categoryId = showInfoInShowGoodsInfo.categoryId
    this.goodsInfoForm.typeId = showInfoInShowGoodsInfo.typeId
    this.goodsInfoForm.categoryName = showInfoInShowGoodsInfo.categoryName
    this.goodsInfoForm.typeName = showInfoInShowGoodsInfo.typeName
    this.goodsInfoForm.contact = showInfoInShowGoodsInfo.contact
    this.goodsInfoForm.coverImage = showInfoInShowGoodsInfo.coverImage
    if (showInfoInShowGoodsInfo.contactQq){
      this.goodsInfoForm.contact = showInfoInShowGoodsInfo.contactQq
      this.goodsInfoForm.contactType = 1
    }else {
      this.goodsInfoForm.contact = showInfoInShowGoodsInfo.contactWx
      this.goodsInfoForm.contactType = 2
    }
    this.goodsInfoForm.content = showInfoInShowGoodsInfo.content
    this.goodsInfoForm.price = showInfoInShowGoodsInfo.price
    this.goodsInfoForm.originalPrice = showInfoInShowGoodsInfo.originalPrice
    if (showInfoInShowGoodsInfo.image1)
      this.goodsInfoForm.fileList.push({name:showInfoInShowGoodsInfo.image1.split('/')[4], url: showInfoInShowGoodsInfo.image1})
    if (showInfoInShowGoodsInfo.image2)
      this.goodsInfoForm.fileList.push({name:showInfoInShowGoodsInfo.image2.split('/')[4], url: showInfoInShowGoodsInfo.image2})
    if (showInfoInShowGoodsInfo.image3)
      this.goodsInfoForm.fileList.push({name:showInfoInShowGoodsInfo.image3.split('/')[4], url: showInfoInShowGoodsInfo.image3})
  },
}
</script>

<style scoped lang="less">
.myPage {
  width: 1515px;
  height: 1500px;
  margin: 0 auto;
}
/deep/.inputDeep .el-input__inner{
  border:0;
}
//.inputDeep>>>.el-input__inner {
//  border: 0;
//}
.temp {
  width: 600px;
  height: 50px;
  margin: 0 auto;
}
.myStyle {
  width: 700px;
  height: 1400px;
  border: 1px solid rgb(223, 223, 223);
  margin: 0 auto;
}


.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
