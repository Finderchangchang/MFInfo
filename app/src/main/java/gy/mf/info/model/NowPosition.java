package gy.mf.info.model;

/**
 * Created by Finder丶畅畅 on 2017/10/10 23:17
 * QQ群481606175
 */

public class NowPosition {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * province : 河北省
     * city : 保定市
     * adcode : 130600
     * rectangle : 115.2708077,38.7220983;115.6799734,39.01996723
     */

    private String status;
    private String info;
    private String infocode;
    private String province;
    private String city;
    private String adcode;
    private String rectangle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }
}
