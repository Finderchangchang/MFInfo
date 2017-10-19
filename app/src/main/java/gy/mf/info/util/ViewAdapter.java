package gy.mf.info.util;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import gy.mf.info.R;

/**
 * Created by lenovo on 2017/8/15.
 */

public class ViewAdapter extends PagerAdapter {

    private List<String> viewlist;
    private Context mContext;

    public ViewAdapter(List<String> viewlist, Context mContext) {
        this.viewlist = viewlist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 20;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        View imagelayout = LayoutInflater.from(mContext).inflate(
                R.layout.item, null);
        ImageView imageview = (ImageView) imagelayout
                .findViewById(R.id.iv);

        imageview.setImageResource(R.mipmap.ic_launcher);
        ((ViewPager) container).addView(imagelayout, 0);
        return imagelayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}