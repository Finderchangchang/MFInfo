package gy.mf.info.control;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gy.mf.info.R;
import gy.mf.info.configs.LoadingView;
import gy.mf.info.method.CommonAdapter;
import gy.mf.info.method.CommonViewHolder;
import okhttp3.Call;
import okhttp3.Response;

public class MoveHairActivity extends AppCompatActivity {
    ImageView bg_img;
    ImageView hair_img;
    Button next_btn;
    Button hl_btn;
    GridView gv;
    String url;
    CommonAdapter<String> commonAdapter;
    List<String> list;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_hair);
        url=getIntent().getStringExtra("url");
        gv= (GridView) findViewById(R.id.grid);
        commonAdapter=new CommonAdapter<String>(this,list,R.layout.item_hair) {
            @Override
            public void convert(CommonViewHolder holder, String s, int position) {
                holder.setGImage(R.id.iv,s);
            }
        };
        gv.setAdapter(commonAdapter);
        hair_img = (ImageView) findViewById(R.id.hair_img);
        bg_img = (ImageView) findViewById(R.id.bg_img);

        bg_img.setImageBitmap(getBitmapFromLocal("demo.png"));

        next_btn = (Button) findViewById(R.id.next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hair_img.setDrawingCacheEnabled(true);
                Bitmap hair_bitmap = Bitmap.createBitmap(hair_img.getDrawingCache());
                hair_img.setDrawingCacheEnabled(false);
                bg_img.setDrawingCacheEnabled(true);Bitmap bg_bitmap = Bitmap.createBitmap(bg_img.getDrawingCache());
                bg_img.setDrawingCacheEnabled(false);

                Bitmap bt = mergeBitmap(bg_bitmap, hair_bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bt.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();

                Bundle b = new Bundle();
                b.putByteArray("bitmap", bytes);
                if (save_img(FILE_PATH, bt)) {
                    startActivity(new Intent(MoveHairActivity.this, TrueImgActivity.class).putExtra("bt", FILE_PATH));
                }
            }
        });
    }

    String FILE_PATH;

    boolean save_img(String url, Bitmap bitmap) {
        try {
            FILE_PATH = getCacheDir() + "/cache/pics";
            // 创建文件流，指向该路径，文件名叫做fileName
            File file = new File(FILE_PATH, "demo.png");
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs();// 创建文件夹
            }
            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    new FileOutputStream(file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;

        try {

            if (bitmap != null) {

                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;

    }



    //把bitmap转换成String
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        Log.d("d", "压缩后的大小=" + b.length);
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


}
