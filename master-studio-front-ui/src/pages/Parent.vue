<template>
  <TitleImage :nav-detail="navDetail"/>
  <div class="articles-box">
    <router-view></router-view>
    <div class="nav-box">
      <nav class="nav-list">
        <div class="nav-title" :title="navDetail.navName">{{ handleNavTitle(navDetail.navName) }}</div>
        <ul>
          <li
              v-for="nav in navList"
              :key="nav.nid"
              @click="clickNavLi($event, nav)"
              :class="{'nav-active': defaultActivePath(nav.path)}"
              :title="nav.navName"
          >{{ handleNavTitle(nav.navName) }}
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import $ from 'jquery';
import TitleImage from "@/components/common/TitleImage";
import ArticlesList from "@/components/article/ArticlesList";
import {children, getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "ParentPage",
  data() {
    return {
      navDetail: {},
      navList: [],
    }
  },
  components: {
    ArticlesList,
    TitleImage,
  },
  computed: {},
  methods: {
    defaultActivePath(navPath) {
      let path = this.$route.path
      let childPath = path.split('/')[2]
      return navPath === childPath
    },
    clickNavLi(e, nav) {
      // 取消之前选中的标签
      let oldActiveLi = document.querySelector('.nav-active')
      if (oldActiveLi !== null) {
        oldActiveLi.removeAttribute('class')
      }
      // 获取点击的dom对象
      e.currentTarget.setAttribute("class", "nav-active");
      // 进行路由跳转
      this.$router.push({path: `/${this.navDetail.path}/${nav.path}`})
    },
    initMethod() {
      let path = this.$route.path
      let indexOf = path.indexOf('/', 1)
      let parentPath = path.substring(1, indexOf)
      this.doGetNavByPath(parentPath)
      this.doGetChildren(parentPath)
    },
    // 根据路径获取栏目信息
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
    // 获取本栏目下的所有子栏目
    doGetChildren(path) {
      children(path).then(resp => {
        if (resp.success) {
          this.navList = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
    },
    // 处理栏目标题，长度利于界面显示
    handleNavTitle(title) {
      if (!title) return ""
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
