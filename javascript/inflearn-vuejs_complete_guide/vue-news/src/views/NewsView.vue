<template>
  <div>
    news
    <div v-for="user in users">{{ user }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: []
    }
  },
  created() {
    var _this = this;
    axios.get('https://api.hnpwa.com/v0/news/1.json')
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