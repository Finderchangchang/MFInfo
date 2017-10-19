package gy.mf.info.control.transfer;

import android.support.v4.view.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import gy.mf.info.R;
import gy.mf.info.base.BaseActivity;

/**
 * Created by bing.ma on 2017/8/20.
 */

public class PreviewActivity extends BaseActivity {
    ViewPager iv_viewpager;
    private PreviewPagerAdapter mPreviewPagerAdapter;
    public static ArrayList<ImageInfo> mImageInfoList;
    public boolean isLooper = false;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_preview_img);
        isLooper = true;
        iv_viewpager = (ViewPager) findViewById(R.id.iv_viewpager);
        mPreviewPagerAdapter = new PreviewPagerAdapter(this);
        mPreviewPagerAdapter.setDataList(mImageInfoList);
        iv_viewpager.setAdapter(mPreviewPagerAdapter);
        iv_viewpager.addOnPageChangeListener(mPreviewPagerAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLooper = true;
                while (isLooper) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里是设置当前页的下一页
                            int item = iv_viewpager.getCurrentItem() + 1;
                            if (item == mPreviewPagerAdapter.getCount()) {
                                item = 0;
                            }
                            //先强制设定跳转到指定页面
                            try {
                                Field field = iv_viewpager.getClass().getField("mCurItem");//参数mCurItem是系统自带的
                                field.setAccessible(true);
                                field.setInt(iv_viewpager,item);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

//然后调用下面的函数刷新数据
                            mPreviewPagerAdapter.notifyDataSetChanged();
//再调用setCurrentItem()函数设置一次
                            iv_viewpager.setCurrentItem(item);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void initEvent() {

    }

//    private void initAdapter() {
//        mImagePagerAdapter = new ImagePagerAdapter(mCheckImgActivity);
//        iv_viewpager.addOnPageChangeListener(mImagePagerAdapter);
//    }

    @Override
    protected void onDestroy() {
        isLooper = false;
        super.onDestroy();
    }
}
