package gy.mf.info.control

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.check_img.CheckImgListener
import gy.mf.info.control.check_img.ICheckImg
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.method.MainPagerAdapter
import gy.mf.info.model.ImgsModel
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TypeModel
import kotlinx.android.synthetic.main.activity_check_img.*
import java.util.*

class NewMainActivity : BaseActivity(), ICheckImg {
    override fun show_type_list(list: MutableList<TypeModel.Type>?) {

    }

    override fun add_imgs_result(boolean: Boolean) {

    }

    internal var img_lists: MutableList<PictureModel> = ArrayList()//购买的课程列表
    var firstAdapter: MainPagerAdapter? = null//购买的课程
    var adapter: CommonAdapter<PictureModel>? = null

    override fun show_pictures(list: MutableList<PictureModel>?) {
        if (now_index == 1) {
            img_lists = ArrayList<PictureModel>()
        }
        img_lists.addAll(list as MutableList<PictureModel>)
        //firstAdapter = MainPagerAdapter(this, img_lists)
        main_gv.getIndex(now_index, page_size, now_index * 11)
        adapter!!.refresh(img_lists)
        firstAdapter!!.refresh(img_lists)
        //viewpager.adapter = firstAdapter
        iv_viewpager.currentItem = now_position
        canJumpPage = true;
    }

    var now_index = 1
    var now_position = 0
    private var isLastPage = false
    private var isDragPage = false
    private var canJumpPage = true
    var now_type = "1"
    var level = "1"//当前选择的登记
    var page_size = 10
    override fun initViews() {
        setContentView(R.layout.activity_check_img)
    }

    override fun initEvent() {
        val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        msr.layoutManager = firstManager
        CheckImgListener(this).getImgs("1", now_index, "1")
        firstAdapter = MainPagerAdapter(this, img_lists)
        iv_viewpager.adapter = firstAdapter

        iv_viewpager.offscreenPageLimit = 1
        iv_viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                now_position = position
                if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage) {
                        canJumpPage = false;
                        now_index++
                        Log.i("now_position", now_index.toString() + ":" + now_position)
                        //dialog!!.show()
                        CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                Log.i("now_position", position.toString())
                isLastPage = position == img_lists!!.size - 1;
            }

            override fun onPageScrollStateChanged(state: Int) {
                isDragPage = state == 1;
            }
        })
        val height = windowManager.defaultDisplay.height / 3

        adapter = object : CommonAdapter<PictureModel>(this, img_lists, R.layout.activity_index_gallery_item) {
            override fun convert(holder: CommonViewHolder, model: PictureModel, position: Int) {
                var url = model.picture_name
                if (url.contains(",")) {
                    url = url.split(",")[0]
                }
                holder.setGImage(R.id.id_index_gallery_item_image, url, height)
            }
        }
        main_gv.adapter = adapter
        main_gv.setInterface {
            now_index++
            CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
        }
        kuai_iv.setOnClickListener { img_show() }

    }

    var type = "1"//一级分类
    var noraml = false//true:单张图false:多图

    fun img_show() {
        if (type.toInt() < 4) {
            noraml = !noraml
        } else {//造型或者时尚默认多图
            noraml = false
        }
        if (noraml) {//单图模式
            main_gv.visibility = View.GONE
            iv_viewpager.visibility = View.VISIBLE
        } else {//多图模式
            main_gv.visibility = View.VISIBLE
            iv_viewpager.visibility = View.GONE
            main_gv.smoothScrollToPosition(now_position)
        }
    }
}
