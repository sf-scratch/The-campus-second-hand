//对axios二次封装
import axios from "axios";
import { Message, MessageBox} from "element-ui";
import Vue from 'vue'
const requests = axios.create({
    //基于路径
    baseURL:"/api",
    //请求超时时间
    timeout:30000,

})

//请求拦截器
requests.interceptors.request.use((config)=>{
    return config;
})

//响应拦截器
requests.interceptors.response.use((success)=>{
    let result = success.data
    //成功回调函数：服务器响应的数据回来后，响应拦截器可以检测到，可以做一些操作
    if (result.state === 200){
        let message = result.message
        //响应成功
        Message({
            message: message,
            type: 'success'
        });
    }else if (result.state === 4010){
        //4010:登录过期 |
        Message({
            message: result.message,
            type: 'warning'
        });
        Vue.$cookies.remove('access_token_admin')
        MessageBox.alert('登录信息超时，请重新登录！','登录超时',{
            confirmButtonText:'跳转登录页面',
            callback:()=>{
                window.location.href = '/'
            }
        })
    }else {
        Message({
            message: result.message,
            type: 'warning'
        });
        return Promise.reject(new Error(result.message));
    }
    return result;
},(error)=>{
    Message({
        message: '服务器响应失败',
        type: 'warning'
    });
    console.log('服务器响应失败')
    //服务器响应失败的回调函数
    return Promise.reject(new Error(error.message));
})

//对外暴露
export default requests;
