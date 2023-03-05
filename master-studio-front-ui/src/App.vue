<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <Header></Header>
      </el-header>
      <el-main style="overflow-x: hidden">
          <router-view></router-view>
      </el-main>
      <el-footer>
        <Footer></Footer>
      </el-footer>
    </el-container>
  </div>

</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";

export default {
  name: 'App',
  components: {
    Header,
    Footer
  },
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

.el-main {
  position: relative;
  top: -60px;
}

.el-header {
  z-index: 100;
  padding: 10px 20px;
}

.el-footer {
  position: relative;
  top: -60px;
}

</style>
