package gy.mf.info.control

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bigkoo.pickerview.OptionsPickerView

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import gy.mf.info.control.check_img.CheckImgListener
import gy.mf.info.control.transfer.TotalModelMA
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.MessageModel
import kotlinx.android.synthetic.main.activity_dialog.*
import java.text.FieldPosition
import java.util.*
import android.view.WindowManager


class DialogActivity : BaseActivity() {
    var now_type = ""
    var level = ""
    var model: TotalModelMA? = null
    var options1Items = ArrayList<String>()
    var options2Items = ArrayList<String>()
    var type_list: MutableList<TotalModelMA.TypeModel.Type> = ArrayList<TotalModelMA.TypeModel.Type>()
    var lv1_adapter: CommonAdapter<String>? = null//资讯
    var lv2_adapter: CommonAdapter<String>? = null//资讯
    var type = ""
    override fun initViews() {
        setContentView(R.layout.activity_dialog)
        model = intent.getSerializableExtra("model") as TotalModelMA
        type = intent.getStringExtra("type")
        type_list = model!!.data.all_content
        lv1_adapter = object : CommonAdapter<String>(this, options1Items, R.layout.item_tag) {
            override fun convert(holder: CommonViewHolder, model: String, position: Int) {
                holder.setText(R.id.val_tv, model)
            }
        }
        lv2_adapter = object : CommonAdapter<String>(this, options2Items, R.layout.item_tag) {
            override fun convert(holder: CommonViewHolder, model: String, position: Int) {
                holder.setText(R.id.val_tv, model)
            }
        }
        lv1.adapter = lv1_adapter
        lv2.adapter = lv2_adapter
        getOptionData2()
        lv2.setOnItemClickListener { parent, view, position, id ->
            var inten: Intent = Intent()
            inten.putExtra("now_type", now_type)
            inten.putExtra("level", level)
            setResult(11, inten)
            finish()
        }
        lv1.setOnItemClickListener { parent, view, position, id ->
            load2(position)
        }
    }

    override fun initEvent() {

    }

    fun getOptionData2() {
        for (model in type_list[type.toInt()].data_2) {
            options1Items.add(model.class_name)
        }
        lv1_adapter!!.refresh(options1Items)
    }

    fun load2(position: Int) {
        options2Items = ArrayList()
        options2Items.add("全部")
        for (model in type_list[type.toInt()].data_2[position].data_3) {
            options2Items.add(model.class_name)
        }
        lv2_adapter!!.refresh(options2Items)
    }
}
