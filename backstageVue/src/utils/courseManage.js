import {getCourseData} from "./axiosFun";
export const getGradeSelector = () => {return getCourseData("post", "/api/course/getGradeSelector", "")}
export const getCollegeSelector = () => {return getCourseData("post", "/api/course/getCollegeSelector", "")}
export const importCourse = (params) => {return getCourseData("post", "/api/course/import", params)}
export const selectCourse = (params) => {return getCourseData("post", "/api/course/select", params)}
export const deleteCourse = (params) => {return getCourseData("post", "/api/course/deleteCourse", params)}
export const getMajorSelector = (params) => {return getCourseData("post", "/api/course/getMajorSelector", params)}










