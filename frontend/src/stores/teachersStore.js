import { defineStore } from 'pinia'
import axios from 'axios'

export const useTeachersStore = defineStore('teachers', {
  state: () => ({
    teachers: [],
    loading: false,
    error: null
  }),
  actions: {
    async fetchTeachers() {
      this.loading = true
      try {
        const response = await axios.get('/api/users?role=TEACHER')
        this.teachers = response.data
      } catch (error) {
        this.error = 'Failed to fetch teachers'
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    async addTeacher(teacher) {
      try {
        teacher.role = 'TEACHER'
        await axios.post('/api/users', teacher)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to add teacher'
        console.error(error)
      }
    },
    async updateTeacher(id, teacher) {
      try {
        await axios.put(`/api/users/${id}`, teacher)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to update teacher'
        console.error(error)
      }
    },
    async deleteTeacher(id) {
      try {
        await axios.delete(`/api/users/${id}`)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to delete teacher'
        console.error(error)
      }
    }
  }
})
