import { createRouter, createWebHistory } from "vue-router";
import UsersView from "../views/UserView.vue";

const routes = [
  { path: "/", component: UsersView }, // Setează lista utilizatorilor ca pagină principală
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
