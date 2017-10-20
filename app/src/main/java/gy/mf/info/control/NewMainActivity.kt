package gy.mf.info.control

import android.content.Intent
import android.net.Uri
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.check_img.*
import gy.mf.info.control.img_detail.CheckedImgActivity
import gy.mf.info.control.transfer.ImageDatat
import gy.mf.info.control.transfer.ListPopupWindow
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.method.MainPagerAdapter
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TypeModel
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_new_main.*
import java.util.*

class NewMainActivity : BaseActivity(), ICheckImg {
    override fun show_type_list2(list: List<TotalModelMA.TypeModel.Type>?) {
        var s = ""
        type_lists = list as MutableList<TotalModelMA.TypeModel.Type>
    }

    override fun show_pictures2(list: MutableList<ImageDatat.DataBean.LinkBean>?) {
    }

    override fun show_type_list(list: MutableList<TypeModel.Type>?) {

    }

    override fun add_imgs_result(boolean: Boolean) {

    }

    internal var img_lists: MutableList<PictureModel> = ArrayList<PictureModel>()//购买的课程列表
    internal var type_lists: MutableList<TotalModelMA.TypeModel.Type> = ArrayList()//购买的课程列表

    var firstAdapter: MainPagerAdapter? = null//购买的课程
    var adapter: CommonAdapter<PictureModel>? = null
    var imgs: MutableList<String> = ArrayList<String>()
    var imgs_index: MutableList<Int> = ArrayList<Int>()
    var ccAdapter: HorizontalAdapter? = null
    var zx_adapter: CommonAdapter<String>? = null//资讯
    var viewList = ArrayList<View>()

