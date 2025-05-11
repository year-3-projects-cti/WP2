<template>
  <div class="dashboard-container">
    <InterfaceNav />
    <div class="main-container">
      <Sidebar />
      <main class="main-content">
        <div class="glass-panel">
          <h1 class="welcome-title">Course Calendar</h1>

          <!-- Controls -->
          <section class="glass-panel calendar-controls-panel">
            <div class="calendar-controls">
              <button class="btn-details" @click="prevWeek">Previous Week</button>
              <h2>{{ currentWeekRange }}</h2>
              <button class="btn-details" @click="nextWeek">Next Week</button>
            </div>
          </section>

          <div v-if="loading" class="loading-overlay">
            <div class="spinner"></div>
          </div>

          <!-- Calendar -->
          <section v-else class="glass-panel calendar-grid-panel">
            <div class="calendar-container">
              <div class="time-labels">
                <div class="time-label header-spacer"></div>
                <div class="time-label" v-for="hour in hours" :key="hour">{{ hour }}:00</div>
              </div>

              <div class="weekday-grid">
                <div class="weekday-column" v-for="(day, index) in weekdays" :key="index">
                  <div class="weekday-header">
                    <div class="day-name">{{ day }}</div>
                    <div class="day-date">{{ formatDate(addDays(currentWeekStart, index)) }}</div>
                  </div>
                  <div class="day-slots">
                    <div class="time-slot" v-for="hour in hours" :key="`${index}-${hour}`">
                      <div 
                        v-for="course in getCoursesForDayAndHour(index, hour)" 
                        :key="course.id"
                        class="course-item"
                        :class="getCourseStatusClass(course.status)"
                      >
                        <div class="course-title">{{ course.name }}</div>
                        <div class="course-details">
                          <span>{{ findTeacherName(course.teacherId) }}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </section>

        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import Sidebar from '@/components/SideBar.vue'
import InterfaceNav from '@/components/InterfaceNav.vue'

// Helpers
function getWeekStart(date) {
  const result = new Date(date)
  const day = result.getDay()
  const diff = result.getDate() - day + (day === 0 ? -6 : 1)
  result.setDate(diff)
  return result
}

function addDays(date, days) {
  const result = new Date(date)
  result.setDate(result.getDate() + days)
  return result
}

function formatDate(date) {
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
}

// State
const today = new Date()
const currentWeekStart = ref(getWeekStart(today))
const hours = ref([8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19])
const weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
const courses = ref([])
const loading = ref(false)
const teachers = ref([])

// Computed
const currentWeekEnd = computed(() => {
  const end = new Date(currentWeekStart.value)
  end.setDate(end.getDate() + 6)
  return end
})

const currentWeekRange = computed(() => {
  const startStr = currentWeekStart.value.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
  const endStr = currentWeekEnd.value.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })
  return `${startStr} - ${endStr}`
})

// Helper function to get day index (0 = Monday, 6 = Sunday)
function getDayIndex(date) {
  const day = date.getDay()
  return day === 0 ? 6 : day - 1 // Convert Sunday (0) to 6, and shift others (1-6) to 0-5
}

// Fetch courses for week
async function fetchCoursesForWeek() {
  loading.value = true
  try {
    const startStr = formatBackendDate(currentWeekStart.value)
    const endStr = formatBackendDate(currentWeekEnd.value)

    const response = await axios.get('http://localhost:8080/api/courses/between', {
      params: { start: startStr, end: endStr }
    })

    // Process one-time courses
    const oneTimeCourses = response.data.oneTimeCourses.map(course => {
      const courseDate = new Date(course.startDateTime)
      return {
        id: course.id,
        name: course.name,
        day: getDayIndex(courseDate), // Convert to day index (0-6)
        hour: courseDate.getHours(),
        status: course.status || 'active',
        teacherId: course.teacher // API uses "teacher" not "teacherId"
      }
    })

    // Process recurrent courses
    const recurrentCourses = response.data.recurrentCourses.flatMap(course => {
      return generateRecurrentSessionsForWeek(course)
    })

    courses.value = [...oneTimeCourses, ...recurrentCourses]

  } catch (error) {
    console.error('Failed to fetch courses:', error)
  } finally {
    loading.value = false
  }
}

// Extra helpers
function formatBackendDate(date) {
  return `${date.getDate().toString().padStart(2, '0')}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getFullYear()} 00:00`
}

function generateRecurrentSessionsForWeek(course) {
  const sessions = []
  
  // Map day names to indices (0 = Monday, 6 = Sunday)
  const dayMapping = {
    'MONDAY': 0,
    'TUESDAY': 1, 
    'WEDNESDAY': 2,
    'THURSDAY': 3,
    'FRIDAY': 4,
    'SATURDAY': 5,
    'SUNDAY': 6
  }
  
  const dayIndex = dayMapping[course.dayOfWeek]
  if (dayIndex !== undefined) {
    // Extract hour from time string (format: "HH:MM:SS")
    const hour = parseInt(course.startTime.split(':')[0])
    
    sessions.push({
      id: `${course.id}-${dayIndex}`,
      name: course.name,
      day: dayIndex,
      hour: hour,
      status: course.status || 'active',
      teacherId: course.teacher // API uses "teacher" not "teacherId"
    })
  }
  
  return sessions
}

