package gy.mf.info.control

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_img.*

class ImgActivity : BaseActivity() {
    override fun initViews() {
        setContentView(R.layout.activity_img)
    }

    override fun initEvent() {
        back_iv.setOnClickListener { finish() }
        var img = intent.getStringExtra("bit")
        var bitmap = BitmapFactory.decodeFile(img)
        iv.setImageBitmap(bitmap)
    }
}
