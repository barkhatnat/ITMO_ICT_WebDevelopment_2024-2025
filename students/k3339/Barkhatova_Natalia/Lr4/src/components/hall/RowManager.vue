<template>
  <div class="row-manager">
    <div class="row-header">
      <span>Row #{{ row.number }}</span>
      <button v-if="isAdmin" class="add-seat-btn" @click="addSeat">+ Seat</button>
      <button v-if="isAdmin" class="remove-row-btn" @click="$emit('remove')">Remove Row</button>
    </div>
    <div>{{ errorMessage }}</div>
    <div class="seats-container">
      <div
          v-for="(seat, index) in row.seats"
          :key="seat.id"
          class="seat"
      >
        <SeatManager
            :seat="seat"
            :sessionId="sessionId"
            @update="updateSeat(index, $event)"
            @remove="removeSeat(index)"
        />
      </div>
    </div>
  </div>
</template>


<script>
import {computed, defineComponent, ref} from "vue";
import Modal from "@/components/Modal.vue";
import SeatManager from "@/components/hall/SeatManager.vue";
import {useAuthStore} from "@/stores/auth.js";

export default defineComponent({
  components: {SeatManager, Modal},
  props: {
    row: {
      type: Object,
      required: true,
    },
    errors: {
      type: Object,
      default: () => ({}),
    },
    isAdmin: {
      type: Boolean,
      default: false,
    },
    sessionId: {
      type: String,
      default: "",
    }
  },

  computed: {
    errorMessage() {
      return this.errors || null;
    },
  },

  emits: ["update", "remove"],
  setup(props, {emit}) {
    const showSeatEditor = ref(false);
    const currentSeat = ref(null);
    const currentSeatIndex = ref(null);
    const authStore = useAuthStore();
    const {sessionId} = props;
    const isAdmin = computed(() => authStore.isAdmin);

    const addSeat = () => {
      const newSeat = {number: props.row.seats.length + 1, type: "STANDARD"};
      props.row.seats.push(newSeat);
      emit("update", props.row);
    };

    const updateSeat = (index, newSeat) => {
      props.row.seats[index] = newSeat;
      emit("update", props.row);
    };

    const removeSeat = (index) => {
      props.row.seats.splice(index, 1);
      emit("update", props.row);
    };

    return {
      showSeatEditor,
      currentSeat,
      currentSeatIndex,
      addSeat,
      updateSeat,
      removeSeat,
      isAdmin,
      sessionId
    };
  },
});
</script>


<style scoped>
.row-manager {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.row-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}


.add-seat-btn,
.remove-row-btn {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 12px;
}

.remove-row-btn {
  background-color: #f44336;
}

.add-seat-btn:hover,
.remove-row-btn:hover {
  opacity: 0.9;
}


.seats-container {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}


</style>


