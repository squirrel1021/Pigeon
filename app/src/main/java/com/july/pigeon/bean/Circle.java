package com.july.pigeon.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class Circle implements Serializable {
    private String username;
    private String themeInfo;
    private List<String> imageurl;
    private List<String> imgUrls;
    private String fdId;

    private String fdName;

    private String fdCreateTime;

    private String memberId;

    private String memberName;

    private String fdContent;

    private String fdPublishTime;

    private List<String> fdImgUrl;

    private int dd;

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

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

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(String fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getFdContent() {
        return fdContent;
    }

    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    public String getFdPublishTime() {
        return fdPublishTime;
    }

    public void setFdPublishTime(String fdPublishTime) {
        this.fdPublishTime = fdPublishTime;
    }

    public List<String> getFdImgUrl() {
        return fdImgUrl;
    }

    public void setFdImgUrl(List<String> fdImgUrl) {
        this.fdImgUrl = fdImgUrl;
    }
}