    override fun show_pictures(list: MutableList<PictureModel>?) {
        if (now_index == 1) {
            img_lists = ArrayList<PictureModel>()
            Log.i("position","------------------")
            iv_viewpager.setCurrentItem(now_position, false)
            main_gv.smoothScrollToPosition(0)
        }
        img_lists.addAll(list as MutableList<PictureModel>)
        model.link = img_lists

        //firstAdapter = MainPagerAdapter(this, img_lists)
        main_gv.getIndex(now_index, page_size, now_index * 11)
        adapter!!.refresh(img_lists)
        firstAdapter!!.refresh(img_lists)
        //viewpager.adapter = firstAdapter
        iv_viewpager.currentItem = now_position

        // iv_viewpager.setCurrentItem(now_position, false)
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
    var type_list: MutableList<TotalModelMA.TypeModel.Type> = ArrayList<TotalModelMA.TypeModel.Type>()
    var position_list: MutableList<Int> = ArrayList<Int>()
    var mAdapter: HorizontalScrollViewAdapter? = null
    var bi_show = false


    override fun initViews() {
        setContentView(R.layout.activity_new_main)
        type = intent.getStringExtra("type")//获得当前
        now_type = type
    }

    override fun initEvent() {

        if (type.toInt() < 4) {
            ran_iv.visibility = View.VISIBLE
        } else {
            jia_iv.visibility = View.INVISIBLE
            bi_iv.visibility = View.INVISIBLE
            shi_iv.visibility = View.INVISIBLE
            bi_tv.visibility = View.INVISIBLE
            //bottom_.visibility = View.INVISIBLE
            ran_iv.visibility = View.GONE
            xiang_iv.visibility = View.GONE
            kuai_iv.visibility = View.INVISIBLE
        }
        bi_iv.setOnClickListener {
            img_add(now_position)
        }
        when (type) {
            "1" -> big_type_iv.background = resources.getDrawable(R.mipmap.nv)
            "2" -> big_type_iv.background = resources.getDrawable(R.mipmap.nan)
            "3" -> big_type_iv.background = resources.getDrawable(R.mipmap.tong)
            "4" -> big_type_iv.background = resources.getDrawable(R.mipmap.zao)
            "5" -> big_type_iv.background = resources.getDrawable(R.mipmap.shang)
        }
        CheckImgListener(this).getImgs(now_type, now_index, level)
        firstAdapter = MainPagerAdapter(this, img_lists)
        iv_viewpager.adapter = firstAdapter
        img_show()
        //                    jia_iv.background = getDrawable(R.mipmap.cang)

        ccAdapter = HorizontalAdapter(imgs, this)

        val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        msr.layoutManager = firstManager
        msr.adapter = ccAdapter
        ccAdapter!!.setClick { position ->
            //                            noraml = false
//                        img_show()
//                        iv_viewpager.currentItem = imgs_index[position]
//                            startActivity(Intent(this, OneImgDetailActivity::class.java).putExtra("model", model))
//                        startActivity(Intent(this, ImgDetailsActivity::class.java)
//                                .putExtra("position", position)
//                                .putExtra("have",true)
//                                .putExtra("model", img_lists!![position]))
            var models = TypeModel()
            var ll: ArrayList<PictureModel>? = ArrayList()//图片的url
            for (mo in imgs_index) {
                ll!!.add(img_lists!![mo])
            }
            models.link = ll
            startActivity(Intent(this@NewMainActivity, ImgListActivity::class.java)
                    .putExtra("model", models)
                    .putExtra("load_gg", false))
        }
        setHH(fan_iv, 60)
        setHH(bi_iv, 60)
        setHH(next_iv, 80)

        setHH(jia_iv, 60)
        setHH(shi_iv, 60)
        setHH(big_type_iv, 100)
        setHH(ran_iv, 90)
        setHH(xiang_iv, 60)
        next_iv.setOnClickListener {
            now_position++
            if (now_position == img_lists.size) {
                now_index++
                CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
            } else {
                iv_viewpager.setCurrentItem(now_position, false)
            }
        }
        iv_viewpager.offscreenPageLimit = 1
        iv_viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.i("now_position", position.toString() + ":" + now_position)
                if (noraml && position < now_position) {
                    iv_viewpager.currentItem = now_position
                }
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
                if (noraml && img_lists!!.size > now_position) {
                    for (i in position_list) {
                        if (position == i) {
                            bi_iv.background = getDrawable(R.mipmap.bi_s)
                        } else {
                            bi_iv.background = getDrawable(R.mipmap.bi)
                        }
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
        ran_iv.setOnClickListener {
            //getOption1()
            if (img_lists!!.size > 0) {
                var builder = AlertDialog.Builder(this);
                var array = arrayOf("图示教程", "视频教程", "发型设计")
                builder.setItems(array) { a, b ->
                    var title = array[b]
                    var is_web = false
                    var imgs = img_lists!![now_position]
                    var content = ""
                    when (b) {
                        0 -> content = imgs.picture_tsjc
                        1 -> content = imgs.picture_videoUrl
                        2 -> content = imgs.picture_fxsj
                    }
                    if (b == 1) {
                        is_web = true
                    }
                    startActivity(Intent(this@NewMainActivity, WebActivity::class.java)
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
        adapter = object : CommonAdapter<PictureModel>(this, img_lists, R.layout.activity_index_gallery_item) {
            override fun convert(holder: CommonViewHolder, model: PictureModel, position: Int) {
                var url = model.picture_name
                if (url.contains(",")) {
                    url = url.split(",")[0]
                }
                holder.setGImage(R.id.id_index_gallery_item_image, url, height)
            }
        }
        ccAdapter = HorizontalAdapter(imgs, this)
        bi_tv.setOnClickListener {
            bi_show = !bi_show
            if (bi_show) {
                val params = bottom_.layoutParams
                params.height = dip2px(75F)
                bottom_.layoutParams = params
            } else {
                val params = bottom_.layoutParams
                params.height = 0
                bottom_.layoutParams = params
            }
        }
        jia_iv.setOnClickListener {
            if (img_lists != null && img_lists!!.size > 0) {
                var sc_result = true
                if (sc_result) {//收藏
                    var model = img_lists!![now_position]
                    model.is_collection = 1
                    jia_iv.background = getDrawable(R.mipmap.cang)
                    CheckImgListener(this).addImgs(img_lists!![now_position].picture_id, "add")
                } else {//取消收藏
                    CheckImgListener(this).addImgs(img_lists!![now_position].picture_id, "del")
                }
            }
        }
        xiang_iv.setOnClickListener {
            if (img_lists != null && img_lists!!.size > 0) {
                startActivity(Intent(this, ImgDetailsActivity::class.java)
                        .putExtra("position", now_position)
                        .putExtra("model", img_lists!![now_position]))
            }
        }
        big_type_iv.setOnClickListener {
            skip()
        }
        main_gv.adapter = adapter
        main_gv.setOnItemClickListener { parent, view, position, id ->
            if (type.toInt() < 4) {
                img_show()
                now_position=position
                iv_viewpager.setCurrentItem(position, false)
            } else {
                startActivity(Intent(this, ImgDetailsActivity::class.java)
                        .putExtra("position", position)
                        .putExtra("model", img_lists!![position])
                        .putExtra("isx4", true))
            }
        }
        CheckImgListener(this).getTypes(type)//获得当前分类
        if (noraml) {
            now_index = 1
            CheckImgListener(this).getImgs(now_type, now_index, level)
        } else {
            now_index = 1
            CheckImgListener(this).getImgs(now_type, now_index, level)
        }
        clear_tv.setOnLongClickListener({
            img_add(-1)
            false
        })
        main_gv.setInterface {
            now_index++
            CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
        }
        set_iv.setOnClickListener {
            showListAlertDialog()
        }
        kuai_iv.setOnClickListener { img_show() }
        fan_iv.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            builder.setMessage("确定要退出当前页面吗？")
            builder.setNegativeButton("取消", null)
            builder.setPositiveButton("确定") { a, b ->
                finish()
            }
            builder.setOnDismissListener {
                hideSystemNavigationBar()
            }
            builder.show()
        }
        zx_adapter = object : CommonAdapter<String>(this, imgs, R.layout.activity_index_gallery_item) {
            override fun convert(holder: CommonViewHolder, model: String, position: Int) {
                holder.setGImage(R.id.id_index_gallery_item_image, urls().upload_picture + model)
            }
        }
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要退出当前页面吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定") { a, b ->
            finish()
        }
        builder.setOnDismissListener {
            hideSystemNavigationBar()
        }
        builder.show();
    }

    fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
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

    fun setHH(iv: TextView, height: Int) {
        var heights = this.windowManager.defaultDisplay.height
        var params = iv.layoutParams;
        //获取当前控件的布局对象
        var s = (heights.toDouble() / 1280 * height).toInt()
        params.height = s
        params.width = s
        iv.layoutParams = params;//将设置好的布局参数应用到控件中
    }

    fun skip() {
        var model: TotalModelMA.TypeModel = TotalModelMA.TypeModel()
        model.all_content = type_lists
        for (key in model.all_content) {
            var now_pp = type.toInt() - 1
            if (key.class_sex == type.toString()) {
                if (key.data_2.size > 0) {
                    startActivityForResult(Intent(this@NewMainActivity, CheckDialogActivity::class.java)
                            .putExtra("type", type)
                            .putExtra("model", model), 1)
                } else {
                    toast("当前无可查询分类")
                }
            }
        }
    }

    fun img_add(position: Int) {
        if (img_lists != null && img_lists!!.size > 0) {
            if (position >= 0) {//添加
                if (!position_list!!.contains(position)) {
                    position_list!!.add(position)
                    imgs!!.add(img_lists!![position].picture_name)
                    imgs_index.add(position)
                    mAdapter = HorizontalScrollViewAdapter(this, imgs)
                    //bottom_hsv.initDatas(mAdapter)
                    zx_adapter!!.refresh(imgs)
                    //bottom_gv.numColumns=imgs.size
                    ccAdapter = HorizontalAdapter(imgs, this)
                    val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    msr.layoutManager = firstManager

                    msr.adapter = ccAdapter
                    //对比管理
                    ccAdapter!!.setClick { position ->
                        //                            noraml = false
//                        img_show()
//                        iv_viewpager.currentItem = imgs_index[position]
//                            startActivity(Intent(this, OneImgDetailActivity::class.java).putExtra("model", model))
//                        startActivity(Intent(this, ImgDetailsActivity::class.java)
//                                .putExtra("position", position)
//                                .putExtra("have",true)
//                                .putExtra("model", img_lists!![position]))
                        var model = TypeModel()
                        var ll: ArrayList<PictureModel>? = ArrayList()//图片的url
                        for (mo in imgs_index) {
                            ll!!.add(img_lists!![mo])
                        }
                        model.link = ll
                        startActivity(Intent(this@NewMainActivity, ImgListActivity::class.java)
                                .putExtra("model", model)
                                .putExtra("load_gg", false))
                    }
                }
            } else {
                //清空
                imgs.clear()
                imgs_index.clear()
                position_list.clear()
                mAdapter = HorizontalScrollViewAdapter(this, imgs)
                //bottom_hsv.initDatas(mAdapter)
                val params = bottom_.layoutParams
                params.height = 0
                bottom_.layoutParams = params
                ccAdapter = HorizontalAdapter(imgs, this)
                //refresh()//刷新比的内容
                val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                msr.layoutManager = firstManager
                msr.adapter = ccAdapter
            }
        }
    }

    fun refresh() {
        val views = arrayOfNulls<View>(img_lists!!.size)

        for (k in 0..img_lists!!.size - 1) {
            var view = View.inflate(this, R.layout.activity_index_gallery_item, null)
            var cc = view.findViewById(R.id.id_index_gallery_item_image) as ImageView
            cc.scaleType = ImageView.ScaleType.FIT_XY
            Glide.with(this)
                    .load(urls().upload_picture + img_lists!![k].picture_name)
                    .error(R.mipmap.defult_user)
                    .into(cc)
            var now_click = 0
            cc.setOnClickListener {
                now_click++
                if (now_click == 2) {
                    startActivity(Intent(this, OneImgDetailActivity::class.java).putExtra("model", img_lists!![k]))
                    now_click = 0
                }
            }
            views[k] = view
            viewList.add(views[k]!!)
        }

        adapter!!.refresh(img_lists)
        firstAdapter = MainPagerAdapter(this, img_lists)
        iv_viewpager.adapter = firstAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 11) {
            now_type = data!!.getStringExtra("result")
            if (!TextUtils.isEmpty(now_type)) {
                if (type.toInt() < 4) {
                    level = "3"
                    ran_iv.visibility = View.VISIBLE
                } else {
                    level = "3"
                    ran_iv.visibility = View.GONE
                }

                now_index = 1
                if (now_type.substring(now_type.length - 1).equals(",")) {
                    now_type = now_type.substring(0, now_type.length - 1)
                }
                if (noraml) {
                    now_index = 1
                    CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
                } else {
                    now_index = 1
                    CheckImgListener(this@NewMainActivity).getImgs(now_type, now_index, level)
                }
            }
        }
    }

    var model = TypeModel()
    fun showListAlertDialog() {
        val arrayList = ArrayList<String>()
        arrayList.add("播放幻灯片")
        arrayList.add("浏览本地图片")
        arrayList.add("浏览收藏")
        arrayList.add("  ")
        val mListPopupWindow = ListPopupWindow(this, arrayList)
        if (imgs.size > 0) {
            var height = this.windowManager.defaultDisplay.height / 1280 * 90
            mListPopupWindow.showAtLocation(height)
        } else {
            mListPopupWindow.showAtLocation(0)
        }
        mListPopupWindow.mPopupWindow!!.setOnDismissListener({
            hideSystemNavigationBar()
        })
        mListPopupWindow.setOnItemClickListener { parent, view, position, id ->
            mListPopupWindow.dismiss()
            when (position) {
                0 -> {//播放幻灯片
                    if (img_lists!!.size > 0) {
                        val intent = Intent()
                        intent.setClass(this, ImgListActivity::class.java)
//                        var model = TypeModel()
//                        model.link = img_lists
                        intent.putExtra("model", model)
                        intent.putExtra("time", 3000)
                        intent.putExtra("load_gg", true)

                        startActivity(intent)
                    } else {
                        toast("当前无数据")
                    }
                }
                1 -> {
                    var url = "com.alensw.PicFolder"
                    try {
                        val packageManager = packageManager
                        var intent = packageManager.getLaunchIntentForPackage(url)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        val viewIntent = Intent("android.intent.action.VIEW", Uri.parse("http://baidu.com/"))
                        startActivity(viewIntent)
                    }
                }
                2 -> {//跳转到浏览收藏
                    startActivity(Intent(this@NewMainActivity, CheckedImgActivity::class.java))

                }
            }
        }
    }
}
