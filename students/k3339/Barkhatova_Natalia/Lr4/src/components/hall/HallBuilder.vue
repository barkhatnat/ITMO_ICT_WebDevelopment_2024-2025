<template>
  <div class="hall-builder">
    <h2>Create Hall</h2>
    <div class="input-container">
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
    <div class="rows-container">
      <RowManager
          v-for="(row, index) in hall.rows"
          :key="index"
          :row="row"
          :errors="getRowError(index)"
          @update="updateRow(index, $event)"
          @remove="removeRow(index)"
      />
      <p v-if="errors.rows" class="error">{{ errors.rows }}</p>
    </div>
    <button class="add-row-btn" @click="addRow">+ Add Row</button>
    <button class="save-btn" @click="saveHall">Save Hall</button>
  </div>
</template>

<script>
import {ref} from "vue";
import RowManager from "./RowManager.vue";
import {useHallStore} from "@/stores/hall.js";

export default {
  components: { RowManager },
  emits: ["save", "cancel"],
  computed: {
    getRowError() {
      return (index) => this.errors[`rows[${index}].seats`] || null;
    }
  },
  setup(_, { emit }) {
    const hallStore = useHallStore();
    const hall = ref({
      name: '',
      rows: [
        { number: 1, seats: [{ number: 1, type: 'STANDARD' }] }
      ],
    });
    const errors = ref({});
    const addRow = () => {
      hall.value.rows.push({
        number: hall.value.rows.length + 1,
        seats: [],
      });
    };

    const updateRow = (rowIndex, updatedRow) => {
      hall.value.rows.splice(rowIndex, 1, updatedRow);
    };

    const removeRow = (rowIndex) => {
      hall.value.rows.splice(rowIndex, 1);
      hall.value.rows.forEach((row, index) => {
        row.number = index + 1;
      });
    };

    const saveHall = async () => {
      try {
        await hallStore.addHall(hall.value);
        errors.value = {};
        emit("save", hall.value);
      } catch (error) {
        if (error.response && error.response.data) {
          const serverErrors = error.response.data;
          for (const key in serverErrors) {
            if (Object.prototype.hasOwnProperty.call(serverErrors, key)) {
              errors.value[key] = serverErrors[key]; // Заполняем ошибки
            }
          }
        } else {
          console.error("Ошибка при сохранении:", error);
        }
      }
    };



    return {
      hall,
      errors,
      addRow,
      updateRow,
      removeRow,
      saveHall,
    };
  },
};
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
</style>
