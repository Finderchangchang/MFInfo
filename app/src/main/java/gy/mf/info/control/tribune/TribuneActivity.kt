package gy.mf.info.control.tribune

import android.content.Intent
import android.text.TextUtils
import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.AddTribuneModel
import gy.mf.info.model.MessageModel
import kotlinx.android.synthetic.main.activity_tribune.*
import java.util.*
import android.widget.AbsListView
import gy.mf.info.method.LoadListView
import gy.mf.info.util.urls

/**
 * 论坛页面
 * */
class TribuneActivity : BaseActivity(), ITribune {
    var zx_list: MutableList<MessageModel> = ArrayList<MessageModel>()
    var zx_adapter: CommonAdapter<MessageModel>? = null//资讯
    var page_index = 1//当前页数
    override fun initViews() {
        setContentView(R.layout.activity_tribune)
        //解决刷新和listview冲突问题
        asl_llv.setIsValid(object : LoadListView.OnSwipeIsValid {
            override fun setSwipeEnabledTrue() {
                main_srl.isEnabled = true
            }

            override fun setSwipeEnabledFalse() {
                main_srl.isEnabled = false
            }
        })
        back_iv.setOnClickListener { finish() }
    }

    override fun initEvent() {
        zx_adapter = object : CommonAdapter<MessageModel>(this, zx_list, R.layout.item_wenda) {
            override fun convert(holder: CommonViewHolder, model: MessageModel, position: Int) {
                holder.setText(R.id.wen_tv, model.forum_message)
                holder.setText(R.id.time_tv, model.forum_date)
                holder.setText(R.id.tv_userid, model.forum_user)
                val urlsa = model.forum_picture//获得所有图片的url
                if (urlsa.contains(":")) {
                    val pics = urlsa.split(":")
                    holder.setVisible(R.id.wen_ll, true)
                    if (pics.size >= 1 && !TextUtils.isEmpty(pics[0])) {
                        holder.setGImage(R.id.wen1_iv, pics[0])
                    }
                    if (pics.size >= 2 && !TextUtils.isEmpty(pics[1])) {
                        holder.setGImage(R.id.wen2_iv, pics[1])
                    }
                    if (pics.size >= 3 && !TextUtils.isEmpty(pics[2])) {
                        holder.setGImage(R.id.wen3_iv, pics[2])
                    }
                } else {
                    holder.setVisible(R.id.wen_ll, false)
                }
            }
        }
        //发布按钮
        add_btn.setOnClickListener {
            startActivityForResult(Intent(this@TribuneActivity, AddTribuneActivity::class.java), 1)
        }
        asl_llv.adapter = zx_adapter
        refresh()//刷新数据
        //加载更多操作
        asl_llv.setInterface {
            page_index++
            refresh()
        }
        //刷新操作
        main_srl.setOnRefreshListener {
            page_index = 1
            refresh()
        }
        asl_llv.setOnItemClickListener { parent, view, position, id ->
            startActivityForResult(Intent(this@TribuneActivity, TribuneDetailActivity::class.java).putExtra("model", zx_list[position]), 1)
        }
    }

    /**
     * 获得查询出来的list
     * @list 查询出来的数据
     * */
    override fun show_ss(list: MutableList<MessageModel>?, total_count: Int) {
        //如果当前页码为1，清空所有数据
        if (page_index == 1) {
            zx_list.clear()
        }
        zx_list.addAll(list as ArrayList)
        zx_adapter!!.refresh(list)//刷新页面数据
        asl_llv.getIndex(page_index, 20, total_count)
        main_srl.isRefreshing = false
    }

    /**
     * 刷新页面数据
     * */
    fun refresh() {
        TribuneListener(this).searchShuoList(page_index)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            22 -> {
                page_index = 1
                refresh()
            }
        }
    }

    override fun add_img_result(local_file: String, url: String) {}

    override fun reg_result(result: Boolean) {}
}
