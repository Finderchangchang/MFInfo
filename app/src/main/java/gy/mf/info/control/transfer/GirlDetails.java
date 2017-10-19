package gy.mf.info.control.transfer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;

import gy.mf.info.R;
import gy.mf.info.control.check_img.CheckImgActivity;
import gy.mf.info.model.PictureModel;

//import com.cooliris.media.Gallery;

/**
 * Created by bing.ma on 2017/8/19.
 */

public class GirlDetails implements View.OnClickListener {
    private CheckImgActivity mCheckImgActivity;
    RelativeLayout activity_check_img;
    ImageView big_type_iv;
    ImageView fan_iv;
    ImageView bi_iv;
    ImageView jia_iv;
    ImageView shi_iv;
    ImageView set_iv;
    LinearLayout bottom_;
    ViewPager iv_viewpager;
    private ImagePagerAdapter mImagePagerAdapter;
    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mLinearLayout;

    public GirlDetails() {

    }

    public GirlDetails(CheckImgActivity checkImgActivity) {
        init(checkImgActivity);
    }

    private void init(CheckImgActivity checkImgActivity) {
        this.mCheckImgActivity = checkImgActivity;
        initView();
        initListener();
        initAdapter();
        renderData();
        refreshViewPager();
    }

    private void initAdapter() {
        mImagePagerAdapter = new ImagePagerAdapter(mCheckImgActivity);
        iv_viewpager.addOnPageChangeListener(mImagePagerAdapter);
    }

    private void initListener() {
        big_type_iv.setOnClickListener(this);
        fan_iv.setOnClickListener(this);
        bi_iv.setOnClickListener(this);
        set_iv.setOnClickListener(this);
    }

    private void initView() {
        activity_check_img = (RelativeLayout) findViewById(R.id.activity_check_img);
        big_type_iv = findImageViewById(R.id.big_type_iv);
        fan_iv = findImageViewById(R.id.fan_iv);
        bi_iv = findImageViewById(R.id.bi_iv);
        jia_iv = findImageViewById(R.id.jia_iv);
        shi_iv = findImageViewById(R.id.shi_iv);
        set_iv = findImageViewById(R.id.set_iv);
        bottom_ = (LinearLayout) findViewById(R.id.bottom_);
        iv_viewpager = (ViewPager) findViewById(R.id.iv_viewpager);
        initBottom();
    }

    private void initBottom() {
        mHorizontalScrollView = new HorizontalScrollView(mCheckImgActivity);
        mHorizontalScrollView.setBackgroundColor(ResourceUtils.getColor(mCheckImgActivity, R.color.main_bg));
        int screenWidth = ResourceUtils.getScreenWidth(mCheckImgActivity);
        screenWidth = (screenWidth / 3) * 2;
        int screenHigh = getDimenValues(R.dimen.dimen_66);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                screenWidth, screenHigh);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
        lp.leftMargin = getDimenValues(R.dimen.dimen_40);
//        lp.topMargin = getDimenValues(R.dimen.dimen_30);
        lp.bottomMargin = getDimenValues(R.dimen.dimen_2);
//        lp.addRule(RelativeLayout.ALIGN_LEFT, bottom_.getId());
//        lp.addRule(RelativeLayout.ALIGN_BOTTOM, bottom_.getId());
//        mHorizontalScrollView.setId();//设置这个View 的id
        mHorizontalScrollView.setLayoutParams(lp);//设置布局参数
        activity_check_img.addView(mHorizontalScrollView);//RelativeLayout添加子View
        HorizontalScrollView.LayoutParams layoutParams = new FrameLayout.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT, HorizontalScrollView.LayoutParams.MATCH_PARENT);
        mLinearLayout = new LinearLayout(mCheckImgActivity);
        mLinearLayout.setId(R.id.error_ll);
        mLinearLayout.setLayoutParams(layoutParams);
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        mHorizontalScrollView.addView(mLinearLayout);
        TextView textView = new TextView(mCheckImgActivity);
//        int screenHigh22 = getDimenValues(R.dimen.dimen_68);
        int screenWidth2 = getDimenValues(R.dimen.dimen_40);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                screenWidth2, screenHigh);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
//        lp.leftMargin = getDimenValues(R.dimen.dimen_40);
////        lp.topMargin = getDimenValues(R.dimen.dimen_30);
//        lp.bottomMargin = getDimenValues(R.dimen.dimen_2);
        lp2.addRule(RelativeLayout.ALIGN_TOP, mLinearLayout.getId());
        lp2.addRule(RelativeLayout.ALIGN_LEFT  , mLinearLayout.getId());
        lp2.bottomMargin = getDimenValues(R.dimen.dimen_2);
