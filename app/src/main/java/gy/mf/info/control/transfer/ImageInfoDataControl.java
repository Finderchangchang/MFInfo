package gy.mf.info.control.transfer;

import java.util.ArrayList;

import gy.mf.info.model.PictureModel;

/**
 * Created by bing.ma on 2017/8/22.
 */

public class ImageInfoDataControl {
    //    override fun show_pictures(list: MutableList<PictureModel>?) {
//
//    }
    public static ArrayList<ImageInfo> getImageInfoListFromServer(ArrayList<PictureModel> list) {
        if (CollectionUtils.noEmpty(list)) {
            ArrayList<ImageInfo> list1 = new ArrayList();
            for (PictureModel pictureModel : list) {
                ImageInfo imageInfo = new ImageInfo(pictureModel);
                list1.add(imageInfo);
            }
            return list1;
        }
        return null;
    }

    public static ArrayList<ImageInfo> getImageInfoListFromServer2(ArrayList<ImageDatat.DataBean.LinkBean> list) {
        if (CollectionUtils.noEmpty(list)) {
            ArrayList<ImageInfo> list1 = new ArrayList();
            for (ImageDatat.DataBean.LinkBean linkBean : list) {
                ImageInfo imageInfo = new ImageInfo(linkBean);
                list1.add(imageInfo);
            }
            return list1;
        }
        return null;
    }

}
