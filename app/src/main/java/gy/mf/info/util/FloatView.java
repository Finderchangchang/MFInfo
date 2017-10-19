package gy.mf.info.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gy.mf.info.R;
import gy.mf.info.base.App;
import gy.mf.info.control.check_img.CheckImgListener;
import gy.mf.info.control.check_img.ICheckImg;
import gy.mf.info.control.transfer.ImageDatat;
import gy.mf.info.control.transfer.TotalModelMA;
import gy.mf.info.model.NowPosition;
import gy.mf.info.model.PictureModel;
import gy.mf.info.model.TypeModel;
import okhttp3.Call;
import okhttp3.Response;

public class FloatView extends AppCompatImageView implements ICheckImg {


    private Rect mRect = new Rect();
    private WindowManager mWm;
    private WindowManager.LayoutParams mLp = new WindowManager.LayoutParams();

    int mTouchSlop;
    float density = getResources().getDisplayMetrics().density;

    Context context;
    FloatView view;
    ICheckImg check;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Glide.with(context)
                            .load(msg.obj)
                            .crossFade()
                            .into(view);
            }
        }
    };

    public FloatView(Context context) {
        super(context);
        this.context = context;
        check = this;
        getWindowVisibleDisplayFrame(mRect);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mTouchSlop = mTouchSlop * mTouchSlop;


        mWm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mLp.gravity = Gravity.LEFT | Gravity.TOP;
        mLp.format = PixelFormat.RGBA_8888;
        mLp.width = 1;
        mLp.height = 1;
        mLp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLp.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            mLp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }


        setOnClickListener(mClickListener);
        setOnTouchListener(mTouchListener);
        view = this;
        String now_position = RomUtil.getCache("now_position");
        if (TextUtils.isEmpty(now_position)) {
            OkGo.post("http://restapi.amap.com/v3/ip?key=3f38f8f2d8b52d9bdf3d6121e447ef26")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            NowPosition model = new Gson().fromJson(s, NowPosition.class);
                            new CheckImgListener(check).getImgs(model.getAdcode(), 1, "1");
                            RomUtil.putCache("now_position", model.getAdcode());
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            new CheckImgListener(check).getImgs("130600", 1, "1");

                        }
                    });
        } else {
            new CheckImgListener(check).getImgs(now_position, 1, "1");
        }
    }


    public void attach() {
        if (getParent() == null) {
            mWm.addView(this, mLp);
        }
        mWm.updateViewLayout(this, mLp);
        getWindowVisibleDisplayFrame(mRect);
        mRect.top += dp2px(30);
        mLp.y = dp2px(450);
        mLp.x = mRect.width() - dp2px(55);
        reposition();
    }

    public void detach() {
        try {
            mWm.removeViewImmediate(this);
        } catch (Exception e) {

        }
    }

    boolean click = false;
    Timer timer = new Timer();
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setShowOrClose();
        }
    };

    //设置当前状态是显示还是隐藏
    public void setShowOrClose() {
        if (click) {
            mLp.width = 1;
            mLp.height = 1;
            //timer.cancel();
            recLen = 12;
            //if (!App.Companion.getPop_is_show()) {
            App.Companion.setPop_num(0);//重新计时
            App.Companion.setPop_is_show(false);
            //}
        } else {
            mLp.width = WindowManager.LayoutParams.MATCH_PARENT;
            mLp.height = 600;
            App.Companion.setPop_is_show(true);//停止计时
            App.Companion.setPop_num(11);//重新计时
            timer = new Timer();
            recLen = 11;
            if (!isStart) {
                timer.schedule(task, 1000, 1000);
            }
            isStart = true;
        }
        click = !click;
        mWm.updateViewLayout(FloatView.this, mLp);
    }

    private boolean isStart = false;
    private int recLen = 0;
    String[] urls;
    int now_position = 0;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            recLen++;
            if (recLen == 12) {
                Message message = new Message();
                message.what = 1;
                if (urls != null && urls.length > 0) {
                    message.obj = urls[now_position];
                } else {
                    message.obj = "";
                }
                handler.sendMessage(message);
                if (urls != null && now_position == urls.length - 1) {
                    now_position = 0;
                } else {
                    if (urls != null) {
                        now_position++;
                    } else {
                        now_position = 0;
                    }
                }
                recLen = 0;
            }
        }
    };

    private boolean isDragging;
    private OnTouchListener mTouchListener = new OnTouchListener() {
        private float touchX;
        private float touchY;
        private float startX;
        private float startY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchX = event.getX() + getLeft();
                    touchY = event.getY() + getTop();
                    startX = event.getRawX();
                    startY = event.getRawY();
                    isDragging = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) (event.getRawX() - startX);
                    int dy = (int) (event.getRawY() - startY);
                    if ((dx * dx + dy * dy) > mTouchSlop) {
                        isDragging = true;
                        mLp.x = (int) (event.getRawX() - touchX);
                        mLp.y = (int) (event.getRawY() - touchY);
                        mWm.updateViewLayout(FloatView.this, mLp);
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchX = touchY = 0.0F;
                    if (isDragging) {
                        reposition();
                        isDragging = false;
                        return true;
                    }
            }
            return false;
        }
    };


    private int dp2px(int dp) {
        return (int) (dp * density);
    }

    private void reposition() {
        if (mLp.x < (mRect.width() - getWidth()) / 2) {
            mLp.x = dp2px(5);
        } else {
            mLp.x = mRect.width() - dp2px(55);
        }
        if (mLp.y < mRect.top) {
            mLp.y = mRect.top;
        }
        mWm.updateViewLayout(this, mLp);
    }

    @Override
    public void show_type_list(List<TypeModel.Type> list) {

    }

    @Override
    public void show_pictures(List<PictureModel> list) {
        if (list != null) {
            urls = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                urls[i] = new urls().getUpload_picture() + list.get(i).getPicture_name();
            }
        }
    }

    @Override
    public void add_imgs_result(boolean b) {

    }

    @Override
    public void no_intent() {

    }

    @Override
    public void error_msg(String msg) {

    }

    @Override
    public void show_type_list2(List<? extends TotalModelMA.TypeModel.Type> list) {

    }

    @Override
    public void show_pictures2(List<ImageDatat.DataBean.LinkBean> list) {

    }
}