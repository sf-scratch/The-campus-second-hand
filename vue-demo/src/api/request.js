//对axios二次封装
import axios from "axios";
//引入进度条
import nprogress from 'nprogress'
//引入进度条样式
import "nprogress/nprogress.css"

const requests = axios.create({
    //基于路径
    baseURL:"/api",
    //请求超时时间
    timeout:30000,

})

//请求拦截器
requests.interceptors.request.use((config)=>{
    // nprogress.start();
    return config;
})

//响应拦截器
requests.interceptors.response.use((success)=>{
    //成功回调函数：服务器响应的数据回来后，响应拦截器可以检测到，可以做一些操作
    // nprogress.done();
    // if (success.data.state === 200){
    //
    // }
    // console.log('响应拦截器',success.data)
    return success.data;
},(error)=>{
    console.log('服务器响应失败')
    //服务器响应失败的回调函数
    return Promise.reject(new Error(error.message));
})

//对外暴露
export default requests;
