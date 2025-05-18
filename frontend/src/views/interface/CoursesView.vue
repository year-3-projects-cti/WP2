<template>
  <div class="dashboard-container">
    <InterfaceNav />
      <div class="main-container">
      <Sidebar />
      <main class="main-content">
        <div class="glass-panel">
          <h1 class="welcome-title">Course Management</h1>
          <div class="add-course-container">
            <button class="btn-add-course" @click="showAddCourseModal = true">
              <span class="plus-icon">+</span> Add Course
            </button>
          </div>
          <div v-if="teachers.length > 0">
  <div class="courses-grid">
    <div v-for="course in courses" :key="course.id" class="course-card glass-panel">
      <div class="course-image">
        <img :src="course.imageUrl" :alt="course.name">
      </div>
      <div class="course-content">
        <h3 class="course-title">{{ course.name }}</h3>
        <div class="course-details">
          <div class="detail-item">
            <span class="detail-icon">⏰</span><span class="detail-text">{{ course.hours }} hours</span>
          </div>
          <div class="detail-item">
            <span class="detail-icon">👨‍🏫</span><span class="detail-text">{{ findTeacherName(course.teacherId) }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-icon">🔄</span><span class="detail-text">{{ course.type }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

        </div>
      </main>
    </div>

    <div class="modal-overlay" v-if="showAddCourseModal" @click.self="showAddCourseModal = false">
  <div class="modal-content glass-panel">
    <h2>Add New Course</h2>
    <form @submit.prevent="addCourse">
      <div class="form-group">
        <label>Course Name</label>
        <input v-model="newCourse.name" type="text" placeholder="Course Name" required />
      </div>

      <div class="form-group">
        <label>Course Image URL</label>
        <input v-model="newCourse.imageUrl" type="text" placeholder="Image URL" required />
      </div>

      <div class="form-group">
        <label>Course Type</label>
        <select v-model="newCourse.type" required>
          <option value="one-time">One-Time</option>
          <option value="recurrent">Recurrent</option>
        </select>
      </div>

      <div class="form-group" v-if="newCourse.type === 'one-time'">
        <label>Session Start Date</label>
        <input v-model="newCourse.startDate" type="date" required />
      </div>

      <div class="form-group" v-if="newCourse.type === 'one-time'">
        <label>Start Hour</label>
        <input v-model="newCourse.startHour" type="time" required />
      </div>

      <div class="form-group" v-if="newCourse.type === 'recurrent'">
        <label>Day of the Week</label>
        <select v-model="newCourse.dayOfWeek" required>
          <option disabled value="">Select day</option>
          <option value="MONDAY">Monday</option>
          <option value="TUESDAY">Tuesday</option>
          <option value="WEDNESDAY">Wednesday</option>
          <option value="THURSDAY">Thursday</option>
          <option value="FRIDAY">Friday</option>
          <option value="SATURDAY">Saturday</option>
          <option value="SUNDAY">Sunday</option>
        </select>
      </div>

      <div class="form-group" v-if="newCourse.type === 'recurrent'">
        <label>Start Hour</label>
        <input v-model="newCourse.startHour" type="time" required />
      </div>

      <div class="form-group">
        <label>Classroom (optional)</label>
        <input v-model="newCourse.classroom" type="text" placeholder="Enter classroom name" />
      </div>

      <div class="form-group">
        <label>Assign Teacher</label>
        <select v-model="newCourse.teacherId" required>
          <option value="" disabled>Select Teacher</option>
          <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">{{ teacher.name }}</option>
        </select>
      </div>

      <div class="form-group">
        <label>Assign Students</label>
        <div class="student-selection">
          <div v-for="student in students" :key="student.id" class="student-checkbox">
            <input type="checkbox" :id="'student-' + student.id" :value="student.id" v-model="newCourse.students" />
            <label :for="'student-' + student.id">{{ student.name }}</label>
          </div>
        </div>
      </div>

      <div class="modal-actions">
        <button class="btn-details" type="button" @click="showAddCourseModal = false">Cancel</button>
        <button class="btn-join" type="submit">Add Course</button>
      </div>
    </form>
  </div>
</div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import Sidebar from '@/components/SideBar.vue'
import InterfaceNav from '@/components/InterfaceNav.vue'

// Data
const teachers = ref([])
const students = ref([])
const courses = ref([])

const showAddCourseModal = ref(false)

const newCourse = ref({
  name: '',
  startHour: null,
  hours: null,
  startDate: '',
  type: 'one-time',
  teacherId: '',
  students: [],
  imageUrl: '',
  classroom: ''
})

const fetchTeachers = async () => {
  try {
    const response = await axios.get('/api/users?role=TEACHER')
    teachers.value = response.data
  } catch (error) {
    console.error('Failed to fetch teachers:', error)
  }
}

const fetchStudents = async () => {
  try {
    const response = await axios.get('/api/users?role=STUDENT')
    students.value = response.data
  } catch (error) {
    console.error('Failed to fetch students:', error)
  }
}

const fetchCourses = async () => {
  try {
    const [oneTimeResponse, recurrentResponse] = await Promise.all([
      axios.get('/api/one-time-courses'),
      axios.get('/api/recurrent-courses')
    ])

    const oneTime = oneTimeResponse.data.map(c => ({ ...c, type: 'one-time' }))
    const recurrent = recurrentResponse.data.map(c => ({ ...c, type: 'recurrent' }))
    courses.value = [...oneTime, ...recurrent]
  } catch (error) {
    console.error('Failed to fetch courses:', error)
  }
}

const findTeacherName = (id) => {
  const teacher = teachers.value.find(t => t.id === id)
  return teacher ? teacher.name : 'Unknown'
}

async function addCourse() {
  try {
    if (!newCourse.value.teacherId) {
      alert('Please select a teacher');
      return;
    }

    let payload;

    if (newCourse.value.type === 'one-time') {
      const startDateTime = new Date(`${newCourse.value.startDate}T${newCourse.value.startHour}:00`);
      const localDateTime = new Date(startDateTime.getTime() - startDateTime.getTimezoneOffset() * 60000);

      payload = {
        name: newCourse.value.name,
        classroom: newCourse.value.classroom || 'Default Room',
        imageUrl: newCourse.value.imageUrl,
        startDateTime: localDateTime.toISOString(), // Send only this, no separate startHour
        status: 'active',
        teacherId: newCourse.value.teacherId,
        studentIds: newCourse.value.students.join(',')
      };

      await axios.post('/api/one-time-courses', payload);

    } else if (newCourse.value.type === 'recurrent') {
      payload = {
        name: newCourse.value.name,
        classroom: newCourse.value.classroom || 'Default Room',
        imageUrl: newCourse.value.imageUrl,
        dayOfWeek: newCourse.value.dayOfWeek,
        startTime: newCourse.value.startHour, // HH:mm format expected by backend
        status: 'active',
        teacherId: newCourse.value.teacherId,
        studentIds: newCourse.value.students.join(',')
      };

      await axios.post('/api/recurrent-courses', payload);
    }

    showAddCourseModal.value = false;
    fetchCourses(); // Refresh courses list

  } catch (error) {
    console.error('Failed to add course:', error);
  }
}

onMounted(async () => {
  await fetchTeachers()
  await fetchStudents()
  await fetchCourses()
})
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
  
  /* Main container styles */
  .main-container {
    display: flex;
  min-height: 100vh;
  position: relative;
  z-index: 1;
  }
  
  .main-content {
    flex: 1;
  padding: 30px;
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  overflow-y: auto;
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
  
  /* Titles and headings */
  .welcome-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 30px;
    text-align: center;
    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 20px;
    font-weight: 600;
    color: rgba(255, 255, 255, 0.9);
  }
  
  /* Add Course Button */
  .add-course-container {
    display: flex;
    justify-content: center;
    margin-bottom: 30px;
  }
  
  .btn-add-course {
    background: rgba(46, 204, 113, 0.8);
    color: white;
    border: none;
    border-radius: 50px;
    padding: 12px 30px;
    font-size: 16px;
    font-weight: 600;
    display: flex;
    align-items: center;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(46, 204, 113, 0.3);
  }
  
  .btn-add-course:hover {
    background: rgba(46, 204, 113, 1);
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(46, 204, 113, 0.4);
  }
  
  .plus-icon {
    font-size: 20px;
    margin-right: 8px;
    font-weight: bold;
  }
  
  /* Course Cards Grid */
  .courses-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 25px;
  }
  
  .course-card {
    padding: 0;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    height: 100%;
  }
  
  .course-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
  }
  
  .course-image {
    height: 180px;
    overflow: hidden;
  }
  
  .course-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s ease;
  }
  
  .course-card:hover .course-image img {
    transform: scale(1.05);
  }
  
  .course-content {
    padding: 20px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
  }
  
  .course-title {
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 15px;
    color: white;
  }
  
  .course-details {
    margin-bottom: 15px;
  }
  
  .detail-item {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
  }
  
  .detail-icon {
    margin-right: 10px;
    font-size: 16px;
  }
  
  .course-students {
    margin-top: auto;
    margin-bottom: 15px;
  }
  
  .students-label {
    font-weight: 600;
    margin-bottom: 8px;
  }
  
  .students-list {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
  }
  
  .student-badge {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 50px;
    padding: 4px 10px;
    font-size: 12px;
  }
  
  .card-actions {
    display: flex;
    gap: 10px;
    margin-top: 10px;
  }
  
  /* Button styles */
  .btn-join,
  .btn-details {
    padding: 8px 16px;
    border-radius: 8px;
    border: none;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    flex: 1;
    text-align: center;
  }
  
  .btn-join {
    background: rgba(46, 204, 113, 0.8);
    color: white;
  }
  
  .btn-join:hover {
    background: rgba(46, 204, 113, 1);
  }
  
  .btn-details {
    background: rgba(255, 255, 255, 0.2);
    color: white;
  }
  
  .btn-details:hover {
    background: rgba(255, 255, 255, 0.3);
  }
  
  /* Modal styles */
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 100;
  }
  
  .modal-content {
    width: 500px;
    max-width: 90%;
    max-height: 90vh;
    overflow-y: auto;
    margin-top: 30px;
  }
  
  .modal-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
  }
  
  /* Form styles */
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: 500;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  input,
  select {
    width: 100%;
    padding: 12px;
    border-radius: 10px;
    border: 1px solid rgba(255, 255, 255, 0.2);
    outline: none;
    background: rgba(255, 255, 255, 0.1);
    color: white;
    font-size: 16px;
    transition: all 0.3s ease;
  }
  
  input:focus,
  select:focus {
    border-color: rgba(255, 255, 255, 0.5);
    box-shadow: 0 0 15px rgba(255, 255, 255, 0.1);
  }
  
  input::placeholder {
    color: rgba(255, 255, 255, 0.6);
  }
  
  .student-selection {
    max-height: 150px;
    overflow-y: auto;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 10px;
    padding: 10px;
  }
  
  .student-checkbox {
    margin-bottom: 8px;
    display: flex;
    align-items: center;
  }
  
  .student-checkbox input[type="checkbox"] {
    width: auto;
    margin-right: 8px;
  }
  
  /* Responsive design */
  @media (max-width: 1024px) {
    .courses-grid {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  @media (max-width: 768px) {
    .courses-grid {
      grid-template-columns: 1fr;
    }
    
    .course-image {
      height: 220px;
    }
    
    .glass-panel {
      padding: 20px;
    }
    
    .welcome-title {
      font-size: 26px;
    }
  }
  </style>