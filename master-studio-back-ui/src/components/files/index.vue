<template>
  <div>
    <!-- 页面标题 -->
    <el-row>
      <el-col :span="24">
        <h1 class="title">文件管理</h1>
      </el-col>
    </el-row>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <!-- 查询表单 -->
    <el-row>
      <el-col>
        <el-form :inline="true" :model="getFileQuery" class="demo-form-inline">
          <el-form-item label="系统文件名">
            <el-input v-model="getFileQuery.filename" placeholder="请输入系统文件名"/>
          </el-form-item>
          <el-form-item label="原始文件名">
            <el-input v-model="getFileQuery.originFilename" placeholder="请输入原始文件名"/>
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
            <el-button type="primary" @click="dialogSave=true">上传</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 主体表格 -->
    <el-row justify="center">
      <el-col :span="23">
        <el-table :data="fileList" border height="400px">
          <el-table-column fixed label="预览" width="60" align="center">
            <template #default="scope">
              <el-image style="width: 40px; height: 40px" :src="scope.row.networkPath" fit="fill" lazy>
                <template #error>
                  <div class="image-slot">
                    无法预览
                  </div>
                </template>
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="filename" label="系统文件名" width="180" align="center"/>
          <el-table-column prop="originFilename" label="原始文件名" width="180" align="center"/>
          <el-table-column prop="networkPath" label="网络路径" width="250" align="center">
            <template #default="scope">
              <el-link :href="scope.row.networkPath" target="target" :underline="false">{{scope.row.networkPath}}</el-link>
            </template>
          </el-table-column>
          <el-table-column prop="localPath" label="本地路径" width="250" align="center"/>
          <!--          <el-table-column prop="size" label="文件大小" width="180" align="center"/>-->
          <el-table-column label="文件大小" width="100" align="center">
            <template #default="scope">
              <el-tag>{{ scope.row.size }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="handleDetail(scope.row.fid)"
              >详情
              </el-button>
              <el-button size="small" @click="handleDownload($event, scope.row)">下载</el-button>
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.row.fid)"
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
            v-model:currentPage="getFileQuery.pageNo"
            v-model:page-size="getFileQuery.pageSize"
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
    <!-- 文件详情 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogDetail"
        title="文件详细信息"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
        size="50%"
    >
      <el-descriptions column="1" border direction="vertical">
        <el-descriptions-item label="文件编号" width="50px" align="center">{{ fileDetail.fid }}</el-descriptions-item>
        <el-descriptions-item label="文件内容" align="center">
          <el-image style="width: 95%; " :src="fileDetail.networkPath" fit="fill">
            <template #error>
              <div class="img-error">该文件无法预览</div>
            </template>
          </el-image>
        </el-descriptions-item>
        <el-descriptions-item label="系统文件名" width="50px" align="center">{{ fileDetail.filename }}</el-descriptions-item>
        <el-descriptions-item label="原始文件名" width="50px" align="center">{{ fileDetail.originFilename }}</el-descriptions-item>
        <el-descriptions-item label="网络路径" width="50px" align="center">{{ fileDetail.networkPath }}
        </el-descriptions-item>
        <el-descriptions-item label="本地路径" width="50px" align="center">{{ fileDetail.localPath }}</el-descriptions-item>
        <el-descriptions-item label="文件大小" width="50px" align="center">
          <el-tag>{{ fileDetail.size }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建者" width="50px" align="center">{{ fileDetail.createBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" width="50px" align="center">{{ fileDetail.createTime }}
        </el-descriptions-item>
      </el-descriptions>
    </el-drawer>
    <!-- 上传文件 -->
    <el-drawer
        ref="drawerRef"
        v-model="dialogSave"
        title="上传文件"
        :before-close="handleClose"
        direction="rtl"
        custom-class="demo-drawer"
    >
      <el-upload
          class="upload-demo"
          drag
          multiple
          :headers="uploadHeaders"
          :action="uploadUrl"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :before-upload="beforeUpload"
          method="post"
      >
        <el-icon class="el-icon--upload">
          <upload-filled/>
        </el-icon>
        <div class="el-upload__text">
          移动文件到此处或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            可上传小于500MB的文件
          </div>
        </template>
      </el-upload>
    </el-drawer>
  </div>

</template>

<script>
import $ from "jquery";
import {StarFilled} from '@element-plus/icons-vue'
import {UploadFilled} from '@element-plus/icons-vue'
import {deleteFile, downloadFile, getFileDetail, getFileList} from "@/api/file";
import {ElMessage, ElMessageBox} from "element-plus";
import config from "@/config/config";

export default {
  name: "FilesComponent",
  data() {
    return {
      // 查询表单
      getFileQuery: {
        "createStartTime": "",
        "createStopTime": "",
        "originFilename": "",
        "filename": "",
        "pageNo": 1,
        "pageSize": 10
      },
      // 总页数
      totalRecords: 100,
      // 文件列表
      fileList: [],
      // 文件上传地址
      uploadUrl: config.BASE_URL + '/file/upload',
      // 文件上传请求头
      uploadHeaders: {'Authorization': this.$store.getters.token},
      // 查询表单时间
      dateValue: [],
      fileDetail: this.resetFileDetail,
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      num: 20,
      dialogChange: false,
      dialogDetail: false,
      dialogSave: false,
      currentPage4: 4,
      pageSize4: 10,
      small: false,
      background: true,
      disabled: false
    }
  },
  methods: {
    // 获取文件列表
    doGetFileList() {
      if (this.dateValue !== null && this.dateValue.length !== 0) {
        this.getFileQuery.createStartTime = this.dateValue[0]
        this.getFileQuery.createStopTime = this.dateValue[1]
      } else {
        this.getFileQuery.createStartTime = null
        this.getFileQuery.createStopTime = null
      }
      getFileList(this.getFileQuery).then(resp => {
        if (resp.success) {
          this.totalRecords = resp.data.totalRecords
          this.fileList = resp.data.records
          this.getFileQuery.createStartTime = ''
          this.getFileQuery.createStopTime = ''
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
    },
    // 修改当前页显示条目
    handleSizeChange() {
      this.doGetFileList()
    },
    // 修改当前页码
    handleCurrentChange() {
      this.doGetFileList()
    },
    // 点击查询按钮
    formQuery() {
      this.doGetFileList();
    },
    // 文件上传成功调用方法
    uploadSuccess() {
      this.doGetFileList()
      ElMessage.success('文件上传成功')
      this.dialogSave = false
    },
    // 文件上传失败
    uploadError(resp) {
      ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
    },
    // 文件上传之前的检查
    beforeUpload(rawFile) {
      /// 如果需要文件类型判断，形式如：rawFile.type !== 'image/jpeg'
      // 判断文件大小
      if (rawFile.size > 536870912) {
        ElMessage.error('文件大小不能超过500MB')
        return false
      }
      return true
    },
    // 获取文件详细信息
    handleDetail(fid) {
      getFileDetail(fid).then(resp => {
        if (resp.success) {
          this.fileDetail = resp.data
          this.dialogDetail = true
        } else {
          ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))

    },
    // 删除文件
    handleDelete(fid) {
      ElMessageBox.confirm(
          '您确定要删除该文件吗？删除后将无法恢复',
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
        deleteFile(fid).then(resp => {
          if (resp.success) {
            ElMessage.success("文件删除成功")
            this.doGetFileList()
          } else {
            ElMessage.warning((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
          }
        }).catch(() => ElMessage.error('当前网络延迟较高，请稍后重试'))
      }).catch(() => {
        ElMessage.info('取消删除文件操作')
      })
    },
    // 文件下载
    handleDownload(e, file) {
      let $downloadBtn = $(e.currentTarget)
      $downloadBtn.attr("disabled", true)
      $downloadBtn.addClass('disable-btn')
      if (file.fid === null || file.fid === '') {
        ElMessage.error('文件信息不全，请刷新重试')
        return
      }
      // 根据文件大小做出提示
      if (file.size !== null && file.size !== '') {
        let sizeNum = file.size.substring(0, file.size.lastIndexOf(' '))
        let sizeUnit = file.size.substring(file.size.lastIndexOf(' ') + 1, file.size.length)
        if (sizeUnit === 'MB' && sizeNum >= 100) {
          ElMessage.warning('文件可能较大，可能需要一点时间')
        }
      }
      // 发起下载请求
      downloadFile(file.fid).then(resp => {
        let url = window.URL.createObjectURL(resp); // 创建 url 并指向 blob
        let a = document.createElement('a');
        a.href = url;
        a.setAttribute('download', file.filename)
        a.click();
        window.URL.revokeObjectURL(url); // 释放该 url
        $downloadBtn.attr('disabled', false)
        $downloadBtn.removeClass('disable-btn')
      }).catch(() => {
        ElMessage.error('当前网络延迟较高，请稍后重试')
        $downloadBtn.attr('disabled', false)
        $downloadBtn.removeClass('disable-btn')
      })
    },
    // 关闭表单
    handleClose() {
      this.dialogDetail = false
      this.dialogChange = false
      this.dialogSave = false
      this.fileDetail = this.resetFileDetail()
    },
    resetFileDetail() {
      return {
        "createBy": "",
        "createTime": "",
        "fid": 0,
        "filename": "",
        "localPath": "",
        "networkPath": "",
        "size": 0,
        "updateBy": "",
        "updateTime": "",
        "originFilename": ""
      }
    }
  },
  components: {
    StarFilled,
    UploadFilled
  },
  mounted() {
    this.doGetFileList()
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

.img-error {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 30px;
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}

.disable-btn {
  color: #bbbbbb;
  background-color: #ffffff;
}
</style>
