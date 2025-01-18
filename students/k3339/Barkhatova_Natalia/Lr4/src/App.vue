<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <router-link to="/about">About</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
nav {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

router-link {
  text-decoration: none;
  color: blue;
}

router-link.active {
  font-weight: bold;
  color: darkblue;
}
</style>


