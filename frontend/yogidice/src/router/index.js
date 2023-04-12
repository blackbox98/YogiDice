import { createRouter, createWebHistory } from "vue-router";


import HomeView from "@/views/HomeView.vue";
import SignupView from "@/views/SignupView.vue";
import KakaoLoginView from "@/views/KakaoLoginView.vue";
import BerChart from "@/components/BerChart.vue";
import RegistNickName from "@/components/intro/RegistNickName.vue";
import InitChoice from "@/views/InitChoice.vue";
import MypageView from "@/views/MypageView.vue";
import MainPageView from "@/views/MainPageView.vue";
import MoreGameView from "@/views/MoreGameView.vue";
import DiceBgLayout from "@/layouts/DiceBgLayout.vue";
import GreyBgHeadBar from "@/layouts/GreyBgHeadBar.vue";
import DetailView from "@/views/DetailView.vue";
import PlaceView from "@/views/PlaceView.vue";
import GamePlusView from "@/views/GamePlusView.vue";
import BoardBgLayout from "@/layouts/BoardBgLayout.vue";
import PickView from "@/views/PickView.vue";
import PickQuestionView from "@/views/PickQuestionView.vue";
import FilteringResView from "@/views/FilteringResView.vue";
import EnterPlusGameView from "@/views/EnterPlusGameView.vue";


const routes = [
  {
    path: "/",
    name: "HomeView",
    component: HomeView,
  },
  {
    path: "/BerChart",
    name: "BerChart",
    component: BerChart,
  },
  {
    path: "/MyPage",
    name: "MypageView",
    component: MypageView,
    meta: {authRequired: true}
  },
  {
    path: "/signup",
    component: DiceBgLayout,
    children: [
      {
        path: "",
        name: "SignupView",
        component: SignupView,
      },
      {
        path: "regist",
        name: "RegistNickName",
        component: RegistNickName,
        props: true,
      },
      {
        path: "choice",
        name: "InitChoice",
        component: InitChoice,
        meta: {authRequired: true}
      },
      {
        path: "/kakaologin",
        name: "KakaoLoginView",
        component: KakaoLoginView,
      },
    ],
  },
  {
    path: "/home",
    component: GreyBgHeadBar,
    children: [
      {
        path: "",
        name: "MainPage",
        component: MainPageView,
        meta: {authRequired: true}
      },
      {
        path: ":type",
        name: "MoreList",
        component: MoreGameView,
        meta: {authRequired: true}
      },
    ],
  },
  {
    path: "/game",
    component: GreyBgHeadBar,
    children: [
      {
        path: "/info",
        name: "GameDetail",
        component: DetailView,
        meta: {authRequired: true}
      },
    ],
  },
  {
    path: "/cafes",
    component: GreyBgHeadBar,
    children: [
      {
        path: "",
        name: "PlaceView",
        component: PlaceView,
        meta: {authRequired: true}
      },
    ],
  },
  {
    path: "/plusgame",
    name: "GamePlusView",
    component: GamePlusView,
    meta: {authRequired: true}
  },
  {
    path: "/plus",
    component: GreyBgHeadBar,
    children: [
      {
        path: "",
        name: "ChoicePlusGame",
        component: EnterPlusGameView,
        meta: {authRequired: true}
      },
    ],
  },
  {
    path: "/pick",
    component: BoardBgLayout,
    children: [
      {
        path: "",
        name: "GamePickHome",
        component: PickView,
        meta: {authRequired: true}
      },
      {
        path: "question",
        name: "QuestionView",
        component: PickQuestionView,
        meta: {authRequired: true}
      },
      {
        path: "result",
        name: "PickResultView",
        component: FilteringResView,
        meta: {authRequired: true}
      },
    ],
  },
];


const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

import store from "../store/index.js"
import { computed } from "vue"


let isLogginedIn = computed(()=>!!store.state.user.token)

router.beforeEach(function (to, from, next) {
  // to: 이동할 url에 해당하는 라우팅 객체
  if (to.matched.some(function(routeInfo) {
    return routeInfo.meta.authRequired;
  }) && !isLogginedIn.value) {
    // 이동할 페이지에 인증 정보가 필요하면 경고 창을 띄우고 페이지 전환은 하지 않음
    alert('로그인이 필요한 서비스입니다!');
    next({name:"SignupView"})
  } else {
    next(); // 페이지 전환
  }
})
