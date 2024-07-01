// router/index.ts 文件
import { createRouter, createWebHashHistory, RouterOptions, Router, RouteRecordRaw } from 'vue-router'
type RouteRecordRaw = typeof RouteRecordRaw
type RouterOptions = typeof RouterOptions
type Router = typeof Router
//由于router的API默认使用了类型进行初始化，内部包含类型定义，所以本文内部代码中的所有数据类型是可以省略的
//RouterRecordRaw是路由组件对象
const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Layout',
        component: () => import("~/views/Layout.vue"),
        children: [
            {
                path: '/',
                name: 'monitor',
                component: () => import("~/chillrain_components/Monitor.vue")
            },
            {
                path: '/settings',
                name: 'settings',
                component: () => import("~/chillrain_components/Settings.vue")
            }
        ]
    },



]

// RouterOptions是路由选项类型
const options: RouterOptions = {
    history: createWebHashHistory(),
    routes,
}
// Router是路由对象类型
const router: Router = createRouter(options)

export default router