<template>
  <!-- Before -->
  <!-- <input type="checkbox" v-model="checked"> -->

  <!-- After -->
  <input type="checkbox" :value="value" @click="toggleCheckBox">
</template>

<script>
export default {
  props: {
    value: Boolean,
  },
  methods: {
    toggleCheckBox() {
      this.$emit('input', !this.value);
    }
  }
}
</script>

<style>

</style>