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

    // 成功率 ----------------------------------------------------------------------------------

    // 机型
    public List<Map<String, Object>> getSuccessByPhone(String product, String begin, String end) {
        return getSuccessData(IMSql.FAIL_SEND_PHONE, IMSql.FAIL_SUCCESS_PHONE, product, begin, end);
    }
    public List<String> getPhonesByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_PHONES, product, begin, end);
    }


    // 渠道
    public List<Map<String, Object>> getSuccessByChannel(String product, String begin, String end) {
        return getSuccessData(IMSql.FAIL_SEND_CHANNEL, IMSql.FAIL_SUCCESS_CHANNEL, product, begin, end);
    }

    public List<String> getChannelsByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_CHANNELS, product, begin, end);
    }


    // 消息类型
    public List<Map<String, Object>> getSuccessByMestype(String product, String begin, String end) {
        return getSuccessData(IMSql.FAIL_SEND_TYPE, IMSql.FAIL_SUCCESS_TYPE, product, begin, end);
    }
    public List<String> getMestypsByProduct(String product, String begin, String end) {
        return dao.getData2(IMSql.GET_MSGTYPE, product, begin, end);
    }


    // 丢包率 ----------------------------------------------------------------------------------


    // 机型
    public List<Map<String, Object>> getLostDataByPhone(String product, String begin, String end) {
        return getLostData(IMSql.LOST_SEND_PHONE, IMSql.LOST_RECEIVE_PHONE, product, begin, end);
    }
    // 渠道
    public List<Map<String, Object>> getLostDataByChannel(String product, String begin, String end) {
        return getLostData(IMSql.LOST_SEND_CHANNEL, IMSql.LOST_RECEIVE_CHANNEL, product, begin, end);
    }
    // 消息类型
    public List<Map<String, Object>> getLostDataByType(String product, String begin, String end) {
        return getLostData(IMSql.LOST_SEND_TYPE, IMSql.LOST_RECEIVE_TYPE, product, begin, end);
    }



    // 延迟 ----------------------------------------------------------------------------------

    public List<Map<String, Object>> getDelayDataByPhone(String product, String begin, String end) {
        return getDelayData(IMSql.DELAY_PHONE,IMSql.GET_PHONES, product, begin, end);
    }

    public List<Map<String, Object>> getDelayDataByChannel(String product, String begin, String end) {
        return getDelayData(IMSql.DELAY_CHANNEL,IMSql.GET_CHANNELS, product, begin, end);
    }

    public List<Map<String, Object>> getDelayDataByMsgType(String product, String begin, String end) {
        return getDelayData(IMSql.DELAY_TYPE,IMSql.GET_MSGTYPE, product, begin, end);
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
    public List<Map<String, Object>> getSuccessData(String sql1, String sql2, String product, String begin, String end) {
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


    /**
     * 统计丢包
     * @param sql1
     * @param sql2
     * @param product
     * @param begin
     * @param end
     * @return
     */
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


    /**
     * 统计延迟
     * @param sql
     * @param product
     * @param begin
     * @param end
     * @return
     */
    public List<Map<String, Object>> getDelayData(String sql, String sql2,String product, String begin, String end) {
        List<Map<String, Object>> origine = dao.getData(sql, product, begin, end);
        List<String> types = dao.getData2(sql2, product, begin, end);
        List<Map<String, Object>> data = new ArrayList<>();

        List<Integer> delaylt1000 = new ArrayList<>();
        List<Integer> delaylt5000 = new ArrayList<>();
        List<Integer> delaylt10000 = new ArrayList<>();
        List<Integer> delaylt50000 = new ArrayList<>();
        List<Integer> delaygt50000 = new ArrayList<>();
        for (String pro : types) {
            delaylt1000.add(0);
            delaylt5000.add(0);
            delaylt10000.add(0);
            delaylt50000.add(0);
            delaygt50000.add(0);
        }

        Map<String, Object> datalt1000 = new HashMap<>();
        Map<String, Object> datalt5000 = new HashMap<>();
        Map<String, Object> datalt10000 = new HashMap<>();
        Map<String, Object> datalt50000 = new HashMap<>();
        Map<String, Object> datagt50000 = new HashMap<>();
        datalt1000.put("name", "x<1000");
        datalt1000.put("data", delaylt1000);

        datalt5000.put("name", "1000<x<5000");
        datalt5000.put("data", delaylt5000);

        datalt10000.put("name", "5000<x<10000");
        datalt10000.put("data", delaylt10000);

        datalt50000.put("name", "10000<x<50000");
        datalt50000.put("data", delaylt50000);

        datagt50000.put("name", "x>50000");
        datagt50000.put("data", delaygt50000);

        data.add(datalt1000);
        data.add(datalt5000);
        data.add(datalt10000);
        data.add(datalt50000);
        data.add(datagt50000);


        for (Map<String, Object> map : origine) {
            int cost = StringUtils.getIntDefault0(map.get("cost"));
            String type = String.valueOf(map.get("itype"));
            int index = types.indexOf(type);
            if (cost < 0) {
                cost = Math.abs(cost);
            }
            if (cost < 1000) {
                Integer integer = delaylt1000.get(index);
                if (integer == null) {
                    delaylt1000.set(index, 1);
                } else {
                    delaylt1000.set(index, ++integer);
                }
            } else if (cost < 5000) {
                Integer integer = delaylt5000.get(index);
                if (integer == null) {
                    delaylt5000.set(index, 1);
                } else {
                    delaylt5000.set(index, ++integer);
                }
            } else if (cost < 10000) {
                Integer integer = delaylt10000.get(index);
                if (integer == null) {
                    delaylt10000.set(index, 1);
                } else {
                    delaylt10000.set(index, ++integer);
                }
            } else if (cost < 50000) {
                Integer integer = delaylt50000.get(index);
                if (integer == null) {
                    delaylt50000.set(index, 1);
                } else {
                    delaylt50000.set(index, ++integer);
                }
            } else {
                Integer integer = delaygt50000.get(index);
                if (integer == null) {
                    delaygt50000.set(index, 1);
                } else {
                    delaygt50000.set(index, ++integer);
                }
            }
        }

        return data;
    }


    public List<Map<String, Object>> getResendRecords(String begin, String end) {
        List<Map<String, Object>> list = dao.getData(IMSql.RESEND_RECORD, begin, end);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
