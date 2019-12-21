# 인프런 - Vue.js 완벽 가이드

## Part 1 : Application 제작

### 기본 정보

Hacker News 사이트에 있는 News, Ask, Jobs 탭과 사용자 정보를 확인할 수 있는 어플리케이션을 제작한다.
https://github.com/tastejs/hacker-news-pwas/blob/master/docs/api.md 에서 제공하는 API를 사용한다.

### 라우트 구성

- New
- Ask
- Jobs
- Ask Detail View
- User Detail Info

### 프로젝트 구성

```bash
$ npm i -g @vue/cli
$ vue -V
@vue/cli 4.1.1
$ vue create vue-news   // default 옵션 선택
```