import { createRouter, createWebHashHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: () => import("../views/Layout.vue"),
        children: [
            {
                path: '/',
                name: 'monitor',
                component: () => import("../chillrain_components/Monitor.vue")
            },
            {
                path: '/settings',
                name: 'settings',
                component: () => import("../chillrain_components/Settings.vue")
            }
        ]
    }
];

const options = {
    history: createWebHashHistory(),
    routes,
};

const router = createRouter(options);

export default router;
