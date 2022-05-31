<template>
  <slide-verify
      :l="42"
      :r="10"
      :w="310"
      :h="155"
      :imgs="picArray"
      :show="false"
      slider-text="向右滑动完成验证"
      ref="slideverify"
      @success="onSuccess"
      @fail="onFail"
      @refresh="onRefresh"
  ></slide-verify>
</template>

<script>
export default {
  name: "SlideVerify",
  data(){
    return {
      picArray:[
        require("@/assets/defaultAvatar.png"),
      ],
    }
  },
  methods:{
    onSuccess() {//往父级传递验证通过的函数
      // this.$emit("success");
    },
    onReset() {//重置图片验证组件
      this.$refs.slideverify.reset();
    },
    onFail() {
      // this.$emit("fail");
    },
    onRefresh() {
      // this.$emit("refresh");
    },
  },
}
</script>

<style scoped>

</style>
