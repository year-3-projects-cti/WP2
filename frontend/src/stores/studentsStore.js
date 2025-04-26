import { defineStore } from 'pinia'
import axios from 'axios'

export const useStudentsStore = defineStore('students', {
  state: () => ({
    students: [],
    loading: false,
    error: null
  }),
  actions: {
    async fetchStudents() {
      this.loading = true
      try {
        const response = await axios.get('/api/students')
        this.students = response.data
      } catch (error) {
        this.error = 'Failed to fetch students'
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    async addStudent(student) {
      try {
        await axios.post('/api/students', student, {
          headers: { 'Content-Type': 'application/json' }
        })
        await this.fetchStudents()
      } catch (error) {
        this.error = 'Failed to add student'
        console.error(error)
      }
    },
    async updateStudent(id, student) {
      try {
        await axios.put(`/api/students/${id}`, student, {
          headers: { 'Content-Type': 'application/json' }
        })
        await this.fetchStudents()
      } catch (error) {
        this.error = 'Failed to update student'
        console.error(error)
      }
    },
    async deleteStudent(id) {
      try {
        await axios.delete(`/api/students/${id}`)
        await this.fetchStudents()
      } catch (error) {
        this.error = 'Failed to delete student'
        console.error(error)
      }
    }
  }
})