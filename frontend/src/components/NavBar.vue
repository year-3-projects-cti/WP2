<template>
  <nav class="navbar">
    <div class="navbar-logo">
      <h1>UserManager</h1>
    </div>
    <div class="navbar-links">
      <button v-if="authStore.isAuthenticated" @click="handleLogout" class="logout-btn">
        Logout
      </button>
    </div>
  </nav>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

function handleLogout() {
  authStore.logout();
  router.push("/login");
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: #2980b9;
  color: white;
}

.logout-btn {
  background: white;
  color: #2980b9;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s;
}

.logout-btn:hover {
  background: #f0f0f0;
}
</style>
