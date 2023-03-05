<template>
  <div class="middle-plate-box" :style="`background: url('${plateDetail.image}') no-repeat; background-size: 100% 100%;`">
    <div class="middle-plate">
      <h2 class="plate-title">
        <el-icon style="vertical-align: middle">
          <Connection/>
        </el-icon>
        &nbsp;{{plateDetail.plateName}}&nbsp;
        <el-icon style="vertical-align: middle">
          <Connection/>
        </el-icon>
      </h2>
      <div class="plate-divider-box">
        <el-divider class="plate-divider"/>
      </div>
      <div class="plate-description-box">
        <div class="plate-description">
          <p>{{plateDetail.description}}</p>
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
      </div>
      <div class="img-mask"></div>
      <!--    <div class="img-mask1"></div>-->
    </div>
    <div class="mask"></div>
  </div>

</template>

<script>
import {Connection, Promotion} from '@element-plus/icons-vue'

export default {
  name: "MiddlePlate",
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
      src: 'https://www.tsinghua.edu.cn/image/banner06.jpg',
      plateDom: Object,
    }
  },
  methods: {
    linkTo(link) {
      window.open(link, '_self')
    }
  },
  components: {
    Connection,
    Promotion
  },
  mounted() {
    this.plateDom = document.querySelector('.middle-plate')
  }
}
</script>

<style scoped>
.middle-plate-box {
  position: relative;
  margin: 0;
  height: 550px;
  /*background-size: 550px;*/
  /*background: url("https://www.tsinghua.edu.cn/image/banner06.jpg") no-repeat;*/
  background-size: 200px 200px;
}
.middle-plate {
  position: absolute;
  left: 15%;
  bottom: 0;
  height: 400px;
  width: 70%;
  overflow-y: hidden;
  z-index: 100;
  background-color: #ffffff;
}

.plate-title {
  position: absolute;
  /*right: 100px;*/
  width: 100%;
  text-align: center;
  top: 40px;
  font-weight: normal;
  letter-spacing: 3px;
  font-size: 30px;
  z-index: 10;
}

.plate-divider-box {
  position: absolute;
  top: 120px;
  width: 100%;
}

.plate-divider {
  text-align: center;
  width: 80px;
  margin: 0 auto;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  z-index: 10;
}

.plate-description-box {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}

.plate-description {
  width: 800px;
  text-align: center;
  padding-top: 90px;
  margin: 0 auto;
  line-height: 2em;
  z-index: 10;
}

.plate-image-box {
  width: 100%;
  height: 100%;
}

.mask {
  position: absolute;
  width: 100%;
  height: 550px;
  background-color: #e5e3e3;
  filter: opacity(40%);
  z-index: 10;
}
</style>
