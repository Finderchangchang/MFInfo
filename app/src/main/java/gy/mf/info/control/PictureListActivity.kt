package gy.mf.info.control

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import gy.mf.info.R
import gy.mf.info.base.BaseActivity
import com.mylhyl.pickpicture.PictureTotal
import com.mylhyl.pickpicture.PickPictureCallback
import com.mylhyl.pickpicture.PickPictureHelper
import kotlinx.android.synthetic.main.activity_picture_list.*
import android.widget.AdapterView
import gy.mf.info.method.CommonAdapter
import gy.mf.info.method.CommonViewHolder
import gy.mf.info.model.PictureModel
import java.util.*


class PictureListActivity : BaseActivity() {
    override fun initViews() {
        setContentView(R.layout.activity_picture_list)
    }

    var adapter: CommonAdapter<PictureTotal>? = null
    var a_list: List<PictureTotal> = ArrayList<PictureTotal>()
    override fun initEvent() {
        adapter = object : CommonAdapter<PictureTotal>(this, a_list, R.layout.activity_index_gallery_item) {
            override fun convert(holder: CommonViewHolder, model: PictureTotal, position: Int) {
                holder.setGImage(R.id.id_index_gallery_item_image, model.topPicturePath)
            }
        }
        var mProgressDialog: ProgressDialog? = null
        var pickPictureHelper = PickPictureHelper.readPicture(this, object : PickPictureCallback {
            override fun onStart() {
                //显示进度条
                mProgressDialog = ProgressDialog.show(this@PictureListActivity, null, "正在加载")
            }

            override fun onSuccess(list: List<PictureTotal>) {
                mProgressDialog!!.dismiss()
                //读取成功，返回 list，直接丢入到 ListView 适配器中
                lv.setAdapter(adapter)
                adapter!!.refresh(list)
            }

            override fun onError() {
                mProgressDialog!!.dismiss()
            }
        })
        lv.setOnItemClickListener(object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {
                val childList = pickPictureHelper.getChildPathList(position)

            }
        })
    }
}
