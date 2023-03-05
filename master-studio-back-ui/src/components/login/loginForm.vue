<template>
    <div class="title">系统登录</div>
    <div class="LoginForm">
      <input type="text" class="LoginInput" placeholder="请输入用户名" v-model="loginUser.username">
      <input type="password" class="LoginInput" placeholder="请输入密码" v-model="loginUser.password">
      <el-button type="primary" @click="doLogin" :class="{active: loginBtnDisabled}" :disabled="loginBtnDisabled">{{loginBtnText}}</el-button>
      <p class="login-tip" @click="findPwd">忘记密码?</p>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import md5 from 'js-md5';
import {login} from "@/api/login";
import {userMutations} from "@/constant";


export default {
  name: "loginForm",
  data() {
    return {
      isLogin: false,
      loginUser: {
        username: '',
        password: ''
      },
      loginBtnText: '登录',
      loginBtnDisabled: false
    }
  },
  methods: {
    doLogin() {
      if (this.loginUser.username === null || this.loginUser.username === '') {
        ElMessage.warning('用户名不能为空')
        return
      }
      if (this.loginUser.password === null || this.loginUser.password === '') {
        ElMessage.warning('密码不能为空')
        return
      }
      let loginData = {
        username: this.loginUser.username,
        password: md5(this.loginUser.password)
      }
      this.loginBtnDisabled = true
      this.loginBtnText = '登录中...'
      login(loginData).then(resp => {
        if (resp.success) {
          ElMessage.success('登录成功')
          this.$store.commit(userMutations.SAVE_USER_TOKEN, resp.data)
          this.$router.push({name: 'home'})
        } else {
          ElMessage.error('登录失败，可能是用户名密码错误，或者账号被锁定')
          this.loginBtnDisabled = false
          this.loginBtnText = '重新登录'
        }
      }).catch(() => {
        ElMessage.error('登录失败，可能是用户名密码错误，或者账号被锁定')
        this.loginBtnDisabled = false
        this.loginBtnText = '重新登录'
      })
    },
    findPwd() {
      this.$router.push({name: 'findPwd'})
    }
  },
  mounted() {
    window.onkeydown = (e) => {
      if (e.key === 'Enter') {
        this.doLogin()
      }
    }
  },
  beforeUnmount() {
    window.onkeydown = null
  }
}
</script>

<style scoped>
.title {
  font-size: 30px;
  margin-top: 20px;
  font-weight: bold;
  letter-spacing: 10px;
  color: #006CCC;
  text-align: center;
}

.LoginForm {
  margin-top: 35px;
}

.LoginForm .LoginInput {
  width: 80%;
  height: 40px;
  margin: 15px;
  border-radius: 5px;
  border: 1px solid #D1D1D1;
  padding: 5px 20px;
  text-align: left;
  font-size: 16px;
}

.LoginForm .el-button {
  width: 90%;
  height: 65px;
  border-radius: 10px;
  background: #509FFF;
  border: none;
  text-align: center;
  letter-spacing: 10px;
  color: white;
  font-size: 20px;
  margin-top: 30px;
  cursor: pointer;
}

.login-tip {
  font-size: 14px;
  color: #6b778c;
  cursor: pointer;
}

.login-tip:hover {
  transition: 0.4s;
  color: #006CCC;
  text-decoration: underline;
}

.active {
  background-color: #ee9f9f !important;
}
</style>
