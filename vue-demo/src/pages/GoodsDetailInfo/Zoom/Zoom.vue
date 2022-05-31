<template>
  <div class="preview">
    <div class="jqzoom">
      <img :src="image" alt=""/>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Zoom",
    props: {
      image: '',
    },
  }
</script>

<style lang="less">

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
          width: 50px;
          height: 50px;
          margin-right: 20px;
        }
      }
    }
  }
}

</style>