import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import md5 from 'blueimp-md5'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import './assets/main.css'

const app = createApp(App)
app.use(md5)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
