package gy.mf.info.control.check_img

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.TextView
import com.lzy.okgo.OkGo
import com.youth.banner.BannerConfig

import gy.mf.info.R
import gy.mf.info.base.App
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.ImgDetailsActivity
import gy.mf.info.model.NowPosition
import gy.mf.info.model.PictureModel
import gy.mf.info.model.TotalModel
import gy.mf.info.model.TypeModel
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_img_list.*
import okhttp3.Call
import okhttp3.Response
import wai.gr.cla.callback.JsonCallback
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*
import java.net.NetworkInterface.getNetworkInterfaces
import java.net.SocketException
import android.net.wifi.WifiInfo
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.text.TextUtils
import com.google.gson.Gson
import com.lzy.okgo.callback.StringCallback
import gy.mf.info.control.transfer.ImageDatat
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.util.Utils


class ImgListActivity : BaseActivity(), ICheckImg {
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
            img_titles.addAll(list)
            for (ids in list) {
                titles.add(ids.picture_url)
                images.add(ids.picture_name)
            }
        }
        load()
    }

    override fun add_imgs_result(boolean: Boolean) {

    }

    var model: TypeModel? = null
    var time: Int = 3000
    var load_gg = false
    override fun initViews() {
        setContentView(R.layout.activity_img_list)
        model = intent.getSerializableExtra("model") as TypeModel
        time = intent.getIntExtra("time", 3000)
        load_gg = intent.getBooleanExtra("load_gg", false)
        if (load_gg) {
            //显示当前
            var now_position = Utils.getCache("now_position")
            if (TextUtils.isEmpty(now_position)) {
                OkGo.post("http://restapi.amap.com/v3/ip?key=3f38f8f2d8b52d9bdf3d6121e447ef26")
                        .execute(object : StringCallback() {
                            override fun onSuccess(model: String, call: Call?, response: Response?) {
                                var model = Gson().fromJson(model, NowPosition::class.java)
                                CheckImgListener(this@ImgListActivity).getImgs(model.adcode, 1, "1")
                                Utils.putCache("now_position", model.adcode)
                            }

                            override fun onError(call: Call?, response: Response?, e: Exception?) {
                                CheckImgListener(this@ImgListActivity).getImgs("130600", 1, "1")
                            }
                        })
            } else {
                CheckImgListener(this@ImgListActivity).getImgs(now_position, 1, "1")
            }

        } else {
            load()
        }
        main_back_iv.setOnClickListener { finish() }
        setHH(ran_iv, 90)
        ran_iv.setOnClickListener {
            startActivity(Intent(this, ImgDetailsActivity::class.java)
                    .putExtra("position", 0)
                    .putExtra("have", true)
                    .putExtra("model", img_titles[index]))
        }
        activity = this
        var ip = getIp(this)
        ip = ""
    }

    fun getIp(context: Context): String? {
        var ip: String? = null
        val conMan = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // mobile 3G Data Network
        // wifi
        val wifi = conMan.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).state

//        // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
//        if (mobile == android.net.NetworkInfo.State.CONNECTED || mobile == android.net.NetworkInfo.State.CONNECTING) {
//            ip = getLocalIpAddress()
//        }
        if (wifi == android.net.NetworkInfo.State.CONNECTED || wifi == android.net.NetworkInfo.State.CONNECTING) {
            //获取wifi服务
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            //判断wifi是否开启
            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
            }
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            ip = (ipAddress and 0xFF).toString() + "." +
                    (ipAddress shr 8 and 0xFF) + "." +
                    (ipAddress shr 16 and 0xFF) + "." +
                    (ipAddress shr 24 and 0xFF)
        }
        return ip

    }

    /**
     *
     * @return 手机GPRS网络的IP
     */
    private fun getLocalIpAddress(): String {
        try {
            //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {//获取IPv4的IP地址
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
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

    //设置图片资源:url或本地资源
    var images = ArrayList<String>()

    //设置图片标题:自动对应
    var titles = ArrayList<String>()
    var img_titles = ArrayList<PictureModel>()

    override fun initEvent() {

    }

    var index = 0
    fun load() {
        //加载传递过来的数据
        if (model!!.link != null) {
            img_titles.addAll(model!!.link as MutableList)
            for (i in 0 until model!!.link!!.size) {
                if (i < 180) {
                    var modd = model!!.link!![i]
                    titles.add(modd.picture_url)
                    images.add(modd.picture_name)
                }
            }
        }
        banner!!.setImageLoader(GlideImageLoader())
        banner.setBannerTitles(titles.toMutableList())
        banner.setImages(images.toMutableList())
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.start()
        banner.setDelayTime(time)
        if (load_gg) {

        } else {
            banner.stopAutoPlay()
        }

//        var is_stop = false//轮播状态
        var now_click = 0
        banner.setOnTouchListener { v, event ->
            System.out.print(event.x.toString() + "：" + event.y.toString())
            false
        }
        banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                index = position - 1
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
//        banner.setOnBannerListener { position ->
//
////            if (load_gg) {
////                finish()
////            } else {
//            now_click++
//            if (now_click == 1) {
//                var s = model!!.link!![index]
//                startActivity(Intent(this, ImgDetailsActivity::class.java)
//                        .putExtra("position", 0)
//                        .putExtra("have", true)
//                        .putExtra("model", model!!.link!![index]))
//                now_click = 0
//            }
////            }
//        }
    }

    companion object {
        var activity: ImgListActivity? = null
    }

    override fun onDestroy() {
//        App.is_show = false
//        App.num = 0
        activity = null
//        start()
        super.onDestroy()
    }
}
