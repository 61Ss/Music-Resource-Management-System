<template>
    <div>


        <div class="app-container">
            <div class="filter-container" ref="toolbar">
                <div class="fr">

                </div>


                <el-input v-model="listQuery.keyword"
                          @keyup.enter.native="handleFilter"
                          style="width: 300px;"
                          placeholder="Keywords"
                          clearable @change="getList"
                >
                    <template #append>
                        <el-button type="primary" @click="getList" icon="search">Search</el-button>
                    </template>

                </el-input>

            </div>
            <el-table stripe border fit highlight-current-row
                      :key='tableKey'
                      :data="listData"
                      v-loading="listLoading"
                      element-loading-text="Loading..."
                      style="width: 100%"
                      :height="tableHeight"
            >

                <el-table-column align="center" header-align="center" label="Image" width="120"
                >
                    <template #default="scope">

                        <img :src="BASE_API+scope.row.image" style="width: 60px;height: 60px">


                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Category" width="120">
                    <template #default="scope">
                        {{ scope.row.projectinfo_typename }}
                    </template>
                </el-table-column>
                <el-table-column align="left" header-align="center" label=" Name">
                    <template #default="scope">
                        <router-link :to="'/projectinfoDetail?id='+scope.row.projectinfoid" target="_blank">
                            {{ scope.row.projectinfoname }}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Keyword" show-overflow-tooltip>
                    <template #default="scope">
                        {{ scope.row.keyword_table }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Pin to Top" width="60">
                    <template #default="scope">
                        {{ scope.row.projectinfo_istop }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Carousel" width="60">
                    <template #default="scope">
                        {{ scope.row.projectinfo_isflv }}
                    </template>
                </el-table-column>

<!--                <el-table-column align="center" header-align="center" label="音乐" width="120" show-overflow-tooltip>
                    <template #default="scope">
                        {{ scope.row.projectinfo_files }}
                    </template>
                </el-table-column>-->
                <el-table-column align="center" header-align="center" label="Publisher" width="120">
                    <template #default="scope">
                        {{ scope.row.username }}
                    </template>
                </el-table-column>
<!--                <el-table-column align="center" header-align="center" label="发布日期">
                    <template #default="scope">
                        {{ scope.row.createdate }}
                    </template>
                </el-table-column>-->





                <el-table-column align="center" header-align="center" label="Review" width="120">
                    <template #default="scope">
                        <el-tag type="success" v-if="Number(scope.row.iscooled)===0">Normal</el-tag>
                        <el-tag type="warning" v-if="Number(scope.row.iscooled)===1">Pending</el-tag>
                        <el-tag type="danger" v-if="Number(scope.row.iscooled)===2">Rejected</el-tag>
                    </template>
                </el-table-column>


                <el-table-column align="center" label="Action" width="280">
                    <template #default="scope">
                        <!--{{managerid}}-&#45;&#45;&#45;&#45;{{scope.row.id}}-->
                        <!--{{role_id}}-->
                        <!--                        <el-button type="primary" size="small" @click="handleUpdate(scope)">编辑</el-button>-->
                        <el-button type="danger" size="small" @click="handleDelete(scope)">Delete</el-button>
                        <el-button type="primary" size="small"
                                   @click="handleIscooled(scope,'0')">Approve
                        </el-button>
                        <el-button type="danger" size="small"
                                   @click="handleIscooled(scope,'2')">Reject
                        </el-button>
                        <div style="margin-top: 5px">
                            <el-button type="primary" size="small"
                                       @click="handleTop(scope,'Yes')">Pin
                            </el-button>
                            <el-button type="danger" size="small"
                                       @click="handleTop(scope,'No')">Unpin
                            </el-button>
                            <el-button type="primary" size="small"
                                       @click="handleFlv(scope,'Yes')">Carousel
                            </el-button>
                            <el-button type="danger" size="small"
                                       @click="handleFlv(scope,'No')">Stop
                            </el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>

            <!--单列标准尺寸 width="40%" top="10vh"-->
            <el-dialog :close-on-click-modal="false" :title="dialogFormTitle" v-model="dialogFormVisible"
                       width="40%" top="5vh">
                <dialog-form
                    :dialogFormVisible="dialogFormVisible"
                    :dataId='dialogFormId'
                    @close="closeDialog"
                    :newRow.sync="newRowDataId"
                >
                </dialog-form>
            </el-dialog>


        </div>
        <div class="toolBarBottom_body" ref="pagebar">
            <el-pagination background @current-change="handleCurrentChange"
                           :current-page.sync="listQuery.pageNumber "
                           :page-size="listQuery.pageSize"
                           layout="total,    ->,prev,pager, next "
                           :total="listQuery.rowCountTotal">
            </el-pagination>
        </div>

    </div>

</template>

<script name="search" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
import axios_request from '@/utils/axios_request'
import dialogForm from './form_components'
import {mapGetters} from 'vuex'


const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const tableKey = ref(0);//表格key
const listData = ref(null)//列表数据

const listLoading = ref(true);//加载条

const listQuery = ref({
    pageNumber: 1,//当前页数
    pageSize: 12,//,每页显示多少条记录
    rowCountTotal: 0//记录总数 默认0,从后端获取总数
})


//对话框
const dialogFormVisible = ref(false);//是否显示对应框
const dialogFormTitle = ref("");//对话框标题
const dialogFormId = ref(0);//编辑时的ID
const newRowDataId = ref(0);//添加或编辑后的新行的值


//获取登录后的信息


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
    getList()

})


