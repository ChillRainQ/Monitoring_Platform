import axios from 'axios';
import { ElMessage, ElLoading } from 'element-plus';

// 创建axios实例
const service = axios.create({
    // 服务接口请求
    baseURL: "/api",
    // 超时设置
    timeout: 15000,
})

let loading:any;
//正在请求的数量
let requestCount:number = 0
//显示loading
const showLoading = () => {
    if (requestCount === 0 && !loading) {
        //加载中显示样式可以自行修改
        loading = ElLoading.service({
            text: "拼命加载中，请稍后...",
            background: 'rgba(0, 0, 0, 0.7)',
            spinner: 'el-icon-loading',
        })
    }
    requestCount++;
}
//隐藏loading
const hideLoading = () => {
    requestCount--
    if (requestCount == 0) {
        loading.close()
    }
}

// 请求拦截
service.interceptors.request.use(config => {
    showLoading()
    if (config.method === 'get' && config.params) {
        let url = config.url + '?';
        for (const propName of Object.keys(config.params)) {
            const value = config.params[propName];
            var part = encodeURIComponent(propName) + "=";
            if (value !== null && typeof(value) !== "undefined") {
                // 对象处理
                if (typeof value === 'object') {
                    for (const key of Object.keys(value)) {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        url += subPart + encodeURIComponent(value[key]) + "&";
                    }
                } else {
                    url += part + encodeURIComponent(value) + "&";
                }
            }
        }
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use((res:any) => {
    hideLoading()
    // 未设置状态码则默认成功状态
    const code = res.data['code'];
    // 获取错误信息
    const msg = res.data['data'];
    if(code === 200){
        console.log(msg + "--测试")
        if(typeof msg == 'string'){
            ElMessage.success(msg);
        }
        return Promise.resolve(res.data)
    }else{
        ElMessage.error(msg);
        return Promise.reject(res.data)
    }
}, error => {
    console.log('err' + error)
    hideLoading()
    let { message } = error;
    if (message == "Network Error") {
        message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
        message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
        message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    ElMessage.error({
        message: message,
        duration: 5 * 1000
    })
    return Promise.reject(error)
})

export default service;
