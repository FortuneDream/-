package com.example.q.pocketmusic.util.common.update;

import java.io.Serializable;

public class UpdateNotificationConfiguration implements Serializable {
    private int appIcons;//应用图标，minmap
    private String appName;//应用名字

    public UpdateNotificationConfiguration(int appIcons, String appName) {
        this.appIcons = appIcons;
        this.appName = appName;
    }

    public int getAppIcons() {
        return appIcons;
    }

    public void setAppIcons(int appIcons) {
        this.appIcons = appIcons;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
