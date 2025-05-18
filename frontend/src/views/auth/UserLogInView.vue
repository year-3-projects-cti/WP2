<template>
    <div class="login-container">
      <!-- Animated background elements -->
      <div class="animated-bg">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="circle circle-3"></div>
      </div>
      
      <!-- Main content -->
      <div class="content-wrapper">
        <!-- Login form panel -->
        <div class="login-panel glass-panel">
          <div class="back-link" @click="goBack">
            <span class="back-icon">←</span>
            Back to home
          </div>
          
          <h1 class="login-title">Log In to UserManager</h1>
          <p class="login-subtitle">Access your dashboard and manage your users</p>
          
          <form class="login-form" @submit.prevent="handleLogin">
            <div class="form-group">
              <label for="email">Email</label>
              <div class="input-wrapper">
                <span class="input-icon">✉️</span>
                <input 
                  type="email" 
                  id="email" 
                  v-model="email" 
                  placeholder="your@email.com" 
                  required
                />
              </div>
            </div>
            
            <div class="form-group">
              <label for="password">Password</label>
              <div class="input-wrapper">
                <span class="input-icon">🔒</span>
                <input 
                  :type="showPassword ? 'text' : 'password'" 
                  id="password" 
                  v-model="password" 
                  placeholder="Your password" 
                  required
                />
                <button 
                  type="button" 
                  class="toggle-password" 
                  @click="showPassword = !showPassword"
                >
                  {{ showPassword ? '👁️' : '👁️‍🗨️' }}
                </button>
              </div>
            </div>
            
            <div class="form-options">
              <div class="remember-me">
                <input type="checkbox" id="remember" v-model="rememberMe" />
                <label for="remember">Remember me</label>
              </div>
              <a href="#" class="forgot-password">Forgot password?</a>
            </div>
            
            <button type="submit" class="btn-login">
              <span class="btn-icon">🔑</span>
              Log In
            </button>
            
            <div class="login-divider">
              <span>or continue with</span>
            </div>
            
            <div class="social-login">
              <button type="button" class="btn-social btn-google">
                G
              </button>
              <button type="button" class="btn-social btn-apple">
                A
              </button>
              <button type="button" class="btn-social btn-github">
                GH
              </button>
            </div>
          </form>
        </div>
        
        <!-- Right side with info -->
        <div class="info-panel glass-panel">
          <div class="welcome-back">
            <h2>Welcome Back!</h2>
            <p>Log in to access your UserManager dashboard and take control of your user management system.</p>
          </div>
          
          <div class="login-benefits">
            <div class="benefit-item">
              <div class="benefit-icon">📊</div>
              <div class="benefit-details">
                <h3>Real-time Analytics</h3>
                <p>Access up-to-date user analytics and activity metrics</p>
              </div>
            </div>
            
            <div class="benefit-item">
              <div class="benefit-icon">🔔</div>
              <div class="benefit-details">
                <h3>Instant Notifications</h3>
                <p>Get alerted about important user activities and events</p>
              </div>
            </div>
            
            <div class="benefit-item">
              <div class="benefit-icon">⚙️</div>
              <div class="benefit-details">
                <h3>Advanced Configuration</h3>
                <p>Customize your user management settings and workflows</p>
              </div>
            </div>
          </div>
          
          <div class="signup-prompt">
            <p>Don't have an account yet?</p>
            <button class="btn-register" @click="goToRegister">
              <span class="btn-icon">✨</span>
              Create Account
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/useAuthStore';
import { useUsersStore } from '@/stores/usersStore'; // 🔥 adăugăm importul usersStore
import axios from 'axios';

const router = useRouter();
const authStore = useAuthStore();
const usersStore = useUsersStore(); // 🔥 instanțiem usersStore

const email = ref('');
const password = ref('');
const showPassword = ref(false);
const rememberMe = ref(false);

async function handleLogin() {
  try {
    const response = await axios.post('http://localhost:8080/api/users/login', {
      email: email.value,
      password: password.value
    });

    if (response.status !== 200) {
      throw new Error('Login failed');
    }

    const user = response.data;

    if (!user || !user.role) {
      throw new Error('Invalid user data received');
    }

    authStore.login();
    usersStore.user = user;
    localStorage.setItem('userId', user.id);

    switch (user.role) {
      case 'ADMIN':
        router.push('/interface');
        break;
      case 'TEACHER':
        router.push('/courses');
        break;
      case 'STUDENT':
        router.push('/calendar');
        break;
      default:
        alert('Invalid user role.');
        router.push('/');
    }

  } catch (error) {
    console.error('Login failed:', error.response?.data || error.message);
    alert('Invalid email or password.');
  }
}

function goBack() {
  router.push('/');
}

