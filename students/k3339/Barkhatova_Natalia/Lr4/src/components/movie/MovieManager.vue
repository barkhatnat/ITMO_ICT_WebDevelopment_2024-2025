<template>
  <div class="movie-manager">
    <h2>Movie Manager</h2>

    <button @click="showAddMovieModal">Add New Movie</button>

    <MovieTable
        :movies="movies"
        @edit="editMovie"
        @delete="deleteMovie"
    />

    <Modal v-if="showModal" @close="closeModal">
      <h3>{{ isEditing ? 'Edit' : 'Add' }} Movie</h3>
      <form @submit.prevent="saveMovie">
        <div>
          <label for="name">Name</label>
          <input v-model="movieForm.name" type="text" id="name" required/>
          <p v-if="errors.name" class="error">{{ errors.name }}</p>
        </div>
        <div>
          <label for="description">Description</label>
          <textarea v-model="movieForm.description" id="description" required></textarea>
          <p v-if="errors.description" class="error">{{ errors.description }}</p>
        </div>
        <div>
          <label for="duration">Duration (in minutes)</label>
          <input v-model="movieForm.duration" type="number" id="duration" required min="1"/>
          <p v-if="errors.duration" class="error">{{ errors.duration }}</p>
        </div>
        <button type="submit">{{ isEditing ? 'Save Changes' : 'Add Movie' }}</button>
      </form>
    </Modal>
  </div>
</template>

<script>
import {computed, onMounted, ref} from 'vue';
import {useMovieStore} from '@/stores/movie.js';
import MovieTable from './MovieTable.vue';
import Modal from '../Modal.vue';

export default {
  components: {
    MovieTable,
    Modal,
  },
  setup() {
    const movieStore = useMovieStore();
    const movies = computed(() => movieStore.movies);
    const showModal = ref(false);
    const isEditing = ref(false);
    const errors = ref({});
    const movieForm = ref({
      title: '',
      description: '',
      releaseDate: '',
    });

    const showAddMovieModal = () => {
      isEditing.value = false;
      movieForm.value = {title: '', description: '', releaseDate: ''};
      showModal.value = true;
    };

    const editMovie = (movie) => {
      isEditing.value = true;
      movieForm.value = {...movie};
      showModal.value = true;
    };

    const saveMovie = async () => {
      try {
        if (isEditing.value) {
          await movieStore.updateMovie(movieForm.value);
        } else {
          await movieStore.addMovie(movieForm.value);
        }
        showModal.value = false;
        movieForm.value = {title: '', description: '', releaseDate: ''};
        errors.value = {};
      } catch (error) {
        if (error.response && error.response.data) {
          const serverErrors = error.response.data;
          for (const key in serverErrors) {
            if (Object.prototype.hasOwnProperty.call(serverErrors, key)) {
              errors.value[key] = serverErrors[key];
            }
          }
        } else {
          console.error("Ошибка при сохранении:", error);
        }
      }
    };


    const deleteMovie = async (movieId) => {
      await movieStore.deleteMovie(movieId);
    };

    const closeModal = () => {
      showModal.value = false;
    };

    onMounted(async () => {
      console.log('Fetching movies on page load...');
      await movieStore.fetchMovies();
      console.log(movieStore.movies);
    });

    return {
      movies,
      errors,
      showModal,
      isEditing,
      movieForm,
      showAddMovieModal,
      editMovie,
      saveMovie,
      deleteMovie,
      closeModal
    };
  },
};
</script>

<style scoped>
.movie-manager {
  padding: 20px;
}

button {
  padding: 10px;
  margin: 10px;
}
</style>