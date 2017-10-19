package gy.mf.info.util

import java.security.MessageDigest

/**
 * Created by lenovo on 2017/8/8.
 */
class urls {
    //http://123.206.219.16:8080
    val total = "http://47.95.210.218/schoolapi/"
//    val total = "http://47.95.210.218/api/schoolapi/"
    //47.95.210.218
    //    val total = "http://123.207.87.199/api/schoolapi/"
    val login = total + "user_login.php"
    val reg_user = total + "user_register.php"//用户注册
    val user_class = total + "user_class.php"//用户分类
    val user_picture = total + "user_picture.php"//获得图片列表
    val user_collection = total + "user_collection.php?action="//图片收藏
    val user_forum = total + "user_forum.php?action="//论坛消息增删改
    val user_forum_picture = total + "user_forum_picture.php"//添加图片操作
    var upload_picture = total + "upload/"//添加图片操作
    var user_password = total + "user_password.php"//修改密码

    /**
     * md5加密
     * */
    fun string2MD5(inStr: String): String {
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            println(e.toString())
            e.printStackTrace()
            return ""
        }

        val charArray = inStr.toCharArray()
        val byteArray = ByteArray(charArray.size)

        for (i in charArray.indices)
            byteArray[i] = charArray[i].toByte()
        val md5Bytes = md5!!.digest(byteArray)
        val hexValue = StringBuffer()
        for (i in md5Bytes.indices) {
            val `val` = md5Bytes[i].toInt() and 0xff
            if (`val` < 16)
                hexValue.append("0")
            hexValue.append(Integer.toHexString(`val`))
        }
        return hexValue.toString()


    }
}