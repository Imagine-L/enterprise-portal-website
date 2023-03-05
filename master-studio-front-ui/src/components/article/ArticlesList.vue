<template>
  <div class="articles-list">
    <div class="navigation-title">{{ navDetail.navName }}</div>
    <el-divider class="navigation-divider"/>
    <div class="navigation-img-box">
      <img :src="navDetail.image" class="navigation-img" alt="栏目图片">
    </div>
    <ul class="articles-list-ul">
      <li
          v-for="(article, index) in articleList"
          :key="article.aid"
          class="list-li"
          @click="$router.push({path: `${navDetail.path}/article/${article.aid}`})"
          :class="{'left-li': index % 2 === 0, 'right-li': index % 2 === 1}"
      >
        <p class="article-title" :title="article.title">{{ handleTextTitle(article.title, 12) }}</p>
        <p class="article-description" :title="article.summary">{{ handleTextTitle(article.summary, 40) }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
import {Promotion} from '@element-plus/icons-vue'
import {children, getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "ArticlesList",
  data() {
    return {
      navDetail: {},
      articleList: [],
      parentPath: '',
    }
  },
  methods: {
    initMethod() {
      let path = this.$route.path
      let lastIndex = path.lastIndexOf('/')
      path = path.substring(lastIndex + 1);
      this.parentPath = path.substring(1, lastIndex)
      // console.log(`parentPath: ${path}`)
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
        // this.doGetArticleDetail(aid)
      }).catch((err) => console.error(err))
    },
    // 处理文章文本，长度利于界面显示
    handleTextTitle(text, size) {
      if (text.length <= size) {
        return text
      }
      text = text.substring(0, size) + '...'
      return text
    }
  },
  components: {
    Promotion
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

.articles-list {
  width: 60%;
  height: 100%;
  float: right;
  margin-right: 10%;
  z-index: 0;
}

.navigation-title {
  font-size: 35px;
  font-weight: normal;
  letter-spacing: 3px;
  height: 60px;
  line-height: 60px;
  /*margin-top: ;*/
  margin-top: 70px;
  margin-bottom: 30px;
  /*margin: 30px 0;*/
}

.navigation-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  margin-bottom: 40px;
  z-index: 10;
}

.navigation-img-box {
  width: 100%;
  text-align: center;
}

.navigation-img {
  width: 100%;
}

.articles-list-ul {
  display: flex;
  justify-content: flex-start;
  width: 100%;
  flex-wrap: wrap;
  margin-top: 2%;
  margin-bottom: 80px;
}

.list-li {
  width: 45%;
  border-bottom: 2px solid #781cb6;
  padding: 10px;
  margin-bottom: 30px;
  position: relative;
  background: url("https://www.tsinghua.edu.cn/image/rr6.png") no-repeat right center;
}

.list-li:hover {
  transition: 0.4s;
  background: url("https://www.tsinghua.edu.cn/image/rr6.png") no-repeat 97% center;
  cursor: pointer;
}

.articles-list-ul .left-li {
  margin-right: 5%;
}

.articles-list-ul .right-li {
  margin-left: 5%;
}

.article-title {
  font-size: 24px;
  color: #781cb6;
  display: inline-block;
  padding-right: 10px;
  font-weight: normal;
  padding-bottom: 10px;
}

.article-title:hover {
  color: #a253d7;
}

.article-description {
  font-size: 14px;
  color: #333333;
  padding-right: 10px;
  width: 95%;
}
</style>
