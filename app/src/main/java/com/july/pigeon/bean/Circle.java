package com.july.pigeon.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class Circle {
    private String username;
    private String themeInfo;
    private List<String> imageurl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThemeInfo() {
        return themeInfo;
    }

    public void setThemeInfo(String themeInfo) {
        this.themeInfo = themeInfo;
    }

    public List<String> getImageurl() {
        return imageurl;
    }

    public void setImageurl(List<String> imageurl) {
        this.imageurl = imageurl;
    }
}
