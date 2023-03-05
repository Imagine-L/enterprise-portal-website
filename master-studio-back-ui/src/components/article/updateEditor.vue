<template>
  <div class="page-header">
    <el-page-header @back="goBack" title="返回">
      <template #content>
        <span class="text-large font mr-3"> 修改文章 </span>
      </template>
      <template #extra>
        <div class="flex items-center">
          <el-button @click="dialogVisible=true">预览</el-button>
          <el-button type="primary" class="ml-2" @click="handleUpdate">保存</el-button>
        </div>
      </template>
    </el-page-header>
  </div>
  <div class="editor-box">
    <div style="border: 1px solid #ccc; margin-top: 10px;">
      <!-- 工具栏 -->
      <Toolbar
          style="border-bottom: 1px solid #ccc"
          :editor="editor"
          :defaultConfig="toolbarConfig"
      />
      <!-- 编辑器，还可以设置@onChange="onChange"钩子函数 -->
      <!-- style="height: 100%;  overflow-y: hidden;" -->
      <Editor
          class="editor"
          :defaultConfig="editorConfig"
          v-model="updateArticleQuery.htmlContent"
          @onCreated="onCreated"
          :align="'left'"
      />
    </div>
  </div>
  <!-- 新增文章 -->
  <el-drawer
      ref="drawerRef"
      v-model="dialogSave"
      title="新增文章信息"
      direction="rtl"
      custom-class="demo-drawer"
  >
    <el-form :model="updateArticleQuery" ref="articleRef" label-width="80px" label-position="left">
      <el-form-item label="文章标题" prop="title" :rules="checkRules.title">
        <el-input v-model="updateArticleQuery.title" placeholder="请输入文章标题"/>
      </el-form-item>
      <el-form-item label="绑定栏目" prop="bindNavArr" :rules="checkRules.bindNavArr">
        <el-cascader :options="multiNavList" clearable placeholder="请选择绑定栏目" v-model="updateArticleQuery.bindNavArr"/>
      </el-form-item>
      <el-form-item label="文章摘要" prop="summary" :rules="checkRules.summary">
        <el-input v-model="updateArticleQuery.summary" type="textarea" placeholder="请输入文章摘要"/>
      </el-form-item>
      <el-form-item label="禁用">
        <el-switch v-model="updateArticleQuery.disabled"/>
      </el-form-item>
      <el-form-item label="发布">
        <el-switch v-model="updateArticleQuery.released"/>
      </el-form-item>
      <el-form-item label="排序种子">
        <el-input-number v-model="updateArticleQuery.orderSeed"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doUpdate">保存</el-button>
        <el-button @click="dialogSave=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
  <!-- 文章预览 -->
  <el-dialog
      v-model="dialogVisible"
      title="文章预览"
      width="90%"
  >
    <div class="preview-box">
      <div class="preview-title">
        {{ updateArticleQuery.title !== null && updateArticleQuery.title !== '' ? updateArticleQuery.title : '示例标题' }}
      </div>
      <el-divider class="preview-divider"/>
      <p class="preview-description" v-html="previewDescription"></p>
      <div class="preview-content" v-html="updateArticleQuery.htmlContent"></div>
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
</template>

<script>
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {multiNavigation} from "@/api/navigation";
import {ElMessage, ElMessageBox} from "element-plus";
import {getArticleDetail, updateArticle} from "@/api/article";
import store from "@/store";
import config from "@/config/config";

