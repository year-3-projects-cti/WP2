import { createRouter, createWebHistory } from "vue-router";
import DashboardView from "@/views/DashboardView.vue";
import UserLogInView from "@/views/auth/UserLogInView.vue";
import AuthView from "@/views/AuthView.vue";
import UserRegisterView from "@/views/auth/UserRegisterView.vue";

const routes = [
  { path: "/", component: DashboardView },
  { path: "/login", component: UserLogInView },
  { path: "/register", component: UserRegisterView },
  { path: "/dashboard", component: AuthView }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
