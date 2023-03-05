<template>
  <div class="form-box">
    <el-form
        ref="formRef"
        :model="validateCodeQuery"
        class="demo-dynamic"
        label-width="auto"
        label-position="left"
    >
      <el-form-item
          prop="username"
          label="用户名"
          :rules="checkRules.username"
      >
        <el-input v-model="validateCodeQuery.username" placeholder="请输入用户名" style="width: 300px"/>
      </el-form-item>
      <el-form-item
          prop="code"
          label="验证码"
          :rules="checkRules.code"
      >
        <el-input
            v-model="validateCodeQuery.code"
            placeholder="请输入六位数邮箱验证码"
            class="input-with-select"
            style="width: 300px"
        >
          <template #append>
            <el-button type="primary" @click="doSendCode($event)">发送验证码</el-button>
          </template>
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
import $ from "jquery";
import { ElMessage } from 'element-plus'
import {sendCode, validateCode} from "@/api/forget";
import {forgetMutations} from "@/constant";

export default {
  name: "FindStep1",
  data() {
    return {
      index: 1,
      formRef: {
        username: '',
        code: '',
      },
      delayer: {
        timer: Object,
        count: 120
      },
      checkRules: {
        username: {
          required: true,
          message: '用户名不能为空',
          trigger: 'blur',
        },
        code: [
          {
            required: true,
            message: '验证码不能为空',
            trigger: 'blur',
          },
          {
            min: 6,
            max:6,
            message: '请输入六位数验证码',
            trigger: 'blur'}
        ]
      },
      validateCodeQuery: {
        username: '',
        code: '',
        tempToken: ''
      }
    }
  },
  methods: {
    nextStep() {
      if (this.validateCodeQuery.tempToken === '') {
        ElMessage.warning('请先发送验证码')
        return
      }
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 发送请求验证用户验证码是否输入正确
          validateCode(this.validateCodeQuery).then(resp => {
            // 验证成功通知父组件进入下一步
            if (resp.success) {
              this.$emit("stepMonitor", this.index);
            } else {
              ElMessage.error('验证码输入错误')
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请输入正确的邮箱和验证码！')
        }
      })
    },
    cancelStep() {
      this.$router.push({name: 'login'})
    },
    // 恢复发送按钮的状态
    recoverSendBtn($sendBtn) {
      clearInterval(this.delayer.timer)
      $sendBtn.attr("disabled", false)
      $sendBtn.text('发送验证码')
      this.delayer.count = 120
    },
    // 设置发送延迟，防止用户频繁发送验证码
    sendDelay(e) {
      clearInterval(this.delayer.timer)
      let $sendBtn = $(e.currentTarget)
      $sendBtn.attr("disabled", true)
      $sendBtn.text('正在发送...')
      this.delayer.timer = setInterval(() => {
        if (this.delayer.count === 0) {
          this.recoverSendBtn($sendBtn)
        } else {
          $sendBtn.text(this.delayer.count + "")
          this.delayer.count--
        }
      }, 1000)
    },
    // 发送验证码
    doSendCode(e) {
      if (this.validateCodeQuery.username === '') {
        ElMessage.warning('用户名不能为空')
        return
      }
      let $sendBtn = $(e.currentTarget)
      sendCode(this.validateCodeQuery.username).then(resp => {
        if (resp.success) {
          // 保存临时token
          this.validateCodeQuery.tempToken = resp.data
          this.$store.commit(forgetMutations.SAVE_TEMP_TOKEN, resp.data)
        } else {
          this.recoverSendBtn($sendBtn)
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => {
        this.recoverSendBtn($sendBtn)
        ElMessage.error('当前网络延迟较高，请稍后重试')
      })
      this.sendDelay(e)
    }
  }
}
</script>

<style scoped>
.form-box {
  width: 100%;
  display: flex;
  justify-content: center;
}

.form-box > .el-form {
  margin: 0 auto;
}
</style>
