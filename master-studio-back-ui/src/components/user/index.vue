<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">用户管理</h1>
      </el-col>
    </el-row>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <!-- 查询表单 -->
    <el-row>
      <el-col :span="20">
        <el-form :inline="true" :model="getUserQuery" class="demo-form-inline">
          <el-form-item label="用户名">
            <el-input v-model="getUserQuery.username" placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="getUserQuery.nickname" placeholder="请输入昵称"/>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="getUserQuery.gender" placeholder="请选择性别" clearable>
              <el-option label="男" value="0"/>
              <el-option label="女" value="1"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="formQuery">查询</el-button>
            <el-button type="primary" @click="handleSave">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 主体表格 -->
    <el-row justify="center">
      <el-col :span="23">
        <el-table :data="userList" border style="width: 100%" height="400px">
          <el-table-column prop="username" label="用户名" width="180" align="center"/>
          <el-table-column prop="nickname" label="昵称" width="180" align="center"/>
          <el-table-column prop="email" label="邮箱" width="180" align="center"/>
          <el-table-column prop="phone" label="手机号" width="180" align="center"/>
          <el-table-column prop="qqNumber" label="QQ号" width="180" align="center"/>
          <el-table-column label="锁定" width="80" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.locked ? 'danger' : ''">{{ scope.row.locked ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="handleDetail(scope.row.uid)">详情</el-button>
              <el-popconfirm
                  confirm-button-text="密码"
                  confirm-button-type="primary"
                  cancel-button-text="信息"
                  cancel-button-type="primary"
                  title="你想要修改什么?"
                  @confirm="handleEditPwd(scope.row)"
                  @cancel="handleEdit(scope.row.uid)"
              >
                <template #reference>
                  <el-button size="small">修改</el-button>
                </template>
              </el-popconfirm>
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.row.uid)"
                  v-if="scope.row.allowDel"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <br>
    <!-- 分页 -->
    <el-row>
      <el-col :span="15" :offset="1">
        <el-pagination
            v-model:currentPage="getUserQuery.pageNo"
            v-model:page-size="getUserQuery.pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalRecords"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </el-col>
    </el-row>
    <!-- 用户详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="用户详细信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-descriptions border column="1">
        <el-descriptions-item label="用户编号" width="50px" align="center">{{ userDetail.uid }}</el-descriptions-item>
        <el-descriptions-item label="用户名" width="50px" align="center">{{ userDetail.username }}</el-descriptions-item>
        <el-descriptions-item label="昵称" width="50px" align="center">{{ userDetail.nickname }}</el-descriptions-item>
        <el-descriptions-item label="所在岗位" width="50px" align="center">{{ userDetail.professionName }}
        </el-descriptions-item>
        <el-descriptions-item label="性别" width="50px" align="center">{{ userDetail.gender }}</el-descriptions-item>
        <el-descriptions-item label="年龄" width="50px" align="center">{{ userDetail.age }}</el-descriptions-item>
        <el-descriptions-item label="邮箱" width="50px" align="center">{{ userDetail.email }}</el-descriptions-item>
        <el-descriptions-item label="手机号" width="50px" align="center">{{ userDetail.phone }}</el-descriptions-item>
        <el-descriptions-item label="QQ号" width="50px" align="center">{{ userDetail.qqNumber }}</el-descriptions-item>
        <el-descriptions-item label="锁定" width="50px" align="center">
          <el-tag size="small" :type="userDetail.locked ? 'danger' : 'success'">{{
              userDetail.locked ? 'YES' : 'NO'
            }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="允许删除" width="50px" align="center">
          <el-tag size="small">{{ userDetail.allowDel ? 'YES' : 'NO' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="描述" width="50px" align="center">{{ userDetail.description }}</el-descriptions-item>
        <el-descriptions-item label="创建者" width="50px" align="center">{{ userDetail.createBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{ userDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="修改者" width="50px" align="center">{{ userDetail.updateBy }}</el-descriptions-item>
        <el-descriptions-item label="修改时间" width="50px" align="center">{{ userDetail.updateTime }}
        </el-descriptions-item>
      </el-descriptions>
    </el-drawer>
    <!-- 修改用户信息 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogChange"
        title="修改用户信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form
          ref="userRef"
          :model="updateUserQuery"
          label-width="60px"
          label-position="left"
      >
        <el-form-item prop="nickname" label="昵称" :rules="checkRules.nickname">
          <el-input v-model="updateUserQuery.nickname" placeholder="请输入昵称"/>
        </el-form-item>
        <el-form-item prop="gender" label="性别" :rules="checkRules.gender">
          <el-select v-model="updateUserQuery.gender" placeholder="请选择性别">
            <el-option label="男" value="0"/>
            <el-option label="女" value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="updateUserQuery.age" :min="1" :max="120"/>
        </el-form-item>
        <el-form-item prop="professionId" label="岗位" :rules="checkRules.professionId">
          <el-select v-model="updateUserQuery.professionId" placeholder="请选择岗位">
            <!--            <el-option label="工作室负责人" value="0"/>-->
            <!--            <el-option label="工作室成员" value="1"/>-->
            <el-option
                v-for="professionName in professionNames"
                :key="professionName.pid"
                :label="professionName.name"
                :value="professionName.pid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="锁定">
          <el-switch v-model="updateUserQuery.locked" :disabled="updateUserQuery.admin"/>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" :rules="checkRules.email">
          <el-input v-model="updateUserQuery.email" placeholder="请输入邮箱"/>
        </el-form-item>
        <el-form-item prop="phone" label="手机号" :rules="checkRules.phone">
          <el-input v-model="updateUserQuery.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item prop="qqNumber" label="QQ号" :rules="checkRules.qqNumber">
          <el-input v-model="updateUserQuery.qqNumber" placeholder="请输入QQ号"/>
        </el-form-item>
        <el-form-item prop="description" label="描述" :rules="checkRules.description">
          <el-input v-model="updateUserQuery.description" type="textarea"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdate">修改</el-button>
          <el-button @click="handleClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 修改用户密码 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogPwd"
        title="修改用户密码"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="updateUserPwdQuery" ref="userRef" label-width="80px" label-position="left">
        <el-form-item label="原密码" prop="oldPwd" :rules="checkRules.oldPwd">
          <el-input
              v-model="updateUserPwdQuery.oldPwd"
              type="password"
              placeholder="请输入原密码"
              show-password
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd" :rules="checkRules.newPwd">
          <el-input
              v-model="updateUserPwdQuery.newPwd"
              type="password"
              placeholder="请输入新密码"
              show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="againPwd" :rules="checkRules.againPwd">
          <el-input
              v-model="updateUserPwdQuery.againPwd"
              type="password"
              placeholder="请确认密码"
              show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdatePwd">修改</el-button>
          <el-button @click="handleClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 新增用户 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogSave"
        title="新增用户信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="saveUserQuery" ref="userRef" label-width="80px" label-position="left">
        <el-form-item label="用户名" prop="username" :rules="checkRules.username">
          <el-input v-model="saveUserQuery.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="密码" prop="newPwd" :rules="checkRules.newPwd">
          <el-input v-model="saveUserQuery.newPwd" type="password" placeholder="请输入密码" show-password/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname" :rules="checkRules.nickname">
          <el-input v-model="saveUserQuery.nickname" placeholder="请输入昵称"/>
        </el-form-item>
        <el-form-item label="性别" prop="gender" :rules="checkRules.gender">
          <el-select v-model="saveUserQuery.gender" placeholder="请选择性别" clearable>
            <el-option label="男" value="0"/>
            <el-option label="女" value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="saveUserQuery.age" :min="1" :max="120"/>
        </el-form-item>
        <el-form-item label="岗位" prop="professionId" :rules="checkRules.professionId">
          <el-select v-model="saveUserQuery.professionId" placeholder="请选择岗位">
            <el-option
                v-for="professionName in professionNames"
                :key="professionName.pid"
                :label="professionName.name"
                :value="professionName.pid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="锁定">
          <el-switch v-model="saveUserQuery.locked"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :rules="checkRules.email">
          <el-input v-model="saveUserQuery.email" placeholder="请输入邮箱"/>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" :rules="checkRules.phone">
          <el-input v-model="saveUserQuery.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="QQ号" prop="qqNumber" :rules="checkRules.qqNumber">
          <el-input v-model="saveUserQuery.qqNumber" placeholder="请输入QQ号"/>
        </el-form-item>
        <el-form-item label="描述" prop="description" :rules="checkRules.description">
          <el-input v-model="saveUserQuery.description" type="textarea"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doSave">保存</el-button>
          <el-button @click="handleClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>

</template>

<script>
import {StarFilled} from '@element-plus/icons-vue'
import {
  deleteUser,
  getUserDetail,
  getUserList,
  updateUser,
  updateUserPwd,
  checkUsername,
  saveUser,
  emailIsBinding
} from "@/api/user";
import {getProfessionNames} from "@/api/profession";
import {ElMessage, ElMessageBox} from 'element-plus'
import md5 from 'js-md5';

export default {
  name: "UserComponent",
  data() {
    return {
      passReg: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$/,
      // 岗位名列表
      professionNames: [
        {
          "pid": "1",
          "name": "工作室总负责人",
          "description": null,
          "allowDel": null
        },
      ],
      // 查询表单
      getUserQuery: {
        "gender": "",
        "nickname": "",
        "pageNo": 1,
        "pageSize": 10,
        "username": ""
      },
      // 总页数
      totalRecords: 100,
      // 用户列表
      userList: [],
      // 某个用户详细信息
      userDetail: this.resetUserDetail(),
      // 修改用户信息
      updateUserQuery: this.resetUpdateUserQuery(),
      // 修改用户密码信息
      updateUserPwdQuery: this.resetUpdateUserPwdQuery(),
      // 新增用户信息
      saveUserQuery: this.resetSaveUserQuery(),
      userRef: {
        username: '',
        nickname: '',
        gender: '',
        professionId: '',
        email: '',
        phone: '',
        qqNumber: '',
        description: '',
        newPwd: '',
        oldPwd: '',
        againPwd: '',
      },
      // 表单校验
      checkRules: {
        username: [
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          },
          {min: 5, max: 30, message: '长度需要在5-30之间', trigger: 'blur'},
          {
            asyncValidator: this.validateUsername,
            trigger: 'blur',
          },
        ],
        nickname: [
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          },
          {min: 2, max: 30, message: '长度需要在2-30之间', trigger: 'blur'},
        ],
        gender: {
          required: true,
          message: '请选择性别',
          trigger: 'change',
        },
        phone: [
          {
            pattern: /^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/,
            message: '请输入正确的手机号',
            trigger: ['blur', 'change'],
          },
        ],
        qqNumber: [
          {
            pattern: /[1-9][0-9]{4,}/,
            message: '请输入正确的QQ号',
            trigger: ['blur', 'change'],
          },
        ],
        professionId: {
          required: true,
          message: '请选择岗位',
          trigger: 'change',
        },
        email: [
          {
            required: true,
            message: '请输入邮箱地址',
            trigger: 'blur',
          },
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change'],
          },
          {
            type: 'email',
            asyncValidator: this.validateEmailIsBinding,
            trigger: 'blur',
          },
        ],
        description: {min: 0, max: 100, message: '长度需要在0-100之间', trigger: 'blur'},
        oldPwd: {
          required: true,
          message: '请输入原密码',
          trigger: 'blur',
        },
        newPwd: {
          required: true,
          validator: this.validatePass,
          trigger: 'blur',
        },
        againPwd: {
          required: true,
          validator: this.validateAgainPass,
          trigger: 'blur',
        },
      },
      dialogChange: false,
      dialogDetail: false,
      dialogSave: false,
      dialogPwd: false,
      small: false,
      background: true,
      disabled: false
    }
  },
  methods: {
    // 校验密码
    validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('密码不能为空'))
      } else if (!this.passReg.test(value)) {
        // console.log(`你的密码：${value}, 必须包含大小写字母和数字，且长度在8-15之间`)
        callback(new Error('密码必须包含大小写字母和数字，且长度在8-15之间'))
      } else {
        callback()
      }
    },
    validateAgainPass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.updateUserPwdQuery.newPwd) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    },
    // 校验用户名
    async validateUsername(rule, value, callback) {
      await checkUsername(value).then(resp => {
        if (resp.success) {
          if (resp.data.repeat) {
            callback(new Error('用户名已重复'))
          } else {
            callback()
          }
        } else {
          callback(new Error((resp.data !== null && resp.data !== '') ? resp.data : resp.message))
        }
      }).catch(() => callback(new Error('当前网络延迟较高，请稍后重试')))
    },
    // 验证邮箱是否已绑定
    async validateEmailIsBinding(rule, value, callback) {
      await emailIsBinding(value).then(resp => {
        if (resp.success) {
          if (resp.data === null || resp.data === '') {
            callback()
          } else if (resp.data === this.updateUserQuery.uid) {
            callback()
          } else {
            callback(new Error('邮箱已被绑定'))
          }
        } else {
          callback(new Error((resp.data !== null && resp.data !== '') ? resp.data : resp.message))
        }
      }).catch(() => callback(new Error('当前网络延迟较高，请稍后重试')))
    },
    // 获取岗位名列表
    doGetProfessionNames() {
      getProfessionNames().then(resp => {
        if (resp.success) {
          this.professionNames = resp.data
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 根据条件获取用户列表
    doGetUserList() {
      getUserList(this.getUserQuery).then(resp => {
        if (resp.success) {
          this.totalRecords = resp.data.totalRecords
          this.userList = resp.data.records
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => {
        ElMessage.error('当前网络延迟较高，请稍后重试')
      })
    },
    // 点击查询按钮
    formQuery() {
      this.doGetUserList()
    },
    // 改变当前页的条数
    handleSizeChange() {
      // console.log(`${val} items per page`)
      this.doGetUserList()
    },
    // 改变当前页码
    handleCurrentChange() {
      // console.log(`current page: ${val}`)
      this.doGetUserList()
    },
    // 查看用户详情
    handleDetail(uid) {
      getUserDetail(uid).then(resp => {
        if (resp.success) {
          this.userDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 展开新增用户的抽屉
    handleSave() {
      this.doGetProfessionNames()
      this.dialogSave = true
    },
    // 展示修改的页面抽屉
    handleEdit(uid) {
      this.doGetProfessionNames()
      getUserDetail(uid).then(resp => {
        if (resp.success) {
          let user = resp.data
          this.updateUserQuery.uid = user.uid
          this.updateUserQuery.nickname = user.nickname
          if (user.gender === '男') {
            this.updateUserQuery.gender = '0'
          } else {
            this.updateUserQuery.gender = '1'
          }
          this.updateUserQuery.age = user.age
          this.updateUserQuery.admin = user.admin
          this.updateUserQuery.professionId = user.professionId
          this.updateUserQuery.email = user.email
          this.updateUserQuery.phone = user.phone
          this.updateUserQuery.qqNumber = user.qqNumber
          this.updateUserQuery.locked = user.locked
          this.updateUserQuery.description = user.description
          this.dialogChange = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 展示修改密码的页面抽屉
    handleEditPwd(user) {
      let myUid = this.$store.getters.uid
      let admin = this.$store.getters.isAdmin
      if (admin || user.uid === myUid) {
        this.updateUserPwdQuery.uid = user.uid
        this.dialogPwd = true
      } else {
        ElMessage.warning('您只能修改自己的密码，若需要修改他人密码，请联系系统管理员')
      }
    },
    // 发起请求修改用户信息
    doUpdate() {
      this.$refs['userRef'].validate((valid) => {
        if (valid) {
          updateUser(this.updateUserQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('用户信息修改成功')
              this.doGetUserList()
              this.dialogChange = false
              this.updateUserQuery = this.resetUpdateUserQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          })
        } else {
          ElMessage.error('请按要求输入信息后再点击修改按钮！')
        }
      })
    },
    // 发起请求修改用户密码
    doUpdatePwd() {
      this.$refs['userRef'].validate((valid) => {
        if (valid) {
          let data = {
            oldPwd: md5(this.updateUserPwdQuery.oldPwd),
            newPwd: md5(this.updateUserPwdQuery.newPwd),
            uid: this.updateUserPwdQuery.uid
          }
          updateUserPwd(data).then(resp => {
            if (resp.success) {
              ElMessage.success('密码修改成功！')
              this.dialogPwd = false
              this.updateUserPwdQuery = this.resetUpdateUserPwdQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          })
        } else {
          ElMessage.error('请按要求输入信息后再点击修改按钮！')
        }
      })
    },
    // 删除用户
    handleDelete(uid) {
      ElMessageBox.confirm(
          '您确定要删除该用户吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteUser(uid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetUserList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除用户操作')
      })
    },
    // 保存用户
    doSave() {
      this.$refs['userRef'].validate((valid) => {
        if (valid) {
          this.saveUserQuery.password = md5(this.saveUserQuery.newPwd)
          this.saveUserQuery.newPwd = null
          saveUser(this.saveUserQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('添加用户成功')
              this.doGetUserList()
              this.dialogSave = false
              this.saveUserQuery = this.resetSaveUserQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          })
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    handleClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.dialogPwd = false
      this.userDetail = this.resetUserDetail()
      this.saveUserQuery = this.resetSaveUserQuery()
      this.updateUserQuery = this.resetUpdateUserQuery()
      this.updateUserPwdQuery = this.resetUpdateUserPwdQuery()
    },
    resetUserDetail() {
      return {
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
      }
    },
    resetSaveUserQuery() {
      return {
        "age": 1,
        "description": "",
        "gender": "",
        "locked": false,
        "nickname": "",
        "password": "",
        "newPwd": "",
        "phone": "",
        "professionId": "",
        "qqNumber": "",
        "username": "",
        "email": ""
      }
    },
    resetUpdateUserQuery() {
      return {
        "admin": false,
        "age": 0,
        "description": "未知",
        "email": "未知",
        "gender": "",
        "locked": false,
        "nickname": "未知",
        "phone": "未知",
        "professionId": "0",
        "qqNumber": "未知",
        "uid": ""
      }
    },
    resetUpdateUserPwdQuery() {
      return {
        "newPwd": "",
        "oldPwd": "",
        "againPwd": "",
        "uid": 0
      }
    }
  },
  components: {
    StarFilled
  },
  mounted() {
    this.doGetUserList()
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
</style>
