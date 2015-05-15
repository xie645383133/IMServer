package com.zhangyong.im.db;

import com.zhangyong.im.model.IMModel;
import com.zhangyong.im.util.Constants;
import com.zhangyong.im.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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

    private String defaultBeginTime;            // 默认的查询起始时间  今天的[00:00:00]
    private String defaultEndTime;              // 默认的查询结束时间  今天的[23:59:59]

    {
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        defaultBeginTime = DateUtil.getHumanReadStr(begin.getTime());

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        defaultEndTime = DateUtil.getHumanReadStr(end.getTime());
    }

    // 总数量 ----------------------------------------------------------------------------------------------------------------


    /**
     * 获取所有产品(HX,RY,YTX,LC)
     * @return ['A','B','C']
     */
    public List<String> getProejcts() {
        String sql = "SELECT DISTINCT product from hxData where product is not null";
        List<String> products = jdbcTemplate.queryForList(sql, String.class);
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }




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
                "BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME) GROUP BY product";

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
        String actionType = sendOrReceive == Constants.SEND ? "SEND" : "RECEIVE";
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null and CAST(recTime2 as DATETIME) " +
                "BETWEEN CAST(? as DATETIME) AND CAST(? as DATETIME) AND actionType = ? GROUP BY product";

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime, actionType);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }



    // 失败率 ----------------------------------------------------------------------------------------------------------------

    /**
     * 获取每种产品发送成功的数量
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getSuccessNums(String beginTime, String endTime) {
        String sql = "select product, count(*) totalNum from hxData WHERE product is not null AND actionType='SEND' " +
                "AND success='TRUE'  and CAST(recTime2 as DATETIME) BETWEEN CAST(? as DATETIME) " +
                "AND CAST(? as DATETIME) GROUP BY product;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }

    /**
     * 获取产品, 手机型号的发送数量
     * @param beginTime
     * @param endTime
     * @return [{product:'XX',phoneCaption:'XX',totalNum:XX},{}]
     */
    public List<Map<String, Object>> getTotalByProductPhone( String beginTime, String endTime) {

        String sql = "select product, phoneCaption, count(*) totalNum from hxData where phoneCaption is not null and product is not null GROUP BY product, phoneCaption order by product";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }


    /**
     * 获取产品, 手机型号的发送数量
     * int successFlag
     * @param beginTime
     * @param endTime
     * @return [{product:'XX',phoneCaption:'XX',totalNum:XX},{}]
     */
    public List<Map<String, Object>> getTotalByProductPhone(int successFlag, String beginTime, String endTime) {
            String success = null;
        if (successFlag == Constants.SUCCESS) {
            success = "TRUE";
        }else if (successFlag == Constants.FAILURE) {
            success = "FALSE";
        }
        String sql = "select product, phoneCaption, count(*) totalNum from hxData where success = ? phoneCaption is not null and product is not null GROUP BY product, phoneCaption order by product";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, success, beginTime, endTime);
        if (maps == null) {
            maps = new ArrayList<>();
        }
        return maps;
    }




}
