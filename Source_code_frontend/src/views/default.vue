<template>
    <div>


        <!--Carousel-->
        <div class="carousel-images  " v-if="favList_projectinfo&&favList_projectinfo.length>0">
            <el-carousel :interval="5000" type="card" height="520px" indicator-position="none">
                <el-carousel-item v-for="(item,index) of favList_projectinfo" :key="index">
                                <router-link :to="'/projectinfoDetail?id='+item.projectinfoid">
                                    <img :src="BASE_API+item.image" class="image">
                                </router-link>
                            </el-carousel-item>

            </el-carousel>
        </div>


        <div class=" layout_page ">


            <ul class="pic_ul_css  ">
            <template v-for="(item,index) of hitsList_projectinfo">
                    <li v-if="index<4">
                    <router-link :to="'/projectinfoDetail?id='+item.projectinfoid">
                        <img :src="BASE_API+item.image">
                        <span>{{ item.projectinfoname }}  </span>
                        </router-link>
                    </li>
                </template>

            </ul>


        </div>
    </div>
</template>

<script name="dashboard" setup>
import store from '@/store'    //For getting cached user information
const postFormRef = ref(null) // If the page has a form, use this ref
const route = useRoute();   //Get information or parameters from the route
const router = useRouter();   //Operate routes, such as navigation
import {ElMessage, ElMessageBox} from "element-plus";


import axios_request from '@/utils/axios_request'
import {parseTime,} from '@/utils' //Date formatting

const keyword = ref('')//Keyword,


const favs = ref([]);//Carousel
const tops = ref({});//Top

const fronts = ref([]);//All category items

const fronts_projectinfo_type = ref(null);//All category items - music
const projectinfoList = ref(null);
const hitsList_projectinfo = ref([]);//Ranking
const favList_projectinfo = ref([]);//Carousel
const topList_projectinfo = ref([]);//Top


//Get information after login

const userid_front = computed(() => {
    return store.getters.userid_front
})

const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');
onMounted(() => {
    getList()

})


function getList() {
    //Get categories

    let listQuery = {}
    if (userid_front.value && userid_front.value > 0) {
        //console.log(userid_front.value)
        listQuery.membersid = userid_front.value
    }
    axios_request.get('/front/front_default.action', {params: listQuery}).then(response => {
        projectinfoList.value = response.data.projectinfoList     //If logged in, generate recommendations based on browsing history; if not logged in, random recommendations


        fronts_projectinfo_type.value = response.data.frontList_projectinfo_type//Music
        topList_projectinfo.value = response.data.topList_projectinfo//Music
        hitsList_projectinfo.value = response.data.hitsList_projectinfo//Music
        favList_projectinfo.value = response.data.favList_projectinfo//Music


        //console.log(fronts_projectinfo_type)


    })


}

function handleSearch() {
    let key = ''
    if (keyword.value !== '') key = '?keyword=' + keyword.value
    //Menu route navigation
    //console.log(key)
    router.push('/projectinfo' + key)
}

</script>



