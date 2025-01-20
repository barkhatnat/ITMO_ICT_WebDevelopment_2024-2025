<template>
  <div class="tickets">
    <h1>Your Tickets</h1>

    <div v-if="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <ul v-if="tickets.length">
      <li v-for="ticket in tickets" :key="ticket.id" class="ticket-item">
        <div>
          <strong>Ticket Code:</strong> {{ ticket.ticketCode }}
        </div>
        <div>
          <strong>Movie:</strong> {{ ticket.session.movie.name }}
        </div>
        <div>
          <strong>Hall:</strong> {{ ticket.session.hall.name }}
        </div>
        <div>
          <strong>Session Time:</strong> {{ formatDate(ticket.session.startTime) }}
        </div>
        <div>
          <strong>Seat:</strong> {{ ticket.seat.number }} ({{ ticket.seat.type }})
        </div>
        <div>
          <strong>Purchased At:</strong> {{ formatDate(ticket.purchasedAt) }}
        </div>
      </li>
    </ul>
    <div v-else>No tickets available.</div>
  </div>
</template>

<script>
import {onMounted, ref} from 'vue';
import {useTicketStore} from "@/stores/ticket.js";

export default {
  name: 'UserTickets',
  setup() {
    const ticketStore = useTicketStore();
    const tickets = ref([]);
    const loading = ref(true);
    const error = ref(null);

    const fetchTickets = async () => {
      try {
        await ticketStore.fetchCurrentUserTickets();  // Загружаем билеты через store
        tickets.value = ticketStore.tickets;  // Получаем билеты из хранилища
      } catch (err) {
        error.value = 'Failed to load tickets. Please try again later.';
      } finally {
        loading.value = false;
      }
    };

    const formatDate = (dateString) => {
      const date = new Date(dateString);
      const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
      };
      return date.toLocaleString('en-GB', options);
    };

    onMounted(() => {
      fetchTickets();
    });

    return {
      tickets,
      loading,
      error,
      formatDate,
    };
  },
};
</script>

<style scoped>
.tickets {
  padding: 20px;
}

.ticket-item {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.ticket-item strong {
  display: inline-block;
  margin-right: 5px;
}

.error {
  color: red;
}
</style>
