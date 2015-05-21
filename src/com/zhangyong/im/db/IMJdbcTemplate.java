package com.zhangyong.im.db;

import com.zhangyong.im.util.Constants;
import com.zhangyong.im.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangyong on 2015/5/14.
 * 即时通信持久层
 */
@Repository
public class IMJdbcTemplate{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String defaultBeginTime;            // 默认的查询起始时间  昨天的[00:00:00]
    public static String defaultEndTime;              // 默认的查询结束时间  昨天的[23:59:59]

    static {
        Calendar begin = Calendar.getInstance();
//        begin.add(Calendar.DAY_OF_MONTH, -1);
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        defaultBeginTime = DateUtil.getHumanReadStr(begin.getTime());

        Calendar end = Calendar.getInstance();
//        end.add(Calendar.DAY_OF_MONTH, -1);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        defaultEndTime = DateUtil.getHumanReadStr(end.getTime());
    }

    /**
     * 总数量
     * ----------------------------------------------------------------------------------------------------------------
     */


    /**
     * 获取产品及该产品的总数量
     * 格式：
     *  HX 5033
        RY 1903
        YTX 3024
     * @param beginTime
     * @param endTime
     * @return [{product:'XX',totalNum:XX},{}]
     */
    public List<Map<String, Object>> getRecordsNumByProdect(String beginTime, String endTime) {
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null and CAST(recTime2 as DATETIME) " +
                " BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME) GROUP BY product";

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }


