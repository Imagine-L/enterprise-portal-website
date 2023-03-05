<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">页面管理</h1>
      </el-col>
    </el-row>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <!-- 查询表单 -->
    <el-row>
      <el-col :span="20" :offset="1">
        <el-form :inline="true" :model="getPlateQuery" class="demo-form-inline">
          <el-form-item label="板块标题">
            <el-input v-model="getPlateQuery.plateName" placeholder="请输入板块标题"/>
          </el-form-item>
          <el-form-item label="板块类型">
            <el-select v-model="getPlateQuery.plateType" placeholder="请选择板块类型" clearable>
              <el-option label="左对齐" value="0"/>
              <el-option label="居中" value="1"/>
              <el-option label="右对齐" value="2"/>
            </el-select>
          </el-form-item>
          <el-form-item label="绑定栏目">
            <el-cascader :options="multiNavList" clearable placeholder="请选择绑定栏目" v-model="searchMultiBindNav"/>
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
        <el-table :data="plateList" border style="width: 100%" height="400px">
          <el-table-column prop="plateName" label="板块标题" width="180" align="center"/>
          <el-table-column label="板块链接" width="250" align="center">
            <template #default="scope">
              <el-link :href="scope.row.link" target="target" :underline="false">{{ scope.row.link }}</el-link>
            </template>
          </el-table-column>
          <el-table-column prop="plateType" label="板块类型" width="180" align="center"/>
          <el-table-column prop="bindNav" label="绑定栏目" width="180" align="center"/>
          <el-table-column label="发布" width="100" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.released ? '' : 'warning'">{{ scope.row.released ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="禁用" width="100" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.disabled ? 'danger' : ''">{{ scope.row.disabled ? 'YES' : 'NO' }}</el-tag>
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
            v-model:currentPage="getPlateQuery.pageNo"
            v-model:page-size="getPlateQuery.pageSize"
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
    <!-- 页面详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="页面详细信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
        size="50%"
    >
      <el-descriptions border column="1" direction="vertical">
        <el-descriptions-item label="板块编号" width="50px" align="center">{{ plateDetail.pid }}</el-descriptions-item>
        <el-descriptions-item label="板块标题" width="50px" align="center">{{ plateDetail.plateName }}
        </el-descriptions-item>
        <el-descriptions-item label="板块图片" width="50px" align="center">
          <el-image style="width: 95%" :src="plateDetail.image" alt="栏目图片"></el-image>
        </el-descriptions-item>
        <el-descriptions-item label="板块链接" width="50px" align="center">
          <el-link :href="plateDetail.link" target="target" :underline="false">{{ plateDetail.link }}</el-link>
        </el-descriptions-item>
        <el-descriptions-item label="板块类型" width="50px" align="center">{{ plateDetail.plateTypeName }}
        </el-descriptions-item>
        <el-descriptions-item label="绑定栏目" width="50px" align="center">{{ plateDetail.bindNavName }}
        </el-descriptions-item>
        <el-descriptions-item label="排序种子" width="50px" align="center">{{ plateDetail.orderSeed }}
        </el-descriptions-item>
        <el-descriptions-item label="禁用" width="50px" align="center">
          <el-tag size="small" :type="plateDetail.disabled ? 'danger' : ''">{{ plateDetail.disabled ? 'YES' : 'NO' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="发布" width="50px" align="center">
          <el-tag size="small" :type="plateDetail.released ? '' : 'warning'">{{ plateDetail.released ? 'YES' : 'NO' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="板块描述" width="50px" align="center">{{ plateDetail.description }}
        </el-descriptions-item>
        <el-descriptions-item label="创建者" width="50px" align="center">{{ plateDetail.createBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{
            plateDetail.createTime
          }}
        </el-descriptions-item>
        <el-descriptions-item label="修改者" width="50px" align="center">{{ plateDetail.updateBy }}</el-descriptions-item>
        <el-descriptions-item label="修改时间" width="50px" align="center">{{
            plateDetail.updateTime
          }}
        </el-descriptions-item>
      </el-descriptions>
    </el-drawer>
    <!-- 修改页面 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogChange"
        title="修改板块信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="updatePlateQuery" ref="plateRef" label-width="80px" label-position="left">
        <el-form-item label="板块标题" prop="plateName" :rules="checkRules.plateName">
          <el-input v-model="updatePlateQuery.plateName" placeholder="请输入板块标题"/>
        </el-form-item>
        <el-form-item label="板块图片">
          <el-upload
              class="upload-demo"
              ref="uploadRef"
              :auto-upload="true"
              :multiple="false"
              :accept="acceptFile"
              :before-upload="setUpdateFile"
              :limit="1"
              :align="'left'"
          >
            <el-button :type="uploadBtnState.type" style="width: 100%">{{ uploadBtnState.text }}</el-button>
            <template #tip>
              <div class="el-upload__tip">
                请上传图片类型的文件
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="板块链接">
          <el-input v-model="updatePlateQuery.link" placeholder="请输入板块链接"/>
        </el-form-item>
        <el-form-item label="板块类型" prop="plateType" :rules="checkRules.plateType">
          <el-select v-model="updatePlateQuery.plateType" placeholder="请选择板块类型">
            <el-option label="左对齐" value="0"/>
            <el-option label="居中" value="1"/>
            <el-option label="右对齐" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定栏目" prop="bindNavArr" :rules="checkRules.bindNavArr">
          <el-cascader v-model="updatePlateQuery.bindNavArr" :options="multiNavList" clearable placeholder="请选择绑定栏目"/>
        </el-form-item>
        <el-form-item label="板块描述" prop="description" :rules="checkRules.description">
          <el-input v-model="updatePlateQuery.description" type="textarea" placeholder="请输入板块"/>
        </el-form-item>
        <el-form-item label="禁用">
          <el-switch v-model="updatePlateQuery.disabled"/>
        </el-form-item>
        <el-form-item label="发布">
          <el-switch v-model="updatePlateQuery.released"/>
        </el-form-item>
        <el-form-item label="排序种子">
          <el-input-number v-model="updatePlateQuery.orderSeed" :min="1" :max="120"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdate">修改</el-button>
          <el-button @click="handleClose">取消</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 新增页面 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogSave"
        title="新增板块信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-form :model="savePlateQuery" label-width="80px" label-position="left" ref="plateRef">
        <el-form-item prop="plateName" :rules="checkRules.plateName" label="板块标题">
          <el-input v-model="savePlateQuery.plateName" placeholder="请输入板块标题"/>
        </el-form-item>
        <el-form-item label="板块图片" prop="image" :rules="checkRules.image">
          <el-upload
              class="upload-demo"
              ref="uploadRef"
              :auto-upload="true"
              :multiple="false"
              :accept="acceptFile"
              :before-upload="setSaveFile"
              :limit="1"
              :align="'left'"
          >
            <el-button :type="uploadBtnState.type" style="width: 100%">{{ uploadBtnState.text }}</el-button>
            <template #tip>
              <div class="el-upload__tip">
                请上传图片类型的文件
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="板块链接">
          <el-input v-model="savePlateQuery.link" placeholder="请输入板块链接"/>
        </el-form-item>
        <el-form-item label="板块类型" prop="plateType" :rules="checkRules.plateType">
          <el-select v-model="savePlateQuery.plateType" placeholder="请选择板块类型">
            <el-option label="左对齐" value="0"/>
            <el-option label="居中" value="1"/>
            <el-option label="右对齐" value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定栏目" prop="bindNavArr" :rules="checkRules.bindNavArr">
          <el-cascader v-model="savePlateQuery.bindNavArr" :options="multiNavList" clearable placeholder="请选择绑定栏目"/>
        </el-form-item>
        <el-form-item label="板块描述" prop="description" :rules="checkRules.description">
          <el-input v-model="savePlateQuery.description" type="textarea" placeholder="请输入板块"/>
        </el-form-item>
        <el-form-item label="禁用">
          <el-switch v-model="savePlateQuery.disabled"/>
        </el-form-item>
        <el-form-item label="发布">
          <el-switch v-model="savePlateQuery.released"/>
        </el-form-item>
        <el-form-item label="排序种子">
          <el-input-number v-model="savePlateQuery.orderSeed" :min="1" :max="120"/>
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
import {ElMessage, ElMessageBox} from "element-plus";
import {deletePlate, getPlateDetail, getPlateList, savePlate, updatePlate} from "@/api/pagePlate";
import {multiNavigation} from "@/api/navigation";

export default {
  name: "PagePlate",
  data() {
    return {
      // 查询表单
      getPlateQuery: {
        "bindNav": "",
        "pageNo": 1,
        "pageSize": 10,
        "plateName": "",
        "plateType": ""
      },
      // 查询表单绑定栏目
      searchMultiBindNav: [],
      // 板块列表
      plateList: [],
      // 总记录数
      totalRecords: 0,
      // 页面多级栏目列表
      multiNavList: [],
      // 板块详情
      plateDetail: this.resetPlateDetail(),
      // 允许上传的文件类型
      acceptFile: 'image/*',
      // 上传按钮状态
      uploadBtnState: this.uploadBtnInit(),
      // 保存表单参数
      savePlateQuery: this.resetSavePlateQuery(),
      // 更新表单参数
      updatePlateQuery: this.resetUpdatePlateQuery(),
      plateRef: {
        plateName: '',
        image: Object,
        plateType: '',
        bindNavArr: '',
        description: ''
      },
      // 检查规则
      checkRules: {
        plateName: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'blur',
          },
          {min: 0, max: 20, message: '长度需要在0-20之间', trigger: 'blur'}
        ],
        image: [
          {
            required: true,
            message: '请选择栏目图片',
            trigger: 'change',
          },
          {
            validator: this.validateImage,
            trigger: 'change',
          }
        ],
        plateType: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'change',
          },
        ],
        bindNavArr: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'change',
          },
        ],
        description: {min: 0, max: 300, message: '长度需要在0-300之间', trigger: 'blur'},
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
    // 获取板块列表
    doGetPlateList() {
      if (this.searchMultiBindNav != null &&
          this.searchMultiBindNav.length !== 0) {
        this.getPlateQuery.bindNav = this.searchMultiBindNav[this.searchMultiBindNav.length - 1]
      } else {
        this.getPlateQuery.bindNav = ""
      }
      getPlateList(this.getPlateQuery).then(resp => {
        if (resp.success) {
          this.plateList = resp.data.records
          this.totalRecords = resp.data.totalRecords
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 获取多级栏目列表
    getMultiNav() {
      multiNavigation(1).then(resp => {
        if (resp.success) {
          if (resp.data === null) return
          let navs = resp.data
          this.multiNavList = this.buildMultiNav(navs)
        }
      })
    },
    // 点击查询按钮
    formQuery() {
      this.doGetPlateList()
    },
    // 页面显示的条数发生变化
    handleSizeChange() {
      this.doGetPlateList()
    },
    // 当前页码发生变化
    handleCurrentChange() {
      this.doGetPlateList()
    },
    // 获取板块
    handleDetail(pid) {
      getPlateDetail(pid).then(resp => {
        if (resp.success) {
          this.plateDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 保存板块
    doSave() {
      this.$refs['plateRef'].validate((valid) => {
        if (valid) {
          this.savePlateQuery.bindNav = this.savePlateQuery.bindNavArr[this.savePlateQuery.bindNavArr.length - 1]
          savePlate(this.savePlateQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('保存成功')
              this.doGetPlateList()
              this.dialogSave = false
              this.savePlateQuery = this.resetSavePlateQuery()
              this.uploadBtnState = this.uploadBtnInit()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 更新板块
    doUpdate() {
      this.$refs['plateRef'].validate((valid) => {
        if (valid) {
          if (this.updatePlateQuery.image !== null) {
            let image = this.updatePlateQuery.image
            let type = ''
            if (image.type !== undefined && image.type !== null) {
              type = image.type.substring(0,5);
            }
            if (type !== 'image') {
              ElMessage.warning('图片后缀不符合要求')
              return
            }
          } else {
            this.updatePlateQuery.image = undefined
          }
          if (this.updatePlateQuery.bindNavArr instanceof Array) {
            this.updatePlateQuery.bindNav = this.updatePlateQuery.bindNavArr[this.savePlateQuery.bindNavArr.length - 1]
          } else {
            this.updatePlateQuery.bindNav = this.updatePlateQuery.bindNavArr
          }
          updatePlate(this.updatePlateQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('修改成功')
              this.doGetPlateList()
              this.dialogChange = false
              this.updatePlateQuery = this.resetUpdatePlateQuery()
              this.uploadBtnState = this.uploadBtnInit()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))

        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 点击修改按钮
    handleEdit(pid) {
      getPlateDetail(pid).then(resp => {
        if (resp.success) {
          this.updatePlateQuery.bindNavArr = resp.data.bindNav
          this.updatePlateQuery.description = resp.data.description
          this.updatePlateQuery.disabled = resp.data.disabled
          this.updatePlateQuery.link = resp.data.link
          this.updatePlateQuery.orderSeed = resp.data.orderSeed
          this.updatePlateQuery.pid = resp.data.pid
          this.updatePlateQuery.plateName = resp.data.plateName
          this.updatePlateQuery.plateType = resp.data.plateType + ''
          this.updatePlateQuery.released = resp.data.released
          this.dialogChange = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    handleDelete(pid) {
      ElMessageBox.confirm(
          '您确定要删除该板块吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deletePlate(pid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetPlateList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除板块操作')
      })
    },
    // 判断文件后缀是否合法
    validateImage(rule, value, callback) {
      let type = ''
      if (value.type !== undefined && value.type !== null) {
        type = value.type.substring(0, 5);
      }
      if (type === 'image') {
        callback()
      } else {
        callback(new Error('文件后缀不符合要求'))
      }
    },
    handleClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.savePlateQuery = this.resetSavePlateQuery()
      this.plateDetail = this.resetPlateDetail()
      this.uploadBtnState = this.uploadBtnInit()
    },
    // 构造绑定栏目
    buildMultiNav(navs) {
      let options = []
      for (let i in navs) {
        let nav = {}
        nav.value = navs[i].nid
        nav.label = navs[i].navName
        if (navs[i].children !== null && navs[i].children.length !== 0) {
          nav.children = this.buildMultiNav(navs[i].children)
        }
        options.push(nav)
      }
      return options
    },
    setSaveFile(file) {
      this.savePlateQuery.image = file
      this.uploadBtnState = this.uploadBtnReady()
      return false
    },
    setUpdateFile(file) {
      this.updatePlateQuery.image = file
      this.uploadBtnState = this.uploadBtnReady()
      return false
    },
    uploadBtnInit() {
      return {
        type: 'primary',
        text: '点我上传'
      }
    },
    uploadBtnReady() {
      return {
        type: 'success',
        text: '重新上传'
      }
    },
    resetPlateDetail() {
      return {
        "bindNav": 0,
        "bindNavName": "string",
        "createBy": "string",
        "createTime": "2022-10-31T08:06:07.286Z",
        "description": "string",
        "disabled": true,
        "image": "string",
        "link": "string",
        "orderSeed": 0,
        "pid": 0,
        "plateName": "string",
        "plateType": 0,
        "plateTypeName": "string",
        "released": true,
        "updateBy": "string",
        "updateTime": "2022-10-31T08:06:07.286Z"
      }
    },
    resetSavePlateQuery() {
      return {
        bindNav: '',
        bindNavArr: [],
        description: '',
        disabled: false,
        image: null,
        link: '',
        orderSeed: 1,
        plateName: '',
        plateType: '',
        released: true
      }
    },
    resetUpdatePlateQuery() {
      return {
        pid: '',
        bindNav: '',
        bindNavArr: [],
        description: '',
        disabled: false,
        image: null,
        link: '',
        orderSeed: 1,
        plateName: '',
        plateType: '',
        released: true
      }
    }
  },
  components: {
    StarFilled
  },
  mounted() {
    this.doGetPlateList()
    this.getMultiNav()
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
