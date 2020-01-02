<template>
  <div>
    <!-- <p>name : {{ userInfo.id }}</p>
    <p>karma : {{ userInfo.karma }}</p>
    <p>created : {{ userInfo.created }}</p>
    <p>about : <span v-html="userInfo.about"></span></p> -->
    <user-profile :info="userInfo"></user-profile>
  </div>
</template>

<script>
import UserProfile from './../components/UserProfile';

export default {
  components: {
    UserProfile,
  },
  computed: {
    userInfo() {
      return this.$store.state.user;
    }
  },
  created() {
    const userName = this.$route.params.id;
    this.$store.dispatch('FETCH_USER', userName);
  }
}
</script>

<style>

</style>