<template>


    <div class="header">


        <div class="logo_img  ">
            <img src="../../static/logo.png">
        </div>
        <div class="logo_text  "> EchoVerse</div>


        <el-menu
            mode="horizontal"
            :default-active="$route.path"
            text-color="#c9c9dc"
            :ellipsis="false"
            class="nav_menu"
        >


            <el-menu-item
                index="default"
                key="default"

                @click="handleToRouterLink('/default')"
            >

                Home
            </el-menu-item>


            <el-menu-item
                v-for="(item,index) of projectinfo_type"

                :key="'aaaa'+item.projectinfo_typeid"
                :index="'aaaa'+item.projectinfo_typeid"

                @click="handleToRouterLink('/projectinfo?projectinfo_typeid='+item.projectinfo_typeid)"

            >

                {{ item.projectinfo_typename }}
            </el-menu-item>


        </el-menu>


        <el-menu
            mode="horizontal"
            :default-active="$route.path"
            text-color="#c9c9dc"
            :ellipsis="false"
            class="menu_right"
        >
            <form>
                <el-input placeholder="Please enter keywords" v-model="keyword"
                          @keyup.enter="handleSearch"
                          @change="handleSearch"
                />

            </form>

            <el-menu
                :ellipsis="false"
                mode="horizontal"
                :default-active="$route.path" v-if="Number(userid_front)>0"
            >
                <el-sub-menu index="1000">
                    <template #title>
                        <el-avatar size="default" :src="BASE_API+info_front.image"></el-avatar>
                        User
                        <!--{{info_front.realname }}--> {{ info_front.username }}
                    </template>

                    <el-menu-item
                        index="changePwd"
                        key="changePwd"
                        @click="dialogPwdFormVisible = true"


                    >
                        Change Password
                    </el-menu-item>


                    <el-menu-item
                        index="edit_info"
                        key="edit_info"
                        @click="handleToRouterLink('/edit_info')"
                    >
                        Edit Profile
                    </el-menu-item>

                    <hr/>


                    <el-menu-item
                        index="projectinfo"
                        key="projectinfo"
                        @click="handleToRouterLink('/members/projectinfo')"
                    >
                        My Music Management
                    </el-menu-item>
                    <el-menu-item
                        index="projectinfo_type"
                        key="projectinfo_type"
                        @click="handleToRouterLink('/members/projectinfo_type')"
                    >
                        Music Categorization
                    </el-menu-item>


                    <el-menu-item
                        index="logout"
                        key="logout"
                        @click="logout"
                    >
                        Log Out
                    </el-menu-item>

                </el-sub-menu>
            </el-menu>

            <el-menu-item v-if="!Number(userid_front)>0"
                          index="login"
                          key="login"
                          style="float: right"
                          @click="handleToRouterLink('/login')"
            >
                Log In
            </el-menu-item>
            <el-menu-item v-if="!Number(userid_front)>0"
                          index="register"
                          key="register"
                          style="float: right"
                          @click="handleToRouterLink('/register')"
            >
                Register
            </el-menu-item>
        </el-menu>
    </div>


    <el-dialog v-if="dialogPwdFormVisible" :close-on-click-modal="false" title="Change Password"
               v-model="dialogPwdFormVisible" width="40%" top="10vh" key="dialogPwdFormVisible">
        <change-pwd
            @close="dialogPwdFormVisible=false">
        </change-pwd>
    </el-dialog>


</template>

<script setup>
import store from '@/store'
const postFormRef = ref(null)
const route = useRoute();
const router = useRouter();
import {ElMessage, ElMessageBox} from "element-plus";

import {mapGetters} from 'vuex'
import changePwd from '@/views/changePwd'
import axios_request from '@/utils/axios_request'


const BASE_API = ref(process.env.VUE_APP_BASE_API + '/');

const dialogPwdFormVisible = ref(false);
const keyword = ref('')

const projectinfo_type = ref({});


const token_front = computed(() => {
    return store.getters.token_front
})
const info_front = computed(() => {
    return store.getters.info_front
})
const userid_front = computed(() => {
    return store.getters.userid_front
})


const managerid = computed(() => {
    return store.getters.managerid
})
const role_front = computed(() => {
    return store.getters.role_front
})


onMounted(() => {
    getList()

})


function handleSearch() {
    let key = ''
    if (keyword.value != '') key = '?keyword=' + keyword.value
    router.push('/projectinfo' + key)
}

function getList() {

    axios_request.get('/front/projectinfo_typeList.action', {
        params: null
    }).then(response => {
        projectinfo_type.value = response.data.projectinfo_typeList
    })


}

function handleToRouterLink(routerPath) {

    router.push(routerPath)

}

async function logout() {
    ElMessageBox.confirm('Confirm Logout?', 'Notification').then(async () => {
        await store.dispatch('LogOut_front')
        window.location.href = '/#/login'
        location.reload();

    }).catch(() => {
    });
}


</script>

<style lang="scss" scoped>
hr {
    height: 1px;
    margin: 5px 5px;
    border: 0;
    clear: both;


    background-color: #e6e6e6;
}

</style>


