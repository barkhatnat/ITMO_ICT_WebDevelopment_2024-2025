import { defineStore } from 'pinia';
import api from "@/api/api.js";

export const useHallStore = defineStore({
    id: 'hall',
    state: () => ({
        halls: [],
    }),
    actions: {
        async fetchHalls() {
            try {
                const response = await api.get('/rest/halls');
                this.halls = response.data;
            } catch (error) {
                console.error('Failed to fetch halls:', error.response.data);
            }
        },

        async addHall(hall) {
            const response = await api.post('/rest/admin/halls', hall);
            this.halls.push(response.data);
        },


        async updateHall(hall) {
            try {
                const response = await api.put(`/rest/admin/halls/${hall.id}`, hall);
                const index = this.halls.findIndex((h) => h.id === hall.id);
                this.halls[index] = response.data;
            } catch (error) {
                console.error('Failed to update hall:', error.response.data);
            }
        },

        async deleteHall(hallId) {
            try {
                await api.delete(`/rest/admin/halls`, { data: { id: hallId } });
                this.halls = this.halls.filter((hall) => hall.id !== hallId);
            } catch (error) {
                console.error('Failed to delete hall:', error.response.data);
            }
        },
    },
});
