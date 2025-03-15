<template>
  <div class="app-container">
    <nav class="main-nav">
      <div class="nav-content">
        <div class="logo">
          <span class="logo-icon">𝑼</span>
          <span class="logo-text">UserManager</span>
        </div>
        
        <div class="nav-links">
          <router-link to="/" class="nav-link">
            <span class="link-icon">👥</span>
            <span class="link-text">Lista Utilizatori</span>
          </router-link>
          <router-link to="/add" class="nav-link">
            <span class="link-icon">➕</span>
            <span class="link-text">Adaugă Utilizator</span>
          </router-link>
        </div>
        
        <div class="user-dropdown">
          <div class="user-menu" @click="toggleDropdown">
            <div class="user-avatar">A</div>
            <span class="user-name">Admin</span>
            <span class="dropdown-icon" :class="{ 'dropdown-open': isDropdownOpen }"></span>
          </div>
          
          <transition name="dropdown">
            <div v-if="isDropdownOpen" class="dropdown-menu">
              <div class="dropdown-header">
                <div class="dropdown-avatar">A</div>
                <div class="dropdown-user-info">
                  <div class="dropdown-user-name">Admin</div>
                  <div class="dropdown-user-email">admin@usermanager.com</div>
                </div>
              </div>
              
              <div class="dropdown-divider"></div>
              
              <router-link to="/profile" class="dropdown-item">
                <span class="dropdown-item-icon profile-icon"></span>
                <span>Profil utilizator</span>
              </router-link>
              
              <router-link to="/settings" class="dropdown-item">
                <span class="dropdown-item-icon settings-icon"></span>
                <span>Setări</span>
              </router-link>
              
              <div class="dropdown-divider"></div>
              
              <a href="#" @click.prevent="toggleDarkMode" class="dropdown-item">
                <span class="dropdown-item-icon" :class="isDarkMode ? 'light-mode-icon' : 'dark-mode-icon'"></span>
                <span>{{ isDarkMode ? 'Mod Luminos' : 'Mod Întunecat' }}</span>
              </a>
              
              <div class="dropdown-divider"></div>
              
              <a href="#" @click.prevent="logout" class="dropdown-item">
                <span class="dropdown-item-icon logout-icon"></span>
                <span>Deconectare</span>
              </a>
            </div>
          </transition>
        </div>
      </div>
    </nav>
    
    <main class="main-content">
      <transition name="fade" mode="out-in">
        <router-view />
      </transition>
    </main>
    <footer class="app-footer">
      <p>© 2025 User Management System</p>
    </footer>
    
    <div v-if="isDropdownOpen" class="click-away-layer" @click="closeDropdown"></div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const isDropdownOpen = ref(false);
const isDarkMode = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const closeDropdown = () => {
  isDropdownOpen.value = false;
};

const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value;
  document.body.classList.toggle('dark-theme', isDarkMode.value);
};

const logout = () => {
  alert('Ai fost deconectat!');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  font-family: 'Roboto', sans-serif;
}

.main-nav {
  background: linear-gradient(90deg, #2980b9, #3498db);
  padding: 0;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 70px;
}

.logo {
  display: flex;
  align-items: center;
  font-weight: 700;
  font-size: 24px;
  color: white;
  text-decoration: none;
  letter-spacing: 0.5px;
}

.logo-icon {
  background: rgba(255, 255, 255, 0.2);
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  font-size: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.logo-text {
  background: linear-gradient(to right, #fff, rgba(255, 255, 255, 0.7));
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
}

.nav-links {
  display: flex;
  gap: 5px;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 10px 15px;
  border-radius: 6px;
  font-weight: 500;
  display: flex;
  align-items: center;
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
}

.nav-link::before {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 3px;
  background-color: white;
  transition: all 0.3s ease;
  transform: translateX(-50%);
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.nav-link:hover::before {
  width: 80%;
}

.router-link-active {
  background-color: rgba(255, 255, 255, 0.15);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.router-link-active::before {
  width: 80%;
}

.link-icon {
  margin-right: 8px;
  font-size: 18px;
}

/* User Menu & Dropdown */
.user-dropdown {
  position: relative;
}

.user-menu {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.1);
  transition: all 0.2s ease;
}

.user-menu:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: linear-gradient(45deg, #e74c3c, #e67e22);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  margin-right: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.user-name {
  color: white;
  font-weight: 500;
}

.dropdown-icon {
  margin-left: 8px;
  width: 12px;
  height: 12px;
  position: relative;
  transition: transform 0.3s ease;
}

.dropdown-icon::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 5px solid white;
  transform: translateY(-50%);
}

.dropdown-icon.dropdown-open {
  transform: rotate(180deg);
}

.dropdown-menu {
  position: absolute;
  top: calc(100% + 10px);
  right: 0;
  width: 280px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  z-index: 101;
}

.dropdown-header {
  display: flex;
  align-items: center;
  padding: 20px;
  background: linear-gradient(90deg, #f5f7fa, #f7f9fc);
}

.dropdown-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: linear-gradient(45deg, #e74c3c, #e67e22);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  font-size: 20px;
  margin-right: 15px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
}

.dropdown-user-info {
  flex: 1;
}

.dropdown-user-name {
  font-weight: 600;
  font-size: 16px;
  color: #333;
  margin-bottom: 3px;
}

.dropdown-user-email {
  font-size: 13px;
  color: #666;
}

.dropdown-divider {
  height: 1px;
  background-color: #eee;
  margin: 2px 0;
}

.dropdown-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  color: #333;
  text-decoration: none;
  transition: all 0.2s ease;
  font-size: 14px;
}

.dropdown-item:hover {
  background-color: #f5f7fa;
}

.dropdown-item-icon {
  width: 18px;
  height: 18px;
  margin-right: 12px;
  opacity: 0.8;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-icon::before {
  content: '👤';
}

.settings-icon::before {
  content: '⚙️';
}

.dark-mode-icon::before {
  content: '🌙';
}

.light-mode-icon::before {
  content: '☀️';
}

.logout-icon::before {
  content: '🚪';
}

.click-away-layer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
}

.main-content {
  flex: 1;
  padding: 20px;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
}

.app-footer {
  background-color: #f5f5f5;
  color: #7f8c8d;
  text-align: center;
  padding: 15px;
  font-size: 14px;
  border-top: 1px solid #eee;
}

/* Transition effects for router-view */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

/* Dropdown animation */
.dropdown-enter-active,
.dropdown-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>