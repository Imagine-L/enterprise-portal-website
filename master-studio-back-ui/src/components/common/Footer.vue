<template>
  <div class="my-footer">
    <p>© 2022 大师工作室 版权所有，并保留所有权利&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;<a href="https://www.yuque.com/u12581613">网站制作者: 刘佳俊</a></p>
  </div>
</template>

<script>
export default {
  name: "FooterComponent"
}
</script>

<style scoped>
.my-footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 30px;
  background-color: #303133;
  color: white;
  font-size: 12px;
  padding-bottom: 10px;
}

a {
  color: white;
  text-decoration: none;
}

.my-footer a:hover {
  color: #67a9e3;
}
</style>
