<template>
  <div>
    <list-item :items="fetchedAsks"></list-item>
  </div>
</template>

<script>
// import { mapState } from 'vuex';
import { mapGetters } from 'vuex';
import ListItem from '../components/ListItem'
import bus from '../utils/bus.js';

export default {
  components: {
    ListItem
  },
  computed: {
    // #1: this.$store.state 사용
    // asks() {
    //   return this.$store.state.asks;
    // }

    // #2: mapState 사용
    // ...mapState({
    //   asks: state => state.asks
    // })

    // #3: mapGetters로 Key 이름 매핑하여 사용
    // ...mapGetters({
    //   fetchedAsks: 'fetchedAsks'
    // })

    // #4: Getter와 Key 이름이 같은 경우 mapGetters 사용 방법
    ...mapGetters([
      'fetchedAsks'
    ])
  },
  async created() {
    bus.$emit('start:spinner');
    await new Promise(resolve => setTimeout(resolve, 2000));
    this.$store.dispatch('FETCH_LIST', this.$route.name);
    console.log('fetched');
    bus.$emit('end:spinner');
  }
}
</script>

<style>
</style>