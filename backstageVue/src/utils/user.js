import {userManage, loginreq} from './axiosFun';
export const login = (params) => { return loginreq("post", "/api/loginPage/login", params) };
export const resetPsd = (params) => {return userManage("post","/api/userManagePage/resetPassword", params)}
