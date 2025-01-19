<template>
  <div class="session-table">
    <table>
      <thead>
      <tr>
        <th>Movie</th>
        <th>Hall</th>
        <th>Start Time</th>
        <th>Price</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="session in sessions" :key="session.id">
        <td>{{ session.movie.name }}</td>
        <td>{{ session.hall.name }}</td>
        <td>{{ session.startTime }}</td>
        <td>{{ session.price }}</td>
        <td>
          <button @click="edit(session)">Edit</button>
          <button @click="deleteSession(session.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    sessions: Array,
  },
  methods: {
    edit(session) {
      this.$emit('edit', session);
    },
    deleteSession(sessionId) {
      this.$emit('delete', sessionId);
    },
  },
};
</script>

<style scoped>
.session-table table {
  width: 100%;
  border-collapse: collapse;
}
.session-table th, .session-table td {
  padding: 10px;
  text-align: left;
}
.session-table button {
  padding: 5px 10px;
  margin: 5px;
}
</style>
