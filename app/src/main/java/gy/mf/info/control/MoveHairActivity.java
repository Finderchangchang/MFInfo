package gy.mf.info.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

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
                startActivity(new Intent(MoveHairActivity.this, TrueImgActivity.class).putExtra("bt", bytes));
            }
        });
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
