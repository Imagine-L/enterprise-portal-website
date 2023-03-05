<template>
  <!-- 轮播图 -->
  <Carousel></Carousel>
  <!-- 首页展示板块 -->
  <HomePlate v-for="parentNav in showNavList" :nav-detail="parentNav" :key="parentNav.nid"></HomePlate>
</template>

<script>
import Carousel from "@/components/home/Carousel";
import HomePlate from "@/components/home/HomePlate";
import {getShowedNav} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "HomePage",
  data() {
    return {
      showNavList: [
        {
          "children": [],
          "level": 0,
          "navName": "",
          "navType": 0,
          "nid": 0,
          "notice": true,
          "path": ""
        }
      ],
    }
  },
  methods: {
    initMethod() {
      this.doGetShowedNav()
    },
    doGetShowedNav() {
      getShowedNav().then(resp => {
        if (resp.success) {
          this.showNavList = resp.data
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
    }
  },
  components: {
    Carousel,
    HomePlate,
  },
  mounted() {
    this.initMethod()
  }
}
</script>

<style scoped>

</style>
