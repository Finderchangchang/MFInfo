package gy.mf.info.control.user_center;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Finder丶畅畅 on 2017/8/30 13:06
 * QQ群481606175
 */

public class cc extends Activity {
    View vie;
    void ccc(){
        vie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
