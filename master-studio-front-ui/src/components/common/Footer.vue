<template>
  <div class="footer-box">
    <div class="footer-container">
      <div class="footer-service clearfix">
        <div class="footer-service-item footer-icon-001">
          <i></i>
          <span>专注学习网络知识</span>
        </div>
        <div class="footer-service-item footer-icon-002">
          <i></i>
          <span>HCIE资深学长老师</span>
        </div>
        <div class="footer-service-item footer-icon-003">
          <i></i>
          <span>学长定期培训指导</span>
        </div>
        <div class="footer-service-item footer-icon-004">
          <i></i>
          <span>专业老师诚意推荐</span>
        </div>
      </div>
      <div class="footer-line"></div>

      <div class="footer-friend">
        <div class="footer-friend-title">友情链接：</div>
        <ul class="footer-friend-title-list clearfix">
          <li>
            <a href="http://www.fvti.cn/" target="_blank">学校官网</a>
          </li>
          <li>
            <a href="http://www.fvti.cn/albb/" target="_blank">阿里巴巴大数据学院</a>
          </li>
          <li>
            <a href="https://www.goldtech.com.cn/index.php" target="_blank">金科教育</a>
          </li>
          <li>
            <a href="https://www.eeafj.cn/" target="_blank">福建省教育考试院</a>
          </li>
          <li>
            <a href="http://jyt.fujian.gov.cn/" target="_blank">福建省教育厅</a>
          </li>
          <li>
            <a href="https://webvpn.fvti.cn/users/sign_in" target="_blank">校园VPN</a>
          </li>
          <li>
            <a href="http://10.1.1.100/studentportal.php" target="_blank">学校教务系统</a>
          </li>
          <li>
            <a href="http://10.1.2.61/stums/" target="_blank">学校学生信息管理系统</a>
          </li>
        </ul>
      </div>
      <div class="footer-copyright">
        <p>© 2022 大师工作室 版权所有，并保留所有权利&nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp;<a href="https://www.yuque.com/u12581613">网站制作者: 刘佳俊</a></p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FooterComponent"
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

a {
  text-decoration: none;
  color: #000;
}

img {
  border: 0;
}
a {
  text-decoration: none;
  color: #08acee;
}

button {
  outline: 0;
}

img {
  border: 0;
}

button,input,optgroup,select,textarea {
  margin: 0;
  font: inherit;
  color: inherit;
  outline: none;
}

li {
  list-style: none;
}

a {
  color: #666;
}

a:hover {
  color: #eee;
}

.clearfix::after {
  clear: both;
  content: ".";
  display: block;
  height: 0;
  visibility: hidden;
}

.clearfix {
}

/* 必要布局样式css */
.footer-box {
  /*#222132*/
  background: #222132;
  overflow: hidden;
  width: 100%;
  color: #888;
  padding: 30px 0 35px;
}

.footer-container {
  width: 1200px;
  margin: 0 auto;
  padding: 0;
}

.footer-service {
  margin-right: -26px;
  margin-bottom: 38px;
  padding-left: 69px;
}

.footer-service-item {
  float: left;
  height: 60px;
  width: 240px;
  margin-right: 26px;
}

.footer-service-item i {
  width: 50px;
  height: 45px;
  float: left;
  margin: 17px 17px 0 29px;
  opacity: .8;
  -webkit-transform: scale(.6);
  -moz-transform: scale(.6);
  transform: scale(.6);
}

.footer-service-item span {
  font-size: 16px;
  color: #ccc;
  float: left;
  margin-top: 29px;
}

.footer-icon-001 i {
  background: url(/src/assets/icon-footer01.png) no-repeat center center;
  background-size: contain;
}

.footer-icon-002 i {
  background: url(/src/assets/icon-footer02.png) no-repeat center center;
  background-size: contain;
}

.footer-icon-003 i {
  background: url(/src/assets/icon-footer03.png) no-repeat center center;
  background-size: contain;
}

.footer-icon-004 i {
  background: url(/src/assets/icon-footer04.png) no-repeat center center;
  background-size: contain;
}

.footer-line {
  height: 0;
  display: block;
  clear: both;
  border-bottom: 1px solid #393854;
}

.footer-article-item dt {
  color: #ccc;
  font-size: 16px;
  margin-bottom: 10px;
  line-height: 2;
  font-weight: 300;
}

.footer-article-item dd {
  line-height: 2;
}

.footer-article-item dd a {
  color: #888;
  transition: all .1s;
}

.footer-article-item dd a:hover {
  color: #fff;
}


.footer-contact-item h3 {
  font-size: 16px;
  color: #ccc;
  line-height: 2;
  margin-bottom: 10px;
  font-weight: 400;
}

.footer-contact-item p {
  font-size: 22px;
  color: #fff;
  font-family: Arial,"Microsoft Yahei","HanHei SC",PingHei,"PingFang SC","Helvetica Neue",Helvetica,Arial,"Hiragino Sans GB","Heiti SC","WenQuanYi Micro Hei",sans-serif;
  margin-bottom: 15px;
}

.footer-ewm img {
  width: 110px;
  height: 110px;
  display: block;
  border: none;
}

.footer-ewm p {
  font-size: 14px;
  color: #ccc;
  margin-top: 12px;
}

.footer-friend {
  width: 1050px;
  height: 20px;
  line-height: 20px;
  margin: 30px auto;
}

.footer-friend-title {
  float: left;
  color: #ccc;
  font-size: 14px;
}

.footer-friend-title-list {
  float: left;
}

.footer-friend-title-list li {
  float: left;
}

.footer-friend-title-list li a {
  color: #888;
  transition: all .1s;
  display: inline-block;
  padding: 0 10px;
  font-size: 14px;
}

.footer-friend-title-list li a:hover {
  color: #fff;
}

.footer-copyright {
  line-height: 20px;
  font-size: 14px;
  text-align: center;
}

.footer-copyright p {
  margin-bottom: 9px;
  font-size: 14px;
  color: #888;
}
</style>
