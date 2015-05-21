package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import com.zhangyong.im.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.zhangyong.im.db.IMJdbcTemplate.defaultBeginTime;
import static com.zhangyong.im.db.IMJdbcTemplate.defaultEndTime;

/**
 * Created by zhangyong on 2015/5/15.
 * 主页面
 */
@Controller
public class HomeController {
    @Autowired
    IMJdbcTemplate imDao;

    @RequestMapping("im")
    public Model im(Model model, HttpSession session) {

        String begin = defaultBeginTime;
        String end = defaultEndTime;
        List<Map<String, Object>> recordsNumByProdect = imDao.getRecordsNumByProdect(begin, end);

        // 总数量数据
        List<Map<String, Object>> sendsByProduct = imDao.getSendsByProduct(begin, end);
        List<Map<String, Object>> successByProduct = imDao.getSuccessByProduct(begin, end); // 发送成功

        // 成功率数据
        List<Map<String, Object>> data = new ArrayList<>();
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

        // 产品数据
        List<String> products = new ArrayList<>();
        Map<String, Object> m = null;
        Map<String, Object> s = null;
        for (int i = 0; i < sendsByProduct.size(); ++i) {
            m = sendsByProduct.get(i);
            s = successByProduct.get(i);
            int total = StringUtils.getIntDefault0(m.get("totalNum"));      // 发送总数
            int success = StringUtils.getIntDefault0(s.get("totalNum"));    // 发送成功
            int fail = total - success < 0 ? 0 : total - success;

            sucData.add(success);
            filData.add(fail);
            products.add(String.valueOf(m.get("product")));
        }


        List<Map<String, Object>> receivesByProduct = imDao.getReceivesByProduct(begin, end);   // 对方接收成功
        // 丢包率
        List<Map<String, Object>> data2 = new ArrayList<>();
        Map<String, Object> send = new HashMap<>();
        Map<String, Object> reci = new HashMap<>();
        List<Integer> sendData = new ArrayList<>();
        List<Integer> reciData = new ArrayList<>();

        send.put("name", "发送");
        send.put("data", sendData);
        reci.put("name", "接收");
        reci.put("data", reciData);

        data2.add(send);
        data2.add(reci);
        for (int i = 0; i < successByProduct.size(); ++i) {
            m = successByProduct.get(i);
            s = receivesByProduct.get(i);
            int send0 = StringUtils.getIntDefault0(m.get("totalNum"));       // 发送成功总数
            int receive = StringUtils.getIntDefault0(s.get("totalNum"));    // 对方接收总数
            sendData.add(send0);
            reciData.add(receive);
        }

        // 延迟
        List<Map<String, Object>> delayByProduct = imDao.getDelayByProduct(begin, end);
        List<Map<String, Object>> data3 = new ArrayList<>();
        List<Integer> delaylt1000 = new ArrayList<>();
        List<Integer> delaylt5000 = new ArrayList<>();
        List<Integer> delaylt10000 = new ArrayList<>();
        List<Integer> delaylt50000 = new ArrayList<>();
        List<Integer> delaygt50000 = new ArrayList<>();
        for (String pro : products) {
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

        data3.add(datalt1000);
        data3.add(datalt5000);
        data3.add(datalt10000);
        data3.add(datalt50000);
        data3.add(datagt50000);


        for (Map<String, Object> map : delayByProduct) {
            int cost = StringUtils.getIntDefault0(map.get("cost"));
            String product = String.valueOf(map.get("product"));
            int index = products.indexOf(product);
            if (cost < 0) {
                cost = 0;
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

        model.addAttribute("proNum", recordsNumByProdect);
        model.addAttribute("products", products);
        model.addAttribute("data", data);
        model.addAttribute("data2", data2);
        model.addAttribute("data3", data3);
        System.out.println(data3);
        return model;
    }






    @RequestMapping("push")
    public void push() {


    }
}
