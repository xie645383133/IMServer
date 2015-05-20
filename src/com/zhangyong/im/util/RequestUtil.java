package com.zhangyong.im.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangyong on 2015/5/20.
 */
public class RequestUtil {

    // string ------------------------------------------------------------------------

    public static String getString(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String val = request.getParameter(key);
        if (val == null) {
            val = defaultValue;
        }
        return val;
    }


    // int ----------------------------------------------------------------------------
    public static int getInt(HttpServletRequest request, String key) {
        String val = request.getParameter(key);
        int result = 0;
        try {
            result = Integer.valueOf(val);
        } catch (NumberFormatException e) { }
        return result;
    }

    public static int getInt(HttpServletRequest request, String key, int defaultValue) {
        String val = request.getParameter(key);
        int result = defaultValue;
        try {
            result = Integer.valueOf(val);
        } catch (NumberFormatException e) { }
        return result;
    }
}
