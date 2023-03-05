import { createStore } from 'vuex'
import user from "@/store/modules/user";
import navigation from "@/store/modules/navigation";

const store = createStore({
    modules: {
        user,
        navigation
    },
    state() {
        return {

        }
    },
    mutations: {
    },
    actions: {

    }
})

export default store
