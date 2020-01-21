<template>
  <canvas ref="myChart" width="400" height="400"></canvas>
</template>

<script>
export default {
  props: [
      'propsdata'
  ],
  mounted() {
    // var ctx = document.getElementById('barChart');
    new this.$_Chart(this.$refs.myChart, {
        type: 'bar',
        data: {
            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
            datasets: this.propsdata
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
  }
}
</script>

<style>

</style>