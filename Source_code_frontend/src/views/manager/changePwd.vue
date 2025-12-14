<template>
    <div>
        <el-form status-icon :model="pwdForm" :rules="pwdRules" ref="postFormRef" label-width="120px">
            <el-form-item label="Username">
                {{ username }}
            </el-form-item>
            <el-form-item label="Old Password" prop="password">
                <el-input name="password" type="password" v-model="pwdForm.password" autoComplete="on"
                          placeholder="Enter old password"/>
            </el-form-item>
            <el-form-item label="New" prop="new_password">
                <el-input name="new_password" type="password" v-model="pwdForm.new_password" autoComplete="on"
                          placeholder="Enter new password"/>
            </el-form-item>
            <el-form-item label="Confirm" prop="repassword">
                <el-input name="repassword" type="password" v-model="pwdForm.repassword" autoComplete="on"
                          placeholder="Confirm new password"/>
            </el-form-item>
        </el-form>
        <div class="dialog-footer">
            <el-button @click="closeDialog" icon="more">Cancel</el-button>
            <el-button :loading="loading" type="primary" @click="saveData()" icon="coin">Save</el-button>
        </div>
    </div>
</template>


<script name="changePwd" setup>
import store from '@/store'    // Used to get cached user info
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息


import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'

let formData = {
    password: '',
    new_password: '',
    repassword: ''
}


// Get logged-in user information


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


const loading = ref(false)// Loading indicator
const pwdForm = ref(formData)// Form data


const validatePass1 = (rule, value, callback) => {
    if (value == pwdForm.value.password) {
        callback(new Error('New password cannot be the same as old password!'));
    } else {
        callback();
    }
}
const validatePass2 = (rule, value, callback) => {
    if (value !== pwdForm.value.new_password) {
        callback(new Error('Passwords do not match!'));
    } else {
        callback();
    }
}


const pwdRules = {
    password: [{required: true, message: "Old password required"}],
    new_password: [
        {required: true, message: "New password required"},
        {min: 6, message: 'Password must be at least 6 characters', trigger: 'blur'},
        {validator: validatePass1, trigger: 'blur'}
    ],
    repassword: [
        {required: true, message: "Confirm password required"},
        {min: 6, message: 'Password must be at least 6 characters', trigger: 'blur'},
        {validator: validatePass2, trigger: 'blur'}
    ]
}

onMounted(() => {

    pwdForm.value = JSON.parse(JSON.stringify(formData))// Initialize form data
    if (postFormRef.value) {
        //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
        //然后watch变化 后,再调用 就可以使用了
        postFormRef.value.resetFields()
    }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用


    //console.log(info.value)


})


// Close dialog
const emits = defineEmits(['close'])

function closeDialog() {
    //关闭的时候 不能恢复数据,如果恢复的话,会引起子组件,传递给父组件行数据出错
    emits('close') // Notify parent component to close dialog
}

//密码修改
function saveData() {
    //console.dir(postForm.value.depidArray)
    postFormRef.value.validate(valid => {

        //console.log(pwdForm.value.password)
        //console.log(info.value.password)
        //if(pwdForm.value.password!==info.value.password){
        //    ElMessage({message: "旧密码错误"})
        //    return
        //}

        if (valid) {

            axios_request.post('/manager/manager/edit_password.action', pwdForm.value).then(response => {

                ElMessage({
                    message: response.message
                })
                if (response.success) {
                    ElMessageBox.alert('Password changed successfully, please login again', '提示', {
                        confirmButtonText: 'Re-login',
                        type: 'warning'
                    }).then(() => {
                        store.dispatch('FedLogOut')
                        store.dispatch('FedLogOut_front')
                window.location.href = '/#/default'// Redirect to home page
                location.reload();// Force full page reload to clear cache
                        /* store.dispatch('FedLogOut').then(() => {
                     window.location.href = '/#/default'// Redirect to home page
                location.reload();// Force full page reload to clear cache
                         });*/
                    })

                    closeDialog()
                }
            })
        }
    })
}


</script>