// Find teacher
function findTeacherName(id) {
  const teacher = teachers.value.find(t => t.id === id)
  return teacher ? teacher.name : 'Unknown'
}

// Navigation
function nextWeek() {
  const newStart = new Date(currentWeekStart.value)
  newStart.setDate(newStart.getDate() + 7)
  currentWeekStart.value = newStart
  fetchCoursesForWeek()
}

function prevWeek() {
  const newStart = new Date(currentWeekStart.value)
  newStart.setDate(newStart.getDate() - 7)
  currentWeekStart.value = newStart
  fetchCoursesForWeek()
}

// Init
onMounted(async () => {
  await fetchCoursesForWeek()
  const response = await axios.get('http://localhost:8080/api/teachers')
  teachers.value = response.data
})

// Filters
function getCoursesForDayAndHour(day, hour) {
  return courses.value.filter(course => course.day === day && course.hour === hour)
}

function getCourseStatusClass(status) {
  return {
    'course-active': status === 'active',
    'course-cancelled': status === 'cancelled'
  }
}
</script>

<style scoped>
/* Existing styles from your application */
.dashboard-container {
  min-height: 100vh;
  width: 100%;
  overflow-x: hidden;
  background: linear-gradient(135deg, #2980b9, #6dd5fa);
  color: #fff;
  font-family: 'Cal Sans', sans-serif;
  position: relative;
}

.main-container {
  display: flex;
  min-height: 100vh;
  position: relative;
  z-index: 1;
}

.main-content {
  flex: 1;
  padding: 0;
  margin: 0;
  position: relative;
  overflow-y: auto;
}

.glass-panel {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  padding: 15px;
  z-index: 1;
  position: relative;
  overflow: hidden;
  margin: 10px;
}

.calendar-controls-panel {
  margin-bottom: 10px;
  padding: 10px 15px;
}

.calendar-grid-panel {
  padding: 10px;
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

.welcome-title {
  font-size: 28px;
  font-weight: 700;
  margin: 15px 0;
  text-align: center;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

h2 {
  font-size: 20px;
  margin: 0;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.9);
}

.btn-details {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  padding: 8px 16px;
  border-radius: 8px;
  border: none;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-details:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(255, 255, 255, 0.2);
}

/* Calendar specific styles */
.calendar-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.calendar-container {
  display: flex;
  width: 100%;
  overflow-x: auto;
}

.time-labels {
  display: flex;
  flex-direction: column;
  min-width: 50px;
  border-right: 1px solid rgba(255, 255, 255, 0.1);
}

.header-spacer {
  height: 60px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.time-label {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.weekday-grid {
  display: flex;
  flex: 1;
  min-width: 0;
}

.weekday-column {
  flex: 1;
  min-width: 130px;
  border-right: 1px solid rgba(255, 255, 255, 0.1);
}

.weekday-column:last-child {
  border-right: none;
}

.weekday-header {
  height: 60px;
  padding: 8px;
  text-align: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(255, 255, 255, 0.05);
}

.day-name {
  font-weight: 600;
  font-size: 14px;
}

.day-date {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

.day-slots {
  display: flex;
  flex-direction: column;
}

.time-slot {
  height: 80px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  position: relative;
  padding: 3px;
  box-sizing: border-box;
}

.course-item {
  width: calc(100% - 6px);
  height: 74px;
  border-radius: 8px;
  padding: 8px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  cursor: pointer;
  transition: all 0.3s ease;
  overflow: hidden;
  box-sizing: border-box;
  position: absolute;
  top: 3px;
  left: 3px;
}

.course-active {
  background: rgba(46, 204, 113, 0.2);
  border-left: 4px solid rgba(46, 204, 113, 0.8);
}

.course-active:hover {
  background: rgba(46, 204, 113, 0.3);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(46, 204, 113, 0.2);
}

.course-cancelled {
  background: rgba(231, 76, 60, 0.2);
  border-left: 4px solid rgba(231, 76, 60, 0.8);
}

.course-cancelled:hover {
  background: rgba(231, 76, 60, 0.3);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(231, 76, 60, 0.2);
}

.course-title {
  font-weight: 600;
  font-size: 14px;
}

.course-details {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .calendar-container {
    flex-direction: column;
  }
  
  .time-labels {
    display: none;
  }
  
  .weekday-grid {
    flex-direction: column;
  }
  
  .weekday-column {
    border-right: none;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  }
  
  .weekday-column:last-child {
    border-bottom: none;
  }
}

.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(41, 128, 185, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
  backdrop-filter: blur(5px);
}

.spinner {
  border: 6px solid rgba(255, 255, 255, 0.3);
  border-top: 6px solid white;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

</style>