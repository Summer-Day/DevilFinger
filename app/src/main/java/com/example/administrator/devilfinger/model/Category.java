package com.example.administrator.devilfinger.model;

/**
 * Created by storm on 14-3-25.
 */
public enum Category {
    robRedMoney("抢红包"), setting("设置");
    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
