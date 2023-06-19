import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './router/router'
import VueResource from "vue-resource"
import '@/assets/css/main.css'


createApp(App).use(router)
    .mount('#app')
    .use(router)
    .use(VueResource)
    .port(8082)
