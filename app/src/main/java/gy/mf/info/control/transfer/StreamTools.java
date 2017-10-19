package gy.mf.info.control.transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bing.ma on 2017/4/20.
 */

public class StreamTools {


    public static void writeStr2Stream(String str , File file) {
        FileOutputStream fos1=null;
        try {
            fos1 = new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            fos1.write(str.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos1.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
