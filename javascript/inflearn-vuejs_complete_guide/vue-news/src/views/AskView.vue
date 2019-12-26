<template>
  <div>
    <list-item v-bind:items="fetchedAsks"></list-item>
  </div>
</template>

<script>
// import { mapState } from 'vuex';
import { mapGetters } from 'vuex';
import ListItem from '../components/ListItem'

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
  created() {
    this.$store.dispatch('FETCH_ASKS');
  }
}
</script>

<style>
</style>