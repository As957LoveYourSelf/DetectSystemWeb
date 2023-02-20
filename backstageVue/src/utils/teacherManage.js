import {getTeacherData} from "./axiosFun";

export const getCollageSelector = () => {return getTeacherData("post", "/api/teacherManagePage/getCollageSelector", "")}
export const selectByCollage = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByCollage", params)}
export const selectByTno = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByTno", params)}
export const selectByTname = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByTname", params)}
export const selectByCollageAndTno = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByCollageAndTno", params)}
export const selectByCollageAndTname = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByCollageAndTname", params)}
export const selectByTnoAndTName = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByTnoAndTName", params)}
export const selectByTnoAndTNameAndCollage = (params) => {return getTeacherData("post", "/api/teacherManagePage/selectByTnoAndTNameAndCollage", params)}
export const getTeacherDetail = (params) => {return getTeacherData("post", "/api/teacherManagePage/getTeacherDetail", params)}
