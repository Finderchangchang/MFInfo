package gy.mf.info.control.transfer;


import java.io.File;

/**
 * Created by bing.ma on 2017/4/20.
 */

public class RunErrorSaveControl {
    public static void saveRunErrot(String string, Exception exception, String file, String name) {
        File curTimeFile = FileUtils2.getCurTimeFile(file, name);
        String neiRong = string + StringUtils.LineLeed + exception.toString();
        StreamTools.writeStr2Stream(neiRong, curTimeFile);
        LogUtils2.i(neiRong);
    }

    public static void saveRunErrot(String string, String file, String name) {
        File curTimeFile = FileUtils2.getCurTimeFile(file, name);
        StreamTools.writeStr2Stream(string, curTimeFile);
        LogUtils2.i(string);
    }





    public static void showI(String str) {
        LogUtils2.i(str);
    }
}
