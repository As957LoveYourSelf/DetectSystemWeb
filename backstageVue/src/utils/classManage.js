import {getClassData} from "@/utils/axiosFun";
export const getGradeSelector = () => {return getClassData("post", "/api/classMangerPage/getGradeSelector", "")}
export const getCollageSelector = () => {return getClassData("post", "/api/classMangerPage/getCollageSelector", "")}
export const selectByGrade = (params) => {return getClassData("post", "/api/classMangerPage/selectByGrade", params)}
export const selectByCollage = (params) => {return getClassData("post", "/api/classMangerPage/selectByCollage", params)}
export const selectByGradeAndCollage = (params) => {return getClassData("post", "/api/classMangerPage/selectByGradeAndCollage", params)}
export const getClassDetail = (params) => {return getClassData("post", "/api/classMangerPage/getClassDetail", params)}
