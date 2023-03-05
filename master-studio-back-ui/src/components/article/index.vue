<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: 'ArticleComponent',
  mounted() {
    this.$router.push({path: '/article/main'})
  }
}
</script>

<style scoped>
</style>
