package gy.mf.info.control.login

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.text.TextUtils
import gy.mf.info.control.main.MainActivity
import gy.mf.info.control.reg_user.RegUserActivity


class LoginActivity : BaseActivity(), ILogin {

    override fun initViews() {
        setContentView(R.layout.activity_login)
    }

    override fun initEvent() {
        login_btn.setOnClickListener {
            var name = name_et.text.toString().trim()
            var pwd = pwd_et.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                toast("请输入账户名")
            } else if (TextUtils.isEmpty(pwd)) {
                toast("请输入密码")
            } else {
                LoginListener(this).login(name, pwd)
            }
        }
        close_iv.setOnClickListener { finish() }
        reg_btn.setOnClickListener {
            var inten=Intent(this@LoginActivity,RegUserActivity::class.java)
            startActivityForResult(inten,10)
        }
    }

    /**
     * 登录处理结果
     * @result 处理结果 true：登录成功
     * */
    override fun login_result(result: Boolean) {
        if (result) {
            setResult(1)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==9){
            if(data!!.getBooleanExtra("is_success",false)){
                setResult(1)
                finish()
            }
        }
    }
}
