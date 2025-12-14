<template>
    <el-card style="margin-bottom:20px;">


        <div class="user-bio">



        </div>
    </el-card>
</template>

<script setup>
import store from '@/store'    // For getting cached user information
const postFormRef = ref(null) // Form reference if page contains form
const route = useRoute();   // Get route information and parameters
const router = useRouter();   // Handle route operations like navigation
import {ElMessage, ElMessageBox} from "element-plus";  // Notifications and message boxes


const keyword = ref('')// Search keyword
onMounted(() => {
    getList()

})


function getList() {


}

</script>

<style lang="scss" scope>
.box-center {
    margin: 0 auto;
    display: table;
}

.text-muted {
    color: #777;
    text-indent: 2em;
    line-height: 25px;
    font-size: 14px;
}

.user-profile {
    .user-name {
        font-weight: bold;
    }

    .box-center {
        padding-top: 10px;
    }

    .user-role {
        padding-top: 10px;
        font-weight: 400;
        font-size: 14px;
    }

    .box-social {
        padding-top: 30px;

        .el-table {
            border-top: 1px solid #dfe6ec;
        }
    }

    .user-follow {
        padding-top: 20px;
    }
}

.user-bio {
    color: #606266;

    span {
        padding-left: 4px;
    }

    .user-bio-section {
        font-size: 14px;
        padding: 15px 0;

        .user-bio-section-header {
            border-bottom: 1px solid #dfe6ec;
            padding-bottom: 10px;
            margin-bottom: 10px;
            font-weight: bold;
        }
    }

    /*时间线的标题*/
    .el-timeline-item__content {
        font-weight: bold;
        font-size: 15px;
        overflow: hidden;
        width: 95%;
        text-overflow: ellipsis;
        white-space: nowrap;
        text-align: left;
        color: #aaaaaa;
    }

    /*时间线的时间*/
    .el-timeline-item__timestamp {
        text-align: right;
    }

    .el-timeline-item {

        padding-bottom: 45px;
    }
}


/*时间线样式*/
ul {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 0px;
}


</style>
