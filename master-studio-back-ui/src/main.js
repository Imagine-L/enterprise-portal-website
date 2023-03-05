import {createApp} from 'vue'
import 'element-plus/dist/index.css'
import '@/static/css/index.css'
// import 'default-passive-events';
// import config from "@/config/config";
import ElementPlus from 'element-plus'
import locale from 'element-plus/lib/locale/lang/zh-cn';
import App from './App.vue'
import router from "@/route";
import store from "@/store";

createApp(App)
    .use(ElementPlus, {locale})
    .use(router)
    .use(store)
    .mount('#app')
