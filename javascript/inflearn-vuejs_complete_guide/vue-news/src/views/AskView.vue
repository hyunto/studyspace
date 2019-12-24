<template>
  <div>
    <p v-for="item in fetchedAsks" v-bind:key="item">
      <router-link v-bind:to="`/item?id=${item.id}`">{{ item.title }}</router-link>"
      <small>{{ item.time_ago }} by {{ item.user }}</small>
    </p>
  </div>
</template>

<script>
// import { mapState } from 'vuex';
import { mapGetters } from 'vuex';

export default {
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