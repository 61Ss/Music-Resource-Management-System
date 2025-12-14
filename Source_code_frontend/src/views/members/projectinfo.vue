<template>
    <div>


        <div class="page_body_box  ">
            <span>My Music Management</span>
            <div style="font-size: 14px; color: #666; margin-top: 5px;">
                You can manage the music files you have uploaded here, but you can only delete the music that you uploaded yourself.
            </div>
        </div>


        <div class="layout_page">
            <div class="filter-container" ref="toolbar">
                <div class="fr">
                    <el-button-group>
                        <el-tooltip content="Add">
                            <el-button type="primary" icon="plus" @click="handleCreate"></el-button>
                        </el-tooltip>

                    </el-button-group>
                </div>


                <el-input v-model="listQuery.keyword"
                          @keyup.enter.native="handleFilter"
                          style="width: 200px;"
                          placeholder="Keywords"
                          clearable @change="getList"
                >
                </el-input>
                <el-button type="primary" icon="search" @click="handleFilter">Search</el-button>

            </div>
            <el-table stripe border fit highlight-current-row
                      :key='tableKey'
                      :data="listData"
                      v-loading="listLoading"
                      element-loading-text="Loading..."
                      style="width: 100%"
            >

                <el-table-column align="center" header-align="center" label="Image" width="120"
                >
                    <template #default="scope">

                        <img :src="BASE_API+scope.row.image" style="width: 60px;height: 60px">


                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label=" Category" width="120">
                    <template #default="scope">
                        {{ scope.row.projectinfo_typename }}
                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="Keyword" show-overflow-tooltip>
                    <template #default="scope">

                        {{ scope.row.keyword_table }}
                    </template>
                </el-table-column>
     <el-table-column align="center" header-align="center" label="Pin to Top"  width="60">
                    <template #default="scope">
                        {{ scope.row.projectinfo_istop }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center"  label="Carousel"  width="60">
                    <template #default="scope">
                        {{ scope.row.projectinfo_isflv }}
                    </template>
                </el-table-column>

                <el-table-column align="left" header-align="center" label=" Name" show-overflow-tooltip>
                    <template #default="scope">
                        <router-link :to="'/projectinfoDetail?id='+scope.row.projectinfoid">

                            {{ scope.row.projectinfoname }}
                        </router-link>
                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="Release Date" width="180">
                    <template #default="scope">
                        {{ scope.row.createdate }}
                    </template>
                </el-table-column>


                <el-table-column align="center" header-align="center" label="Review" width="150">
                    <template #default="scope">
                        <el-tag type="success" v-if="Number(scope.row.iscooled)===0">Normal</el-tag>
                        <el-tag type="warning" v-if="Number(scope.row.iscooled)===1">Not Reviewed</el-tag>
                        <el-tag type="danger" v-if="Number(scope.row.iscooled)===2">Rejected</el-tag>
                    </template>
                </el-table-column>


                <el-table-column align="center" label="Action" width="200">
                    <template #default="scope">
                        <!--{{managerid}}-&#45;&#45;&#45;&#45;{{scope.row.id}}-->
                        <!--{{role_id}}-->
                        <el-button type="primary" size="small" @click="handleUpdate(scope)">Edit</el-button>
                        <el-button type="danger" size="small" @click="handleDelete(scope)">Delete</el-button>


                    </template>
                </el-table-column>
            </el-table>

            <!--Single column standard size width="40%" top="10vh"-->
            <el-dialog :close-on-click-modal="false" :title="dialogFormTitle"
                       v-model="dialogFormVisible"
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

        <el-pagination background @current-change="handleCurrentChange"
                       :current-page.sync="listQuery.pageNumber "
                       :page-size="listQuery.pageSize"
                       layout="total,    ->,prev,pager, next "
                       :total="listQuery.rowCountTotal">
        </el-pagination>


    </div>
</template>

<script name="search" setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) // Form reference if page contains form
const route = useRoute();   // Get route information and parameters
const router = useRouter();   // Handle route operations like navigation
import {ElMessage, ElMessageBox} from "element-plus";  // Notifications and message boxes


import {tableHeight} from '@/components/mixin/mixin_publicList' // List component parameter utility, get table height
import axios_request from '@/utils/axios_request'
import dialogForm from './projectinfo_form_components'
import {mapGetters} from 'vuex'


const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const tableKey = ref(0);//Table key
const listData = ref(null)//List data

const listLoading = ref(true);//Loading indicator

const listQuery = ref({
    pageNumber: 1,//Current page
    pageSize: 12,//Records per page
    rowCountTotal: 0//Total record count, default 0, get total from backend
})


//Dialog
const dialogFormVisible = ref(false);//Whether to show dialog
const dialogFormTitle = ref("");//Dialog title
const dialogFormId = ref(0);//ID for editing
const newRowDataId = ref(0);//New row value after adding or editing


//Get login information
const token_front = computed(() => {
    return store.getters.token_front
})
const info_front = computed(() => {
    return store.getters.info_front
})
const userid_front = computed(() => {
    return store.getters.userid_front
})


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
const role_front = computed(() => {
    return store.getters.role_front
})


onMounted(() => {
    getList()

})


function getList() {

    listLoading.value = true
    axios_request.get('/front/getList.action', {params: listQuery.value}).then(response => {
        listData.value = response.data.projectinfoList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        listLoading.value = false
    })
}

//Delete process
function handleDelete(scope) {
    ElMessageBox.confirm("Are you sure you want to delete this music file?", "Notification").then(() => {
        //Delete
        const id = scope.row.projectinfoid
        axios_request.post('/front/delete.action?id=' + id).then(response => {
            if (response.code === 200) {
                ElMessage({
                    type: 'success',
                    message: response.message || 'Delete successful'
                })
                getList()
            } else {
                ElMessage({
                    type: 'error',
                    message: response.message || 'Delete failed, you can only delete music files you uploaded'
                })
            }
        }).catch(error => {
            ElMessage({
                type: 'error',
                message: 'Operation failed, please try again later'
            })
        })
    }).catch(() => {
        // User canceled delete operation
    });
}


//Status change
function handleStatus(scope, status_int) {
    ElMessageBox.confirm("Are you sure you want to perform this action?", "Notification").then(() => {
        const id = scope.row.projectinfoid


        axios_request.post('/front/status.action?id=' + id + '&status=' + status).then(response => {
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


//Search
function handleFilter() {
    listQuery.value.pageNumber = 1
    //listQuery.value.rowCountTotal = 0
    getList()
}

//Current page input box
function handleCurrentChange(val) {
    listQuery.value.pageNumber = val
    getList()
}

//Show add
function handleCreate() {
    dialogFormTitle.value = "Add"
    dialogFormId.value = 0
    dialogFormVisible.value = true
}

//Show edit
function handleUpdate(scope) {
    //console.log(id)
    dialogFormTitle.value = "Edit"
    dialogFormId.value = parseInt(scope.row.projectinfoid) //Current operation ID

    dialogFormVisible.value = true
}

//Close add and edit dialog

function closeDialog() {
    dialogFormVisible.value = false;    //Hide el-dialog
    getList()
}


</script>