function goToRegister() {
  router.push('/register');
}
</script>

  
  
  
  <style scoped>
  /* Base styles */
  .login-container {
    min-height: 100vh;
    width: 100%;
    overflow-x: hidden;
    background: linear-gradient(135deg, #2980b9, #6dd5fa);
    color: #fff;
    font-family: 'Roboto', sans-serif;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  /* Reuse animated background from main template */
  .animated-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
    z-index: 0;
  }
  
  .circle {
    position: absolute;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.1);
    animation: float 15s infinite ease-in-out;
  }
  
  .circle-1 {
    width: 400px;
    height: 400px;
    top: -100px;
    right: -100px;
    animation-delay: 0s;
  }
  
  .circle-2 {
    width: 600px;
    height: 600px;
    bottom: -300px;
    left: -200px;
    animation-delay: -5s;
  }
  
  .circle-3 {
    width: 300px;
    height: 300px;
    top: 40%;
    right: 25%;
    animation-delay: -2s;
  }
  
  @keyframes float {
    0%, 100% {
      transform: translateY(0) scale(1);
    }
    50% {
      transform: translateY(-20px) scale(1.05);
    }
  }
  
  /* Content wrapper */
  .content-wrapper {
    display: flex;
    gap: 30px;
    max-width: 1200px;
    width: 100%;
    padding: 20px;
    z-index: 1;
    position: relative;
  }
  
  /* Glass effect styles */
  .glass-panel {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    border-radius: 20px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    padding: 40px;
    z-index: 1;
    position: relative;
    overflow: hidden;
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
  
  /* Login panel styles */
  .login-panel {
    flex: 1;
    animation: fadeSlideIn 0.8s ease-out;
  }
  
  .back-link {
    display: flex;
    align-items: center;
    margin-bottom: 30px;
    cursor: pointer;
    width: fit-content;
    transition: opacity 0.3s ease;
    font-size: 14px;
  }
  
  .back-link:hover {
    opacity: 0.8;
  }
  
  .back-icon {
    margin-right: 8px;
    font-size: 16px;
  }
  
  .login-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 12px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .login-subtitle {
    font-size: 16px;
    margin-bottom: 30px;
    opacity: 0.9;
  }
  
  /* Form styles */
  .login-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  
  .form-group label {
    font-size: 14px;
    font-weight: 500;
  }
  
  .input-wrapper {
    display: flex;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    transition: all 0.3s ease;
    overflow: hidden;
  }
  
  .input-wrapper:focus-within {
    border-color: rgba(255, 255, 255, 0.4);
    box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.1);
  }
  
  .input-icon {
    padding: 0 12px;
    font-size: 16px;
  }
  
  .input-wrapper input {
    flex: 1;
    background: transparent;
    border: none;
    color: white;
    padding: 14px 12px 14px 0;
    font-size: 16px;
    outline: none;
  }
  
  .input-wrapper input::placeholder {
    color: rgba(255, 255, 255, 0.6);
  }
  
  .toggle-password {
    background: transparent;
    border: none;
    cursor: pointer;
    padding: 0 16px;
    color: white;
    font-size: 16px;
  }
  
  .form-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
  }
  
  .remember-me {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  
  .forgot-password {
    color: white;
    opacity: 0.9;
    text-decoration: none;
    transition: opacity 0.3s ease;
  }
  
  .forgot-password:hover {
    opacity: 1;
    text-decoration: underline;
  }
  
  .btn-login {
    padding: 16px;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    border: none;
    background: white;
    color: #2980b9;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    margin-top: 10px;
  }
  
  .btn-login:hover {
    background: #f0f0f0;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
  
  .btn-icon {
    margin-right: 8px;
    font-size: 18px;
  }
  
  /* Login divider */
  .login-divider {
    display: flex;
    align-items: center;
    margin: 10px 0;
    color: rgba(255, 255, 255, 0.8);
    font-size: 14px;
  }
  
  .login-divider::before,
  .login-divider::after {
    content: '';
    flex: 1;
    height: 1px;
    background: rgba(255, 255, 255, 0.3);
  }
  
  .login-divider span {
    padding: 0 10px;
  }
  
  /* Social login buttons */
  .social-login {
    display: flex;
    justify-content: center;
    gap: 16px;
  }
  
  .btn-social {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: bold;
    color: white;
  }
  
  .btn-social:hover {
    transform: translateY(-2px);
    background: rgba(255, 255, 255, 0.2);
  }
  
  /* Info panel styles */
  .info-panel {
    flex: 1;
    display: flex;
    flex-direction: column;
    animation: fadeSlideIn 0.8s ease-out 0.2s both;
    justify-content: space-between;
  }
  
  .welcome-back {
    margin-bottom: 30px;
  }
  
  .welcome-back h2 {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 12px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .welcome-back p {
    line-height: 1.6;
    font-size: 16px;
  }
  
  .login-benefits {
    display: flex;
    flex-direction: column;
    gap: 24px;
    flex: 1;
  }
  
  .benefit-item {
    display: flex;
    gap: 16px;
    align-items: center;
  }
  
  .benefit-icon {
    font-size: 24px;
    width: 50px;
    height: 50px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }
  
  .benefit-details h3 {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 4px;
  }
  
  .benefit-details p {
    font-size: 14px;
    opacity: 0.8;
    line-height: 1.4;
  }
  
  .signup-prompt {
    margin-top: 40px;
    text-align: center;
  }
  
  .signup-prompt p {
    margin-bottom: 16px;
    font-size: 16px;
  }
  
  .btn-register {
    padding: 14px 28px;
    width: 100%;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    border: none;
    background: rgba(255, 255, 255, 0.2);
    color: white;
    border: 1px solid rgba(255, 255, 255, 0.3);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  .btn-register:hover {
    background: rgba(255, 255, 255, 0.25);
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
  
  /* Animation keyframes */
  @keyframes fadeSlideIn {
    from {
      opacity: 0;
      transform: translateY(20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
  
  /* Responsive styles */
  @media (max-width: 992px) {
    .content-wrapper {
      flex-direction: column;
      padding: 20px;
    }
    
    .info-panel {
      display: none;
    }
  }
  
  @media (max-width: 600px) {
    .glass-panel {
      padding: 30px 20px;
    }
    
    .login-title {
      font-size: 28px;
    }
    
    .form-options {
      flex-direction: column;
      align-items: flex-start;
      gap: 15px;
    }
  }
  </style>