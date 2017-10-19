package gy.mf.info.control.transfer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import gy.mf.info.R;

public class ListPopupWindow extends BasePopupWindow<Activity, String> {

    private ListView mListView;

    public ListPopupWindow() {

    }

    public ListPopupWindow(Activity mPresenter) {
        super(mPresenter);
    }

    public ListPopupWindow(Activity mPresenter, ArrayList<String> dataList) {
        super(mPresenter, dataList);
    }


    public void initWindow() {

        mPopupWindow = new PopupWindow(mRootView, 590,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT, true);
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.update();
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(this);
    }

    public void showAtLocation(int height) {
        // TODO Auto-generated method stub
        isShow = true;
        mPopupWindow.showAtLocation(mRootView, Gravity.RIGHT | Gravity.BOTTOM, 100, 200+height);
    }

    public void initListener() {

    }

    @Override
    public void initView(View rootView) {
        mListView = (ListView) findViewById(R.id.mlistview);
        PopupListAdapter popupListAdapter = new PopupListAdapter(mContext, mDataList);
        mListView.setAdapter(popupListAdapter);
    }


    public int getRootLayoutID() {
        return R.layout.popupwindow_list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mListView.setOnItemClickListener(onItemClickListener);//
    }

}


