import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import md5 from 'blueimp-md5'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import './assets/main.css'

router.beforeEach((to, from, next) => {
    if (to.matched.length !== 0) {
        if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
            if (Boolean(localStorage.getItem("userInfo"))) { // 通过vuex state获取当前的user是否存在
                next();
            } else {
                next({
                    path: '/login',
                    query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
                })
            }
        } else {
            if (Boolean(localStorage.getItem("userInfo"))) { // 判断是否登录
                if (to.path !== "/" && to.path !== "/login") { //判断是否要跳到登录界面
                    next();
                } else {
                    /**
                     * 防刷新，如果登录，修改路由跳转到登录页面，修改路由为登录后的首页
                     */
                    next({
                        path: '/home'
                    })
                }
            } else {
                next();
            }
        }
    } else {
        next({
            path: '/login',
            query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
    }
    //路由缓存
    //从cacheList中的任何一个页面返回，当前页面缓存
    const cacheList = to.meta.cacheList
    if (cacheList) {
        if (cacheList.indexOf(from.name) > -1) {
            to.meta.keepAlive = true
        } else {
            //如果没有纳进cacheList缓存需求，就不缓存
            if (from.name) {
                to.meta.keepAlive = false
            }
            // 导航跳转需要缓存处理
            if (from.meta.cacheList) {
                to.meta.keepAlive = true
            }
        }
    }
    next()
})


const app = createApp(App)
app.use(md5)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
