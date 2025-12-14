import router from './routers'
import store from './store'
import NProgress from 'nprogress' // progress bar顶部加载进度条
import '@/styles/nprogress.css' // global css

NProgress.configure({showSpinner: false})// NProgress Configuration


router.beforeEach((to, from, next) => {

    NProgress.start() // start progress bar顶部加载条
    document.title =  process.env.title   // 设定标题


    //console.log(to.path);
    if (to.path === '/') next(`/login`) //  重定向到首页;
    next()

    NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it


})

router.afterEach(() => {
    //console.log(5);
    NProgress.done() // finish progress bar
})

function contains(arr, obj) {
    if (arr && arr.length > 0) {
        var i = arr.length;
        while (i--) {
            //console.log(arr[i])
            if (arr[i] === obj) {
                return true;
            }
        }
    }
    return false;
}
