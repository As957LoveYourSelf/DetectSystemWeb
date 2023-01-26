import {getStudentData} from "@/utils/axiosFun";
export const selectBysno = (params) => {return getStudentData("post", "/api/studentManagePage/selectBysno", params)}
export const selectBysname = (params) => {return getStudentData("post", "/api/studentManagePage/selectBysname", params)}
export const selectBysnoAndsname = (params) => {return getStudentData("post", "/api/studentManagePage/selectBysnoAndsname", params)}
export const getStudentDetail = (params) => {return getStudentData("post", "/api/studentManagePage/getStudentDetail", params)}
