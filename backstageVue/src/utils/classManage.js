import {getClassData} from "./axiosFun";
export const getGradeSelector = () => {return getClassData("post", "/api/classMangerPage/getGradeSelector", "")}
export const getCollegeSelector = () => {return getClassData("post", "/api/classMangerPage/getCollegeSelector", "")}
export const selectClass = (params) => {return getClassData("post", "/api/classMangerPage/selectClass", params)}
export const getClassDetail = (params) => {return getClassData("post", "/api/classMangerPage/getClassDetail", params)}
