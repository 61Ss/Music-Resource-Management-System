import {createStore} from 'vuex'
import store_manager from './store_manager'
import store_front from './store_front'//前台用户


export default createStore({
    modules: {

         store_manager: store_manager,//后台用户
          store_front: store_front,//前台用户
    },
    getters: {


        token: state => state.store_manager.token,
        managerid: state => state.store_manager.managerid,
        username: state => state.store_manager.username,
        role_id: state => state.store_manager.role_id,
        info: state => state.store_manager.info,

        token_front: state => state.store_front.token_front,
        userid_front: state => state.store_front.userid_front,
        info_front: state => state.store_front.info_front,

    }
})

