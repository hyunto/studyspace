<template>
  <div>
    news
    <div v-for="user in users">{{ user }}</div>
  </div>
</template>

<script>
import { fetchNewsList } from '../api/index.js';

export default {
  data() {
    return {
      users: []
    }
  },
  created() {
    var _this = this;
    fetchNewsList()
      .then(function(response) {
        console.log(response);
        _this.users = response.data;
      })
      .catch(function(error) {
        console.log(error);
      });
  }
}
</script>

<style>

</style>