<template>
  <div class="app-wrapper">
    <NavBar :isSticky="true" :connected="authStore.isAuthenticated" />

    <router-view v-if="!authStore.isAuthenticated" />
    <AuthView v-else />

    <footer class="app-footer">
      <p>© 2025 User Management System</p>
    </footer>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore";
import { storeToRefs } from "pinia";
import NavBar from "./components/NavBar.vue";
import AuthView from "./views/AuthView.vue";

const authStore = useAuthStore();
const { isAuthenticated } = storeToRefs(authStore);
</script>
