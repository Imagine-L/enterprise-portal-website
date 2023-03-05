import {createRouter, createWebHashHistory} from 'vue-router'
import Home from "@/pages/Home";
import store from "@/store";
import {navActions, navMutations} from "@/constant";
import {ElMessage} from "element-plus";
import {getNavList} from "@/api/navigation";
import {getNoticeDateList} from "@/api/article";
import NotFound from "@/pages/error/NotFound";

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        meta: {
            title: '福州职业技术学院-何志清大师工作室'
        }
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

let flag = false

router.beforeEach(async (to, form) => {
    let currentPath = sessionStorage.getItem('CURRENT_PATH')
    store.commit(navMutations.SET_CURRENT_PATH, to.path)
    /* 路由发生变化修改页面title */
    if (to.meta.title) {
        document.title = to.meta.title
    }
    if ((currentPath === null || to.path === currentPath) && !flag) {
        let navResp = await getNavList()
        let noticeResp = await getNoticeDateList()
        if (navResp.success) {
            let navList = navResp.data
            let noticeDateList = noticeResp.data
            // 保存栏目，同时动态添加路由
            await store.dispatch(navActions.SAVE_NAV_LIST, {navList, noticeDateList})
            flag = true
            router.addRoute({
                path: '/:pathMatch(.*)',
                name: 'notFound',
                component: NotFound,
                meta: {
                    title: '404[页面未找到]-何志清大师工作室'
                }
            })
            return {...to}
        } else {
            ElMessage.warning((navResp.data !== null && navResp.data !== '') ? navResp.data : navResp.message)
            return false
        }
    }
    return true
})

router.afterEach((to, form) => {
    sessionStorage.setItem('CURRENT_PATH', to.path)
})

export default router
