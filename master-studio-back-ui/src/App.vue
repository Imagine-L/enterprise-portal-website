<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import {userMutations} from "@/constant";

export default {
  name: 'App',
  created() {
    // 应用刷新后，从localStorage中恢复token信息
    this.$store.commit(userMutations.RESTORE_USER_BY_TOKEN)
  }
}
</script>

<style>
body{
  position: absolute;
  width: 100%;
  height: 100%;
  background: url("./assets/bg.png") no-repeat;
  background-size: 100%;
  margin: 0 auto;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
