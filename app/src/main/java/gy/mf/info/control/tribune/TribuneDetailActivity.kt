package gy.mf.info.control.tribune

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.ImgDetailsActivity
import gy.mf.info.control.check_img.OneImgDetailActivity
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.AddTribuneModel
import gy.mf.info.model.ImgsModel
import gy.mf.info.model.MessageModel
import gy.mf.info.model.PictureModel
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_tribune_detail.*
import java.util.*

/**
 * 论坛详情页
 * */
class TribuneDetailActivity : BaseActivity(), ITribune {
    override fun reg_result(result: Boolean) {
        if (result) {
            add_result = true
            zx_list.add(0, message)
            zx_adapter!!.refresh(zx_list)
            msg_et.setText("")
        }
    }

    override fun onBackPressed() {
        if (add_result) {
            setResult(22)
            finish()
        }
        super.onBackPressed()
    }

    var add_result = false
    var model: MessageModel? = null
    var zx_list: MutableList<MessageModel> = ArrayList<MessageModel>()
    var zx_adapter: CommonAdapter<MessageModel>? = null//资讯
    var message: MessageModel = MessageModel()
    override fun initViews() {
        setContentView(R.layout.activity_tribune_detail)
        back_iv.setOnClickListener { finish() }
        model = intent.getSerializableExtra("model") as MessageModel
        zx_list = model!!.reply!!
        zx_adapter = object : CommonAdapter<MessageModel>(this, zx_list, R.layout.item_wenda) {
            override fun convert(holder: CommonViewHolder, model: MessageModel, position: Int) {
                holder.setText(R.id.wen_tv, model.forum_message)
                holder.setText(R.id.tv_userid, model.forum_user)
                holder.setText(R.id.time_tv, model.forum_date)
            }
        }
        answer_lv.adapter = zx_adapter
    }

    var urlsa = ""
    override fun initEvent() {
        wen_tv.text = model!!.forum_message
        time_tv.text = model!!.forum_date
        urlsa = model!!.forum_picture//获得所有图片的url
        if (urlsa.contains(":")) {
            val pics = urlsa.split(":")
            if (pics.size >= 1 && !TextUtils.isEmpty(pics[0])) {
                Glide.with(this).load(urls().total + "upload/" + pics[0]).error(R.mipmap.fen_bg).into(wen1_iv)
                var model: PictureModel = PictureModel()
                model.picture_name = pics[0]
                img_lists.add(model)
                wen1_iv.visibility = View.VISIBLE
            }
            if (pics.size >= 2 && !TextUtils.isEmpty(pics[1])) {
                Glide.with(this).load(urls().total + "upload/" + pics[1]).error(R.mipmap.fen_bg).into(wen2_iv)
                var model: PictureModel = PictureModel()
                model.picture_name = pics[1]
                img_lists.add(model)
                wen2_iv.visibility = View.VISIBLE
            }
            if (pics.size >= 3 && !TextUtils.isEmpty(pics[2])) {
                Glide.with(this).load(urls().total + "upload/" + pics[2]).error(R.mipmap.fen_bg).into(wen3_iv)
                var model: PictureModel = PictureModel()
                model.picture_name = pics[2]
                img_lists.add(model)
                wen3_iv.visibility = View.VISIBLE
            }
        }
        wen1_iv.setOnClickListener { skip(0) }
        wen2_iv.setOnClickListener { skip(1) }
        wen3_iv.setOnClickListener { skip(2) }
        send_btn.setOnClickListener {
            var send_message = msg_et.text.toString().trim()
            message.forum_message = send_message
            message.forum_date = "刚刚"
            if (!TextUtils.isEmpty(send_message)) {
                TribuneListener(this).add_delShuo(AddTribuneModel(send_message, model!!.forum_id.toInt()), "add")
            } else {
                toast("请输入些内容吧...")
            }
        }
    }

    var img_lists: ArrayList<PictureModel> = ArrayList<PictureModel>()
    fun skip(index: Int) {
        var model: PictureModel = PictureModel()
        var urls = ""
        for (i in urlsa.split(":")) {
            if (!TextUtils.isEmpty(i)) {
                urls += i + ","
            }
        }
        model.picture_name = urls
//        startActivity(Intent(this, OneImgDetailActivity::class.java).putExtra("model", model))
        startActivity(Intent(this, ImgDetailsActivity::class.java)
                .putExtra("position", index - 1)
                .putExtra("model", model)
                .putExtra("is_show", true))
    }

    override fun show_ss(list: MutableList<MessageModel>?, total_count: Int) {

    }

    override fun add_img_result(local_file: String, url: String) {

    }
}