export default {
  name: "updateEditor",
  setup() {
    const token = store.getters.token
    const baseUrl = config.BASE_URL
    // 为了方便设置token，所以将uploadConfig设置在setup中
    // 这是因为在data域中的editorConfig会修改this为它自己，无法获取到vue解析的token
    let uploadConfig = {
      // form-data fieldName ，默认值 'wangeditor-uploaded-image'
      fieldName: 'file',
      // 单个文件的最大体积限制，默认为 2M
      maxFileSize: 1000 * 1024 * 1024, // 1M
      // 最多可上传几个文件，默认为 100
      maxNumberOfFiles: 10,
      // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
      // allowedFileTypes: ['image/*'],
      // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
      // meta: {
      //   token: 'xxx',
      //   otherKey: 'yyy'
      // },
      // 将 meta 拼接到 url 参数中，默认 false
      metaWithUrl: false,
      // 自定义增加 http  header
      headers: {
        // 'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': token
      },
      // 跨域是否传递 cookie ，默认为 false
      withCredentials: false,
      // 超时时间，默认为 10 秒
      timeout: 5 * 1000, // 5 秒
      server: `${baseUrl}/article/file`,
    }
    const editorConfig = {
      placeholder: '请输入内容...',
      // 所有的菜单配置，都要在 MENU_CONF 属性下
      MENU_CONF: {
        uploadImage: uploadConfig,
        uploadVideo: uploadConfig
      }
    }
    return {
      token,
      baseUrl,
      editorConfig
    }
  },
  data() {
    return {
      // 页面多级栏目列表
      multiNavList: [],
      // 内容正则匹配
      contentPattern: /^\s*$/,
      // 保存文章参数
      updateArticleQuery: {
        "disabled": false,
        "htmlContent": "",
        "navId": 0,
        "orderSeed": 0,
        "released": true,
        "summary": "",
        "bindNavArr": [],
        "title": "",
        "aid": ""
      },
      articleRef: {
        title: '',
        bindNavArr: '',
        summary: '',
      },
      // 表单校验规则
      checkRules: {
        title: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'blur',
          },
          {min: 0, max: 20, message: '长度需要在0-20之间', trigger: 'blur'}
        ],
        bindNavArr: [
          {
            required: true,
            message: '请输入栏目名',
            trigger: 'change',
          },
        ],
        summary: {min: 0, max: 300, message: '长度需要在0-300之间', trigger: 'blur'},
      },
      // wangEditor对象
      editor: null,
      toolbarConfig: {},
      dialogSave: false,
      dialogVisible: false,
    }
  },
  computed: {
    previewDescription() {
      let date = new Date();
      let nickname = this.$store.getters.nickname
      return `来自&nbsp;-&nbsp;${nickname}&nbsp;&nbsp;&nbsp;&nbsp;创建时间&nbsp;-&nbsp;${date.getFullYear()}-${date.getMonth() + 1}-${date.getDay()}`
    },
    windowHeight() {
      return (window.innerHeight * 0.7) + 'px'
    },
  },
  methods: {
    // 获取文章详情
    doGetArticleDetail(aid) {
      getArticleDetail(aid).then(resp => {
        if (resp.success) {
          this.updateArticleQuery.disabled = resp.data.disabled
          this.updateArticleQuery.htmlContent = resp.data.htmlContent
          this.updateArticleQuery.navId = resp.data.navId
          this.updateArticleQuery.bindNavArr = resp.data.navId
          this.updateArticleQuery.orderSeed = resp.data.orderSeed
          this.updateArticleQuery.released = resp.data.released
          this.updateArticleQuery.summary = resp.data.summary
          this.updateArticleQuery.title = resp.data.title
          this.updateArticleQuery.aid = resp.data.aid
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
    // 点击保存按钮
    handleUpdate() {
      // 文本校验
      let text = this.editor.getText()
      console.log(text)
      if (this.contentPattern.test(text)) {
        ElMessage.warning('文章没有内容，无法保存')
        return
      }
      // 获取多级列表
      this.getMultiNav()
      this.dialogSave = true
    },
    // 保存文章
    doUpdate() {
      this.$refs['articleRef'].validate((valid) => {
        if (valid) {
          if (this.updateArticleQuery.bindNavArr instanceof Array) {
            this.updateArticleQuery.navId = this.updateArticleQuery.bindNavArr[this.updateArticleQuery.bindNavArr.length - 1]
          }
          updateArticle(this.updateArticleQuery).then(resp => {
            if (resp.success) {
              ElMessage.success('修改成功')
              window.removeEventListener('beforeunload', this.beforeunloadHandler)
              this.$router.go(-1)
            } else {
              ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
            }
          }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
        } else {
          ElMessage.error('请按要求输入信息后再点击保存按钮！')
        }
      })
    },
    goBack() {
      ElMessageBox.confirm(
          '您确定要返回上一级吗？您当前的文章还未保存！',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        window.removeEventListener('beforeunload', this.beforeunloadHandler)
        this.$router.go(-1)
      })

    },
    // wangEditor方法
    onCreated(editor) {
      this.editor = Object.seal(editor) // 【注意】一定要用 Object.seal() 否则会报错
    },
    beforeunloadHandler(e) {    //根据事件进行操作进行操作
      console.log(e)
      console.log('浏览器刷新')
      e.returnValue = false
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
  },
  components: {
    Editor,
    Toolbar
  },
  mounted() {
    let aid = this.$route.params.aid
    this.doGetArticleDetail(aid)
    window.addEventListener('beforeunload', this.beforeunloadHandler)
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.beforeunloadHandler)
  }
}
</script>

<style scoped>
@import "@wangeditor/editor/dist/css/style.css";

.page-header {
  padding: 0 20px;
}

.font {
  font-weight: bold;
}

.editor-box {
  padding: 0 20px;
  margin-top: 20px;
}

.editor {
  height: v-bind(windowHeight) !important;
  overflow-y: hidden;
}

.el-dialog > p {
  text-align: left;
}

.preview-box {
  text-align: left;
  color: black;
  font-family: 微软雅黑, serif;
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
