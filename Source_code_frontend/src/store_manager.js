import axios_request from '@/utils/axios_request'
//管理员的缓存
const store_manager = {
    state: {
        token: localStorage.getItem("Manager-Token"),
        managerid: localStorage.getItem("managerid"),
        role_id: localStorage.getItem("role_id"),
        username: localStorage.getItem("username"),
        info: JSON.parse(localStorage.getItem("info"))//将存储的JSON转为对象,供使用

    },

    mutations: {
        SET_MANAGERID: (state, managerid) => {
            state.managerid = managerid
            localStorage.setItem("managerid", managerid);
        },
        SET_TOKEN: (state, token) => {
            state.token = token
            localStorage.setItem("Manager-Token", token);

        },
        SET_USERNAME: (state, username) => {
            state.username = username
            localStorage.setItem("username", username);
        },
        SET_ROLE_ID: (state, role_id) => {
            state.role_id = role_id
            localStorage.setItem("role_id", role_id);
        },
        SET_INFO: (state, info) => {


            var checkedIdStr = JSON.stringify(info);//后端过来的是实体对象,要转换为json存储
            state.info = checkedIdStr
            localStorage.setItem("info", checkedIdStr);

        },

    },

    actions: {
        // 用户名登录
        SetStoreLoginByusername({commit}, userInfo) {

            //console.log(userInfo)
            return new Promise((resolve, reject) => {

                axios_request.post('/manager/login/login.action', userInfo).then(response => {
                    const data = response
                    //console.log(data)
                    if (data.code === 200) {
                        //console.log(data.data.token)

                        commit('SET_TOKEN', data.data.token)
                        commit('SET_MANAGERID', parseInt(data.data.managerid))
                        commit('SET_USERNAME', data.data.username)
                        //commit('SET_ROLE_ID', parseInt(data.data.role_id))
                        commit('SET_INFO', data.data.info)

                        resolve()
                    } else {
                        reject(data.message);

                    }
                }).catch(error => {
                    console.log(error)
                    reject("登录失败请重新登录")
                })


            })
        },


        // 人工操作登出
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                console.log(state);
                //console.log(logout(state.token));

                commit('SET_TOKEN', '')
                commit('SET_MANAGERID', '')
                commit('SET_USERNAME', '')
                commit('SET_ROLE_ID', '')
                commit('SET_INFO', '')
                resolve()
                /* }).catch(error => {
                     reject(error)
                 })*/
            })
        },

        // 代码中机器前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_MANAGERID', '')
                commit('SET_USERNAME', '')
                commit('SET_ROLE_ID', '')
                commit('SET_INFO', '')


                resolve()
            })
        },


    }
}

export default store_manager
