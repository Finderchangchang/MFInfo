package gy.mf.info.base

import android.app.Application
import android.content.Context
import cn.finalteam.galleryfinal.CoreConfig
import cn.finalteam.galleryfinal.GalleryFinal
import cn.finalteam.galleryfinal.ThemeConfig
import com.tencent.bugly.crashreport.CrashReport
import gy.mf.info.control.transfer.exception.CrashHandler
import gy.mf.info.method.GlideImageLoader
import gy.mf.info.util.FloatView
import java.util.*

/**
 * Created by Finder丶畅畅 on 2017/7/10 22:19
 * QQ群481606175
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initCrash()
        CrashReport.initCrashReport(this, "0b3fc81617", false)
    }

    companion object {
        var is_show = false
        var num = 180
        var pop_is_show = false
        var pop_num = 120
        var context: Context? = null
        var token = "lasdifsdlkjasdoi"
        var vFloat: FloatView? = null
        var timer: Timer = Timer()
        var pop_timer: Timer = Timer()
    }

    fun initCrash() {
        //val crashHandler = CrashHandler.getInstance()
        //crashHandler.init(applicationContext)
        //        PhotoErrotControl.getRunningAppProcessInfo(this);
    }
}
