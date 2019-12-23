<template>
  <div>
    jobs
    <div v-for="job in this.$store.state.jobs">{{ job }}</div>
  </div>
</template>

<script>
import { fetchJobsList } from '../api/index';

export default {
  created() {
    this.$store.dispatch('FETCH_JOBS');
  }
}
</script>

<style>

</style>