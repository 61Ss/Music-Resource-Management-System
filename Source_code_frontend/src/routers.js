import {createRouter, createWebHashHistory} from 'vue-router'
import Layout_front from '@/layout_front/Layout' //引入前台界面框架
import Layout_manager from '@/layout_manager/Layout' //引入后台界面框架
import Layout_login from '@/layout_front/LoginLayout.vue' //引入登录界面

export const constantRouterMap = [


    //---------------------------------------------------------------前台
    /*首页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            name: '/default',
            path: '/default',
            component: () => import('@/views/default'),
        }]
    },


    /* 普通用户登录页*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_login,
        children: [{
            path: '/login',
            component: () => import('@/views/login'),
        }]
    },

    /*注册*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/register',
            component: () => import('@/views/register'),
        }]
    },
    /*找回密码*/
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/pwdback',
            component: () => import('@/views/pwdback'),
        }]
    },





    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/projectinfo',
            component: () => import('@/views/projectinfo'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/projectinfoDetail',
            component: () => import('@/views/projectinfoDetail'),
        }]
    },


    //////////////////////////////////前台普通用户-----------------------------------------------------
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/edit_info',
            component: () => import('@/views/members/edit_info'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/members/projectinfo',
            component: () => import('@/views/members/projectinfo'),
        }]
    },

    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        component: Layout_front,
        children: [{
            path: '/members/projectinfo_type',
            component: () => import('@/views/members/projectinfo_type'),
        }]
    },



    //---------------------------------------------------------------后台
    //单页显示 无框架
    {
        path: '/manager/login',
        component: () => import('@/views/manager/login'),
        hidden: true
    },
    {
        path: '/membersinfo',
        component: () => import('@/views/manager/members/membersinfo'),
        hidden: true
    },
    {
        /*这是标准格式 必须这样写   */
        path: '',//如果打开的是首页,或登录后没有页面,
        hidden: true,
        redirect: 'default',  //则跳转到这个首页
        component: Layout_manager,

        children: [{
            path: '/manager/default',
            component: () => import('@/views/manager/default'),


        }]
    },



    {
        path: '',
        hidden: true,
        component: Layout_manager,

        children: [{
            path: 'manager/manager/search',
            component: () => import('@/views/manager/manager/search'),

        }]
    },

    {
        path: '',
        hidden: true,
        component: Layout_manager,

        children: [{
            path: 'manager/projectinfo/search',
            component: () => import('@/views/manager/projectinfo/search'),

        }]
    },
    {
        path: '',
        hidden: true,
        component: Layout_manager,

        children: [{
            path: 'manager/projectinfo_type/search',
            component: () => import('@/views/manager/projectinfo_type/search'),

        }]
    },


    {
        path: '',
        hidden: true,
        component: Layout_manager,

        children: [{
            path: 'manager/members/search',
            component: () => import('@/views/manager/members/search'),

        }]
    },


]


///404判断必须在最后
export const footRouterMap = [
    /*
        {path: '*', redirect: '/404', hidden: true}
    */
]


const router = createRouter({
    history: createWebHashHistory(),
    scrollBehavior: () => ({top: 0}),
    routes: constantRouterMap
})
export default router

