package gy.mf.info.control.main

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View

import gy.mf.info.R
import gy.mf.info.base.App
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.KeepLiveServices
import gy.mf.info.control.MyReceiver
import gy.mf.info.control.check_img.CheckImgActivity
import gy.mf.info.control.check_img.CheckImgListener
import gy.mf.info.control.check_img.ICheckImg
import gy.mf.info.control.check_img.OneImgDetailActivity
import gy.mf.info.control.login.LoginActivity
import gy.mf.info.control.tribune.TribuneActivity
import gy.mf.info.control.user_center.UserCenterActivity
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TypeModel
import gy.mf.info.util.FloatView
import gy.mf.info.util.SettingsCompat
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import gy.mf.info.control.NewMainActivity
import gy.mf.info.control.transfer.ImageDatat
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.Img
import java.util.*


class MainActivity : BaseActivity(), ICheckImg {
    override fun show_type_list2(list: List<TotalModelMA.TypeModel.Type>?) {
        if (list != null) {
            //type_list = ArrayList(list)//获得所有样式
            var s = ""
        }
    }

    override fun show_pictures2(list: MutableList<ImageDatat.DataBean.LinkBean>?) {
    }

    override fun show_type_list(list: MutableList<TypeModel.Type>?) {

    }

    override fun show_pictures(list: MutableList<PictureModel>?) {
        if (list != null) {
            var a: Array<String>? = null
            for (i in 0 until list.size) {
                a!![i] = list[i].picture_url
            }
            Utils.putCache("urls", a.toString())
        }
    }

    override fun add_imgs_result(boolean: Boolean) {

    }

