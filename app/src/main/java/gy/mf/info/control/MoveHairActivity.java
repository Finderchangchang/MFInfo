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
import android.widget.ImageView;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gy.mf.info.R;
import gy.mf.info.configs.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

public class MoveHairActivity extends AppCompatActivity {
    ImageView bg_img;
    ImageView hair_img;
    Button next_btn;
    Button hl_btn;
    ImageConfig imageConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_hair);
        imageConfig = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.blue))
                .titleBgColor(getResources().getColor(R.color.blue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                //.showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();


        hair_img = (ImageView) findViewById(R.id.hair_img);
        bg_img = (ImageView) findViewById(R.id.bg_img);
        next_btn = (Button) findViewById(R.id.next_btn);
        hl_btn = (Button) findViewById(R.id.hl_btn);
        hl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector.open(MoveHairActivity.this, imageConfig);   // 开启图片选择器
            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hair_img.setDrawingCacheEnabled(true);
                Bitmap hair_bitmap = Bitmap.createBitmap(hair_img.getDrawingCache());
                hair_img.setDrawingCacheEnabled(false);
                bg_img.setDrawingCacheEnabled(true);
                Bitmap bg_bitmap = Bitmap.createBitmap(bg_img.getDrawingCache());
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
    DialogFragment dialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Get Image Path List
            List<String> imagesPath = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            if (imagesPath != null && imagesPath.size() > 0) {
                dialog=new CircleDialog.Builder()
                        .setProgressText("登录中...")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
                        .show(getSupportFragmentManager());
                String url = imagesPath.get(0);
                Bitmap bmp = BitmapFactory.decodeFile(url);
                bg_img.setDrawingCacheEnabled(true);
                Bitmap bmp2 = Bitmap.createBitmap(bg_img.getDrawingCache());
                bg_img.setDrawingCacheEnabled(false);

                String i1 = bitmapToBase64(bmp);
                String i2 = bitmapToBase64(bmp2);
                OkGo.post("http://47.95.210.218:81/upload")
                        .params("head", i2)
                        .params("face", i1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                bg_img.setImageBitmap(base64ToBitmap(s));
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                String mye = e.getMessage();
                                dialog.dismiss();
                            }
                        });
            }
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

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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
