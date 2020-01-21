import Chart from 'chart.js';

export default {
  install(Vue) {
    console.log('Chart Plugin Loaded!!!');
    Vue.prototype.$_Chart = Chart;
  }
}