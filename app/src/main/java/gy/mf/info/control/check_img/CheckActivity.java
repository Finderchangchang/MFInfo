package gy.mf.info.control.check_img;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import gy.mf.info.R;
import gy.mf.info.base.BaseActivity;
import gy.mf.info.control.transfer.ImageDatat;
import gy.mf.info.control.transfer.TotalModelMA;
import gy.mf.info.model.PictureModel;
import gy.mf.info.model.TypeModel;

public class CheckActivity extends BaseActivity implements ICheckImg {
    CheckImgListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_img);
        listener = new CheckImgListener(this);
    }

    @Override
    public void initViews() {
        listener.getTypes("1");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void show_type_list(List<TypeModel.Type> list) {

    }

    @Override
    public void add_imgs_result(boolean b) {

    }

    @Override
    public void show_pictures(@Nullable List<PictureModel> list) {

    }

    @Override
    public void show_type_list2(List<? extends TotalModelMA.TypeModel.Type> list) {

    }

    @Override
    public void show_pictures2(List<ImageDatat.DataBean.LinkBean> list) {

    }
}
