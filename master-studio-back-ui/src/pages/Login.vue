<template>
  <div class="contain">
    <div class="TitleBox">
      <div class="TitleHead">大师工作室后台管理系统</div>
      <div class="TitleTip">路由今日汗水，交换明日辉煌</div>
      <div class="LoginBgIcon"></div>
    </div>
    <div class="LoginBox" :class="{active:isLogin}">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage'
}
</script>

<style scoped>
.contain {
  height: 100%;
  overflow-y: hidden;
}
.TitleBox {
  text-align: left;
  margin: 8% 10%;
  float: left;
  width: 440px;
}

.TitleHead {
  color: #006CCC;
  font-size: 50px;
  letter-spacing: 5px;
  font-weight: bold;
  width: 650px;
}

.TitleTip {
  color: #9E9E9E;
  font-size: 14px;
  text-align: center;
  margin: 10px;
  letter-spacing: 10px;
}

.LoginBgIcon {
  height: 680px;
  width: 700px;
  background: url("../assets/phone-web-icon-all.png") no-repeat;
  background-size: 100%;
}

.LoginBox {
  height: 420px;
  width: 450px;
  float: right;
  background: #ffffff;
  border-radius: 10px;
  position: absolute;
  top: 30%;
  right: 15%;
  box-shadow: 0 0 50px #ece9e9
}
</style>
