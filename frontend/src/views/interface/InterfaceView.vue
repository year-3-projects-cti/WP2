<template>
    <div class="dashboard-container">
      <!-- Animated background -->
      <div class="animated-bg">
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>
        <div class="circle circle-3"></div>
      </div>
  
      <!-- Header -->
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
        <!-- User Profile -->
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
  
      <div class="main-container">
        <Sidebar />
  
        <!-- Main content -->
        <main class="main-content">
          <div class="content-wrapper">
            <!-- Welcome section -->
            <section class="welcome-panel glass-panel">
              <h1 v-if="usersStore.user" class="welcome-title">
                Welcome, {{ usersStore.user.name }}
              </h1>

              <p class="welcome-subtitle">Here is an overview of your day</p>
  
              <div class="stats-container">
                <div class="stat-card" v-for="stat in stats" :key="stat.title">
                  <div class="stat-icon">
                    <i :class="stat.icon"></i>
                  </div>
                  <div class="stat-details">
                    <h3>{{ stat.value }}</h3>
                    <p>{{ stat.title }}</p>
                  </div>
                </div>
              </div>
            </section>
  
            <!-- Calendar section -->
            <section class="calendar-panel glass-panel">
              <div class="panel-header">
                <h2>Course Calendar</h2>
                <div class="calendar-controls">
                  <button class="control-btn"><i class="fas fa-chevron-left"></i></button>
                  <h3>{{ currentDate }}</h3>
                  <button class="control-btn"><i class="fas fa-chevron-right"></i></button>
                </div>
              </div>
  
              <div class="schedule-container">
                <div class="time-column">
                  <div class="time-slot" v-for="hour in hours" :key="hour">{{ hour }}:00</div>
                </div>
  
                <div class="schedule-events">
                  <div 
                    v-for="event in todayEvents" 
                    :key="event.id" 
                    class="schedule-event" 
                    :class="event.status"
                    :style="{ 
                      top: `${(event.startHour - 8) * 60 + event.startMinute}px`, 
                      height: `${event.duration * 60}px` 
                    }"
                  >
                    <div class="event-header">
                      <h4>{{ event.title }}</h4>
                      <span class="event-time">{{ formatEventTime(event) }}</span>
                    </div>
                    <div class="event-details">
                      <p>{{ event.location }}</p>
                      <p>Teacher: {{ event.teacher }}</p>
                      <p class="seats-info" v-if="event.status !== 'past'">
                        Seats: {{ event.seatsOccupied }}/{{ event.seatsTotal }}
                        <span v-if="event.seatsOccupied < event.seatsTotal" class="available">Available</span>
                        <span v-else class="full">Full</span>
                      </p>
                      <div class="event-actions" v-if="event.status === 'upcoming'">
                        <button class="btn-details">Details</button>
                        <button class="btn-join" v-if="event.seatsOccupied < event.seatsTotal">Join</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </div>
  
          <div class="content-wrapper">
            <!-- Recent activities section -->
            <section class="activities-panel glass-panel">
              <h2>Recent Activities</h2>
              <ul class="activity-list">
                <li class="activity-item" v-for="activity in recentActivities" :key="activity.id">
                  <div class="activity-icon" :class="activity.type">
                    <i :class="getActivityIcon(activity.type)"></i>
                  </div>
                  <div class="activity-details">
                    <p class="activity-text">{{ activity.description }}</p>
                    <span class="activity-time">{{ activity.time }}</span>
                  </div>
                </li>
              </ul>
            </section>
  
            <!-- Notifications section -->
            <section class="notifications-panel glass-panel">
              <h2>Notifications</h2>
              <ul class="notification-list">
                <li class="notification-item" v-for="notification in notifications" :key="notification.id" :class="{ unread: !notification.read }">
                  <div class="notification-icon" :class="notification.type">
                    <i :class="getNotificationIcon(notification.type)"></i>
                  </div>
                  <div class="notification-content">
                    <p class="notification-text">{{ notification.message }}</p>
                    <span class="notification-time">{{ notification.time }}</span>
                  </div>
                  <button class="notification-action" v-if="!notification.read">
                    <i class="fas fa-check"></i>
                  </button>
                </li>
              </ul>
              <button class="view-all-btn">View All Notifications</button>
            </section>
          </div>
        </main>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref, onMounted, computed } from 'vue'
