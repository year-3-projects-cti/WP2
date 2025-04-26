import { defineStore } from "pinia";
import { ref } from "vue";

export const useAuthStore = defineStore("auth", () => {
  const isAuthenticated = ref(false);

  const storedAuth = localStorage.getItem("isAuthenticated");
  if (storedAuth === "true") {
    isAuthenticated.value = true;
  }

  function login() {
    isAuthenticated.value = true;
    localStorage.setItem("isAuthenticated", "true");
  }

  function logout() {
    isAuthenticated.value = false;
    localStorage.setItem("isAuthenticated", "false");
  }

  return { isAuthenticated, login, logout };
});
