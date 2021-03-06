package gy.mf.info.control.check_img;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Finder丶畅畅 on 2017/8/26 22:43
 * QQ群481606175
 */

public class MyPageAdapter extends PagerAdapter {

    private ArrayList<View> listViews;// content

    private int size;// 页数

    public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
        // 初始化viewpager的时候给的一个页面
        this.listViews = listViews;

        size = listViews == null ? 0 : listViews.size();
    }
    public void refresh(ArrayList<View> listViews){
        this.listViews = listViews;

        size = listViews == null ? 0 : listViews.size();
        notifyDataSetChanged();
    }
    public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据  这个可是重点啊
        this.listViews = listViews;
        size = listViews == null ? 0 : listViews.size();
    }

    @Override
    public int getCount() {// 返回数量
        return size;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
        ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {// 返回view对象
        try {
            ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);
        } catch (Exception e) {

        }
        return listViews.get(arg1 % size);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}