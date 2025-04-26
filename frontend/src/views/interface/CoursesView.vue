<template>
    <div class="dashboard-container">
      <InterfaceNav />
      <div class="main-container">
        <Sidebar />
        <main class="main-content">
          <div class="glass-panel">
            <h1 class="welcome-title">Course Management</h1>
            
            <!-- Add Course Button -->
            <div class="add-course-container">
              <button class="btn-add-course" @click="showAddCourseModal = true">
                <span class="plus-icon">+</span> Add Course
              </button>
            </div>
            
            <!-- Course Cards Container -->
            <div class="courses-grid">
              <div v-for="course in courses" :key="course.id" class="course-card glass-panel">
                <div class="course-image">
                  <img :src="course.imageUrl" :alt="course.name">
                </div>
                <div class="course-content">
                  <h3 class="course-title">{{ course.name }}</h3>
                  <div class="course-details">
                    <div class="detail-item">
                      <span class="detail-icon">⏰</span>
                      <span class="detail-text">{{ course.hours }} hours</span>
                    </div>
                    <div class="detail-item">
                      <span class="detail-icon">👨‍🏫</span>
                      <span class="detail-text">{{ course.teacher }}</span>
                    </div>
                    <div class="course-details">
  <div class="detail-item">
    <span class="detail-icon">🏫</span>
    <span class="detail-text">{{ course.classroom }}</span>
  </div>
  <div class="detail-item">
    <span class="detail-icon">📅</span>
    <span class="detail-text">{{ dayName(course.day) }}</span> <!-- transformare număr în nume zi -->
  </div>
  <div class="detail-item">
    <span class="detail-icon">🔔</span>
    <span class="detail-text">{{ course.status }}</span>
  </div>
</div>
                  </div>
                  <div class="course-students">
                    <div class="students-label">Students:</div>
                    <div class="students-list">
                      <span v-for="(student, index) in course.students" :key="index" class="student-badge">
                        {{ student }}
                      </span>
                    </div>
                  </div>
                  <div class="card-actions">
                    <button class="btn-details" @click="editCourse(course)">Edit</button>
                    <button class="btn-join" @click="deleteCourse(course.id)">Delete</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
      
      <!-- Add Course Modal -->
      <div class="modal-overlay" v-if="showAddCourseModal" @click.self="showAddCourseModal = false">
        <div class="modal-content glass-panel">
          <h2>Add New Course</h2>
          <form @submit.prevent="addCourse">
            <div class="form-group">
              <label>Course Image URL</label>
              <input v-model="newCourse.imageUrl" type="text" placeholder="Image URL" required />
            </div>
            <div class="form-group">
              <label>Course Name</label>
              <input v-model="newCourse.name" type="text" placeholder="Course Name" required />
            </div>
            <div class="form-group">
              <label>Start Hour</label>
              <input v-model="newCourse.hours" type="number" placeholder="Hourgi" min="1" required />
            </div>
            <div class="form-group">
  <label>Day of the Week (1=Monday, 7=Sunday)</label>
  <input v-model="newCourse.day" type="number" min="1" max="7" placeholder="Enter day (1-7)" />
</div>
<div class="form-group">
  <label>Status</label>
  <select v-model="newCourse.status" required>
    <option value="active">Active</option>
    <option value="canceled">Canceled</option>
  </select>
</div>
            <div class="form-group">
            <label>Classroom / Location</label>
            <input v-model="newCourse.classroom" type="text" placeholder="e.g. Room A101" />
            </div>
            <div class="form-group">
              <label>Assign Teacher</label>
              <select v-model="newCourse.teacher" required>
                <option value="" disabled>Select Teacher</option>
                <option v-for="teacher in teachers" :key="teacher">{{ teacher }}</option>
              </select>
            </div>
            <div class="form-group">
              <label>Assign Students</label>
              <div class="student-selection">
                <div v-for="student in availableStudents" :key="student" class="student-checkbox">
                  <input 
                    type="checkbox" 
                    :id="'student-' + student"
                    :value="student"
                    v-model="newCourse.students"
                  />
                  <label :for="'student-' + student">{{ student }}</label>
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
      
      <!-- Edit Course Modal -->
      <div class="modal-overlay" v-if="showEditCourseModal" @click.self="showEditCourseModal = false">
        <div class="modal-content glass-panel">
          <h2>Edit Course</h2>
          <form @submit.prevent="updateCourse">
            <div class="form-group">
              <label>Course Image URL</label>
              <input v-model="editingCourse.imageUrl" type="text" placeholder="Image URL" required />
            </div>
            <div class="form-group">
              <label>Course Name</label>
              <input v-model="editingCourse.name" type="text" placeholder="Course Name" required />
            </div>
            <div class="form-group">
              <label>Course Hours</label>
              <input v-model="editingCourse.hours" type="number" placeholder="Hours" min="1" required />
            </div>
            <div class="form-group">
            <label>Classroom / Location</label>
            <input v-model="editingCourse.classroom" type="text" placeholder="e.g. Room A101" />
            </div>
            <div class="form-group">
  <label>Day of the Week (1=Monday, 7=Sunday)</label>
  <input v-model="editingCourse.day" type="number" min="1" max="7" placeholder="Enter day (1-7)" />
    </div>
<div class="form-group">
  <label>Status</label>
  <select v-model="newCourse.status" required>
    <option value="active">Active</option>
    <option value="canceled">Canceled</option>
  </select>
