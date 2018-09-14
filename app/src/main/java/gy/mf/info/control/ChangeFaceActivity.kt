package gy.mf.info.control

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Base64
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.jaiky.imagespickers.ImageConfig
import com.jaiky.imagespickers.ImageSelector
import com.jaiky.imagespickers.ImageSelectorActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.mylhyl.circledialog.CircleDialog
import com.mylhyl.circledialog.params.ProgressParams
import gy.mf.info.R
import gy.mf.info.control.MoveHairActivity.bitmapToBase64
import gy.mf.info.util.urls
import kotlinx.android.synthetic.main.activity_change_face.*
import okhttp3.Call
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ChangeFaceActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    var old_hair_bitmap:Bitmap?=null
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        //bg_img.imageAlpha = p1
        if(old_hair_bitmap!=null) {
            if(p1<3){
                bg_img.setImageBitmap(old_hair_bitmap)
            }else{
                bg_img.setImageBitmap(BeautifyMultiThread().beautifyImg(old_hair_bitmap, p1))
            }
        }else{
            bg_img.setDrawingCacheEnabled(true)
            old_hair_bitmap = Bitmap.createBitmap(bg_img.getDrawingCache())
            bg_img.setDrawingCacheEnabled(false)
            bg_img.setImageBitmap(BeautifyMultiThread().beautifyImg(old_hair_bitmap, p1))
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    var FILE_PATH: String=""
    internal var imageConfig: ImageConfig?=null
    internal fun save_img(url: String, bitmap: Bitmap): Boolean {
        try {
            FILE_PATH = cacheDir.toString() + "/cache/pics"
            // 创建文件流，指向该路径，文件名叫做fileName
            val file = File(FILE_PATH, "demo.png")
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            val fileParent = file.parentFile
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs()// 创建文件夹
            }
            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    FileOutputStream(file))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }
    var bottom_left_click=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_face)
        left_iv.imageAlpha=0
        right_iv.imageAlpha=100
        left_iv.setOnClickListener{
            bottom_left_click=true
            left_iv.imageAlpha=0
            right_iv.imageAlpha=100
        }
        right_iv.setOnClickListener{
            bottom_left_click=false
            left_iv.imageAlpha=100
            right_iv.imageAlpha=0
        }
        back_iv.setOnClickListener { finish() }
        imageConfig = ImageConfig.Builder(GlideLoader())
                .steepToolBarColor(resources.getColor(R.color.blue))
                .titleBgColor(resources.getColor(R.color.blue))
                .titleSubmitTextColor(resources.getColor(R.color.white))
                .titleTextColor(resources.getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                //.showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build()
        hl_btn.setOnClickListener {
            ImageSelector.open(this@ChangeFaceActivity, imageConfig)   // 开启图片选择器
        }
        seekBar.max=100
        seekBar.setOnSeekBarChangeListener(this);
        val url = intent.getStringExtra("url")
        Glide.with(this).load(url).into(bg_img)

        //下一步
        next_btn.setOnClickListener {
            bg_img.setDrawingCacheEnabled(true)
            val hair_bitmap = Bitmap.createBitmap(bg_img.getDrawingCache())
            bg_img.setDrawingCacheEnabled(false)

            val baos = ByteArrayOutputStream()
            hair_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val bytes = baos.toByteArray()

            val b = Bundle()
            b.putByteArray("bitmap", bytes)
            if (save_img(FILE_PATH, hair_bitmap)) {
                startActivity(Intent(this@ChangeFaceActivity, MoveHairActivity::class.java).putExtra("url", FILE_PATH))
            }
        }

    }

    internal var dialog: DialogFragment? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            // Get Image Path List
            val imagesPath = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)

            if (imagesPath != null && imagesPath.size > 0) {
                dialog = CircleDialog.Builder()
                        .setProgressText("换脸中，请稍后...")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
                        //                        .setProgressDrawable(R.drawable.bg_progress_s)
                        .show(supportFragmentManager)
                val url = imagesPath[0]
                val bmp = BitmapFactory.decodeFile(url)
                bg_img.isDrawingCacheEnabled = true
                val bmp2 = Bitmap.createBitmap(bg_img.drawingCache)
                bg_img.isDrawingCacheEnabled = false

                val i1 = bitmapToBase64(compressQuality(bmp))
                val i2 = bitmapToBase64(compressQuality(bmp2))
                OkGo.post("http://47.95.210.218:81/upload")
                        .params("head", i2)
                        .params("face", i1)
                        .execute(object : StringCallback() {
                            override fun onSuccess(s: String, call: Call, response: Response) {
                                bg_img.setImageBitmap(base64ToBitmap(s))
                                dialog?.dismiss()
                                Toast.makeText(this@ChangeFaceActivity, "换脸成功", Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(call: Call?, response: Response?, e: Exception?) {
                                super.onError(call, response, e)
                                val mye = e!!.message
                                dialog?.dismiss()
                                Toast.makeText(this@ChangeFaceActivity, "换脸失败，请稍后重试", Toast.LENGTH_SHORT).show()
                            }
                        })
            }
        }
    }

    private fun compressQuality(bm: Bitmap): Bitmap {
        val bos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 30, bos)
        val bytes = bos.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
