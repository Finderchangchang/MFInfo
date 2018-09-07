package gy.mf.info.control.check_img

import android.app.Dialog
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import gy.mf.info.R
import gy.mf.info.base.App
import gy.mf.info.base.BaseActivity
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_check_img.*
import okhttp3.Call
import okhttp3.Response
import java.util.*
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.*
import gy.mf.info.control.*
import gy.mf.info.control.img_detail.CheckedImgActivity
import gy.mf.info.control.transfer.*
import gy.mf.info.control.transfer.ListPopupWindow
import gy.mf.info.method.*
import gy.mf.info.model.*


/**
 * 图像比对收藏页面
 * */
class CheckImgActivity : BaseActivity(), ICheckImg {


    var girlDetails: GirlDetails? = null
    var img_lists: ArrayList<PictureModel>? = ArrayList()//图片的url
    
    var viewList = ArrayList<View>()
    var firstAdapter: MainPagerAdapter? = null
    var adapter: CommonAdapter<PictureModel>? = null
    var now_position = 0//当前选中的位置
    var now_index = 1//当前页码
    var now_type = ""
    var noraml = false//true:单张图false:多图
    //获得图片列表
    var dialog: Dialog? = null


    override fun show_pictures2(list: MutableList<ImageDatat.DataBean.LinkBean>?) {
        girlDetails?.show_pictures2(ArrayList(list))
    }

    fun skip(model: TotalModelMA.TypeModel) {
        for (key in model.all_content) {
            var now_pp = type.toInt() - 1
            if (key.class_sex == type.toString()) {
                if (key.data_2.size > 0) {
                    startActivityForResult(Intent(this@CheckImgActivity, CheckDialogActivity::class.java)
                            .putExtra("type", type)
                            .putExtra("model", model), 1)
                } else {
                    toast("当前无可查询分类")
                }
            }
        }

    }

    var page2 = 0;
    fun requestPhoto2(class_id: String?, amount: Int, page: Int) {
        var amount2 = 5;
        page2++
        page2 = 1
        amount2 = 1
        OkGo.post(urls().user_picture)
                .params("id", class_id)
                .params("amount", amount2)
                .params("page", page2)
                .params("token", App.token)
                .execute(object : StringCallback() {
                    override fun onSuccess(s: String, call: Call, response: Response) {
                        // s 即为所需要的结果
//                        val typeModelTotalModelMA = Gson().fromJson(s, TotalModelMA::class.java)
                        if (StringUtils.noEmpty(s)) {
                            val imageDatat = Gson().fromJson(s, ImageDatat::class.java)
                            if (imageDatat !== null && imageDatat.data !== null && imageDatat.data.link !== null) {
                                val link = imageDatat.data.link
                                //    do_some.show_type_list2(typeModelTotalModelMA.data.getAll_content())
                                //                                show_pictures(model.data!!.link)
                                show_pictures2(link)
                            }
                        }

                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                    }
                })
    }

    fun rollback2(class_id: String?, amount: Int, page: Int) {
        var amount2 = 5;
        page2--
        if (page2 < 0 || page2 == 0) {
            return
        }
        OkGo.post(urls().user_picture)
                .params("id", class_id)
                .params("amount", amount2)
                .params("page", page2)
                .params("token", App.token)
                .execute(object : StringCallback() {
                    override fun onSuccess(s: String, call: Call, response: Response) {
                        // s 即为所需要的结果
                        val typeModelTotalModelMA = Gson().fromJson(s, TotalModelMA::class.java)
                        val imageDatat = Gson().fromJson(s, ImageDatat::class.java)
                        val link = imageDatat.data.link
                        //    do_some.show_type_list2(typeModelTotalModelMA.data.getAll_content())
                        //                                show_pictures(model.data!!.link)
                        show_pictures2(link)
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                    }
                })
    }

    //收藏，取消收藏结果
    override fun add_imgs_result(boolean: Boolean) {

    }

