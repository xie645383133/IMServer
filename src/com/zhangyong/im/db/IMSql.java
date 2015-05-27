package com.zhangyong.im.db;

/**
 * Created by zhangyong on 2015/5/21.
 */
public class IMSql {

    // 失败率
    public static final String FAIL_SEND_CHANNEL =
            "SELECT channel, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY channel";
    public static final String FAIL_SUCCESS_CHANNEL =
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



    public static final String GET_PHONES=
            "SELECT phoneCaption from hxData\n" +
            "WHERE  phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";
    public static final String FAIL_SEND_PHONE =
            "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";
    public static final String FAIL_SUCCESS_PHONE =
            "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ? AND success='TRUE'\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption";



    public static final String FAIL_SEND_TYPE =
            "select mestype, count(*) totalNum from hxData WHERE product is not null \n" +
            "AND actionType='SEND' AND product = ?\n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY mestype";
    public static final String FAIL_SUCCESS_TYPE =
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




    // 丢包率

    public static final String LOST_SEND_PHONE =
            "SELECT phoneCaption, count(*) totalNum from hxData\n" +
            "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' and success ='TRUE'\n" +
            "AND product=? \n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY phoneCaption ORDER BY phoneCaption";
    public static final String LOST_RECEIVE_PHONE =
            "SELECT h2.phoneCaption, count(*) totalNum FROM hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
            "AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND h1.actionType='RECEIVE' AND h2.product=?\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY h2.phoneCaption ORDER BY h2.phoneCaption";



    public static final String LOST_SEND_CHANNEL =
            "SELECT channel, count(*) totalNum from hxData\n" +
            "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' and success ='TRUE'\n" +
            "AND product=? \n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY channel ORDER BY channel";
    public static final String LOST_RECEIVE_CHANNEL =
            "SELECT h2.channel, count(*) totalNum FROM hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
            "AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND h1.actionType='RECEIVE' AND h2.product=?\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY h2.channel ORDER BY h2.channel";


    public static final String LOST_SEND_TYPE =
            "SELECT mestype, count(*) totalNum from hxData\n" +
            "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
            "AND actionType='SEND' and success ='TRUE'\n" +
            "AND product=? \n" +
            "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY mestype ORDER BY mestype";
    public static final String LOST_RECEIVE_TYPE =
            "SELECT h2.mestype, count(*) totalNum FROM hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
            "AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND h1.actionType='RECEIVE' AND h2.product=?\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "GROUP BY h2.mestype ORDER BY h2.mestype";


    // 延时
    public static final String DELAY_PHONE =
            "select DISTINCT h1.messageId,h1.phoneCaption itype, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost \n" +
            "from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
            "AND h2.phoneCaption is not null and h2.product is not null\n" +
            "AND h1.product=?\n" +
            "AND h1.actionType='RECEIVE' AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "ORDER by h1.phoneCaption, cost ASC";

    public static final String DELAY_CHANNEL =
            "select DISTINCT h1.messageId,h1.channel itype, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost \n" +
            "from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
            "AND h2.phoneCaption is not null and h2.product is not null\n" +
            "AND h1.product=?\n" +
            "AND h1.actionType='RECEIVE' AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "ORDER by h1.channel, cost ASC";
    public static final String DELAY_TYPE =
            "select DISTINCT h1.messageId,h1.mestype itype, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost \n" +
            "from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
            "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
            "AND h2.phoneCaption is not null and h2.product is not null\n" +
            "AND h1.product=?\n" +
            "AND h1.actionType='RECEIVE' AND h2.actionType='SEND' AND h2.success='TRUE'\n" +
            "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
            "ORDER by h1.mestype, cost ASC";


    public static final String RESEND_RECORD = "SELECT product, resendTimes, actionType, success , channel from hxData \n" +
        "WHERE product is not null AND reSendTimes is NOT NULL\n" +
        "AND actionType = 'SEND'\n" +
        "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
        "ORDER BY product, reSendTimes";


    /*
    SELECT phoneCaption, count(*) totalNum from hxData
WHERE phoneCaption is not null and product is not null and reSendTimes is not null
AND actionType='SEND' and success ='TRUE'
AND CAST(recTime2 as DATETIME) BETWEEN CAST('2015-05-20 00:00:00' as DATETIME) AND CAST('2015-05-20 23:59:59' as DATETIME)
AND product='HX'
GROUP BY phoneCaption ORDER BY phoneCaption;




SELECT h2.phoneCaption, count(*) totalNum FROM hxData h1 join hxData h2 on h1.messageId = h2.messageId
WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null
AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST('2015-05-20 00:00:00' as DATETIME) AND CAST('2015-05-20 23:59:59' as DATETIME)
AND h2.actionType='SEND' AND h2.success='TRUE'
AND h1.actionType='RECEIVE' AND h2.product='HX'
GROUP BY h2.phoneCaption ORDER BY h2.phoneCaption;
     */

}
