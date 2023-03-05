<template>
  <div class="left-plate">
    <h2 class="plate-title">
      <el-icon style="vertical-align: middle">
        <Connection/>
      </el-icon>&nbsp;{{ plateDetail.plateName }}
    </h2>
    <el-divider class="plate-divider"/>
    <div class="plate-description" :style="`width: ${plateDom.clientWidth * 0.5}px `">
      <p>{{ plateDetail.description }}</p>
      <p>
        <el-button
            color="#626aef"
            plain
            size="large"
            v-if="plateDetail.link !== null && plateDetail.link !== ''"
            @click="linkTo(plateDetail.link)"
        >
          查看详细
          <el-icon style="margin-left: 10px" size="large">
            <Promotion/>
          </el-icon>
        </el-button>
      </p>
    </div>
    <div class="plate-image-box">
      <img :src="plateDetail.image" class="plate-image" alt="板块图片"/>
    </div>
  </div>
</template>

<script>
import {Connection, Promotion} from '@element-plus/icons-vue'

export default {
  name: "LeftPlate",
  props: {
    plateDetail: {
      pid: '',
      plateName: '',
      description: '',
      image: '',
      link: '',
      plateType: '',
    }
  },
  data() {
    return {
      currentDate: new Date(),
      src: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
      // src: 'https://www.tsinghua.edu.cn/image/banner06.jpg',
      plateDom: Object,
    }
  },
  methods: {
    linkTo(link) {
      window.open(link, "_self")
    }
  },
  components: {
    Connection,
    Promotion
  },
  mounted() {
    this.plateDom = document.querySelector('.left-plate')
  }
}
</script>

<style scoped>
.left-plate {
  margin: 0;
  height: 400px;
  position: relative;
  background-color: #ffffff;
  background-image: linear-gradient(90deg, #ffffff 57%, #ccd3e5 100%);
  /*border-bottom: 1px solid #8383a2;*/
}

.plate-title {
  position: absolute;
  left: 100px;
  top: 20px;
  font-weight: normal;
  letter-spacing: 3px;
  font-size: 30px;
  z-index: 10;
}

.plate-divider {
  position: absolute;
  top: 80px;
  left: 100px;
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  z-index: 10;
}

.plate-description {
  width: 800px;
  position: absolute;
  top: 120px;
  left: 100px;
  line-height: 2em;
  z-index: 10;
}

.plate-image-box {
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: right;
  padding-right: 100px;
  z-index: 1;
}

.plate-image {
  width: 500px;
  max-height: 300px;
  box-shadow: 10px 10px 10px rgba(0, 0, 0, .5);
}
</style>