import { useStudentsStore } from '@/stores/studentsStore'
import { useUsersStore } from '@/stores/usersStore' // 🔥 importăm usersStore
import { useAuthStore } from '@/stores/useAuthStore'
import { useRouter } from 'vue-router'
  import Sidebar from '@/components/SideBar.vue'

const router = useRouter()
const authStore = useAuthStore()
const studentsStore = useStudentsStore()
const usersStore = useUsersStore() // 🔥 instanțiem usersStore

const hours = ref([8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18])
const currentDate = ref('April 26, 2025')

const todayEvents = ref([
  { id: 1, title: 'Mathematics', startHour: 9, startMinute: 0, duration: 1.5, location: 'Room A102', teacher: 'Mary Laurent', seatsTotal: 25, seatsOccupied: 22, status: 'past' },
  { id: 2, title: 'Physics', startHour: 11, startMinute: 0, duration: 2, location: 'Room B201', teacher: 'Peter Martin', seatsTotal: 20, seatsOccupied: 20, status: 'current' },
  { id: 3, title: 'English', startHour: 14, startMinute: 30, duration: 1.5, location: 'Room C305', teacher: 'Sophie White', seatsTotal: 15, seatsOccupied: 12, status: 'upcoming' },
  { id: 4, title: 'Computer Science', startHour: 16, startMinute: 15, duration: 2, location: 'Room D110', teacher: 'Thomas Black', seatsTotal: 18, seatsOccupied: 15, status: 'upcoming' }
])

const recentActivities = ref([
  { id: 1, type: 'student', description: 'New student registered: Emma Small', time: '10 minutes ago' },
  { id: 2, type: 'class', description: '"Advanced Chemistry" course added to schedule', time: '35 minutes ago' },
  { id: 3, type: 'payment', description: 'Payment received from the Martin family', time: '2 hours ago' },
  { id: 4, type: 'message', description: 'New message from Prof. Dubois', time: '3 hours ago' },
  { id: 5, type: 'admin', description: 'System update completed', time: '5 hours ago' }
])

const notifications = ref([
  { id: 1, type: 'alert', message: 'Teachers meeting tomorrow at 2:00 PM', time: '20 minutes ago', read: false },
  { id: 2, type: 'info', message: 'New shared document: "Second Trimester Schedule"', time: '1 hour ago', read: false },
  { id: 3, type: 'reminder', message: 'Exam registration deadline: April 28', time: '3 hours ago', read: false },
  { id: 4, type: 'message', message: 'New message from Mary Laurent', time: 'Yesterday', read: true }
])

const stats = computed(() => [
  { title: 'Students', value: studentsStore.students.length, icon: 'fas fa-user-graduate' },
  { title: 'Active Classes', value: 24, icon: 'fas fa-chalkboard' },
  { title: 'Courses Today', value: 8, icon: 'fas fa-calendar-check' },
  { title: 'New Messages', value: 5, icon: 'fas fa-envelope' }
])

onMounted(async () => {
  await studentsStore.fetchStudents()
  
  const userId = localStorage.getItem('userId')
  if (userId) {
    await usersStore.fetchUser(userId)
  }
})

