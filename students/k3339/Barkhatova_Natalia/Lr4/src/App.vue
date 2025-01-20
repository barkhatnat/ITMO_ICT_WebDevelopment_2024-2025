<template>
  <div id="app">
    <nav>
      <router-link to="/">Home</router-link>
      <a href="#" @click.prevent="handleLogout">Logout</a>
    </nav>
    <router-view/>
  </div>
</template>


<
<script>
import {useAuthStore} from '@/stores/auth';
import {useRouter} from 'vue-router';

export default {
  name: 'App',
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();

    const handleLogout = async () => {
      await authStore.logout();
      router.push('/login');
    };
    return {
      handleLogout
    };
  }
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


