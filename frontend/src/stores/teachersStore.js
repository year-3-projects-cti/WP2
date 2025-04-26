// src/stores/teachersStore.js
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
        const response = await axios.get('/api/teachers')
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
        await axios.post('/api/teachers', teacher)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to add teacher'
        console.error(error)
      }
    },
    async updateTeacher(id, teacher) {
      try {
        await axios.put(`/api/teachers/${id}`, teacher)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to update teacher'
        console.error(error)
      }
    },
    async deleteTeacher(id) {
      try {
        await axios.delete(`/api/teachers/${id}`)
        await this.fetchTeachers()
      } catch (error) {
        this.error = 'Failed to delete teacher'
        console.error(error)
      }
    }
  }
})
