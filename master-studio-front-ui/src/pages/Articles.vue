<template>
  <TitleImage :nav-detail="navDetail"/>
  <div class="articles-box">
    <ArticleDetail :article-detail="articleDetail"/>
    <div class="nav-box">
      <nav class="nav-list">
        <div class="nav-title">所有文章</div>
        <ul>
          <li
              v-for="(article, index) in articleList"
              :key="article.aid"
              @click="clickNavLi($event, article)"
              :class="{'nav-active': index === 0}"
              :title="article.title"
          >{{ handleArticleTitle(article.title) }}
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import TitleImage from "@/components/common/TitleImage";
import ArticleDetail from "@/components/article/ArticleDetail";
import ArticlesList from "@/components/article/ArticlesList";
import {children, getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";
import {getArticleDetail} from "@/api/article";

export default {
  name: "ArticlesPage",
  data() {
    return {
      navDetail: {},
      articleList: [],
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
  components: {
    ArticlesList,
    ArticleDetail,
    TitleImage,
  },
  methods: {
    initMethod() {
      let path = this.$route.path
      path = path.substring(path.lastIndexOf('/') + 1);
      this.doGetNavByPath(path)
      this.doGetChildren(path)
    },
    // 根据路径获取信息
    doGetNavByPath(path) {
      getNavByPath(path).then(resp => {
        if (resp.success) {
          this.navDetail = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
    },
    // 获取本栏目下的所有文章
    doGetChildren(path) {
      children(path).then(resp => {
        return new Promise((resolve, reject) => {
          if (resp.success) {
            this.articleList = resp.data
            resolve(this.articleList[0].aid)
          } else {
            ElMessage.warning('当前网络延迟较高，请稍后重试')
            reject((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        })
      }).then(aid => {
        this.doGetArticleDetail(aid)
      }).catch((err) => console.error(err))
    },
    // 获取文章详情内容
    doGetArticleDetail(aid) {
      getArticleDetail(aid).then(resp => {
        if (resp.success) {
          this.articleDetail = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.log((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      })
    },
    // 点击文章选项卡
    clickNavLi(e, article) {
      // 取消之前选中的标签
      let oldActiveLi = document.querySelector('.nav-active')
      if (oldActiveLi !== null) {
        oldActiveLi.removeAttribute('class')
      }
      // 获取点击的dom对象
      e.currentTarget.setAttribute("class", "nav-active");
      this.doGetArticleDetail(article.aid)
    },
    // 处理文章标题，长度利于界面显示
    handleArticleTitle(title) {
      if (title.length <= 10) {
        return title
      }
      title = title.substring(0, 10) + '...'
      return title
    }
  },
  mounted() {
    this.initMethod()
  }
}
</script>

<style scoped>

* {
  margin: 0;
  padding: 0;
  list-style: none;
  box-sizing: border-box;
}

.articles-box {
  position: relative;
}

.nav-box {
  width: 300px;
  margin-left: 5%;
  background-color: #ecf0f9;
  z-index: 10;
}

.nav-list {
  text-align: right;
  font-family: '微软雅黑', serif;
  margin-bottom: 100px;
  padding-left: 60px;
  padding-top: 80px;
  padding-bottom: 500px;
  background: url('@/assets/nav.png') no-repeat bottom;
  background-size: 100% auto;
  font-weight: normal;
  z-index: 10;
}

.nav-title {
  height: 80px;
  text-align: left;
  background-color: #b93c28;
  /*background-image: linear-gradient(270deg, #d2b1ed 0%, #781cb6 100%);*/
  color: #fff;
  font-size: 24px;
  padding-top: 23px;
  padding-left: 20px;
}

.nav-list > ul > li {
  height: 52px;
  line-height: 52px;
  text-align: left;
  background-color: #dee2eb;
  margin: 5px 0;
  font-size: 16px;
  padding-left: 20px;
}

.nav-active {
  background-color: #73a6da !important;
  color: white;
}

.nav-list > ul > li:hover {
  background-color: #73a6da;
  color: white;
  transition: 0.3s;
  cursor: pointer;
}
</style>
