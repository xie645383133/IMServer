package com.zhangyong.im.db;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyong on 2015/5/15.
 */
public class IMMapper implements RowMapper<Map> {
    public static String C_ID = "id";
    public static String C_MESSAGE = "mes";
    public static String C_MSG_TYPE = "mesType";
    public static String C_MSG_ID = "messageId";
    public static String C_MSG_TIME = "recTime";
    public static String C_DIRECT = "actionType";
    public static String C_FUSER = "fromUser";
    public static String C_TUSER = "toUser";
    public static String C_SUCCESS = "success";
    public static String C_MSG_TIME2 = "recTime2";
    public static String C_OFFON = "offOn";
    public static String C_DATA_TYPE = "dataType";
    public static String C_CHANNEL = "channel";
    public static String C_UUID = "uuid";
    public static String C_PRODUCT = "product";
    public static String C_BRAND = "phoneCaption";
    public static String C_REGAIN_TIME = "reSendTimes";
    public static String C_SEND_TIME = "sendTime";
    public static String C_SEND_TIME2 = "sendTime2";

    @Override
    public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put(C_ID, rs.getString(C_ID));
        map.put(C_MESSAGE, rs.getString(C_MESSAGE));
        map.put(C_MSG_TYPE, rs.getString(C_MSG_TYPE));
        map.put(C_MSG_ID, rs.getString(C_MSG_ID));
        map.put(C_MSG_TIME, rs.getString(C_MSG_TIME));
        map.put(C_DIRECT, rs.getString(C_DIRECT));
        map.put(C_FUSER, rs.getString(C_FUSER));
        map.put(C_TUSER, rs.getString(C_TUSER));
        map.put(C_SUCCESS, rs.getString(C_SUCCESS));
        map.put(C_MSG_TIME2, rs.getString(C_MSG_TIME2));
        map.put(C_OFFON, rs.getString(C_OFFON));
        map.put(C_DATA_TYPE, rs.getString(C_DATA_TYPE));
        map.put(C_CHANNEL, rs.getString(C_CHANNEL));
        map.put(C_UUID, rs.getString(C_UUID));
        map.put(C_PRODUCT, rs.getString(C_PRODUCT));
        map.put(C_BRAND, rs.getString(C_BRAND));
        map.put(C_REGAIN_TIME, rs.getString(C_REGAIN_TIME));
        map.put(C_SEND_TIME, rs.getString(C_SEND_TIME));
        map.put(C_SEND_TIME2, rs.getString(C_SEND_TIME2));
        return null;
    }
}
