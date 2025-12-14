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

                <el-table-column align="center" header-align="center" label="Avatar"
                >
                    <template #default="scope">

                            <img :src="BASE_API+scope.row.image" style="width: 60px;height: 60px">


                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="Username">
                    <template #default="scope">
		    <router-link :to="'/membersinfo?id='+scope.row.membersid" target="_blank">

                            {{ scope.row.username }}
                        </router-link>


                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Full Name">
                    <template #default="scope">
		       <router-link :to="'/membersinfo?id='+scope.row.membersid" target="_blank">
                        {{ scope.row.realname }}
			</router-link>
                    </template>
                </el-table-column>

                <el-table-column align="center" header-align="center" label="Gender">
                    <template #default="scope">
                        {{ scope.row.sex }}
                    </template>
                </el-table-column>
                <!-- <el-table-column align="center" header-align="center" label="Address">
                    <template #default="scope">
                        {{scope.row.address  }}
                    </template>
                </el-table-column> -->
                <el-table-column align="center" header-align="center" label="Contact">
                    <template #default="scope">
                        {{ scope.row.contact }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Registration Date">
                    <template #default="scope">
                         {{ $filters.parseTime(scope.row.regdate, '{m}.{d}') }}
                    </template>
                </el-table-column>
                <el-table-column align="center" header-align="center" label="Status">
                    <template #default="scope">
                        <el-tag type="success" v-if="Number(scope.row.iscooled)===0">Active</el-tag>
                        <el-tag type="danger" v-if="Number(scope.row.iscooled)===1">Frozen</el-tag>


                    </template>
                </el-table-column>



                <el-table-column align="center" label="Actions"  width="270">
                    <template #default="scope">
                        <el-button type="danger" size="small"
                                   @click="handleIscooled(scope,'1')">Freeze
                        </el-button>
                        <el-button type="primary" size="small"
                                   @click="handleIscooled(scope,'0')">Activate
                        </el-button>

                        <el-button type="danger" size="small" @click="handleDelete(scope)" >Delete</el-button>
                    </template>
                </el-table-column>
            </el-table>



        </div>
        <div class="toolBarBottom_body" ref="pagebar">
            <el-pagination background   @current-change="handleCurrentChange"
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
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息


    import {tableHeight} from '@/components/mixin/mixin_publicList' // 列表组件参数利用,获取表格高度
    import axios_request from '@/utils/axios_request'
     import {mapGetters} from 'vuex'







                        const BASE_API = ref(process.env.VUE_APP_BASE_API + '/'   );

  const tableKey = ref( 0);//表格key
                const listData=ref( null)//列表数据

  const listLoading = ref( true);//加载条

const listQuery= ref({
    pageNumber: 1,//当前页数
    pageSize: 12,//,每页显示多少条记录
    rowCountTotal: 0//记录总数 默认0,从后端获取总数
})



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



onMounted(() =>
 {
            getList()

})



             function getList() {

                listLoading.value = true
                axios_request.get('/manager/members/getAll.action',{                params: listQuery.value            }).then(response => {
                    listData.value = response.data.membersList
                    listQuery.value.rowCountTotal = response.data.pager.rowCountTotal

                    listLoading.value = false
                })
            }

            //删除过程
            function handleDelete(scope){
                ElMessageBox.confirm("Are you sure you want to proceed with this action?", "Notification").then(() => {
                    //删除
                    const id = scope.row.membersid
                    axios_request.post('/manager/members/delete.action?id='+ id).then(response => {
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
            //状态操作过程
            function handleIscooled(scope,iscooled_int){
                ElMessageBox.confirm("Are you sure you want to proceed with this action?", "Notification").then(() => {
                    //删除
                    const id = scope.row.membersid
                    axios_request.post('/manager/members/iscooled.action?id='+id+'&iscooled='+iscooled_int).then(response => {
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
            function handleFilter(){
                listQuery.value.pageNumber = 1
                //listQuery.value.rowCountTotal = 0
                getList()
            }

            //当前页输入框
            function handleCurrentChange(val){
                listQuery.value.pageNumber = val
                getList()
            }



</script>