    //获得当前分类
    override fun show_type_list(list: MutableList<TypeModel.Type>?) {

    }

    //获得当前分类
    override fun show_type_list2(list: List<TotalModelMA.TypeModel.Type>?) {
        if (list != null) {
            //type_list = ArrayList(list)//获得所有样式
            var s = ""
        }
    }

    var type_list: MutableList<TotalModelMA.TypeModel.Type> = ArrayList<TotalModelMA.TypeModel.Type>()
    var type = ""//一级分类
    var type1 = ""//二级分类
    var level = "1"//当前选择的登记
    fun setHH(iv: TextView, height: Int) {
        var heights = this.windowManager.defaultDisplay.height
        var params = iv.layoutParams;
        //获取当前控件的布局对象
        var s = (heights.toDouble() / 1280 * height).toInt()
        params.height = s
        params.width = s
        iv.layoutParams = params;//将设置好的布局参数应用到控件中
    }

    var gv_click = 0
    override fun initViews() {
        setContentView(R.layout.activity_check_img)
        //handleEvent()
        //getOptionData()
        var imageView = ImageView(this);
        imageView.setImageResource(R.mipmap.icon_cv_loading)
        var circle_anim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        var interpolator = LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            imageView.startAnimation(circle_anim);  //开始动画
        }
        dialog = Dialog(this, R.style.CustomProgressDialog)
        dialog!!.setContentView(imageView)
        dialog!!.setCancelable(false)
        dialog!!.setOnDismissListener {
            hideSystemNavigationBar()
        }
        setHH(fan_iv, 60)
        setHH(bi_iv, 60)
        setHH(jia_iv, 60)
        setHH(shi_iv, 60)
        setHH(big_type_iv, 100)
        setHH(ran_iv, 90)
        setHH(xiang_iv, 60)
        xiang_iv.setOnClickListener {
            if (img_lists != null && img_lists!!.size > 0) {
                startActivity(Intent(this, ImgDetailsActivity::class.java)
                        .putExtra("position", now_position)
                        .putExtra("model", img_lists!![now_position]))
            }
        }
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
                    startActivity(Intent(this@CheckImgActivity, WebActivity::class.java)
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
        huan_iv.setOnClickListener{
            startActivity(Intent(this@CheckImgActivity,MoveHairActivity::class.java))
        }
        big_type_iv.setOnClickListener {
            var type_model = TotalModelMA.TypeModel()
            if (type_list.size > 0) {
                type_model.all_content = type_list
                skip(type_model)
            }
        }
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

        kuai_iv.setOnClickListener { img_show() }
        main_gv.adapter = adapter
        main_gv.setOnItemClickListener { parent, view, position, id ->
            if (type.toInt() < 4) {
                img_show()
                iv_viewpager.setCurrentItem(position, false)
            } else {
                startActivity(Intent(this, ImgDetailsActivity::class.java)
                        .putExtra("position", position)
                        .putExtra("model", img_lists!![position])
                        .putExtra("isx4", true))
            }
            //startActivity(Intent(this, OneImgDetailActivity::class.java).putExtra("model", img_lists!![position]))
//            startActivity(Intent(this, ImgDetailsActivity::class.java)
//                    .putExtra("position", position)
            //.putExtra("model", ImgsModel(img_lists!!)))
        }
        main_gv.setInterface {
            now_index++
            CheckImgListener(this@CheckImgActivity).getImgs(now_type, now_index, level)
        }
    }


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
                    CheckImgListener(this@CheckImgActivity).getImgs(now_type, now_index, level)
                } else {
                    now_index = 1
                    CheckImgListener(this@CheckImgActivity).getImgs(now_type, now_index, level)
                }
            }
        }
        if (requestCode == 7) {
            if (data != null) {
                if (Build.VERSION.SDK_INT >= 19) {
                    //4.4系统一上用该方法解析返回图片
                    handleImageOnKitKat(data!!);
                } else {
                    //4.4一下用该方法解析图片的获取
                    handleImageBeforeKitKat(data!!);
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
            /**
     * api 19以后
     *  4.4版本后 调用系统相机返回的不在是真实的uri 而是经过封装过后的uri，
     * 所以要对其记性数据解析，然后在调用displayImage方法尽心显示
     * @param data
     */

    fun handleImageOnKitKat(data: Intent) {
        var imagePath = "";
        var uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri 则通过id进行解析处理
            var docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式id
                var id = docId.split(":")[1];
                var selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                var contentUri = ContentUris.withAppendedId(Uri.parse("" +
                        "content://downloads/public_downloads"), java.lang.Long.valueOf(docId));
                imagePath = getImagePath(contentUri, "");
            }
        } else if ("content".equals(uri.getScheme())) {
            //如果不是document类型的uri，则使用普通的方式处理
            imagePath = getImagePath(uri, "");
        }
        displayImage(imagePath);
    }

    /**
     * 4.4版本一下 直接获取uri进行图片处理
     * @param data
     */
    fun handleImageBeforeKitKat(data: Intent) {
        var uri = data.getData();
        var imagePath = getImagePath(uri, "");
        displayImage(imagePath);
    }

    /**
     * 通过 uri seletion选择来获取图片的真实uri
     * @param uri
     * @param seletion
     * @return
     */
    fun getImagePath(uri: Uri, seletion: String): String {
        var path = "";
        var cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 通过imagepath来绘制immageview图像
     * @param imagPath
     */
    fun displayImage(imagPath: String) {
        if (imagPath != null) {

            startActivity(Intent(this@CheckImgActivity, ImgActivity::class.java).putExtra("bit", imagPath))
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }
    var page_size=5
    var mAdapter: HorizontalScrollViewAdapter? = null
    override fun show_pictures(list: MutableList<PictureModel>?) {

        if (now_index == 1) {
            img_lists = ArrayList()
            viewList = ArrayList()
        }
        //now_index
        //Log.i("now_position------", "list--------" + list!!.size.toString())
        if (list!!.size > 0) {
            //多图
            img_lists!!.addAll(list)
            main_gv.getIndex(now_index, page_size, now_index * 11)
            adapter!!.refresh(img_lists)
            //---------单图-------

            model.link = img_lists
            type_model = model
//            if (now_index == 1) {
//                img_lists!!.addAll(list)
//                main_gv.getIndex(now_index, 10, now_index * 11)
//                adapter!!.refresh(img_lists)
//            }
            val views = arrayOfNulls<View>(img_lists!!.size)
            viewList = ArrayList<View>()
            for (k in 0..img_lists!!.size - 1) {
                var view = View.inflate(this, R.layout.activity_index_gallery_item, null)
                var cc = view.findViewById(R.id.id_index_gallery_item_image) as ImageView
                cc.scaleType = ImageView.ScaleType.FIT_XY
                var url = img_lists!![k].picture_name
                if (url.contains(",")) {
                    url = url.split(",")[0]
                }
                Glide.with(this)
                        .load(urls().upload_picture + url)
                        .error(R.mipmap.defult_user)
                        .into(cc)
                var index = 0
                cc.setOnClickListener {
                    if (index == 1) {

                        index = 0
                    } else {
                        index++
                    }
                }
                views[k] = view
                viewList.add(views[k]!!)
            }
            firstAdapter = MainPagerAdapter(this,img_lists)
            iv_viewpager.adapter = firstAdapter
            if (now_index > 1) {
                iv_viewpager.setCurrentItem(page_size * (now_index - 1) - 1, false)
            }
        } else {
            //单图
            if (noraml && now_index == 1) {
                viewList = ArrayList()
                firstAdapter = MainPagerAdapter(this,img_lists)
                iv_viewpager.adapter = firstAdapter
            }
            //多图
            if (!noraml && now_index == 1) {
                img_lists = ArrayList()
                adapter!!.refresh(img_lists)
            }
            //now_index--
        }
        canJumpPage = true
        //Handler().postDelayed({
            //dialog!!.dismiss()
        //}, 2000);

    }

    private var isLastPage = false
    private var isDragPage = false
    private var canJumpPage = true
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun initEvent() {
        //handleEvent()
        type = intent.getStringExtra("type")//获得当前
        now_type = type
        img_show()
        if (type.toInt() < 4) {
            ran_iv.visibility = View.VISIBLE
        } else {
            jia_iv.visibility = View.INVISIBLE
            bi_iv.visibility = View.INVISIBLE
            shi_iv.visibility = View.INVISIBLE
            bi_tv.visibility = View.INVISIBLE
            bottom_.visibility = View.INVISIBLE
            ran_iv.visibility = View.GONE
            xiang_iv.visibility = View.GONE
            kuai_iv.visibility = View.INVISIBLE
        }
        when (now_type) {
            "1" -> big_type_iv.background = resources.getDrawable(R.mipmap.nv)
            "2" -> big_type_iv.background = resources.getDrawable(R.mipmap.nan)
            "3" -> big_type_iv.background = resources.getDrawable(R.mipmap.tong)
            "4" -> big_type_iv.background = resources.getDrawable(R.mipmap.zao)
            "5" -> big_type_iv.background = resources.getDrawable(R.mipmap.shang)
        }
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
                        CheckImgListener(this@CheckImgActivity).getImgs(now_type, now_index, level)
                    }
                }
                //if()
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
                isLastPage = position == img_lists!!.size - 1;
            }

            override fun onPageScrollStateChanged(state: Int) {
                isDragPage = state == 1;
            }

        })
