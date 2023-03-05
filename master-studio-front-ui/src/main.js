import { createApp } from 'vue'
import '@/static/css/index.css'
import 'element-plus/dist/index.css'
import locale from 'element-plus/lib/locale/lang/zh-cn';
import ElementPlus from 'element-plus'
import App from './App.vue'
import router from "@/route";
import store from "@/store";

createApp(App)
    .use(ElementPlus, {locale})
    .use(router)
    .use(store)
    .mount('#app')
