<template>
    <el-container>

        <el-aside>


            <el-scrollbar>
                <el-menu
                    ref="menuRef"
                    :collapse="false"
                    :collapse-transition="false"
                >

                    <el-menu-item
                        index="default"
                        key="default"
                        @click="handleToRouterLink('/manager/default')"
                    >
                        <el-icon>
                            <Menu/>
                        </el-icon>
                        Home
                    </el-menu-item>


                        <el-menu-item index="projectinfo-1"
                                      @click="handleToRouterLink('/manager/projectinfo_type/search')"> <el-icon>
                            <Menu/>
                        </el-icon>Category Management
                        </el-menu-item>

                        <el-menu-item index="projectinfo-2" @click="handleToRouterLink('/manager/projectinfo/search')">
                            <el-icon>
                                <Menu/>
                            </el-icon> Music Management
                        </el-menu-item>



                    <el-menu-item
                        index="1"
                        key="1" v-if="managerid==1"
                        @click="handleToRouterLink('/manager/manager/search')"
                    >
                        <el-icon>
                            <Menu/>
                        </el-icon>
                        Administrator List
                    </el-menu-item>


                    <el-menu-item
                        index="15"
                        key="15"
                        @click="handleToRouterLink('/manager/members/search')"
                    >
                        <el-icon>
                            <Menu/>
                        </el-icon>
                        User List
                    </el-menu-item>


                </el-menu>

                <div class="bottom">


                    Welcome:
                    <b>
                        <!--{{managerid }}-&#45;&#45;{{role_id }}-&#45;&#45;  -->

                        {{ info.realname || '' }} {{ username }}
                    </b>


                    <router-link to="" @click.native="dialogPwdFormVisible = true">

                        Change Password
                    </router-link>
                    <router-link to="" @click.native="logout">

                        Logout
                    </router-link>

                </div>
            </el-scrollbar>

        </el-aside>
        <el-main>
            <app-main/>
        </el-main>
    </el-container>


    <!--单列标准尺寸 width="40%" top="10vh"-->
    <el-dialog v-if="dialogPwdFormVisible" :close-on-click-modal="false" title="Change Password"
               v-model="dialogPwdFormVisible" width="40%" top="10vh" key="dialogPwdFormVisible">
        <change-pwd
            @close="dialogPwdFormVisible=false">
        </change-pwd>
    </el-dialog>
</template>


<script name="Layout" setup>
import '@/styles/manager_css.scss' // 后台样式css
import store from '@/store'    //用于获取缓存的用户信息
const postFormRef = ref(null) // 如果页面有表单,则使用此ref
const route = useRoute();   //获取路由中的信息或参数
const router = useRouter();   //操作路由,跳转之类的
import {ElMessage, ElMessageBox} from "element-plus";  //提示框和消息

import AppMain from './AppMain'

import {mapGetters} from 'vuex'
import changePwd from '@/views/manager/changePwd'


const dialogPwdFormVisible = ref(false);//是否显示密码修改对应框


const role = ref('')//权限汉字名称

//获取登录后的信息


const token = computed(() => {
    return store.getters.token
})
//const username = ref('admin')

const username = computed(() => {
    //console.log(store.getters.username)
    if (store.getters.username === null || store.getters.username === '') {
        window.location.href = '/#/manager/login'//跳转到登录页
        location.reload();// 整个界面刷新  强制刷新  否则获取不到 缓存数据

    }

    return store.getters.username
})

const role_id = computed(() => {
    return Number(store.getters.role_id)
})

const info = computed(() => {

    //console.log(store.getters.info)
    if (store.getters.info === null || store.getters.info === '') {
        window.location.href = '/#/manager/login'//跳转到登录页
        location.reload();// 整个界面刷新  强制刷新  否则获取不到 缓存数据

    }
    return store.getters.info
})


const managerid = computed(() => {
    return store.getters.managerid
})


// 初始化

onMounted(() => {


})


function handleToRouterLink(routerPath) {
    //console.log(routerPath)
    //菜单 的路由跳转
    router.push(routerPath)
}

async function logout() {
    ElMessageBox.confirm('Confirm Logout?', 'Notification').then(async () => {
        await store.dispatch('LogOut')
        window.location.href = '/#/manager/login'//跳转到登录页

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


.el-container {
    background: #f2f2f2;
    height: 100%;
    display: flex;
    overflow: hidden;


}


// 左边菜单
.el-aside {
    background-color: #e9eff4 !important;
    width: 200px;
    overflow-x: hidden;
    transition: width ease .3s;
    height: 100%;
    position: fixed;
    left: 0;

    bottom: 0;
    z-index: 999;
    box-shadow: 2px 1px 12px 2px rgba(0, 0, 0, .45);


    .el-scrollbar {
        height: calc(100%);
    }

    .el-scrollbar__wrap {
        overflow-x: hidden !important;
    }

    // 菜单样式
    .el-menu {
        border-right: 0 !important;
        user-select: none;

        --el-menu-text-color: rgba(25, 45, 87, 0.9);
        --el-menu-hover-text-color: #ffffff;
        --el-menu-active-color: #c51a1a;
        --el-menu-bg-color: #e9eff4;
        --el-menu-hover-bg-color: rgba(0, 0, 0, .1);
        // 子菜单
        .el-sub-menu {
            --el-menu-bg-color: rgba(0, 0, 0, .3);
            /*
            --el-menu-base-level-padding:40px
            */

            --el-menu-level-padding: 40px

        }


    }
}

.bottom {
    color: #090e0e;
    font-size: 14px;
    position: absolute;
    bottom: 0;
    padding-bottom: 10px;
    text-align: center;
    line-height: 25px;
    margin: 0 auto;
    width: 100%;
    background-color: rgba(0, 0, 0, .03) !important;
    box-shadow: 0 1px 18px 2px rgba(0, 0, 0, .15);
    text-shadow: 0 3px 8px rgba(27, 58, 121, 0.9);

    a:hover {
        color: #9a3636;
    }

    a {
        display: block;
        color: #090e0e;
    }
}

// 右边内容
.el-main {
    position: absolute;
    right: 0;
    z-index: 900;
    width: calc(100% - 200px);
    box-sizing: border-box;
    padding: 0;


}

// 页面过渡动画
.fade-enter-active, .fade-leave-active {
    transition: all .5s;
    opacity: 1;
    position: absolute;
    width: 100%;
}

.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
    transform: translateY(200px);
    opacity: 0;
    transition: all .5s;
    position: absolute;
}
</style>
