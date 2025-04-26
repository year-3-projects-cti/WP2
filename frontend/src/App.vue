<template>
  <div class="app-wrapper">

    <router-view v-if="!isAuthenticated" />
    <AuthView v-else />

    <footer class="app-footer">
      <p>© 2025 User Management System</p>
    </footer>
  </div>
</template>

<script setup>
import { ref } from "vue";
import NavBar from "./components/NavBar.vue";
import AuthView from "./views/AuthView.vue";

const isAuthenticated = ref(false);

</script>
