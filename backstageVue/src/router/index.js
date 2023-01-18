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
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/student',
      name: 'student',
      component:() => import('../views/StudentManageView.vue')
    },
    {
      path: '/teacher',
      name: 'teacher',
      component:() => import('../views/TeacherManageView.vue')
    },
    {
      path: '/class',
      name: 'class',
      component:() => import('../views/ClassManageView.vue')
    },
    {
      path: '/classroom',
      name: 'classroom',
      component:() => import('../views/ClassroomManageView.vue')
    },
    {
      path: '/setting',
      name: 'setting',
      component:() => import('../views/SettingView.vue')
    },
    {
      path: '/todo',
      name: 'todo',
      component:() => import('../views/TODOView.vue')
    }
  ]
})

export default router
