import { createStore } from 'vuex'
import user from "@/store/modules/user";
import forget from "@/store/modules/forget";

const store = createStore({
    modules: {
        user,
        forget
    },
    state() {

    },
    mutations: {

    },
    actions: {

    }
})

export default store
