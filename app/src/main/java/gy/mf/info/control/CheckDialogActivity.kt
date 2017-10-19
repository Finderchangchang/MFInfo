package gy.mf.info.control

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.control.transfer.TotalModelMA.TypeModel.Type.Data2Bean.Data3Bean
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import kotlinx.android.synthetic.main.check_dialog.*
import java.util.*

class CheckDialogActivity : BaseActivity() {
    var lv1_adapter: CommonAdapter<TotalModelMA.TypeModel.Type.Data2Bean>? = null
    var list = ArrayList<TotalModelMA.TypeModel.Type.Data2Bean>()
    var lv2_adapter: CommonAdapter<Data3Bean>? = null
    var list2 = ArrayList<Data3Bean>()
    var islv2: Boolean = true
    var model: TotalModelMA.TypeModel? = null
    override fun initEvent() {
        search_tv.setOnClickListener {
            var result_ids = ""
            for (a in 0 until list.size) {
                var cc = list[a].data_3
                var key = ""
                for (e in 1 until cc.size) {
                    if (cc[e].isCheck) {
                        key += cc[e].class_id + ","
                    }
                }
                result_ids += key
            }
            setResult(11, Intent().putExtra("result", result_ids))
            finish()
        }
    }

    var position = 0
    var type = ""
    override fun initViews() {
        setContentView(R.layout.check_dialog)
        back_iv.setOnClickListener { finish() }
        model = intent.getSerializableExtra("model") as TotalModelMA.TypeModel
        type = intent.getStringExtra("type")
        var now_pp = type.toInt() - 1
        for (key in model!!.all_content) {
            var now_pp = type.toInt() - 1
            if (key.class_sex == type.toString()) {
                list = key.data_2 as ArrayList<TotalModelMA.TypeModel.Type.Data2Bean>
            }
        }
        lv1_adapter = object : CommonAdapter<TotalModelMA.TypeModel.Type.Data2Bean>(this, list, R.layout.check_dialog_item) {
            override fun convert(holder: CommonViewHolder, model: TotalModelMA.TypeModel.Type.Data2Bean, p: Int) {
                holder.setText(R.id.X_item_text, model.class_name)
                holder.setCheck(R.id.X_checkbox, model.isCheck)
                holder.setVisible(R.id.X_checkbox, false)
                if (position == p) {
                    holder.setTextColor(R.id.X_item_text, R.color.black)
                } else {
                    holder.setTextColor(R.id.X_item_text, R.color.peisong_lab)
                }
            }
        }
        X_listview.adapter = lv1_adapter
        X_listview.setOnItemClickListener { parent, view, po, id ->
            list2 = ArrayList()
            position = po
            list2 = list[position].data_3 as ArrayList<Data3Bean>
            if (list2.size == 0 || ("全部") != list2[0].class_name) {
                var data3Bean = Data3Bean()
                data3Bean.class_name = "全部"
                list2.add(0, data3Bean)
            }
            lv2_adapter!!.refresh(list2)
            lv1_adapter!!.refresh(list)
        }
        if (list.size > 0) {
            list2 = list[position].data_3 as ArrayList<Data3Bean>
            if (list2.size == 0 || ("全部") != list2[0].class_name) {
                var data3Bean = Data3Bean()
                data3Bean.class_name = "全部"
                list2.add(0, data3Bean)
            }
        } else {

        }
        lv2_adapter = object : CommonAdapter<Data3Bean>(this, list2, R.layout.check_dialog_item) {
            override fun convert(holder: CommonViewHolder, model: Data3Bean, p: Int) {
                holder.setText(R.id.X_item_text, model.class_name)
                holder.setCheck(R.id.X_checkbox, model.isCheck)
            }
        }
        Y_listview.adapter = lv2_adapter
        Y_listview.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                var is_checked = list2[0].isCheck
                for (ll in list2) {
                    ll.isCheck = !is_checked
                }
            } else {
                if (list2[0].isCheck) {
                    list2[0].isCheck = !list2[0].isCheck
                }
                var size = list2.size
                var click_size = 0
                list2[position].isCheck = !list2[position].isCheck
                for (i in 1 until list2.size) {
                    if (list2[i].isCheck) {
                        click_size++
                        if (click_size == size - 1) {
                            list2[0].isCheck = true
                        }
                    }
                }
            }
            lv2_adapter!!.refresh(list2)
        }
    }
}
