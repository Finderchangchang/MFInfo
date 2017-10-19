package gy.mf.info.control;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Finder丶畅畅 on 2017/8/27 08:40
 * QQ群481606175
 */

public class aa extends Activity {
    ViewPager viewPager;
    public void cc(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
