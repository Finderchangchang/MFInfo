package gy.mf.info.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import gy.mf.info.control.main.MainActivity;

/**
 * Created by Finder丶畅畅 on 2017/8/30 14:57
 * QQ群481606175
 */

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}