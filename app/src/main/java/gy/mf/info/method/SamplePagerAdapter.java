package gy.mf.info.method;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import gy.mf.info.R;
import gy.mf.info.util.urls;

public class SamplePagerAdapter extends PagerAdapter {
    List<String> urls;
    Context context;

    public SamplePagerAdapter(Context context, List<String> url) {
        urls = url;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.size();
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
        ImageView textView = new ImageView(view.getContext());
        textView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(new urls().getUpload_picture() + urls.get(position)).error(R.mipmap.ic_launcher).into(textView);
        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;
    }
}