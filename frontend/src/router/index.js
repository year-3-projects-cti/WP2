import { createRouter, createWebHistory } from "vue-router";
import DashboardView from "@/views/DashboardView.vue";
import UserLogInView from "@/views/auth/UserLogInView.vue";
import AuthView from "@/views/AuthView.vue";
import UserRegisterView from "@/views/auth/UserRegisterView.vue";
import { useAuthStore } from "@/stores/useAuthStore";
import InterfaceView from "@/views/interface/InterfaceView.vue";
import StudentsView from "@/views/interface/StudentsView.vue";

const routes = [
  { path: "/", component: DashboardView },
  { path: "/login", component: UserLogInView },
  { path: "/register", component: UserRegisterView },
  { path: "/dashboard", component: AuthView },
  { path: "/interface", component: InterfaceView },
  { path: "/students", component: StudentsView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.path === "/dashboard" && !authStore.isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
