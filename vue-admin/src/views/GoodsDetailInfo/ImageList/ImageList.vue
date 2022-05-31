<template>
  <!-- 左侧放大镜区域 -->
  <div class="previewWrap">
    <!--放大镜效果-->
    <div class="spec-preview">
      <img :src="images[imageIndex]" />
      <div class="event" @mousemove="handler"></div>
      <div class="big">
        <img :src="images[imageIndex]" ref="big"/>
      </div>
      <div class="mask" ref="mask"></div>
    </div>
    <!--下方的缩略图-->
    <div class="specScroll">
      <!--左按钮-->
      <a class="prev" @click="toPreviousPicture">&lt;</a>
      <!-- 中间可滑动区域 -->
      <div class="items">
        <div class="itemsCon">
          <img v-for="(image,index) in images" :key="index" :src="image" alt="" @click="changeShowImage(index)">
        </div>
      </div>
      <!--右按钮-->
      <a class="next" @click="toNextPicture">&gt;</a>
    </div>
  </div>
</template>

<script>

  export default {
    name: "ImageList",
    props: ['images'],
    data(){
      return{
        imageIndex:0,
      }
    },
    methods:{
      changeShowImage(index){
        this.imageIndex = index
      },
      handler(event){
        let mask = this.$refs.mask
        let big = this.$refs.big
        let left = event.offsetX - mask.offsetWidth/2
        let top = event.offsetY - mask.offsetHeight/2
        //约束范围
        if (left <= 0) left = 0;
        if (left >= mask.offsetWidth) left = mask.offsetWidth
        if (top <= 0) top = 0
        if (top >= mask.offsetHeight) top = mask.offsetHeight
        mask.style.left = left + 'px'
        mask.style.top = top + 'px'
        big.style.left = -2 * left + 'px'
        big.style.top = -2 * top + 'px'
      },
      //上一张图片
      toPreviousPicture(){
        if (this.imageIndex > 0){
          this.imageIndex--
        }
      },
      //下一张图片
      toNextPicture(){
        if (this.imageIndex < this.images.length - 1){
          this.imageIndex++
        }
      },
    },
  }
</script>

<style lang="less" scoped>
.spec-preview {
  position: relative;
  width: 400px;
  //height: 400px;
  border: 1px solid #ccc;

  img {
    width: 100%;
    height: 100%;
  }

  .event {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 998;
  }

  .mask {
    width: 50%;
    height: 50%;
    background-color: rgba(0, 255, 0, 0.3);
    position: absolute;
    left: 0;
    top: 0;
    display: none;
  }

  .big {
    width: 100%;
    height: 100%;
    position: absolute;
    top: -1px;
    left: 100%;
    border: 1px solid #aaa;
    overflow: hidden;
    z-index: 998;
    display: none;
    background: white;

    img {
      width: 200%;
      max-width: 200%;
      height: 200%;
      position: absolute;
      left: 0;
      top: 0;
    }
  }

  .event:hover~.mask,
  .event:hover~.big {
    display: block;
  }
}



.previewWrap{
  float: left;
  width: 400px;
  position: relative;
  .preview {
    width: 400px;
    //height: 400px;
    border: 1px solid #DFDFDF;
    .jqzoom {
      cursor: pointer;
      width: 400px;
      //height: 400px;
      position: relative;
      img{
        width: 100%;
      }
      .mask{
        width: 200px;
        height: 200px;
        background: rgba(255,255,255,.5);
        position: absolute;
        left: 0;
        top: 0;
        border: 1px solid #ddd;
      }
    }
    .maxbox{
      width: 400px;
      height: 400px;
      position: absolute;
      left: 420px;
      top: 0px;
      overflow: hidden;
      z-index: 20;
      border: 1px solid #ddd;
      img{
        width: 800px;
        height: 800px;
        display: block;
      }
    }
  }
  .specScroll{
    margin-top: 5px;
    width: 400px;
    overflow: hidden;
    .prev,.next{
      text-align: center;
      width: 10px;
      height: 54px;
      line-height: 54px;
      border: 1px solid #CCC;
      background: #EBEBEB;
      cursor: pointer;
    }
    .prev{
      float: left;
      margin-right: 4px;
    }
    .next{
      float: right;
    }
    .items{
      float: left;
      position: relative;
      width: 372px;
      height: 56px;
      overflow: hidden;
      .itemsCon{
        position: absolute;
        width: 9999px;
        height: 56px;
        left: 0;
        img{
          float: left;
          text-align: center;
          border: 1px solid #CCC;
          padding: 2px;
          //width: 50px;
          height: 50px;
          margin-right: 20px;
        }
      }
    }
  }
}
</style>