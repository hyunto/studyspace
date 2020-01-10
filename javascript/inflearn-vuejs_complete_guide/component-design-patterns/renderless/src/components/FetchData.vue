<script>
import axios from 'axios';

export default {
  props: {
    url: String
  },
  data() {
    return {
      response: null,
      loading: true,
    }
  },
  created() {
    axios.get(this.url)
      .then(response => {
        this.response = response.data;
        this.loading = false;
      })
      .catch(error => {
        alert('[ERROR] fetching the data', error);
      })
  },
  render() {
    return this.$scopedSlots.default({
      response: this.response,
      loading: this.loading
    })
  }
}
</script>
