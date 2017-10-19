package gy.mf.info.control.transfer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import gy.mf.info.R;
import gy.mf.info.model.PictureModel;

/**
 * Created by bing.ma on 2017/8/19.
 */

public class ImageInfo {
    public ImageView imageView;
    public String imagePath;
    public String assetsPath;
    public InputStream inputStream;
    public String name;
    public String id;
    public String setId;
    public int position;
//    public InputStream inputStream;
//    public InputStream inputStream;

    public ImageInfo() {
    }


    public ImageInfo(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageInfo(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageInfo(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ImageInfo(ImageView imageView, String imagePath) {
        this.imageView = imageView;
        this.imagePath = imagePath;
    }

    public ImageInfo(PictureModel pictureModel) {

    }

    public ImageInfo(ImageDatat.DataBean.LinkBean linkBean) {
//       this. imageView   =linkBean.   ;
        //       this. assetsPath  =linkBean.   ;
//        this. inputStream =linkBean.   ;
        this.imagePath = linkBean.getPicture_url();
        this.name = linkBean.getPicture_name();
        this.id = linkBean.getPicture_id();
        this.setId = linkBean.getPicture_set();
    }

    public Bitmap toBitmap(Context context, int screenWidth, int screenHight) {
        Bitmap bitmap = null;
        if (StringUtils.noEmpty(imagePath))
            bitmap = BitmapCompressor.decodeSampledBitmapFromFile(imagePath, screenWidth, screenHight);
        else if (inputStream != null) {
            bitmap = BitmapCompressor.decodeSampledBitmapFromStream(inputStream, screenWidth, screenHight);
        } else {
            bitmap = BitmapCompressor.decodeSampledBitmapFromResource(context.getResources(), R.mipmap.ic_launcher, screenWidth, screenHight);
        }
        Log.i("jiji", inputStream.toString());
        return bitmap;
    }

    public boolean isSame(ImageInfo imageInfo) {
        boolean b1 = StringUtils.StringIsSame(imagePath, imageInfo.imagePath);
        boolean b2 = StringUtils.StringIsSame(assetsPath, imageInfo.assetsPath);
        boolean b3 = StringUtils.StringIsSame(name, imageInfo.name);
//        boolean b2 = StringUtils.ObjectIsSame(inputStream, imageInfo.inputStream);
        if (b1 && b2 && b3) {
            return true;
        }
        return false;
    }

}
