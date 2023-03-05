<template>
  <div class="middle-plate-box" :style="`background: url(${plateDetail.image}) no-repeat;background-size: 100% 100%;`">
    <div class="middle-plate-mask-box">
      <div class="middle-plate-mask"></div>
    </div>
    <div class="middle-plate-title-box">
      <div class="middle-plate-title" @click="linkTo(plateDetail.link)">
        <el-icon class="middle-plate-title-icon">
          <Link/>
        </el-icon>
        {{plateDetail.plateName}}
        <el-icon class="middle-plate-title-icon">
          <Link/>
        </el-icon>
      </div>
    </div>
    <div class="middle-plate-divider-box">
      <el-divider class="middle-plate-divider"/>
    </div>
    <div class="middle-plate-description-box">
      <div class="middle-plate-description">{{plateDetail.description}}</div>
    </div>
    <div class="left-plate-arrow"></div>
    <div class="right-plate-arrow" @mouseenter="hover($event)" @mouseleave="unhover($event)" @click="linkTo(plateDetail.link)"></div>
  </div>
</template>

<script>
import $ from 'jquery'
import {Link} from '@element-plus/icons-vue'

export default {
  name: "ChildMiddlePlate",
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
    hover(e) {
      $(e.currentTarget).prev().addClass('left-plate-arrow-hover')
    },
    unhover(e) {
      $(e.currentTarget).prev().removeClass('left-plate-arrow-hover')
    },
    linkTo(link) {
      window.open(link, "_self")
    }
  },
  components: {
    Link
  },
  mounted() {
    // $('.right-plate-arrow').hover(() => {
    //   $('.left-plate-arrow').addClass('left-plate-arrow-hover')
    // }, () => {
    //   $('.left-plate-arrow').removeClass('left-plate-arrow-hover')
    // })
  }
}
</script>

<style scoped>
.middle-plate-box {
  width: 100%;
  height: 300px;
  position: relative;
  cursor: pointer;
  margin: 50px 0;
}

.middle-plate-mask-box {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}

.middle-plate-mask {
  width: 80%;
  height: 100%;
  border-radius: 200px;
  background-color: rgba(255, 255, 255, 0.3);
  background-image: linear-gradient(180deg, rgba(255, 255, 255, 0.3) 15%, rgba(35, 74, 206, 0.3) 100%);
}

.middle-plate-title-box {
  width: 100%;
  text-align: center;
  position: absolute;
  top: 10%;
  margin: 20px 0;
  z-index: 10;
}

.middle-plate-title:hover {
  transition: 0.4s;
  color: #1b42a2;
}

.middle-plate-title {
  font-size: 32px;
  letter-spacing: 3px;
  z-index: 100;
  color: white;
}

.middle-plate-title-icon {
  vertical-align: middle;
  margin-right: 10px;
}

.middle-plate-divider-box {
  position: absolute;
  width: 100%;
  top: 34%;
  text-align: center;
}

.middle-plate-divider {
  width: 80px;
  background-color: #ee7d40;
  border: 1px solid #ee7d40;
  z-index: 10;
  margin: 0 auto;
}

.middle-plate-description-box {
  position: absolute;
  top: 40%;
  width: 100%;
  text-align: center;
}

.middle-plate-description {
  width: 50%;
  font-size: 15px;
  color: #fff;
  line-height: 2em;
  margin: 0 auto;
}

.left-plate-arrow {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background: url("@/assets/left-child-plate-arrow.png") no-repeat;
  background-position: 12%;
  background-size: 75px;
}

.left-plate-arrow-hover {
  background-position: 15%;
  transition: 0.4s;
}

.right-plate-arrow {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  background: url("@/assets/right-child-plate-arrow.png") no-repeat;
  background-position: 88%;
  background-size: 75px;
}

.right-plate-arrow:hover {
  background-position: 85%;
  transition: 0.4s;
}
</style>
