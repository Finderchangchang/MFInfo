package gy.mf.info.control.tribune

import android.text.TextUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import gy.mf.info.base.App
import gy.mf.info.base.IBear
import gy.mf.info.model.*
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import gy.mf.info.util.urls
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback
import java.io.File

/**
 * Created by lenovo on 2017/8/16.
 */
interface ITribune : IBear {
    fun reg_result(result: Boolean)
    //显示查询出的说说列表
    fun show_ss(list: MutableList<MessageModel>?, total_count: Int)

    /**
     * 添加图片返回的url
     * @local_file 本地地址
     * @url 服务器地址
     * */
    fun add_img_result(local_file: String, url: String)
}

class TribuneListener(img: ITribune) {
    var do_some = img
    /**
     * 发布，删除说说
     * @model 发布说说的model
     * @method 发布，删除方法
     * */
    fun add_delShuo(model: AddTribuneModel?, method: String) {
        if (!TextUtils.isEmpty(model!!.msg) || !TextUtils.isEmpty(model!!.picture)) {
            OkGo.post(urls().user_forum + method)
                    .params("msg", model!!.msg)
                    .params("reply", model!!.reply)
                    .params("picture", model!!.picture)
                    .params("user", Utils.getCache(key.user_id))
                    .params("token", App.token)
                    .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                        override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                            if (model.data!!.state == "1") {
                                do_some.reg_result(true)
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

    /**
     * 添加图片
     * @file 图片的file文件
     * */
    fun add_img(local_file: String) {
        OkGo.post(urls().user_forum_picture)
                .params("file", File(local_file))
                .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                    override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.data!!.state == "1") {
                            do_some.add_img_result(local_file, model.data!!.links!!.picture_name)
                        } else {
                            do_some.add_img_result(local_file, "")
                        }
                        do_some.error_msg(model.message)
                        //                        {"code":200,"message":"\u83b7\u53d6\u6210\u529f","data":{"state":"1","links":{"picture_id":"3","picture_name":"3b008e2335507db928b40aafd2704b12.jpg","picture_set":"","picture_url":""}}}
                        var s = ""
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()
                    }
                })
    }


    /**
     * 收藏图片
     * @index 当前页码
     * */
    fun searchShuoList(index: Int) {
        OkGo.post(urls().user_forum + "show")
                .params("amount", "20")
                .params("page", index)
                .params("token", App.token)
                .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                    override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.data!!.state == "1") {
                            do_some.show_ss(model.data!!.msg, model.data!!.num.toInt())
                        } else {
                            do_some.show_ss(null, 0)
                            do_some.error_msg(model.message)
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()
                    }
                })
    }
}