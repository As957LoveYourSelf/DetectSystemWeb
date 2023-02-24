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
        requireAuth: true
      },
    },
    {
      path: '/student',
      name: 'Student',
      component:() => import('../views/StudentManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
      },
    },
    {
      path: '/teacher',
      name: 'Teacher',
      component:() => import('../views/TeacherManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
      },
    },
    {
      path: '/class',
      name: 'Class',
      component:() => import('../views/ClassManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
      },
    },
    {
      path: '/classroom',
      name: 'Classroom',
      component:() => import('../views/ClassroomManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
      },
    },
    {
      path: '/course',
      name: 'Course',
      component:() => import('../views/CourseManageView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
      },
    },
    {
      path: '/test',
      name: 'Test',
      component:() => import('../views/TestView.vue'),
      meta: {
        requireAuth: true
      },
    },
    {
      path: '/todo',
      name: 'ToDo',
      component:() => import('../views/TODOView.vue'),
      meta: {
        requireAuth: true,
        keepAlive:true
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
      path: '/course',
      name: 'Course',
      component:() => import('../views/CourseManageView.vue'),
      meta: {
        requireAuth: true
      },
    }
  ]
})

export default router
