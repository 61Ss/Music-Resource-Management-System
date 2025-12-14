<template>
    <div class="home">
        <div class="wrap">
            <h2></h2>
            <p></p>
            <p></p>


        </div>
    </div>
</template>

<script name="dashboard" setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) 
const route = useRoute();   // Get route information or parameters
const router = useRouter();   
import {ElMessage, ElMessageBox} from "element-plus";  


import {mapGetters} from 'vuex'



const token = computed(() => {
    return store.getters.token
})
const username = computed(() => {
    return store.getters.username
})
const role_id = computed(() => {
    return Number(store.getters.role_id)
})
const info = computed(() => {

    return store.getters.info
})


const managerid = computed(() => {
    return store.getters.managerid
})


onMounted(() => {


})


</script>

<style rel="stylesheet/scss" lang="scss" scoped>

.home {
    background: url(../../../static/login2.png);
    background-size: 100% 100%;

    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    -ms-background-size: cover;
    height: 100vh;
    position: relative;
    z-index: 1;
    justify-content: center;
    display: grid;
    align-items: center;

    text-align: center;
    color: #777;

    .wrap {
        margin-top: 20%;
        text-shadow: 0 0 10px rgba(53, 106, 200, 10.5);
        letter-spacing: 35px;
        color: #f2f2f2

    }

    h2 {
        font-size: 32px;
        color: #555;
        margin-bottom: 20px;
    }

    p {
        line-height: 24px;
        margin: 0;
        color: #020202;
    }

    .start-up {
        margin-top: 8px;
    }


    .award {
        position: absolute;
        right: 20px;
        bottom: 60px;
        display: flex;
        flex-direction: column;
        text-align: left;
        padding: 12px;
        border: 1px solid #eee;
        box-shadow: -1px 1px 10px #ccc;

        h4 {
            font-weight: bold;
            margin-bottom: 8px;
        }

        img {
            width: 160px;
            margin-bottom: 20px;
            transition: opacity ease .3s;
        }
    }
}
</style>