</div>
            <div class="form-group">
              <label>Assign Teacher</label>
              <select v-model="editingCourse.teacher" required>
                <option value="" disabled>Select Teacher</option>
                <option v-for="teacher in teachers" :key="teacher">{{ teacher }}</option>
              </select>
            </div>
            <div class="form-group">
              <label>Assign Students</label>
              <div class="student-selection">
                <div v-for="student in availableStudents" :key="student" class="student-checkbox">
                  <input 
                    type="checkbox" 
                    :id="'edit-student-' + student"
                    :value="student"
                    v-model="editingCourse.students"
                  />
                  <label :for="'edit-student-' + student">{{ student }}</label>
                </div>
              </div>
            </div>
            <div class="modal-actions">
              <button class="btn-details" type="button" @click="showEditCourseModal = false">Cancel</button>
              <button class="btn-join" type="submit">Save Changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
    import Sidebar from '@/components/SideBar.vue';
    import InterfaceNav from '@/components/InterfaceNav.vue';

    function dayName(dayNumber) {
  const days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
  return days[dayNumber - 1] || 'Unknown';
}
  
  // Sample data (replace with API calls in production)
  const teachers = ref([
    'Prof. Smith',
    'Dr. Johnson',
    'Mrs. Williams',
    'Mr. Brown',
    'Ms. Davis'
  ]);
  
  const availableStudents = ref([
    'John Doe',
    'Jane Smith',
    'Mike Johnson',
    'Sarah Williams',
    'David Brown',
    'Emily Davis',
    'Alex Wilson',
    'Olivia Martin',
    'Ethan Thompson',
    'Sophia Anderson'
  ]);
  
  const courses = ref([
    {
      id: 1,
      name: 'Introduction to Programming',
      hours: 48,
      teacher: 'Prof. Smith',
      students: ['John Doe', 'Jane Smith', 'Mike Johnson'],
      imageUrl: 'https://via.placeholder.com/300x200?text=Programming',
      classroom: 'Room A101',
      day: 4,                 // VINERI (4 = Friday)
      status: 'active'  
    },
    {
      id: 2,
      name: 'Advanced Mathematics',
      hours: 60,
      teacher: 'Dr. Johnson',
      students: ['Sarah Williams', 'David Brown', 'Emily Davis'],
      imageUrl: 'https://via.placeholder.com/300x200?text=Mathematics',
      classroom: 'Room A101',
      day: 4,                 // VINERI (4 = Friday)
      status: 'active' 
    },
    {
      id: 3,
      name: 'Data Science Fundamentals',
      hours: 36,
      teacher: 'Ms. Davis',
      students: ['Alex Wilson', 'Olivia Martin', 'Ethan Thompson'],
      imageUrl: 'https://via.placeholder.com/300x200?text=Data+Science',
      classroom: 'Room A101',
      day: 4,                 // VINERI (4 = Friday)
      status: 'active'  
    },
    {
      id: 4,
      name: 'Web Development',
      hours: 54,
      teacher: 'Mr. Brown',
      students: ['John Doe', 'Sophia Anderson', 'Mike Johnson'],
      imageUrl: 'https://via.placeholder.com/300x200?text=Web+Development',
      classroom: 'Room A101',
      day: 4,                 // VINERI (4 = Friday)
      status: 'active' 
    },
    {
      id: 5,
      name: 'Graphic Design',
      hours: 42,
      teacher: 'Mrs. Williams',
      students: ['Jane Smith', 'Emily Davis', 'Olivia Martin'],
      imageUrl: 'https://via.placeholder.com/300x200?text=Graphic+Design',
      classroom: 'Room A101',
      day: 4,                 // VINERI (4 = Friday)
      status: 'active'  
    }
  ]);
  
  // State for modals and forms
  const showAddCourseModal = ref(false);
  const showEditCourseModal = ref(false);
  const newCourse = ref({
    name: '',
    hours: null,
    teacher: '',
    students: [],
    imageUrl: '',
    classroom: '',
    day: null,           // aici
  status: 'active'   
  });
  
  const editingCourse = ref({
    id: null,
    name: '',
    hours: null,
    teacher: '',
    students: [],
    imageUrl: '',
    classroom: '',
    day: null,           // aici
  status: 'active'  
  });
  
  // Functions
  const addCourse = () => {
    const courseId = courses.value.length > 0 ? Math.max(...courses.value.map(c => c.id)) + 1 : 1;
    
    courses.value.push({
      id: courseId,
      name: newCourse.value.name,
      hours: newCourse.value.hours,
        classroom: newCourse.value.classroom,
        day: newCourse.value.day,
        status: newCourse.value.status,
      teacher: newCourse.value.teacher,
      students: [...newCourse.value.students],
      imageUrl: newCourse.value.imageUrl || 'https://via.placeholder.com/300x200?text=New+Course'
    });
    
    // Reset form and close modal
    newCourse.value = {
      name: '',
      hours: null,
        classroom: '',
        day: null,          
        status: 'active',
      teacher: '',
      students: [],
      imageUrl: ''
    };
    showAddCourseModal.value = false;
  };
  
  const editCourse = (course) => {
    editingCourse.value = { ...course, students: [...course.students] };
    showEditCourseModal.value = true;
  };
  
  const updateCourse = () => {
    const index = courses.value.findIndex(c => c.id === editingCourse.value.id);
    if (index !== -1) {
      courses.value[index] = { ...editingCourse.value };
    }
    showEditCourseModal.value = false;
  };
  
  const deleteCourse = (id) => {
    if (confirm('Are you sure you want to delete this course?')) {
      courses.value = courses.value.filter(course => course.id !== id);
    }
  };
  
  onMounted(() => {
    // You could fetch courses from an API here
  });
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