package gy.mf.info.model

import java.io.Serializable

/**
 * Created by Finder丶畅畅 on 2017/8/21 21=""//14
 * QQ群481606175
 */
class MessageModel : Serializable {
    var forum_id = ""//消息的id，唯一标识,
    var forum_user = ""//用户,
    var forum_message = ""//消息内容,
    var forum_lastid = ""//上一级id
    var forum_date = ""//时间
    var forum_picture = ""//图片集合
    var reply: MutableList<MessageModel>? = null
}