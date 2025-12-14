<template>
    <div class="app-wrapper">
        <video class="bg-video" autoplay loop muted playsinline>
            <source src="../../static/login_v.mp4" type="video/mp4">
        </video>
        <div>
            <navbar/>
            <app-main/>
            <el-affix>
                <bottom/>
            </el-affix>

        </div>
    </div>
</template>

<script name="layout" setup>
import '@/styles/front_css.scss'
import store from '@/store'
const postFormRef = ref(null)
const route = useRoute();
const router = useRouter();
import {ElMessage,ElMessageBox} from "element-plus";

// import navbar from './Navbar'
import bottom from './Bottom'
import AppMain from './AppMain'




</script>

