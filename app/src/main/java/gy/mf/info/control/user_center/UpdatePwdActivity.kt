package gy.mf.info.control.user_center

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.lzy.okgo.OkGo

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.model.TotalModel
import gy.mf.info.model.TypeModel
import gy.mf.info.model.UserModel
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_update_pwd.*
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback

class UpdatePwdActivity : BaseActivity() {
    override fun initViews() {
        setContentView(R.layout.activity_update_pwd)
    }

    override fun initEvent() {
        main_back.setOnClickListener { finish() }
        save_btn.setOnClickListener {
            var old = old_pwd_et.text.toString().trim()
            var new = new_pwd_et.text.toString().trim()
            if (TextUtils.isEmpty(old) || TextUtils.isEmpty(new) || old.equals(new)) {
                toast("前后密码不一致，请重新输入")
            } else {
                OkGo.post(urls().user_password)
                        .params("old_pass", urls().string2MD5(old))
                        .params("new_pass", urls().string2MD5(new))
                        .params("id", Utils.getCache(key.user_id))
                        .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                            override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                                if (model.data!!.state == "1") {
                                    finish()
                                }
                                toast(model.message)
                            }

                            override fun onError(call: Call?, response: Response?, e: Exception?) {
                                super.onError(call, response, e)
                                toast("请检查网络连接")
                            }
                        })
            }
        }
    }
}