//        lp.addRule(RelativeLayout.ALIGN_BOTTOM, bottom_.getId());
//        mHorizontalScrollView.setId();//设置这个View 的id
        textView.setLayoutParams(lp2);//设置布局参数
        textView.setText("清除");
        textView.setTextSize(22);
        textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        textView.setBackgroundColor(ResourceUtils.getColor(mCheckImgActivity,R.color.__picker_text_120));
        activity_check_img.addView(textView);//RelativeLayout添加子View
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayout.removeAllViews();
            }
        });
    }

    private void renderData() {
        String carlogo = "carlogo";
        String[] carlogos = ResourceUtils.getAssetsFileNameList(mCheckImgActivity, carlogo);
        ArrayList<ImageInfo> list = new ArrayList();
        int i = 0;
        for (String path : carlogos) {
            i++;
            String imageName = carlogo + "/" + path;
            InputStream inputStream = ResourceUtils.openAssetsFile(mCheckImgActivity, imageName);
            ImageInfo imageInfo = new ImageInfo(inputStream);
            imageInfo.assetsPath = imageName;
            list.add(imageInfo);
            if (i == 5) {
                mImagePagerAdapter.setDataList(list);
                return;
            }
        }
        mImagePagerAdapter.setDataList(list);
    }

    private ImageView findImageViewById(int id) {
        return (ImageView) mCheckImgActivity.findViewById(id);
    }

    private View findViewById(int id) {
        return (View) mCheckImgActivity.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.big_type_iv:
                // 弹出联动列表
                //mCheckImgActivity.getOptionData2(mCheckImgActivity.getType_list2());
                break;
            case R.id.fan_iv:
                mCheckImgActivity.finish();
                break;
            case R.id.bi_iv:
                comparisonImage();
//                comparisonCurrentImage();
                break;
            case R.id.set_iv:
                showListAlertDialog();
                break;

        }
    }

    private void comparisonImage() {
        int currentItem = iv_viewpager.getCurrentItem();
        int screenWidth = getDimenValues(R.dimen.dimen_60);
        int screenHigh = mLinearLayout.getMeasuredHeight();
//        int screenHigh = ViewGroup.LayoutParams.MATCH_PARENT;
        View view = mImagePagerAdapter.constructionItemView(currentItem, screenWidth, screenHigh);
//        mHorizontalScrollView.removeAllViews();
//        view.
        boolean needAdd = isNeedAdd(view);
        if (needAdd) {
            mLinearLayout.addView(view);
        }

    }

    private boolean isNeedAdd(View view) {
        ImageInfo imageInfo2 = (ImageInfo) view.getTag();
        int childCount = mLinearLayout.getChildCount();
        if (childCount == 0) {
            return true;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = mLinearLayout.getChildAt(i);
            ImageInfo imageInfo1 = (ImageInfo) childAt.getTag();
            if (imageInfo1.isSame(imageInfo2)) {
                return false;
            }
        }
        return true;
    }

//    private void comparisonCurrentImage() {
//
//    }

    public void refreshViewPager(ArrayList<ImageInfo> imageInfoList) {
        mImagePagerAdapter.setDataList(imageInfoList);
        iv_viewpager.setAdapter(mImagePagerAdapter);
    }

    public void showListAlertDialog() {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("播放幻灯片");
        arrayList.add("浏览本地图片");
        arrayList.add("进入展示");
        arrayList.add("搜索");
        arrayList.add(" ");
        arrayList.add("  ");
        ListPopupWindow mListPopupWindow = new ListPopupWindow(mCheckImgActivity, arrayList);
        mListPopupWindow.showAtLocation();
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(mCheckImgActivity, PreviewActivity.class);
                        PreviewActivity.mImageInfoList = mImagePagerAdapter.getmDataList();
                        mCheckImgActivity.startActivity(intent);
                        break;
                    case 1:
//                        Intent intent2 = new Intent(mCheckImgActivity, ImageGalleryActivity.class);
//
//                        String[] images =mCheckImgActivity. getResources().getStringArray(R.array.unsplash_images);
//                        Bundle bundle = new Bundle();
//                        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, new ArrayList<>(Arrays.asList(images)));
//                        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Unsplash Images");
//                        intent2.putExtras(bundle);
//
//                        mCheckImgActivity.startActivity(intent2);
//                        Intent intent3 = new Intent(mCheckImgActivity, AlbumActivity.class);
//                        intent3.putExtra(AlbumActivity.ARG_MODE, AlbumActivity.MODE_PORTRAIT);
//                        mCheckImgActivity.startActivity(intent3);
//                        startActivityForResult(intent3, INTENT_CODE);
//带配置
//
// GalleryFinal.openGallerySingle(1000, functionConfig, mOnHanlderResultCallback);
                        Intent intent3 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(intent3, IMAGE);
                        mCheckImgActivity.startActivity(intent3);
                        break;
                }

//
//


            }
        });
    }


    public void refreshViewPager() {
        iv_viewpager.setAdapter(mImagePagerAdapter);
        mImagePagerAdapter.notifyDataSetChanged();
    }

    public int getDimenValues(int ind) {
        return ResourceUtils.getDimenValues(mCheckImgActivity, ind);
    }

    public void show_pictures(ArrayList<PictureModel> list) {
        ArrayList<ImageInfo> imageInfoListFromServer = ImageInfoDataControl.getImageInfoListFromServer(list);
        if (imageInfoListFromServer != null) {
            mImagePagerAdapter.setDataList(imageInfoListFromServer);

        } else {
            renderData();
        }
        mImagePagerAdapter.notifyDataSetChanged();
    }

    public void show_pictures2(@NotNull ArrayList<ImageDatat.DataBean.LinkBean> list) {
        ArrayList<ImageInfo> imageInfoListFromServer = ImageInfoDataControl.getImageInfoListFromServer2(list);
        if (CollectionUtils.noEmpty(imageInfoListFromServer)) {
            mImagePagerAdapter.setDataList(imageInfoListFromServer);
            iv_viewpager.setAdapter(mImagePagerAdapter);
////            mImagePagerAdapter.notifyDataSetChanged();
//            iv_viewpager.setCurrentItem(0);
        } else {
            renderData();
        }
        mImagePagerAdapter.notifyDataSetChanged();

    }


}
