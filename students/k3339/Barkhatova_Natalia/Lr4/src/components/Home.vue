<template>
  <div class="home">
    <h1>Welcome to the Cinema Management System</h1>

    <p v-if="isAdmin">
      <router-link to="/halls">Hall Manager</router-link>
    </p>

    <p v-if="isAdmin">
      <router-link to="/movies">Movie Manager</router-link>
    </p>

    <p v-if="isAdmin">
      <router-link to="/sessions">Session Manager</router-link>
    </p>

    <p v-if="!isAdmin">
      <router-link to="/session-board">Active sessions</router-link>
    </p>

    <p v-if="!isAdmin">
      <router-link to="/tickets">Your tickets</router-link>
    </p>
  </div>
</template>

<script>
import {computed} from 'vue';
import {useAuthStore} from '@/stores/auth.js'; // Импортируем authStore

export default {
  name: 'Home',
  setup() {
    const authStore = useAuthStore();
    const isAdmin = computed(() => authStore.isAdmin);
    return {
      isAdmin,
    };
  },
};
</script>

<style scoped>
.home {
  padding: 20px;
  text-align: center;
}

button {
  padding: 10px;
  margin: 10px;
}
</style>