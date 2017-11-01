package gy.mf.info.model

import android.widget.ImageView
import java.io.InputStream
import java.io.Serializable

/**
 * Created by Administrator on 2017/8/19.
 */

class PictureModel : Serializable {

    /**
     * picture_id:唯一id
     * picture_name：图片名称
     * picture_set：分类
     * picture_url：外链
     */
    var click=false
    var picture_id: String = ""
    var picture_name: String = ""
    var picture_set: String = ""
    var picture_url: String = ""
    var is_collection = 0
    var tag_id = ""
    var collection_id = "" //唯一的id
    var collection_user = ""//用户
    var collection_picture = ""//图片名称
    var picture_tsjc = ""//图示教程
    var picture_videoUrl = ""//视频教程
    var picture_fxsj = ""//发型设计
    var collection=0//0未收藏
}
