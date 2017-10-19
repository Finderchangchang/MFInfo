package gy.mf.info.control.check_img;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gy.mf.info.R;
import gy.mf.info.util.urls;

public class HorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<String> mDatas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.activity_index_gallery_item, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position >= 0 && mDatas.size() > position) {
            String model = mDatas.get(position);
            if (mDatas.size() > position) {
                String url = new urls().getUpload_picture() + model;
                Glide.with(mContext)
                        .load(url)
                        .error(R.mipmap.defult_user)
                        .into(viewHolder.mImg);
                //viewHolder.mText.setText("some info ");
            }
        }
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double old_y = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        old_y = event.getY();
                    }
                    case MotionEvent.ACTION_UP: {
                        if (old_y > event.getY()) {
                            Log.i("shanchu", "ccc");
                        } else {
                            Log.i("shanchu","-[---ccc");
                        }
                    }
                }
                return false;
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
    }

}