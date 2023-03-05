<template>
  <!-- 头部图片 -->
  <TitleImage :nav-detail="navDetail"/>
  <template v-for="plate in plateList" :key="plate.pid">
    <LeftPlate v-if="plate.plateType === 0" :plate-detail="plate"></LeftPlate>
    <MiddlePlate v-else-if="plate.plateType === 1" :plate-detail="plate"></MiddlePlate>
    <RightPlate v-else :plate-detail="plate"></RightPlate>
  </template>
</template>

<script>
import TitleImage from "@/components/common/TitleImage";
import LeftPlate from "@/components/page/LeftPlate";
import RightPlate from "@/components/page/RightPlate";
import MiddlePlate from "@/components/page/MiddlePlate";
import {children, getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "PagePage",
  data() {
    return {
      plateList: [],
      navDetail: {}
    }
  },
  components: {
    MiddlePlate,
    RightPlate,
    LeftPlate,
    TitleImage,
  },
  methods: {
    initMethod() {
      let path = this.$route.path
      path = path.substring(path.lastIndexOf('/') + 1);
      this.doGetNavByPath(path)
      this.doGetChildren(path)
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
    // 获取本栏目下的所有文章
    doGetChildren(path){
      // 获取所有子板块
      children(path).then(resp => {
        if (resp.success) {
          this.plateList = resp.data
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
</style>
