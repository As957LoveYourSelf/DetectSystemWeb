import {getTeacherData} from "./axiosFun";
export const getCollageSelector = () => {return getTeacherData("post", "/api/teacherManagePage/getCollageSelector", "")}
export const searchTeacherInfo=(params) => {return getTeacherData("post","/api/teacherManagePage/selectTeacher", params)}
export const getTeacherDetail = (params) => {return getTeacherData("post", "/api/teacherManagePage/getTeacherDetail", params)}
