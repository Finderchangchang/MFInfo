package gy.mf.info.control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import gy.mf.info.R;
import gy.mf.info.util.urls;

/**
 * Created by takusemba on 2017/08/03.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private List<String> titles;
    Context mContext;

    public HorizontalAdapter(List<String> model, Context context) {
        this.titles = model;
        this.mContext = context;
    }

    ViewHolder holder;
    int now_position;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_item, viewGroup, false);
        //holder=new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String title = titles.get(position);
        Glide.with(mContext)
                .load(new urls().getUpload_picture() + title)
                .error(R.mipmap.defult_user)
                .into(holder.title);
        now_position = position;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != null) {
                    click.click(position);

                }
            }
        });
    }

    public void setClick(final Click click) {
        this.click = click;
//        if (click != null) {
//            holder.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    click.click(now_position);
//                }
//            });
//        }
        String a = "";
    }

    Click click;

    interface DelClick {
        void click(int position);
    }

    interface Click {
        void click(int position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView title;
        RelativeLayout rl;

        ViewHolder(final View itemView) {
            super(itemView);
            this.title = (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
            this.rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }
}