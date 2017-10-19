package gy.mf.info.control.transfer;


import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by bing.ma on 2017/4/20.
 */

public class FileUtils2 {
    public static final String txt_=".txt";
    public static void createDirectory(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        try {
            // 获得文件对象
            File f = new File(path);
            if (!f.exists()) {
                // 如果路径不存在,则创建
                f.mkdirs();
            }
        } catch (Exception e) {
            // log.i("创建目录错误.path=" + path, e);
            // Log.e(tag, msg) ;
            throw e;
        }
    }
    public static File createFile(File path) {
        if (path == null) {
            return null;
        }
        // 获得文件对象

        // 如果路径不存在,则创建
        if (!path.getParentFile().exists()) {
            path.getParentFile().mkdirs();
        }
        if (!path.exists()) {// 判断文件夹是否创建，没有创建则创建新文件夹
            try {
                path.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return path;
    }

    /**
     * 新建文件.
     *
     * @param path
     *            文件路径
     * @throws Exception
     */
    public static File createFile(String path) {
        File f;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        // 获得文件对象
        f = new File(path);
        if (f.exists()) {
            return f;
        }
        // 如果路径不存在,则创建
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        if (!f.exists()) {// 判断文件夹是否创建，没有创建则创建新文件夹
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return f;
    }

public static File getCurTimeFile(String fileT,String name_) {
    HashMap<String, String> curTimeList = TimeUtils.getCurTimeList();
    String front = curTimeList.get(TimeUtils.front_);
    String name = curTimeList.get(TimeUtils.later_);
    String fileStr=getRootPath( ) +front+fileT+"/"+name+name_+txt_;
    return createFile(fileStr) ;
}

public static String getRootPath( ) {
    String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    String rootPath = SDCARD_ROOT+"/aaabbb/" ;
    return rootPath ;
}

}
