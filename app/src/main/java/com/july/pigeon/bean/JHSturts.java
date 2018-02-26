package com.july.pigeon.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class JHSturts {

    private String fdId;
    private String fdName;
    private String fdCreateTime;
    private String ringId;
    private String memberId;
    private boolean status;
    private String startTime;
    private String endTime;

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



    public String getRingId() {
        return ringId;
    }

    public void setRingId(String ringId) {
        this.ringId = ringId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(String fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
