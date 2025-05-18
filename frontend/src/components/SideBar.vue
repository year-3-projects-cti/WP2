<script setup>
import { useRoute } from 'vue-router';
import { useUsersStore } from '@/stores/usersStore';
import { computed } from 'vue';

const route = useRoute();
const usersStore = useUsersStore();

const menuItems = [
  { path: '/interface', label: 'Dashboard', icon: 'fas fa-house-user', roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { path: '/students', label: 'Students', icon: 'fas fa-users', roles: ['ADMIN'] },
  { path: '/teachers', label: 'Teachers', icon: 'fas fa-user-tie', roles: ['ADMIN'] },
  { path: '/courses', label: 'Courses', icon: 'fas fa-book-open', roles: ['ADMIN', 'TEACHER'] },
  { path: '/calendar', label: 'Calendar', icon: 'fas fa-calendar-check', roles: ['TEACHER', 'STUDENT'] },
  { path: '/payments', label: 'Payments', icon: 'fas fa-wallet', roles: ['STUDENT'] },
  { path: '/reports', label: 'Reports', icon: 'fas fa-chart-pie', roles: ['ADMIN'] },
  { path: '/messages', label: 'Messages', icon: 'fas fa-envelope-open-text', badge: 5, roles: ['ADMIN', 'TEACHER', 'STUDENT'] },
  { path: '/settings', label: 'Settings', icon: 'fas fa-sliders-h', roles: ['ADMIN', 'TEACHER', 'STUDENT'] }
];

const filteredMenu = computed(() => {
  if (!usersStore.user) return [];
  const role = usersStore.user.role || '';
  return menuItems.filter(item => item.roles.includes(role));
});

function isActive(path) {
  return route.path.startsWith(path);
}
</script>