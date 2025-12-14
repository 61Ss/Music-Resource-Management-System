<template>
    <div style="text-align: center;font-size: 24px;font-weight: bold;padding: 30px">


        User Information


    </div>

    <div style="width: 50%;margin: 0 auto;">
        <el-form status-icon :model="membersinfo" ref="membersinfoRef" label-width="100px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="Username" prop="username">
                        {{membersinfo.username}}

                    </el-form-item>


                    <el-form-item label="Full Name" prop="realname">
                        {{membersinfo.realname}}
                    </el-form-item>
                    <el-form-item label="Gender" prop="sex">
                        {{ membersinfo.sex }}
                    </el-form-item>


                    <el-form-item label="Birthday" prop="birthday">
                        {{ membersinfo.birthday }}
                    </el-form-item>
                    <el-form-item label="QQ" prop="qq">
                        {{membersinfo.qq}}
                    </el-form-item>
                    <el-form-item label="ID Number" prop="idcard">
                        {{membersinfo.idcard}}
                    </el-form-item>
                    <!-- <el-form-item label="Address" prop="address">
                        {{membersinfo.address}}
                    </el-form-item> -->
                </el-col>
                <el-col :span="12">
                    <el-form-item prop="image" >
                        <el-avatar v-if="membersinfo.image"
                                   style="display: block;width: 250px;height: 246px;text-align: center;margin: 0 auto"
                                   :src="BASE_API+membersinfo.image"></el-avatar>



                    </el-form-item>
                    <el-form-item label="Email" prop="email">
                        {{membersinfo.email}}
                    </el-form-item>



                    <el-form-item label="Phone" prop="contact">
                        {{membersinfo.contact}}
                    </el-form-item>

                    <!-- <el-form-item label=" " prop="address">
                        &nbsp
                    </el-form-item> -->


                </el-col>
            </el-row>
        </el-form>

    </div>
</template>


<script name="membersinfo" setup>
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的

import axios_request from '@/utils/axios_request'


const loading = ref(false)//加载提示

const membersinfo = ref({});
const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');


onMounted(() => {
    fetchData()
})


watch(() => route, newdata => {
    // 如果路由参数有变化，会再次执行获取列表数据
    fetchData()

}, {deep: true})


            //获取数据,并初始化
             function fetchData() {

                //显示 数据
                axios_request.get('/manager/members/getById.action?id='+ route.query.id).then(response => {
                        if (response.data) {

                            membersinfo.value = response.data.members
                        }
                    }
                )


            }


</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.el-row {
    background-color: #f2f2f2;
    border: 1px solid #CCCCCC;
}

.el-form-item {

    border: 1px solid #CCCCCC;
    margin-bottom: 0;
    /* padding: 15px;*/

}

:deep(.el-form-item__label) {
    color: #2a2a2a;
    font-weight: bold;
    width: 30% !important;
    border-right: 1px solid #CCCCCC;

    height: 60px;
    padding: 15px;
    background-color: #F0F0F0;

}

:deep(.el-form-item__content) {
    margin-left: 0 !important;
    padding-left: 10px !important;
}
</style>
