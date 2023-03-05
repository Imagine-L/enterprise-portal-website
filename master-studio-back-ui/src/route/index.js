import {createRouter, createWebHashHistory} from 'vue-router'
import store from "@/store";
import Login from "@/pages/Login";
import Home from "@/pages/Home";
import User from "@/components/user";
import Profession from "@/components/profession";
import Personal from "@/components/personal";
import Files from "@/components/files";
import Navigation from "@/components/navigation";
import Page from "@/components/page";
import Article from "@/components/article";
import SaveEditor from "@/components/article/saveEditor";
import ArticleMain from "@/components/article/main";
import Log from "@/components/log";
import LoginForm from "@/components/login/loginForm";
import FindPwd from "@/components/login/find/findPwd";
import Step1 from "@/components/login/find/step1";
import Step2 from "@/components/login/find/step2";
import Step3 from "@/components/login/find/step3";
import UpdateEditor from "@/components/article/updateEditor";

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
        redirect: '/personal',
        children: [
            {path: 'personal', name: 'personal', component: Personal},
            {path: 'user', name: 'user', component: User},
            {path: 'profession', name: 'profession', component: Profession},
            {path: 'files', name: 'files', component: Files},
            {path: 'navigation', name: 'navigation', component: Navigation},
            {path: 'page', name: 'page', component: Page},
            {
                path: 'article',
                name: 'article',
                component: Article,
                children: [
                    {
                        path: 'main',
                        name: 'main',
                        component: ArticleMain
                    },
                    {
                        path: 'save',
                        name: 'save',
                        component: SaveEditor,
                    },
                    {
                        path: 'update/:aid',
                        name: 'update',
                        component: UpdateEditor,
                    },
                ]
            },
            {path: 'log', name: 'log', component: Log},
        ]
    },
    {
        path: '/login',
        name: 'login',
        redirect: '/login/form',
        component: Login,
        children: [
            {path: 'form', name: 'loginForm', component: LoginForm},
            {
                path: 'find',
                name: 'findPwd',
                // redirect: '/login/find/step1',
                component: FindPwd,
                children: [
                    {path: 'step1', name: 'findStep1', component: Step1},
                    {path: 'step2', name: 'findStep2', component: Step2},
                    {path: 'step3', name: 'findStep3', component: Step3},
                ]
            },
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

let routeReg = /login\/.*/

// 全局导航守卫
router.beforeEach((to) => {
    // 判断路由是否可以放行
    if (routeReg.test(to.path)) {
        return true
    } else {
        // 如果路由不可放行，则判断用户是否含有token
        let token = store.getters.token
        if (token === null || token === "") {
            // 没有token则跳转到登录页
            return {name: 'login'}
        }
        // 含有token则放行
        return true
    }
})

export default router
