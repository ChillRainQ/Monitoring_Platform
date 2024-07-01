import { createApp } from "vue";
import App from "./App.vue";
import "./styles/index.scss";
import "uno.css";
import "element-plus/theme-chalk/src/message.scss";
import Message from "~/utils/message"
import router from "~/router";
import Dialog from "~/chillrain_components/Dialog.vue";
import vue3videoPlay from 'vue3-video-play/lib/index' ;
import 'vue3-video-play/dist/style.css' // 引入css

// import * as Icons from './element-plus/icons'
const app = createApp(App);
app.use(router)
app.use(vue3videoPlay)
app.component("Dialog", Dialog)
app.mount("#app");
app.config.globalProperties.Message = Message
//图标注册
// Object.keys(Icons).forEach(key => {
//     app.component(key, Icons[key])
// })
