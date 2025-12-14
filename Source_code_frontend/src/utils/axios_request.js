import axios from 'axios'
import { ElMessageBox, ElMessage } from 'element-plus'
import store from '@/store'
import qs from 'qs'; 

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, 
    timeout: 60000 * 3, 
    withCredentials: false, 
    transformRequest: [function (data) {
        if (data && !Array.isArray(data)) {
            return (qs.stringify(data, {arrayFormat: 'indices'}))
        }
        return data;
    }]
})

// request interceptor
service.interceptors.request.use(
    config => {
        // do something before request is sent
        if (store.getters.token || store.getters.token_front) {
            const currentUrl = window.location.href;
            const ismanager = currentUrl.indexOf('manager/') > 0
            const token_aa = ismanager ? store.getters.token : store.getters.token_front

            config.headers['token'] = token_aa // Send token to resource server

            // console.log(config.headers['token'] )

            config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8' // Convert POST data (OBJECT type) from vue page to URL format like aa=1111&bb=4444 that can be received, corresponding to transformRequest settings, otherwise PHP cannot receive it
            // config.headers['Access-Control-Allow-Credentials'] = true
            config.headers['X-Requested-With'] = 'XMLHttpRequest'
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        let status_code = response.data.code
        let message_temp = ''
        let message = '<br>' + response.data.message + '<br>'

        if (status_code === 401) {
            ElMessageBox.alert('Login status invalid, please login again', 'Prompt', {
                confirmButtonText: 'Got it',
                type: 'warning'
            }).then(() => {
                store.dispatch('FedLogOut')
                store.dispatch('LogOut_front')

                window.location.href = '/#/default' // Jump to homepage
                location.reload(); // Reinstantiate vue-router object to avoid bugs
            })
        } else if (status_code === 500) {
            // 500 or other errors
            message_temp += status_code + ' ' + message + ' Error: Access failed, please try again '
        }
        if (message_temp !== '') {
            ElMessage({
                dangerouslyUseHTMLString: true, // Enable HTML code
                message: message_temp,
                type: 'error',
                duration: 5 * 1000
            })
        }
        return response.data
    },
    error => {
        console.dir(error)

        let status_code = 0
        let errors_data = []
        let message = ''
        if (error.response) {
            status_code = error.response.status
            errors_data = error.response.data.errors
            message = error.response.data.message
        } else {
            message = error.message + '<br>'
        }
        if (status_code === 401) {
            ElMessageBox.alert('Login status invalid, please login again', 'Prompt', {
                confirmButtonText: 'Got it',
                type: 'warning'
            }).then(() => {
                store.dispatch('FedLogOut')
                store.dispatch('LogOut_front')

                window.location.href = '/#/default'
                location.reload();
            })
        } else if (status_code === 422) {
            // 422 API reading error
            message += '<b>' + status_code + ' Error</b>'
            if (error.response.data) {
                message += ':' + message
            }

            let error_i = 0
            for (const item in errors_data) {
                for (const i in errors_data[item]) {
                    error_i++
                    message += '<br>' + error_i + ' ' + (errors_data[item][i])
                }
            }
        } else if (status_code === 403) {
            // 403 Permission error
            message = '<b>' + status_code + ' Error</b> ' + message
        } else {
            // 500 or other errors
            message += status_code + ' Error: Access failed, please try again '
        }
        ElMessage({
            dangerouslyUseHTMLString: true, // Enable HTML code
            message: message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
