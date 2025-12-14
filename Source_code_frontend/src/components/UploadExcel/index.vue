<template>
    <div v-loading="loading">
        <input id="excel-upload-input" type="file" accept=".xlsx, .xls" class="c-hide" @change="handkeFileChange">
        <el-button style="margin-left:16px;" size="small" type="primary" @click="handleUpload">浏览文件</el-button>
        <span>{{ uploadFileName }}</span>
    </div>
</template>

<script setup>
import store from '@/store'    //
const postFormRef = ref(null) // 
const route = useRoute();   //
const router = useRouter();   //
import {ElMessage,ElMessageBox} from "element-plus";  //
import * as XLSX from 'xlsx'

const emits = defineEmits(['on-selected-file'])
const loading = ref(false)//

const excelSheetData = ref({});//
const uploadFileName = ref('')


function generateDate(datas) {

    excelSheetData.value = datas
    emits('on-selected-file', excelSheetData.value)
    loading.value = false
}

function handleUpload(){
    document.getElementById('excel-upload-input').click()
}

function handkeFileChange(e) {
    //console.log(e)
    const files = e.target.files
    const itemFile = files[0] // only use files[0]
    readerData(itemFile)
}

function readerData(itemFile) {
    if (itemFile) {
        const filesName = itemFile.name
        uploadFileName.value = filesName
        loading.value = true
        const reader = new FileReader()
        reader.onload = e => {
            const data = e.target.result
            const fixedData = fixdata(data)
            const workbook = XLSX.read(btoa(fixedData), {type: 'base64'})


            let datas = {}
           
            for (let i = 0; i < workbook.SheetNames.length; i++) {
                const sheetName = workbook.SheetNames[i]
                const worksheet = workbook.Sheets[sheetName]
                let header = get_header_row(worksheet)
                let results = XLSX.utils.sheet_to_json(worksheet)
                datas[i] = {header, results}
            }


            generateDate(datas)
        }
        reader.readAsArrayBuffer(itemFile)
    } else {

        const header = []
        const results = []
        uploadFileName.value = ''//显示文件名称
        generateDate({header, results})
    }
}

function fixdata(data) {
    let o = ''
    let l = 0
    const w = 10240
    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
    return o
}

function get_header_row(sheet) {
    const headers = []
    const range = XLSX.utils.decode_range(sheet['!ref'])
    let C
    const R = range.s.r
    /* start in the first row */
    for (C = range.s.c; C <= range.e.c; ++C) { /* walk every column in the range */
        var cell = sheet[XLSX.utils.encode_cell({c: C, r: R})]
        /* find the cell in the first row */
        var hdr = 'UNKNOWN ' + C // <-- replace with your desired default
        if (cell && cell.t) hdr = XLSX.utils.format_cell(cell)
        headers.push(hdr)
    }
    return headers
}

</script>

<style scoped>
#excel-upload-input {
    display: none;
    z-index: -9999;
}
</style>
