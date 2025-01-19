<template>
  <div class="session-poster">
    <div class="poster-header">
      <h3>{{ session.movie.name }}</h3>
      <p>{{ session.hall.name }}</p>
    </div>
    <div class="poster-body">
      <p><strong>Start Time:</strong> {{ formattedStartTime }}</p>
      <p><strong>Price:</strong> {{ session.price }} $</p>
      <p><strong>Description:</strong> {{ session.movie.description || 'No description available' }}</p>
    </div>
    <router-link :to="`/sessions/${session.id}/buy`" class="buy-ticket-button">
      Buy Ticket
    </router-link>
  </div>
</template>

<script>
import {computed} from 'vue';

export default {
  props: {
    session: Object, // Данные о сеансе
  },
  setup(props) {
    const formattedStartTime = computed(() => {
      const date = new Date(props.session.startTime);
      const options = {
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
      };
      return date.toLocaleString('en-GB', options);
    });

    return {
      formattedStartTime,
    };
  },
};
</script>


<style scoped>
.session-poster {
  width: 250px;
  height: 250px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin: 20px;
  transition: transform 0.3s ease;
  justify-content: center;
  display: compact;
  align-items: center;
}

.session-poster:hover {
  transform: scale(1.05);
}

.poster-header {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.poster-body {
  font-size: 14px;
  margin-bottom: 15px;
}

</style>
