import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')


// ------------------------------------------
// render 함수 변환 과정

// #1
// render: function(createElement) {
//   return createElement(App);
// }

// #2
// render: function(h) {
//   return h(App);
// }

// #3
// render: (h) => {
//   return h(App);
// }

// #4
// render: h => h(App);