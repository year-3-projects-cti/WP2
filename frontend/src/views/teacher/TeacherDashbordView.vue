<template>
    <div class="dashboard-container">
      <!-- Animated background elements -->
      <div class="animated-bg">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="circle circle-3"></div>
      </div>
      
      <!-- Main content -->
      <div class="content-wrapper">
        <!-- Left side with welcome message and auth -->
        <div class="welcome-panel glass-panel">
          <h1 class="welcome-title">Welcome to UserManager</h1>
          <p class="welcome-subtitle">The intelligent solution for managing user accounts</p>
          
          <div class="auth-container">
            <p class="auth-message">Please log in or register to access your dashboard</p>
            <div class="auth-buttons">
              <button class="btn-login" @click="showLogin">
                <span class="btn-icon">👤</span>
                Log In
              </button>
              <button class="btn-register" @click="showRegister">
                <span class="btn-icon">✨</span>
                Register
              </button>
            </div>
          </div>
        </div>
        
        <!-- Right side with features -->
        <div class="features-panel glass-panel">
          <h2 class="features-title">Why Choose UserManager?</h2>
          
          <div class="features-grid">
            <div class="feature-card">
              <div class="feature-icon">🔒</div>
              <h3>Secure Access</h3>
              <p>Enterprise-grade security to protect all your user data</p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon">📊</div>
              <h3>Powerful Analytics</h3>
              <p>Gain insights with comprehensive user activity dashboards</p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon">⚡</div>
              <h3>Fast Performance</h3>
              <p>Lightning-quick response times and efficient workflows</p>
            </div>
            
            <div class="feature-card">
              <div class="feature-icon">🔄</div>
              <h3>Seamless Integration</h3>
              <p>Connects with all your existing tools and platforms</p>
            </div>
          </div>
        </div>
      </div>
      
      <!-- App info section -->
      <div class="app-info glass-panel">
        <h2 class="info-title">About UserManager</h2>
        <p class="info-text">
          UserManager is a comprehensive solution designed to simplify user management for organizations of all sizes. 
          With powerful tools for user registration, authentication, permissions, and monitoring, you'll have everything 
          you need to manage your user base effectively.
        </p>
        <div class="info-stats">
          <div class="stat-item">
            <div class="stat-number">10k+</div>
            <div class="stat-label">Active Users</div>
          </div>
          <div class="stat-item">
            <div class="stat-number">99.9%</div>
            <div class="stat-label">Uptime</div>
          </div>
          <div class="stat-item">
            <div class="stat-number">24/7</div>
            <div class="stat-label">Support</div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  
  import { useRouter } from 'vue-router';
  const router = useRouter();
  
  function showLogin() {
    router.push('/login');
  }
  
  function showRegister() {
    router.push('/register');
  }
  
  </script>
  
  <style scoped>
  /* Base styles */
  .dashboard-container {
    min-height: 100vh;
    width: 100%;
    overflow-x: hidden;
    background: linear-gradient(135deg, #2980b9, #6dd5fa);
    color: #fff;
    font-family: 'Roboto', sans-serif;
    position: relative;
  }
  
  /* Animated background */
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
  
  /* Header styles */
  .glass-header {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    padding: 20px 40px;
    display: flex;
    justify-content: center;
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
  
  /* Content wrapper */
  .content-wrapper {
    display: flex;
    gap: 30px;
    padding: 40px;
    max-width: 1400px;
    margin: 0 auto;
    z-index: 1;
    position: relative;
  }
  
  /* Welcome panel styles */
  .welcome-panel {
    flex: 1;
    display: flex;
    flex-direction: column;
    animation: fadeSlideIn 0.8s ease-out;
  }
  
  .welcome-title {
    font-size: 36px;
    font-weight: 700;
    margin-bottom: 16px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .welcome-subtitle {
    font-size: 18px;
    margin-bottom: 40px;
    opacity: 0.9;
  }
  
  .auth-container {
    margin-top: auto;
  }
  
  .auth-message {
    font-size: 16px;
    margin-bottom: 20px;
  }
  
  .auth-buttons {
    display: flex;
    gap: 16px;
  }
  
  .btn-login, .btn-register {
    padding: 14px 28px;
    border-radius: 12px;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    border: none;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  .btn-icon {
    margin-right: 8px;
    font-size: 18px;
  }
  
  .btn-login {
    background: rgba(255, 255, 255, 0.2);
    color: white;
    border: 1px solid rgba(255, 255, 255, 0.3);
  }
  
  .btn-login:hover {
    background: rgba(255, 255, 255, 0.25);
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
  
  .btn-register {
    background: white;
    color: #2980b9;
  }
  
  .btn-register:hover {
    background: #f0f0f0;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
  
  /* Features panel styles */
  .features-panel {
    flex: 1;
    animation: fadeSlideIn 0.8s ease-out 0.2s both;
  }
  
  .features-title {
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 30px;
  }
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }
  
  .feature-card {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 16px;
    padding: 24px;
    transition: all 0.3s ease;
    border: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .feature-card:hover {
    transform: translateY(-5px);
    background: rgba(255, 255, 255, 0.15);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  }
  
  .feature-icon {
    font-size: 28px;
    margin-bottom: 16px;
  }
  
  .feature-card h3 {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 8px;
  }
  
  .feature-card p {
    font-size: 14px;
    opacity: 0.8;
    line-height: 1.5;
  }
  
  /* App info section */
  .app-info {
    max-width: 1400px;
    margin: 0 auto 40px;
    padding: 40px;
    text-align: center;
    animation: fadeSlideIn 0.8s ease-out 0.4s both;
  }
  
  .info-title {
    font-size: 24px;
    font-weight: 600;
    margin-bottom: 20px;
  }
  
  .info-text {
    max-width: 800px;
    margin: 0 auto 30px;
    line-height: 1.6;
    font-size: 16px;
  }
  
  .info-stats {
    display: flex;
    justify-content: center;
    gap: 80px;
    margin-top: 30px;
  }
  
  .stat-item {
    text-align: center;
  }
  
  .stat-number {
    font-size: 36px;
    font-weight: 700;
    margin-bottom: 8px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .stat-label {
    font-size: 16px;
    opacity: 0.8;
  }
  
  /* Footer styles */
  .glass-footer {
    background: rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    padding: 20px 40px;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .footer-content {
    max-width: 1400px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .footer-links {
    display: flex;
    gap: 20px;
  }
  
  .footer-links a {
    color: white;
    opacity: 0.8;
    text-decoration: none;
    transition: opacity 0.3s ease;
  }
  
  .footer-links a:hover {
    opacity: 1;
  }
  
  /* Animation keyframes */
  @keyframes fadeSlideIn {
    from {
      opacity: 0;
      transform: translateY(20px);
    }
    to {
      opacity: c1;
      transform: translateY(0);
    }
  }
  
  /* Router transition */
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.3s, transform 0.3s;
  }
  
  .fade-enter-from,
  .fade-leave-to {
    opacity: 0;
    transform: translateY(10px);
  }
  
  /* Responsive styles */
  @media (max-width: 1200px) {
    .content-wrapper {
      padding: 30px;
    }
  }
  
  @media (max-width: 992px) {
    .content-wrapper {
      flex-direction: column;
    }
    
    .welcome-panel, .features-panel {
      width: 100%;
    }
    
    .info-stats {
      gap: 40px;
    }
  }
  
  @media (max-width: 768px) {
    .features-grid {
      grid-template-columns: 1fr;
    }
    
    .info-stats {
      flex-direction: column;
      gap: 30px;
    }
    
    .footer-content {
      flex-direction: column;
      gap: 20px;
      text-align: center;
    }
    
    .footer-links {
      justify-content: center;
    }
  }
  </style>