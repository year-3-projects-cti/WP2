<template>
    <aside class="sidebar">
      <nav>
        <div class="sidebar-header">
          <h3>Menu</h3>
        </div>
        <ul class="sidebar-menu">
          <li class="menu-item" :class="{ active: isActive('/interface') }">
            <router-link to="/interface">
              <i class="fas fa-tachometer-alt"></i>
              <span>Dashboard</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/students') }">
            <router-link to="/students">
              <i class="fas fa-user-graduate"></i>
              <span>Students</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/teachers') }">
            <router-link to="/teachers">
              <i class="fas fa-chalkboard-teacher"></i>
              <span>Teachers</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/classes') }">
            <router-link to="/classes">
              <i class="fas fa-chalkboard"></i>
              <span>Classes</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/calendar') }">
            <router-link to="/calendar">
              <i class="fas fa-calendar-alt"></i>
              <span>Calendar</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/payments') }">
            <router-link to="/payments">
              <i class="fas fa-credit-card"></i>
              <span>Payments</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/reports') }">
            <router-link to="/reports">
              <i class="fas fa-chart-line"></i>
              <span>Reports</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/messages') }">
            <router-link to="/messages">
              <i class="fas fa-comments"></i>
              <span>Messages</span>
              <span class="menu-badge">5</span>
            </router-link>
          </li>
          <li class="menu-item" :class="{ active: isActive('/settings') }">
            <router-link to="/settings">
              <i class="fas fa-cog"></i>
              <span>Settings</span>
            </router-link>
          </li>
        </ul>
      </nav>
    </aside>
  </template>
  
  <script setup>
  import { useRoute } from 'vue-router'
  
  const route = useRoute()
  
  function isActive(path) {
    return route.path.startsWith(path)
  }
  </script>

<style scoped>
/* Sidebar styles */
.sidebar {
  width: 260px;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px 0;
  z-index: 4;
  transition: all 0.3s ease;
}

.sidebar-header {
  padding: 0 20px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  margin-bottom: 20px;
}

.sidebar-header h3 {
  font-size: 18px;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
}

.sidebar-menu {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-item {
  position: relative;
}

.menu-item a {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
  font-size: 15px;
}

.menu-item a i {
  margin-right: 15px;
  font-size: 18px;
  width: 20px;
  text-align: center;
}

.menu-item a:hover {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.menu-item.active a {
  background: rgba(255, 255, 255, 0.15);
  color: white;
  border-left: 4px solid white;
}

.menu-badge {
  background: #ff5252;
  color: white;
  font-size: 12px;
  border-radius: 50px;
  padding: 2px 8px;
  margin-left: auto;
}
</style>