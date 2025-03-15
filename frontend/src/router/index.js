import { createRouter, createWebHistory } from "vue-router";
import UsersView from "../views/UserView.vue";
import AddUser from "../views/AddUser.vue";
import Settings from "../views/Settings.vue";

const routes = [
  { path: "/", component: UsersView },  // Lista utilizatorilor (default)
  { path: "/add", component: AddUser }, // Formular pentru adăugarea utilizatorilor
  { path: "/settings", component: Settings } // Pagina de setări
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
