<template>
    <div>

        <div class="page_body_box">


            <span class="title">{{ projectinfo.projectinfoname }}</span>


        </div>


        <div class=" layout_page">


            <div class="post">
                <div class="post-block">

                    <span class="description">
                        Release Date:{{ $filters.parseTime(projectinfo.createdate, '{m}-{d}') }}
                        &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                        Publisher:{{ projectinfo.username }}
                        &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; Views: {{ projectinfo.projectinfo_hits }}
                    </span>
                </div>

                <el-row :gutter="20">
                    <el-col :span="12">
                        <div style="text-align: center;margin-top: 20px" v-if="projectinfo.image!=null">
                            <video  v-if="projectinfo.projectinfo_files && projectinfo.projectinfo_files!='' && Number(userid_front)>0"
                                   :poster="BASE_API+projectinfo.image"
                                   :src="BASE_API+projectinfo.projectinfo_files"
                                   data-ckin="default" controls="controls" data-overlay="1"
                                   style="text-align: center;width:60%;height:80%;min-height: 620px;max-height: 260px"
                            />
                            <img v-else :src="BASE_API+projectinfo.image"
                                 style="text-align: center;width:60%;height:80%;min-height: 460px;max-height: 260px"
                            />
                            <div v-if="!Number(userid_front)>0 && projectinfo.projectinfo_files && projectinfo.projectinfo_files!=''"
                                 style="margin-top: 10px; color: #ff6b6b; font-size: 14px;">
                                Please log in to listen to the music.
                            </div>


                        </div>

                    </el-col>
                    <el-col :span="12" style="padding-top: 200px">


                        <div class="text2"> Keywords:
                            <template v-for="(item,index) of projectinfo.keyword_table_array" :key="index">

                                <router-link :to="'/projectinfo?keyword_table='+item" v-if="item!=''">
                                    <el-tag>{{ item }}</el-tag>
                                </router-link>
                            </template>


                        </div>
                        <div class="text2"> Album:
                            {{ projectinfo.projectinfo_album }}


                        </div>
                        <div class="text2"> Artist:
                            {{ projectinfo.projectinfo_artist }}


                        </div>


                    </el-col>
                </el-row>
                <el-tabs v-model="activeName" style="margin-top: 30px">
                    <el-tab-pane label="Song Story" name="first">
                        <div class="text" v-html="projectinfo.remark" style="min-height: 500px;color: #FFFFFF"></div>

                    </el-tab-pane>

                </el-tabs>
            </div>
        </div>
    </div>
</template>

<script name="projectinfoDetail" setup>
import store from '@/store' 

const postForm = ref({})
const rules = ref({});
const postFormRef = ref(null) 
const route = useRoute();  
const router = useRouter(); 
import {ElMessage, ElMessageBox} from "element-plus"; 


import axios_request from '@/utils/axios_request'
import {mapGetters} from "vuex";


import {parseTime,} from '@/utils'


const projectinfo = ref({});

const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const activeName = ref('first')
//const activeName = ref('second')


//获取登录后的信息
const token_front = computed(() => {
    return store.getters.token_front
})
const info_front = computed(() => {
    return store.getters.info_front
})
const userid_front = computed(() => {
    return store.getters.userid_front
})


const role_front = computed(() => {
    return store.getters.role_front
})


onMounted(() => {
    postForm.value = JSON.parse(JSON.stringify(formData))

    getList()


})


watch(() => route, newdata => {

    getList()

}, {deep: true})

function getList() {

    if (route.query.id > 0) {
        //  console.log("content"+route.query.id)

        listQuery.value.id = route.query.id

        axios_request.get('/front/projectinfoDetail.action', {params: listQuery.value}).then(response => {

            projectinfo.value = response.data.projectinfo
            projectinfo.value.remark = projectinfo.value.remark.replace(/\n|\\r\\n/g, '<br>')

            if (projectinfo.value.keyword_table && projectinfo.value.keyword_table != '') projectinfo.value.keyword_table_array = projectinfo.value.keyword_table.split('#')


            console.log(listData.value)
        })


    }
}


const listData = ref(null);
const listLoading = ref(true);
const listQuery = ref({
    pageNumber: 1,
    pageSize: 12,
    rowCountTotal: 0
})
let formData = {
    projectinfoid: 0,
    score: 5,
    text: '',
}


//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()

}

</script>


<style rel="stylesheet/scss" lang="scss" scoped>


.post {
    padding: 10px;
    font-size: 20px;

    color: #666;

    .image {
        width: 80%;
        height: 100%;

    }

    .text1 {
        text-indent: 25px;
        font-size: 16px;
        line-height: 45px;
    }

    .text2 {

        padding-left: 40px;
        font-size: 16px;
        line-height: 45px;
        color: #FFFFFF;
    }

    .text {
        text-indent: 25px;
        font-size: 16px;
        line-height: 25px;
        white-space: pre-line;
    }
}

:deep(.el-textarea__inner) {
    height: 64px;
}

.post-block {
    text-align: center;
    border-bottom: 1px solid #d2d6de;
    padding-bottom: 15px;

    .description {
        display: block;

        color: #FFFFFF;
        padding: 2px 0;
        margin-top: 5px;
        margin-bottom: 10px;
        line-height: 15px;
    }

    .title {
        font-size: 30px;
        color: #000;
        text-align: center;
        font-weight: bold;
    }


}
</style>
