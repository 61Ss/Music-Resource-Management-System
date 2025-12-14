<template>
    <div>


        <div class="page_body_box  ">
            <span>Category Management</span>
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


                <el-table-column align="left" header-align="center" label=" Name" show-overflow-tooltip>
                    <template #default="scope">

                        {{ scope.row.projectinfo_typename }}
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

            <!--单列标准尺寸 width="40%" top="10vh"-->
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
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
import axios_request from '@/utils/axios_request'
import dialogForm from './projectinfo_type_form_components'
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
    axios_request.get('/manager/projectinfo_type/getAll.action', {params: listQuery.value}).then(response => {
        listData.value = response.data.projectinfo_typeList
        listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

        listLoading.value = false
    })
}

//删除过程
function handleDelete(scope) {
    ElMessageBox.confirm("Are you sure you want to perform this action?", "Notification").then(() => {
        //删除
        const id = scope.row.projectinfo_typeid
        axios_request.post('/manager/projectinfo_type/delete.action?id=' + id).then(response => {
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
    dialogFormId.value = parseInt(scope.row.projectinfo_typeid) //当前操作的ID

    dialogFormVisible.value = true
}

//关闭添加和编辑的弹窗

function closeDialog() {
    dialogFormVisible.value = false;    //隐藏el-dialog
    getList()
}


</script>
