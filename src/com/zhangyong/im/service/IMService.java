package com.zhangyong.im.service;

import com.zhangyong.im.db.IMJdbcTemplate;
import com.zhangyong.im.db.IMSql;
import com.zhangyong.im.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2015/5/21.
 */
@Component
public class IMService {
    @Autowired
    private IMJdbcTemplate dao;

    // 成功率-机型
    public List<Map<String, Object>> getSuccessByPhone(String product, String begin, String end) {
        return getFileData(IMSql.BAI_SEND_PHONE, IMSql.BAI_SUCCESS_PHONE, product, begin, end);
    }
    public List<String> getPhonesByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_PHONES, product, begin, end);
    }



    // 成功率-渠道-----------------------
    public List<Map<String, Object>> getSuccessByChannel(String product, String begin, String end) {
        return getFileData(IMSql.BAI_SEND_CHANNEL, IMSql.BAI_SUCCESS_CHANNEL, product, begin, end);
    }

    public List<String> getChannelsByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_CHANNELS, product, begin, end);
    }



    // 成功率-消息类型--------
    public List<Map<String, Object>> getSuccessByMestype(String product, String begin, String end) {
        return getFileData(IMSql.BAI_SEND_TYPE, IMSql.BAI_SUCCESS_TYPE, product, begin, end);
    }
    public List<String> getMestypsByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_MSGTYPE, product, begin, end);
    }


    // 丢包率 ------------------------------------------------------------------------------------------------------------


    // 机型
    public List<Map<String, Object>> getDiuDataByPhone(String product, String begin, String end) {
        return getLostData(IMSql.DIU_SEND_PHONE, IMSql.DIU_RECEIVE_PHONE, product, begin, end);
    }
    // 渠道
    public List<Map<String, Object>> getDiuDataByChannel(String product, String begin, String end) {
        return getLostData(IMSql.DIU_SEND_CHANNEL, IMSql.DIU_RECEIVE_CHANNEL, product, begin, end);
    }
    // 消息类型
    public List<Map<String, Object>> getDiuDataByType(String product, String begin, String end) {
        return getLostData(IMSql.DIU_SEND_TYPE, IMSql.DIU_RECEIVE_TYPE, product, begin, end);
    }















    /**
     * 统计发送失败
     * @param sql1
     * @param sql2
     * @param product
     * @param begin
     * @param end
     * @return
     */
    public List<Map<String, Object>> getFileData(String sql1, String sql2, String product, String begin, String end) {
        List<Map<String, Object>> data = new ArrayList<>();
        List<Map<String, Object>> data1 = dao.getData(sql1, product, begin, end);
        List<Map<String, Object>> data2 = dao.getData(sql2, product, begin, end);

        Map<String, Object> suc = new HashMap<>();
        Map<String, Object> fil = new HashMap<>();
        List<Integer> sucData = new ArrayList<>();
        List<Integer> filData = new ArrayList<>();

        suc.put("name", "成功");
        suc.put("data", sucData);
        fil.put("name", "失败");
        fil.put("data", filData);
        data.add(suc);
        data.add(fil);

        for (int i = 0; i < data1.size(); ++i) {
            Map<String, Object> m1 = data1.get(i);
            Map<String, Object> m2 = data2.get(i);
            int total = StringUtils.getIntDefault0(m1.get("totalNum"));
            int success = StringUtils.getIntDefault0(m2.get("totalNum"));
            int fail = total - success < 0 ? 0 : total - success;

            sucData.add(success);
            filData.add(fail);
        }
        return data;
    }


    public List<Map<String, Object>> getLostData(String sql1, String sql2, String product, String begin, String end){
        List<Map<String, Object>> data = new ArrayList<>();
        List<Map<String, Object>> data1 = dao.getData(sql1, product, begin, end);
        List<Map<String, Object>> data2 = dao.getData(sql2, product, begin, end);

        Map<String, Object> suc = new HashMap<>();
        Map<String, Object> fil = new HashMap<>();
        List<Integer> sucData = new ArrayList<>();
        List<Integer> filData = new ArrayList<>();

        suc.put("name", "接收");
        suc.put("data", sucData);
        fil.put("name", "丢失");
        fil.put("data", filData);
        data.add(suc);
        data.add(fil);

        System.out.println(data1.size());
        System.out.println(data2.size());

        for (int i = 0; i < data1.size(); ++i) {
            Map<String, Object> m1 = data1.get(i);
            Map<String, Object> m2 = data2.get(i);
            int total = StringUtils.getIntDefault0(m1.get("totalNum"));
            int receive = StringUtils.getIntDefault0(m2.get("totalNum"));
            int fail = total - receive < 0 ? 0 : total - receive;

            sucData.add(receive);
            filData.add(fail);
        }
        return data;
    }

}
