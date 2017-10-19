package gy.mf.info.control.check_img

import android.text.TextUtils
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import gy.mf.info.base.App
import gy.mf.info.base.IBear
import gy.mf.info.control.transfer.ImageDatat
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TotalModel
import gy.mf.info.model.TypeModel
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import gy.mf.info.util.urls
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback

/**
 * Created by lenovo on 2017/8/6.
 */
interface ICheckImg : IBear {
    //获得当前分类
    fun show_type_list(list: MutableList<TypeModel.Type>?)

    //获得图片列表
    fun show_pictures(list: MutableList<PictureModel>?)

    //收藏，取消收藏结果
    fun add_imgs_result(boolean: Boolean)

    fun show_type_list2(list: List<TotalModelMA.TypeModel.Type>?)
    fun show_pictures2(list: MutableList<ImageDatat.DataBean.LinkBean>?)

}

class CheckImgListener(img: ICheckImg) {
    var do_some = img
    /**
     * 收藏图片
     * @picture_id 图片的id
     * */
    fun addImgs(picture_id: String, method: String) {
        var user = Utils.getCache(key.user_id)
        OkGo.post(urls().user_collection + method)
                .params("user", Utils.getCache(key.user_id))
                .params("picture", picture_id)
                .params("token", App.token)
                .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                    override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        var s = ""
                        if (model.data!!.state == "1") {
                            if (("show").equals(method)) {
                                do_some.show_pictures(model.data!!.link)
                            } else {
                                do_some.add_imgs_result(true)
                            }
                        } else {
                            if (("show").equals(method)) {
                                do_some.show_type_list(null)
                            } else {
                                do_some.add_imgs_result(false)
                            }
                        }
                        if (!("show").equals(method)) {
                            do_some.error_msg(model.message)
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)

                        if (("show").equals(method)) {
                            do_some.show_type_list(null)
                            //do_some.no_intent()
                            do_some.add_imgs_result(false)
                        } else if (("del").equals(method)) {
                            do_some.add_imgs_result(true)
                        } else {
                            //do_some.no_intent()
                            do_some.add_imgs_result(false)
                        }
                    }
                })
    }

    /**
     * 分类查询图片列表
     * @type 分类id
     * @index 当前页面索引
     * */
    fun getImgs(type: String, index: Int, level: String) {
        OkGo.post(urls().total + "main_pic.php")
                .params("class_sex", type)
                .params("level", level)
                .params("amount", "20")
                .params("page", index)
                //.params("page_size", "5")
                .params("token", App.token)
                .execute(object : JsonCallback<TotalModel<MutableList<PictureModel>>>() {
                    override fun onSuccess(model: TotalModel<MutableList<PictureModel>>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        //if (model.data!!.state == "1") {
                        do_some.show_pictures(model.data!!)
                        //} else {
                        //  do_some.show_pictures(null)
                        //}
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()
                    }
                })
        /*OkGo.post(urls().user_picture)
                .params("id", type)
                .params("level", level)
                .params("amount", "20")
                .params("page", index)
                .params("token", App.token)
                .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                    override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.data!!.state == "1") {
                            do_some.show_pictures(model.data!!.link)
                        } else {
                            do_some.show_pictures(null)
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()
                    }
                })*/
    }

    /**
     * 获得当前分类
     * */
    fun getTypes2(type: String) {
        OkGo.post(urls().user_class)
                .params("sex", type)
                .execute(object : JsonCallback<TotalModel<TypeModel>>() {
                    override fun onSuccess(model: TotalModel<TypeModel>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        if (model.code == 200) {
                            do_some.show_type_list(model.data!!.all_content)
                        } else {
                            do_some.show_type_list(null)
                            do_some.error_msg(model.message)
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        do_some.no_intent()

                    }
                })
    }

    fun getTypes(type: String) {
        OkGo.post(urls().user_class)
                .params("sex", type)           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .params("token", App.token)           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .execute(object : StringCallback() {
                    override fun onSuccess(s: String, call: Call, response: Response) {
                        // s 即为所需要的结果
                        val typeModelTotalModelMA = Gson().fromJson(s, TotalModelMA::class.java)
                        do_some.show_type_list2(typeModelTotalModelMA.data.getAll_content())
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                    }
                })
    }

}