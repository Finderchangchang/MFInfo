package gy.mf.info.control.img_detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_img_list2.*
import java.util.*

/**
 * 图片详情列表
 * */
class ImgListActivity : BaseActivity() {
    var img_list = ArrayList<String>()
    var adapter: ImgAdapter? = null
    override fun initViews() {
        setContentView(R.layout.activity_img_list2)
        img_view_pager.adapter=adapter
    }

    override fun initEvent() {

    }

}
