package com.example.administrator.devilfinger.model.enumbean;

/**
 * Created by storm on 14-3-25.
 */
public enum Category {
    main("首页"), robRedMoney("抢红包"), gs("模糊效果"), setting("设置");
    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
