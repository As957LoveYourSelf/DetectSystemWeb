import {getClassroomData} from "./axiosFun";
export const getBuildingFloorSelector = (params) => {return getClassroomData("post", "/api/classroomManage/getBuildingFloorSelector",params)}
export const getBuildingSelector = () => {return getClassroomData("post","/api/classroomManage/getBuildingSelector")}
export const getClassroomInfo = (params) => {return getClassroomData("post","/api/classroomManage/getClassroomInfo",params)}
export const orderClassroom = (params) => {return getClassroomData("post", "/api/classroomManage/orderClassroom", params)}
export const deorderClassroom = (params) => {return getClassroomData("post", "/api/classroomManage/deorderClassroom", params)}
export const getOrderDetail = (params) => {return getClassroomData("post", "/api/classroomManage/getOrderDetail", params)}
