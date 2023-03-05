<template>
  <div class="steps-box">
    <el-steps :active="activeIndex"  :align-center="true">
      <el-step title="邮箱验证"/>
      <el-step title="修改密码"/>
      <el-step title="修改成功"/>
    </el-steps>
  </div>
  <router-view @stepMonitor="changeActive"></router-view>
</template>

<script>

export default {
  name: "findPwd",
  data() {
    return {
      activeIndex: 1
    }
  },
  methods: {
    changeActive(index) {
      this.activeIndex = index + 1
      if (this.activeIndex === 2) {
        this.$router.push({name: 'findStep2'})
      } else if (this.activeIndex === 3) {
        this.$router.push({name: 'findStep3'})
      }
    }
  },
  mounted() {
    this.$router.push({name: 'findStep1'})
  }
}
</script>

<style scoped>
.steps-box {
  width: 100%;
  text-align: center;
  margin: 60px 0;
}

.el-steps {
  width: 80%;
  margin: 0 auto;
}

</style>
