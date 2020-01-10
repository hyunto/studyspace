<template>
  <div>
    <ul>
      <li v-for="item in items" :key="item">
        {{ item }}
      </li>
    </ul>
    <!-- 핵심내용 Start -->
    <button @click="$emit('renew')">renew items</button>
    <!-- 핵심내용 End -->
  </div>
</template>

<script>
export default {
  props: {
    items: {
      type: Array,
      required: true,
    }
  }
}
</script>

<style>

</style>