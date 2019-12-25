<template>
  <div>
    <ul class="news-list">
      <li v-for="item in fetchedAsks" v-bind:key="item" class="post">
        <!-- 포인트 영역 -->
        <div class="points">
          {{ item.points }}
        </div>
        <!-- 기타 정보 영역 -->
        <div>
          <p class="news-title">
            <router-link v-bind:to="item.url">
              {{ item.title }}
            </router-link>
          </p>
          <small class="link-text">
            {{ item.time_ago }} by 
            <router-link v-bind:to="`/user/${item.user}`" class="link-text">{{ item.user }}</router-link>
          </small>
        </div>
      </li>
    </ul>
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
.news-list {
  margin: 0;
  padding: 0;
}
.post {
  list-style: none;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #eee;
}
.points {
  width: 80px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #42b883;
}
.news-title {
  margin: 0;
}
.link-text {
  color: #828282;
}
</style>