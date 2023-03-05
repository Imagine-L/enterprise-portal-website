<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">栏目管理</h1>
      </el-col>
    </el-row>
    <el-divider style="margin-bottom: 10px">
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-change="changeActiveTab">
      <el-tab-pane label="常规栏目" name="navigation">
        <!-- 查询表单 -->
        <el-row>
          <el-col :span="20" :offset="1">
            <el-form :inline="true" :model="getNavigationQuery" class="demo-form-inline">
              <el-form-item label="栏目名">
                <el-input v-model="getNavigationQuery.navName" placeholder="请输入栏目名"/>
              </el-form-item>
              <el-form-item label="栏目级别">
                <el-select v-model="getNavigationQuery.level" placeholder="请选择栏目级别" clearable>
                  <el-option label="一级栏目" value="1"/>
                  <el-option label="二级栏目" value="2"/>
                </el-select>
              </el-form-item>
              <el-form-item label="栏目类型">
                <el-select v-model="getNavigationQuery.navType" placeholder="请选择栏目类型" clearable>
                  <el-option label="父栏目" value="0"/>
                  <el-option label="页面" value="1"/>
                  <el-option label="文章" value="2"/>
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
            <el-table :data="navList" border style="width: 100%" height="340px">
              <el-table-column prop="navName" label="栏目名" width="150" align="center"/>
              <el-table-column label="栏目级别" width="150">
                <template #default="scope">
                  <el-tag>{{ scope.row.level }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="parentName" label="父栏目" width="150" align="center"/>
              <el-table-column label="访问路径" width="150" align="center">
                <template #default="scope">
                  <el-tag>{{ scope.row.path }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="navType" label="栏目类型" width="150" align="center"/>
              <el-table-column label="是否可用" width="100" align="center">
                <template #default="scope">
                  <el-tag :type="scope.row.used ? '' : 'warning'">{{ scope.row.used ? 'YES' : 'NO' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="是否禁用" width="100" align="center">
                <template #default="scope">
                  <el-tag :type="scope.row.disabled ? 'danger' : ''">{{ scope.row.disabled ? 'YES' : 'NO' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center">
                <template #default="scope">
                  <el-button size="small" @click="handleDetail(scope.row.nid)"
                  >详情
                  </el-button>
                  <el-button size="small" @click="handleNavEdit(scope.row.nid)"
                  >修改
                  </el-button>
                  <el-button
                      size="small"
                      type="danger"
                      @click="handleNavDelete(scope.row.nid)"
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
                v-model:currentPage="getNavigationQuery.pageNo"
                v-model:page-size="getNavigationQuery.pageSize"
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

        <!-- 栏目详情 -->
        <el-drawer
            ref="drawerRef"
            v-model="dialogDetail"
            title="栏目详细信息"
            :before-close="handleNavClose"
            direction="rtl"
            size="50%"
            custom-class="demo-drawer"
        >
          <el-descriptions border column="1" direction="vertical">
            <el-descriptions-item label="栏目编号" width="50px" align="center">{{ navDetail.nid }}</el-descriptions-item>
            <el-descriptions-item label="栏目名" width="50px" align="center">{{ navDetail.navName }}</el-descriptions-item>
            <el-descriptions-item label="栏目级别" width="50px" align="center">
              <el-tag>{{ navDetail.levelName }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="父栏目" width="50px" align="center">{{ navDetail.parentName }}
            </el-descriptions-item>
            <el-descriptions-item label="栏目类型" width="50px" align="center">{{ navDetail.navTypeName }}
            </el-descriptions-item>
            <el-descriptions-item label="栏目路径" width="50px" align="center">
              <el-tag>{{ navDetail.path }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="栏目图片" width="50px" align="center">
              <el-image style="width: 95%" :src="navDetail.image" alt="栏目图片"></el-image>
            </el-descriptions-item>
            <el-descriptions-item label="栏目描述" width="50px" align="center">{{ navDetail.description }}
            </el-descriptions-item>
            <el-descriptions-item label="排序种子" width="50px" align="center">{{ navDetail.orderSeed }}
            </el-descriptions-item>
            <el-descriptions-item label="是否可用" width="50px" align="center">
              <el-tag size="small" :type="navDetail.used ? '' : 'warning'">{{ navDetail.used ? 'YES' : 'NO' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="是否禁用" width="50px" align="center">
              <el-tag size="small" :type="navDetail.disabled ? 'danger' : ''">{{ navDetail.disabled ? 'YES' : 'NO' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="首页展示" width="50px" align="center">
              <el-tag size="small">{{ navDetail.showed ? 'YES' : 'NO' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="允许删除" width="50px" align="center">
              <el-tag size="small">{{ navDetail.allowDel ? 'YES' : 'NO' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="创建者" width="50px" align="center">{{ navDetail.createBy }}
            </el-descriptions-item>
            <el-descriptions-item label="创建时间" width="50px" align="center">{{ navDetail.createTime }}
            </el-descriptions-item>
            <el-descriptions-item label="修改者" width="50px" align="center">{{ navDetail.updateBy }}
            </el-descriptions-item>
            <el-descriptions-item label="修改时间" width="50px" align="center">{{ navDetail.updateTime }}
            </el-descriptions-item>
          </el-descriptions>
        </el-drawer>
        <!-- 修改栏目 -->
        <el-drawer
            ref="drawerRef"
            v-model="dialogChange"
            title="修改栏目"
            :before-close="handleNavClose"
            direction="rtl"
            custom-class="demo-drawer"
        >
          <el-form :model="updateNavigationQuery" ref="navRef" label-width="80px" label-position="left">
            <el-form-item label="栏目名" prop="navName" :rules="navCheckRules.navName">
              <el-input v-model="updateNavigationQuery.navName" placeholder="请输入栏目名"/>
            </el-form-item>
            <el-form-item label="栏目级别" prop="level" :rules="navCheckRules.level">
              <el-select v-model="updateNavigationQuery.level" placeholder="请选择栏目级别" @change="navLevelChange">
                <el-option label="一级栏目" value="1"/>
                <el-option label="二级栏目" value="2" v-if="updateNavigationQuery.allowDel"/>
              </el-select>
            </el-form-item>
            <el-form-item label="父栏目" prop="parentId" :rules="navCheckRules.parentId" v-if="showParent">
              <el-select v-model="updateNavigationQuery.parentId" placeholder="请选择父栏目">
                <el-option v-for="item in firstLevelNames" :key="item.nid" :label="item.navName" :value="item.nid"/>
              </el-select>
            </el-form-item>
            <el-form-item label="栏目类型" prop="navType" :rules="navCheckRules.navType">
              <el-select v-model="updateNavigationQuery.navType" placeholder="请选择栏目类型">
                <el-option label="父栏目" value="0" v-if="showParentNav && updateNavigationQuery.allowDel"/>
                <el-option label="页面" value="1" v-if="updateNavigationQuery.allowDel"/>
                <el-option label="文章" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="栏目路径" prop="path" :rules="navCheckRules.path">
              <el-input v-model="updateNavigationQuery.path" placeholder="请输入栏目路径"/>
            </el-form-item>
            <el-form-item label="禁用">
              <el-switch v-model="updateNavigationQuery.disabled"/>
            </el-form-item>
            <el-form-item label="首页展示" v-if="updateNavigationQuery.allowShow">
              <el-switch v-model="updateNavigationQuery.showed"/>
            </el-form-item>
            <el-form-item label="排序种子">
              <el-input-number v-model="updateNavigationQuery.orderSeed" :min="1" :max="120"/>
            </el-form-item>
            <el-form-item label="栏目图片">
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
                <el-button :type="uploadBtnState.type">{{uploadBtnState.text}}</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传图片类型的文件
                  </div>
                </template>
              </el-upload>

            </el-form-item>
            <el-form-item label="描述" prop="description" :rules="navCheckRules.description">
              <el-input v-model="updateNavigationQuery.description" type="textarea"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doUpdateNav">修改</el-button>
              <el-button @click="handleNavClose">取消</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
        <!-- 新增栏目 -->
        <el-drawer
            ref="drawerRef"
            v-model="dialogSave"
            title="新增栏目"
            :before-close="handleNavClose"
            direction="rtl"
            custom-class="demo-drawer"
        >
          <el-form :model="saveNavigationQuery" ref="navRef" label-width="80px" label-position="left">
            <el-form-item label="栏目名" prop="navName" :rules="navCheckRules.navName">
              <el-input v-model="saveNavigationQuery.navName" placeholder="请输入栏目名"/>
            </el-form-item>
            <el-form-item label="栏目级别" prop="level" :rules="navCheckRules.level">
              <el-select v-model="saveNavigationQuery.level" placeholder="请选择栏目级别" @change="navLevelChange">
                <el-option label="一级栏目" value="1"/>
                <el-option label="二级栏目" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="父栏目" v-if="showParent" prop="parentId" :rules="navCheckRules.parentId">
              <el-select v-model="saveNavigationQuery.parentId" placeholder="请选择父栏目">
                <el-option v-for="item in firstLevelNames" :key="item.nid" :label="item.navName" :value="item.nid"/>
              </el-select>
            </el-form-item>
            <el-form-item label="栏目类型" prop="navType" :rules="navCheckRules.navType">
              <el-select v-model="saveNavigationQuery.navType" placeholder="请选择栏目类型">
                <el-option label="父栏目" value="0" v-if="showParentNav"/>
                <el-option label="页面" value="1"/>
                <el-option label="文章" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="栏目路径" prop="path" :rules="navCheckRules.path">
              <el-input v-model="saveNavigationQuery.path" placeholder="请输入栏目路径"/>
            </el-form-item>
            <el-form-item label="禁用">
              <el-switch v-model="saveNavigationQuery.disabled"/>
            </el-form-item>
            <el-form-item label="排序种子">
              <el-input-number v-model="saveNavigationQuery.orderSeed" :min="1" :max="120"/>
            </el-form-item>
            <el-form-item label="栏目图片" prop="image" :rules="navCheckRules.image">
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
                <el-button :type="uploadBtnState.type" style="width: 100%">{{uploadBtnState.text}}</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传图片类型的文件
                  </div>
                </template>
              </el-upload>
            </el-form-item>
            <el-form-item label="描述" prop="description" :rules="navCheckRules.description">
              <el-input v-model="saveNavigationQuery.description" type="textarea"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doSaveNav">保存</el-button>
              <el-button @click="handleNavClose">取消</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
      </el-tab-pane>

      <!-- 第二页 -->
      <el-tab-pane label="轮播图" name="carousel">
        <!-- 上传轮播图 -->
        <el-row>
          <el-col :span="24">
            <div class="uploadBtn">
              <el-button type="primary" @click="dialogCarouselSave=true">上传</el-button>
            </div>
          </el-col>
        </el-row>
        <div style="height: 20px"></div>
        <!-- 轮播图表格 -->
        <el-row justify="center">
          <el-col :span="23">
            <el-table :data="chartList" border style="width: 100%" height="300px">
              <el-table-column fixed label="预览" width="60" align="center">
                <template #default="scope">
                  <el-image style="width: 40px; height: 40px" :src="scope.row.image" fit="fill" lazy>
                    <template #error>
                      <div class="image-slot">
                        无法预览
                      </div>
                    </template>
                  </el-image>
                </template>
              </el-table-column>
              <el-table-column prop="image" label="图片地址" width="300" align="center">
                <template #default="scope">
                  <el-link :href="scope.row.image" target="target" :underline="false">{{scope.row.image}}</el-link>
                </template>
              </el-table-column>
              <el-table-column label="链接地址" width="300" align="center">
                <template #default="scope">
                  <el-link :href="scope.row.link" target="target" :underline="false">{{scope.row.link}}</el-link>
                </template>
              </el-table-column>
              <el-table-column prop="createBy" label="创建者" width="100" align="center"/>
              <el-table-column prop="createTime" label="创建时间" width="180" align="center"/>
              <el-table-column prop="updateBy" label="修改者" width="100" align="center"/>
              <el-table-column prop="updateTime" label="修改时间" width="180" align="center"/>
              <el-table-column label="操作" width="200" align="center">
                <template #default="scope">
                  <el-button size="small" @click="handleCarouselEdit(scope.row)"
                  >修改
                  </el-button>
                  <el-button
                      size="small"
                      type="danger"
                      @click="handleCarouselDelete(scope.row.hid)"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <!-- 新增轮播图 -->
        <el-drawer
            ref="drawerRef"
            v-model="dialogCarouselSave"
            title="新增轮播图"
            :before-close="handleCarouselClose"
            direction="rtl"
            custom-class="demo-drawer"
        >
          <el-form :model="saveChartQuery" ref="chartRef"  label-width="80px" label-position="left">
            <el-form-item :align="'left'" label="轮播图片" prop="image" :rules="chartCheckRules.image">
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
                <el-button :type="uploadBtnState.type"  style="width: 100%">{{uploadBtnState.text}}</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传图片类型的文件
                  </div>
                </template>
              </el-upload>
            </el-form-item>
            <el-form-item label="链接地址" prop="link" :rules="chartCheckRules.link">
              <el-input v-model="saveChartQuery.link" placeholder="请输入链接地址"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doSaveChart">保存</el-button>
              <el-button @click="handleCarouselClose">取消</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
        <!-- 修改轮播图 -->
        <el-drawer
            ref="drawerRef"
            v-model="dialogCarouselChange"
            title="修改轮播图信息"
            :before-close="handleCarouselClose"
            direction="rtl"
            custom-class="demo-drawer"
        >
          <el-form :model="updateChartQuery" ref="chartRef" label-width="80px" label-position="left">
            <el-form-item :align="'left'" label="轮播图片">
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
                <el-button :type="uploadBtnState.type" style="width: 100%">{{uploadBtnState.text}}</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传图片类型的文件
                  </div>
                </template>
              </el-upload>
            </el-form-item>
            <el-form-item label="链接地址" prop="link" :rules="chartCheckRules.link">
              <el-input v-model="updateChartQuery.link" placeholder="请输入链接地址"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doUpdateChart">修改</el-button>
              <el-button @click="handleCarouselClose">取消</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
import {StarFilled} from '@element-plus/icons-vue'
import {
  deleteNavigation,
  existName,
  existPath,
  getNavDetail,
  getNavList,
  getNavNamesByLevel,
  saveNavigation,
  updateNavigation
} from "@/api/navigation";
import {ElMessage, ElMessageBox} from "element-plus";
import {deleteChart, getChartList, saveChart, updateChart} from "@/api/homeChart";

export default {
  name: "NavigationComponent",
  data() {
    return {
      activeName: 'navigation',
      // 栏目列表
      navList: [],
      // 轮播图列表
      chartList: [],
      // 允许上传的文件类型
      acceptFile: 'image/*',
      // 查询表单
      getNavigationQuery: {
        "level": "",
        "navName": "",
        "navType": "",
        "pageNo": 1,
        "pageSize": 10
      },
      // 总页数
      totalRecords: 100,
      // 栏目详情
      navDetail: this.resetNavDetail(),
      // 新增栏目请求参数
      saveNavigationQuery: this.resetSaveNavigationQuery(),
      saveChartQuery: this.resetSaveChartQuery(),
      // 等级为1的栏目名列表
      firstLevelNames: [],
      // 上传按钮状态
      uploadBtnState: this.uploadBtnInit(),
      uploadRef: {},
      navRef: {
        navName: '',
        path: '',
        level: '',
        navType: '',
        parentId: '',
        image: Object,
        description: ''
      },
      // 表单检测
      navCheckRules: {
        navName: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'blur',
          },
          {
            asyncValidator: this.validateNavName,
            trigger: 'blur',
          },
        ],
        path: [
          {
            required: true,
            message: '请输入访问路径',
            trigger: 'blur',
          },
          {
            pattern: /^[a-z_\-0-9]{1,10}$/,
            message: '路径只能含小写字母、数字和下划线，且长度小于10',
            trigger: 'blur',
          },
          {
            asyncValidator: this.validatePath,
            trigger: 'blur',
          },
        ],
        level: [
          {
            required: true,
            message: '请选择栏目级别',
            trigger: 'change',
          },
        ],
        navType: [
          {
            required: true,
            message: '请选择栏目类型',
            trigger: 'change',
          },
        ],
        parentId: [
          {
            required: true,
            message: '请选择父栏目',
            trigger: 'change',
          },
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
        description: {min: 0, max: 100, message: '长度需要在0-100之间', trigger: 'blur'},
      },
      chartRef: {
        image: Object,
        link: ''
      },
      chartCheckRules: {
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
        link: {min: 0, max: 200, message: '长度需要在0-100之间', trigger: 'blur'},
      },
      // 修改请求参数
      updateNavigationQuery: this.resetUpdateNavigationQuery(),
      updateChartQuery: this.resetUpdateChartQuery(),
      showParent: false,
      showParentNav: false,
      dialogCarouselSave: false,
      dialogCarouselChange: false,
      dialogChange: false,
      dialogDetail: false,
      dialogSave: false,
      small: false,
      background: true,
      disabled: false
    }
  },
  methods: {
    // 激活标签页
    changeActiveTab() {
      if (this.activeName === 'navigation') {
        this.doGetNavList()
      } else {
        this.doGetChartList()
      }
    },
    // 根据条件获取栏目列表
    doGetNavList() {
      getNavList(this.getNavigationQuery).then(resp => {
        if (resp.success) {
          this.navList = resp.data.records
          this.totalRecords = resp.data.totalRecords
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 页面显示的条数发生变化
    handleSizeChange() {
      this.doGetNavList()
    },
    // 当前页码发生变化
    handleCurrentChange() {
      this.doGetNavList()
    },
    // 点击查询按钮
    formQuery() {
      this.doGetNavList()
    },
    // 获取栏目详情
    handleDetail(nid) {
      getNavDetail(nid).then(resp => {
        if (resp.success) {
          this.navDetail = resp.data
          this.dialogDetail = true;
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 点击新增按钮
    handleSave() {
      getNavNamesByLevel('1').then(resp => {
        if (resp.success) {
          this.firstLevelNames = resp.data
          this.navLevelChange('2')
          this.uploadBtnState = this.uploadBtnInit()
          this.dialogSave = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 执行保存操作
    doSaveNav() {
      this.$refs['navRef'].validate((valid) => {
        if (valid) {
          let formData = new FormData();
          for (let key in this.saveNavigationQuery) {
            formData.append(key, this.saveNavigationQuery[key])
          }
          saveNavigation(formData).then(resp => {
            if (resp.success) {
              ElMessage.success('栏目保存成功')
              this.doGetNavList()
              this.dialogSave = false
              this.saveNavigationQuery = this.resetSaveNavigationQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 保存轮播图
    doSaveChart() {
      this.$refs['chartRef'].validate((valid) => {
        if (valid) {
          // 封装参数
          let formData = new FormData();
          formData.append('image', this.saveChartQuery.image)
          formData.append('link', this.saveChartQuery.link)
          saveChart(formData).then(resp => {
            if (resp.success) {
              ElMessage.success('轮播图保存成功')
              this.doGetChartList()
              this.dialogCarouselSave = false
              this.saveChartQuery = this.resetSaveChartQuery()
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
    // 执行更新操作
    doUpdateNav() {
      this.$refs['navRef'].validate((valid) => {
        if (valid) {
          // 封装参数
          let formData = new FormData();
          formData.append('nid', this.updateNavigationQuery.nid)
          formData.append('navName', this.updateNavigationQuery.navName)
          formData.append('level', this.updateNavigationQuery.level)
          if (this.updateNavigationQuery.level === '2') {
            formData.append('parentId', this.updateNavigationQuery.parentId)
          }
          if (this.updateNavigationQuery.image !== null) {
            let image = this.updateNavigationQuery.image
            let type = ''
            if (image.type !== undefined && image.type !== null) {
              type = image.type.substring(0,5);
            }
            if (type === 'image') {
              formData.append('image', this.updateNavigationQuery.image)
            } else {
              ElMessage.warning('图片后缀不符合要求')
              return
            }
          }
          formData.append('description', this.updateNavigationQuery.description)
          formData.append('disabled', this.updateNavigationQuery.disabled);
          formData.append('showed', this.updateNavigationQuery.allowShow && this.updateNavigationQuery.showed)
          formData.append('navType', this.updateNavigationQuery.navType)
          formData.append('path', this.updateNavigationQuery.path)
          formData.append('orderSeed', this.updateNavigationQuery.orderSeed)
          // 发起请求
          updateNavigation(formData).then(resp => {
            if (resp.success) {
              ElMessage.success('栏目修改成功')
              this.doGetNavList()
              this.dialogChange = false
              this.updateNavigationQuery = this.resetUpdateNavigationQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    // 更新轮播图
    doUpdateChart() {
      this.$refs['chartRef'].validate((valid) => {
        if (valid) {
          // 封装参数
          let formData = new FormData();
          formData.append('hid', this.updateChartQuery.hid)
          if (this.updateChartQuery.image !== null) {
            let image = this.updateChartQuery.image
            let type = ''
            if (image.type !== undefined && image.type !== null) {
              type = image.type.substring(0,5);
            }
            if (type === 'image') {
              formData.append('image', this.updateChartQuery.image)
            } else {
              ElMessage.warning('图片后缀不符合要求')
              return
            }
          }
          formData.append('link', this.updateChartQuery.link)
          updateChart(formData).then(resp => {
            if (resp.success) {
              ElMessage.success('轮播图修改成功')
              this.doGetChartList()
              this.dialogCarouselChange = false
              this.updateChartQuery = this.resetUpdateChartQuery()
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    setSaveFile(file) {
      if (this.activeName === 'navigation') {
        this.saveNavigationQuery.image = file
      } else {
        this.saveChartQuery.image = file
      }
      this.uploadBtnState = this.uploadBtnReady()
      return false
    },
    setUpdateFile(file) {
      if (this.activeName === 'navigation') {
        this.updateNavigationQuery.image = file
      } else {
        this.updateChartQuery.image = file
      }
      this.uploadBtnState = this.uploadBtnReady()
      return false
    },
    // 校验栏目名是否重复
    async validateNavName(rule, value, callback) {
      await existName(value).then(resp => {
        if (resp.success) {
          if (resp.data === null || resp.data === '') {
            callback()
          } else if (resp.data === this.updateNavigationQuery.nid) {
            callback()
          } else {
            callback(new Error('栏目名不能重复'))
          }
        } else {
          callback(new Error((resp.data !== null && resp.data !== '') ? resp.data : resp.message))
        }
      }).catch(() => callback(new Error('当前网络延迟较高，请稍后重试')))
    },
    // 校验栏目路径是否重复
    async validatePath(rule, value, callback) {
      await existPath(value).then(resp => {
        if (resp.success) {
          if (resp.data === null || resp.data === '') {
            callback()
          } else if (resp.data === this.updateNavigationQuery.nid) {
            callback()
          } else {
            callback(new Error('栏目路径不能重复'))
          }
        } else {
          callback(new Error((resp.data !== null && resp.data !== '') ? resp.data : resp.message))
        }
      }).catch(() => callback(new Error('当前网络延迟较高，请稍后重试')))
    },
    // 判断文件后缀是否合法
    validateImage(rule, value, callback) {
      let type = ''
      if (value.type !== undefined && value.type !== null) {
        type = value.type.substring(0,5);
      }
      if (type === 'image') {
        callback()
      } else {
        callback(new Error('文件后缀不符合要求'))
      }
    },
    // 点击修改按钮
    handleNavEdit(nid) {
      getNavNamesByLevel('1').then(resp => {
        if (resp.success) {
          this.firstLevelNames = resp.data
          getNavDetail(nid).then(resp => {
            if (resp.success) {
              this.updateNavigationQuery = resp.data
              this.updateNavigationQuery.level = this.updateNavigationQuery.level + ''
              this.updateNavigationQuery.navType = this.updateNavigationQuery.navType + ''
              this.updateNavigationQuery.image = null
              this.navLevelChange(this.updateNavigationQuery.level)
              this.uploadBtnState = this.uploadBtnInit()
              this.dialogChange = true;
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    handleCarouselEdit(chart) {
      this.updateChartQuery.hid = chart.hid
      this.updateChartQuery.link = chart.link
      this.uploadBtnState = this.uploadBtnInit()
      this.dialogCarouselChange = true
    },
    // 点击删除按钮
    handleNavDelete(nid) {
      ElMessageBox.confirm(
          '您确定要删除该栏目吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteNavigation(nid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetNavList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除栏目操作')
      })
    },
    handleCarouselDelete(hid) {
      ElMessageBox.confirm(
          '您确定要删除该轮播图吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteChart(hid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetChartList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除轮播图操作')
      })
    },
    // 查看轮播图列表
    doGetChartList() {
      getChartList().then(resp => {
        if (resp.success) {
          this.chartList = resp.data
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    handleNavClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.navDetail = this.resetNavDetail()
      this.saveNavigationQuery = this.resetSaveNavigationQuery()
      this.uploadBtnState = this.uploadBtnInit()
      this.updateNavigationQuery = this.resetUpdateNavigationQuery();
    },
    handleCarouselClose() {
      this.dialogCarouselChange = false
      this.dialogCarouselSave = false
      this.uploadBtnState = this.uploadBtnInit()
    },
    navLevelChange(val) {
      if (val === '2') {
        this.showParent = true
        this.showParentNav = false
      } else {
        this.showParent = false
        this.showParentNav = true
        this.saveNavigationQuery.parentId = ''
      }
    },
    resetNavDetail() {
      return {
        "nid": "",
        "navName": "",
        "level": 0,
        "levelName": "",
        "parentId": 0,
        "parentName": "",
        "image": "",
        "description": "",
        "allowDel": true,
        "allowShow": false,
        "used": false,
        "disabled": false,
        "showed": false,
        "navType": 0,
        "navTypeName": "",
        "path": "",
        "orderSeed": 0,
        "createBy": "",
        "createTime": "",
        "updateBy": "",
        "updateTime": ""
      }
    },
    resetSaveNavigationQuery() {
      return {
        "description": "",
        "disabled": false,
        "level": "",
        "navName": "",
        "navType": "",
        "orderSeed": 1,
        "parentId": "",
        "path": "",
        "image": null
      }
    },
    resetUpdateNavigationQuery() {
      return {
        "nid": "",
        "navName": "",
        "level": "",
        "parentId": 0,
        "parentName": "",
        "image": Object,
        "description": "",
        "allowShow": false,
        "used": false,
        "disabled": false,
        "showed": false,
        "navType": "",
        "path": "",
        "orderSeed": 0
      }
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
    resetSaveChartQuery() {
      return {
        image: null,
        link: ''
      }
    },
    resetUpdateChartQuery() {
      return {
        hid: '',
        image: null,
        link: ''
      }
    }
  },
  components: {
    StarFilled,
  },
  mounted() {
    this.doGetNavList()
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

.demo-tabs {
  padding-left: 30px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
  margin: 0;
}

.uploadBtn {
  width: 100%;
  height: 100%;
  text-align: left;
  padding-left: 30px;
}
</style>
