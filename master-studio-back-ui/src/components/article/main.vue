<template>
  <div>
    <!-- 文章标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">文章发布</h1>
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
        <el-form :inline="true" :model="getArticleQuery" class="demo-form-inline">
          <el-form-item label="文章标题">
            <el-input v-model="getArticleQuery.title" placeholder="请输入文章标题"/>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="getArticleQuery.author" placeholder="请输入作者昵称"/>
          </el-form-item>
          <el-form-item label="绑定栏目">
            <el-cascader :options="multiNavList" clearable placeholder="请选择绑定栏目" v-model="searchMultiBindNav"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="formQuery">查询</el-button>
            <el-button type="primary" @click="this.$router.push({path: '/article/save'})">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 主体表格 -->
    <el-row justify="center">
      <el-col :span="23">
        <el-table :data="articleList" border style="width: 100%" height="400px">
          <el-table-column prop="title" label="文章标题" width="250" align="center"/>
          <el-table-column label="文章摘要" width="180" align="center">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="500px">
                <template #default>
                  <div>{{ scope.row.summary }}
                  </div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.summary.slice(0, 10) }} ...</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="navName" label="绑定栏目" width="180" align="center"/>
          <el-table-column prop="author" label="作者" width="180" align="center"/>
          <el-table-column prop="released" label="发布" width="100" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.released ? '' : 'warning'">{{ scope.row.released ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="disabled" label="禁用" width="100" align="center">
            <template #default="scope">
              <el-tag :type="scope.row.disabled ? 'danger' : ''">{{ scope.row.disabled ? 'YES' : 'NO' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="handleDetail(scope.row.aid)"
              >详情
              </el-button>
              <el-button size="small" @click="handleEdit(scope.row.aid)"
              >修改
              </el-button>
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.row.aid)"
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
            v-model:currentPage="getArticleQuery.pageNo"
            v-model:page-size="getArticleQuery.pageSize"
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
    <!-- 文章详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="文章详细信息"
        size="50%"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-descriptions border column="1">
        <el-descriptions-item label="文章编号" width="50px" align="center">{{ articleDetail.aid }}</el-descriptions-item>
        <el-descriptions-item label="文章标题" width="50px" align="center">{{ articleDetail.title }}</el-descriptions-item>
        <el-descriptions-item label="文章正文" width="50px" align="center">
          <el-button @click="dialogVisible=true">查看详情</el-button>
        </el-descriptions-item>
        <el-descriptions-item label="文章摘要" width="50px" align="center">{{ articleDetail.summary }}
        </el-descriptions-item>
        <el-descriptions-item label="绑定栏目" width="50px" align="center">{{ articleDetail.navName }}
        </el-descriptions-item>
        <el-descriptions-item label="排序种子" width="50px" align="center">{{ articleDetail.orderSeed }}
        </el-descriptions-item>
        <el-descriptions-item label="是否发布" width="50px" align="center">
          <el-tag size="small" :type="articleDetail.released ? '' : 'warning'">
            {{ articleDetail.released ? 'YES' : 'NO' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="是否禁用" width="50px" align="center">
          <el-tag size="small" :type="articleDetail.disabled ? 'danger' : ''">
            {{ articleDetail.disabled ? 'YES' : 'NO' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="作者" width="50px" align="center">{{ articleDetail.author }}</el-descriptions-item>
        <el-descriptions-item label="创建者" width="50px" align="center">{{ articleDetail.createBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{ articleDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="修改者" width="50px" align="center">{{articleDetail.updateBy}}</el-descriptions-item>
        <el-descriptions-item label="修改时间" width="50px" align="center">{{articleDetail.updateTime}}</el-descriptions-item>
      </el-descriptions>
    </el-drawer>

    <el-dialog
        v-model="dialogVisible"
        title="文章正文"
        width="90%"
        :before-close="handleInnerClose"
    >
      <div class="preview-box">
        <div class="preview-title">{{articleDetail.title}}</div>
        <el-divider class="preview-divider"/>
        <p class="preview-description">来自&nbsp;-&nbsp;{{articleDetail.author}}&nbsp;&nbsp;&nbsp;&nbsp;创建时间&nbsp;-&nbsp;{{articleDetail.createTime.substring(0,10)}}</p>
        <div class="preview-content" v-html="articleDetail.htmlContent"></div>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>
  </div>

</template>

<script>
import {StarFilled} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import {multiNavigation} from "@/api/navigation";
import {deleteArticle, getArticleDetail, getArticleList} from "@/api/article";

export default {
  name: "ArticleMain",
  data() {
    return {
      // 查询表单
      getArticleQuery: {
        "author": '',
        "navId": '',
        "pageNo": 1,
        "pageSize": 10,
        "title": ""
      },
      // 文章列表
      articleList: [],
      // 总记录数
      totalRecords: 0,
      // 页面多级栏目列表
      multiNavList: [],
      // 查询表单绑定栏目
      searchMultiBindNav: [],
      // 文章详情
      articleDetail: this.resetArticleDetail(),
      dialogChange: false,
      dialogDetail: false,
      dialogSave: false,
      dialogVisible: false,
      small: false,
      background: true,
      disabled: false,
    }
  },
  methods: {
    // 获取文章列表
    doGetArticleList() {
      if (this.searchMultiBindNav != null &&
          this.searchMultiBindNav.length !== 0) {
        this.getArticleQuery.navId = this.searchMultiBindNav[this.searchMultiBindNav.length - 1]
      } else {
        this.getArticleQuery.navId = ""
      }
      getArticleList(this.getArticleQuery).then(resp => {
        if (resp.success) {
          this.articleList = resp.data.records
          this.totalRecords = resp.data.totalRecords
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 获取多级栏目列表
    getMultiNav() {
      multiNavigation(2).then(resp => {
        if (resp.success) {
          if (resp.data === null) return
          let navs = resp.data
          this.multiNavList = this.buildMultiNav(navs)
        }
      })
    },
    // 点击查询按钮
    formQuery() {
      this.doGetArticleList()
    },
    // 页面显示的条数发生变化
    handleSizeChange() {
      this.doGetArticleList()
    },
    // 当前页码发生变化
    handleCurrentChange() {
      this.doGetArticleList()
    },
    // 查询文章详情
    handleDetail(aid) {
      getArticleDetail(aid).then(resp => {
        if (resp.success) {
          this.articleDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 点击修改按钮，进行页面跳转
    handleEdit(aid) {
      this.$router.push({path: `/article/update/${aid}`})
    },
    // 点击删除按钮
    handleDelete(aid) {
      ElMessageBox.confirm(
          '您确定要删除该文章吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteArticle(aid).then(resp => {
          if (resp.success) {
            ElMessage.success('删除成功')
            this.doGetArticleList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除文章操作')
      })
    },
    handleClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.articleDetail = this.resetArticleDetail()
    },
    handleInnerClose() {
      this.dialogVisible = false
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
    resetArticleDetail() {
      return {
        "aid": 0,
        "createBy": "string",
        "createTime": "2022-11-01T13:05:24.891Z",
        "disabled": true,
        "htmlContent": "string",
        "navId": 0,
        "navName": "string",
        "orderSeed": 0,
        "released": true,
        "summary": "string",
        "title": "string",
        "updateBy": "string",
        "updateTime": "2022-11-01T13:05:24.891Z"
      }
    }
  },
  components: {
    StarFilled,
    // Editor,
    // Toolbar
  },
  beforeUnmount() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁 editor ，重要！！！
  },
  mounted() {
    this.getMultiNav()
    this.doGetArticleList()
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

.preview-box {
  text-align: left;
  color: black;
  font-family: 微软雅黑,serif;
}

.preview-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  margin: 0 auto;
  /*margin-bottom: 40px;*/
  z-index: 10;
}

.preview-title {
  font-size: 35px;
  font-weight: 400;
  letter-spacing: 3px;
  height: 60px;
  line-height: 60px;
  margin-bottom: 20px;
  text-align: center;
}

.preview-content {
  padding: 0 10px;
}

.preview-description {
  color: #7a7878;
  font-size: 14px;
  letter-spacing: 2px;
  margin: 20px 0;
  width: 100%;
  text-align: center;
}
</style>
