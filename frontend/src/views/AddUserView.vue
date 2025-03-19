<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const showNotification = ref(false);
const lastAddedUser = ref({});
const user = ref({
  name: "",
  email: "",
  role: "user",
});

const addUser = async () => {
  try {
    const response = await fetch("http://localhost:8080/api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user.value),
    });

    if (!response.ok) {
      throw new Error("Failed to add user");
    }

    const newUser = await response.json();
    lastAddedUser.value = newUser;

    user.value = { name: "", email: "", role: "user" };
    showNotification.value = true;
  } catch (error) {
    console.error("Error adding user:", error);
  }
};

const closeNotification = () => {
  showNotification.value = false;
};

const viewUsers = () => {
  showNotification.value = false;
  router.push("/");
};
</script>


<template>
    <div class="add-user-container">
      <div class="form-header">
        <h2>Adaugă Utilizator</h2>
        <div class="underline"></div>
      </div>
      
      <form @submit.prevent="addUser" class="form-content">
        <div class="form-group">
          <label for="name">Nume</label>
          <div class="input-container">
            <i class="icon user-icon"></i>
            <input v-model="user.name" type="text" id="name" required placeholder="Introdu numele complet" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="email">Email</label>
          <div class="input-container">
            <i class="icon email-icon"></i>
            <input v-model="user.email" type="email" id="email" required placeholder="Introdu adresa de email" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="role">Rol</label>
          <div class="input-container">
            <i class="icon role-icon"></i>
            <select v-model="user.role" id="role">
              <option value="admin">Admin</option>
              <option value="user">Utilizator</option>
            </select>
          </div>
        </div>
        
        <button type="submit" class="btn-submit">
          <span>Adaugă</span>
          <i class="arrow-icon"></i>
        </button>
      </form>
    </div>
    
    <transition name="notification-fade">
      <div v-if="showNotification" class="fullpage-notification">
        <div class="notification-card">
          <div class="notification-icon">✅</div>
          <h3>Utilizator adăugat cu succes!</h3>
          <p>Utilizatorul <strong>{{ lastAddedUser.name }}</strong> a fost adăugat în sistem.</p>
          <div class="notification-actions">
            <button @click="closeNotification" class="btn-close">Închide</button>
            <button @click="viewUsers" class="btn-view">Vezi lista utilizatori</button>
          </div>
        </div>
      </div>
    </transition>
  </template>
  
  <style scoped>
  .add-user-container {
    max-width: 450px;
    margin: 40px auto;
    padding: 30px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
    position: relative;
    overflow: hidden;
  }
  
  .add-user-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 6px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
  }
  
  .form-header {
    text-align: center;
    margin-bottom: 30px;
  }
  
  h2 {
    font-size: 28px;
    color: #333;
    margin-bottom: 10px;
    font-weight: 600;
  }
  
  .underline {
    height: 4px;
    width: 60px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
    margin: 0 auto;
    border-radius: 2px;
  }
  
  .form-content {
    margin-top: 25px;
  }
  
  .form-group {
    margin-bottom: 24px;
  }
  
  label {
    display: block;
    font-weight: 500;
    margin-bottom: 8px;
    color: #555;
    font-size: 16px;
  }
  
  .input-container {
    position: relative;
    display: flex;
    align-items: center;
  }
  
  .icon {
    width: 20px;
    height: 20px;
    position: absolute;
    left: 12px;
    opacity: 0.6;
  }
  
  .user-icon::before {
    content: '👤';
  }
  
  .email-icon::before {
    content: '✉️';
  }
  
  .role-icon::before {
    content: '🔑';
  }
  
  input, select {
    width: 100%;
    padding: 14px 14px 14px 40px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    font-size: 15px;
    transition: all 0.3s ease;
    background-color: #f9f9f9;
  }
  
  input:focus, select:focus {
    border-color: #3498db;
    box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.1);
    outline: none;
    background-color: #fff;
  }
  
  input::placeholder {
    color: #aaa;
  }
  
  .btn-submit {
    width: 100%;
    padding: 14px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s ease;
    box-shadow: 0 4px 6px rgba(52, 152, 219, 0.2);
  }
  
  .btn-submit:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(52, 152, 219, 0.3);
  }
  
  .btn-submit:active {
    transform: translateY(0);
  }
  
  .arrow-icon {
    margin-left: 8px;
  }
  
  .arrow-icon::after {
    content: '→';
  }
  
  .fullpage-notification {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
  }
  
  .notification-card {
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
    text-align: center;
    max-width: 500px;
    width: 90%;
    animation: popup 0.5s ease-out;
  }
  
  .notification-icon {
    font-size: 48px;
    margin-bottom: 10px;
  }
  
  .notification-card h3 {
    font-size: 24px;
    margin-bottom: 15px;
    color: #2ecc71;
  }
  
  .notification-card p {
    font-size: 16px;
    margin-bottom: 25px;
    color: #555;
  }
  
  .notification-actions {
    display: flex;
    justify-content: center;
    gap: 15px;
  }
  
  .btn-close, .btn-view {
    padding: 12px 20px;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .btn-close {
    background-color: #f5f5f5;
    color: #555;
    border: 1px solid #ddd;
  }
  
  .btn-view {
    background: linear-gradient(90deg, #3498db, #2980b9);
    color: white;
    border: none;
    box-shadow: 0 4px 6px rgba(52, 152, 219, 0.2);
  }
  
  .btn-close:hover {
    background-color: #eee;
  }
  
  .btn-view:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(52, 152, 219, 0.3);
  }
  
  @keyframes popup {
    0% {
      transform: scale(0.8);
      opacity: 0;
    }
    100% {
      transform: scale(1);
      opacity: 1;
    }
  }
  
  .notification-fade-enter-active, .notification-fade-leave-active {
    transition: opacity 0.5s;
  }
  
  .notification-fade-enter-from, .notification-fade-leave-to {
    opacity: 0;
  }
  
  @media (max-width: 500px) {
    .add-user-container {
      max-width: 100%;
      margin: 20px;
      padding: 20px;
    }
    
    h2 {
      font-size: 24px;
    }
    
    input, select, .btn-submit {
      padding: 12px;
    }
    
    .notification-card {
      padding: 20px;
    }
  }
  </style>