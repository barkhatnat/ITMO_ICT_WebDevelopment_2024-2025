<template>
  <div class="hall-manager">
    <h2>Hall Manager</h2>

    <button @click="showAddHallModal">Add New Hall</button>

    <HallTable
        :halls="halls"
        @edit="editHall"
        @delete="deleteHall"
    />

    <Modal v-if="showModal" @close="closeModal">
      <HallBuilder
          :hall="hallForm"
          :isEditing="isEditing"
          @save="saveHall"
          @cancel="closeModal"
          class="modal-content"
      />
    </Modal>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useHallStore } from '@/stores/hall';
import HallTable from './HallTable.vue';
import Modal from "@/components/Modal.vue";
import HallBuilder from "./HallBuilder.vue";

export default {
  components: {
    HallTable,
    Modal,
    HallBuilder,
  },
  setup() {
    const hallStore = useHallStore();
    const halls = computed(() => hallStore.halls);
    const showModal = ref(false);
    const isEditing = ref(false);
    const hallForm = ref(null);

    const showAddHallModal = () => {
      isEditing.value = false;
      hallForm.value = { name: '', rows: [] };
      showModal.value = true;
    };

    const editHall = (hall) => {
      isEditing.value = true;
      hallForm.value = JSON.parse(JSON.stringify(hall));
      showModal.value = true;
    };

    const saveHall = async () => {
      showModal.value = false;
    };

    const deleteHall = async (hallId) => {
      await hallStore.deleteHall(hallId);
    };

    const closeModal = () => {
      showModal.value = false;
    };

    onMounted(async () => {
      await hallStore.fetchHalls();
    });

    return {
      halls,
      showModal,
      isEditing,
      hallForm,
      showAddHallModal,
      editHall,
      saveHall,
      deleteHall,
      closeModal,
    };
  },
};
</script>
