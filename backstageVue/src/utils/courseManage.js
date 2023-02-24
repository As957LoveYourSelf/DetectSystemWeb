import {getCourseData} from "./axiosFun";
export const getGradeSelector = () => {return getCourseData("post", "/api/course/getGradeSelector", "")}
export const getCollageSelector = () => {return getCourseData("post", "/api/course/getCollageSelector", "")}
export const importCourse = () => {return getCourseData("post", "/api/course/import", "")}
export const selectCourse = (params) => {return getCourseData("post", "/api/course/select", params)}
export const deleteCourse = (params) => {return getCourseData("post", "/api/course/deleteCourse", params)}
export const getCourseTableBySno = (params) => {return getCourseData("post", "/api/course/getCourseTableBySno", params)}
export const getCourseTableByTno = (params) => {return getCourseData("post", "/api/course/getCourseTableByTno", params)}










