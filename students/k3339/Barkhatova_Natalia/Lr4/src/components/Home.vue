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

    <!-- Для обычного пользователя -->
    <p v-else>
      <router-link to="/session-board">Active sessions</router-link>
    </p>


  </div>
</template>

<script>
export default {
  name: 'Home',
  props: {
    isAdmin: {
      type: Boolean,
      default: false,
    },
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
