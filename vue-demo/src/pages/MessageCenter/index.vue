<template>
  <div class="myPage">
    <el-tabs @tab-click="dd" v-model="activeName" type="height: 200px;" tab-position="right">
      <el-tab-pane label="回复我的6" name="first">
        <div class="cart-complete">
          <replyComment :commentList="commentList"></replyComment>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的商品" name="second">
        <div class="cart-complete">
          <goodsComment :commentList="commentList"></goodsComment>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import replyComment from './ReplyComment/Comment'
import goodsComment from './GoodsComment/Comment'
import {reqReplyComment} from "@/api";
export default {
  name: "messageCenter",
  components: {
    replyComment,goodsComment
  },
  data () {
    return {
      commentList:[
        {
          id:1,
          commentUser:{
            id:1,
            nickName:'花非花',
            avatar:'http://qzapp.qlogo.cn/qzapp/101483738/6637A2B6611592A44A7699D14E13F7F7/50'
          },
          content:"<a style='text-decoration:none;color: #409eff ' href='https://blog.csdn.net/abcwanglinyong/'>我的CSDN博客地址</a>[害羞][害羞][害羞]<br/>"
              +"我的微信公众号：<br/>",
          createDate:'2019-9-23 17:36:02',
          childrenList:[
            {
              id:2,
              commentUser:{
                id:2,
                nickName:'坏菠萝',
                avatar:''
              },
              targetUser:{
                id:1,
                nickName:'花非花',
                avatar:'http://qzapp.qlogo.cn/qzapp/101483738/6637A2B6611592A44A7699D14E13F7F7/50'
              },
              content:'真的就很棒！很Nice!',
              createDate:'2019-9-23 17:45:26'
            }

          ]
        },
      ],
      activeName: '',
    }
  },
  methods: {
    async doChildSend(content,targetUser,parentId,goodsId){
      try {
        let visited = targetUser.id === this.userDate.uid ? '1' : '0'
        let result = await reqReplyComment({content,targetUser,parentId,goodsId,visited})
        if (result.state === 200){
          this.$message({
            type: 'success',
            message: result.message
          });
          await this.getCommentList()
        }else if (result.state === 4010){
          this.$message({
            type: 'warning',
            message: result.message
          });
        }else {
          this.$message({
            type: 'error',
            message: result.message
          });
        }
      }catch (e) {
        console.log(e.message)
      }
    },
    getQuery() {
      this.activeName = this.$route.query.activeName;
    },
    dd(a,b,c,d){
      console.log(a.name)
    }
  },
  watch: {
    '$route'(){
      this.getQuery();
    }
  },
  mounted() {
    this.getQuery();
  },
}
</script>

<style scoped>
.myPage {
  width: 100%;
  height: 500px;
  margin: 0 auto;
}
.cart-complete {
  width: 1200px;
  margin: 0 auto;
}
</style>
