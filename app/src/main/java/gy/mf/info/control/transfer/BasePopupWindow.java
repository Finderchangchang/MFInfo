package gy.mf.info.control.transfer;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;

public abstract class BasePopupWindow<T extends Activity, E> implements PopupWindow.OnDismissListener {
    public ImageInfo imageInfo;
    public PopupWindow mPopupWindow;
    public View mRootView;
    public T mContext;

    public boolean isShow = false;
    public boolean isExternalCancel = true;
    public ArrayList<E> mDataList;

    public BasePopupWindow(T mPresenter, ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
        init(mPresenter);
    }

    public BasePopupWindow() {

    }


    /**
     * @param mPresenter 一定要传入一个引子 用于初始化控件
     */

    public BasePopupWindow(T mPresenter) {

        init(mPresenter);
    }

    public BasePopupWindow(T mPresenter, ArrayList<E> dataList) {
        this.mDataList = dataList;
        init(mPresenter);
    }

    public void initWindow() {

        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.update();
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(this);
    }

    public void showAtLocation() {
        // TODO Auto-generated method stub
        isShow = true;
        mPopupWindow.showAtLocation(mRootView, Gravity.NO_GRAVITY, 0, 0);
    }


    public void dismiss() {
        // TODO Auto-generated method stub
        isShow = false;
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
        mPopupWindow.dismiss();

    }


    public void init(T mContext) {
        this.mContext = mContext;
        initRootView();
        initView(mRootView);
        initWindow();
        initListener();
        full(true);

    }


    public void initListener() {

    }


    public abstract void initView(View rootView);

    public LayoutInflater getInflater() {
        return LayoutInflater.from(mContext);
    }


    public View initRootView() {
        int id = getRootLayoutID();
        LayoutInflater inflater = getInflater();
        mRootView = inflater.inflate(
                id, null);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExternalCancel) {
                    if (mPopupWindow != null) {
                        dismiss();
                    }
                }

            }
        });
        return mRootView;
    }


    public abstract int getRootLayoutID();


    public View findViewById(int id) {
        return mRootView.findViewById(id);
    }


    @Override
    public void onDismiss() {
        this.dismiss();
    }


    public void full(boolean enable) {
        if (enable) {//隐藏状态栏
            WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            mContext.getWindow().setAttributes(lp);
        } else {//显示状态栏
            WindowManager.LayoutParams attr = mContext.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mContext.getWindow().setAttributes(attr);
        }
        //mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        mRootView.setBackgroundColor((int) ResourceGainUtils.getColor(mContext, R.color.colorBlue));
    }
}


