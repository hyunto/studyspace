<template>
  <div>
    <list-item :items="this.$store.state.jobs"></list-item>
  </div>
</template>

<script>
import ListItem from '../components/ListItem';
import bus from '../utils/bus.js';

export default {
  components: {
    ListItem,
  },
  async created() {
    bus.$emit('start:spinner');
    await new Promise(resolve => setTimeout(resolve, 2000));
    this.$store.dispatch('FETCH_JOBS');
    console.log('fetched');
    bus.$emit('end:spinner');
  }
}
</script>

<style>
</style>