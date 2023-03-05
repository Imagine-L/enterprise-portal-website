<template>
  <div class="right-plate-box" :style="`background: url(${plateDetail.image}) no-repeat;background-size: 100% 100%;`">
    <div class="right-plate-mask"></div>
    <div class="right-plate-title" @click="linkTo(plateDetail.link)"><el-icon class="right-plate-title-icon"><Link /></el-icon>{{plateDetail.plateName}}</div>
    <el-divider class="right-plate-divider"/>
    <div class="right-plate-description">{{plateDetail.description}}</div>
    <div class="right-plate-arrow" @click="linkTo(plateDetail.link)"></div>
  </div>
</template>

<script>
import {Link} from '@element-plus/icons-vue'
export default {
  name: "ChildRightPlate",
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
.right-plate-box {
  width: 100%;
  height: 300px;
  /*background: url("https://www.tsinghua.edu.cn/__local/D/63/9E/CF31EE10062A8D96A060880922F_5DE13A65_698AD.jpg?e=.jpg") no-repeat;*/
  background-size: cover;
  position: relative;
  cursor: pointer;
  margin: 50px 0;
}

.right-plate-mask {
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  border-top-left-radius: 200px;
  border-bottom-left-radius: 200px;
  background-color: rgba(255, 255, 255, 0.3);
  background-image: linear-gradient(135deg, rgba(255, 255, 255, 0.3) 15%, rgba(35, 74, 206, 0.3) 100%);
  z-index: 0;
}

.right-plate-title:hover,.right-plate-mask:hover {
  background-position: 90%;
  transition: 0.4s;
  cursor: pointer;
  color: #1b42a2;
}

.right-plate-title {
  position: absolute;
  top: 20%;
  right: 5%;
  /*padding-right: 20px;*/
  font-size: 32px;
  letter-spacing: 3px;
  z-index: 100;
  color: white;
}

.right-plate-title-icon {
  vertical-align: middle;
  margin-right: 10px;
}

.right-plate-divider {
  position: absolute;
  top: 30%;
  right: 5%;
  width: 80px;
  background-color: #ee7d40;
  border: 1px solid #ee7d40;
  z-index: 10;
}

.right-plate-description {
  position: absolute;
  width: 50%;
  top: 40%;
  right: 5%;
  font-size: 15px;
  color: #fff;
  line-height: 2em;
  text-align: right;
}

.right-plate-arrow {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background: url("@/assets/left-child-plate-arrow.png") no-repeat;
  background-position: 35%;
  background-size: 75px;
}

.right-plate-arrow:hover {
  background-position: 37%;
  transition: 0.4s;
}
</style>
