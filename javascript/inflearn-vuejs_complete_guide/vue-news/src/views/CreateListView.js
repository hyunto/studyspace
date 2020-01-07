import ListView from './ListView.vue';
import bus from '../utils/bus.js';

export default function createListView(name) {
  return {
    // 재사용할 인스턴스(컴포넌트) 옵션들이 들어갈 자리
    name: name,
    // ----------------------------------------------------------------------
    // 아래의 내용(Spinner)는 라우터 네비게이션 가드 + 믹스인으로 로직 이동
    // async created() {
    //   bus.$emit('start:spinner');
    //   await new Promise(resolve => setTimeout(resolve, 2000));
    //   this.$store.dispatch('FETCH_LIST', this.$route.name);
    //   console.log('fetched');
    //   bus.$emit('end:spinner');
    // },
    // ----------------------------------------------------------------------
    render(createElement) {
      return createElement(ListView);
    }
  } 
}