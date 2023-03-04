import {algorithmServer} from "./axiosFun"
export const enhance = (params) => {return algorithmServer("post", "/api/enhance", params)}
export const defaultStyleTran = (params) => {return algorithmServer("post", "/api/defaultStyleTran", params)}
export const anyStyleTran = (params) => {return algorithmServer("post", "/api/anyStyleTran", params)}