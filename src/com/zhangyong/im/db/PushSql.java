package com.zhangyong.im.db;

/**
 * Created by zhangyong on 2015/6/6.
 */
public class PushSql {

    /**
     * 服务器发送的
     */
    public static final String SERVER_PUSH =
            "SELECT product, count(*) totalNum from push_server \n" +
            "WHERE channel = ? and success = 1 AND sendBeginTime2 BETWEEN ? and ?\n" +
            "GROUP BY product;";


    /**
     * 手机收到的
     */
    public static final String PHONE_RECEIVE =
            "select DISTINCT p.* from push_phone p JOIN push_server s on s.messageId = p.messageId\n" +
            "WHERE p.channel = ? AND p.product = ?\n" +
            "AND s.sendBeginTime2 BETWEEN ? and ?";



    public static final String DELAY =
            "select DISTINCT s.sendEndTime, s.sendEndTime2, p.recTime, p.recTime2,p.product,p.channel,(p.recTime-s.sendEndTime) cost , p.message\n" +
            "from push_phone p JOIN push_server s on s.messageId = p.messageId AND p.product = s.product  AND p.channel = s.channel\n" +
            "WHERE recTime <> 2147483647\n" +
            "AND s.sendEndTime2 BETWEEN '2015-06-08 00:00:00' and '2015-06-08 23:59:59'\n" +
            "ORDER BY p.product, cost";

    public static final String AVG_DELAY =
            "SELECT  avg(p.recTime-s.sendEndTime)\n" +
            "from push_phone p JOIN push_server s on s.messageId = p.messageId AND p.product = s.product  AND p.channel = s.channel\n" +
            "WHERE recTime <> 2147483647\n" +
            "AND s.sendEndTime2 BETWEEN '2015-06-08 00:00:00' and '2015-06-08 23:59:59'\n" +
            "AND s.channel = 'ANDROID' and p.channel = 'ANDROID'\n" +
            "AND s.product = 'JG' AND p.product = 'JG'";
}
