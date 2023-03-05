<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">个人中心</h1>
      </el-col>
    </el-row>
    <br>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <el-row>
      <el-col :span="24">
        <h5 class="welcome">欢迎您，{{ personalMsg.nickname }}</h5>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-descriptions border title="个人信息">
          <el-descriptions-item label="用户名">{{ personalMsg.username }}</el-descriptions-item>
          <el-descriptions-item label="昵称">{{ personalMsg.nickname }}</el-descriptions-item>
          <el-descriptions-item label="岗位">{{ personalMsg.professionName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ personalMsg.gender }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ personalMsg.age }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ personalMsg.phone }}</el-descriptions-item>
          <el-descriptions-item label="QQ号">{{ personalMsg.qqNumber }}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{ personalMsg.email }}</el-descriptions-item>
          <el-descriptions-item label="管理员">
            <el-tag size="small">{{ personalMsg.admin ? 'YES' : 'NO' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="锁定">
            <el-tag size="small">{{ personalMsg.locked ? 'YES' : 'NO' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="描述">{{ personalMsg.description }}</el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
    <br>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-descriptions border title="来源信息">
          <el-descriptions-item label="来源IP">{{source.cip}}</el-descriptions-item>
          <el-descriptions-item label="地区">{{source.cname}}</el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" :offset="2">
        <div class="tips">
          <p><el-icon style="vertical-align: middle"><InfoFilled /></el-icon>&nbsp;&nbsp;温馨提示：</p>
          <ul>
            <li>为了您的浏览体验，推荐使用谷歌、火狐等主流浏览器，不建议使用IE浏览器浏览本网站。</li>
            <li>来源信息不由本站直接提供服务，在某些情况下，可能无法获取到，但不影响本站其他服务使用。</li>
          </ul>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {StarFilled,InfoFilled} from '@element-plus/icons-vue'
import {getUserDetail, getUserSource} from "@/api/user";
import {ElMessage} from "element-plus";

export default {
  name: "PersonalPage",
  data() {
    return {
      personalMsg: {
        "admin": false,
        "age": 0,
        "allowDel": Boolean,
        "createBy": '未知',
        "createTime": new Date(),
        "description": '未知',
        "email": '未知',
        "gender": '未知',
        "locked": false,
        "nickname": '未知',
        "phone": '未知',
        "professionName": '未知',
        "qqNumber": '未知',
        "uid": 0,
        "updateBy": '未知',
        "updateTime": new Date(),
        "username": '未知'
      },
      source: {
        cid: '',
        cip: "未知",
        cname: "未知"
      }
    }
  },
  components: {
    StarFilled,
    InfoFilled
  },
  mounted() {
    // 获取个人用户编号
    let uid = this.$store.getters.uid
    // 从后台获取个人信息
    getUserDetail(uid).then(resp => {
      if (resp.success) {
        this.personalMsg = resp.data
      } else {
        ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
      }
    }).catch(() => {
      ElMessage.error('当前网络延迟较高，请稍后重试')
    })
    // 获取来源信息
    getUserSource().then(() => {
      if (window.returnCitySN !== undefined && window.returnCitySN !== null) {
        this.source = window.returnCitySN
      }
    })
  }
}
</script>

<style scoped>
.demo-pagination-block + .demo-pagination-block {
  margin-top: 10px;
}

.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}

.title {
  font-size: 30px;
  margin: 0;
  padding: 0
}

.welcome {
  width: 100%;
  height: 100%;
  text-align: left;
  font-size: 30px;
  /*margin-bottom: 50px;*/
  margin-left: 110px;
  margin-top: 0;
  /*padding-left: 100px;*/
}

.tips {
  width: 100%;
  text-align: left;
  /*padding-left: 110px;*/
  line-height: 30px;
  margin-top: 20px;
  font-size: 13px;
  color: #6b778c;
}

.tips * {
  margin: 0;
  padding: 0;
}

.tips li {
  list-style: none;
  /*margin-left: 2em;*/
}
</style>
