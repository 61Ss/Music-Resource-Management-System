<template>
    <div class="login-box_aa">

        <div class="login-title"><h2>User Login</h2></div>
        <el-form status-icon :model="loginForm" :rules="loginRules" ref="postFormRef" label-width="120px"
        >
            <el-input name="username" type="text" v-model="loginForm.username"
                      autoComplete="on"
                      placeholder="Please enter username"
                      :prefix-icon="User"
            />


            <el-input name="password" type="password" v-model="loginForm.password"
                      autoComplete="on"
                      placeholder="Please enter password"
                      :prefix-icon="Unlock"

                      style="margin-top: 20px"/>
            <div style="margin-top: 20px" v-if="is_login_code">

                <el-input
                    name="loginCode"
                    type="number"
                    @keyup.enter.native="handleLogin"
                    v-model="loginForm.loginCode"
                    autoComplete="off"
                    :prefix-icon="Coin"
                    placeholder="Verification code" style=" width:160px"
                />

                <img v-if="loginCode_img_src!=''" title="Click to change verification code"
                     style="width:120px;height:32px;margin-right:50px;border-radius: 4px;cursor:pointer;float: right "
                     :src="loginCode_img_src"
                     @click="get_login_code()"
                />


            </div>
            <el-button :loading="loading" type="primary" @click="handleLogin()" style="margin-top: 20px"
            >Login
            </el-button>
            <div style="display: flex; justify-content: space-between; margin-top: 10px">
                <router-link tag="a" to="/register">
                    <el-button size="small" type="primary" style="margin-top: 10px">Register</el-button>
                </router-link>
                <router-link tag="a" to="/default">
                    <el-button size="small" type="primary" style="margin-top: 10px">Home</el-button>
                </router-link>
                <router-link tag="a" to="/pwdback">
                    <el-button size="small" type="primary" style="margin-top: 10px">Forgot Password</el-button>
                </router-link>
            </div>
        </el-form>

    </div>


</template>


<script name="login" setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) // If the page has a form, use this ref
const route = useRoute();   // Get route information and parameters
const router = useRouter();   // Handle route operations like navigation
import {ElMessage, ElMessageBox} from "element-plus";  // Notifications and message boxes
import {User, Unlock, Coin} from '@element-plus/icons-vue'
import axios_request from '@/utils/axios_request'

import PanThumb from '@/components/PanThumb'
import {mapGetters} from "vuex";


//Get information after login
const token_front = computed(() => {
    return store.getters.token_front
})
const info_front = computed(() => {
    return store.getters.info_front
})
const userid_front = computed(() => {
    return store.getters.userid_front
})


const loading = ref(false)// Loading indicator
const loginForm = ref({
    username: '',
    password: '',
    loginCode: '',
    loginCode_create: ''
})
const loginCode_img_src = ref('')// Verification code image URL from backend
const loginCode_create = ref('')// Backend-generated verification code
const is_login_code = ref(true)//Whether to enable verification code
const auto_fill_login_username = ref(true)//Whether to enable auto-fill username

const loginRules = {
    username: [{required: true, message: "Please enter username", trigger: 'blur'}],
    password: [{required: true, message: "Please enter password", trigger: 'blur'}]
}
onMounted(() => {
    nextTick(() => {

        get_login_code()


    })
})

function get_login_code() {
    axios_request.get(
        '/system/getCpacha.action?vl=4&w=120&h=32&type=loginCpacha'
    ).then(response => {
        loginCode_img_src.value = (response.data.base64)

        auto_fill_login_username.value = response.data.auto_fill_login_username
        is_login_code.value = response.data.is_login_code
        loginCode_create.value = response.data.login_code

        // if (auto_fill_login_username.value) {
        //     loginForm.value = {
        //         username: 'test',
        //         password: '111111',
        //         role_id: '0',
        //         loginCode: loginCode_create.value,
        //     }

        // }
        loginForm.value.loginCode_create = loginCode_create.value
    })

}

function handleLogin() {
    if (is_login_code.value && loginCode_create.value !== loginForm.value.loginCode) {
        ElMessage.error("Verification code error, please re-enter")
        return
    }
    postFormRef.value.validate(valid => {
        if (valid) {
            loading.value = true
            store.dispatch('SetStoreLoginByusername_front', loginForm.value).then(response => {

                loading.value = false
                window.location.href = '/#/default'//Redirect to homepage
                location.reload();// Refresh the entire interface to force reload, otherwise cached data cannot be obtained
            }).catch(err => {
                //console.log(err)
                loading.value = false
                ElMessage.error(err)

                //return false
            })
        } else {
            //console.log('error submit!!')
            return false
        }
    })
}

</script>


<style lang="scss" scoped>


.login-box_aa {

    width: 356px;
    margin: 0 auto;
    min-height: 550px;
    padding-top: 190px;

    .login-title {
        padding-bottom: 20px;

        h2 {
            text-align: center;
            padding-bottom: 10px;
            font-size: 36px;
            color: #FFFFFF;
            font-weight: bold;
        }
    }

    .el-button {
        border: medium none;
        border-radius: 2px;
        color: #fff;
        cursor: pointer;
        display: inline;

        height: 40px;
        width: 100%;

    }

}

</style>
