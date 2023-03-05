<template>
  <el-row style="margin: 0;">
    <el-col :span="24">
      <el-image :src="navDetail.image" alt="标题图片" class="title-image" v-if="navDetail.image"/>
      <div class="mask"></div>
      <div class="page-title-box" @loadeddata="onLoad">
        <h1 class="page-title">{{ navDetail.navName }}</h1>
        <el-divider direction="vertical" class="page-divider"/>
        <div class="page-description">{{ navDetail.description }}</div>
      </div>
      <el-breadcrumb class="page-breadcrumb" separator="·" style="z-index: 20">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item
            v-for="breadcrumb in breadcrumbList"
            :key="breadcrumb.nid"
            :to="{path: `/${breadcrumb.fullPath}`}"
        >{{ breadcrumb.navName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>
  </el-row>
</template>

<script>

export default {
  name: "TitleImage",
  props: {
    navDetail: {
      nid: '',
      navName: '',
      level: 1,
      image: '',
      description: '',
      navType: 0,
      path: ''
    },
  },
  data() {
    return {
      // 访问路径列表
      paths: []
    }
  },
  computed: {
    imageMaxHeight() {
      let value = window.innerHeight * 0.6
      if (value > 1000) {
        value = 1000
      }
      return value + 'px'
    },
    imageMinHeight() {
      let value = window.innerHeight * 0.5
      if (value < 400) {
        value = 400
      }
      return value + 'px'
    },
    // 面包屑导航
    breadcrumbList() {
      return this.$store.getters.getBreadcrumbList
    },
  },
  methods: {
    onLoad() {
      console.log('load')
    }
  },
  mounted() {
    // 延迟一定时间后，动态加载栏目标题左侧的线的高度
    setTimeout(() => {
      let boxDom = document.querySelector('.page-title-box')
      let divider = document.querySelector('.page-divider')
      divider.style.height = boxDom.clientHeight + 'px'
    }, 500)
  }
}
</script>

<style scoped>
.mask {
  position: absolute;
  top: 0;
  background-color: rgba(64, 64, 64, 0.2);
  width: 100%;
  height: 99%
}

.title-image {
  width: 100%;
  min-height: v-bind(imageMinHeight);
  max-height: v-bind(imageMaxHeight);
}

.page-title-box {
  position: absolute;
  bottom: 10%;
  left: 5%;
  font-family: '微软雅黑', serif;
  color: white;
  /*border: 1px solid rebeccapurple;*/
  padding-left: 40px;
}

.page-title {
  line-height: 0;
  font-size: 40px;
  font-weight: normal;
  letter-spacing: 15px;
}

.page-divider {
  position: absolute;
  top: 0;
  left: 0;
  background-color: white;
  border: 1px solid white;
  /*height: v-bind(pageBoxHeight);*/
}

.page-description {
  font-size: 15px;
  color: #fff;
  width: 500px;
  letter-spacing: 1px;
  line-height: 2em;
}

.page-breadcrumb {
  position: absolute;
  right: 0;
  bottom: -30px;
  padding: 0 50px;
  height: 70px;
  line-height: 70px;
  width: 40%;
  /*height: 60px;*/
  background: url("@/assets/img.png");
  z-index: 10;
}

/* 不被选中时的颜色 */
.el-breadcrumb :deep .el-breadcrumb__inner {
  font-size: 14px;
  color: #fff !important;
  font-weight: 400 !important;
  padding: 0 20px;
}

/* 被选中时的颜色 */
.el-breadcrumb__item:last-child :deep .el-breadcrumb__inner {
  color: #fff !important;
  font-weight: 800 !important;
}


</style>
