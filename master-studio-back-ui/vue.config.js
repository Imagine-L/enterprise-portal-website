const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  assetsDir: 'static',
  parallel: false,
  publicPath: './',
  transpileDependencies: true,
  devServer:{
    // proxy:"http://localhost:5050", // 后台服务器地址
    port: 5000
  },
  chainWebpack: config => {
    //设置标题  默认不设置的话是项目名字
    config.plugin('html').tap(args => {
      args[0].title = "大师工作室官网-后台管理系统"
      return args
    })
  }
})
