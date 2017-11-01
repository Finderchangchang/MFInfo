package gy.mf.info.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import android.view.MotionEvent
import java.util.*
import android.content.Intent
import android.os.Handler
import android.os.Message
import gy.mf.info.control.check_img.ImgListActivity
import gy.mf.info.model.TypeModel
import gy.mf.info.util.FloatView
import gy.mf.info.util.SettingsCompat
import android.R.attr.orientation
import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import gy.mf.info.control.NewMainActivity
import gy.mf.info.control.check_img.CheckImgActivity
import gy.mf.info.model.PictureModel


/**
 * Created by Finder丶畅畅 on 2017/7/10 22:23
 * QQ群481606175
 */

abstract class BaseActivity : AppCompatActivity(), IBear {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initViews()
        initEvent()
        if (App.vFloat == null) {
            App.vFloat = FloatView(App.context)
            //checkPermission()
            if (SettingsCompat.canDrawOverlays(this)) {
                App.vFloat!!.attach()
            } else {
                App.vFloat!!.detach()
                SettingsCompat.manageDrawOverlays(this)
            }
        } else {
            var s = ""
        }
    }

    //判断当前的应用程序是不是在运行
    //需要申请GETTask权限
    fun isApplicationBroughtToBackground(): Boolean {
        var am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;
        var tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            var topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true; }
        }
        return false; }

    var wasBackground = false    //声明一个布尔变量,记录当前的活动背景
    var can_start_pop=false//true:能启动
    override fun onResume() {
        hideSystemNavigationBar()
        //vFloat!!.detach()
        start()
        App.is_show = can_start_pop
        App.pop_is_show = true//pop停止计时
        if (wasBackground) {
            var s = ""
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        //暂停
        App.is_show = true
        //挂起pop开始计时
        App.pop_is_show = false
        App.pop_num = 0
        if (isApplicationBroughtToBackground())
            wasBackground = true;
    }


    var type_model: TypeModel = TypeModel()
    // 每当用户接触了屏幕，都会执行此方法
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        App.num = 0
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 定时弹出
     * 1.弹出状态，横屏状态不计时
     * 2.收回之后开始计时
     * */
    //var timer = Timer()
//    var pop_timer = Timer()
    var pop_task: TimerTask? = null
    var task: TimerTask? = null
    fun start_pop() {
        App.pop_num = 0
        App.pop_timer.cancel()
        App.pop_timer = Timer()
        pop_task = object : TimerTask() {
            override fun run() {
                if (!App.pop_is_show && isScreenOriatationPortrait()) {
                    val message = Message()
                    message.what = 1
                    pop_handler.sendMessage(message)
                }
            }
        }
        App.pop_timer.schedule(pop_task, 1000 * 1, 1000 * 1)
    }

    /**
     * 发送验证码操作
     * */
    fun start() {
        App.num = 0
        App.timer.cancel()
        App.timer = Timer()
        task=null
        task = object : TimerTask() {
            override fun run() {
                if (!App.is_show) {
                    val message = Message()
                    message.what = 1
                    handler.sendMessage(message)
                }
            }
        }
        App.timer.schedule(task, 1000 * 1, 1000 * 1)
    }
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (App.num >180 && !App.is_show) {
                App.is_show = true
                App.timer.cancel()
                if (ImgListActivity.activity == null) {
                    if (CheckImgActivity != null) {
                        type_model = NewMainActivity.model
                    } else {
                        type_model = TypeModel()
                    }
                    startActivity(Intent(this@BaseActivity, ImgListActivity::class.java)
                            .putExtra("model", type_model)
                            .putExtra("load_gg", true))
                }
            } else {
                App.num++
            }
        }
    }
    var pop_handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (App.pop_num > 120 && !App.pop_is_show) {
                App.vFloat!!.setShowOrClose()
            } else {
                App.pop_num++
            }
        }
    }

    fun isScreenOriatationPortrait(): Boolean {
        return getResources().getConfiguration().orientation === Configuration.ORIENTATION_PORTRAIT
    }


    fun hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT in 12..18) {
            val view = this.window.decorView
            view.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView//View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            val uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            decorView.systemUiVisibility = uiOptions
        }
    }

    abstract fun initViews()

    abstract fun initEvent()
    private var toast: Toast? = null
    //输出提示信息
    fun toast(mes: String) {
        if (toast == null) {
            toast = Toast.makeText(applicationContext, mes, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(mes)
        }
        if (toast != null) {
            toast!!.show()
        }
    }

    /**
     * 无网络显示的内容
     * */
    override fun no_intent() {
        toast("请检查您的网络连接")
    }

    /***
     * 提示错误信息
     */
    override fun error_msg(msg: String) {
        toast(msg)
    }

    companion object {
        /**
         * 显示导航栏
         */
        fun showBar() {
            try {
                val command: String
                command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib am startservice -n com.android.systemui/.SystemUIService"
                val envlist = ArrayList<String>()
                val env = System.getenv()
                for (envName in env.keys) {
                    envlist.add(envName + "=" + env[envName])
                }
                val envp = envlist.toTypedArray()
                val proc = Runtime.getRuntime().exec(
                        arrayOf("su", "-c", command), envp)
                proc.waitFor()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