function getList() {

    listLoading.value = true
    axios_request.get('/manager/projectinfo/getAll.action', {params: listQuery.value}).then(response => {
        listData.value = response.data.projectinfoList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        listLoading.value = false
    })
}

//删除过程
function handleDelete(scope) {
    ElMessageBox.confirm("Confirm this operation?", "Warning").then(() => {
        //删除
        const id = scope.row.projectinfoid
        axios_request.post('/manager/projectinfo/delete.action?id=' + id).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}


//状态改变
function handleStatus(scope, status_int) {
    ElMessageBox.confirm("Confirm this operation?", "Warning").then(() => {
        const id = scope.row.projectinfoid


        axios_request.post('/manager/projectinfo/status.action?id=' + id + '&status=' + status_int).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}

//状态改变
function handleIscooled(scope, iscooled_int) {
    ElMessageBox.confirm("Confirm this operation?", "Warning").then(() => {
        const id = scope.row.projectinfoid


        axios_request.post('/manager/projectinfo/iscooled.action?id=' + id + '&iscooled=' + iscooled_int).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}

//置顶
function handleTop(scope, top_string) {
    ElMessageBox.confirm("Confirm this operation?", "Warning").then(() => {
        const id = scope.row.projectinfoid


        axios_request.post('/manager/projectinfo/projectinfo_top.action?id=' + id + '&projectinfo_top=' + top_string).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}

//轮播
function handleFlv(scope, flv_string) {
    ElMessageBox.confirm("Confirm this operation?", "Warning").then(() => {
        const id = scope.row.projectinfoid


        axios_request.post('/manager/projectinfo/projectinfo_flv.action?id=' + id + '&projectinfo_flv=' + flv_string).then(response => {
            ElMessage({
                message: response.message
            })
            if (response.message) {
                getList()
            }

        })
    }).catch(() => {
    });

}


//搜索
function handleFilter() {
    listQuery.value.pageNumber = 1
    //listQuery.value.rowCountTotal = 0
    getList()
}

//当前页输入框
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
}

//显示添加
function handleCreate() {
    dialogFormTitle.value = "Add"
    dialogFormId.value = 0
    dialogFormVisible.value = true
}

//显示编辑
function handleUpdate(scope) {
    //console.log(id)
    dialogFormTitle.value = "Edit"
    dialogFormId.value = parseInt(scope.row.projectinfoid) //当前操作的ID

    dialogFormVisible.value = true
}

//关闭添加和编辑的弹窗

function closeDialog() {
    dialogFormVisible.value = false;    //隐藏el-dialog
    getList()
}


</script>
