<script>
import { ref, onMounted } from "vue";
import axios from "axios";

export default {
  setup() {
    const users = ref([]);
    const loading = ref(true);
    const searchQuery = ref("");
    const showModal = ref(false);
    const selectedUser = ref(null);

    // Fetch users from backend
    const fetchUsers = async () => {
      loading.value = true;
      try {
        const response = await axios.get("http://localhost:8080/api/users");
        users.value = response.data;
        setTimeout(() => {
          loading.value = false;
        }, 800); // Add slight delay for loading animation
      } catch (error) {
        console.error("Error fetching users:", error);
        loading.value = false;
      }
    };

    const filteredUsers = () => {
      if (!searchQuery.value) return users.value;
      return users.value.filter(user => 
        user.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        user.email.toLowerCase().includes(searchQuery.value.toLowerCase())
      );
    };

    const viewUserDetails = (user) => {
      selectedUser.value = user;
      showModal.value = true;
    };

    const closeModal = () => {
      showModal.value = false;
    };

    onMounted(fetchUsers);

    return { 
      users, 
      loading, 
      searchQuery, 
      filteredUsers, 
      viewUserDetails, 
      selectedUser, 
      showModal, 
      closeModal 
    };
  },
};
</script>

<template>
  <div class="container">
    <div class="header-section">
      <h1>Lista Utilizatori</h1>
      <div class="search-bar">
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="Caută utilizatori..." 
          class="search-input"
        />
      </div>
    </div>

    <!-- Loading animation -->
    <div v-if="loading" class="loading-container">
      <div class="spinner"></div>
      <p>Se încarcă utilizatorii...</p>
    </div>

    <div v-else class="table-container">
      <div v-if="filteredUsers().length === 0" class="no-results">
        Nu au fost găsite rezultate pentru "{{ searchQuery }}"
      </div>
      <table v-else>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nume</th>
            <th>Email</th>
            <th>Acțiuni</th>
          </tr>
        </thead>
        <tbody>
          <tr 
            v-for="user in filteredUsers()" 
            :key="user.id"
            class="user-row"
            @click="viewUserDetails(user)"
          >
            <td>{{ user.id }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>
              <button class="view-btn">Vezi detalii</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- User Details Modal -->
    <div v-if="showModal" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h2>Detalii Utilizator</h2>
          <button class="close-btn" @click="closeModal">×</button>
        </div>
        <div v-if="selectedUser" class="modal-body">
          <div class="user-avatar">{{ selectedUser.name.charAt(0) }}</div>
          <div class="user-info">
            <div class="info-row">
              <strong>ID:</strong> 
              <span>{{ selectedUser.id }}</span>
            </div>
            <div class="info-row">
              <strong>Nume:</strong> 
              <span>{{ selectedUser.name }}</span>
            </div>
            <div class="info-row">
              <strong>Email:</strong> 
              <span>{{ selectedUser.email }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');

.container {
  max-width: 1000px;
  margin: auto;
  padding: 30px;
  font-family: 'Roboto', sans-serif;
  color: #333;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 2px solid #eaeaea;
}

h1 {
  font-size: 32px;
  color: #2c3e50;
  font-weight: 500;
  margin: 0;
  background: linear-gradient(45deg, #3498db, #8e44ad);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  animation: titleFade 1.5s ease-in-out;
}

@keyframes titleFade {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

.search-bar {
  position: relative;
}

.search-input {
  padding: 10px 15px;
  border: none;
  border-radius: 20px;
  width: 250px;
  font-size: 14px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  box-shadow: 0 2px 10px rgba(52, 152, 219, 0.3);
  width: 300px;
}

.table-container {
  overflow: hidden;
  border-radius: 8px;
  animation: fadeIn 0.5s ease-in-out;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

th, td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background: linear-gradient(to right, #3498db, #2980b9);
  color: white;
  font-weight: 500;
  letter-spacing: 0.5px;
}

.user-row {
  transition: all 0.2s ease;
  cursor: pointer;
}

.user-row:hover {
  background-color: #f5f9ff;
  transform: translateY(-2px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.view-btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 12px;
}

.view-btn:hover {
  background-color: #2980b9;
  transform: scale(1.05);
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
}

.spinner {
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-radius: 50%;
  border-top: 4px solid #3498db;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-bottom: 15px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.no-results {
  text-align: center;
  padding: 30px;
  color: #7f8c8d;
  font-style: italic;
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from { opacity: 0; transform: translateY(-30px); }
  to { opacity: 1; transform: translateY(0); }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #eee;
}

.modal-header h2 {
  margin: 0;
  font-size: 18px;
  font-weight: 500;
  color: #2c3e50;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #7f8c8d;
}

.modal-body {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: linear-gradient(45deg, #3498db, #8e44ad);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
}

.user-info {
  width: 100%;
}

.info-row {
  display: flex;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.info-row strong {
  width: 100px;
  color: #7f8c8d;
}

.info-row span {
  flex: 1;
  color: #2c3e50;
}
</style>