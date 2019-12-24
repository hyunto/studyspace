import { fetchNewsList, fetchJobsList, fetchAskList, fetchUserInfo } from '../api/index.js';

export default {
  FETCH_NEWS(context) {
    fetchNewsList()
      .then(response => {
        console.log(context);
        console.log(response);
        context.commit('SET_NEWS', response.data);
      })
      .catch(error => {
        console.log(error);  
      })
  },
  FETCH_JOBS({ commit }) {
    fetchJobsList()
      .then(({ data }) => {
        commit('SET_JOBS', data);
      })
      .catch(error => {
        console.log(error);
      })
  },
  FETCH_ASKS({ commit }) {
    fetchAskList()
      .then(({ data }) => {
        commit('SET_ASKS', data);
      })
      .catch(error => {
        console.log(error);
      })
  },
  FETCH_USER({ commit }, name) {
    console.log("name : " + name)
    fetchUserInfo(name)
      .then(({ data }) => {
        commit('SET_USER', data);
      })
      .catch(error => {
        console.log(error);
      })
  }
}