package gy.mf.info.control.transfer;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import gy.mf.info.R;

/**
 * Created by bing.ma on 2017/8/21.
 */

public class PreviewPagerAdapter extends ImagePagerAdapter {
    public PreviewPagerAdapter() {
        super();
    }

    public PreviewPagerAdapter(Context context) {
        super(context);
    }

    public PreviewPagerAdapter(ArrayList<ImageInfo> dataList) {
        super(dataList);
    }

    public PreviewPagerAdapter(Context context, ArrayList<ImageInfo> dataList) {
        super(context, dataList);

    }

    @Override
    public View getItemView(int position) {
        int dimenValues = ResourceUtils.getDimenValues(mContext, R.dimen.dimen_40);
        int screenWidth = ResourceUtils.getScreenWidth(mContext);
        int screenHeight = ResourceUtils.getScreenHeight(mContext);
//        int screenHight = screenWidth - dimenValues;
        ImageInfo dataFromPosition = getDataFromPosition(position);
        View view = constructionItemView(dataFromPosition, screenWidth, screenHeight);
        return view;
    }
}


