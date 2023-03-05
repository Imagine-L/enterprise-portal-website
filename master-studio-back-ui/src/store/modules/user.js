import storage from "@/utils/storage";
import {authConstant, userMutations} from "@/constant";

const user = {
    state() {
        return {
            currentUser: {
                uid: Number,
                username: '',
                nickname: '',
                token: '',
                admin: false
            }
        }
    },
    getters: {
        token(state) {
            return state.currentUser.token
        },
        uid(state) {
            return state.currentUser.uid
        },
        username(state) {
            return state.currentUser.username
        },
        nickname(state) {
          return state.currentUser.nickname
        },
        isAdmin(state) {
            return state.currentUser.admin
        }
    },
    mutations: {
        // 保存用户token信息
        [userMutations.SAVE_USER_TOKEN](state, data) {
            state.currentUser = data
            storage.saveSessionObject(authConstant.STORAGE_KEY, data)
        },
        // 从本地的token中恢复用户信息
        [userMutations.RESTORE_USER_BY_TOKEN](state) {
            let currentUser = storage.getSessionObject(authConstant.STORAGE_KEY);
            if (currentUser !== null) {
                state.currentUser = currentUser
            }
        },
        [userMutations.REMOVE_LOCAL_TOKEN](state) {
            storage.remove(authConstant.STORAGE_KEY)
            state.currentUser = {
                uid: Number,
                username: '',
                nickname: '',
                token: '',
            }
        }
    },
    actions: {}
}

export default user
