import { createApp } from 'vue'
import 'element3/lib/theme-chalk/index.css'
import Element3 from 'element3'
import './style.css'
import App from './App.vue'
import { store } from './store'

createApp(App).use(Element3).use(store).mount('#app')
