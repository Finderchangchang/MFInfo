package gy.mf.info.control.transfer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//订单列表adapter
public abstract class MyBaseListAdapter<P extends Activity, T> extends BaseAdapter implements View.OnClickListener {
    public P mContext;
    public ArrayList<T> mList;

    public MyBaseListAdapter() {
    }

    public MyBaseListAdapter(P mContext, ArrayList<T> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    public MyBaseListAdapter(P mContext) {
        this.mContext = mContext;

    }

    public MyBaseListAdapter(ArrayList<T> mList) {
        this.mList = mList;

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = getViewHolder(parent, convertView, position);
        onBindViewHolder(viewHolder, position);

        return viewHolder.rootView;
    }


    public class ViewHolder {
        public View rootView;
        public TextView tv;
        public TextView division;


    }


    public ViewHolder getViewHolder(ViewGroup parent, View convertView, int position) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent, getItemViewType(position));
            viewHolder.rootView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//			 viewHolder.mRootView=convertView;
        }

        return viewHolder;
    }


    public abstract ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);


    public abstract void onBindViewHolder(ViewHolder holder, int position);


    public void viewSetClickListener(View view, int position) {

        view.setOnClickListener(this);
        view.setTag(position);
    }

    public T getObjFromPosition(int position) {

        return mList.get(position);

    }


}
