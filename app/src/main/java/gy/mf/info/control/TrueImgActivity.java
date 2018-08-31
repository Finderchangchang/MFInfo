package gy.mf.info.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import gy.mf.info.R;

public class TrueImgActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_img);
        iv= (ImageView) findViewById(R.id.iv);
        byte[] bytes= getIntent().getByteArrayExtra("bt");
        Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        iv.setImageBitmap(bmp);
    }
}
