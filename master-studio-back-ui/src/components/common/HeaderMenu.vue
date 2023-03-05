<template>
  <el-menu
      background-color="#303133"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      active-color="#fff"
      text-color="#fff"
      router
  >
    <div class="header-tip">
      <el-image :src="require('@/assets/logo.png')" style="width: 40px; margin: 10px 10px; vertical-align: middle;"></el-image>
      企业后台管理系统
    </div>
    <div class="flex-grow"/>
    <el-sub-menu index="">
      <template #title>菜单</template>
      <el-menu-item index="/personal">个人中心</el-menu-item>
      <el-menu-item index="/login" @click="doLogout">退出登录</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script>
import {userMutations} from "@/constant";
import {logout} from "@/api/login";
import {ElMessage} from "element-plus";

export default {
  name: "HeaderMenu",
  data() {
    return {}
  },
  methods: {
    doLogout() {
      // 向后台发送登出请求
      logout().then((resp) => {
        if (resp.success) {
          // 删除本地token信息
          this.$store.commit(userMutations.REMOVE_LOCAL_TOKEN)
        } else {
          console.error('后台登出错误')
        }
      }).catch(error => {
        console.error(`登出请求异常, 错误: ${error}`)
      })
      ElMessage.success('登出成功')
    }
  }
}
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

.header-tip {
  color: white;
  height: 60px;
  /*line-height: 60px;*/
  margin: 0 20px;
}
</style>
