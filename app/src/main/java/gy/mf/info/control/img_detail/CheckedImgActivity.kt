package gy.mf.info.control.img_detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.ImgDetailsActivity
import gy.mf.info.control.check_img.CheckImgListener
import gy.mf.info.control.check_img.ICheckImg
import gy.mf.info.control.check_img.OneImgDetailActivity
import gy.mf.info.control.transfer.ImageDatat
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.ImgsModel
import gy.mf.info.model.PictureModel
import gy.mf.info.model.StarModel
import gy.mf.info.model.TypeModel
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_checked_img.*
import kotlinx.android.synthetic.main.activity_user_center.*
import java.util.*

class CheckedImgActivity : BaseActivity(), ICheckImg {
    override fun show_type_list2(list: List<TotalModelMA.TypeModel.Type>?) {
        if (list != null) {
            //type_list = ArrayList(list)//获得所有样式
            var s = ""
        }
    }

    override fun show_pictures2(list: MutableList<ImageDatat.DataBean.LinkBean>?) {
        }

    override fun show_type_list(list: MutableList<TypeModel.Type>?) {
        test_list = ArrayList()
//        for (model in list!!) {
//            if (model.collection_npicture == null) {
//                test_list.add(StarModel(model.collection_picture, "", false))
//            } else {
//                test_list.add(StarModel(model.collection_picture, model.collection_npicture, false))
//            }
//        }
        test_adapter!!.refresh(test_list)
    }

    internal var test_list: MutableList<PictureModel> = ArrayList()//购买的课程列表
    var test_adapter: CommonAdapter<PictureModel>? = null//购买的课程
    override fun show_pictures(list: MutableList<PictureModel>?) {
        test_list = list!!
        test_adapter!!.refresh(test_list)
    }

    override fun add_imgs_result(boolean: Boolean) {
        if (boolean) {
            CheckImgListener(this).addImgs("", "show")
        }
        if (!manage_click) {
            for (model in test_list) {
                model.click = false
            }
            manage_tv.text = "管理"
        } else {
            manage_tv.text = "取消"
        }
        test_adapter!!.refresh(test_list)
    }

    var manage_click = false
    override fun initViews() {
        setContentView(R.layout.activity_checked_img)
        main_back.setOnClickListener { finish() }
        test_adapter = object : CommonAdapter<PictureModel>(this, test_list, R.layout.item_iv) {
            override fun convert(holder: CommonViewHolder, model: PictureModel, position: Int) {
                holder.setGImage(R.id.iv, model.picture_name)
                holder.setRBChecked(R.id.check_rn, model.click)
                holder.setVisible(R.id.check_rn, manage_click)
                holder.setOnClickListener(R.id.item_rl) {
                    if (manage_click) {
                        var model = test_list[position]
                        model.click = !model.click
                        test_adapter!!.refresh(test_list)
                        for (mo in 0 until test_list.size) {
                            var m = test_list[mo]
                            if (m.click) {
                                manage_tv.text = "删除"
                                break
                            } else {
                                manage_tv.text = "取消"
                            }
                        }
                    } else {
                        var urls = ""
                        var model: PictureModel = PictureModel()
                        for (key in test_list) {
                            if (!TextUtils.isEmpty(key.picture_name)) {
                                urls += key.picture_name + ","
                            }
                        }
                        model.picture_name = urls
                        //var modd = ImgsModel(test_list as ArrayList<PictureModel>)
                        startActivity(Intent(this@CheckedImgActivity, ImgDetailsActivity::class.java)
                                .putExtra("position", position)
                                .putExtra("model", ImgsModel(test_list as ArrayList<PictureModel>))
                                .putExtra("show_xx", true)
                        )
                    }
                }
            }
        }
        gv.adapter = test_adapter
        gv.setOnItemClickListener { parent, view, position, id ->
            if (manage_click) {
//                var model = test_list[position]
//                model.click = !model.click
//                test_adapter!!.refresh(test_list)
                var model = test_list[position]
                model.click = !model.click
                test_adapter!!.refresh(test_list)
                for (mo in 0 until test_list.size) {
                    var m = test_list[mo]
                    if (m.click) {
                        manage_tv.text = "删除"
                    } else {
                        if (mo == test_list.size - 1 && !m.click) {
                            manage_tv.text = "取消"
                        }
                    }
                }
            } else {
                var img_lists: ArrayList<PictureModel> = ArrayList<PictureModel>()
                for (key in test_list) {
                    var model = PictureModel()
                    model.picture_name = key.picture_name
                    img_lists.add(model)
                }
                startActivity(Intent(this, ImgDetailsActivity::class.java)
                        .putExtra("position", position)
                        .putExtra("model", img_lists[position])
                        .putExtra("show_xx", true))
            }
        }
        manage_tv.setOnClickListener {
            manage_click = !manage_click
            if (manage_tv.text == "删除") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("提示")
                builder.setMessage("确定要删除吗？")
                builder.setNegativeButton("取消", null)
                builder.setPositiveButton("确定") { dialog, which ->
                    for (id in test_list) {
                        if (id.click) {
                            CheckImgListener(this).addImgs(id.picture_id, "del")
                        }
                    }
                }
                builder.setOnDismissListener {
                    hideSystemNavigationBar()
                }
                builder.show()
            } else {
                if (!manage_click) {
                    for (model in test_list) {
                        model.click = false
                    }
                    manage_tv.text = "管理"
                }
                test_adapter!!.refresh(test_list)
            }
        }
    }

    override fun initEvent() {
        CheckImgListener(this).addImgs("", "show")
    }
}
