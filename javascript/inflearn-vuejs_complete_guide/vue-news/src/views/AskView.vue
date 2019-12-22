<template>
  <div>
    ask
    <div v-for="ask in asks">{{ ask }}</div>
  </div>
</template>

<script>
import { fetchAskList } from '../api/index';

export default {
  data() {
    return {
      asks: []
    }
  },
  created() {
    fetchAskList()
      .then((response) => {
        console.log(response);
        this.asks = response.data;
      })
      .catch((error) => {
        console.log(error);
      })
  }
}
</script>

<style>

</style>