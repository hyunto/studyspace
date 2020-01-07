import Vue from 'vue';
import VueRouter from 'vue-router';
import NewsView from '../views/NewsView.vue'
import AskView from '../views/AskView.vue'
import JobsView from '../views/JobsView.vue'
import UserView from '../views/UserView.vue'
import ItemView from '../views/ItemView.vue'
// import createListView from '../views/CreateListView'

import bus from '../utils/bus';
import { store } from '../store/index';

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/news',
    },
    {
      path: '/news',
      name: 'news',
      component: NewsView,  // mix-in
      // component: createListView('NewsView'),
      // ----------------------------------------------------------------------
      // 아래의 내용(Spinner)는 라우터 네비게이션 가드 + 믹스인으로 로직 이동
      // beforeEnter: (to, from, next) => {
      //   console.log(to);
      //   console.log(from);
      //   console.log(next);

      //   bus.$emit('start:spinner');
      //   store.dispatch('FETCH_LIST', to.name)
      //     .then(() => {
      //       console.log('fetched');
      //       bus.$emit('end:spinner');
      //       next();
      //     })
      //     .catch((error) => {
      //       console.log(error);
      //     });
      // }
      // ----------------------------------------------------------------------
    },
    {
      path: '/ask',
      name: 'ask',
      component: AskView,  // 예전 방식
      // component: createListView('AskView'),
    },
    {
      path: '/jobs',
      name: 'jobs',
      component: JobsView,  // mix-in
      // component: createListView('JobsView'),
    },
    {
      path: '/user/:id',
      component: UserView
    },
    {
      path: '/item',
      component: ItemView
    }
  ]
});

router.beforeEach((to, from, next) => {
  // console.log(to);
  // console.log(from);
  // console.log(next);

  bus.$emit('start:spinner');
  store.dispatch('FETCH_LIST', to.name)
    .then(() => {
      console.log('fetched');
      // bus.$emit('end:spinner');
      next();
    })
    .catch((error) => {
      console.log(error);
    });
});
