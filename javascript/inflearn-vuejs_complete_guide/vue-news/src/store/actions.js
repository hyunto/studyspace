// import { fetchNewsList, fetchJobsList, fetchAskList, fetchUserInfo, fetchAskItem, fetchList } from '../api/index.js';
import { fetchUserInfo, fetchAskItem, fetchList } from '../api/index.js';

export default {
  // FETCH_NEWS(context) {
  //   fetchNewsList()
  //     .then(response => {
  //       console.log(context);
  //       console.log(response);
  //       context.commit('SET_NEWS', response.data);
  //     })
  //     .catch(error => {
  //       console.log(error);  
  //     })
  // },
  // FETCH_JOBS({ commit }) {
  //   fetchJobsList()
  //     .then(({ data }) => {
  //       commit('SET_JOBS', data);
  //     })
  //     .catch(error => {
  //       console.log(error);
  //     })
  // },
  // FETCH_ASKS({ commit }) {
  //   fetchAskList()
  //     .then(({ data }) => {
  //       commit('SET_ASKS', data);
  //     })
  //     .catch(error => {
  //       console.log(error);
  //     })
  // },
  async FETCH_USER({ commit }, name) {
    // -------------------------------------------------
    // Promise에서 Async/Await로 로직 변경
    // return fetchUserInfo(name)
    //   .then(({ data }) => {
    //     commit('SET_USER', data);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   })
    // -------------------------------------------------
    try {
      const response = await fetchUserInfo(name);
      commit('SET_USER', response.data);
      return response;
    } catch(err) {
      console.log(err);
    }
  },
  async FETCH_ASK_ITEM({ commit }, id) {
    // -------------------------------------------------
    // Promise에서 Async/Await로 로직 변경
    // return fetchAskItem(id)
    //   .then(({ data }) => {
    //     commit('SET_ASK_ITEM', data);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   })
    // -------------------------------------------------
    try {
      const response = await fetchAskItem(id);
      commit('SET_ASK_ITEM', response.data);
      return response;
    } catch(err) {
      console.log(err);
    }
  },
  async FETCH_LIST({ commit }, pageName) {
    // -------------------------------------------------
    // Promise에서 Async/Await로 로직 변경
    // return fetchList(pageName)
    //   .then(({ data }) => commit('SET_LIST', data))
    //   .catch(error => console.log(error));
    // -------------------------------------------------
    // 이 로직의 에러 처리는 api 단에서 수행한다.
    const response = await fetchList(pageName);
    console.log(response);
    commit('SET_LIST', response.data);
    return response;
  }
}