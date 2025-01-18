import { createRouter, createWebHistory } from "vue-router";
import RegistrationForm from "@/components/auth/RegistrationForm.vue"; // Компонент регистрации
import LoginForm from "@/components/auth/LoginForm.vue";
import Home from "@/components/Home.vue";
import {useAuthStore} from "@/stores/auth.js";
import MovieManager from "@/components/movie/MovieManager.vue";
import HallManager from "@/components/hall/HallManager.vue"; // Страница логина

const routes = [
    {
        path: "/register",
        name: "Register",
        component: RegistrationForm, // Форма регистрации
    },
    {
        path: "/login",
        name: "Login",
        component: LoginForm,
    },
    {
        path: "/",
        name: "Home",
        component: Home,
        meta: { requiresAuth: true },
    },
    {
        path: "/movies",
        name: "Movies",
        component: MovieManager,
        meta: { requiresAuth: true },
    },
    {
        path: "/halls",
        name: "Halls",
        component: HallManager,
        meta: { requiresAuth: true },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from, next) => {
    const authStore = useAuthStore();

    if (!authStore.user && authStore.accessToken) {
        await authStore.fetchUser();
    }

    if (to.meta.requiresAuth && !authStore.accessToken) {
        next('/login');
    } else {
        next();
    }
});
export default router;
