<template>
    <div>
        <el-form
            status-icon :model="postForm" :rules="rules"
            ref="postFormRef" label-width="120px"
            size="small"
        >


            <el-form-item label="name" prop="projectinfo_typename">
                <el-input placeholder="Please enter" v-model="postForm.projectinfo_typename"/>
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
import {mapGetters} from "vuex";

import {
    pickerRangOptions,
    pickerRangNoAfterOptions,
    pickerDayNoAfterOptions,
    pickerDayUseAfterOptions,
    pickerDayNobefoOptions
} from '@/components/mixin/mixin_publicData'  // 一些常用的DATA里的参数,如日期选择的参数项等

import Upload from '@/components/Upload'

let formData = {
    projectinfo_typename: '',

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
    projectinfo_typename: [{required: true, message: "Please fill in"}],


}


const ueditorconfig1 = {
    //多媒体编辑器的配置
    headers: {
        token: localStorage.getItem("Manager-Token") //   token传给资源服务器。
    },
    serverUrl: null,
    // UEditor 资源文件的存放路径，如果你使用的是 vue-cli 生成的项目，通常不需要设置该选项，vue-ueditor-wrap 会自动处理常见的情况，如果需要特殊配置，参考下方的常见问题2
    UEDITOR_HOME_URL: '/UEditor/',
    // 初始容器高度
    initialFrameHeight: 300,
    // 初始容器宽度
    initialFrameWidth: '100%',
    // 编辑器不自动被内容撑高
    autoHeightEnabled: false,
}


watch(() => props.dataId, newdata => {
    //console.log('监听到改变')
    fetchData()//根据id的变化 更新表单的值

}, {deep: true})

watch(() => props.dialogFormVisible, newdata => {
    //console.log('监听到改变')
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化
    if (newdata) fetchData()//监控弹窗是否显示,显示则初始化数据

}, {deep: true})

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

const role_front = computed(() => {
    return store.getters.role_front
})


// 初始化

onMounted(() => {
    postForm.value = JSON.parse(JSON.stringify(formData))//深拷贝了一份数据模板,用于表单初始化

    if (route.query.id) props.dataId = route.query.id//如果是在菜单中直接打开的路由编辑界面(不是弹窗界面),则从路由获取ID
    fetchData()
    //深拷贝了一份数据模板,用于表单初始化

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
        axios_request.get('/manager/projectinfo_type/getById.action?id=' + props.dataId).then(response => {
                if (response.data) {
                    postForm.value = response.data.projectinfo_type
                }
            }
        )
    } else {
        //显示添加数据
        axios_request.get('/manager/projectinfo_type/create.action').then(response => {
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
            postForm.value.username = ''
            postForm.value.password = ''

            if (props.dataId > 0) {
                //保存编辑数据
                postForm.value.projectinfo_typeid = props.dataId
                axios_request.post('/manager/projectinfo_type/update.action', postForm.value).then(response => {
                    ElMessage({message: response.message})
                    if (response.message) {
                        closeDialog()//必须在axios异步过程内
                    }
                })
            } else {
                //保存添加数据

                axios_request.post('/manager/projectinfo_type/add.action', postForm.value).then(response => {
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
