import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'aPerson', component: () => import('@/views/manager/APerson.vue')},
        { path: 'pPerson', component: () => import('@/views/manager/PPerson.vue')},
        { path: 'uPerson', component: () => import('@/views/manager/UPerson.vue')},
        { path: 'password', component: () => import('@/views/manager/Password.vue')},
        { path: 'home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'prouser', component: () => import('@/views/manager/ProUser.vue')},
        { path: 'user', component: () => import('@/views/manager/User.vue')},
        { path: 'attend', component: () => import('@/views/manager/Attend.vue')},
        { path: 'leave', component: () => import('@/views/manager/Leave.vue')},
      ]
    },
    { path: '/login', component: () => import('@/views/Login.vue')},
    { path: '/register', component: () => import('@/views/Register.vue')},
  ]
})

export default router
