package com.dashwood.dashwoodmediaplayer.handler;

import android.text.TextUtils;

public class HandlerCheckerValue {

    public static boolean checkEmptyOrNullValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        if (value.equals("null")) {
            return true;
        }
        return value.equals("NULL");
    }

    public static boolean checkValueIsNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
