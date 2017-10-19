package gy.mf.info.model

/**
 * 添加论坛model
 * Created by Finder丶畅畅 on 2017/8/21 20:07
 * QQ群481606175
 */

class AddTribuneModel {
    constructor(msg: String, picture: String) {
        this.msg = msg
        this.picture = picture
    }

    constructor(msg: String, reply: Int) {
        this.msg = msg
        this.reply = reply.toString()
        this.picture = picture
    }

    var user = ""//用户id
    var msg = ""//消息
    var reply = ""//回复的上级id
    var picture = ""//图片
}
