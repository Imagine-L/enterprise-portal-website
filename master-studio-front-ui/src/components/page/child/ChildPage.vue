<template>
  <div class="child-pages-box">
    <div class="child-page-title">{{navDetail.navName}}</div>
    <el-divider class="child-page-divider"/>
    <div class="child-page-img-box">
      <img :src="navDetail.image" class="child-page-img" alt="栏目图片">
    </div>
    <template v-for="plate in plateList" :key="plate.pid">
      <ChildLeftPlate v-if="plate.plateType === 0" :plate-detail="plate"></ChildLeftPlate>
      <ChildMiddlePlate v-else-if="plate.plateType === 1" :plate-detail="plate"></ChildMiddlePlate>
      <ChildRightPlate v-else :plate-detail="plate"></ChildRightPlate>
    </template>
  </div>
</template>

<script>
import {Link} from '@element-plus/icons-vue'
import ChildLeftPlate from "@/components/page/child/ChildLeftPlate";
import ChildRightPlate from "@/components/page/child/ChildRightPlate";
import ChildMiddlePlate from "@/components/page/child/ChildMiddlePlate";
import {children, getNavByPath} from "@/api/navigation";
import {ElMessage} from "element-plus";

export default {
  name: "child-pagesList",
  data() {
    return {
      navDetail: {},
      plateList: [],
    }
  },
  components: {
    ChildMiddlePlate,
    ChildRightPlate,
    ChildLeftPlate
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
* {
  margin: 0;
  padding: 0;
  list-style: none;
  box-sizing: border-box;
}

.child-pages-box {
  width: 60%;
  height: 100%;
  float: right;
  margin-right: 10%;
  /*background-color: red;*/
  z-index: 0;
  margin-bottom: 100px;
}

.child-page-title {
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

.child-page-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  margin-bottom: 40px;
  z-index: 10;
}

.child-page-img-box {
  width: 100%;
  text-align: center;
}

.child-page-img {
  width: 100%;
}

.child-page-plate {
  margin-bottom: 50px;
}
</style>
