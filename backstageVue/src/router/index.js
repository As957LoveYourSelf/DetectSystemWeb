import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path : '/',
      name: 'Login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/student',
      name: 'Student',
      component:() => import('../views/StudentManageView.vue')
    },
    {
      path: '/teacher',
      name: 'Teacher',
      component:() => import('../views/TeacherManageView.vue')
    },
    {
      path: '/class',
      name: 'Class',
      component:() => import('../views/ClassManageView.vue')
    },
    {
      path: '/classroom',
      name: 'Classroom',
      component:() => import('../views/ClassroomManageView.vue')
    },
    {
      path: '/setting',
      name: 'Setting',
      component:() => import('../views/SettingView.vue')
    },
    {
      path: '/todo',
      name: 'ToDo',
      component:() => import('../views/TODOView.vue')
    },
    {
      path: '/classdetail',
      name: 'ClassDetail',
      component:() => import('../views/ClassDetailView.vue')
    },
    {
      path: '/studentdetail',
      name: 'StudentDetail',
      component:() => import('../views/StudentDetailView.vue')
    },
    {
      path: '/teacherdetail',
      name: 'TeacherDetail',
      component:() => import('../views/TeacherDetailView.vue')
    }
  ]
})

export default router
