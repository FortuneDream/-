package com.example.q.pocketmusic.model.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2016/9/12.
 */
public class ExceptionBean extends BmobObject {
    private String version;
    private String vendor;
    private String Model;
    private String cpu;
    private String date;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
}
