package gy.mf.info.control.img_detail

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_img_detail.*
import android.opengl.ETC1.getWidth


class ImgDetailActivity : BaseActivity() {
    var animation: TranslateAnimation? = null
    override fun initViews() {
        setContentView(R.layout.activity_img_detail)
        animation = TranslateAnimation(img_iv.scaleX, 150F, img_iv.scaleY, 70F)
        animation!!.duration = 2000//动画持续时间
        animation!!.repeatCount = 1;//设置重复次数
        animation!!.repeatMode = Animation.REVERSE;//设置反方向执行
    }

    override fun initEvent() {
        start_btn.setOnClickListener {
            img_iv.animation = animation
            img_iv.startAnimation(animation)
            item_size++
            add_view()
        }
        end_btn.setOnClickListener {
            animation!!.cancel()
        }
        add_num_view()
    }

    var num_array = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    var yun_array = arrayOf("+", "-", "=", "×", "÷")
    var item_size = 10//当前
    fun add_num_view() {
        var wai_ll = LinearLayout(this)//外部
        for (i in 0..num_array.size - 1) {
            if ((i + 1) % 5 == 1) {
                wai_ll = LinearLayout(this)//外部
            }
            var view = TextView(this)
            view.gravity = Gravity.CENTER
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                when (i % 3) {
                    0 -> view.background = resources.getDrawable(R.mipmap.lan_bg)
                    1 -> view.background = resources.getDrawable(R.mipmap.lv_bg)
                    2 -> view.background = resources.getDrawable(R.mipmap.fen_bg)
                }
            }
            var width = this.windowManager.defaultDisplay.width / 5//获得每个item的宽度
            var layout = ViewGroup.LayoutParams(width, 100)
            view.layoutParams = layout
            view.text = num_array[i]
            view.textSize = 24F
            view.setTextColor(Color.parseColor("#FFFFFF"))
            view.setOnClickListener {
                toast("你点的是我："+i)
            }
            wai_ll.addView(view)
            if (i % 5 == 0 || i == item_size) {
                ll.addView(wai_ll)
            }
        }
    }

    fun add_view() {
        var wai_ll = LinearLayout(this)//外部
        for (i in 1..item_size) {
            if (i % 7 == 1) {
                wai_ll = LinearLayout(this)//外部
            }
            var view = ImageView(this)
            view.layoutParams = ViewGroup.LayoutParams(100, 100);
            view.setImageResource(R.mipmap.ic_launcher)
            wai_ll.addView(view)
            if (i % 7 == 0 || i == item_size) {
                ll.addView(wai_ll)
            }
        }

    }
}
