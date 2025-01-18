<template>
  <div>
    <form @submit.prevent="handleLogin">
      <input type="email" v-model="email" placeholder="Email" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <button type="submit" :disabled="authStore.loading">Login</button>
    </form>
    <p v-if="authStore.error" class="error">{{ authStore.error }}</p>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/auth.js';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const email = ref('');
    const password = ref('');
    const authStore = useAuthStore();
    const router = useRouter();

    const handleLogin = async () => {
      const success = await authStore.login({ email: email.value, password: password.value });
      if (success) {
        router.push('/');
      }
    };

    return { email, password, authStore, handleLogin };
  },
};
</script>

<style>
.error {
  color: red;
}
</style>
