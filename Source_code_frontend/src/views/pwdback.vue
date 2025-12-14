<template>
    <div>


        <div class="page_body_box  ">
            <span>Password Recovery</span>
        </div>


        <div class="layout_page">

            <el-form class="form_web" status-icon :model="loginForm" :rules="pwdRules" ref="postFormRef"
                     label-width="140px"
            >
                <el-form-item label="Email" prop="email">
                    <el-input placeholder="Please enter" v-model="loginForm.email">
                        <template #append>
                            <el-button type="primary" @click="handleSend">Send</el-button>
                        </template>

                    </el-input>
                </el-form-item>


                <el-form-item label="Verification Code" prop="code">
                    <el-input name="code" type="number" v-model="loginForm.code"
                              autoComplete="on"
                              placeholder="Please enter"/>
                </el-form-item>


                <el-form-item>
                    <el-button :loading="loading" type="primary" @click="saveData()"
                               icon="coin">Verify
                    </el-button>
                    <div v-if="newpwd!=''">New password: {{ newpwd }}</div>
                </el-form-item>
            </el-form>
        </div>


    </div>
</template>

<script name="pwdback" setup>
import {ElMessage, ElMessageBox} from "element-plus";

const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();
const router = useRouter();
import store from '@/store'


import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'


import {validateEmail} from '@/utils/validate'
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等

import Upload from '@/components/Upload'

let formData = {

    email: '',

    code: '',
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


const newpwd = ref('')
const sendCode = ref('')
const loading = ref(false)//加载提示
const loginForm = ref(formData)
const pwdRules = {

    email: [
        {required: true, message: "Please fill in"},
        {validator: validateEmail, trigger: 'blur'}// 格式检查

    ],
    code: [
        {required: true, message: "Please enter"},
        {
            validator: (rule, value) => {
                if (Number(value) !== Number(sendCode.value)) {
                    return new Error('Incorrect verification code.');
                }
                return true;
            },
            trigger: 'blur' // 或 'change'
        }
    ],

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

            axios_request.post('/front/pwdpack.action?email=' + loginForm.value.email + '&pwdback_code=' + loginForm.value.code).then(response => {

                ElMessage({
                    message: response.message
                })

                newpwd.value = response.data.password
            })
        }
    })
}

function handleSend() {
    //console.dir(postForm.value.depidArray)

    if (loginForm.value.email == '') {
        ElMessage({
            message: "Email cannot be empty."
        })

    }

    axios_request.post('/front/sendMail03.action?email=' + loginForm.value.email).then(response => {

        ElMessage({
            message: response.message
        })

        sendCode.value = response.data.code
    })

}


</script>
