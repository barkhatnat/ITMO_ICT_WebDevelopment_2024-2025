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
          <button @click="edit(movie)">Edit</button>
          <button @click="deleteMovie(movie.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    movies: Array,
  },
  methods: {
    edit(movie) {
      this.$emit('edit', movie);
    },
    deleteMovie(movieId) {
      this.$emit('delete', movieId);
    },
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
