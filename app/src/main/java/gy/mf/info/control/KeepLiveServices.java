package gy.mf.info.control;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.List;

import gy.mf.info.R;
import gy.mf.info.control.main.MainActivity;

/**
 * Created by 龚浩 on 2017/7/27.
 */

public class KeepLiveServices extends Service {
    private Notification notify;
    public static final int ID = 12345;
    private boolean isPhone = false;
    private int count = 0;
    private boolean result = true;
    private static final String TAG = "KeepLiveServices";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendNotify();
        initLocalCache();
    }

    private void initLocalCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }

    private void sendNotify() {
        //设置成前台服务
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notify = new Notification.Builder(this)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(getResources().getString(R.string.app_name))
                    .setSmallIcon(R.drawable.ic_arrow_back_black_24dp)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        startForeground(ID, notify);
    }

    //注销EventBus
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent localIntent = new Intent(this, KeepLiveServices.class);
        this.startService(localIntent);
    }
}
