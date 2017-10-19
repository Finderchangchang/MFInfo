package gy.mf.info.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2017/8/16.
 */

public class DataModel<T> {
    public DataModel(String packageX) {
        this.packageX = packageX;
    }

    /**
     * package :
     */

    @SerializedName("package")
    private String packageX;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }
}
