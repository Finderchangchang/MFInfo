package gy.mf.info.base

/**
 * Created by lenovo on 2017/8/6.
 */
interface IBear {
    //无网络
    fun no_intent()

    //提示消息
    fun error_msg(msg: String)
}