package gy.mf.info.control.img_detail

import com.lzy.okgo.OkGo
import gy.mf.info.base.IBear
import gy.mf.info.model.TotalModel
import gy.mf.info.model.UserModel
import gy.mf.info.util.urls
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback

/**
 * 图片列表页
 * Created by Administrator on 2017/8/10.
 */
interface IImgDetail : IBear {
    fun show_type_list()
    fun sc_result()
}

class ImgDetailListener(img: IImgDetail) {
    var do_some = img
    /**
     * 获得整体分类
     * @type 分类id（男,女）
     * */
    fun getTypeList(type: String) {
        OkGo.post(urls().login)
                .execute(object : JsonCallback<TotalModel<UserModel>>() {
                    override fun onSuccess(model: TotalModel<UserModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.code == 200) {
                            //do_some.reg_result(true)
                        } else {
                            //do_some.reg_result(false)
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