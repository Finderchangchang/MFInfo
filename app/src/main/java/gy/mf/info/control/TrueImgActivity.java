package gy.mf.info.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

import gy.mf.info.R;

public class TrueImgActivity extends AppCompatActivity {
    ImageView iv;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_img);
        iv= (ImageView) findViewById(R.id.iv);
//        byte[] bytes= getIntent().getByteArrayExtra("bt");
//        Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        url=getIntent().getStringExtra("bt");
        iv.setImageBitmap(getBitmapFromLocal("demo.png"));
    }
    /**
     * 从本地SD卡获取缓存的bitmap
     */
    public Bitmap getBitmapFromLocal(String fileName) {
        try {
            File file = new File(url, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
