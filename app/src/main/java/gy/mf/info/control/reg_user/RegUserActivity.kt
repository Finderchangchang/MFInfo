package gy.mf.info.control.reg_user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_reg_user.*

class RegUserActivity : BaseActivity(),IRegUser {
    /**
     * 处理注册结果
     * */
    override fun reg_result(result: Boolean) {
        if(result){
            var inten=Intent()
            inten.putExtra("is_success",true)
            setResult(9,inten)
            finish()
        }
    }

    override fun initViews() {
        setContentView(R.layout.activity_reg_user)
    }

    override fun initEvent() {
        close_iv.setOnClickListener { finish() }
        reg_btn.setOnClickListener {
            var name = name_et.text.toString().trim()
            var pwd = pwd_et.text.toString().trim()
            var shop_name = shop_name_et.text.toString().trim()
            var shop_address = shop_address_et.text.toString().trim()

            if (TextUtils.isEmpty(name)) {
                toast("请输入手机号")
            } else if (TextUtils.isEmpty(pwd)) {
                toast("请输入密码")
            } else if (TextUtils.isEmpty(shop_name)) {
                toast("请输入店铺名称")
            } else if (TextUtils.isEmpty(shop_address)) {
                toast("请输入店铺地址")
            } else {
                RegUserListener(this).reg_do(name,pwd,shop_name,shop_address)
            }
        }
    }
}
