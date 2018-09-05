package gy.mf.info.control;

import android.content.Context;
import android.widget.ImageView;
import com.jaiky.imagespickers.ImageLoader;
import com.bumptech.glide.Glide;

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .centerCrop()
                .into(imageView);
    }

}
