package com.intgo.reword.entity;

import java.util.List;

public class RegisterEntity {
    private DataEntity data;
    private String token;
    private List<couponEntity> couponList;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<couponEntity> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<couponEntity> couponList) {
        this.couponList = couponList;
    }

    private static class DataEntity{
        private int n;
        private int ok;

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public int getOk() {
            return ok;
        }

        public void setOk(int ok) {
            this.ok = ok;
        }

    }

    private static class couponEntity {
        private int condition;
        private boolean isUsed;
        private boolean lock;
        private int parentSn;
        private int price;
        private int sn;
        private boolean timeOut;
        private String title;

        public int getCondition() {
            return condition;
        }

        public void setCondition(int condition) {
            this.condition = condition;
        }

        public boolean isUsed() {
            return isUsed;
        }

        public void setUsed(boolean used) {
            isUsed = used;
        }

        public boolean isLock() {
            return lock;
        }

        public void setLock(boolean lock) {
            this.lock = lock;
        }

        public int getParentSn() {
            return parentSn;
        }

        public void setParentSn(int parentSn) {
            this.parentSn = parentSn;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public boolean isTimeOut() {
            return timeOut;
        }

        public void setTimeOut(boolean timeOut) {
            this.timeOut = timeOut;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
