import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const router = new Router({
    routes: [
        {
          path: '/ll1',
          name: 'LL1',
          component: () => import('../components/LL1.vue'),
        },
        {
            path: '/lr1',
            name: 'LR1',
            component: () => import('../components/LR1.vue'),
          },
    ]
})

export default router