package com.zhangyong.im.db;

/**
 * Created by zhangyong on 2015/5/21.
 */
public class IMSql {

    // 渠道
    public static final String GET_SENDS_BY_CHANNEL =
            "SELECT channel, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY channel";
    public static final String GET_SENDS_SUCCESS_BY_CHANNEL =
            "SELECT channel, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ? AND success='TRUE'\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY channel;";

    public static final String GET_CHANNELS =
            "SELECT channel from hxData\n" +
            "WHERE  phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY channel";



    // 机型
    public static final String GET_PHONES=
            "SELECT phoneCaption from hxData\n" +
            "WHERE  phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";

    public static final String GET_SEND_BY_PHONE =
            "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";

    public static final String GET_SEND_SUCCESS_BY_PHONES =
            "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ? AND success='TRUE'\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";




        // 消息 类型
    public static final String GET_SEND_BY_MSGTYPE =
            "select mestype, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY mestype";


    public static final String GET_SUCCESS_BY_MSGTYPE =
            "select mestype, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ? AND success='TRUE'\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY mestype";

    public static final String GET_MSGTYPE =
            "SELECT mestype from hxData\n" +
            "WHERE  phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY mestype";


}
