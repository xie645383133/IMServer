package com.zhangyong.im.service;

import com.zhangyong.im.db.PushSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2015/6/6.
 */

@Component
public class PushService {

    @Autowired
    private JdbcTemplate dao;


    /**
     * 获取服务器发送的
     * @param channel
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getServerSend(String channel, String beginTime, String endTime) {
        List<Map<String, Object>> data = dao.queryForList(PushSql.SERVER_PUSH, channel, beginTime, endTime);
        data = fill(data);
        return data;
    }


    /**
     * 获取手机收到的
     * @param channel
     * @param product
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getPhoneReceive(String channel, String product, String beginTime, String endTime) {
        List<Map<String, Object>> data = dao.queryForList(PushSql.PHONE_RECEIVE, channel, product, beginTime, endTime);
        data = fill(data);
        return data;
    }


    /**
     * 获取延时数据
     * @param channel
     * @param product
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getDelay(String channel, String product, String beginTime, String endTime) {
        List<Map<String, Object>> data = dao.queryForList(PushSql.PHONE_RECEIVE, channel, product, beginTime, endTime);
        data = fill(data);
        return data;
    }


    /**
     * 包装空集合
     * @param list
     * @return
     */
    public List<Map<String, Object>> fill(List<Map<String, Object>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
