<template>
  <div>
    <p>id: {{ item.id }}</p>
    <p>title: {{ item.title }}</p>
  </div>
</template>

<script>
export default {
  computed: {
    item() {
      return this.$store.state.item;
    }
  },
  created() {
    const id = this.$route.query.id;
    this.$store.dispatch('FETCH_ASK_ITEM', id);
  }
}
</script>

<style>

</style>