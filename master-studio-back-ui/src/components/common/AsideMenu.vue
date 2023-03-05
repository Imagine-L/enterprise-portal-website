<template>
  <el-menu
      :default-active="activeIndex"
      active-text-color="#79bbff"
      background-color="#303133"
      class="el-menu-vertical-demo"
      text-color="#fff"
      router
  >
    <el-menu-item index="/personal">
      <el-icon>
        <User/>
      </el-icon>
      <span>个人中心</span>
    </el-menu-item>
    <el-sub-menu index="system">
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="/user">用户管理</el-menu-item>
      <el-menu-item index="/profession">岗位管理</el-menu-item>
      <el-menu-item index="/files">文件管理</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="content">
      <template #title>
        <el-icon>
          <icon-menu/>
        </el-icon>
        <span>内容管理</span>
      </template>
      <el-menu-item index="/navigation">栏目管理</el-menu-item>
      <el-menu-item index="/page">页面管理</el-menu-item>
      <el-menu-item index="/article">文章发布</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/log">
      <el-icon>
        <document/>
      </el-icon>
      <span>日志监控</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  User
} from '@element-plus/icons-vue'

export default {
  name: "AsideMenu",
  data() {
    return {
      activeIndex: ''
    }
  },
  components: {
    Document,
    IconMenu,
    Location,
    User
  },
  methods: {
  },
  mounted() {
    let path = this.$route.path
    let indexOf = path.indexOf('/', 1)
    if (indexOf === -1) {
      this.activeIndex = path
    } else {
      this.activeIndex = path.substring(0, indexOf)
    }
  }
}
</script>

<style scoped>

</style>
