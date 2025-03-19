<template>
    <nav class="main-nav" :class="{ 'sticky-nav': isSticky }">
      <div class="nav-content">
        <!-- Logo (always visible) -->
        <div class="logo">
          <span class="logo-icon">𝑼</span>
          <span class="logo-text">UserManager</span>
        </div>
        
        <!-- Connected state: middle navigation buttons -->
        <div v-if="connected" class="center-nav">
          <button class="nav-button">Button 1</button>
          <button class="nav-button">Button 2</button>
          <button class="nav-button">Button 3</button>
        </div>
        
        <!-- Connected state: profile dropdown -->
        <div v-if="connected" class="user-dropdown">
          <div class="user-menu" @click="toggleDropdown">
            <div class="user-avatar">A</div>
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
              
              <a href="#" class="dropdown-item">
                <span class="dropdown-item-icon profile-icon"></span>
                <span>Profile</span>
              </a>
              
              <a href="#" class="dropdown-item">
                <span class="dropdown-item-icon settings-icon"></span>
                <span>Settings</span>
              </a>
              
              <div class="dropdown-divider"></div>
              
              <a href="#" @click.prevent="toggleDarkMode" class="dropdown-item">
                <span class="dropdown-item-icon" :class="isDarkMode ? 'light-mode-icon' : 'dark-mode-icon'"></span>
                <span>{{ isDarkMode ? 'Light Mode' : 'Dark Mode' }}</span>
              </a>
              
              <div class="dropdown-divider"></div>
              
              <a href="#" @click.prevent="logout" class="dropdown-item">
                <span class="dropdown-item-icon logout-icon"></span>
                <span>Logout</span>
              </a>
            </div>
          </transition>
        </div>
        
        <!-- Placeholder for disconnected state -->
        <div v-else class="placeholder-right"></div>
      </div>
    </nav>
    
    <div v-if="isDropdownOpen" class="click-away-layer" @click="closeDropdown"></div>
  </template>
  
  <script setup>
  import { ref, defineProps, defineEmits } from 'vue';
  
  const props = defineProps({
    connected: {
      type: Boolean,
      default: false
    },
    isSticky: {
      type: Boolean,
      default: true
    }
  });
  
  const emit = defineEmits(['logout', 'toggleDarkMode']);
  
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
    emit('toggleDarkMode', isDarkMode.value);
  };
  
  const logout = () => {
    emit('logout');
    closeDropdown();
  };
  </script>
  
  <style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');
  
  .main-nav {
    background: rgba(41, 128, 185, 0.8);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    padding: 15px 20px;
    box-shadow: 0 2px 15px rgba(0, 0, 0, 0.15);
    border-radius: 15px;
    margin-left: 12.5%;
    margin-right: 12.5%;
    z-index: 100;
    border: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .main-nav::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 50%;
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0.15), rgba(255, 255, 255, 0));
    border-radius: 15px 15px 0 0;
    pointer-events: none;
  }
  
  .sticky-nav {
    position: sticky;
    top: 15px;
  }
  
  .nav-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
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
  
  /* Center navigation buttons */
  .center-nav {
    display: flex;
    gap: 15px;
    justify-content: center;
  }
  
  .nav-button {
    color: white;
    background-color: rgba(255, 255, 255, 0.1);
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.2s ease;
  }
  
  .nav-button:hover {
    background-color: rgba(255, 255, 255, 0.2);
  }
  
  /* Placeholder for disconnected state */
  .placeholder-right {
    width: 40px;
  }
  
  /* User dropdown styles */
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
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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
  
  /* Animations */
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