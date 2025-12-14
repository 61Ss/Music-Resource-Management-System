<template>
    <div>


        <div class="page_body_box  ">
            <span>User Registration</span>
        </div>


        <div class="layout_page">

            <el-form class="form_web" status-icon :model="loginForm" :rules="pwdRules" ref="postFormRef"
                     label-width="140px"
            >
                <el-form-item label="Username" prop="username">
                    <el-input name="username" type="text" v-model="loginForm.username"
                              autoComplete="on"
                              placeholder="Please enter"/>
                </el-form-item>


                <el-form-item label="Password" prop="password">
                    <el-input name="password" type="password" v-model="loginForm.password"
                              autoComplete="on" @input="handlePasswordInput"
                              placeholder="Include any two of: uppercase letters, lowercase letters, numbers"/>
                    <div class="password-strength" :class="passwordStrengthClass">
                        {{ passwordStrengthText }}
                    </div>
                </el-form-item>

                <el-form-item label="Confirm" prop="password1">
                    <el-input name="password1" type="password" v-model="loginForm.password1"
                              autoComplete="on"
                              placeholder="Please confirm again"/>
                </el-form-item>

                <el-form-item label="Full Name" prop="realname">
                    <el-input placeholder="Please enter" v-model="loginForm.realname"/>
                </el-form-item>
                <el-form-item label="QQ" prop="qq">
                    <el-input placeholder="Please enter" v-model="loginForm.qq"/>
                </el-form-item>
                <el-form-item label="ID Card Number">
                    <el-input placeholder="Please enter" v-model="loginForm.idcard"/>
                </el-form-item>
                <el-form-item label="Email" prop="email">
                    <el-input placeholder="Please enter" v-model="loginForm.email"/>
                </el-form-item>
                <el-form-item label="Verification Code" prop="code">
                    <el-input name="code" type="number" v-model="loginForm.code" placeholder="Please enter email verification code">
                        <template #append>
                            <el-button type="primary" @click="handleSendCode">Send Code</el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item label="Education" prop="college">
                    <el-input placeholder="Please enter" v-model="loginForm.college"/>
                </el-form-item>
                <el-form-item label="Gender" prop="sex">
                    <el-radio-group v-model="loginForm.sex">
                        <el-radio value='Male' key='Male'>Male</el-radio>
                        <el-radio value='Female' key='Female'>Female</el-radio>
                    </el-radio-group>

                </el-form-item>
                <el-form-item label="Date of Birth" prop="birthday">
                    <el-date-picker
                        value-format="YYYY-MM-DD"

                        :editable="false" v-model="loginForm.birthday"
                        type="date"
                        placeholder="Select date"
                        :disabled-date="pickerDayNoAfterOptions.disabledDate"
                        style="width:190px"

                    >
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="Contact Phone" prop="contact">
                    <el-input placeholder="Please enter" type="text" v-model="loginForm.contact"></el-input>
                </el-form-item>
                <el-form-item label="Avatar" prop="image">
                    <Upload v-model="loginForm.image" accept=".jpg,.png,.gif"/>
                </el-form-item>


                <el-form-item>
                    <el-button :loading="loading" type="primary" @click="saveData()"
                               icon="coin">Register
                    </el-button>
                </el-form-item>
            </el-form>
        </div>


    </div>
</template>


<script name="register" setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) // Form reference if page contains form
const route = useRoute();   // Get route information and parameters
const router = useRouter();   // Handle route operations like navigation
import {ElMessage, ElMessageBox} from "element-plus";  // Notifications and message boxes


import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'
import {validateMobileFormat, validateIdentityCodeFormat, validateEmail} from '@/utils/validate'
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions
} from '@/components/mixin/mixin_publicData' // 一些常用的DATA里的参数,如日期选择的参数项等
import {parseTime,} from '@/utils'

import Upload from '@/components/Upload'

let formData = {
    username: '',
    password: '',
    password1: '',
    realname: '',
    sex: 'male',
    birthday: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
    contact: '',
    image: '',
    qq: '',
    idcard: '',
    email: '',
    code: '', // 新增验证码字段
    iscooled: '0',
    college: '',
    major: '',
    pwdback_code: '',
    grades1: '',
    grades2: '',
    grades3: '',
    grades4: '',
    grades5: '',
    grades6: '',
}


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


const loading = ref(false)//加载提示
const loginForm = ref(formData)


const validatePass2 = (rule, value, callback) => {
    if (value !== loginForm.value.password) {
        callback(new Error('Passwords do not match!'));
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
}// New verification code sending method (copied from pwdback.vue)
const sendCode = ref('')
const handleSendCode = () => {
    if (!loginForm.value.email) {
        ElMessage.error("Please fill in email first")
        return
    }

    axios_request.post('/front/sendMail04.action?email=' + loginForm.value.email)
        .then(response => {
            ElMessage.success(response.message)
            sendCode.value = Number(response.data.code)
            console.log(sendCode.value)

        })
}
const pwdRules = {
    username: [{required: true, message: "Please fill in"}],

    password: [
        {required: true, message: "Please enter"},
        {min: 6, message: 'Password must be at least 6 characters.', trigger: 'blur'},
        {validator: validatePasswordStrength, trigger: 'blur'}
    ],
    password1: [
        {required: true, message: "Please enter the confirmation password."},
        {min: 6, message: 'Password must be at least 6 characters.', trigger: 'blur'},
        {validator: validatePass2, trigger: 'blur'}
    ],
    realname: [{required: true, message: "Please fill in"}],
    contact: [
        {required: true, message: "Please fill in"},
        {min: 11, max: 11, message: 'Phone number must be 11 digits.', trigger: 'blur'},
        {validator: validateMobileFormat, trigger: 'blur'}//手机格式检查
    ],
    idcard: [
        {required: false, message: "Optional fill in"},
    ],
    email: [
        {required: true, message: "Please fill in"},

        {validator: validateEmail, trigger: 'blur'}// 格式检查


    ],

    // address: [
    //     {required: true, message: "Please fill in"},
    // ],
    
    code: [
        {required: true, message: "Please enter the verification code."},
        {
            validator: (rule, value, callback) => {
                console.log(value)
                console.log(sendCode.value)
                if (Number(value) !== Number(sendCode.value)) {
                    callback(new Error('Incorrect verification code'))
                } else {
                    callback()
                }
            }, trigger: 'blur'
        }
    ]
}

onMounted(() => {

    loginForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
    if (postFormRef.value) {
        //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
        //然后watch变化 后,再调用 就可以使用了
        postFormRef.value.resetFields()
    }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用

})


//
function saveData() {
    //console.dir(postForm.value.depidArray)
    postFormRef.value.validate(valid => {


        if (valid) {

            axios_request.post('/front/register.action', loginForm.value).then(response => {

                ElMessage({
                    message: response.message
                })

                //如果操作成功则到 登录页,操作不成功 还留在此页
                if (response.success) {

                    router.push({path: '/login'})
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
    passwordStrength.value = checkPasswordStrength(loginForm.value.password)
}

// 计算属性
const passwordStrengthClass = computed(() => {
    return {
        [passwordStrength.value]: !!loginForm.value.password
    }
})

const passwordStrengthText = computed(() => {
    if (!loginForm.value.password) return ''
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
