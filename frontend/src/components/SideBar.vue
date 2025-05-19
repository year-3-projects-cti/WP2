<template>
  <nav class="sidebar">
    <ul class="menu-list">
      <li 
        v-for="item in filteredMenu" 
        :key="item.path" 
        :class="{ active: isActive(item.path) }"
      >
        <router-link :to="item.path">
          <i :class="item.icon"></i>
          <span>{{ item.label }}</span>
          <span v-if="item.badge" class="badge">{{ item.badge }}</span>
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import { useRoute } from 'vue-router';
import { useUsersStore } from '@/stores/usersStore';
import { computed } from 'vue';

const route = useRoute();
const usersStore = useUsersStore();

const menuItems = [
  { path: '/interface', label: 'Dashboard', icon: 'fas fa-house-user', roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { path: '/students', label: 'Students', icon: 'fas fa-users', roles: ['ADMIN'] },
  { path: '/teachers', label: 'Teachers', icon: 'fas fa-user-tie', roles: ['ADMIN'] },
  { path: '/courses', label: 'Courses', icon: 'fas fa-book-open', roles: ['ADMIN', 'TEACHER'] },
  { path: '/calendar', label: 'Calendar', icon: 'fas fa-calendar-check', roles: ['TEACHER', 'STUDENT'] },
  { path: '/payments', label: 'Payments', icon: 'fas fa-wallet', roles: ['STUDENT'] },
  { path: '/reports', label: 'Reports', icon: 'fas fa-chart-pie', roles: ['ADMIN'] },
  { path: '/messages', label: 'Messages', icon: 'fas fa-envelope-open-text', badge: 5, roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { path: '/settings', label: 'Settings', icon: 'fas fa-sliders-h', roles: ['ADMIN', 'TEACHER', 'STUDENT'] }
];

const filteredMenu = computed(() => {
  if (!usersStore.user) return [];
  const role = usersStore.user.role || '';
  return menuItems.filter(item => item.roles.includes(role));
});

function isActive(path) {
  return route.path.startsWith(path);
}
</script>

<style scoped>
.sidebar {
  width: 220px;
  background: #1e2a38;
  color: #fff;
  height: 100vh;
  padding-top: 20px;
  display: flex;
  flex-direction: column;
}

.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-list li {
  margin-bottom: 10px;
}

.menu-list li a {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  color: #cfd8dc;
  text-decoration: none;
  transition: background 0.2s;
}

.menu-list li a i {
  margin-right: 10px;
}

.menu-list li a .badge {
  background: red;
  color: white;
  border-radius: 50%;
  padding: 2px 8px;
  margin-left: auto;
  font-size: 12px;
}

.menu-list li.active a {
  background: #32475b;
  color: #fff;
}
</style>
