<template>
    <header class="glass-header">
      <div class="logo">
        <div class="logo-icon">S</div>
        <div class="logo-text">SchoolCRM</div>
      </div>
      <div class="user-menu">
        <button class="notification-btn">
          <i class="fas fa-bell"></i>
          <span class="notification-badge">3</span>
        </button>
        <div v-if="usersStore.user" class="user-profile">
          <img src="" alt="Profile" class="user-avatar" />
          <span class="user-name">{{ usersStore.user.name }}</span>
          <span class="user-role">{{ usersStore.user.role }}</span>
          <i class="fas fa-chevron-down"></i>
        </div>
        <button @click="handleLogout" class="logout-btn">
          Logout
        </button>
      </div>
    </header>
  </template>
  
  <script setup>
  import { useUsersStore } from '@/stores/usersStore'
  import { useAuthStore } from '@/stores/useAuthStore'
  import { useRouter } from 'vue-router'
  
  const usersStore = useUsersStore()
  const authStore = useAuthStore()
  const router = useRouter()
  
  function handleLogout() {
    authStore.logout()
    localStorage.removeItem('userId')
    router.push('/login')
  }
  </script>
  
  <style scoped>
/* Glass effect styles */
.glass-panel {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    border-radius: 20px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    padding: 30px;
    z-index: 1;
    position: relative;
    overflow: hidden;
    margin-bottom: 30px;
  }
  
  .glass-panel::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 50%;
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0));
    border-radius: 20px 20px 0 0;
    pointer-events: none;
  }
  
  /* Header styles */
  .glass-header {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    padding: 15px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    z-index: 5;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .logo {
    display: flex;
    align-items: center;
  }
  
  .logo-icon {
    background: rgba(255, 255, 255, 0.2);
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 24px;
    font-weight: bold;
    margin-right: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  .logo-text {
    font-size: 28px;
    font-weight: 700;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.7));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
    letter-spacing: 0.5px;
  }
  
  .user-menu {
    display: flex;
    align-items: center;
    gap: 20px;
  }

  .logout-btn {
  background: rgba(255, 255, 255, 0.1);
  border: none;
  padding: 10px 20px;
  border-radius: 50px;
  font-size: 16px;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
}

.logout-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}
  
  .notification-btn {
    background: rgba(255, 255, 255, 0.1);
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 18px;
    cursor: pointer;
    position: relative;
    transition: all 0.3s ease;
  }
  
  .notification-btn:hover {
    background: rgba(255, 255, 255, 0.2);
  }
  
  .notification-badge {
    position: absolute;
    top: -5px;
    right: -5px;
    background: #ff5252;
    color: white;
    font-size: 12px;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .user-profile {
    display: flex;
    align-items: center;
    gap: 10px;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 50px;
    background: rgba(255, 255, 255, 0.1);
    transition: all 0.3s ease;
  }
  
  .user-profile:hover {
    background: rgba(255, 255, 255, 0.2);
  }
  
  .user-avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    object-fit: cover;
    border: 2px solid rgba(255, 255, 255, 0.3);
  }
  
  .user-name {
    font-weight: 500;
    font-size: 16px;
  }
  </style>