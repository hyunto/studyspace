<template>
  <div>
    <fetch-data url="https://jsonplaceholder.typicode.com/users">
      <div slot-scope="{ response, loading }">  <!-- 핵심 내용 -->
        <div v-if="!loading">  <!-- 핵심 내용 -->
          {{ response }}
        </div> 
        <div v-if="loading">  <!-- 핵심 내용 -->
          Loading...
        </div> 
      </div> 
    </fetch-data>
  </div>
</template>

<script>
import FetchData from './components/FetchData';

export default {
  name: 'app',
  components: {
    FetchData,
  }
}
</script>

<style>

</style>