package gy.mf.info.control.img_detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.util.List;
import java.util.Random;

import gy.mf.info.R;
import gy.mf.info.util.urls;
import okhttp3.Call;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ImgAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int mSize;
    List<String> models;
    Context context;

    public ImgAdapter(Context context, List<String> url) {
        models = url;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View views = LayoutInflater.from(context).inflate(R.layout.layout_img, null);
        PhotoView textView = (PhotoView) views.findViewById(R.id.img_iv);
        //Glide.with(context).load(URLUtil.get_image + models.get(position).getSealCertificateId()).into(textView);
        String url = new urls().getUpload_picture() + models.get(position);

        Glide.with(context).load(url).error(R.mipmap.fen_bg).into(textView);
        view.addView(views);
        return views;
    }
}