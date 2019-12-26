<template>
  <div>
    <list-item v-bind:items="this.$store.state.jobs"></list-item>
  </div>
</template>

<script>
import ListItem from '../components/ListItem';

export default {
  components: {
    ListItem,
  },
  created() {
    this.$store.dispatch('FETCH_JOBS');
  }
}
</script>

<style>
</style>