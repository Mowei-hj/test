import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'user', name: 'User', meta: { name: '学员信息' }, component: () => import('../views/manager/User') },
      { path: 'coach', name: 'Coach', meta: { name: '教练信息' }, component: () => import('../views/manager/Coach') },
      { path: 'reserve', name: 'Reserve', meta: { name: '教练预约' }, component: () => import('../views/manager/Reserve') },
      { path: 'course', name: 'Course', meta: { name: '课程' }, component: () => import('../views/manager/Course') },
      { path: 'orders', name: 'Orders', meta: { name: '课程订单' }, component: () => import('../views/manager/Orders') },
      { path: 'equipment', name: 'Equipment', meta: { name: '器材' }, component: () => import('../views/manager/Equipment') },
      { path: 'eqReserve', name: 'EqReserve', meta: { name: '器材预约' }, component: () => import('../views/manager/EqReserve') },
      { path: 'menu', name: 'Menu', meta: { name: '菜谱' }, component: () => import('../views/manager/Menu') },
      { path: 'experience', name: 'Experience', meta: { name: '经验' }, component: () => import('../views/manager/Experience') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      { path: 'coachPerson', name: 'CoachPerson', meta: { name: '个人信息' }, component: () => import('../views/front/CoachPerson') },
      { path: 'coach', name: 'Coach', meta: { name: '金牌教练' }, component: () => import('../views/front/Coach') },
      { path: 'coachDetail', name: 'CoachDetail', meta: { name: '教练详情' }, component: () => import('../views/front/CoachDetail') },
      { path: 'reserve', name: 'Reserve', meta: { name: '我的预约' }, component: () => import('../views/front/Reserve') },
      { path: 'course', name: 'Course', meta: { name: '课程' }, component: () => import('../views/front/Course') },
      { path: 'courseDetail', name: 'CourseDetail', meta: { name: '课程详情' }, component: () => import('../views/front/CourseDetail') },
      { path: 'orders', name: 'Orders', meta: { name: '课程订单' }, component: () => import('../views/front/Orders') },
      { path: 'equipment', name: 'Equipment', meta: { name: '器材' }, component: () => import('../views/front/Equipment') },
      { path: 'eqReserve', name: 'EqReserve', meta: { name: '器材预约' }, component: () => import('../views/front/EqReserve') },
      { path: 'menu', name: 'Menu', meta: { name: '菜谱' }, component: () => import('../views/front/Menu') },
      { path: 'myExperience', name: 'MyExperience', meta: { name: '我的经验' }, component: () => import('../views/front/MyExperience') },
      { path: 'experience', name: 'Experience', meta: { name: '经验分享' }, component: () => import('../views/front/Experience') },
      { path: 'experienceDetail', name: 'ExperienceDetail', meta: { name: '经验分享' }, component: () => import('../views/front/ExperienceDetail') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