function handleLogout() {
  authStore.logout();
  localStorage.removeItem('userId')
  router.push("/login");
}
function formatEventTime(event) {
    const startHour = event.startHour.toString().padStart(2, '0')
    const startMin = event.startMinute.toString().padStart(2, '0')
    const endMinutes = (event.startHour * 60 + event.startMinute) + (event.duration * 60)
    const endHour = Math.floor(endMinutes / 60)
    const endMin = Math.floor(endMinutes % 60)
    return `${startHour}:${startMin} - ${endHour.toString().padStart(2, '0')}:${endMin.toString().padStart(2, '0')}`
  }
  function getActivityIcon(type) {
    const icons = {
      student: 'fas fa-user-graduate',
      class: 'fas fa-chalkboard',
      payment: 'fas fa-credit-card',
      message: 'fas fa-comment',
      admin: 'fas fa-cog'
    }
    return icons[type] || 'fas fa-info-circle'
  }
  
  function getNotificationIcon(type) {
    const icons = {
      alert: 'fas fa-exclamation-circle',
      info: 'fas fa-info-circle',
      reminder: 'fas fa-clock',
      message: 'fas fa-envelope'
    }
    return icons[type] || 'fas fa-bell'
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
    font-family: 'Cal Sans', sans-serif;
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
  
  /* Main container */
  .main-container {
    display: flex;
    min-height: calc(100vh - 70px);
  }
  
  /* Sidebar */
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
  
  /* Main content */
  .main-content {
    flex: 1;
    padding: 30px;
    position: relative;
    z-index: 1;
    overflow-y: auto;
  }
  
  .content-wrapper {
    display: flex;
    gap: 30px;
    margin-bottom: 30px;
  }
  
  .content-wrapper > section {
    flex: 1;
  }
  
  /* Welcome panel */
  .welcome-panel {
    animation: fadeSlideIn 0.8s ease-out;
  }
  
  .welcome-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 10px;
    background: linear-gradient(to right, #ffffff, rgba(255, 255, 255, 0.8));
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
  }
  
  .welcome-subtitle {
    font-size: 16px;
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 30px;
  }
  
  /* Stats container */
  .stats-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }
  
  .stat-card {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 15px;
    padding: 20px;
    display: flex;
    align-items: center;
    transition: all 0.3s ease;
  }
  
  .stat-card:hover {
    transform: translateY(-5px);
    background: rgba(255, 255, 255, 0.15);
  }
  
  .stat-icon {
    width: 50px;
    height: 50px;
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.1);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 22px;
    margin-right: 15px;
  }
  
  .stat-details h3 {
    font-size: 24px;
    font-weight: 700;
    margin: 0;
    margin-bottom: 5px;
  }
  
  .stat-details p {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    margin: 0;
  }
  
  /* Calendar panel */
  .calendar-panel {
    min-height: 500px;
  }
  
  .panel-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .panel-header h2 {
    font-size: 24px;
    font-weight: 600;
    margin: 0;
  }
  
  .calendar-controls {
    display: flex;
    align-items: center;
    gap: 15px;
  }
  
  .calendar-controls h3 {
    margin: 0;
    font-size: 18px;
    font-weight: 500;
  }
  
  .control-btn {
    background: rgba(255, 255, 255, 0.1);
    border: none;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .control-btn:hover {
    background: rgba(255, 255, 255, 0.2);
  }
  
  /* Schedule styles */
  .schedule-container {
    display: flex;
    height: 600px;
    position: relative;
    overflow-y: auto;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
  }
  
  .time-column {
    width: 60px;
    padding-top: 20px;
    background: rgba(0, 0, 0, 0.1);
    border-radius: 12px 0 0 12px;
  }
  
  .time-slot {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    position: relative;
  }
  
  .time-slot::after {
    content: '';
    position: absolute;
    right: 0;
    left: 0;
    height: 1px;
    background: rgba(255, 255, 255, 0.1);
    bottom: 0;
  }
  
  .schedule-events {
    flex: 1;
    position: relative;
    padding: 20px;
  }
  
  .schedule-event {
    position: absolute;
    left: 20px;
    right: 20px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    padding: 15px;
    transition: all 0.3s ease;
    cursor: pointer;
    border-left: 4px solid transparent;
  }
  
  .schedule-event:hover {
    transform: translateX(5px);
    background: rgba(255, 255, 255, 0.15);
  }
  
  .schedule-event.past {
    border-left-color: #95a5a6;
    opacity: 0.7;
  }
  
  .schedule-event.current {
    border-left-color: #3498db;
    background: rgba(52, 152, 219, 0.2);
  }
  
  .schedule-event.upcoming {
    border-left-color: #2ecc71;
  }
  
  .event-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .event-header h4 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
  }
  
  .event-time {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
  }
  
  .event-details p {
    font-size: 14px;
    margin: 5px 0;
    color: rgba(255, 255, 255, 0.8);
  }
  
  .seats-info {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  
  .seats-info .available {
    background: rgba(46, 204, 113, 0.2);
    color: #2ecc71;
    font-size: 12px;
    padding: 2px 8px;
    border-radius: 20px;
  }
  
  .seats-info .full {
    background: rgba(231, 76, 60, 0.2);
    color: #e74c3c;
    font-size: 12px;
    padding: 2px 8px;
    border-radius: 20px;
  }
  
  .event-actions {
    display: flex;
    gap: 10px;
    margin-top: 10px;
  }
  
  .event-actions button {
    padding: 5px 15px;
    border: none;
    border-radius: 5px;
    font-size: 13px;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .btn-details {
    background: rgba(255, 255, 255, 0.1);
    color: white;
  }
  
  .btn-details:hover {
    background: rgba(255, 255, 255, 0.2);
  }
  
  .btn-join {
    background: rgba(46, 204, 113, 0.8);
    color: white;
  }
  
  .btn-join:hover {
    background: rgba(46, 204, 113, 1);
  }
  
 /* Activities panel */
.activities-panel {
  animation: fadeSlideIn 0.8s ease-out;
}

.activity-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.activity-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 18px;
}

.activity-icon.student {
  background: rgba(52, 152, 219, 0.2);
  color: #3498db;
}

.activity-icon.class {
  background: rgba(155, 89, 182, 0.2);
  color: #9b59b6;
}

.activity-icon.payment {
  background: rgba(46, 204, 113, 0.2);
  color: #2ecc71;
}

.activity-icon.message {
  background: rgba(241, 196, 15, 0.2);
  color: #f1c40f;
}

.activity-icon.admin {
  background: rgba(231, 76, 60, 0.2);
  color: #e74c3c;
}

.activity-details {
  flex: 1;
}

.activity-text {
  margin: 0 0 5px 0;
  font-size: 15px;
}

.activity-time {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
}

/* Notifications panel */
.notifications-panel {
  animation: fadeSlideIn 1s ease-out;
}

.notification-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.notification-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 10px;
  background: rgba(255, 255, 255, 0.05);
  transition: all 0.3s ease;
}

