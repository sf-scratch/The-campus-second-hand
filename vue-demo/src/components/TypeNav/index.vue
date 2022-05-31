<template>
  <div class="type-nav">
    <div class="container">
      <h2 class="all" @mouseenter="enterShow"  @mouseleave="leaveShow">全部商品分类</h2>
      <div class="myAll"></div>
      <el-tag
          :key="tag"
          v-for="tag in dynamicTags"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)">
        {{tag}}
      </el-tag>
      <nav class="nav">
      </nav>
      <el-collapse-transition name="sort" @mouseenter="enterShow" >
        <div class="sort" v-if="show" @mouseleave="leaveShow_sort">
          <div class="all-sort-list2">
            <div class="item" v-for="(category,index) in categoryList" :key="category.categoryId"
                 :class="{cur:currentIndex===category.categoryId}">
              <h3 @mouseenter="changeIndex(category.categoryId)" @mouseleave="leaveIndex()">
                <i :class="myIcon[index]">&nbsp;&nbsp;&nbsp;</i>
                <a href="javascript:void(0);" @click="goSearch" :data-temp-name="category.categoryName"
                   :data-category-id="category.categoryId" :data-category-name="category.categoryName"
                >{{category.categoryName}}</a>
              </h3>
              <div class="item-list clearfix">
                <el-row>
                  <el-col :span="12" v-for="(type) in category.types">
                    <div class="subitem">
                      <a href="javascript:void(0);" @click="goSearch" :data-temp-name="type.typeName"
                         :data-type-id="type.typeId" :data-type-name="type.typeName"
                         :data-category-name="category.categoryName">{{type.typeName}}</a>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
        </div>
      </el-collapse-transition>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: "TypeNav",
  components:{},
  data(){
    return{
      myIcon:[
          'el-icon-reading',
          'el-icon-bicycle',
          'el-icon-suitcase-1',
          'el-icon-refrigerator',
          'el-icon-mobile-phone',
          'el-icon-house',
          'el-icon-hot-water',
          'el-icon-baseball',
      ],
      show:true,
      currentIndex:-1,
      dynamicTags: [],
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
    // changeIndex:throttle(function (id) {
    //   this.currentIndex = id
    // }),
    changeIndex(id){
      this.currentIndex = id
    },
    async handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      await this.$store.dispatch('searchGoodsInfoList',{
        currentPage:this.$store.state.home.defaultCurrentPage,
        size:this.$store.state.home.defaultSize,
        keyword:this.$route.params.keyword,
      })
      let location = {name: "search",
        params:{
          'keyword':this.$route.params.keyword,
          'currentPage':this.$store.state.home.defaultCurrentPage.toString(),
          'size':this.$store.state.home.defaultSize.toString(),
        }}
      await this.$router.push(location)
    },
    leaveIndex(){
      this.currentIndex = -1
    },
    enterShow(){
        this.show = true
    },
    leaveShow(mouseEvent){
      if (this.$route.path !== '/home'){
        if (mouseEvent && mouseEvent.clientY < 94){
          this.currentIndex = -1
          this.show = false
        }
      }
    },
    // leaveShow_item_list(mouseEvent){
    //   if (this.$route.path !== '/home'){
    //     if (mouseEvent.screenX >= 150){
    //       this.currentIndex = -1
    //       this.show = false
    //     }
    //   }
    // },
    leaveShow_sort(){
      if (this.$route.path !== '/home'){
          this.currentIndex = -1
          this.show = false
      }
    },
    async goSearch(event){
      let {tempName, categoryId, typeId} = event.target.dataset
      if (tempName){
        //改标签
        this.dynamicTags = [tempName]
        //判断search参数
        let location = {'name':'search'}
        let query = {'categoryName':tempName}
        if (categoryId){
          query.categoryId = categoryId
        }else if (typeId){
          query.typeId = typeId
        }
        if (this.$route.params){
          location.params = this.$route.params
        }
        location.query = query
        //先获取信息再跳转，可以避免search的钩子函数的重复去获取信息
        await this.$store.dispatch('searchGoodsInfoList',{
          currentPage:this.$store.state.home.defaultCurrentPage,
          size:this.$store.state.home.defaultSize,
          keyword:this.$route.params.keyword,
          categoryId:categoryId,
          typeId:typeId,
        })
        await this.$router.push(location)
        this.currentIndex = -1
        this.show = false
      }
    },
  },
  mounted() {
    if (this.$route.path !== '/home'){
      this.show = false
    }
    if (this.$route.query.categoryName) this.dynamicTags.push(this.$route.query.categoryName)
  },
  beforeDestroy(){

  } ,
}
</script>

<style scoped lang="less">
.type-nav {
  border-bottom: 2px solid #1b53e1;

  .container {
    width: 100%;
    margin: 0 auto;
    display: flex;
    position: relative;

    .all {
      width: 150px;
      height: 34px;
      background-color: #1b53e1;
      line-height: 34px;
      text-align: center;
      color: #ffffff;
      font-size: 14px;
      font-weight: bold;
    }

    .myAll {
      width: 260px;
      height: 33px;
      line-height: 33px;
      text-align: center;
      color: #ffffff;
      font-size: 14px;
      font-weight: bold;
    }

    .nav {
      a {
        height: 45px;
        margin: 0 22px;
        line-height: 45px;
        font-size: 16px;
        color: #333;
      }
    }

    .sort {
      position: absolute;
      left: 0;
      top: 36px;
      width: 150px;
      height: 461px;
      //position: absolute;
      background: #fafafa;
      z-index: 999;

      .all-sort-list2 {
        .cur{
          background-color: deepskyblue;
        }
        .item {
          h3 {
            line-height: 30px;
            font-size: 14px;
            font-weight: 400;
            overflow: hidden;
            padding: 0 20px;
            margin: 0;

            a {
              color: #333;
            }
          }

          .item-list {
            display: none;
            position: absolute;
            width: 200px;
            min-height: 460px;
            background: #f7f7f7;
            left: 150px;
            border: 1px solid #ddd;
            top: 0;
            z-index: 9999 !important;


            .subitem {
              float: left;
              padding: 0 4px 0 8px;
              height: 20px;

              a{
                font: 12px/1.3 "Microsoft YaHei",Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif; /* 用 ascii 字符表示，使得在任何编码下都无问题 */
                color: #333;
              }

              dl {
                border-top: 1px solid #eee;
                padding: 6px 0;
                overflow: hidden;
                zoom: 1;

                &.fore {
                  border-top: 0;
                }

                dt {
                  float: left;
                  width: 54px;
                  line-height: 22px;
                  text-align: right;
                  padding: 3px 6px 0 0;
                  font-weight: 700;
                }

                dd {
                  float: left;
                  width: 415px;
                  padding: 3px 0 0;
                  overflow: hidden;

                  em {
                    float: left;
                    height: 14px;
                    line-height: 14px;
                    padding: 0 8px;
                    margin-top: 5px;
                    border-left: 1px solid #ccc;
                  }
                }
              }
            }
          }

          &:hover {
            .item-list {
              display: block;
            }
          }
        }
      }
    }


  }
}
</style>
