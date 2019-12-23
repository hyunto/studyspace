<template>
  <div>
    news
    <div v-for="user in this.$store.state.news">{{ user }}</div>
  </div>
</template>

<script>


export default {
  created() {
    this.$store.dispatch('FETCH_NEWS');
  }
}
</script>

<style>

</style>