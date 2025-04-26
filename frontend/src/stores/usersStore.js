import { defineStore } from 'pinia'
import axios from 'axios'

export const useUsersStore = defineStore('users', {
  state: () => ({
    user: null,
    loading: false,
    error: null
  }),
  actions: {
    async fetchUser(id) {
      this.loading = true
      try {
        const response = await axios.get(`/api/users/${id}`)
        this.user = response.data
      } catch (error) {
        console.error('Failed to fetch user', error)
        this.error = 'Failed to fetch user'
      } finally {
        this.loading = false
      }
    }
  }
})