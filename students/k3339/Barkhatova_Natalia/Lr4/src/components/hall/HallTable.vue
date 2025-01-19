<template>
  <div class="hall-table">
    <table>
      <thead>
      <tr v-for="hall in halls" :key="hall.id">
        <td>{{ hall.name }}</td>
        <td>{{ hall.capacity }}</td>
        <td>
          <button @click="edit(hall)">Edit</button>
          <button @click="deleteHall(hall.id)">Delete</button>
        </td>
      </tr>
      <tr v-for="row in hall?.rows" :key="row.id">
        <td colspan="2">Row {{ row.number }}</td>
        <td>
          <ul>
            <li v-for="seat in row?.seats" :key="seat.id">
              Seat {{ seat.number }} ({{ seat.type }})
            </li>
          </ul>
        </td>
      </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    halls: Array,
  },
  methods: {
    edit(hall) {
      this.$emit('edit', hall);
    },
    deleteHall(hallId) {
      this.$emit('delete', hallId);
    },
  },
};
</script>

<style scoped>
.hall-table table {
  width: 100%;
  border-collapse: collapse;
}

.hall-table th, .hall-table td {
  padding: 10px;
  text-align: left;
}

.hall-table button {
  padding: 5px 10px;
  margin: 5px;
}
</style>