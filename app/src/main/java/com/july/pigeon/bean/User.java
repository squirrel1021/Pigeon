package com.july.pigeon.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class User {
    private String id;

    private String nickName;

    private String mobile;

    private String icon;

    private String honor;

    private String breedAge;
    private List<String> iconUrl;

    public List<String> getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(List<String> iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getBreedAge() {
        return breedAge;
    }

    public void setBreedAge(String breedAge) {
        this.breedAge = breedAge;
    }
}
