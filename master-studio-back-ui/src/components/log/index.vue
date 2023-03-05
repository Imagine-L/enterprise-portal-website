<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">日志监控</h1>
      </el-col>
    </el-row>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <!-- 查询表单 -->
    <el-row>
      <el-col :offset="1">
        <el-form :inline="true" :model="getLogQuery" class="demo-form-inline" :align="'left'">
          <el-form-item label="操作模块">
            <el-input v-model="getLogQuery.module" placeholder="请输入操作模块"/>
          </el-form-item>
          <el-form-item label="操作类型">
            <el-input v-model="getLogQuery.operType" placeholder="请输入操作模块"/>
          </el-form-item>
          <el-form-item label="请求方式">
            <el-select v-model="getLogQuery.requestMode" placeholder="请选择请求方式">
              <el-option label="GET" value="GET"/>
              <el-option label="POST" value="POST"/>
              <el-option label="PUT" value="PUT"/>
              <el-option label="DELETE" value="DELETE"/>
            </el-select>
          </el-form-item>
          <el-form-item label="操作者">
            <el-input v-model="getLogQuery.requestUser" placeholder="请输入操作者"/>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
                v-model="dateValue"
                type="datetimerange"
                range-separator="To"
                format="YYYY-MM-DD hh:mm:ss"
                value-format="YYYY-MM-DD hh:mm:ss"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                lang="china"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="formQuery">查询</el-button>
            <el-button type="danger" @click="handleDelete" plain v-if="allowDelete">删除</el-button>
            <el-button type="danger" @click="handleDeleteAll" v-if="allowDelete">清空</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 主体表格 -->
    <el-row justify="center">
      <el-col :span="23">
        <el-table :data="logList" ref="logRef" border style="width: 100%" height="400px">
          <el-table-column type="selection" width="40" fixed/>
          <el-table-column prop="module" label="操作模块" width="180" align="center"/>
          <el-table-column prop="operType" label="操作类型" width="180" align="center"/>
          <el-table-column label="请求方式" width="100" align="center">
            <template #default="scope">
              <el-tag>{{ scope.row.requestMode }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="requestAddr" label="请求地址" width="180" align="center"/>
          <el-table-column label="请求对象" width="180" align="center">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <pre style="font-family: Consolas, serif; margin: 0">{{ formatJson(scope.row.requestJson) }}</pre>
                <template #reference>
                  <el-tag>{{ scope.row.requestJson.slice(0, 20) }} ...</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="requestUser" label="操作用户" width="180" align="center"/>
          <el-table-column prop="success" label="成功" width="80" align="center">
            <template #default="scope">
              <el-tag>{{ scope.row.success ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="handleDetail(scope.row.lid)"
              >详情
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
            v-model:currentPage="getLogQuery.pageNo"
            v-model:page-size="getLogQuery.pageSize"
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
    <!-- 日志详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="日志详细信息"
        :before-close="handleClose"
        direction="rtl"

        custom-class="demo-drawer"
    >
      <el-descriptions border column="1">
        <el-descriptions-item label="日志编号" width="50px" align="center">{{ logDetail.lid }}</el-descriptions-item>
        <el-descriptions-item label="操作模块" width="50px" align="center">{{ logDetail.module }}</el-descriptions-item>
        <el-descriptions-item label="操作类型" width="50px" align="center">{{ logDetail.operType }}</el-descriptions-item>
        <el-descriptions-item label="请求方式" width="50px" align="center">
          <el-tag size="small">{{ logDetail.requestMode }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="请求地址" width="50px" align="center">{{ logDetail.requestAddr }}
        </el-descriptions-item>
        <el-descriptions-item label="请求参数" width="50px" align="center">
          <el-popover
              placement="top-start"
              title="请求参数"
              width="auto"
              trigger="hover"
          >
            <pre>{{ formatJson(logDetail.requestJson) }}</pre>
            <!--            <pre>{{ JSON.stringify(this.form, null, 4) }}</pre>-->
            <template #reference>
              <el-tag size="small">{{ logDetail.requestJson.slice(0, 20) }} ...</el-tag>
            </template>
          </el-popover>
        </el-descriptions-item>
        <el-descriptions-item label="处理方法" width="50px" align="center">
          <el-popover
              placement="top-start"
              title="处理方法"
              width="auto"
              trigger="hover"
          >
            <p>{{ logDetail.handlerMethod }}</p>
            <template #reference>
              <el-tag size="small">{{ logDetail.handlerMethod.slice(0, 20) }} ...</el-tag>
            </template>
          </el-popover>
        </el-descriptions-item>
        <el-descriptions-item label="是否成功" width="50px" align="center">
          <el-tag size="small">{{ logDetail.success }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="异常类名" width="50px" align="center">{{ logDetail.errorClass }}</el-descriptions-item>
        <el-descriptions-item label="异常信息" width="50px" align="center">{{ logDetail.errorMessage }}
        </el-descriptions-item>
        <el-descriptions-item label="操作者" width="50px" align="center">{{ logDetail.requestUser }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{ logDetail.createTime }}</el-descriptions-item>
        <el-descriptions-item label="程序执行时间" width="50px" align="center">{{ logDetail.runtime }} ms
        </el-descriptions-item>
      </el-descriptions>
    </el-drawer>
  </div>

</template>

<script>
import {StarFilled} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import {deleteLogAll, deleteLogList, getLogDetail, getLogList} from "@/api/log";

export default {
  name: "LogComponent",
  data() {
    return {
      // 查询日志列表参数
      getLogQuery: {
        "createStartTime": "",
        "createStopTime": "",
        "module": "",
        "operType": "",
        "pageNo": 1,
        "pageSize": 10,
        "requestMode": "",
        "requestUser": ""
      },
      // 日志列表
      logList: [],
      logRef: {},
      // 查询日期
      dateValue: [],
      // 总页数
      totalRecords: 100,
      // 日志详情
      logDetail: {
        "createTime": "",
        "errorClass": "",
        "errorMessage": "",
        "handlerMethod": "",
        "lid": 0,
        "module": "",
        "operType": "",
        "requestAddr": "",
        "requestJson": "",
        "requestMode": "",
        "requestUser": "",
        "runtime": 0,
        "success": true
      },
      dialogDetail: false,
      small: false,
      background: true,
      disabled: false
    }
  },
  computed: {
    allowDelete() {
      return this.$store.getters.isAdmin
    }
  },
  methods: {
    // 格式化json
    formatJson(json) {
      let obj = JSON.parse(json)
      return JSON.stringify(obj, null, 4)
    },
    // 获取日志列表
    doGetLogList() {
      if (this.dateValue !== null && this.dateValue.length !== 0) {
        this.getLogQuery.createStartTime = this.dateValue[0]
        this.getLogQuery.createStopTime = this.dateValue[1]
      } else {
        this.getLogQuery.createStartTime = null
        this.getLogQuery.createStopTime = null
      }
      getLogList(this.getLogQuery).then(resp => {
        if (resp.success) {
          this.logList = resp.data.records
          this.totalRecords = resp.data.totalRecords
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 查询按钮
    formQuery() {
      this.doGetLogList()
    },
    // 页面显示的条数发生变化
    handleSizeChange() {
      this.doGetLogList()
    },
    // 当前页码发生变化
    handleCurrentChange() {
      this.doGetLogList()
    },
    // 获取日志详情
    handleDetail(lid) {
      getLogDetail(lid).then(resp => {
        if (resp.success) {
          this.logDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 删除日志
    handleDelete() {
      let rows = this.$refs['logRef'].getSelectionRows()
      if (rows === null || rows.length === 0) {
        ElMessage.warning('请先选择需要删除的日志记录')
        return
      }
      // 警告提示框
      ElMessageBox.confirm(
          '您确定要删除这些日志吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        let deleteList = []
        for (let i in rows) {
          deleteList.push(rows[i].lid)
        }
        // 发起删除请求
        deleteLogList(deleteList).then(resp => {
          if (resp.success) {
            ElMessage.success("删除成功")
            this.doGetLogList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => ElMessage.info('取消删除日志操作'))
    },
    // 删除全部日志
    handleDeleteAll() {
      ElMessageBox.prompt('您确定要删除全部日志吗？删除后将无法恢复！如果您确定的话，请在下面输入框输入您的用户名：', '严重警告', {
        confirmButtonText: '非常确定',
        cancelButtonText: '取消',
        inputValidator: this.validateUsername,
        inputErrorMessage: '错误的用户名',
      }).then(() => {
        deleteLogAll().then(resp => {
          if (resp.success) {
            ElMessage.success('删除全部日志成功')
            this.logList = []
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除日志操作',
        })
      })
    },
    // 校验用户名
    validateUsername(value) {
      let username = this.$store.getters.username
      return value === username;
    },
    handleClose() {
      this.dialogDetail = false
    }
  },
  components: {
    StarFilled
  },
  mounted() {
    this.doGetLogList()
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
