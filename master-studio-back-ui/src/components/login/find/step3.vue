<template>
  <div class="form-box">
    <el-result
        icon="success"
        title="密码修改成功"
        sub-title="点击返回按钮后，请使用新密码登录系统"
    >
      <template #extra>
        <el-button type="primary" @click="cancelStep">返回</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>

export default {
  name: "FindStep1",
  methods: {
    cancelStep() {
      this.$router.push({name: 'login'})
    }
  }
}
</script>

<style scoped>
.form-box {
  width: 100%;
  position: absolute;
  top: 30%;
}
</style>
