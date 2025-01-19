<template>
  <div class="registration-form">
    <h1>Регистрация</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="email">Email</label>
        <input
            id="email"
            v-model="form.email"
            type="email"
            placeholder="Введите email"
            class="form-control"
            required
            @input="clearError('email')"
        />
        <p v-if="errors.email" class="error">{{ errors.email }}</p>
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input
            id="password"
            v-model="form.password"
            type="password"
            placeholder="Введите пароль"
            class="form-control"
            required
            @input="clearError('password')"
        />
        <p v-if="errors.password" class="error">{{ errors.password }}</p>
      </div>

      <div class="form-group">
        <label for="firstName">Имя</label>
        <input
            id="firstName"
            v-model="form.firstName"
            type="text"
            placeholder="Введите имя"
            class="form-control"
            required
            @input="clearError('firstName')"
        />
        <p v-if="errors.firstName" class="error">{{ errors.firstName }}</p>
      </div>

      <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input
            id="lastName"
            v-model="form.lastName"
            type="text"
            placeholder="Введите фамилию"
            class="form-control"
            required
            @input="clearError('lastName')"
        />
        <p v-if="errors.lastName" class="error">{{ errors.lastName }}</p>
      </div>

      <div class="form-group">
        <label for="middleName">Отчество (необязательно)</label>
        <input
            id="middleName"
            v-model="form.middleName"
            type="text"
            placeholder="Введите отчество"
            class="form-control"
            @input="clearError('middleName')"
        />
        <p v-if="errors.middleName" class="error">{{ errors.middleName }}</p>
      </div>

      <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import api from "@/api/api.js";

export default {
  name: "RegistrationForm",
  setup() {
    const form = ref({
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      middleName: "",
      roleId: ""
    });

    const errors = reactive({});
    const router = useRouter();

    const submitForm = async () => {
      try {
        form.value.roleId = "223e4567-e89b-12d3-a456-426614174001";
        await api.post("/auth/registration", form.value);
        alert("Регистрация прошла успешно!");
        router.push("/login");
        errors.value = {};
      } catch (error) {
        if (error.response && error.response.data) {
          const serverErrors = error.response.data;
          for (const key in serverErrors) {
            if (Object.prototype.hasOwnProperty.call(serverErrors, key)) {
              errors[key] = serverErrors[key];
            }
          }
        } else {
          console.error("Ошибка при регистрации:", error);
        }
      }
    };

    const clearError = (field) => {
      if (errors[field]) {
        delete errors[field];
      }
    };

    return {
      form,
      errors,
      submitForm,
      clearError,
    };
  },
};
</script>

<style scoped>
.registration-form {
  max-width: 500px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 15px;
}

.error {
  color: red;
  font-size: 0.9em;
}
</style>
