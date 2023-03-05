const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  assetsDir: 'static',
  parallel: false,
  publicPath: './',
  transpileDependencies: true,
  devServer: {
    port: 80
  },
  chainWebpack: config => {
    //设置标题  默认不设置的话是项目名字
    config.plugin('html').tap(args => {
      args[0].title = "福州职业技术学院-何志清大师工作室"
      return args
    })
  }
})
