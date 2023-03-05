<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <HeaderMenu></HeaderMenu>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <AsideMenu></AsideMenu>
        </el-aside>
        <el-container>
          <el-main>
            <div class="main">
              <router-view></router-view>
            </div>
          </el-main>
          <el-footer>
            <Footer/>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import HeaderMenu from "@/components/common/HeaderMenu";
import AsideMenu from "@/components/common/AsideMenu";
import Footer from "@/components/common/Footer";
// import UserComponent from "@/components/user";

export default {
  name: "HomePage",
  components: {
    // UserComponent,
    AsideMenu,
    HeaderMenu,
    Footer
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  background: white;
}

.common-layout, .el-container {
  height: 100%;
  overflow-y: hidden;
}

.el-header {
  margin: 0;
  padding: 0;
  border: none;
}

.el-menu {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
  border: none;
}

.el-footer {
  position: relative;
  margin: 0;
  padding: 0;
}

.el-main {
  margin: 0;
  padding: 0;
  overflow-x: hidden;
}

.main {
  width: 100%;
  padding: 20px 0;
}
</style>
