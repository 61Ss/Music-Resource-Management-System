<template>
    <div>

        <div class="page_body_box  ">
            <span>Music playlist</span>
            <div style="font-size: 14px; color: #666; margin-top: 5px;">
                You can browse and listen to all the music uploaded by users, but you can only manage the music you uploaded yourself in the "My Music Management" section.
            </div>
        </div>


        <div class="layout_page">


            <div style="padding: 15px; text-align: center">
                <el-input placeholder="Search" v-model="keyword" style="width: 270px;">


                    <template #append>
                        <el-button type="primary" @click="handleSearch" icon="search">Search</el-button>
                    </template>

                </el-input>
            </div>
            <ul class="pic_ul_css">
                <li v-for="(item,index) of projectinfos"

                    :key="index">


                    <router-link :to="'/projectinfoDetail?id='+item.projectinfoid">
                        <img :src="BASE_API+item.image">
                        <span>{{ item.projectinfoname }}  </span>
                    </router-link>


                </li>
            </ul>


            <el-pagination background @current-change="handleCurrentChange"
                           :current-page.sync="listQuery.pageNumber "
                           :page-size="listQuery.pageSize"
                           layout="total,    ->,prev,pager, next "
                           :total="listQuery.rowCountTotal">
            </el-pagination>
        </div>

    </div>
</template>

<script name="projectinfo" setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) // Form reference if page contains form
const route = useRoute();   // Get route information and parameters
const router = useRouter();   // Handle route operations like navigation
import {ElMessage, ElMessageBox} from "element-plus";  // Notifications and message boxes


import axios_request from '@/utils/axios_request'

const keyword = ref('')
const keyword_table = ref('')//,

const projectinfos = ref(null)
const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const listQuery = ref({
    pageNumber: 1,//当前页数
    pageSize: 12,//,每页显示多少条记录
    rowCountTotal: 0//记录总数 默认0,从后端获取总数
})


onMounted(() => {
    getList()


})


watch(() => route, newdata => {
    // 如果路由参数有变化，会再次执行获取列表数据
    getList()

}, {deep: true})

function handleSearch() {
    let key = ''
    if (keyword.value !== '') key = '?keyword=' + keyword.value
    //菜单 的路由跳转
    //console.log(key)
    router.push('/projectinfo' + key)
}

function getList() {
    delete listQuery.value['projectinfo_typeid']
    delete listQuery.value['keyword']
    delete listQuery.value['keyword_table']
    //分类检索
    if (route.query.projectinfo_typeid > 0) listQuery.value.projectinfo_typeid = route.query.projectinfo_typeid
    if (route.query.keyword !== '') listQuery.value.keyword = route.query.keyword
    if (route.query.keyword_table !== '') listQuery.value.keyword_table = route.query.keyword_table


    // 列表页 //审核过的
    axios_request.get('/front/projectinfo.action', {
        params: listQuery.value
    }).then(response => {
        projectinfos.value = null
        projectinfos.value = response.data.projectinfoList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

    })

}

//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
}


</script>

<style rel="stylesheet/scss" lang="scss" scoped>


.head_box {

    .el-input {
        width: 30%;
        float: right;
    }

    .title {

        height: 40px;
        border-bottom: 1px solid #ddd;
        text-align: center;


        margin-bottom: 20px;
        margin-top: 20px;

        span {
            display: inline-block;
            line-height: 39px;
            border-bottom: 2px solid #50314F;
            float: left;
            font-size: 32px;
            font-weight: bold;

        }
    }


}


</style>
