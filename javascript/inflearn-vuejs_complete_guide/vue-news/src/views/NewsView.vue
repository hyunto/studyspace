<template>
  <div>
    <list-item v-bind:items="this.$store.state.news"></list-item>
  </div>
</template>

<script>
import ListItem from '../components/ListItem';

export default {
  components: {
    ListItem,
  },
  created() {
    this.$store.dispatch('FETCH_NEWS');
  }
}
</script>

<style>
</style>