import { createRouter, createWebHistory } from 'vue-router'
import Mainview from '../components/Main-view.vue'
import Login from '../components/Log-in.vue'
import Createuser from '../components/Create-user.vue'
import Checkout from '../components/Check-out.vue'
import Books from '../components/Service-list.vue'
import AddService from '../components/Add-service.vue'
import UserList from '../components/User-list.vue'
import EditUser from '../components/Edit-user.vue'
import EditService from '../components/Edit-service.vue'
import ServiceDetails from '../components/Service-details.vue'

const routes = [
    {
        path: '/',
        redirect: '/log-in'
    },
    {
        path: '/main',
        name: 'Mainview',
        component: Mainview
    },
    {
        path: '/log-in',
        name: 'Login',
        component: Login
    },
    {
        path: '/create-user',
        name: 'Createuser',
        component: Createuser
    },
    {
        path: '/check-out',
        name: 'Checkout',
        component: Checkout
    },
    {
        path: '/service-list',
        name: 'Books',
        component: Books
    },
    {
        path: '/add-service',
        name: 'AddService',
        component: AddService
    },
    {
        path: '/user-list',
        name: 'UserList',
        component: UserList
    },
    {
        path: '/edit-user',
        name: 'EditUser',
        component: EditUser,
        props: true
    },
    {
        path: '/edit-service',
        name: 'EditService',
        component: EditService,
       
    },
    {
        path: '/detail-service',
        name: 'ServiceDetails',
        component: ServiceDetails,
        props: true
    },
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})



export default router