package com.zhangyong.im.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/14.
 * 字符串工具类
 */
public class StringUtils {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final boolean isNull(String str) {
		return str == null;
	}

	public static final boolean isNotNull(String str) {
		return str != null;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 如果str==null返回 "",否则返回str
	 * @param str
	 * @return
	 */
	public static String getNotNullValue(String str) {
		return str == null ? "" : str;
	}



	/**
	 * 获取url地址中的资源名
	 * @param url
	 * @return
	 */
	public static String getNameFromUrl(String url) {
		if (url == null) {
			return null;
		}
		return url.substring(url.lastIndexOf("/") + 1);
	}

    public static int getInt(String num) {
        Integer intNum = 0;
        try {
            intNum = Integer.valueOf(num);
        } catch (NumberFormatException e) {
            /* ignore */
        }
        return intNum;
    }

    public static int getInt(String num, int defaultNum) {
        Integer intNum = defaultNum;
        try {
            intNum = Integer.valueOf(num);
        } catch (NumberFormatException e) {
            /* ignore */
        }
        return intNum;
    }

    public static double getDouble(String num) {
        Double dou = 0D;
        try {
            dou = Double.parseDouble(num);
        } catch (NumberFormatException e) { }
        return dou;
    }

    public static double getDouble(String num, double defaultNum) {
        Double dou = defaultNum;
        try {
            dou = Double.parseDouble(num);
        } catch (NumberFormatException e) { }
        return dou;
    }

    public static String maskDot(String src, int maskNum) {
        if (src == null || src.length() <= maskNum) {
            return src;
        }
        return src.substring(0, maskNum).concat("...");

    }

    public static String maskPhone(String phone) {
        String str = phone;
        if (phone == null || phone.length() <= 8) {
            return str;
        }
        return str.substring(0, 3).concat("****").concat(str.substring(7));
    }

    /**
     * 根据异常，获取异常的详细信息
     * @param e
     * @return
     */
    public static String getExceptionDetail(Throwable e) {
        if (e == null) {
            return "";
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuffer buffer = new StringBuffer("exception_name:".concat(String.valueOf(e)).concat("\n"));
        for (int i = 0; i < stackTrace.length; ++i) {
            if (i == 0) {
                buffer.append("exception_from:" + stackTrace[i].toString().concat("\n"));
                continue;
            }
            buffer.append(stackTrace[i].toString() + "\n");
        }
        return buffer.toString();
    }


    //  int------------------------------------------------------------------------------------------------
    public static int getIntDefault0(Object obj) {
        int value = 0;
        if (obj == null) {
            return value;
        }
        try {
            value = Integer.valueOf(obj.toString());
        } catch (NumberFormatException e) { }
        return value;
    }
    public static int getInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj == null) {
            return value;
        }
        try {
            value = Integer.valueOf(obj.toString());
        } catch (NumberFormatException e) { }
        return value;
    }

    // date -----------------------------------------------------------------------------------------------

    public static String addBeginHms(String ymd) {
        String date = null;
        if (validDateStr(ymd)) {
            date = ymd + " 00:00:00";
        }
        return date;
    }

    public static String addEndHms(String ymd) {
        String date = null;
        if (validDateStr(ymd)) {
            date = ymd + " 23:59:59";
        }
        return date;
    }

    private static boolean validDateStr(String ymd) {
        boolean validDateStr = true;
        try{
            DATE_FORMAT.parse(ymd);
        } catch (Exception e) {
            validDateStr = false;
        }
        return validDateStr;
    }

    public static boolean endGtBegin(String begin, String end) {
        Date b = parseDate(begin);
        Date e = parseDate(end);
        if (b == null || e == null) {
            return false;
        }
        return e.getTime() >= b.getTime();
    }

    private static Date parseDate(String ymd) {
        Date date = null;
        try{
            date = DATE_FORMAT.parse(ymd);
        } catch (Exception e) { }
        return date;
    }
}


