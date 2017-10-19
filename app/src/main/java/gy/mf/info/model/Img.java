package gy.mf.info.model;

/**
 * Created by Finder丶畅畅 on 2017/9/29 23:47
 * QQ群481606175
 */

public class Img {
    public Img(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int id = 0;
    String name = "";
}
