<template>
  <div class="notice-detail-box">
    <el-page-header class="notice-page-header" @back="goBack" title="返回">
      <template #content>
        <span class="text-large font mr-3"> {{ title }} </span>
      </template>
    </el-page-header>
    <div class="notice-content">
      <div class="notice-title">{{ articleDetail.title }}</div>
      <el-divider class="notice-divider"/>
      <div class="notice-description">
        <p>来自 - {{ articleDetail.professionName }}&nbsp;{{ articleDetail.author }}&nbsp;&nbsp;&nbsp;&nbsp;创建时间 -
          {{ articleDetail.createTime }}</p>
      </div>
      <div v-html="articleDetail.htmlContent"></div>
    </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import {getArticleDetail} from "@/api/article";

export default {
  name: "CommonArticleDetail",
  props: {
    title: String
  },
  data() {
    return {
      articleDetail: {
        aid: "",
        author: "",
        title: "",
        createTime: "",
        htmlContent: "",
        professionName: ""
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 获取文章详情内容
    doGetArticleDetail() {
      let aid = this.$route.params.aid
      getArticleDetail(aid).then(resp => {
        if (resp.success) {
          this.articleDetail = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
    },
  },
  mounted() {
    this.doGetArticleDetail()
  }
}
</script>

<style scoped>
.notice-detail-box {
  width: 60%;
  height: 100%;
  float: right;
  margin-right: 10%;
  margin-bottom: 100px;
  /*background-color: red;*/
  z-index: 0;
}

.font {
  font-size: 20px;
}

.notice-page-header {
  margin-top: 80px;
  margin-bottom: 30px;
}

.notice-content {
  /*background-color: red;*/
}

.notice-title {
  font-size: 35px;
  font-weight: normal;
  letter-spacing: 3px;
  height: 60px;
  line-height: 60px;
  margin: 20px 0;
  text-align: center;
}

.notice-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  z-index: 10;
  margin: 0 auto;
}

.notice-description {
  color: #7a7878;
  font-size: 14px;
  letter-spacing: 2px;
  margin: 20px 0;
  width: 100%;
  text-align: center;
}
</style>
