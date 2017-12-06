package com.july.pigeon.bean;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class LaLoBean {


        private String id;
        private String name;
        private String speed;
        private String height;
        private String direction;
        private double longitude;//纬度
        private double latitude;//经度
        private String upTime;
        private String source;
        private String ringId;
        private String ringName;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }
        public String getSpeed() {
            return speed;
        }

        public void setHeight(String height) {
            this.height = height;
        }
        public String getHeight() {
            return height;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
        public String getDirection() {
            return direction;
        }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public void setSource(String source) {
            this.source = source;
        }
        public String getSource() {
            return source;
        }

        public void setRingId(String ringId) {
            this.ringId = ringId;
        }
        public String getRingId() {
            return ringId;
        }

        public void setRingName(String ringName) {
            this.ringName = ringName;
        }
        public String getRingName() {
            return ringName;
        }

    }

