// import storage from "@/utils/storage";
import {forgetMutations} from "@/constant";

const forget = {
    state() {
        return {
            tempToken: ''
        }
    },
    getters: {
        tempToken(state) {
            return state.tempToken
        }
    },
    mutations: {
        [forgetMutations.SAVE_TEMP_TOKEN](state, tempToken) {
            state.tempToken = tempToken
        }
    },
    actions: {}
}

export default forget
