<template>
  <div class="hall-table">
    <table>
      <thead>
      <tr v-for="hall in halls" :key="hall.id">
        <td>{{ hall.name }}</td>
        <td>{{ hall.capacity }}</td>
        <td>
          <button
              @click="edit(hall)" :disabled="isHallRelatedToSession(hall.id)">
            Edit
          </button>
          <button @click="deleteHall(hall.id)" :disabled="isHallRelatedToSession(hall.id)">
            Delete
          </button>
        </td>
      </tr>
      </thead>
      <tbody></tbody>
    </table>
  </div>
</template>

<script>
import {useTicketStore} from '@/stores/ticket';
import {onMounted, ref} from "vue";

export default {
  props: {
    halls: Array,
  },
  setup(props, {emit}) {
    const ticketStore = useTicketStore();

    const hallSessionsMap = ref(new Map());

    const loadTickets = async () => {
      await ticketStore.fetchTickets(); // Загружаем билеты
      ticketStore.tickets.forEach(ticket => {
        hallSessionsMap.value.set(ticket.session.hall.id, true);
      });
    };

    onMounted(() => {
      loadTickets();
    });

    const isHallRelatedToSession = (hallId) => {
      return hallSessionsMap.value.has(hallId);
    };

    const edit = (hall) => {
      emit('edit', hall);
    };

    const deleteHall = (hallId) => {
      emit('delete', hallId);
    };

    return {
      isHallRelatedToSession,
      edit,
      deleteHall,
    };
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
