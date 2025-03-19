<template>
    <div class="settings-container">
      <div class="settings-header">
        <h2>Setări Aplicație</h2>
        <div class="settings-underline"></div>
        <p class="settings-description">Personalizează experiența ta în aplicație</p>
      </div>
      
      <div class="settings-sections">
        <div class="settings-section">
          <h3>Interfață</h3>
          
          <div class="setting-card">
            <div class="setting-info">
              <div class="setting-icon dark-mode-icon"></div>
              <div class="setting-details">
                <h4>Mod Întunecat</h4>
                <p>Schimbă aspectul aplicației pentru a reduce oboseala ochilor</p>
              </div>
            </div>
            <label class="toggle">
              <input type="checkbox" v-model="darkMode">
              <span class="toggle-slider"></span>
            </label>
          </div>
          
          <div class="setting-card">
            <div class="setting-info">
              <div class="setting-icon animation-icon"></div>
              <div class="setting-details">
                <h4>Animații</h4>
                <p>Activează sau dezactivează animațiile din interfață</p>
              </div>
            </div>
            <label class="toggle">
              <input type="checkbox" v-model="animations">
              <span class="toggle-slider"></span>
            </label>
          </div>
        </div>
        
        <div class="settings-section">
          <h3>Notificări</h3>
          
          <div class="setting-card">
            <div class="setting-info">
              <div class="setting-icon notification-icon"></div>
              <div class="setting-details">
                <h4>Notificări Push</h4>
                <p>Primește notificări despre actualizări și evenimente</p>
              </div>
            </div>
            <label class="toggle">
              <input type="checkbox" v-model="notifications">
              <span class="toggle-slider"></span>
            </label>
          </div>
          
          <div class="setting-card">
            <div class="setting-info">
              <div class="setting-icon email-icon"></div>
              <div class="setting-details">
                <h4>Notificări Email</h4>
                <p>Primește actualizări importante prin email</p>
              </div>
            </div>
            <label class="toggle">
              <input type="checkbox" v-model="emailNotifications">
              <span class="toggle-slider"></span>
            </label>
          </div>
        </div>
        
        <div class="settings-section">
          <h3>Securitate</h3>
          
          <div class="setting-card">
            <div class="setting-info">
              <div class="setting-icon security-icon"></div>
              <div class="setting-details">
                <h4>Autentificare în doi pași</h4>
                <p>Adaugă un nivel suplimentar de securitate contului tău</p>
              </div>
            </div>
            <label class="toggle">
              <input type="checkbox" v-model="twoFactorAuth">
              <span class="toggle-slider"></span>
            </label>
          </div>
        </div>
      </div>
      
      <div class="settings-actions">
        <button @click="resetSettings" class="btn-reset">Resetează</button>
        <button @click="saveSettings" class="btn-save">
          <span>Salvează setările</span>
          <span class="save-icon"></span>
        </button>
      </div>
      
      <transition name="fade">
        <div v-if="showNotification" class="save-notification">
          <div class="notification-icon-success"></div>
          <p>Setările au fost salvate cu succes!</p>
        </div>
      </transition>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const darkMode = ref(false);
  const animations = ref(true);
  const notifications = ref(true);
  const emailNotifications = ref(false);
  const twoFactorAuth = ref(false);
  const showNotification = ref(false);
  
  const saveSettings = () => {
    console.log('Setări salvate:', { 
      darkMode: darkMode.value, 
      animations: animations.value,
      notifications: notifications.value,
      emailNotifications: emailNotifications.value,
      twoFactorAuth: twoFactorAuth.value
    });
    
    showNotification.value = true;
    setTimeout(() => {
      showNotification.value = false;
    }, 3000);
  };
  
  const resetSettings = () => {
    darkMode.value = false;
    animations.value = true;
    notifications.value = true;
    emailNotifications.value = false;
    twoFactorAuth.value = false;
  };
  </script>
  
  <style scoped>
  .settings-container {
    max-width: 700px;
    margin: 0 auto;
    padding: 30px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
    position: relative;
  }
  
  .settings-header {
    text-align: center;
    margin-bottom: 30px;
  }
  
  h2 {
    font-size: 28px;
    color: #333;
    margin-bottom: 10px;
    font-weight: 600;
  }
  
  .settings-underline {
    height: 4px;
    width: 60px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
    margin: 0 auto;
    border-radius: 2px;
  }
  
  .settings-description {
    color: #666;
    font-size: 16px;
    margin-top: 10px;
  }
  
  .settings-sections {
    display: flex;
    flex-direction: column;
    gap: 25px;
  }
  
  .settings-section {
    background: #f9f9f9;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.03);
  }
  
  .settings-section h3 {
    font-size: 18px;
    color: #333;
    margin-bottom: 15px;
    font-weight: 500;
    display: flex;
    align-items: center;
  }
  
  .setting-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    background: white;
    border-radius: 8px;
    margin-bottom: 12px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.03);
  }
  
  .setting-card:last-child {
    margin-bottom: 0;
  }
  
  .setting-info {
    display: flex;
    align-items: center;
    gap: 15px;
    flex: 1;
  }
  
  .setting-icon {
    width: 36px;
    height: 36px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: white;
  }
  
  .dark-mode-icon {
    background: linear-gradient(135deg, #2c3e50, #34495e);
  }
  
  .dark-mode-icon::before {
    content: '🌙';
  }
  
  .animation-icon {
    background: linear-gradient(135deg, #8e44ad, #9b59b6);
  }
  
  .animation-icon::before {
    content: '✨';
  }
  
  .notification-icon {
    background: linear-gradient(135deg, #e74c3c, #c0392b);
  }
  
  .notification-icon::before {
    content: '🔔';
  }
  
  .email-icon {
    background: linear-gradient(135deg, #3498db, #2980b9);
  }
  
  .email-icon::before {
    content: '✉️';
  }
  
  .security-icon {
    background: linear-gradient(135deg, #27ae60, #2ecc71);
  }
  
  .security-icon::before {
    content: '🔒';
  }
  
  .setting-details {
    flex: 1;
  }
  
  .setting-details h4 {
    font-size: 16px;
    color: #333;
    margin: 0 0 5px 0;
    font-weight: 500;
  }
  
  .setting-details p {
    font-size: 14px;
    color: #666;
    margin: 0;
  }
  
  /* Toggle Switch */
  .toggle {
    position: relative;
    display: inline-block;
    width: 52px;
    height: 26px;
  }
  
  .toggle input { 
    opacity: 0;
    width: 0;
    height: 0;
  }
  
  .toggle-slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    transition: 0.4s;
    border-radius: 34px;
  }
  
  .toggle-slider:before {
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    left: 3px;
    bottom: 3px;
    background-color: white;
    transition: 0.4s;
    border-radius: 50%;
  }
  
  input:checked + .toggle-slider {
    background: linear-gradient(90deg, #3498db, #9b59b6);
  }
  
  input:checked + .toggle-slider:before {
    transform: translateX(26px);
  }
  
  .settings-actions {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 30px;
  }
  
  .btn-reset {
    padding: 12px 20px;
    background-color: #f5f5f5;
    color: #555;
    border: 1px solid #ddd;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.2s ease;
  }
  
  .btn-reset:hover {
    background-color: #eee;
  }
  
  .btn-save {
    padding: 12px 24px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    display: flex;
    align-items: center;
    transition: all 0.3s ease;
    box-shadow: 0 3px 6px rgba(52, 152, 219, 0.2);
  }
  
  .btn-save:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(52, 152, 219, 0.3);
  }
  
  .save-icon {
    margin-left: 8px;
  }
  
  .save-icon::after {
    content: '✓';
  }
  
  .save-notification {
    position: fixed;
    bottom: 20px;
    right: 20px;
    background: #2ecc71;
    color: white;
    padding: 15px 20px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    box-shadow: 0 4px 15px rgba(46, 204, 113, 0.3);
    z-index: 1000;
  }
  
  .notification-icon-success {
    margin-right: 10px;
  }
  
  .notification-icon-success::before {
    content: '✅';
  }
  
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.5s, transform 0.5s;
  }
  
  .fade-enter-from, .fade-leave-to {
    opacity: 0;
    transform: translateY(20px);
  }
  
  @media (max-width: 600px) {
    .settings-container {
      padding: 20px;
      margin: 0 10px;
    }
    
    .setting-info {
      gap: 10px;
    }
    
    .setting-details h4 {
      font-size: 15px;
    }
    
    .setting-details p {
      font-size: 13px;
    }
  }
  </style>