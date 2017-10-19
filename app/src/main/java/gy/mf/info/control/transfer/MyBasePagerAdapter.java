package gy.mf.info.control.transfer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by bing.ma on 2017/8/19.
 */

public abstract class MyBasePagerAdapter<T> extends PagerAdapter {

    public Context mContext;

    private ArrayList<T> mDataList;
    private View mCurrentView;

    public MyBasePagerAdapter() {

    }

    public MyBasePagerAdapter(Context context) {
        this.mContext = context;
    }

    public MyBasePagerAdapter(ArrayList<T> dataList) {
        this.mDataList = dataList;
    }

    public MyBasePagerAdapter(Context context, ArrayList<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    public ArrayList<T> getmDataList() {
        return mDataList;
    }

    public void setDataList(ArrayList<T> mDataList) {
        this.mDataList = mDataList;
    }

    public T getDataFromPosition(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = getItemView(position);
        container.addView(view);
        return view;
    }

    public abstract View getItemView(int position);

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
        super.setPrimaryItem(container, position, object);
    }


    public View getCurrentShowItem() {
        return mCurrentView;
    }
}
