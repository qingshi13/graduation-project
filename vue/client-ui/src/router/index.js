import Vue from 'vue'
import Router from 'vue-router'
import Member from '../views/Member'
import Coach from '../views/Coach'

import Aside from "../components/MemberAside";
import Header from "../components/Header"
Vue.use(Router)

const routes = [
  {
    path:'/login',
    component:() => import('../views/LoginRegister')
  },
  {
    path:'/member',
    component: Member,Aside,Header,
    children:[
      {
        path:'/member/home',
        name:'home',
        component: () => import('../views/MemberHome')
      },
      {
        path:'/member/information',
        name:'information',
        component: () => import('../views/MemberModifyInformation')
      },
      {
        path: '/member/rechargemanagement',
        name: 'RechargeManagement',
        component: () => import('../views/MemberRechargeManagement'),
        // alwaysShow:true
      },
      {
        path:'/member/allcoachs',
        name:'AllCoachs',
        component: () => import('../views/AllCoachs'),
      },
      {
        path:'/member/coachdetail',
        name:'CoachDetail',
        component: () => import('../views/CoachDetail')
      },

      {
        path:'/member/allcourses',
        name:'AllCourses',
        component: () => import('../views/AllCourses'),
      },
      {
        path:'/member/coursedetail',
        name:'CourseDetail',
        component: () => import('../views/CourseDetail')
      },
      {
        path:'/member/mycourse',
        name:'MemberCourse',
        component: () => import('../views/MemberCourse')
      },
      {
        path:'/member/allgoods',
        name:'AllGoods',
        component: () => import('../views/AllGoods'),
      },
      {
        path:'/member/gooddetail',
        name:'Detail',
        component: () => import('../views/Detail')
      },
      {
        path:'/member/cart',
        name:'Cart',
        component: () => import('../views/Cart')
      },
      {
        path:'/member/myorder',
        name:'MyOrder',
        component: () => import('../views/MyOrder')
      },
    ]
  },
  {
    path:'/Coach',
    component: Coach,Aside,Header,
    children:[
      {
        path:'/coach/home',
        name:'home',
        component: () => import('../views/MemberHome')
      },
      {
        path:'/coach/information',
        name:'information',
        component: () => import('../views/CoachModifyInformation')
      },
      {
        path: '/coach/rechargemanagement',
        name: 'RechargeManagement',
        component: () => import('../views/MemberRechargeManagement'),
      },
      {
        path: '/coach/mycourse',
        name: 'CoachCourse',
        component: () => import('../views/CoachCourse'),
      },
      {
        path: '/coach/mystudent',
        name: 'CoachMember',
        component: () => import('../views/CoachMember'),
      },
      {
        path: '/coach/myappointment',
        name: 'CoachAppointment',
        component: () => import('../views/CoachAppointment'),
      },
      {
        path:'/coach/allgoods',
        name:'AllGoods',
        component: () => import('../views/AllGoods'),
      },
      {
        path:'/coach/gooddetail',
        name:'Detail',
        component: () => import('../views/Detail')
      },
      {
        path:'/coach/cart',
        name:'Cart',
        component: () => import('../views/Cart')
      },
      {
        path:'/coach/myorder',
        name:'MyOrder',
        component: () => import('../views/MyOrder')
      },
    ]
  },


]

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


