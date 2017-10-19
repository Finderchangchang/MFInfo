package gy.mf.info.control

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback

import gy.mf.info.R
import gy.mf.info.base.App
import gy.mf.info.base.BaseActivity
import gy.mf.info.method.SamplePagerAdapter
import gy.mf.info.model.ImgsModel
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TotalModel
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_img_details.*
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback
import java.util.*

class ImgDetailsActivity : BaseActivity() {
    override fun initEvent() {

    }

    internal var images: MutableList<String> = ArrayList()
    var model: PictureModel? = null
    var position = 0
    var isx4 = false
    fun setHH(iv: TextView, height: Int) {
        var heights = this.windowManager.defaultDisplay.height
        var params = iv.layoutParams;
        //获取当前控件的布局对象
        var s = (heights.toDouble() / 1280 * height).toInt()
        params.height = s
        params.width = s
        iv.layoutParams = params;//将设置好的布局参数应用到控件中
    }

    var mm: ImgsModel? = null
    override fun initViews() {
        setContentView(R.layout.activity_img_details)
        main = this
        isx4 = intent.getBooleanExtra("isx4", false)
        if (isx4) {
            ran_iv.setBackgroundResource(R.mipmap.sp)
        }
        if (intent.getBooleanExtra("is_show", false)) {
            check_img_above.visibility = View.GONE
            ran_iv.visibility = View.GONE
        }
        var show_xx = intent.getBooleanExtra("show_xx", false)

        var show_position = intent.getIntExtra("position", 0)


        shi_iv.setOnClickListener {
            var mode = PictureModel()
            mode.picture_name = images[position] + ","
            startActivity(Intent(this, ImgDetailsActivity::class.java)
                    .putExtra("position", position)
                    .putExtra("model", mm!!.urls[position])
                    .putExtra("show_xx", false))
        }
        back_iv.setOnClickListener { finish() }
//        ImgsModel
        if (show_xx) {
            mm = intent.getSerializableExtra("model") as ImgsModel
            shi_iv.visibility = View.VISIBLE
            for (model in mm!!.urls) {
                images.add(model.picture_name)
            }
            main_list = mm!!.urls
            viewpager.adapter = SamplePagerAdapter(this@ImgDetailsActivity, images)
            viewpager.currentItem = show_position
        } else {//详情页
            model = intent.getSerializableExtra("model") as PictureModel
            shi_iv.visibility = View.GONE
            if (intent.getBooleanExtra("is_show", false)) {
                images = model!!.picture_name.split(",") as MutableList<String>
                if (images.size > 0) {
                    if (TextUtils.isEmpty(images[images.size - 1])) {
                        images.removeAt(images.size - 1)
                    }
                    if (images.size == 1) {
                        shi_iv.visibility = View.GONE
                    } else {
                        shi_iv.visibility = View.VISIBLE
                    }
                }
                viewpager.adapter = SamplePagerAdapter(this@ImgDetailsActivity, images)
                viewpager.currentItem = show_position
            } else {
                getDetails(model!!.tag_id)
            }
        }
        fan_iv.setOnClickListener { finish() }
        setHH(ran_iv, 90)
        setHH(fan_iv, 60)
        setHH(shi_iv, 60)

        ran_iv.setOnClickListener {
            if (isx4) {
                startActivity(Intent(this@ImgDetailsActivity, WebActivity::class.java)
                        .putExtra("title", "视频教程")
                        .putExtra("is_web", true)
                        .putExtra("content", main_list!![position].picture_videoUrl))
            } else {
                var builder = AlertDialog.Builder(this);
                var array = arrayOf("图示教程", "视频教程", "发型设计")
                builder.setItems(array) { a, b ->
                    var title = array[b]
                    var is_web = false
                    var content = ""
                    when (b) {
                        0 -> content = main_list!![position].picture_tsjc
                        2 -> content = main_list!![position].picture_fxsj
                        1 -> content = main_list!![position].picture_videoUrl
                    }
                    if (b == 1) {
                        is_web = true
                    }
                    startActivity(Intent(this@ImgDetailsActivity, WebActivity::class.java)
                            .putExtra("title", title)
                            .putExtra("is_web", is_web)
                            .putExtra("content", content)
                    )
                }
                builder.setOnDismissListener {
                    hideSystemNavigationBar()
                }
                builder.show()
            }
        }
        viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(p: Int, positionOffset: Float, positionOffsetPixels: Int) {
                position = p
            }
        })
    }

    var main_list: MutableList<PictureModel>? = ArrayList()
    fun getDetails(id: String) {
        OkGo.post(urls().total + "dec_pic.php")
                .params("tag_id", id)
                .params("token", App.token)
                .execute(object : JsonCallback<TotalModel<MutableList<PictureModel>>>() {
                    override fun onSuccess(list: TotalModel<MutableList<PictureModel>>, call: okhttp3.Call?, response: okhttp3.Response?) {
                        main_list = list.data!!
                        for (model in list.data!!) {
                            images.add(model.picture_name)
                        }
                        viewpager.adapter = SamplePagerAdapter(this@ImgDetailsActivity, images)
                        viewpager.currentItem = position
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        //do_some.no_intent()
                    }
                })
    }

    companion object {
        var main: ImgDetailsActivity? = null
    }
}
