<template>
  <div>
    <list-item :items="this.$store.state.list"></list-item>
  </div>
</template>

<script>
import ListItem from '../components/ListItem';

// ListView의 역할 : 각 View마다 필요한 데이터를 조회해서 ListItem 컴포넌트로 넘겨준다.
export default {
  components: {
    ListItem,
  }
}
</script>

<style>

</style>