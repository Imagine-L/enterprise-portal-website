<template>
  <!-- 栏目 -->
  <div class="home-title">
    <p style="margin: 0; padding: 0">{{navDetail.navName}}</p>
  </div>
  <el-tabs tab-position="left" :stretch="false">
    <el-tab-pane :label="childNav.navName" v-for="childNav in navDetail.children" :key="childNav.nid">
      <div class="article-box">
        <ul>
          <li v-for="article in articleModule[childNav.nid]" :key="article.aid" class="article-li">
            <el-card class="box-card" shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>{{article.title}}</span>
                  <el-button class="button" text @click="readMore(`/${navDetail.path}/${childNav.path}/article/${article.aid}`)">&nbsp;&nbsp;&nbsp;阅读更多&nbsp;&nbsp;&nbsp;</el-button>
                </div>
              </template>
              <el-scrollbar height="150px">
                <div class="article-description">{{article.summary}}</div>
              </el-scrollbar>
            </el-card>
          </li>
        </ul>
      </div>
      <el-row justify="center" :gutter="30" style="clear: both">
        <el-col :span="24" style="text-align: center;margin-bottom: 0">
          <el-button type="primary" size="large" @click="readMore(`/${navDetail.path}/${childNav.path}`)">查看更多</el-button>
        </el-col>
      </el-row>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import {Management} from '@element-plus/icons-vue'
import {children} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "HomePlate",
  props: {
    navDetail: {
      "children": [],
      "level": 0,
      "navName": "",
      "navType": 0,
      "nid": 0,
      "notice": true,
      "path": ""
    }
  },
  data() {
    return {
      articleModule: {}
    }
  },
  computed: {
  },
  methods: {
    initMethod() {
      if (this.navDetail.children === null || this.navDetail.children.size === 0) {
        return
      }
      for (let i in this.navDetail.children) {
        let child = this.navDetail.children[i]
        children(child.path).then(resp => {
          if (resp.success) {
            let articleList = []
            if (resp.data !== null && resp.data.length !== 0) {
              articleList = resp.data.slice(0, 6)
            }
            this.articleModule[child.nid] = articleList
          } else {
            ElMessage.warning('当前网络延迟较高，请稍后重试')
            console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
      }
    },
    readMore(path) {
      this.$router.push({path})
    }
  },
  components: {
    Management
  },
  mounted() {
    this.initMethod()
  }
}
</script>

<style scoped>
.el-tab-pane {
  margin: 0;
  padding: 0;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header > span {
  font-family: '微软雅黑', serif;
  font-size: 17px;
}

.card-header > .el-button {
  color: #918d8d;
}

.card-header > .el-button:hover {
  color: #2f72d7
}

.article-description {
  color: #797777;
  font-family: "仿宋", serif;
}
.el-row {
  margin-bottom: 20px;
}

.home-title {
  font-size: 35px;
  height: 140px;
  line-height: 140px;
  width: 100%;
  color: white;
  letter-spacing: 10px;
  background: url("@/assets/home-showed1.png") no-repeat;
  background-size: 100% auto;
  position: relative;
  text-align: center;
  margin-bottom: 30px;
}

li {
  list-style: none;
}

.article-box {
  width: 100%;
  height: 100%;
}

.article-box ul {

}

.article-li {
  width: 30%;
  height: 30%;
  float: left;
  margin: 10px 0.5%;
}
</style>
