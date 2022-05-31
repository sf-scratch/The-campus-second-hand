<template>
  <div class="myPage">
    <div class="list">
      <div style="float: left;width: 50%;">
        <el-table
            :data="BulletinBoardMessageList"
            height="530"
            stripe
            style="width: 100%">
          <el-table-column
              label="公告栏"
              width="350">
            <template slot-scope="scope">
              <a href="javascript:0;" class="buttonText" @click="systemMessageDetail(scope.row)">{{scope.row.title}}</a>
            </template>
          </el-table-column>

          <el-table-column
              prop="createDate"
              label="发布时间"
              width="200">
          </el-table-column>
        </el-table>
      </div>

      <div style="margin-left: 50%; width: 50%;">
        <el-table
            :data="commonMessageList"
            height="530"
            stripe
            style="width: 100%">
          <el-table-column
              label="通知"
              width="350">
            <template slot-scope="scope">
              <a href="javascript:0;" class="buttonText" @click="systemMessageDetail(scope.row)">{{scope.row.title}}</a>
            </template>
          </el-table-column>

          <el-table-column
              prop="createDate"
              label="发布时间"
              width="200">
            <!--          <template slot="header" slot-scope="scope">-->
            <!--            <a href="" target="_blank">More<<</a>-->
            <!--          </template>-->
          </el-table-column>
        </el-table>

        <el-drawer
            :visible.sync="drawer"
            :with-header="false">
          <h1 style="text-align: center">{{drawerTitle}}</h1>
          <mavon-editor defaultOpen="preview" :ishljs="true" :toolbarsFlag='false' :zIndex="0" :subfield="false" ref=md :value="drawerContent"></mavon-editor>
        </el-drawer>
      </div>
    </div>
  </div>

</template>

<script>
import {mapState} from "vuex";
import {reqSystemMessageList} from "@/api"
let socket;

export default {
  name: "systemAnnouncement",
  data() {
    return {
      BulletinBoardMessageList: [],
      commonMessageList: [],
      drawer:false,
      drawerTitle:'',
      drawerContent:'',
    }
  },
  methods:{
    async getSystemMessageList(){
      let result = await reqSystemMessageList()
      let list = result.data
      let list1 = []
      let list2 = []
      list.forEach(message => {
        if (message.contentType === 1){
          list1.push(message)
        }else {
          list2.push(message)
        }
      })
      this.BulletinBoardMessageList = list1
      this.commonMessageList = list2
    },
    systemMessageDetail(systemMessage){
      if (systemMessage.contentType === 1){
        this.drawer = true
        this.drawerTitle = systemMessage.title
        this.drawerContent = systemMessage.content
      }else {
        this.$notify({
          title: systemMessage.title,
          message: systemMessage.content,
        });
      }
    },
    createContent(remoteUser, nowUser, text) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
      let html
      // 当前用户消息
      if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
      } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
      }
      console.log(html)
      this.content += html;
    },
  },
  created() {
    this.getSystemMessageList()
  },
}
</script>

<style scoped lang="less">
.myPage{
  width: 100%;
}
a{
  color: #7ac804;
}
.list {
  width: 1200px;
  margin: 0 auto;
  padding-top: 20px;
}
</style>
