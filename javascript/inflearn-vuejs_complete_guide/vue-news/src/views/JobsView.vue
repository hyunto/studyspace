<template>
  <div>
    <p v-for="item in this.$store.state.jobs" v-bind:key="item">
      <a :href="item.url">{{ item.title }}</a>
      <small>{{ item.time_ago }}, {{ item.domain }}</small>
    </p>
  </div>
</template>

<script>
export default {
  created() {
    this.$store.dispatch('FETCH_JOBS');
  }
}
</script>

<style>

</style>