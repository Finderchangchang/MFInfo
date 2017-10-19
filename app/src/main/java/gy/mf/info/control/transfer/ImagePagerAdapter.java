package gy.mf.info.control.transfer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gy.mf.info.R;
import gy.mf.info.control.check_img.CheckImgActivity;
import gy.mf.info.control.transfer.localphoto.ZoomImageView;
import gy.mf.info.util.urls;
//import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by bing.ma on 2017/8/20.
 */

public class ImagePagerAdapter extends MyBasePagerAdapter<ImageInfo> implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public int requestCount = 0;

    public ImagePagerAdapter() {
        super();
        init();
    }

    private void init() {

    }

    public ImagePagerAdapter(Context context) {
        super(context);
        init();
    }

    public ImagePagerAdapter(ArrayList<ImageInfo> dataList) {
        super(dataList);
        init();
    }

    public ImagePagerAdapter(Context context, ArrayList<ImageInfo> dataList) {
        super(context, dataList);
        init();
    }

    @Override
    public View getItemView(int position) {
        int dimenValues = ResourceUtils.getDimenValues(mContext, R.dimen.dimen_40);
        int screenWidth = ResourceUtils.getScreenWidth(mContext);
        int screenHight = screenWidth - dimenValues;
        ImageInfo dataFromPosition = getDataFromPosition(position);
        dataFromPosition.position=position;
        View view = constructionItemView(dataFromPosition, screenWidth, screenHight);
        view.setTag(position);
        return view;
    }

    public View constructionItemView(final ImageInfo dataFromPosition, int screenWidth, int screenHight) {
//        Bitmap bitmap = dataFromPosition.toBitmap(mContext, screenWidth, screenHight);
        ZoomImageView imageView = new ZoomImageView(mContext){

            @Override
            public void onClick2() {

                ImageInfo imageInfo = getmDataList().get(dataFromPosition.position);
                ImagePopupWindow ImagePopupWindow = new ImagePopupWindow((Activity) mContext,imageInfo) ;
                ImagePopupWindow.showAtLocation();
            }
        };
//        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                screenWidth, screenHight);
        imageView.setLayoutParams(layoutParams);
//        imageView.setImage(bitmap);
//        imageView.setImageBitmap(bitmap);
        String upload_picture = new urls().getUpload_picture();
        String string = upload_picture + dataFromPosition.name;
        Glide.with(mContext)
                .load(string)
                .override(screenWidth, screenHight)
                .into(imageView);

        return imageView;
    }

    public View constructionItemView(int position, int screenWidth, int screenHight) {
        ImageInfo dataFromPosition = getDataFromPosition(position);
        View view = constructionItemView(dataFromPosition, screenWidth, screenHight);
        view.setTag(dataFromPosition);
        return view;
    }

    @Override
    public void onClick(View v) {
//        int tag = (int) v.getTag();
//        ImageInfo imageInfo = getmDataList().get(tag);
//        ImagePopupWindow ImagePopupWindow = new ImagePopupWindow(mContext,imageInfo) ;
//        ImagePopupWindow.showAtLocation();
    }

    class Offset {
        public int left;
        public int right;

        public Offset() {

        }

        public Offset(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public boolean isKimaru() {
            if (left != 0) {
                return false;
            }
            if (right != 0) {
                return false;
            }
            return true;
        }
    }

    ArrayList<Offset> offsetList = new ArrayList();

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ImageInfo imageInfo = getmDataList().get(position);
        if (position == (getmDataList().size() - 1)) {
            boolean kimaru = isKimaru((int) positionOffset, positionOffsetPixels);
            if (kimaru) {

                loadMore(imageInfo);
                return;
            }
        } else if (position == 0) {
            boolean kimaru = isKimaru((int) positionOffset, positionOffsetPixels);
            if (kimaru) {
                rollback(imageInfo);
                return;
            }
        }
        if (!new Offset((int) positionOffset, positionOffsetPixels).isKimaru()) {
            offsetList.clear();
        }
        Log.i("iji", "positionOffset:" + positionOffset + ":" + positionOffsetPixels + "ii" + positionOffsetPixels);
    }

    private void rollback(ImageInfo imageInfo) {
        Toast.makeText(mContext, "回退", Toast.LENGTH_LONG).show();
        ((CheckImgActivity) mContext).rollback2(imageInfo.setId, 0, 0);
    }

    private void loadMore(ImageInfo imageInfo) {
        Toast.makeText(mContext, "加载更多", Toast.LENGTH_LONG).show();
        if (mContext instanceof CheckImgActivity) {
            ((CheckImgActivity) mContext).requestPhoto2(imageInfo.setId, 0, 0);
        }
    }

    private boolean isKimaru(int positionOffset, int positionOffsetPixels) {
        Offset Offset = new Offset(positionOffset, positionOffsetPixels);
        offsetList.add(Offset);
        if (offsetList.size() > 7) {
            for (int i = offsetList.size() - 1; i > offsetList.size() - 8; i--) {
                ImagePagerAdapter.Offset offset = offsetList.get(i);
                if (!offset.isKimaru()) {
                    offsetList.clear();
                    return false;
                }
            }
            offsetList.clear();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("ghjji", state + "JJI");

    }
}
