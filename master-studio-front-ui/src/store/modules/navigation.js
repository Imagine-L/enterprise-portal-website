import {navActions, navMutations} from "@/constant";
import router from "@/route";
import Notices from "@/pages/Notices";

// 导入动态路由，并修改组件名为指定的栏目名
function dynamicRouteComponent(_import, routeName) {
    return _import.then((comp) => {
        if (comp && comp.default) {
            return {...comp.default, name: routeName}
        }
        return comp
    })
}

// 构造面包屑导航栏
function buildBreadcrumbList(breadcrumbList, navList, paths, pathIndex) {
    for (let i in navList) {
        let nav = navList[i]
        if (nav.path === paths[pathIndex]) {
            let fullPaths = paths.slice(0, pathIndex + 1)
            breadcrumbList.push({
                nid: nav.nid,
                navName: nav.navName,
                fullPath: fullPaths.toString().replaceAll(',', '/')
            })
            if (nav.children !== null && nav.children.size !== 0) {
                buildBreadcrumbList(breadcrumbList, nav.children, paths, ++pathIndex)
            }
            break
        }
    }
}

const navigation = {
    state() {
        return {
            // 栏目列表
            navList: [],
            // 通知的年份日期
            noticeDateList: [],
            // 当前访问路径
            currentPath: '',
            // 当前路径的每一个结点路径
            paths: [],
            // 面包屑导航
            breadcrumbList: [
                {
                    nid: '',
                    navName: '',
                    fullPath: ''
                }
            ],
        }
    },
    getters: {
        getCurrentPath(state) {
            return state.currentPath
        },
        getNavList(state) {
            return state.navList
        },
        getBreadcrumbList(state) {
            return state.breadcrumbList
        }
    },
    mutations: {
        [navMutations.SET_CURRENT_PATH](state, path) {
            state.currentPath = path
            state.paths = path.substring(1).split('/')
            this.commit(navMutations.BUILD_BREADCRUMB_LIST)
        },
        [navMutations.SET_BREADCRUMB_LIST](state, breadcrumbList) {
            state.breadcrumbList = breadcrumbList
        },
        // 根据日期构造通知列表路由
        [navMutations.BUILD_NOTICE_LIST_ROUTE](state, {parentNavName, dateList}) {
            for (let i in dateList) {
                let date = dateList[i]
                router.addRoute(parentNavName, {
                    path: date,
                    name: `${parentNavName}-${date}`,
                    props: {date: date},
                    component: () => dynamicRouteComponent(import('@/components/notice/NoticesList.vue'),
                        `${parentNavName}-${date}`)
                })
                router.addRoute(parentNavName, {
                    path: `${date}/:aid`,
                    name: `${parentNavName}-${date}-detail`,
                    component: () => dynamicRouteComponent(import('@/components/common/CommonArticleDetail.vue'),
                        `${parentNavName}-${date}`),
                    props: {title: '通知详情'}
                })
            }
        },
        // 根据路由信息构造面包屑导航
        [navMutations.BUILD_BREADCRUMB_LIST](state) {
            state.breadcrumbList = []
            buildBreadcrumbList(state.breadcrumbList, state.navList, state.paths, 0)
        },
    },
    actions: {
        [navActions.SAVE_NAV_LIST](context, {navList, noticeDateList}) {
            context.state.navList = navList
            context.state.noticeDateList = noticeDateList
            this.dispatch(navActions.BUILD_NAV_ROUTE, {
                navList: navList,
                parentNav: null
            })
        },
        async [navActions.BUILD_NAV_ROUTE](context, {navList, parentNav}) {
            for (let i in navList) {
                let nav = navList[i]
                // 判断栏目等级
                if (nav.level === 1) {
                    if (nav.navType === 0) {
                        // 是一级栏目，且栏目的类型为0(父栏目)，则添加一个父路由，并构造子路由
                        router.addRoute({
                            path: `/${nav.path}`,
                            name: nav.navName,
                            component: () => dynamicRouteComponent(import('@/pages/Parent.vue'), nav.navName),
                        })
                        this.dispatch(navActions.BUILD_NAV_ROUTE, {
                            navList: nav.children,
                            parentNav: nav
                        })
                    } else if (nav.navType === 1) {
                        // 是一级栏目，且栏目的类型为1(页面栏目)，则添加一个页面路由
                        router.addRoute({
                            path: `/${nav.path}`,
                            name: nav.navName,
                            component: () => dynamicRouteComponent(import('@/pages/Page.vue'), nav.navName),
                            meta: {
                                title: `${nav.navName}-何志清大师工作室`
                            }
                        })
                    } else if (nav.navType === 2) {
                        // 是一级栏目，且栏目的类型为2(文章栏目)，判断是否为通知
                        if (nav.notice) {
                            // 是通知就构造通知的路由
                            let noticeRoute = {
                                path: `/${nav.path}`,
                                name: nav.navName,
                                redirect: `/${nav.path}/all`,
                                component: Notices,
                                children: [
                                    {
                                        path: 'all',
                                        name: `${nav.navName}-all`,
                                        component: () => dynamicRouteComponent(import('@/components/notice/NoticesList.vue'), `${nav.navName}-all`),
                                        props: {date: ''},
                                    },
                                    {
                                        path: 'all/:aid',
                                        name: `${nav.navName}-all-detail`,
                                        component: () => dynamicRouteComponent(import('@/components/common/CommonArticleDetail.vue'),
                                            `${nav.navName}-all-detail`),
                                        props: {title: '通知详情'}
                                    },
                                ],
                                meta: {
                                    title: `${nav.navName}-何志清大师工作室`
                                }
                            }
                            router.addRoute(noticeRoute)
                            // 动态添加其他通知子路由
                            // let resp = await getNoticeDateList()
                            this.commit(navMutations.BUILD_NOTICE_LIST_ROUTE, {
                                parentNavName: nav.navName,
                                dateList: context.state.noticeDateList
                            })
                        } else {
                            // 不是通知则构造一个普通页面的路由
                            router.addRoute({
                                path: `/${nav.path}`,
                                name: nav.navName,
                                component: () => dynamicRouteComponent(import('@/pages/Articles.vue'), nav.navName),
                                meta: {
                                    title: `${nav.navName}-何志清大师工作室`
                                }
                            })
                        }
                    }
                } else {
                    if (nav.navType === 1) {
                        // 是二级栏目，且栏目类型是1(页面栏目)，则构造页面子路由，addRoute()第一个参数表示父栏目的name
                        router.addRoute(parentNav.navName, {
                            path: nav.path,
                            name: nav.navName,
                            component: () => dynamicRouteComponent(import('@/components/page/child/ChildPage.vue'), nav.navName),
                            meta: {
                                title: `${nav.navName}-何志清大师工作室`
                            }
                        })
                    } else if (nav.navType === 2) {
                        // 是二级栏目，且栏目类型是2(页面栏目)，则构造文章子路由，addRoute()第一个参数表示父栏目的name
                        router.addRoute(parentNav.navName, {
                            path: nav.path,
                            name: nav.navName,
                            component: () => dynamicRouteComponent(import('@/components/article/ArticlesList.vue'), nav.navName),
                            meta: {
                                title: `${nav.navName}-何志清大师工作室`
                            }
                        })
                        // 是二级栏目，且栏目类型是2(页面栏目)，构造栏目详情页路由
                        router.addRoute(parentNav.navName, {
                            path: nav.path + '/article/:aid',
                            name: nav.navName + '-detail',
                            component: () => dynamicRouteComponent(import('@/components/common/CommonArticleDetail.vue'),
                                nav.navName + '-detail'),
                            props: {title: '文章详情'}
                            // props: route => ({ query: route.query.title })
                        })
                    }
                }
            }
        },
    }
}

export default navigation
