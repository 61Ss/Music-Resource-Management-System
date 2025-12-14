<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef" label-width="120px"
            size="small"
            style="padding-top:20px "
        >


            <el-form-item label="username" prop="username">
                <el-input name="username" type="text" v-model="postForm.username"
                          autoComplete="on"
                          placeholder="Please fill in"
                          :disabled="postForm.username==='SuperAdmin'&&postForm.managerid==='SuperAdmin'"/>
            </el-form-item>


            <el-form-item label="password" prop="password">
                <el-input name="password" type="password" v-model="postForm.password"
                          autoComplete="on"
                          placeholder="Please fill in"/>
            </el-form-item>


            <el-form-item label="realname" prop="realname">
                <el-input placeholder="Please fill in" v-model="postForm.realname"/>
            </el-form-item>
            <el-form-item label="contact" prop="contact">
                <el-input placeholder="Please fill in" type="text" v-model="postForm.contact"></el-input>
            </el-form-item>

        </el-form>

        <div class="dialog-footer">
            <el-button type="primary" @click="saveData" icon="coin">Save</el-button>
        </div>

    </div>
</template>


<script name="dialogForm" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import axios_request from '@/utils/axios_request'
import {validateMobileFormat} from '@/utils/validate'
import {mapGetters} from "vuex";


let formData = {
    username: '',
    password: '',
    realname: '',
    contact: '',

}


const props = defineProps({

    //数据ID,只在这个组件中使用
    dataId: {
        type: Number,
        default: 0
    },
    //隐藏与显示,用于监控和初始,只在这个组件中使用
    dialogFormVisible: {
        type: Boolean,
        default: false
    },

});


const postForm = ref({})//表单

const rules = {
    username: [{required: true, message: "Please fill in"}],
    password: [
        {required: true, message: "Please enter"},
        {min: 6, message: 'Password must be more than 6 characters.', trigger: 'blur'},
    ],

    realname: [{required: true, message: "Please fill in"}],

    contact: [
        {required: true, message: "Please fill in"},
        {min: 11, max: 11, message: 'Phone number must be 11 digits.', trigger: 'blur'},
        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
    ],

}

watch(() => props.dataId, newdata => {
    fetchData()//根据id的变化 更新表单的值

}, {deep: true})

watch(() => props.dialogFormVisible, newdata => {
    //console.log('监听到改变')
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化
    if (newdata) fetchData()//监控弹窗是否显示,显示则初始化数据

}, {deep: true})
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


// 初始化

onMounted(() => {
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化

    if (route.query.id) props.dataId = route.query.id//如果是在菜单中直接打开的路由编辑界面(不是弹窗界面),则从路由获取ID
    fetchData()


})


//关闭弹窗
const emits = defineEmits(['close'])

function closeDialog() {
    //关闭的时候 不能恢复数据,如果恢复的话,会引起子组件,传递给父组件行数据出错
    emits('close') //事件通知父组件修改el-model的显示状态
}

//获取数据,并初始化
function fetchData() {
    if (props.dataId > 0) {
        //显示编辑数据
        axios_request.get('/manager/manager/getById.action?id=' + props.dataId).then(response => {
                if (response.data) {

                    postForm.value = response.data.manager
                    postForm.value.password = ''
                }
            }
        )
    } else {
        //显示添加数据
        axios_request.get('/manager/manager/create.action').then(response => {
                if (response.data) {

                }
            }
        )
    }

}

//保存数据
function saveData() {

    postFormRef.value.validate(valid => {
        //console.log(valid);
        if (valid) {
            if (postForm.value.username === 'SuperAdmin' && Number(postForm.value.managerid) !== 1) {
                ElMessage({message: 'Username cannot be "SuperAdmin".', type: 'warning'})
                return
            }

            if (props.dataId > 0) {
                //保存编辑数据
                postForm.value.managerid = props.dataId
                axios_request.post('/manager/manager/update.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在axios异步过程内
                    }
                })
            } else {
                //保存添加数据

                axios_request.post('/manager/manager/add.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在axios异步过程内
                    }
                })
            }
        }

    })

}

</script>
