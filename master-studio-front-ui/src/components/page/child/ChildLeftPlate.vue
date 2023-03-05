<template>
  <div class="left-plate-box" :style="`background: url(${plateDetail.image}) no-repeat;background-size: 100% 100%;`">
    <div class="left-plate-mask"></div>
    <div class="left-plate-title" @click="linkTo(plateDetail.link)">
      <el-icon class="left-plate-title-icon">
        <Link/>
      </el-icon>
      {{ plateDetail.plateName }}
    </div>
    <el-divider class="left-plate-divider"/>
    <div class="left-plate-description">{{ plateDetail.description }}</div>
    <div class="left-plate-arrow" @click="linkTo(plateDetail.link)"></div>
  </div>
</template>

<script>
import {Link} from '@element-plus/icons-vue'

export default {
  name: "ChildLeftPlate",
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
    return {}
  },
  methods: {
    linkTo(link) {
      window.open(link, "_self")
    }
  },
  components: {
    Link
  }
}
</script>

<style scoped>
.left-plate-box {
  width: 100%;
  height: 300px;
  /*background: url("https://www.tsinghua.edu.cn/ditu/banner12.jpg") no-repeat;*/
  /*background-size: cover;*/
  position: relative;
  cursor: pointer;
  margin: 50px 0;
}

.left-plate-mask {
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  border-top-right-radius: 200px;
  border-bottom-right-radius: 200px;
  background-color: rgba(255, 255, 255, 0.3);
  background-image: linear-gradient(225deg, rgba(255, 255, 255, 0.3) 15%, rgba(35, 74, 206, 0.3) 100%);
  z-index: 0;
}

.left-plate-title:hover, .left-plate-mask:hover {
  background-position: 90%;
  transition: 0.4s;
  cursor: pointer;
  color: #1b42a2;
}

.left-plate-title {
  position: absolute;
  top: 20%;
  padding-left: 5%;
  text-align: left;
  font-size: 32px;
  letter-spacing: 3px;
  z-index: 100;
  color: white;
}

.left-plate-title-icon {
  vertical-align: middle;
  margin-right: 10px;
}

.left-plate-divider {
  position: absolute;
  top: 30%;
  left: 5%;
  width: 80px;
  background-color: #ee7d40;
  border: 1px solid #ee7d40;
  z-index: 10;
}

.left-plate-description {
  position: absolute;
  width: 50%;
  top: 40%;
  left: 5%;
  font-size: 15px;
  color: #fff;
  line-height: 2em;
}

.left-plate-arrow {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background: url("@/assets/right-child-plate-arrow.png") no-repeat;
  background-position: 65%;
  background-size: 75px;
}

.left-plate-arrow:hover {
  background-position: 62%;
  transition: 0.4s;
}
</style>
