import {getTeacherData} from "./axiosFun";
export const getCollegeSelector = () => {return getTeacherData("post", "/api/teacherManagePage/getCollegeSelector", "")}
export const searchTeacherInfo=(params) => {return getTeacherData("post","/api/teacherManagePage/selectTeacher", params)}
export const getTeacherDetail = (params) => {return getTeacherData("post", "/api/teacherManagePage/getTeacherDetail", params)}
export const removeTeacher = (params) => {return getTeacherData("post", "/api/teacherManagePage/removeTeacher", params)}
