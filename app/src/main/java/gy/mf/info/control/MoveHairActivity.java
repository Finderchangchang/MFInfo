package gy.mf.info.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import gy.mf.info.R;

public class MoveHairActivity extends AppCompatActivity {
    ImageView bg_img;
    ImageView hair_img;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_hair);
        hair_img = (ImageView) findViewById(R.id.hair_img);
        bg_img = (ImageView) findViewById(R.id.bg_img);
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hair_img.setDrawingCacheEnabled(true);
                Bitmap hair_bitmap = Bitmap.createBitmap(hair_img.getDrawingCache());
                hair_img.setDrawingCacheEnabled(false);
                bg_img.setDrawingCacheEnabled(true);
                Bitmap bg_bitmap = Bitmap.createBitmap(bg_img.getDrawingCache());
                bg_img.setDrawingCacheEnabled(false);

                Bitmap bt = mergeBitmap(bg_bitmap,hair_bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bt.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes=baos.toByteArray();

                Bundle b = new Bundle();
                b.putByteArray("bitmap", bytes);
                save_img(FILE_PATH,bt);
                startActivity(new Intent(MoveHairActivity.this, TrueImgActivity.class).putExtra("bt", FILE_PATH));
            }
        });
    }
    String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics";
    void save_img(String url,Bitmap bitmap){
        try {
            // 创建文件流，指向该路径，文件名叫做fileName
            File file = new File(FILE_PATH, "demo.png");
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs();// 创建文件夹
            }
            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }
}
