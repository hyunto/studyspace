import axios from 'axios';

// 1. HTTP Request & Response와 관련된 기본 설정
const config = {
  baseUrl: 'http://api.hnpwa.com/v0'
};

// 2. API 함수들을 정리
// 참고: https://github.com/tastejs/hacker-news-pwas/blob/master/docs/api.md
function fetchNewsList() {
  return axios.get(`${config.baseUrl}/news/1.json`);
}
function fetchAskList() {
  return axios.get(`${config.baseUrl}/ask/1.json`);
}
function fetchJobsList() {
  return axios.get(`${config.baseUrl}/jobs/1.json`);
}
function fetchUserInfo(userName) {
  return axios.get(`${config.baseUrl}/user/${userName}.json`);
}
function fetchAskItem(id) {
  return axios.get(`${config.baseUrl}/item/${id}.json`);
}

export {
  fetchNewsList,
  fetchAskList,
  fetchJobsList,
  fetchUserInfo,
  fetchAskItem,
}