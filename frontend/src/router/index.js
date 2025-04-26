import { createRouter, createWebHistory } from "vue-router";
import DashboardView from "@/views/DashboardView.vue";
import UserLogInView from "@/views/auth/UserLogInView.vue";
import UserRegisterView from "@/views/auth/UserRegisterView.vue";
import InterfaceView from "@/views/interface/InterfaceView.vue";
import StudentsView from "@/views/interface/StudentsView.vue";
import TeachersView from "@/views/interface/TeachersView.vue";
import { useAuthStore } from "@/stores/useAuthStore";

const routes = [
  { path: "/", component: DashboardView },
  { path: "/login", component: UserLogInView },
  { path: "/register", component: UserRegisterView },
  { path: "/interface", component: InterfaceView, meta: { requiresAuth: true } },
  { path: "/students", component: StudentsView, meta: { requiresAuth: true } },
  { path: "/teachers", component: TeachersView, meta: { requiresAuth: true } },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.path === "/" && authStore.isAuthenticated) {
    next("/interface");
  }
  else if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/login");
  }
  else {
    next();
  }
});

export default router;
