<template>
  <TitleImage :nav-detail="navDetail"/>
  <div class="notices-box">
    <!--    <NoticesList/>-->
    <router-view></router-view>
    <div class="nav-box">
      <nav class="nav-list">
        <div class="nav-title">通知年份</div>
        <ul>
          <li @click="clickNavLi($event, 'all')" :class="{'nav-active': defaultActivePath('all')}">全部通知</li>
          <li
              v-for="(date, index) in dateList"
              :key="index"
              @click="clickNavLi($event, date)"
              :class="{'nav-active': defaultActivePath(date)}"
          >{{ date }}&nbsp;年</li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import TitleImage from "@/components/common/TitleImage";
import NoticesList from "@/components/notice/NoticesList";
import {getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";
import {getNoticeDateList} from "@/api/article";


export default {
  name: "NoticesPage",
  data() {
    return {
      navDetail: {},
      dateList: []
    }
  },
  components: {
    NoticesList,
    TitleImage,
    // Paperclip,
  },
  methods: {
    // 激活的日期选项卡
    defaultActivePath(navPath) {
      let path = this.$route.path
      let childPath = path.split('/')[2]
      return navPath === childPath
    },
    clickNavLi(e, date) {
      // 取消之前选中的标签
      let oldActiveLi = document.querySelector('.nav-active')
      if (oldActiveLi !== null) {
        oldActiveLi.removeAttribute('class')
      }
      // 获取点击的dom对象
      e.currentTarget.setAttribute("class", "nav-active");
      this.$router.push({path: `/${this.navDetail.path}/${date}`})
    },
    initMethod() {
      let path = this.$route.path
      let paths = path.split('/')
      let parentPath = paths[1]
      let childPath = paths[2]
      this.doGetNavByPath(parentPath)
      this.doGetNoticeDateList()
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
    // 获取通知日期列表
    doGetNoticeDateList() {
      getNoticeDateList().then(resp => {
        if (resp.success) {
          this.dateList = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
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

.notices-box {
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
