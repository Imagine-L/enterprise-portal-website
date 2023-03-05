<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">岗位管理</h1>
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
        <el-form :inline="true" :model="getProfessionQuery" class="demo-form-inline">
          <el-form-item label="编号">
            <el-input v-model="getProfessionQuery.pid" placeholder="请输入编号"/>
          </el-form-item>
          <el-form-item label="岗位名">
            <el-input v-model="getProfessionQuery.name" placeholder="请输入岗位名"/>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="getProfessionQuery.description" placeholder="请输入描述"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="formQuery">查询</el-button>
            <el-button type="primary" @click="dialogSave=true">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 主体表格 -->
    <el-row justify="center">
      <el-col :span="23">
        <el-table :data="professionList" border style="width: 100%" height="400px">
          <el-table-column prop="pid" label="编号" width="180" align="center"/>
          <el-table-column prop="name" label="岗位名" width="180" align="center"/>
          <el-table-column prop="description" label="描述" width="500" align="center"/>
          <el-table-column label="可否删除" width="100" align="center">
            <template #default="scope">
              <el-tag>{{ scope.row.allowDel ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="handleDetail(scope.row.pid)"
              >详情
              </el-button>
              <el-button size="small" @click="handleEdit(scope.row.pid)"
              >修改
              </el-button>
              <el-button
                  v-if="scope.row.allowDel"
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.row.pid)"
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
            v-model:currentPage="getProfessionQuery.pageNo"
            v-model:page-size="getProfessionQuery.pageSize"
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
    <!-- 岗位详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="岗位详细信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-descriptions border column="1">
        <el-descriptions-item label="岗位编号" width="50px" align="center">{{professionDetail.pid}}</el-descriptions-item>
        <el-descriptions-item label="岗位名" width="50px" align="center">{{professionDetail.name}}</el-descriptions-item>
        <el-descriptions-item label="岗位描述" width="50px" align="center">{{professionDetail.description}}</el-descriptions-item>
        <el-descriptions-item label="允许删除" width="50px" align="center">
          <el-tag size="small">{{professionDetail.allowDel ? 'YES' : 'NO'}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建者" width="50px" align="center">{{professionDetail.createBy}}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{professionDetail.createTime}}</el-descriptions-item>
        <el-descriptions-item label="修改者" width="50px" align="center">{{professionDetail.updateBy}}</el-descriptions-item>
        <el-descriptions-item label="修改时间" width="50px" align="center">{{professionDetail.updateTime}}</el-descriptions-item>
      </el-descriptions>
    </el-drawer>
    <!-- 修改岗位 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogChange"
        title="修改岗位信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="updateProfessionQuery" label-width="80px" label-position="left" ref="professionRef">
        <el-form-item prop="name" :rules="checkRules.name" label="岗位名">
          <el-input v-model="updateProfessionQuery.name" placeholder="请输入岗位名"/>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="updateProfessionQuery.description" type="textarea" placeholder="请输入描述"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdate">修改</el-button>
          <el-button @click="handleClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 新增岗位 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogSave"
        title="新增岗位信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="saveProfessionQuery" ref="professionRef" label-width="80px" label-position="left">
        <el-form-item label="岗位名" prop="name" :rules="checkRules.name">
          <el-input v-model="saveProfessionQuery.name" placeholder="请输入岗位名"/>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="saveProfessionQuery.description" type="textarea" placeholder="请输入描述"/>
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
import {getProfessionDetail, getProfessionList, saveProfession, checkProfessionName, updateProfession, deleteProfession} from "@/api/profession";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: "ProfessionComponent",
  data() {
    return {
      // 查询表单
      getProfessionQuery: {
        "description": "",
        "name": "",
        "pageNo": 1,
        "pageSize": 10,
        "pid": ""
      },
      // 总页数
      totalRecords: 100,
      // 详细岗位信息
      professionDetail: this.resetProfessionDetail(),
      // 岗位列表
      professionList: [],
      // 保存岗位请求参数
      saveProfessionQuery: this.resetSaveProfessionQuery(),
      // 修改岗位请求参数
      updateProfessionQuery: this.resetUpdateProfessionQuery(),
      professionRef: {name: ''},
      // 检查表单条件
      checkRules: {
        name: [
          {
            required: true,
            message: '岗位名不能为空',
            trigger: 'blur',
          },
          {
            asyncValidator: this.validateProfessionName,
            trigger: 'blur',
          },
        ]
      },
      dialogChange: false,
      dialogDetail: false,
      dialogSave: false,
      small: false,
      background: true,
      disabled: false
    }
  },
  methods: {
    // 获取岗位列表
    doGetProfessionList() {
      getProfessionList(this.getProfessionQuery).then(resp => {
        if (resp.success) {
          this.totalRecords = resp.data.totalRecords
          this.professionList = resp.data.records
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 根据查询条件查询
    formQuery() {
      this.doGetProfessionList()
    },
    // 更改页面显示条目
    handleSizeChange() {
      this.doGetProfessionList()
    },
    // 更改当前页码
    handleCurrentChange() {
      this.doGetProfessionList()
    },
    // 查看岗位详情
    handleDetail(pid) {
      getProfessionDetail(pid).then(resp => {
        if (resp.success) {
          this.professionDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 异步判断岗位名是否重复
    async validateProfessionName(rule, value, callback) {
      await checkProfessionName(value).then(resp => {
        if (resp.success) {
          if (resp.data === null || resp.data === '') {
            callback()
          } else if (resp.data === this.updateProfessionQuery.pid) {
            callback()
          } else {
            callback(new Error('岗位名不能重复'))
          }
        } else {
          callback(new Error((resp.data !== null && resp.data !== '') ? resp.data : resp.message))
        }
      }).catch(() => callback(new Error('当前网络延迟较高，请稍后重试')))
    },
    // 保存岗位操作
    doSave() {
      this.$refs['professionRef'].validate((valid) => {
        if (valid) {
          saveProfession(this.saveProfessionQuery).then(resp => {
            if (resp.success) {
              this.doGetProfessionList()
              ElMessage.success('岗位保存成功')
              this.dialogSave = false
              this.saveProfessionQuery = this.resetSaveProfessionQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          })
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 点击列表中的修改按钮
    handleEdit(pid) {
      getProfessionDetail(pid).then(resp => {
        if (resp.success) {
          this.updateProfessionQuery.name = resp.data.name
          this.updateProfessionQuery.description = resp.data.description
          this.updateProfessionQuery.pid = resp.data.pid
          this.dialogChange = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))

    },
    // 执行修改操作
    doUpdate() {
      this.$refs['professionRef'].validate((valid) => {
        if (valid) {
          updateProfession(this.updateProfessionQuery).then(resp => {
            if (resp.success) {
              this.doGetProfessionList()
              ElMessage.success('修改成功')
              this.dialogChange = false
              this.updateProfessionQuery = this.resetUpdateProfessionQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 删除岗位操作
    handleDelete(pid) {
      ElMessageBox.confirm(
          '您确定要删除该岗位吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteProfession(pid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetProfessionList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))      }).catch(() => {
        ElMessage.info('取消删除岗位操作')
      })
    },
    // 关闭抽屉
    handleClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.professionDetail = this.resetProfessionDetail()
      this.saveProfessionQuery = this.resetSaveProfessionQuery()
      this.updateProfessionQuery = this.resetUpdateProfessionQuery()
    },
    resetProfessionDetail() {
      return {
        "allowDel": true,
        "createBy": "string",
        "createTime": "2022-10-18T14:23:00.945Z",
        "description": "string",
        "name": "string",
        "pid": 0,
        "updateBy": "string",
        "updateTime": "2022-10-18T14:23:00.945Z"
      }
    },
    resetSaveProfessionQuery() {
      return {
        "description": "",
        "name": ""
      }
    },
    resetUpdateProfessionQuery() {
      return {
        "description": "",
        "name": "",
        "pid": 0
      }
    }
  },
  components: {
    StarFilled
  },
  mounted() {
    this.doGetProfessionList()
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
