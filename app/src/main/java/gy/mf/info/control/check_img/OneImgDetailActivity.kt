package gy.mf.info.control.check_img

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.model.PictureModel
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_one_img_detail.*

/**
 * 一个图片的详情
 * */
class OneImgDetailActivity : BaseActivity() {
    var model: PictureModel? = null
    override fun initViews() {
        setContentView(R.layout.activity_one_img_detail)
        model = intent.getSerializableExtra("model") as PictureModel
    }

    override fun initEvent() {
        main_back.setOnClickListener { finish() }
        Glide.with(this)
                .load(urls().upload_picture + model!!.picture_name)
                .error(R.mipmap.defult_user)
                .into(iv)
        if (!TextUtils.isEmpty(model!!.picture_url)) {
            val_tv.text = model!!.picture_url
        } else {
            val_tv.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
