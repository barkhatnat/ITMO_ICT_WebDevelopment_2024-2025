<template>
  <div class="movie-table">
    <table>
      <thead>
      <tr>
        <th>Name</th>
        <th>Duration (mins)</th>
        <th>Description</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="movie in movies" :key="movie.id">
        <td>{{ movie.name }}</td>
        <td>{{ movie.duration }}</td>
        <td>{{ movie.description }}</td>
        <td>
          <button @click="edit(movie)" :disabled="isMovieRelatedToSessions(movie.id)">Edit</button>
          <button @click="deleteMovie(movie.id)" :disabled="isMovieRelatedToSessions(movie.id)">
            Delete
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useSessionStore } from '@/stores/session';

export default {
  props: {
    movies: Array,
  },
  setup(props, { emit }) {
    const sessionStore = useSessionStore();
    const movieSessionsMap = ref(new Map());

    const loadSessions = async () => {
      await sessionStore.fetchSessions(); // Загружаем сеансы
      sessionStore.sessions.forEach(session => {
        movieSessionsMap.value.set(session.movie.id, true);
      });
    };

    onMounted(() => {
      loadSessions();
    });

    const isMovieRelatedToSessions = (movieId) => {
      return movieSessionsMap.value.has(movieId);
    };

    const edit = (movie) => {
      if (isMovieRelatedToSessions(movie.id)) {
        alert('You cannot edit this movie because it has associated sessions.');
        return;
      }
      emit('edit', movie);
    };


    const deleteMovie = (movieId) => {
      if (isMovieRelatedToSessions(movieId)) {
        alert('You cannot delete this movie because it has associated sessions.');
        return;
      }
      emit('delete', movieId);
    };

    return {
      isMovieRelatedToSessions,
      edit,
      deleteMovie,
    };
  },
};
</script>

<style scoped>
.movie-table table {
  width: 100%;
  border-collapse: collapse;
}

.movie-table th, .movie-table td {
  padding: 10px;
  text-align: left;
}

.movie-table button {
  padding: 5px 10px;
  margin: 5px;
}
</style>
