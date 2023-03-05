<template>
  <el-row>
    <el-col :span="24">
      <el-carousel :height="windowHeight" style="background-color: #d3dce6" v-if="carouselList[0]">
        <el-carousel-item v-for="carousel in carouselList" :key="carousel.hid">
          <el-image :key="carousel.hid" :src="carousel.image" style="width: 100%; height: 100%"
                    @click="linkTo(carousel.link)"/>
        </el-carousel-item>
      </el-carousel>
    </el-col>
  </el-row>
</template>

<script>
import {ElMessage} from "element-plus";
import {getCarouselList} from "@/api/carousel";

export default {
  name: "CarouselPage",
  data() {
    return {
      carouselList: [],
    }
  },
  computed: {
    windowHeight() {
      return window.innerHeight + 'px'
    }
  },
  methods: {
    // 获取轮播图列表
    async doGetCarouselList() {
      await getCarouselList().then(resp => {
        if (resp.success) {
          this.carouselList = resp.data
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    linkTo(link) {
      window.open(link, "_self")
    }
  },
  mounted() {
    this.doGetCarouselList()
  }
}
</script>

<style scoped>
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
