import axios from 'axios';
axios.interceptors.request.use((config)=>{
  // console.log("进入请求拦截器")
  return config
},(error)=>{
  return Promise.reject(error)
})

axios.interceptors.response.use((config)=>{
  // console.log("响应拦截器")
  console.log(config.data.code)
  if(config.data.code === 401){
    setTimeout(() => {
        console.log('false')
    }, 500)
  }
  return config
},(error)=>{
  return Promise.reject(error)
})

// 登录请求方法
const loginreq = (method, url, params) => {
    return axios({
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json;charset:utf-8;',
        },
        params: params
    }).then(res => res.data);
};

//  班级管理数据请求
const getClassData = (method, url, params) => {
    return axios({
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json;charset:utf-8;',
            // 'Bearer ' +
            'Authorization': localStorage.getItem('userToken')
        },
        params: params
    }).then(res => res.data);
};

// 学生管理数据请求
const getStudentData = (method, url, params) => {
    return axios({
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json;charset:utf-8;',
            'Authorization': localStorage.getItem('token')
        },
        data: params,
        transformRequest: [
            (data) => {
                let ret = "";
                for (let it in data) {
                    ret += encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
                }
                return ret;
            }]
    }).then(res => res.data);
};
// 教师管理请求
const getTeacherData = (method, url, params) => {
    return axios({
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/json;charset:utf-8;',
            'Authorization': localStorage.getItem('token')
        },
        data: params,
        transformRequest: [
            (data) => {
                let ret = "";
                for (let it in data) {
                    ret += encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
                }
                return ret;
            }]
    }).then(res => res.data);
};
export {
    loginreq,
    getClassData,
    getStudentData,
    getTeacherData
}
