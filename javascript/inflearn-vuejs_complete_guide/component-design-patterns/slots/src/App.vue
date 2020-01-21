<template>
  <div>
    <ul>
      <!-- Slot 방식 -->
      <p>--- Slot 방식 ---</p>
      <item v-for="item in items" :key="item">{{ item }}</item>

      <!-- Props 방식 -->
      <p>--- Props 방식 ---</p>
      <item v-for="item in items" :key="item" :item="item"></item>

      <!-- Slot을 사용하는 이유/장점 : 재사용 할 컴포넌트가 특정 데이터/포맷에 결합되지 않고, 원하는 대로 전달하여 표현할 수 있음 -->
      <p>--- Slot 장점 ---</p>
      <item>
        아이템 1
      </item>
      <item>
        아이템 2 <button>Click Me</button>
      </item>
      <item>
        <small>아이템 3</small>
        <img src="https://store.nintendo.co.kr/media/catalog/product/cache/14e5d81868ac859844dff5ea3cdb33f6/f/i/file.jpg" alt="젤다의 전설: 야생의 숨결" width="500px" height="300px">
      </item>
      <item>
        <div style="color: blue; font-size: 20px;">아이템 4</div>
      </item>
    </ul>
  </div>
</template>

<script>
import Item from './Item';

export default {
  components: {
    Item,
  },
  data() {
    return {
      items: ['아이템 1', '아이템 2', '아이템 3', '아이템 4', '아이템 5',]
    }
  }
}
</script>