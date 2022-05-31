import axios from 'axios'
import { Message } from 'element-ui'

//响应拦截器
axios.interceptors.response.use(success=>{
    //业务逻辑错误
    if (success.status && success.status === 200){
        if (success.data.state !== 200){
            Message.error({message:success.data.message})
            return ;
        }
        if (success.data.message){
            Message.success({message:success.data.message})
        }
    }
    return success.data;
},error => {
    Message.error("错误")
})

let base = ''//可能的前置路径

//传送json格式的post请求
export const postRequest = (url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params
    })
}