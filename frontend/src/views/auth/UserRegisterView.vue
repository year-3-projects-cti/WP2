<template>
    <div class="register-container">
      <!-- Animated background elements -->
      <div class="animated-bg">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="circle circle-3"></div>
      </div>
      
      <!-- Main content -->
      <div class="content-wrapper">
        <!-- Registration form panel -->
        <div class="register-panel glass-panel">
          <div class="back-link" @click="goBack">
            <span class="back-icon">←</span>
            Back to home
          </div>
          
          <h1 class="register-title">Create Your Account</h1>
          <p class="register-subtitle">Join UserManager and start managing your users effectively</p>
          
          <form class="register-form" @submit.prevent="handleRegister">
            <div class="form-row">
              <div class="form-group">
                <label for="firstName">First Name</label>
                <div class="input-wrapper">
                  <span class="input-icon">👤</span>
                  <input 
                    type="text" 
                    id="firstName" 
                    v-model="firstName" 
                    placeholder="First name" 
                    required
                  />
                </div>
              </div>
              
              <div class="form-group">
                <label for="lastName">Last Name</label>
                <div class="input-wrapper">
                  <span class="input-icon">👤</span>
                  <input 
                    type="text" 
                    id="lastName" 
                    v-model="lastName" 
                    placeholder="Last name" 
                    required
                  />
                </div>
              </div>
            </div>
            
            <div class="form-group">
              <label for="email">Email Address</label>
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
              <label for="company">Company Name (Optional)</label>
              <div class="input-wrapper">
                <span class="input-icon">🏢</span>
                <input 
                  type="text" 
                  id="company" 
                  v-model="company" 
                  placeholder="Your company" 
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
                  placeholder="Create a password" 
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
              <div class="password-strength" :class="passwordStrengthClass">
                <div class="strength-bar"></div>
                <div class="strength-bar"></div>
                <div class="strength-bar"></div>
                <div class="strength-bar"></div>
                <span class="strength-text">{{ passwordStrengthText }}</span>
              </div>
            </div>
            
            <div class="form-group">
              <label for="confirmPassword">Confirm Password</label>
              <div class="input-wrapper">
                <span class="input-icon">🔒</span>
                <input 
                  :type="showConfirmPassword ? 'text' : 'password'" 
                  id="confirmPassword" 
                  v-model="confirmPassword" 
                  placeholder="Confirm your password" 
                  required
                />
                <button 
                  type="button" 
                  class="toggle-password" 
                  @click="showConfirmPassword = !showConfirmPassword"
                >
                  {{ showConfirmPassword ? '👁️' : '👁️‍🗨️' }}
                </button>
              </div>
              <div class="password-match" v-if="password && confirmPassword" :class="{ 'match': passwordsMatch, 'no-match': !passwordsMatch }">
                {{ passwordsMatch ? 'Passwords match' : 'Passwords do not match' }}
              </div>
            </div>
            
            <div class="form-group terms-group">
              <div class="checkbox-wrapper">
                <input type="checkbox" id="terms" v-model="agreeToTerms" required />
                <label for="terms">I agree to the <a href="#" class="terms-link">Terms of Service</a> and <a href="#" class="terms-link">Privacy Policy</a></label>
              </div>
            </div>
            
            <button 
              type="submit" 
              class="btn-register" 
              :disabled="!formValid"
            >
              <span class="btn-icon">✨</span>
              Create Account
            </button>
          </form>
          
          <div class="login-divider">
            <span>or register with</span>
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
        </div>
        
        <!-- Right side with info -->
        <div class="info-panel glass-panel">
          <div class="welcome-info">
            <h2>Join UserManager Today</h2>
            <p>Create your account and get instant access to powerful user management tools designed for businesses of all sizes.</p>
          </div>
          
          <div class="registration-benefits">
            <div class="benefit-item">
              <div class="benefit-icon">✅</div>
              <div class="benefit-details">
                <h3>Easy User Management</h3>
                <p>Create, modify, and organize user accounts with just a few clicks</p>
              </div>
            </div>
            
            <div class="benefit-item">
              <div class="benefit-icon">🔄</div>
              <div class="benefit-details">
                <h3>Seamless Integrations</h3>
                <p>Connect with your existing systems and applications effortlessly</p>
              </div>
            </div>
            
            <div class="benefit-item">
              <div class="benefit-icon">📱</div>
              <div class="benefit-details">
                <h3>Multi-platform Access</h3>
                <p>Manage your users from any device, anywhere, anytime</p>
              </div>
            </div>
            
            <div class="benefit-item">
              <div class="benefit-icon">🛡️</div>
              <div class="benefit-details">
                <h3>Enhanced Security</h3>
                <p>Advanced security features to protect user data and privacy</p>
              </div>
            </div>
          </div>
          
          <div class="login-prompt">
            <p>Already have an account?</p>
            <button class="btn-login" @click="goToLogin">
              <span class="btn-icon">🔑</span>
              Log In
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, watch } from 'vue';
  import { useRouter } from 'vue-router';
  
  const router = useRouter();
  
  // Form fields
  const firstName = ref('');
  const lastName = ref('');
  const email = ref('');
  const company = ref('');
  const password = ref('');
  const confirmPassword = ref('');
  const showPassword = ref(false);
  const showConfirmPassword = ref(false);
  const agreeToTerms = ref(false);
  
  // Password strength calculation
  const passwordStrength = computed(() => {
    if (!password.value) return 0;
    
    let score = 0;
    
    // Length check
    if (password.value.length >= 8) score += 1;
    if (password.value.length >= 12) score += 1;
    
    // Complexity checks
    if (/[A-Z]/.test(password.value)) score += 1;
    if (/[a-z]/.test(password.value)) score += 1;
    if (/[0-9]/.test(password.value)) score += 1;
    if (/[^A-Za-z0-9]/.test(password.value)) score += 1;
    
    return Math.min(4, score);
  });
  
  const passwordStrengthText = computed(() => {
    const strength = passwordStrength.value;
    if (!password.value) return 'Enter password';
    if (strength === 0) return 'Very weak';
    if (strength === 1) return 'Weak';
    if (strength === 2) return 'Medium';
    if (strength === 3) return 'Strong';
    return 'Very strong';
  });
  
  const passwordStrengthClass = computed(() => {
    const strength = passwordStrength.value;
    if (!password.value) return '';
    if (strength === 0) return 'strength-very-weak';
    if (strength === 1) return 'strength-weak';
    if (strength === 2) return 'strength-medium';
    if (strength === 3) return 'strength-strong';
    return 'strength-very-strong';
  });
  
  const passwordsMatch = computed(() => {
    return password.value && confirmPassword.value && password.value === confirmPassword.value;
  });
  
  const formValid = computed(() => {
    return firstName.value && 
           lastName.value && 
           email.value && 
           password.value && 
           confirmPassword.value && 
           passwordsMatch.value && 
           passwordStrength.value >= 2 && 
           agreeToTerms.value;
  });
  
  function handleRegister() {
    if (!formValid.value) return;
    
    // Here you would add your registration logic
    console.log('Registration with:', {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      company: company.value
    });
    
    // Redirect to dashboard after successful registration
    router.push('/dashboard');
  }
  
  function goBack() {
    router.push('/');
  }
  
  function goToLogin() {
    router.push('/login');
  }
  </script>
  
  <style scoped>
  /* Base styles */
  .register-container {
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
  
  /* Register panel styles */
  .register-panel {
    flex: 1.2;
    animation: fadeSlideIn 0.8s ease-out;
    max-height: 90vh;
    overflow-y: auto;
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
  
  .register-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 12px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .register-subtitle {
    font-size: 16px;
    margin-bottom: 30px;
    opacity: 0.9;
  }
  
  /* Form styles */
  .register-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
  
  .form-row {
    display: flex;
    gap: 20px;
  }
  
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
    flex: 1;
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
  
  .password-strength {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-top: 8px;
  }
  
  .strength-bar {
    height: 4px;
    flex: 1;
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 2px;
  }
  
  .strength-text {
    font-size: 12px;
    white-space: nowrap;
  }
  
  .strength-very-weak .strength-bar:nth-child(1) {
    background-color: #ff4d4d;
  }
  
  .strength-weak .strength-bar:nth-child(1),
  .strength-weak .strength-bar:nth-child(2) {
    background-color: #ffa64d;
  }
  
  .strength-medium .strength-bar:nth-child(1),
  .strength-medium .strength-bar:nth-child(2),
  .strength-medium .strength-bar:nth-child(3) {
    background-color: #ffff4d;
  }
  
  .strength-strong .strength-bar:nth-child(1),
  .strength-strong .strength-bar:nth-child(2),
  .strength-strong .strength-bar:nth-child(3),
  .strength-strong .strength-bar:nth-child(4) {
    background-color: #4dff4d;
  }
  
  .strength-very-strong .strength-bar {
    background-color: #4dff4d;
  }
  
  .password-match {
    font-size: 12px;
    margin-top: 8px;
  }
  
  .match {
    color: #4dff4d;
  }
  
  .no-match {
    color: #ff4d4d;
  }
  
  .terms-group {
    margin-top: 10px;
  }
  
  .checkbox-wrapper {
    display: flex;
    align-items: flex-start;
    gap: 10px;
  }
  
  .checkbox-wrapper input[type="checkbox"] {
    margin-top: 3px;
  }
  
  .terms-link {
    color: white;
    text-decoration: underline;
    transition: opacity 0.3s ease;
  }
  
  .terms-link:hover {
    opacity: 0.8;
  }
  
  .btn-register {
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
  
  .btn-register:hover:not(:disabled) {
    background: #f0f0f0;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
  
  .btn-register:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  .btn-icon {
    margin-right: 8px;
    font-size: 18px;
  }
  
  /* Login divider */
  .login-divider {
    display: flex;
    align-items: center;
    margin: 20px 0;
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
    flex: 0.8;
    display: flex;
    flex-direction: column;
    animation: fadeSlideIn 0.8s ease-out 0.2s both;
    justify-content: space-between;
  }
  
  .welcome-info {
    margin-bottom: 30px;
  }
  
  .welcome-info h2 {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 12px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .welcome-info p {
    line-height: 1.6;
    font-size: 16px;
  }
  
  .registration-benefits {
    display: flex;
    flex-direction: column;
    gap: 20px;
    flex: 1;
  }
  
  .benefit-item {
    display: flex;
    gap: 16px;
    align-items: center;
  }
  
  .benefit-icon {
    font-size: 20px;
    width: 40px;
    height: 40px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }
  
  .benefit-details h3 {
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 4px;
  }
  
  .benefit-details p {
    font-size: 14px;
    opacity: 0.8;
    line-height: 1.4;
  }
  
  .login-prompt {
    margin-top: 40px;
    text-align: center;
  }
  
  .login-prompt p {
    margin-bottom: 16px;
    font-size: 16px;
  }
  
  .btn-login {
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
  
  .btn-login:hover {
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
  
  @media (max-width: 768px) {
    .form-row {
      flex-direction: column;
      gap: 20px;
    }
    
    .glass-panel {
      padding: 30px 20px;
    }
  }
  
  @media (max-width: 600px) {
    .register-title {
      font-size: 28px;
    }
    
    .checkbox-wrapper {
      align-items: flex-start;
    }
    
    .checkbox-wrapper label {
      font-size: 14px;
    }
  }
  </style>