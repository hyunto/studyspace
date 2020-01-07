import bus from '../utils/bus';

export default {
  // 재사용할 컴포넌트 옵션 & 로직을 입력한다.
  created() {
    // ----------------------------------------------------------------------
    // 아래의 내용(Spinner)는 라우터 네비게이션 가드 + 믹스인으로 로직 이동
    // bus.$emit('start:spinner');
    // this.$store.dispatch('FETCH_LIST', this.$route.name)
    //   .then(() => {
    //     console.log('fetched');
    //     bus.$emit('end:spinner');
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
    // ----------------------------------------------------------------------
  },
  mounted() {
    bus.$emit('end:spinner');
  }
}