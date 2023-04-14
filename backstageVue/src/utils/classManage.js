import {getClassData} from "./axiosFun";
export const getGradeSelector = () => {return getClassData("post", "/api/classMangerPage/getGradeSelector", "")}
export const getCollegeSelector = () => {return getClassData("post", "/api/classMangerPage/getCollegeSelector", "")}
export const selectClass = (params) => {return getClassData("post", "/api/classMangerPage/selectClass", params)}
export const getClassDetail = (params) => {return getClassData("post", "/api/classMangerPage/getClassDetail", params)}
export const getClassNo = (params) => {return getClassData("post", "/api/classMangerPage/getClassNo", params)}
export const addCls = (params) => {return getClassData("post", "/api/classMangerPage/addClass", params)}
export const searchCls = (params) => {return getClassData("post", "/api/classMangerPage/getClasses", params)}