    /**
     * 获取产品发送/接收的总数量
     * @param sendOrReceive
     * @param beginTime
     * @param endTime
     * @return [{product:'XX',totalNum:XX},{}]
     */
    public List<Map<String, Object>> getRecordsNumByProdect(int sendOrReceive, String beginTime, String endTime) {
        String actionType = sendOrReceive == Constants.ACTION_TYPE_SEND ? "SEND" : "RECEIVE";
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null and CAST(recTime2 as DATETIME) " +
                " BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME) AND actionType = ? GROUP BY product";

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime, actionType);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }


    /**
     * 失败率
     * ----------------------------------------------------------------------------------------------------------------
      */

    /**
     * 获取发送总数量
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getSendsByProduct(String beginTime, String endTime) {
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null \n" +
                "AND actionType='SEND' \n" +
                "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME) \n" +
                "GROUP BY product";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }

    /**
     * 获取某种产品发送总数量, 按机型分组
     * @param product
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getProductsSendsByPhone(String product,String beginTime, String endTime) {
        String sql = "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
                "AND actionType='SEND' AND product = ?\n" +
                "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                "GROUP BY phoneCaption";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, product, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }


    /**
     * 获取某种产品发送*成功*总数量, 按机型分组
     * @param product
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getProductsSendsSuccessByPhone(String product,String beginTime, String endTime) {
        String sql = "select phoneCaption, count(*) totalNum from hxData WHERE product is not null \n" +
                "AND actionType='SEND' AND product = ? AND success='TRUE'\n" +
                "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                "GROUP BY phoneCaption";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, product, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }

    /**
     * 获取每种产品发送成功的数量
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getSuccessByProduct(String beginTime, String endTime) {
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null \n" +
                "AND actionType='SEND' AND success='TRUE'  \n" +
                "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                "GROUP BY product";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }

    public List<Map<String, Object>> getData(String sql , String... args) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, args[0], args[1], args[2]);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }

    public List<String> getData2(String sql, String... args) {
        List<String> list = jdbcTemplate.queryForList(sql, args, String.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }



        /**
         * 丢包率
         * -----------------------------------------------------------------------------------------------------------------
         */

        public List<Map<String, Object>> getReceivesByProduct (String beginTime, String endTime){
            String sql = "SELECT h2.product, count(*) totalNum FROM hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
                    "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "AND h1.actionType='SEND' AND h1.success='TRUE'\n" +
                    "AND h2.actionType='RECEIVE'\n" +
                    "GROUP BY h2.product;";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;

        }

        /**
         * 根据机型获取发送成功数量
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getSendTotalByPhone (String beginTime, String endTime){
            String sql = "SELECT product, phoneCaption, count(*) totalNum from hxData\n" +
                    "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
                    "AND actionType='SEND' and success ='TRUE'\n" +
                    "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "GROUP BY product, phoneCaption ORDER BY product,phoneCaption";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }

        /**
         * 按机型分类，接收的数量
         * @param product
         * @param phoneCaption
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getReceiveTotalByPhone (String product, String phoneCaption, String
        beginTime, String endTime){
            String sql = "SELECT * from hxData h1 join hxData h2\n" +
                    "on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
                    "and h1.actionType='RECEIVE' \n" +
                    "and h2.actionType='SEND' and h2.success = 'true' \n" +
                    "and h2.product = ? and h2.phoneCaption=? \n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "ORDER BY h1.product, h1.phoneCaption";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, product, phoneCaption, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


        /**
         * 根据渠道获取发送成功数量
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getSendTotalByChannel (String beginTime, String endTime){
            String sql = "SELECT product, channel, count(*) totalNum ,'SEND' send from hxData \n" +
                    "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
                    "AND actionType='SEND' and success ='TRUE'\n" +
                    "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "GROUP BY product, channel\n" +
                    "ORDER BY product, channel";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;

        }

        /**
         * 获取指定产品,渠道 接收成功数量
         * @param product
         * @param channel
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getSendTotalByChannel (String product, String channel, String beginTime, String
        endTime){
            String sql = "SELECT * from hxData h1 join hxData h2\n" +
                    "on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
                    "and h1.actionType='RECEIVE' \n" +
                    "and h2.actionType='SEND' and h2.success = 'true'\n" +
                    "and h2.product=? and h2.channel = ?\n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, product, channel, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;

        }


        /**
         * 根据类型获取发送成功数量
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getSendTotalByMsgType (String beginTime, String endTime){

            String sql = "SELECT product, mestype, count(*) totalNum ,'SEND' send from hxData \n" +
                    "WHERE phoneCaption is not null and product is not null and reSendTimes is not null\n" +
                    "AND actionType='SEND' and success ='TRUE'\n" +
                    "AND CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "GROUP BY product, mestype\n" +
                    "ORDER BY product, mestype";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


        /**
         * 根据产品,类型获取接收成功数量
         * @param product
         * @param msgType
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getSendTotalByMsgType (String product, String msgType, String beginTime, String
        endTime){

            String sql = "SELECT * from hxData h1 join hxData h2\n" +
                    "on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null and h1.reSendTimes is not null\n" +
                    "and h1.actionType='RECEIVE' \n" +
                    "and h2.actionType='SEND' and h2.success = 'true'\n" +
                    "and h2.product=? and h2.mestype=?\n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, product, msgType, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


        /**
         * 延迟
         * -----------------------------------------------------------------------------------------------------------------
         */

        /**
         * 根据产品获取发送延迟
         * @param beginTime
         * @param endTime
         * @return
         */
        public List<Map<String, Object>> getDelayByProduct (String beginTime, String endTime){
            String sql = "select DISTINCT h1.messageId,h2.product, \n" +
                    "(CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
                    "AND h2.phoneCaption is not null and h2.product is not null\n" +
                    "AND CAST(h2.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "AND h2.actionType='SEND' and h2.success='TRUE'\n" +
                    "and h1.actionType='RECEIVE'\n" +
                    "ORDER BY h2.product, cost ASC";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }

        /**
         * 获取根据机型获取发送延迟
         * @param beginTime
         * @param endTime
         * @return [{product:'xx', phone:'xx', cost:123}]
         */
        public List<Map<String, Object>> getDelayByPhone (String beginTime, String endTime){

            String sql = "select DISTINCT h1.messageId,h1.product, h1.phoneCaption, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
                    "AND h2.phoneCaption is not null and h2.product is not null\n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "ORDER BY h1.product, h1.phoneCaption, cost ASC";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


        /**
         * 根据渠道获取发送延迟
         * @param beginTime
         * @param endTime
         * @return [{product:'xx', channel:'xx', cost:123}]
         */
        public List<Map<String, Object>> getDelayByChannel (String beginTime, String endTime){

            String sql = "select DISTINCT h1.messageId,h1.product, h1.channel, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
                    "AND h2.phoneCaption is not null and h2.product is not null\n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "ORDER BY h1.product, h1.channel, cost ASC";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


        /**
         * 根据消息类型获取发送延迟
         * @param beginTime
         * @param endTime
         * @return [{product:'xx', mestype:'xx', cost:123}]
         */
        public List<Map<String, Object>> getDelayByMsgType (String beginTime, String endTime){

            String sql = "select DISTINCT h1.messageId, h1.product, h1.mestype, (CAST(h2.recTime as NUMERIC)-CAST(h1.recTime as NUMERIC)) cost\n" +
                    "from hxData h1 join hxData h2 on h1.messageId = h2.messageId\n" +
                    "WHERE h1.phoneCaption is not null and h1.product is not null\n" +
                    "AND h2.phoneCaption is not null and h2.product is not null\n" +
                    "AND h1.actionType='SEND' and h1.success='TRUE' and h2.actionType='RECEIVE'\n" +
                    "AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME)\n" +
                    "ORDER BY h1.product, h1.mestype, cost ASC";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
            if (maps == null) {
                maps = new ArrayList<>();
            }
            return maps;
        }


    public static void main(String[] args) {
        System.out.println(defaultBeginTime);
        System.out.println(defaultEndTime);

    }
}
/*
-- 根据渠道获取发送延迟
select h1.product, h1.channel, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost from hxData h1 join hxData h2 on h1.messageId = h2.messageId
WHERE h1.phoneCaption is not null and h1.product is not null
AND h2.phoneCaption is not null and h2.product is not null
AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST('2015-05-13 00:00:00' as DATETIME) AND CAST('2015-05-15 23:59:59' as DATETIME)
ORDER BY h1.product, h1.channel, cost ASC


-- 根据消息类型获取发送延迟
select DISTINCT h1.messageId, h1.product, h1.mestype, (CAST(h2.recTime as NUMERIC)-CAST(h1.recTime as NUMERIC)) cost
from hxData h1 join hxData h2 on h1.messageId = h2.messageId
WHERE h1.phoneCaption is not null and h1.product is not null
AND h2.phoneCaption is not null and h2.product is not null
AND h1.actionType='SEND' and h1.success='TRUE' and h2.actionType='RECEIVE'
AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST('2015-05-13 00:00:00' as DATETIME) AND CAST('2015-05-15 23:59:59' as DATETIME)
ORDER BY h1.product, h1.mestype, cost ASC


-- 查询发送时间 > 接收时间数据
select h1.product, h1.channel, (CAST(h1.recTime as NUMERIC)-CAST(h2.recTime as NUMERIC)) cost from hxData h1 join hxData h2 on h1.messageId = h2.messageId
WHERE h1.phoneCaption is not null and h1.product is not null
AND h2.phoneCaption is not null and h2.product is not null
AND CAST(h1.recTime2 as DATETIME) BETWEEN CAST('2015-05-13 00:00:00' as DATETIME) AND CAST('2015-05-15 23:59:59' as DATETIME)
ORDER BY h1.product, h1.channel, cost ASC
 */
