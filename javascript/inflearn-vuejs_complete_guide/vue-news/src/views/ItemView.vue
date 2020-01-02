<template>
  <div>
    <!-- 사용자 정보 -->
    <section>
      <user-profile :info="fetchedItem">
        <router-link slot="username" :to="`/user/${fetchedItem.user}`">{{ fetchedItem.user }}</router-link>
        <template slot="time">{{ 'Posted ' + fetchedItem.time_ago }}</template>
      </user-profile>
    </section>

    <!-- 타이틀 -->
    <section>
      <h2>
        {{ fetchedItem.title }}
      </h2>
    </section>
    
    <!-- 내용 -->
    <section>
      <div v-html="fetchedItem.content"></div>
    </section>
  </div>
</template>

<script>
import UserProfile from '../components/UserProfile';
import { mapGetters } from 'vuex';

export default {
  components: {
    UserProfile,
  },
  computed: {
    ...mapGetters(['fetchedItem']),
  },
  created() {
    const id = this.$route.query.id;
    this.$store.dispatch('FETCH_ASK_ITEM', id);
  }
}
</script>

<style>
</style>