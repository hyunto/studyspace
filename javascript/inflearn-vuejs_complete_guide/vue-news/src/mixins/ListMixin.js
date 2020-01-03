import bus from '../utils/bus';

export default {
  // 재사용할 컴포넌트 옵션 & 로직을 입력한다.
  async created() {
    bus.$emit('start:spinner');
    await new Promise(resolve => setTimeout(resolve, 2000));
    this.$store.dispatch('FETCH_LIST', this.$route.name);
    console.log('fetched');
    bus.$emit('end:spinner');
  }
}