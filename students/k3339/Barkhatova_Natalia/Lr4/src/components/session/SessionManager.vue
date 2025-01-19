<template>
  <div class="session-manager">
    <h2>Session Manager</h2>

    <button @click="showAddSessionModal">Add New Session</button>

    <SessionTable
        :sessions="sessions"
        @edit="editSession"
        @delete="deleteSession"
    />

    <Modal v-if="showModal" @close="closeModal">
      <h3>{{ isEditing ? 'Edit' : 'Add' }} Session</h3>
      <form @submit.prevent="saveSession">
        <div>
          <label for="movie">Movie</label>
          <select v-model="sessionForm.movieId" id="movie" required>
            <option v-for="movie in movies" :key="movie.id" :value="movie.id">{{ movie.name }}</option>
          </select>
          <p v-if="errors.movieId" class="error">{{ errors.movieId }}</p>
        </div>
        <div>
          <label for="hall">Hall</label>
          <select v-model="sessionForm.hallId" id="hall" required>
            <option v-for="hall in halls" :key="hall.id" :value="hall.id">{{ hall.name }}</option>
          </select>
          <p v-if="errors.hallId" class="error">{{ errors.hallId }}</p>
        </div>
        <div>
          <label for="start-time">Start Time</label>
          <input v-model="sessionForm.startTime" type="datetime-local" id="start-time" required />
          <p v-if="errors.startTime" class="error">{{ errors.startTime }}</p>
        </div>
        <div>
          <label for="price">Price</label>
          <input v-model="sessionForm.price" type="number" id="price" required min="1" />
          <p v-if="errors.price" class="error">{{ errors.price }}</p>
        </div>
        <button type="submit">{{ isEditing ? 'Save Changes' : 'Add Session' }}</button>
      </form>
    </Modal>
  </div>
</template>

<script>
import { computed, onMounted, ref } from 'vue';
import { useSessionStore } from '@/stores/session.js';
import { useMovieStore } from '@/stores/movie.js';
import SessionTable from './SessionTable.vue';
import Modal from '../Modal.vue';
import {useHallStore} from "@/stores/hall.js";

export default {
  components: { SessionTable, Modal },
  setup() {
    const sessionStore = useSessionStore();
    const movieStore = useMovieStore();
    const hallStore = useHallStore();
    const sessions = computed(() => sessionStore.sessions);
    const movies = computed(() => movieStore.movies);
    const halls = computed(() => hallStore.halls);

    const showModal = ref(false);
    const isEditing = ref(false);
    const errors = ref({});
    const sessionForm = ref({
      movieId: '',
      hallId: '',
      startTime: '',
      price: '',
    });

    const showAddSessionModal = () => {
      isEditing.value = false;
      sessionForm.value = { movieId: '', hallId: '', startTime: '', price: '' };
      showModal.value = true;
    };

    const editSession = (session) => {
      isEditing.value = true;
      sessionForm.value = {
        movieId: session.movie.id,
        hallId: session.hall.id,
        startTime: session.startTime.slice(0, 16),
        price: session.price,
      };
      showModal.value = true;
    };

    const saveSession = async () => {
      try {
        if (isEditing.value) {
          await sessionStore.updateSession(sessionForm.value);
        } else {
          await sessionStore.addSession(sessionForm.value);
        }
        showModal.value = false;
        sessionForm.value = { movieId: '', hallId: '', startTime: '', price: '' };
        errors.value = {};
      } catch (error) {
        errors.value = error.response?.data || {};
      }
    };

    const deleteSession = async (sessionId) => {
      await sessionStore.deleteSession(sessionId);
    };

    const closeModal = () => {
      showModal.value = false;
    };

    onMounted(async () => {
      await movieStore.fetchMovies();
      await hallStore.fetchHalls();
      await sessionStore.fetchSessions();
    });

    return {
      sessions,
      movies,
      halls,
      showModal,
      isEditing,
      sessionForm,
      errors,
      showAddSessionModal,
      editSession,
      saveSession,
      deleteSession,
      closeModal
    };
  },
};
</script>

<style scoped>
.session-manager {
  padding: 20px;
}
button {
  padding: 10px;
  margin: 10px;
}
</style>
