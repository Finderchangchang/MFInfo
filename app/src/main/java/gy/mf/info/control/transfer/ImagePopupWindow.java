package gy.mf.info.control.transfer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gy.mf.info.R;
import gy.mf.info.control.transfer.localphoto.ZoomImageView;
import gy.mf.info.util.urls;

public class ImagePopupWindow extends BasePopupWindow<Activity, String> {

    //    private ListView mListViewView;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    public TextView tv;
    private ImageView imageView1;

    public ImagePopupWindow() {

    }

    public ImagePopupWindow(Activity mPresenter) {
        super(mPresenter);
    }

    public ImagePopupWindow(Activity mPresenter, ImageInfo imageInfo) {
        super(mPresenter, imageInfo);
    }


    public void initWindow() {

        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT, true);
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.update();
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(this);
    }

    public void showAtLocation() {
        // TODO Auto-generated method stub
        isShow = true;
        mPopupWindow.showAtLocation(mRootView, Gravity.RIGHT | Gravity.BOTTOM, 100, 290);
    }

    public void initListener() {

    }

    @Override
    public void initView(View rootView) {
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        int screenWidth = ResourceUtils.getScreenWidth(mContext);

        int screenHigh = (screenWidth / 3) * 2;
//        int screenHigh = getDimenValues(R.dimen.dimen_66);

        constructionItemView(imageInfo, screenWidth, screenHigh);
        TextView textView = new TextView(mContext);

        RelativeLayout.LayoutParams lpp = new RelativeLayout.LayoutParams(
                screenWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lpp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        lpp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
//        lp.leftMargin = getDimenValues(R.dimen.dimen_40);
////        lp.topMargin = getDimenValues(R.dimen.dimen_30);
        lpp.bottomMargin = ResourceUtils.getDimenValues(mContext, R.dimen.dimen_40);
//        lp.addRule(RelativeLayout.ALIGN_LEFT, bottom_.getId());
        lpp.addRule(RelativeLayout.ALIGN_BOTTOM, imageView1.getId());
//        mHorizontalScrollView.setId();//设置这个View 的id
        textView.setTextColor(ResourceUtils.getColor(mContext, R.color.white));
        textView.setPadding(25, 10, 10, 10);
        textView.setLayoutParams(lpp);//设置布局参数
        textView.setText(imageInfo.imagePath);
        relativeLayout.addView(textView);//RelativeLayout添加子View
    }


    public int getRootLayoutID() {
        return R.layout.popupwindow_image;
    }

//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//        mListView.setOnItemClickListener(onItemClickListener);//
//    }


    public View constructionItemView(ImageInfo dataFromPosition, int screenWidth, int screenHight) {
//        Bitmap bitmap = dataFromPosition.toBitmap(mContext, screenWidth, screenHight);


        imageView1 = new ZoomImageView(mContext) {

            @Override
            public void onClick2() {

            }
        };
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);//与父容器的左侧对齐
//        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
//        lp.leftMargin = getDimenValues(R.dimen.dimen_40);
////        lp.topMargin = getDimenValues(R.dimen.dimen_30);
//        lp.bottomMargin = getDimenValues(R.dimen.dimen_2);
//        lp.addRule(RelativeLayout.ALIGN_LEFT, bottom_.getId());
//        lp.addRule(RelativeLayout.ALIGN_BOTTOM, bottom_.getId());
//        mHorizontalScrollView.setId();//设置这个View 的id
        imageView1.setLayoutParams(lp);//设置布局参数
//        imageView1.setId(R.id.img_iv);
        relativeLayout.addView(imageView1);//RelativeLayout添加子View

        String upload_picture = new urls().getUpload_picture();
        String string = upload_picture + dataFromPosition.name;
        Glide.with(mContext)
                .load(string)
                .override(screenWidth, screenHight)
                .into(imageView1);
        return imageView1;
    }
}


