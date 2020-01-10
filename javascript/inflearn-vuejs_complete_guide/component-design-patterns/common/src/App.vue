<template>
  <div>
    <app-header :title="appTitle"></app-header>
    <!-- 핵심내용 Start -->
    <app-content :items="items" @renew="renewItems"></app-content>
    <!-- 핵심내용 End -->
  </div>
</template>

<script>
import AppHeader from './components/AppHeader';
import AppContent from './components/AppContent';

export default {
  components: {
    AppHeader,
    AppContent
  },
  data() {
    return {
      appTitle: 'Common Approach',
      items: [10, 20, 30]
    }
  },
  methods: {
    renewItems() {
      this.items = [40, 50, 60];
    }
  }
}
</script>

<style>

</style>