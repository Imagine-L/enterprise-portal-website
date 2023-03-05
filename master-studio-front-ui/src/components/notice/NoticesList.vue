<template>
  <div class="notices-list">
    <div class="notice-title">
      <span>{{ title }}</span>
      <el-input
          v-model="getNoticeQuery.title"
          placeholder="请输入查询的通知标题"
          @blur="searchNotice"
          @clear="searchNotice"
          clearable
          :suffix-icon="search"
      />
    </div>
    <el-divider class="notice-divider"/>
    <ul>
      <li v-for="notice in noticeList" :key="notice.aid" @click="showDetail(notice.aid)">
        <div class="notice-li-date">{{ notice.createTime }}</div>
        <div class="notice-li-title">
          <el-icon class="notice-li-icon">
            <Paperclip/>
          </el-icon>
          {{ notice.title }}
          <span class="notice-li-desc">{{ handleTextTitle(notice.summary, 20) }}</span>
        </div>
      </li>
    </ul>
    <div class="notice-pagination">
      <el-pagination
          v-model:currentPage="getNoticeQuery.pageNo"
          v-model:page-size="getNoticeQuery.pageSize"
          :page-sizes="[20, 30, 40, 50]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalRecords"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

  </div>
</template>

<script>
import {Paperclip, Search} from '@element-plus/icons-vue'
import {getNoticeListByDate} from "@/api/article";
import {ElMessage} from "element-plus";

export default {
  name: "NoticesList",
  props: {
    date: String
  },
  data() {
    return {
      // 查询通知列表请求参数
      getNoticeQuery: {
        date: '',
        title: '',
        pageNo: 1,
        pageSize: 20
      },
      // 总记录数
      totalRecords: 0,
      // 通知列表
      noticeList: [],
      small: false,
      disabled: false,
      background: false,
      search: Search
    }
  },
  components: {
    Paperclip,
    Search
  },
  computed: {
    title() {
      if (this.date === null || this.date === '') {
        return '全部通知'
      }
      return `${this.date} 年 - 全部通知`
    }
  },
  methods: {
    // 根据日期查询通知列表
    doGetNoticeListByDate() {
      this.getNoticeQuery.date = this.date
      getNoticeListByDate(this.getNoticeQuery).then(resp => {
        if (resp.success) {
          // console.log(resp.data)
          this.noticeList = resp.data.records
          this.totalRecords = resp.data.totalRecords
        } else {
          ElMessage.warning('当前网络延迟较高，请稍后重试')
          console.warn((resp.data !== null && resp.data !== '') ? resp.data : resp.message)
        }
      }).catch(() => ElMessage.warning('当前网络延迟较高，请稍后重试'))
    },
    // 修改页面显示条目数
    handleSizeChange() {
      this.doGetNoticeListByDate()
    },
    // 修改当前页码
    handleCurrentChange() {
      this.doGetNoticeListByDate()
    },
    // 搜索文章
    searchNotice() {
      this.doGetNoticeListByDate()
    },
    // 查看文章详情
    showDetail(aid) {
      let parentPath = this.date === null || this.date === '' ? 'all' : this.date
      this.$router.push({path: `${parentPath}/${aid}`})
    },
    // 处理文章文本，长度利于界面显示
    handleTextTitle(text, size) {
      if (text.length <= size) {
        return text
      }
      text = text.substring(0, size) + '...'
      return text
    }
  },
  mounted() {
    this.doGetNoticeListByDate()
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  list-style: none;
  box-sizing: border-box;
}

.notices-list {
  width: 60%;
  height: 100%;
  float: right;
  margin-right: 10%;
  margin-bottom: 100px;
  /*background-color: red;*/
  z-index: 0;
}

.notice-title {
  font-size: 35px;
  font-weight: normal;
  letter-spacing: 3px;
  height: 60px;
  line-height: 60px;
  /*margin-top: ;*/
  margin-top: 70px;
  margin-bottom: 30px;
  /*margin: 30px 0;*/
}

.notice-title .el-input {
  display: inline;
  margin-left: 2%;
}

.notice-divider {
  width: 80px;
  background-color: #791cb5;
  border: 2px solid #791cb5;
  margin-bottom: 20px;
  z-index: 10;
}

.notices-list > ul {
  /*border-top: 1px dotted #612cb0;*/
  /*border-top: 1px dotted #7e7d80;*/
}

.notices-list > ul > li {
  height: 50px;
  line-height: 50px;
  letter-spacing: 1px;
  padding-left: 5px;
  border-bottom: 1px dotted #7e7d80;
}

.notices-list > ul > li:hover {
  transition: 0.3s;
  color: #4c159d;
  cursor: pointer;
}

.notice-li-icon {
  vertical-align: middle;
  margin-right: 10px;
}

.notice-li-desc {
  font-size: 12px;
  margin-left: 5%;
  color: #7a7878;
}

.notice-li-date {
  float: right;
}

.notice-pagination {
  margin: 20px 0;
}
</style>
