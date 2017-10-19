package gy.mf.info.control.transfer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gy.mf.info.R;

/**
 * Created by bing.ma on 2017/8/20.
 */

public class PopupListAdapter extends MyBaseListAdapter<Activity, String> {
    public PopupListAdapter() {
        super();
    }

    public PopupListAdapter(Activity mContext, ArrayList<String> mList) {
        super(mContext, mList);
    }

    public PopupListAdapter(Activity mContext) {
        super(mContext);
    }

    public PopupListAdapter(ArrayList<String> mList) {
        super(mList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(
                R.layout.popup_list_item, null);
        ViewHolder listViewHolder = new ViewHolder();
        listViewHolder.rootView = convertView;
        listViewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
        listViewHolder.division = (TextView) convertView.findViewById(R.id.division);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String objFromPosition = getObjFromPosition(position);
        holder.tv.setText(objFromPosition);
        if (position == mList.size() - 1) {
            holder.division.setVisibility(View.INVISIBLE);
        } else {
            holder.division.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

    }


}
