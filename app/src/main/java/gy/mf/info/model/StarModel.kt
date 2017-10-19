package gy.mf.info.model

import java.util.*

/**
 * Created by Finder丶畅畅 on 2017/9/17 21:19
 * QQ群481606175
 */
class StarModel {
    constructor(pid: String, url: String, click: Boolean) {
        this.pid=pid
        this.url = url
        this.click = click
    }

    var pid = ""
    var url = ""
    var click = false
}