//        iv_viewpager.adapter = ViewAdapter(null, this)
        jia_iv.setOnClickListener {
            if (img_lists != null && img_lists!!.size > 0) {
                var sc_result = true
                if (sc_result) {//收藏
                    var model = img_lists!![now_position]
                    model.collection = 1
                    jia_iv.background = getDrawable(R.mipmap.cang)
                    CheckImgListener(this).addImgs(img_lists!![now_position].picture_id, "add")
                } else {//取消收藏
                    CheckImgListener(this).addImgs(img_lists!![now_position].picture_id, "del")
                }
            }
        }
        bi_iv.setOnClickListener {
            img_add(now_position)
        }
        set_iv.setOnClickListener {
            showListAlertDialog()
        }
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
        CheckImgListener(this).getTypes(now_type)//获得当前分类
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

        zx_adapter = object : CommonAdapter<String>(this, imgs, R.layout.activity_index_gallery_item) {
            override fun convert(holder: CommonViewHolder, model: String, position: Int) {
                holder.setGImage(R.id.id_index_gallery_item_image, urls().upload_picture + model)
            }
        }
        //bottom_gv.adapter=zx_adapter
        ccAdapter = HorizontalAdapter(imgs, this)
        val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        msr.layoutManager = firstManager
        msr.adapter = ccAdapter
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

    var bi_show = false
    var ccAdapter: HorizontalAdapter? = null
    var zx_adapter: CommonAdapter<String>? = null//资讯
    var imgs: MutableList<String> = ArrayList<String>()
    var imgs_index: MutableList<Int> = ArrayList<Int>()
    var position_list: MutableList<Int> = ArrayList<Int>()

    companion object {
        var model = TypeModel()
    }

    /**
     *@param position 正添加，负清空
     * */
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
                    var ccAdapter = HorizontalAdapter(imgs, this)
                    val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    msr.layoutManager = firstManager
                    msr.adapter = ccAdapter
                    //对比管理
                    ccAdapter.setClick { position ->
                        //                        noraml = false
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
                        startActivity(Intent(this@CheckImgActivity, ImgListActivity::class.java)
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
                refresh()//刷新比的内容
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
        firstAdapter = MainPagerAdapter(this,img_lists)
        iv_viewpager.adapter = firstAdapter
    }

    fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

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
                    startActivity(Intent(this@CheckImgActivity, CheckedImgActivity::class.java))

                }
            }
        }
    }
}
