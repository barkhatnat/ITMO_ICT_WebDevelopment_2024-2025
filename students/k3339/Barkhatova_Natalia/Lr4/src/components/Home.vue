<template>
  <div class="home">
    <h1>Welcome to the Cinema Management System</h1>

    <p>
      <router-link to="/halls">Go to Hall Manager</router-link>
    </p>

    <p>
      <router-link to="/movies">Go to Movie Manager</router-link>
    </p>
  </div>
</template>

<script>
export default {
  name: 'Home',
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