    fun isServiceRunning(ServiceName: String?): Boolean {
        if ("" == ServiceName || ServiceName == null)
            return false
        val myManager = this
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningService = myManager
                .getRunningServices(30) as ArrayList<ActivityManager.RunningServiceInfo>
        for (i in 0 until runningService.size) {
            var name = runningService.get(i).service.getClassName().toString()
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true
            }
        }
        return false
    }

    override fun initViews() {
        setContentView(R.layout.activity_main)
        startService(Intent(this, KeepLiveServices::class.java))
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        startService(Intent(this, MyReceiver::class.java))
        context = this
        App.is_show = false
        App.num = 0
        //SettingsCompat.manageDrawOverlays(this) 去授权
        start()
        App.pop_is_show = true
        App.pop_num = 0
        start_pop()//开始计时
    }

    companion object {
        var context: Context? = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //暂停
        App.is_show = true
        //挂起pop开始计时
        App.pop_is_show = false
        App.pop_num = 0
        if (isApplicationBroughtToBackground())
            wasBackground = true;
    }

    fun AppSkip(position: Int) {
        var url = ""
        when (position) {
            0 -> url = "cn.cntv"
            1 -> url = "com.tencent.qqlive"
            2 -> url = "com.baidu.netdisk"
            3 -> url = "com.qiyi.video"
            4 -> url = "cn.kuwo.player"
            5 -> url = "com.ss.android.article.news"
            6 -> url = "com.alensw.PicFolder"
        }
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

    var adapter: CommonAdapter<Img>? = null
    var right_adapter: CommonAdapter<Img>? = null

    override fun initEvent() {
        var list = ArrayList<Img>()
        list.add(Img(R.mipmap.img1, "央视新闻"))
        list.add(Img(R.mipmap.img2, "腾讯视频"))
        list.add(Img(R.mipmap.wp, "百度网盘"))
        list.add(Img(R.mipmap.img4, "爱奇艺"))
        list.add(Img(R.mipmap.img5, "酷我音乐"))
        list.add(Img(R.mipmap.img6, "今日头条"))
        list.add(Img(R.mipmap.tk, "本地图库"))
        adapter = object : CommonAdapter<Img>(this, list, R.layout.item_gallery_item1) {
            override fun convert(holder: CommonViewHolder, model: Img, p: Int) {
                holder.setImageResource(R.id.id_index_gallery_item_image, model.id)
                holder.setText(R.id.tv, model.name)

            }
        }
        list = ArrayList<Img>()
        list.add(Img(R.mipmap.wifibox, "wifiBox"))
        list.add(Img(R.mipmap.meiboy, "MEIBOYI"))
        right_adapter = object : CommonAdapter<Img>(this, list, R.layout.item_gallery_item1) {
            override fun convert(holder: CommonViewHolder, model: Img, p: Int) {
                holder.setImageResource(R.id.id_index_gallery_item_image, model.id)
                holder.setText(R.id.tv, model.name)

            }
        }
        var yy_click = 0

        yy_ll.setOnClickListener {
            if (yy_click > 0) {
                var builder = AlertDialog.Builder(this);
                builder.setAdapter(adapter) { v, vv ->
                    if (vv != 7) {
                        AppSkip(vv)
                    }
                }
                builder.setOnDismissListener { hideSystemNavigationBar() }
                builder.show();
                yy_click = 0
            } else {
                yy_click++
            }
        }
        //会员中心
        huiyuan_ll.setOnClickListener { skip("7") }
        //女的
        nv_ll.setOnClickListener { skip("1") }
        //男
        nan_ll.setOnClickListener { skip("2") }
        //儿童
        et_ll.setOnClickListener { skip("3") }
        //资讯
        zx_ll.setOnClickListener { skip("4") }
        //论坛
        lt_ll.setOnClickListener { skip("6") }
        //时尚
        var now_click = 0
        ss_ll.setOnClickListener {

            if (now_click > 0) {
                skip("5")
                now_click = 0
            } else {
                now_click++
            }

        }
        var che_click = 0
        //购物车
        che_ll.setOnClickListener {
            if (che_click > 0) {
                var builder = AlertDialog.Builder(this);
                builder.setAdapter(right_adapter) { v, vv ->
                    try {
                        val packageManager = packageManager
                        var intent = Intent()
                        var url = ""
                        when (vv) {
                            0 -> url = "com.ftr.wificamera.XJ_wifibox"
                            else -> url = "com.g_zhang.MEIBOYI"
                        }
                        intent = packageManager.getLaunchIntentForPackage(url)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        val viewIntent = Intent("android.intent.action.VIEW", Uri.parse("http://baidu.com/"))
                        startActivity(viewIntent)
                    }

                }
                builder.setOnDismissListener { hideSystemNavigationBar() }
                builder.show();
                che_click = 0
            } else {
                che_click++
            }

        }
        //登录
        login_tv.setOnClickListener { startActivityForResult(Intent(this@MainActivity, LoginActivity::class.java), 1) }
        check_login()//判断登录按钮显示隐藏
    }

    /**
     * 跳页处理
     * @type
     * */
    fun skip(type: String) {
        if (!check_login_state()) {
            when (type) {
                "6" -> {//跳转到论坛页面
                    startActivity(Intent(this, TribuneActivity::class.java))
                }
                "7" -> {//跳转到会员中心
                    startActivity(Intent(this, UserCenterActivity::class.java))
                }
                else -> {//跳转到图片选择页面
                    startActivity(Intent(this, NewMainActivity::class.java).putExtra("type", type))
                }
            }
        } else {
            startActivityForResult(Intent(this@MainActivity, LoginActivity::class.java), 1)
        }
    }

    /**
     * 检测当前是否为登录状态
     * user_id为空 未登录
     * @result true 登录
     * */
    fun check_login_state(): Boolean {
        return TextUtils.isEmpty(Utils.getCache(key.user_id))
    }

    /**
     * 控制登录显示隐藏
     * */
    fun check_login() {
        if (check_login_state()) {
            login_tv.visibility = View.VISIBLE
        } else {
            login_tv.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            1 -> check_login()
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
