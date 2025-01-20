<template>
  <div class="ticket-purchase">
    <div class="session-info">
      <h2>{{ session?.movie?.name }}</h2>
      <p><strong>Time:</strong> {{ formattedStartTime }}</p>
      <p><strong>Hall:</strong> {{ session?.hall?.name }}</p>
    </div>

    <div class="hall-selection">
      <h3>Select Your Seats</h3>
      <HallBuilder
          :hall="session?.hall"
          :isEditing="false"
      />
    </div>
    <button class="buy-ticket-btn" @click="buyTickets">Buy Ticket</button>
  </div>
</template>

<script>
import {computed, defineComponent, onMounted} from 'vue';
import HallBuilder from '@/components/hall/HallBuilder.vue';
import {useRoute} from 'vue-router';
import {useSessionStore} from '@/stores/session.js';
import {useMovieStore} from '@/stores/movie.js';
import {useHallStore} from '@/stores/hall.js';
import {useAuthStore} from "@/stores/auth.js";
import {useTicketStore} from "@/stores/ticket.js";

export default defineComponent({
  components: {HallBuilder},
  setup() {
    const route = useRoute();
    const sessionId = route.params.sessionId;

    const sessionStore = useSessionStore();
    const authStore = useAuthStore();
    const ticketStore = useTicketStore();
    const movieStore = useMovieStore();
    const hallStore = useHallStore();
    const selectedSeats = computed(() => ticketStore.selectedSeats);
    const session = computed(() => {
      return sessionStore.sessions.find(s => s.id === sessionId);
    });

    const formattedStartTime = computed(() => {
      if (!session.value) return '';
      const date = new Date(session.value.startTime);
      return date.toLocaleString('en-GB', {
        weekday: 'short',
        day: 'numeric',
        month: 'short',
        hour: '2-digit',
        minute: '2-digit',
      });
    });

    const buyTickets = async () => {
      if (!session.value || selectedSeats.length === 0) {
        alert("Please select at least one seat.");
        return;
      }
      try {
        const ticketPromises = selectedSeats.value.map(seat => {
          const ticket = {
            userId: authStore.user.id,
            sessionId: session.value.id,
            seatId: seat.id,
          };
          return ticketStore.addTicket(ticket);
        });

        await Promise.all(ticketPromises);
        alert("Tickets successfully purchased!");
        ticketStore.clearSelectedSeats();
      } catch (error) {
        console.error("Error purchasing tickets:", error);
        alert("An error occurred while purchasing tickets.");
      }
    };


    onMounted(() => {
      sessionStore.fetchSessions();
      movieStore.fetchMovies();
      hallStore.fetchHalls();
    })

    return {
      session,
      formattedStartTime,
      buyTickets,
    };
  },
});
</script>

<style scoped>
.ticket-purchase {
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 10px;
  max-width: 800px;
  margin: 0 auto;
}

.session-info {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.session-info h2 {
  margin: 0;
  font-size: 24px;
}

.session-info p {
  margin: 5px 0;
  font-size: 16px;
}

.hall-selection {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.buy-ticket-btn {
  background-color: #4caf50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
}

.buy-ticket-btn:hover {
  background-color: #45a049;
}
</style>
