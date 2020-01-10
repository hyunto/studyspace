<template>
  <div>
    <!-- Before -->
    <!-- <check-box :checked="checked"></check-box> -->

    <!-- After -->
    <check-box v-model="checked"></check-box>
  </div>
</template>

<script>
import CheckBox from './components/CheckBox';

export default {
  components: {
    CheckBox,
  },
  data() {
    return {
      checked: false
    }
  }
}
</script>

<style>

</style>