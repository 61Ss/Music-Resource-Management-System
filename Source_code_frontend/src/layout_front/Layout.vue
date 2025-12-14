<template>
    <div class="app-wrapper">
        <video ref="videoRef" class="bg-video" autoplay loop muted playsinline>
            <source :src="currentVideo" type="video/mp4">
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
import { ref, onMounted } from 'vue'
const postFormRef = ref(null)
const route = useRoute();
const router = useRouter();
import {ElMessage,ElMessageBox} from "element-plus";

import navbar from './Navbar'
import bottom from './Bottom'
import AppMain from './AppMain'

// 背景视频数组 - 使用import方式引入视频文件
import bg1 from '../../static/bg1.mp4'
import bg2 from '../../static/bg2.mp4'
import bg3 from '../../static/bg3.mp4'

const backgroundVideos = [
    bg1,
    bg2,
    bg3,
]

// 当前选择的视频
const currentVideo = ref(backgroundVideos[0])
const videoRef = ref(null)

// 在组件挂载时随机选择一个视频
onMounted(() => {
    const randomIndex = Math.floor(Math.random() * backgroundVideos.length)
    currentVideo.value = backgroundVideos[randomIndex]
})
</script>