<template>
  <div class="session-board">
    <h2>Movie Sessions</h2>
    <div v-if="isLoading">Loading...</div>
    <div class="session-grid" v-else>
      <SessionPoster
          v-for="session in sessions"
          :key="session.id"
          :session="session"
      />
    </div>
  </div>
</template>

<script>
import { defineComponent, computed, onMounted, ref } from 'vue';
import SessionPoster from './SessionPoster.vue';
import { useSessionStore } from "@/stores/session.js";
import { useMovieStore } from "@/stores/movie.js";
import { useHallStore } from "@/stores/hall.js";

export default defineComponent({
  name: 'SessionBoard',
  components: {
    SessionPoster,
  },
  setup() {
    const sessionStore = useSessionStore();
    const movieStore = useMovieStore();
    const hallStore = useHallStore();
    const isLoading = ref(true);
    const sessions = computed(() => sessionStore.sessions);
    onMounted(async () => {
      if (sessionStore.sessions.length === 0) {
        await sessionStore.fetchSessions();
      }
      isLoading.value = false;
    });

    return {
      sessions,
      isLoading,
    };
  },
});
</script>

<style scoped>
.session-board {
  padding: 20px;
  text-align: center;
}

.session-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.loading {
  font-size: 1.5rem;
  color: #888;
}
</style>
