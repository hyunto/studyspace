import Vue from 'vue';
import VueRouter from 'vue-router';
import NewsView from '../views/NewsView.vue'
// import AskView from '../views/AskView.vue'
import JobsView from '../views/JobsView.vue'
import UserView from '../views/UserView.vue'
import ItemView from '../views/ItemView.vue'
import createListView from '../views/CreateListView'

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
    },
    {
      path: '/ask',
      name: 'ask',
      // component: AskView,  // 예전 방식
      component: createListView('AskView'),
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
