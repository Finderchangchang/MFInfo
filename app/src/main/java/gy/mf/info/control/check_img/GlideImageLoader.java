package gy.mf.info.control.check_img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import gy.mf.info.util.urls;

/**
 * Created by lenovo on 2017/8/27.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(new urls().getUpload_picture() + path).into(imageView);
        }
    }
}

