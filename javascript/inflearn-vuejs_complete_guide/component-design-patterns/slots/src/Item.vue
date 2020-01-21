<template>
  <li>
    <slot>
      <!-- NOTE: 등록하는 곳에서 정의할 영역 -->
    </slot>
    <template v-if="item">{{ item }}</template>
  </li>
</template>

<script>
export default {
  props: {
    item: String,
  }
}
</script>