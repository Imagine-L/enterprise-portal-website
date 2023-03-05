<template>
  <el-menu
      :default-active="defaultActive"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      active-color="#fff"
      text-color="white"
      active-text-color="#337ecc"
      router
  >
    <div style="position: absolute; top:20px; left: 30px; ">
      <el-image :src="require('@/assets/logo.png')" alt="LOGO" style="width: 350px;vertical-align: middle"></el-image>
<!--      <span style="font-size: 40px; color: white; letter-spacing: 10px; margin: 0 20px">大师工作室</span>-->
    </div>
    <div class="flex-grow"/>
    <el-menu-item index="/">首页</el-menu-item>
    <template v-for="nav in navList" :key="nav.nid">
      <template v-if="nav.navType === 0">
        <!-- 如果是父栏目 -->
        <el-sub-menu :index="nav.path">
          <template #title>{{ nav.navName }}</template>
          <el-menu-item
              v-for="navChild in nav.children"
              :key="navChild.nid"
              :index="`/${nav.path}/${navChild.path}`"
              class="menu-sub-item"
          >{{ navChild.navName }}
          </el-menu-item>
        </el-sub-menu>
      </template>
      <template v-else>
        <el-menu-item :index="`/${nav.path}`">{{ nav.navName }}</el-menu-item>
      </template>
    </template>
  </el-menu>
</template>

<script>

export default {
  name: "HeaderComponent",
  data() {
    return {
    }
  },
  computed: {
    defaultActive() {
      return this.$store.getters.getCurrentPath
    },
    navList() {
      return this.$store.getters.getNavList
    },
  },
  methods: {
  },
  mounted() {
  }
}
</script>

<style>

.flex-grow {
  flex-grow: 1;
}

.el-menu-demo {
  background-color: transparent;
  border: none;
  font-weight: bold;
  padding: 10px 20px;
}

.el-menu-item, .el-sub-menu * {
  font-size: 17px !important;
}

.el-menu-item.is-active {
  background-color: transparent !important;
}

.el-menu-item:hover {
  background-color: transparent !important;
  color: #337ecc !important;
}

.el-sub-menu:hover {
  background-color: transparent !important;
  color: #337ecc !important;
}

.el-sub-menu__title.el-tooltip__trigger.el-tooltip__trigger:hover {
  color: #337ecc !important;
  background-color: transparent;
}

/* 测试 */
/*.el-sub-menu > * {*/
/*  color: black !important;*/
/*}*/
.menu-sub-item {
  color: #7a7878 !important;
  font-size: 15px !important;
}

.el-menu--horizontal>.el-menu-item.is-active {
  border: none;
}

.el-menu--horizontal>.el-sub-menu.is-active .el-sub-menu__title {
  border: none;
}
</style>
