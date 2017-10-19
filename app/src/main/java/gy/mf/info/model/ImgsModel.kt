package gy.mf.info.model

import java.io.Serializable
import java.util.*

/**
 * Created by Finder丶畅畅 on 2017/9/16 23:37
 * QQ群481606175
 */
class ImgsModel : Serializable {
    constructor(urls: ArrayList<PictureModel>) {
        this.urls = urls
    }

    var urls = ArrayList<PictureModel>()
}