<template>
    <div>
        <el-form status-icon :model="pwdForm" :rules="pwdRules" ref="postFormRef" label-width="120px">
            <el-form-item label="Username">
                {{ info_front.username }}
            </el-form-item>
            <el-form-item label="Old Password" prop="password">
                <el-input name="password" type="password" v-model="pwdForm.password"
                          autoComplete="on"
                          placeholder="Please fill in "/>

            </el-form-item>
            <el-form-item label="New" prop="new_password">
                <el-input name="new_password" type="password"
                          @input="handlePasswordInput"


                          v-model="pwdForm.new_password" autoComplete="on"
                          placeholder="Please enter."/>
                <div class="password-strength" :class="passwordStrengthClass">
                    {{ passwordStrengthText }}
                </div>
            </el-form-item>
            <el-form-item label="Confirm" prop="repassword">
                <el-input name="repassword" type="password" v-model="pwdForm.repassword" autoComplete="on"
                          placeholder="Please confirm the new password again."/>
            </el-form-item>
        </el-form>
        <div class="dialog-footer">
            <el-button @click="closeDialog" icon="more">Cancel</el-button>
            <el-button :loading="loading" type="primary" @click="saveData()" icon="coin">Save</el-button>
        </div>
    </div>
</template>


<script name="changePwd" setup>
import store from '@/store'    
const postFormRef = ref(null) 
const route = useRoute();  
const router = useRouter();  
import {ElMessage, ElMessageBox} from "element-plus";  


import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'

let formData = {
    password: '',
    new_password: '',
    repassword: ''

}



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


const loading = ref(false)
const pwdForm = ref(formData)


const validatePass1 = (rule, value, callback) => {
    if (value == pwdForm.value.password) {
        callback(new Error('The new password cannot be the same as the old password!'));
    } else {
        callback();
    }
}
const validatePass2 = (rule, value, callback) => {
    if (value !== pwdForm.value.new_password) {
        callback(new Error('The two password entries do not match!'));
    } else {
        callback();
    }
}

const validatePasswordStrength = (rule, value, callback) => {
    const strength = checkPasswordStrength(value)
    if (strength === 'weak') {
        callback(new Error(''))
    } else {
        callback()
    }
}

const pwdRules = {
    password: [{required: true, message: "Please enter old password"}],
    new_password: [
        {required: true, message: "Please enter new password"},
        {min: 6, message: 'Password must be at least 6 characters', trigger: 'blur'},
        {validator: validatePasswordStrength, trigger: 'blur'}
    ],
    repassword: [
        {required: true, message: "Please confirm password"},
        {min: 6, message: 'Password must be at least 6 characters', trigger: 'blur'},
        {validator: validatePass2, trigger: 'blur'}
    ]
}

onMounted(() => {

    pwdForm.value = JSON.parse(JSON.stringify(formData))// Initialize data
    if (postFormRef.value) {
        //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
        //然后watch变化 后,再调用 就可以使用了
        postFormRef.value.resetFields()
    }// Reset form fields to initial values and clear validation results (only available in methods)

})


//关闭弹窗
const emits = defineEmits(['close'])

function closeDialog() {
    //关闭的时候 不能恢复数据,如果恢复的话,会引起子组件,传递给父组件行数据出错
    emits('close') //事件通知父组件修改el-model的显示状态
}

//密码修改
function saveData() {
    //console.dir(postForm.value.depidArray)
    postFormRef.value.validate(valid => {

        //console.log(pwdForm.value.password)
        //console.log(info_front.value.password)
        //if (pwdForm.value.password !== info_front.value.password) {
        //    ElMessage({message: "旧密码错误"})
        //    return
        //}

        if (valid) {


            axios_request.post('/front/edit_password.action', pwdForm.value).then(response => {

                ElMessage({
                    message: response.message
                })
                if (response.success) {


                    ElMessageBox.alert('Password changed successfully, please log in again', 'Notification', {
                        confirmButtonText: 'Log In Again',
                        type: 'warning'
                    }).then(() => {
                        store.dispatch('FedLogOut')
                        store.dispatch('FedLogOut_front')
                        window.location.href = '/#/default'//跳转到首页
                        location.reload();// 整个界面刷新  强制刷新  否则获取不到 缓存数据
                        /* store.dispatch('FedLogOut').then(() => {
                     window.location.href = '/#/default'//跳转到首页
                location.reload();// 整个界面刷新  强制刷新  否则获取不到 缓存数据
                         });*/
                    })

                    closeDialog()
                }
            })


        }
    })
}

// 在script中新增响应式数据和校验逻辑
const passwordStrength = ref('') // weak/medium/strong

// 实时密码强度检测
const checkPasswordStrength = (value) => {
    const hasUpper = /[A-Z]/.test(value)
    const hasLower = /[a-z]/.test(value)
    const hasNumber = /\d/.test(value)
    const lengthValid = value.length >= 6

    if (!lengthValid) return 'weak'
    const score = [hasUpper, hasLower, hasNumber].filter(Boolean).length
    return score >= 3 ? 'strong' : score >= 2 ? 'medium' : 'weak'
}

const handlePasswordInput = () => {
    passwordStrength.value = checkPasswordStrength(pwdForm.value.new_password)
}

// 计算属性
const passwordStrengthClass = computed(() => {
    return {
        [passwordStrength.value]: !!pwdForm.value.new_password
    }
})

const passwordStrengthText = computed(() => {
    if (!pwdForm.value.new_password) return ''
    const map = {
        weak: 'Password Strength: Weak (requires at least two of the following: uppercase letters, lowercase letters, numbers)',
        medium: 'Password Strength: Medium',
        strong: 'Password Strength: Strong'
    }
    return map[passwordStrength.value]
})


</script>

<style scoped>.password-strength {
    font-size: 12px;
    margin-top: 5px;
    transition: color 0.3s;
}

.weak {
    color: #ff4d4f;
}

.medium {
    color: #faad14;
}

.strong {
    color: #52c41a;
}
</style>
