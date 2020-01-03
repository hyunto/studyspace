<template>
  <div>
    <list-item :items="this.$store.state.news"></list-item>
  </div>
</template>

<script>
import ListItem from '../components/ListItem';
// import bus from '../utils/bus.js';
import ListMixin from '../mixins/ListMixin';

export default {
  components: {
    ListItem,
  },
  mixins: [
    ListMixin
  ],
  // async created() {
  //   bus.$emit('start:spinner');
  //   await new Promise(resolve => setTimeout(resolve, 2000));
  //   this.$store.dispatch('FETCH_NEWS');
  //   console.log('fetched');
  //   bus.$emit('end:spinner');
  // }
}
</script>

<style>
</style>