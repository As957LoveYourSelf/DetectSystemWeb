import {getStudentData} from "./axiosFun";
export const selectStudent = (params) => {return getStudentData("post", "/api/studentManagePage/selectStudent", params)}
export const getStudentDetail = (params) => {return getStudentData("post", "/api/studentManagePage/getStudentDetail", params)}
