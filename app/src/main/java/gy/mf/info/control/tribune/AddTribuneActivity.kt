package gy.mf.info.control.tribune

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kaopiz.kprogresshud.KProgressHUD

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.model.AddTribuneModel
import gy.mf.info.model.MessageModel
import gy.mf.info.model.PictureModel
import kotlinx.android.synthetic.main.activity_add_tribune.*
import me.iwf.photopicker.PhotoPicker
import me.iwf.photopicker.PhotoPreview
import java.io.File
import java.util.*

/**
 * 发布消息
 * */
class AddTribuneActivity : BaseActivity(), ITribune {
    override fun show_ss(list: MutableList<MessageModel>?, total_count: Int) {

    }

    override fun add_img_result(local_file: String, url: String) {
        src_list.add(local_file)
        img_list.add(url)
        refresh()
    }


    var img_list: ArrayList<String> = ArrayList<String>()
    var src_list = ArrayList<String>()
    var kp_dialog: KProgressHUD? = null
    override fun initViews() {
        setContentView(R.layout.activity_add_tribune)
        kp_dialog = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
        back_iv.setOnClickListener { finish() }
    }

    /**
     * 添加结果
     * */
    override fun reg_result(result: Boolean) {
        if (result) {
            add_tv.isEnabled = true
            setResult(22)
            finish()
        }
    }

    override fun initEvent() {
        add_tv.setOnClickListener {
            if (ask_et.text.toString().trim().equals("")) {
                toast("内容不能为空")
            } else {
                var pictures = ""
                for (url in img_list) {
                    pictures += url + ":"
                }
                add_tv.isEnabled = false
                TribuneListener(this).add_delShuo(AddTribuneModel(ask_et.text.toString().trim(), pictures), "add")
            }
        }
        del1_iv.setOnClickListener {
            img_list.removeAt(0)
            src_list.removeAt(0)
            refresh()
        }
        del2_iv.setOnClickListener {
            img_list.removeAt(1)
            src_list.removeAt(1)
            refresh()
        }
        del3_iv.setOnClickListener {
            img_list.removeAt(2)
            src_list.removeAt(2)
            refresh()
        }
        select_img1.setOnClickListener {
            show_camera_pview(1)
        }
        select_img2.setOnClickListener {
            show_camera_pview(2)
        }
        select_img3.setOnClickListener {
            show_camera_pview(3)
        }
    }

    /**
     * 控制显示图片详情还是选择图片
     * @type 当前点击位置
     * @show_camera true显示选择图片界面
     * */
    fun show_camera_pview(type: Int) {
        if (img_list.size == type - 1) {
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setPreviewEnabled(false)
                    //.setSelected(img_list)
                    .start(this, PhotoPicker.REQUEST_CODE)
        } else if (img_list.size in type..type) {
            PhotoPreview.builder()
                    .setPhotos(src_list)
                    .setCurrentItem(type - 1)
                    .setShowDeleteButton(false)
                    .start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK && requestCode === PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                var list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                if (list.size > 0) {
                    //kp_dialog!!.show()
                    for (i in 0..list.size - 1) {
                        TribuneListener(this).add_img(list[0])
                    }
                }
            }
        }
    }

    /**
     * 刷新当前页面图片显示内容
     * */
    fun refresh() {
        del1_iv.visibility = View.VISIBLE
        del2_iv.visibility = View.VISIBLE
        del3_iv.visibility = View.VISIBLE
        for (i in 0..2) {
            when (i) {
                0 -> setErrorOrImg(src_list, select_img1, i)
                1 -> setErrorOrImg(src_list, select_img2, i)
                2 -> setErrorOrImg(src_list, select_img3, i)
            }
        }
        /**
         * 删除按钮显示隐藏
         * */
        when (img_list.size) {
            0 -> {
                del1_iv.visibility = View.GONE
                del2_iv.visibility = View.GONE
                del3_iv.visibility = View.GONE
                card2_rl.visibility = View.INVISIBLE
                card3_rl.visibility = View.INVISIBLE
            }
            1 -> {
                del2_iv.visibility = View.GONE
                del3_iv.visibility = View.GONE
                card2_rl.visibility = View.VISIBLE
                card3_rl.visibility = View.INVISIBLE
            }
            2 -> {
                del3_iv.visibility = View.GONE
                card2_rl.visibility = View.VISIBLE
                card3_rl.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 设置error图片或者显示选择的图片
     * */
    fun setErrorOrImg(url: MutableList<String>, iv: ImageView, position: Int) {
        if (url.size > position) {
            if (TextUtils.isEmpty(url[position])) {
                iv.setImageDrawable(resources.getDrawable(R.mipmap.sc))
            } else {
                Glide.with(this)
                        .load(url[position]).into(iv)
            }
        } else {
            iv.setImageDrawable(resources.getDrawable(R.mipmap.sc))
        }
    }
}
