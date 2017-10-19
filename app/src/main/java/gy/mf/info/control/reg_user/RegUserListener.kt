package gy.mf.info.control.reg_user

import com.lzy.okgo.OkGo
import gy.mf.info.base.IBear
import gy.mf.info.model.TotalModel
import gy.mf.info.model.UserModel
import gy.mf.info.util.urls
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback

/**
 * Created by Administrator on 2017/8/10.
 */
interface IRegUser : IBear {
    fun reg_result(result: Boolean)
}

class RegUserListener(reg: IRegUser) {
    var do_some = reg
    /**
     * 执行注册操作
     * @name 注册的账号
     * @pwd 注册的密码
     * */
    fun reg_do(tel: String, pwd: String,name:String,address:String) {
        OkGo.post(urls().reg_user)
                .params("account",name)
                .params("password",urls().string2MD5(pwd))
                .params("phone",tel)
                .params("address",address)
                .execute(object : JsonCallback<TotalModel<UserModel>>() {
                    override fun onSuccess(model: TotalModel<UserModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.code == 200) {
                            if(model.data!!.state=="1"){
                                do_some.reg_result(true)
                            }else{
                                do_some.reg_result(false)
                            }
                        } else {
                            do_some.reg_result(false)
                        }
                        do_some.error_msg(model.message)
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()
                    }
                })
    }
}