.notification-item:hover {
  background: rgba(255, 255, 255, 0.1);
}

.notification-item.unread {
  background: rgba(52, 152, 219, 0.1);
  border-left: 3px solid #3498db;
}

.notification-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 18px;
}

.notification-icon.alert {
  background: rgba(231, 76, 60, 0.2);
  color: #e74c3c;
}

.notification-icon.info {
  background: rgba(52, 152, 219, 0.2);
  color: #3498db;
}

.notification-icon.reminder {
  background: rgba(241, 196, 15, 0.2);
  color: #f1c40f;
}

.notification-icon.message {
  background: rgba(46, 204, 113, 0.2);
  color: #2ecc71;
}

.notification-content {
  flex: 1;
}

.notification-text {
  margin: 0 0 5px 0;
  font-size: 15px;
}

.notification-time {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
}

.notification-action {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.notification-action:hover {
  background: rgba(255, 255, 255, 0.2);
}

.view-all-btn {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.1);
  color: white;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  margin-top: 15px;
  transition: all 0.3s ease;
}

.view-all-btn:hover {
  background: rgba(255, 255, 255, 0.2);
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
@media (max-width: 1200px) {
  .content-wrapper {
    flex-direction: column;
  }
  
  .stats-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 992px) {
  .main-container {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .sidebar-menu {
    display: flex;
    overflow-x: auto;
    padding-bottom: 15px;
  }
  
  .menu-item {
    flex: 0 0 auto;
  }
  
  .menu-item a {
    padding: 10px 15px;
    flex-direction: column;
    text-align: center;
  }
  
  .menu-item a i {
    margin-right: 0;
    margin-bottom: 5px;
  }
  
  .menu-badge {
    position: absolute;
    top: 5px;
    right: 5px;
  }
}

@media (max-width: 768px) {
  .stats-container {
    grid-template-columns: 1fr;
  }
  
  .schedule-container {
    height: 500px;
  }
}
</style>