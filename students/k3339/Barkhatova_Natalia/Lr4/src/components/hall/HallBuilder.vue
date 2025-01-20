<template>
  <div class="hall-builder">
    <h2 v-if="isAdmin">{{ isEditing ? 'Edit Hall' : 'Create Hall' }}</h2>

    <div v-if="isAdmin" class="input-container">
      <label for="hall-name">Hall Name:</label>
      <input
          id="hall-name"
          v-model="hall.name"
          type="text"
          placeholder="Enter hall name"
      />
      <p v-if="errors.name" class="error">{{ errors.name }}</p>
    </div>

    <div class="legend">
      <div class="legend-item">
        <div class="color-box standard"></div>
        <span>Standard</span>
      </div>
      <div class="legend-item">
        <div class="color-box vip"></div>
        <span>VIP</span>
      </div>
      <div class="legend-item">
        <div class="color-box handicapped"></div>
        <span>HANDICAPPED</span>
      </div>
      <div class="legend-item">
        <div class="color-box couple"></div>
        <span>COUPLE</span>
      </div>
    </div>

    <div class="rows-container" v-if="hall?.rows">
      <RowManager
          v-for="(row, index) in hall.rows"
          :key="index"
          :row="row"
          :errors="getRowError(index)"
          :sessionId="sessionId"
          @update="updateRow(index, $event)"
          @remove="removeRow(index)"
      />
      <p v-if="errors.rows" class="error">{{ errors.rows }}</p>
    </div>

    <button v-if="isAdmin" class="add-row-btn" @click="addRow">+ Add Row</button>
    <button v-if="isAdmin" class="save-btn" @click="saveHall">Save Hall</button>
  </div>
</template>

<script>
import {computed, defineComponent, ref} from "vue";
import RowManager from "./RowManager.vue";
import {useHallStore} from "@/stores/hall";
import {useAuthStore} from "@/stores/auth.js";

export default defineComponent({
  components: {RowManager},
  props: {
    hall: {
      type: Object,
      default: () => ({rows: []}),
    },
    isEditing: {
      type: Boolean,
      default: false,
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
  setup(props, {emit}) {
    const errors = ref({});
    const showErrors = ref(false);
    const hallStore = useHallStore();
    const {sessionId} = props;
    const authStore = useAuthStore();
    const isAdmin = computed(() => authStore.isAdmin);
    const addRow = () => {
      props.hall.rows.push({
        number: props.hall.rows.length + 1,
        seats: [],
      });
    };

    const updateRow = (rowIndex, updatedRow) => {
      props.hall.rows.splice(rowIndex, 1, updatedRow);
    };

    const removeRow = (rowIndex) => {
      props.hall.rows.splice(rowIndex, 1);
      props.hall.rows.forEach((row, index) => {
        row.number = index + 1;
      });
    };

    const getRowError = (index) => {
      return errors.value[`rows[${index}].seats`] || null;
    };

    const saveHall = async () => {
      showErrors.value = true;
      try {
        if (props.isEditing) {
          await hallStore.updateHall(props.hall);
        } else {
          await hallStore.addHall(props.hall);
        }

        errors.value = {};
        emit("save", props.hall);
      } catch (error) {
        if (error.response && error.response.data) {
          const serverErrors = error.response.data;
          for (const key in serverErrors) {
            if (Object.prototype.hasOwnProperty.call(serverErrors, key)) {
              errors.value[key] = serverErrors[key];
            }
          }
        } else {
          console.error("Ошибка при сохранении:", error);
        }
      }
    };

    return {
      errors,
      showErrors,
      addRow,
      updateRow,
      removeRow,
      saveHall,
      getRowError,
      sessionId,
      isAdmin
    };
  },
});
</script>

<style scoped>
.hall-builder {
  padding: 0px;
}

.rows-container {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.add-row-btn {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.add-row-btn:hover {
  background-color: #45a049;
}

.legend {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
  align-items: center;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.color-box {
  width: 20px;
  height: 20px;
  border-radius: 3px;
}

.color-box.standard {
  background-color: #9ef7b2;
}

.color-box.vip {
  background-color: #c0c9f3;
}

.color-box.handicapped {
  background-color: #f7c378;
}

.color-box.couple {
  background-color: #fbcbcc;
}

.error {
  color: red;
}
</style>
