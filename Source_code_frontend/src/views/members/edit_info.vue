<template>
    <div>


        <div class="page_body_box  ">
            <span>Edit Profile</span>
        </div>


        <div class="layout_page">

            <el-form class="form_web" status-icon :model="loginForm" :rules="pwdRules" ref="postFormRef"
                     label-width="140px"
            >
                        <el-form-item label="username" prop="username">
                            {{ loginForm.username }}

                        </el-form-item>



                        <el-form-item label="realname" prop="realname">
                            <el-input placeholder="Please enter" v-model="loginForm.realname"/>
                        </el-form-item>
                        <el-form-item label="QQ" prop="qq">
                            <el-input placeholder="Please enter" v-model="loginForm.qq"/>
                        </el-form-item>
                                   <el-form-item label="idcard" prop="idcard">
                <el-input placeholder="Please enter" v-model="loginForm.idcard"/>
            </el-form-item>
            <el-form-item label="email" prop="email">
                <el-input placeholder="Please enter" v-model="loginForm.email"/>
            </el-form-item>
                                             <el-form-item label="college" prop="college">
                                                    <el-input placeholder="Please enter" v-model="loginForm.college"/>
                                                </el-form-item>
                                                <el-form-item label="major" prop="major">
                                                    <el-input placeholder="Please enter" v-model="loginForm.major"/>
                                                </el-form-item> 
                        <el-form-item label="sex" prop="sex">
                            <el-radio-group v-model="loginForm.sex">
                                <el-radio value='male' key='male'>male</el-radio>
                                <el-radio value='female' key='famale'>famale</el-radio>
                            </el-radio-group>

                        </el-form-item>
                        <el-form-item label="birthday" prop="birthday">
                            <el-date-picker
                                value-format="YYYY-MM-DD"

                                :editable="false"                                 v-model="loginForm.birthday"
                                type="date"
                                placeholder="Select Date"
                                :disabled-date="pickerDayNoAfterOptions.disabledDate"
                                style="width:190px"

                            >
                            </el-date-picker>
                        </el-form-item>

                        <el-form-item label="contact" prop="contact">
                            <el-input placeholder="text" type="text" v-model="loginForm.contact"></el-input>
                        </el-form-item>
                        <!-- <el-form-item label="address" prop="address">
                            <el-input placeholder="text" type="text" v-model="loginForm.address"></el-input>
                        </el-form-item> -->
                        <el-form-item label="image" prop="image">
                            <Upload v-model="loginForm.image" accept=".jpg,.png,.gif"/>
                        </el-form-item>


            <el-form-item>
                <el-button :loading="loading" type="primary" @click="saveData()"
                           icon="coin">Save
                </el-button>
            </el-form-item>
        </el-form>
        </div>
    </div>
</template>


<script name="edit_info" setup>
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage,ElMessageBox} from "element-plus";  //提示框和消息



import {mapGetters} from 'vuex'
import axios_request from '@/utils/axios_request'

import {validateMobileFormat, validateEmail, validateIdentityCodeFormat} from '@/utils/validate'
import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions,
    pickerDayNobefoOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等
import {parseTime,} from '@/utils'


import Upload from '@/components/Upload'

let formData = {
    username: '',
    realname: '',
    sex: 'male',
    birthday: parseTime(new Date().getTime(), '{y}-{m}-{d}'),  /*'{y}-{m}-{d} {h}:{i}:{s}'*/
    contact: '',
    image: '',
    qq: '',
    idcard: '',
    email: '',

    iscooled: '',
    college: '',
    major: '',

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





                 const loading=ref(false)//加载提示
            const loginForm=ref(formData)

  const             pwdRules= {
                username: [{required: true, message: "Please fill in"}],

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
            }

onMounted(() =>
 {

        loginForm.value = JSON.parse(JSON.stringify(formData))//初始化数据
        if (postFormRef.value) {
            //这个fetchData第一次是created调用的,界面还未初始化,resetFields功能是不可使用的
            //然后watch变化 后,再调用 就可以使用了
            postFormRef.value.resetFields()
        }//对整个表单进行重置，将所有字段值重置为初始值并移除校验结果 只能在methods中使用
        fetchData()



})



         function fetchData() {
            axios_request.get('/front/preInfo.action?id=' + userid_front.value).then(response => {
                    if (response.data) {
                        loginForm.value = response.data.members
                    }
                }
            )


        }
        //
         function saveData() {
            //console.dir(postForm.value.depidArray)
            postFormRef.value.validate(valid => {


                if (valid) {


                    axios_request.post('/front/edit_info.action', loginForm.value).then(response => {
                        ElMessage({
                            message: response.message
                        })


                    })
                }
            })
        }


</script>
