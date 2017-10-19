package gy.mf.info.control.user_center

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.img_detail.CheckedImgActivity
import gy.mf.info.control.main.MainActivity
import gy.mf.info.util.Utils
import gy.mf.info.util.key
import kotlinx.android.synthetic.main.activity_user_center.*

class UserCenterActivity : BaseActivity() {
    override fun initViews() {
        setContentView(R.layout.activity_user_center)
        back_iv.setOnClickListener { finish() }
        if (TextUtils.isEmpty(Utils.getCache(key.user_id))) {
            user_tv_name.text = "未登录"
        } else {
            user_tv_name.text = Utils.getCache(key.user_id)
        }
        exit_btn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("提示")
            builder.setMessage("确定退出当前账号吗？")
            builder.setNegativeButton("取消", null)
            builder.setPositiveButton("确定") { dialog, which ->
                toast("清除成功")
                user_tv_name.text = "未登录"
                Utils.putCache(key.user_id, "")
                finish()
            }
            builder.setOnDismissListener {
                hideSystemNavigationBar()
            }
            builder.show()
        }
        user_mv_sc.setOnClickListener {
            startActivity(Intent(this, CheckedImgActivity::class.java))
        }
        user_mv_dd.setOnClickListener {
            startActivity(Intent(this, UpdatePwdActivity::class.java))
        }
    }

    override fun initEvent() {

    }
}
