<template>
  <div
      class="seat"
      :class="[
        seat.type.toLowerCase(),
        { selected: isSelected },
        { occupied: isOccupied }
      ]"
      @click="handleClick"
  >
    {{ seat.number }}
  </div>

  <Modal v-if="showEditor && isAdmin" @close="closeEditor">
    <h3 class="modal-title">Edit Seat</h3>
    <form @submit.prevent="saveSeat" class="seat-form">
      <label for="seat-number">
        Seat Number:
        <input
            id="seat-number"
            v-model="editableSeat.number"
            type="number"
            min="1"
            required
            class="form-input"
        />
      </label>
      <label for="seat-type">
        Seat Type:
        <select
            id="seat-type"
            v-model="editableSeat.type"
            class="form-select"
        >
          <option value="STANDARD">Standard</option>
          <option value="VIP">VIP</option>
          <option value="HANDICAPPED">Handicapped</option>
          <option value="COUPLE">Couple</option>
        </select>
      </label>

      <div class="modal-actions">
        <button type="submit" class="save-btn">Save</button>
        <button type="button" class="delete-btn" @click="removeSeat">Delete</button>
      </div>
    </form>
  </Modal>
</template>

<script>
import {computed, defineComponent, onMounted, ref, watch} from 'vue';
import Modal from '@/components/Modal.vue';
import {useAuthStore} from "@/stores/auth.js";
import {useTicketStore} from "@/stores/ticket.js";
import {useOccupiedSeatsStore} from "@/stores/occupiedSeats.js";

export default defineComponent({
  components: {Modal},
  props: {
    seat: {
      type: Object,
      required: true,
    },
    isAdmin: {
      type: Boolean,
      default: false,
    },
    sessionId: {
      type: String,
      required: true
    },
  },
  emits: ['update', 'remove', 'select'],
  setup(props, {emit}) {
    const showEditor = ref(false);
    const editableSeat = ref({...props.seat});
    const isSelected = ref(false);
    const authStore = useAuthStore();
    const ticketStore = useTicketStore();
    const isAdmin = computed(() => authStore.isAdmin);
    const seatsStore = useOccupiedSeatsStore();
    const isOccupied = computed(() => {
      return seatsStore.occupiedSeats.has(`${props.sessionId}-${props.seat.id}`);
    });


    const fetchStatus = () => {
      seatsStore.fetchSeatStatus(props.sessionId, props.seat.id);
    };


    onMounted(() => {
      fetchStatus(props.sessionId, props.seat.id);
    });

    watch(
        () => props.ticket,
        fetchStatus(props.sessionId, props.seat.id),
        {immediate: true}
    );

    const openEditor = () => {
      if (!isAdmin.value) return;
      showEditor.value = true;
      editableSeat.value = {...props.seat};
    };

    const closeEditor = () => {
      showEditor.value = false;
    };

    const saveSeat = () => {
      emit('update', {...editableSeat.value});
      closeEditor();
    };

    const removeSeat = () => {
      emit('remove');
      closeEditor();
    };

    const handleClick = () => {
      if (isOccupied.value) return;
      if (isAdmin.value) {
        openEditor();
      } else {
        isSelected.value = !isSelected.value;
        emit('select', {seat: props.seat, selected: isSelected.value});

        if (isSelected.value) {
          ticketStore.addSeat(props.seat);
        } else {
          ticketStore.removeSeat(props.seat);
        }
      }
    };

    return {
      showEditor,
      editableSeat,
      isSelected,
      openEditor,
      closeEditor,
      saveSeat,
      removeSeat,
      handleClick,
      isAdmin,
      isOccupied,
    };
  },
});

</script>

<style scoped>
.seat {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 5px;
  font-size: 14px;
  font-weight: bold;
  color: #fff;
  cursor: pointer;
  text-align: center;
  background-color: #ccc;
}

.seat.standard {
  background-color: #9ef7b2;
}

.seat.vip {
  background-color: #c0c9f3;
}

.seat.handicapped {
  background-color: #f7c378;
}

.seat.couple {
  background-color: #fbcbcc;
}

.seat.selected {
  border: 3px solid #007bff;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.7);
}

.seat.occupied {
  background-color: lightgray;
  pointer-events: none;
  opacity: 0.6;
}

</style>
