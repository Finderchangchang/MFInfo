package gy.mf.info.control.login

import com.lzy.okgo.OkGo
import gy.mf.info.base.App
import gy.mf.info.base.IBear
import gy.mf.info.model.TotalModel
import gy.mf.info.model.UserModel
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import gy.mf.info.util.urls
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback

/**
 * Created by lenovo on 2017/8/6.
 */
interface ILogin : IBear {
    /**
     * 登录结果处理
     * */
    fun login_result(result: Boolean)
}

class LoginListener(iLogin: ILogin) {
    var iLogin = iLogin
    /***
     * 根据账号密码进行登录
     * @name 登录账号
     * @pwd 密码
     */
    fun login(name: String, pwd: String) {
        var f=urls().string2MD5(pwd)
        OkGo.post(urls().login)
                .params("account",name)
                .params("password",urls().string2MD5(pwd))
                .execute(object : JsonCallback<TotalModel<UserModel>>() {
                    override fun onSuccess(model: TotalModel<UserModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.data!!.state == "2") {
                            iLogin.login_result(true)
                            Utils.putCache(key.user_id,model.data!!.account)
                        } else {
                            iLogin.login_result(false)
                        }
                        iLogin.error_msg(model.message)
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        iLogin.no_intent()
                    }
                })

    }
}
