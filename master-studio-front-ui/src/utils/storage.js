export default {
    saveSessionString(key, value) {
        window.localStorage.setItem(key, value)
    },
    getSessionString(key) {
        return window.localStorage.getItem(key)
    },
    saveSessionObject(key, value) {
        window.localStorage.setItem(key, JSON.stringify(value))
    },
    getSessionObject(key) {
        return JSON.parse(window.localStorage.getItem(key))
    },
    remove(key) {
        return window.localStorage.removeItem(key)
    },
    sessionKeyIsEmpty(key) {
        let val = this.getSessionString(key)
        return val != null && val !== ''
    }
}
