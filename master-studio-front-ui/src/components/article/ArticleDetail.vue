<template>
  <div class="article-content">
    <div class="article-title">{{articleDetail.title}}</div>
    <el-divider class="article-divider"/>
    <div class="article-description">
      <p>来自 - {{articleDetail.professionName}}&nbsp;{{articleDetail.author}}&nbsp;&nbsp;&nbsp;&nbsp;创建时间 - {{articleDetail.createTime}}</p>
    </div>
    <div v-html="articleDetail.htmlContent"/>
  </div>
</template>

<script>
export default {
  name: "ArticleDetail",
  props: {
    articleDetail: {
      aid: "",
      author: "",
      title: "",
      createTime: "",
      htmlContent: "",
      professionName: ""
    }
  },
  data() {
    return {}
  }
}
</script>

<style scoped>
.article-title {
  font-size: 35px;
  font-weight: normal;
  letter-spacing: 3px;
  height: 60px;
  line-height: 60px;
  margin-top: 80px;
  margin-bottom: 30px;
  /*margin: 30px 0;*/
}

.article-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  z-index: 10;
}

.article-description {
  color: #7a7878;
  font-size: 14px;
  letter-spacing: 2px;
  margin: 30px 0;
}

.article-content {
  width: 60%;
  height: 100%;
  float: right;
  margin-right: 10%;
  margin-bottom: 100px;
  z-index: 0;
}
</style>
