<template>
  <div class="form-box">
    <el-form
        ref="formRef"
        :model="passwordForm"
        class="demo-dynamic"
        label-width="auto"
        label-position="left"
    >
      <el-form-item
          prop="newPwd"
          label="新密码"
          :rules="checkRules.newPwd"
      >
        <el-input type="password" v-model="passwordForm.newPwd" placeholder="请输入新密码" style="width: 300px"/>
      </el-form-item>
      <el-form-item
          prop="againPwd"
          label="确认密码"
          :rules="checkRules.againPwd"
      >
        <el-input
            v-model="passwordForm.againPwd"
            placeholder="请再次输入密码"
            class="input-with-select"
            style="width: 300px"
            type="password"
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="nextStep">下一步</el-button>
        <el-button @click="cancelStep">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import md5 from "js-md5";
import { ElMessage } from 'element-plus'
import {updatePwdByToken} from "@/api/forget";

export default {
  name: "FindStep2",
  data() {
    return {
      index: 2,
      passReg: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$/,
      formRef: {
        newPwd: '',
        againPwd: ''
      },
      delayer: {
        timer: Object,
        count: 10
      },
      checkRules: {
        newPwd: [
          {
            required: true,
            validator: this.validatePass,
            trigger: 'blur',
          }
        ],
        againPwd: [
          {
            required: true,
            validator: this.validateAgainPass,
            trigger: 'blur',
          },
        ]
      },
      passwordForm: {
        newPwd: '',
        againPwd: ''
      },
      updatePwdQuery: {
        newPwd: '',
        tempToken: ''
      }
    }
  },
  methods: {
    validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('密码不能为空'))
      } else if (!this.passReg.test(value)) {
        callback(new Error('密码必须包含大小写字母和数字，且长度在8-15之间'))
      } else {
        callback()
      }
    },
    validateAgainPass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.passwordForm.newPwd) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    },
    nextStep() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          let tempToken = this.updatePwdQuery.tempToken
          if (tempToken === null || tempToken === '') {
            ElMessage.error('会话过期，请重新验证邮箱')
            this.$router.push({name: 'findStep1'})
            return
          }
          this.updatePwdQuery.newPwd = md5(this.passwordForm.newPwd)
          // 重新修改密码
          updatePwdByToken(this.updatePwdQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('密码修改成功，请重新登录')
              this.$emit("stepMonitor", this.index);
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))

        } else {
          ElMessage.error('您输入的新密码为空或不符合复杂度要求！')
        }
      })
    },
    cancelStep() {
      this.$router.push({name: 'login'})
    },
  },
  mounted() {
    let tempToken = this.$store.getters.tempToken
    if (tempToken === null || tempToken === '') {
      ElMessage.error('会话过期，请重新验证邮箱')
      this.$router.push({name: 'findStep1'})
      return
    }
    this.updatePwdQuery.tempToken = tempToken
  }
}
</script>

<style scoped>
.form-box {
  width: 100%;
  display: flex;
  justify-content: center;
  /*margin-top: -30px;*/
}

.form-box > .el-form {
  margin: 0 auto;
}
</style>
