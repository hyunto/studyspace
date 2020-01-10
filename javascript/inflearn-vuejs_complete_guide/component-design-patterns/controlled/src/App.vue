<template>
  <check-box :checked="checked"></check-box>
</template>

<script>
import CheckBox from './components/CheckBox';

export default {
  components: {
    CheckBox,
  },
  data() {
    return {
      checked: true
    }
  }
}
</script>

<style>

</style>