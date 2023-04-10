import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path : '/',
      name: 'Login1',
      component: () => import('../views/LoginView.vue'),
      meta: {
        requireAuth: false
      },
    },
    {
      path : '/login',
      name: 'Login2',
      component: () => import('../views/LoginView.vue'),
      meta: {
        requireAuth: false
      },
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../views/HomeView.vue'),
      meta: {
        requireAuth: true,
        keepAlive: true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","Teacher","Student","Class","Classroom","Course","SupImg","StyTran"]
      },
    },
    {
      path: '/student',
      name: 'Student',
      component:() => import('../views/StudentManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","Home","Teacher","Class","Classroom","Course","SupImg","StyTran"]
      },
    },
    {
      path: '/teacher',
      name: 'Teacher',
      component:() => import('../views/TeacherManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","Home","Student","Class","Classroom","Course","SupImg","StyTran"]
      },
    },
    {
      path: '/class',
      name: 'Class',
      component:() => import('../views/ClassManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","Home","Teacher","Student","Classroom","Course","SupImg","StyTran"]
      },
    },
    {
      path: '/classroom',
      name: 'Classroom',
      component:() => import('../views/ClassroomManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","Home","Teacher","Class","Student","Course","SupImg","StyTran"]
      },
    },
    {
      path: '/course',
      name: 'Course',
      component:() => import('../views/CourseManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["CourseDetail","ClassDetail","TeacherDetail","StudentDetail","CourseDetail","Home","Teacher","Class","Classroom","Student","SupImg","StyTran"]
      },
    },
    {
      path: '/courseDetail',
      name: 'CourseDetail',
      component:() => import('../views/CourseDetailView.vue'),
      meta: {
        requireAuth: true,
      },
    },
    {
      path: '/classdetail',
      name: 'ClassDetail',
      component:() => import('../views/ClassDetailView.vue'),
      meta: {
        requireAuth: true
      },
    },
    {
      path: '/studentdetail',
      name: 'StudentDetail',
      component:() => import('../views/StudentDetailView.vue'),
      meta: {
        requireAuth: true
      },
    },
    {
      path: '/teacherdetail',
      name: 'TeacherDetail',
      component:() => import('../views/TeacherDetailView.vue'),
      meta: {
        requireAuth: true
      },
    },
    {
      path: '/supImg',
      name: 'SupImg',
      component:() => import('../views/SuperResolutionTestView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["Course","CourseDetail","ClassDetail","TeacherDetail","StudentDetail","CourseDetail","Home","Teacher","Class","Classroom","Student","StyTran"]
      },
    },
    {
      path: '/styTran',
      name: 'StyTran',
      component:() => import('../views/StyleTransformTestView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["Course","CourseDetail","ClassDetail","TeacherDetail","StudentDetail","CourseDetail","Home","Teacher","Class","Classroom","Student","SupImg"]
      },
    },
    {
      path: '/styTran2',
      name: 'StyTran2',
      component:() => import('../views/AnyStyleTransformView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true,
        cacheList:["Course","CourseDetail","ClassDetail","TeacherDetail","StudentDetail","CourseDetail","Home","Teacher","Class","Classroom","Student","SupImg","StyTran"]
      },
    },
    {
      path:'/webview',
      name:'WebView',
      component:() => import('../views/WebView.vue'),
      meta: {
        requireAuth:false,
        keepAlive:false,
        fromApp:true
      },
    }
  ]
})

export default router
