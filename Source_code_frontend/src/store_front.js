import axios_request from '@/utils/axios_request'
//前台用户的缓存
const store_front = {
    state: {
        token_front: localStorage.getItem("Front-Token"),
        userid_front: localStorage.getItem("userid_front"),

        info_front: JSON.parse(localStorage.getItem("info_front"))//将存储的JSON转为对象,供使用
    },

    mutations: {
        SET_USERID_FRONT: (state, userid_front) => {
            state.userid_front = userid_front
            localStorage.setItem("userid_front", userid_front);
        },
        SET_TOKEN_FRONT: (state, token_front) => {
            state.token_front = token_front
            localStorage.setItem("Front-Token", token_front);

        },


        SET_INFO_FRONT: (state, info_front) => {

            var checkedIdStr = JSON.stringify(info_front);//后端过来的是实体对象,要转换为json存储
            state.info_front = checkedIdStr
            localStorage.setItem("info_front", checkedIdStr);
        },

    },

    actions: {
        // 用户名登录
        SetStoreLoginByusername_front({commit}, userInfo) {

            //console.log(userInfo)
            return new Promise((resolve, reject) => {

                axios_request.post('/front/login.action', userInfo).then(response => {
                    const data = response
                    //console.log(data)
                    if (data.code === 200) {
                        //console.log(data.data.info)
                        commit('SET_TOKEN_FRONT', data.data.token)
                        commit('SET_USERID_FRONT', parseInt(data.data.userid))
                        commit('SET_INFO_FRONT', data.data.info)
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
        LogOut_front({commit, state}) {
            return new Promise((resolve, reject) => {
                console.log(state);
                //console.log(logout(state.token));

                commit('SET_TOKEN_FRONT', '')
                commit('SET_USERID_FRONT', '')

                commit('SET_INFO_FRONT', '')
                resolve()
                /* }).catch(error => {
                     reject(error)
                 })*/
            })
        },

        // 代码中机器前端 登出
        FedLogOut_front({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN_FRONT', '')
                commit('SET_USERID_FRONT', '')

                commit('SET_INFO_FRONT', '')
                resolve()
            })
        },


    }
}

export default store_front
