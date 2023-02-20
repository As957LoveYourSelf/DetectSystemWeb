import {getClassData} from "./axiosFun";
export const getGradeSelector = () => {return getClassData("post", "/api/classMangerPage/getGradeSelector", "")}
export const getCollageSelector = () => {return getClassData("post", "/api/classMangerPage/getCollageSelector", "")}
export const selectClass = (params) => {return getClassData("post", "/api/classMangerPage/selectClass", params)}
export const getClassDetail = (params) => {return getClassData("post", "/api/classMangerPage/getClassDetail", params)}
