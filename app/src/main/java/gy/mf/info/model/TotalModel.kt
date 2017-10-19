package gy.mf.info.model

/**
 * Created by lenovo on 2017/8/8.
 */

class TotalModel<T> {

    /**
     * code : 200
     * message : 登陆成功
     * data : {"state":"2","account":""}
     */

    var code: Int = 0
    var message: String = ""
    var data: T? = null
    var msg: String? = null
}
