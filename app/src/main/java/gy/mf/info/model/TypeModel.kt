package gy.mf.info.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by lenovo on 2017/8/16.
 */

class TypeModel: Serializable {
    /**
     * state : 1
     * all_content : null
     */

    var state: String = ""
    var all_content: MutableList<Type>? = null
    var link: MutableList<PictureModel>? = null
    var links: PictureModel? = null
    var msg: MutableList<MessageModel>? = null
    //var reply: MutableList<String>? = null
    var id: String = ""
    var num: String = ""

    inner class Type:Serializable {

        /**
         * 0 : 6
         * class_id : 6
         * class_name : 长的
         * class_set : 2
         * class_sex : 2
         * class_lastid : 5
         *
         *
         * "class_id":他的id，唯一标识
         * "class_name":名字
         * "class_set"://1为第一级，2为第二级
         * "class_sex"://1为男，2为女，3为儿童
         * "class_lastid":上一级id
         */

        var class_id: String = ""
        var class_name: String = ""
        var class_set: String = ""
        var class_sex: String = ""
        var class_lastid: String = ""
        var collection_id="" //  ：唯一的id
        var collection_user=""// ：用户
        var collection_picture=""//  ：图片名称
        var collection_npicture=""
    }
}
