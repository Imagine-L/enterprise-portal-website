<template>
  <TitleImage :nav-detail="navDetail"/>
  <div class="not-found-body">
    <div class="not-found-box">
      <div class="not-found-description">你访问的页面可能已失效或被删除<br/>你可以选择</div>
      <ul class="not-found-choose">
        <li>
          <el-link type="primary" @click="this.$router.go(-1)">返回上一页</el-link>
        </li>
        <li>
          <el-link type="primary" @click="this.$router.push({name: 'home'})">返回首页</el-link>
        </li>
      </ul>
      <div class="not-found-tip">{{ countdown.num }}秒后自动跳转到首页</div>
    </div>
  </div>
</template>

<script>
import TitleImage from "@/components/common/TitleImage";
import {ElMessage} from "element-plus";

export default {
  name: "NotFound",
  data() {
    return {
      navDetail: {
        nid: '',
        navName: '怎么回事,页面走丢了...',
        level: 1,
        image: require('@/assets/404.jpg'),
        description: '你访问的页面可能已失效或被删除',
        navType: 0,
        path: ''
      },
      // 倒计时
      countdown: {
        timer: null,
        num: 5
      }
    }
  },
  methods: {},
  components: {
    TitleImage
  },
  mounted() {
    clearInterval(this.countdown.timer)
    this.countdown.timer = setInterval(() => {
      this.countdown.num--
      if (this.countdown.num === 0) {
        clearInterval(this.countdown.timer)
        this.countdown.timer = null
        this.$router.push({name: 'home'})
      }
    }, 1000)
  },
  beforeUnmount() {
    clearInterval(this.countdown.timer)
  }
}
</script>

<style scoped>
* {
  list-style: none;
  padding: 0;
  margin: 0;
}

.not-found-body {
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  background-image: linear-gradient(180deg, #ffffff 77%, #ecb9ed 100%);
  padding: 50px 0;
}

.not-found-box {
  width: 50%;
  margin: 0 auto;
  text-align: center;
}

.not-found-description {
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 5px;
  line-height: 40px;
  width: 60%;
  margin: 20px auto;
}

.not-found-choose li {
  width: 300px;
}

.not-found-choose li .el-link {
  height: 40px;
  width: 150px;
  text-align: justify;
  text-align-last: justify;
  letter-spacing: 10px;
  margin: 0 auto;
  cursor: pointer;
}

li {
  content: "";
  display: inline-block;
  width: 100%;
}

.not-found-tip {
  font-size: 15px;
  color: #7a7878;
  margin-top: 20px;
  letter-spacing: 5px;
}
</style>
