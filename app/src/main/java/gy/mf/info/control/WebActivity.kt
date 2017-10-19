package gy.mf.info.control

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    var title = ""
    var is_web = false
    var content = ""
    override fun initViews() {
        setContentView(R.layout.activity_web)
        title = intent.getStringExtra("title")
        is_web = intent.getBooleanExtra("is_web", false)
        content = intent.getStringExtra("content")
        main_back.setOnClickListener { finish() }
    }

    override fun initEvent() {
        toolbar.text = title
        web.visibility = View.VISIBLE
        val_tv.visibility = View.GONE
        if (!is_web) {
            web.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null)
        } else {
            web.loadUrl(content)
//            web.visibility = View.GONE
//            val_tv.visibility = View.VISIBLE
//            val_tv.text = content
        }
    }
}
