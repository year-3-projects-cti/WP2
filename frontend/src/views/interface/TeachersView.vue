<template>
    <div class="dashboard-container">
      <InterfaceNav />
      <div class="main-container">
        <Sidebar />
        <main class="main-content">
          <div class="glass-panel">
            <h1 class="welcome-title">Teachers Management</h1>
  
            <!-- Add Teacher Form -->
            <section class="glass-panel" style="margin-bottom: 30px;">
              <h2>Add New Teacher</h2>
              <form @submit.prevent="addTeacher">
                <div class="form-group">
                  <input v-model="newTeacher.name" type="text" placeholder="Name" required />
                </div>
                <div class="form-group">
                  <input v-model="newTeacher.email" type="email" placeholder="Email" required />
                </div>
                <div class="form-group">
                  <input v-model="newTeacher.subject" type="text" placeholder="Subject" required />
                </div>
                <div class="form-group">
                  <select v-model="newTeacher.status" required>
                    <option value="" disabled>Select status</option>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                  </select>
                </div>
                <button class="btn-join" type="submit">Add Teacher</button>
              </form>
            </section>
  
            <!-- Teachers List -->
            <section class="glass-panel">
              <h2>Teacher List</h2>
              <table class="students-table">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Subject</th>
                    <th>Status</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="teacher in teachersStore.teachers" :key="teacher.id">
                    <td>{{ teacher.name }}</td>
                    <td>{{ teacher.email }}</td>
                    <td>{{ teacher.subject }}</td>
                    <td>{{ teacher.status }}</td>
                    <td>
                      <button class="btn-details" @click="populateEditTeacher(teacher)">Edit</button>
                      <button class="btn-join" @click="deleteTeacher(teacher.id)">Delete</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </section>
  
            <!-- Edit Teacher Form -->
            <section class="glass-panel" v-if="editMode">
              <h2>Edit Teacher</h2>
              <form @submit.prevent="updateTeacher">
                <div class="form-group">
                  <input v-model="editTeacher.name" type="text" placeholder="Name" required />
                </div>
                <div class="form-group">
                  <input v-model="editTeacher.email" type="email" placeholder="Email" required />
                </div>
                <div class="form-group">
                  <input v-model="editTeacher.subject" type="text" placeholder="Subject" required />
                </div>
                <div class="form-group">
                  <select v-model="editTeacher.status" required>
                    <option value="" disabled>Select status</option>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                  </select>
                </div>
                <button class="btn-join" type="submit">Save Changes</button>
                <button class="btn-details" @click="cancelEdit" type="button">Cancel</button>
              </form>
            </section>
          </div>
        </main>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useTeachersStore } from '@/stores/teachersStore'
  import Sidebar from '@/components/SideBar.vue'
  import InterfaceNav from '@/components/InterfaceNav.vue'
  
  const teachersStore = useTeachersStore()
  
  const newTeacher = ref({
    name: '',
    email: '',
    subject: '',
    status: ''
  })
  
  const editMode = ref(false)
  const editTeacher = ref({
    id: null,
    name: '',
    email: '',
    subject: '',
    status: ''
  })
  
  const fetchTeachers = async () => {
    await teachersStore.fetchTeachers()
  }
  
  const addTeacher = async () => {
    await teachersStore.addTeacher(newTeacher.value)
    newTeacher.value = { name: '', email: '', subject: '', status: '' }
  }
  
  const deleteTeacher = async (id) => {
    await teachersStore.deleteTeacher(id)
  }
  
  const populateEditTeacher = (teacher) => {
    editMode.value = true
    editTeacher.value = { ...teacher }
  }
  
  const updateTeacher = async () => {
    await teachersStore.updateTeacher(editTeacher.value.id, editTeacher.value)
    editMode.value = false
  }
  
  const cancelEdit = () => {
    editMode.value = false
    editTeacher.value = { id: null, name: '', email: '', subject: '', status: '' }
  }
  
  onMounted(fetchTeachers)
  </script>
    <style scoped>
    .students-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    
    .students-table th,
    .students-table td {
      padding: 10px;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      text-align: left;
    }
    
    .students-table th {
      background: rgba(255, 255, 255, 0.1);
    }
    
    .students-table tr:hover {
      background: rgba(255, 255, 255, 0.05);
    }
    
    .form-group {
      margin-bottom: 15px;
    }
    
    input,
    select {
      width: 100%;
      padding: 10px;
      border-radius: 8px;
      border: none;
      outline: none;
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }
    
    input::placeholder {
      color: rgba(255, 255, 255, 0.6);
    }
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
  
  /* Form styles */
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
  
  /* Button styles */
  .btn-join,
  .btn-details {
    padding: 10px 20px;
    border-radius: 10px;
    border: none;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    margin-right: 10px;
  }
  
  .btn-join {
    background: rgba(46, 204, 113, 0.8);
    color: white;
  }
  
  .btn-join:hover {
    background: rgba(46, 204, 113, 1);
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(46, 204, 113, 0.3);
  }
  
  .btn-details {
    background: rgba(255, 255, 255, 0.2);
    color: white;
  }
  
  .btn-details:hover {
    background: rgba(255, 255, 255, 0.3);
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(255, 255, 255, 0.2);
  }
  
  /* Table styles */
  .students-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    border-radius: 10px;
    overflow: hidden;
  }
  
  .students-table th,
  .students-table td {
    padding: 15px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    text-align: left;
  }
  
  .students-table th {
    background: rgba(255, 255, 255, 0.15);
    font-weight: 600;
    text-transform: uppercase;
    font-size: 14px;
    letter-spacing: 1px;
  }
  
  .students-table tr:last-child td {
    border-bottom: none;
  }
  
  .students-table tr:hover {
    background: rgba(255, 255, 255, 0.05);
  }
  
  /* For smaller screens */
  @media (max-width: 768px) {
    .glass-panel {
      padding: 20px;
    }
    
    .welcome-title {
      font-size: 26px;
    }
    
    .students-table th,
    .students-table td {
      padding: 10px;
      font-size: 14px;
    }
  }
  </style>