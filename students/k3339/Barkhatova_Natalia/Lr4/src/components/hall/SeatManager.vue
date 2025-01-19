<template>
  <div
      class="seat"
      :class="seat.type.toLowerCase()"
      @click="openEditor"
  >
    {{ seat.number }}
  </div>

  <Modal
      v-if="showEditor"
      @close="closeEditor"
  >
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

      <!-- Действия -->
      <div class="modal-actions">
        <button type="submit" class="save-btn">Save</button>
        <button type="button" class="delete-btn" @click="removeSeat">Delete</button>
      </div>
    </form>
  </Modal>
</template>

<script>
import Modal from "@/components/Modal.vue";

export default {
  components: {Modal},
  props: {
    seat: {
      type: Object,
      required: true,
    },
  },
  emits: ["update", "remove"],
  data() {
    return {
      showEditor: false,
      editableSeat: {...this.seat},
    };
  },
  methods: {
    openEditor() {
      this.showEditor = true;
      this.editableSeat = {...this.seat};
    },
    closeEditor() {
      this.showEditor = false;
    },
    saveSeat() {
      this.$emit("update", {...this.editableSeat});
      this.closeEditor();
    },
    removeSeat() {
      this.$emit("remove");
      this.closeEditor();
    },
  },
};
</script>

<style scoped>
.modal-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.seat-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-input,
.form-select {
  padding: 10px;
  font-size: 14px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-top: 5px;
}

.form-input:focus,
.form-select:focus {
  border-color: #007bff;
  outline: none;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.save-btn,
.delete-btn {
  padding: 8px 15px;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.save-btn {
  background-color: #4caf50;
  color: white;
  border: none;
}

.save-btn:hover {
  background-color: #45a049;
}

.delete-btn {
  background-color: #f44336;
  color: white;
  border: none;
}

.delete-btn:hover {
  background-color: #e53935;
}

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

</style>
