import { createApp } from "vue";
import App from "./App.vue";

// vuex
import store from "./store/index.js";

// vue-router
import router from "./router/index.js";

// axios
import axios from "axios";

// mitt
import mitt from "mitt";
const emitter = mitt();

const app = createApp(App);
app.use(store);
app.use(router);

// 전역 변수로 선언
app.config.globalProperties.emitter = emitter;
app.config.globalProperties.$axios = axios;

app.mount("#app");
