package gy.mf.info.control.transfer.exception;

import android.app.ActivityManager;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by bing.ma on 2017/7/7.
 */

public class ForceKillProgress {

    public static void forceStopProgress(Context context, String pkgName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        try {
            Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(am, pkgName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}