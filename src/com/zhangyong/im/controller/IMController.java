package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import com.zhangyong.im.service.IMService;
import com.zhangyong.im.util.RequestUtil;
import com.zhangyong.im.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhangyong.im.db.IMJdbcTemplate.defaultBeginTime;
import static com.zhangyong.im.db.IMJdbcTemplate.defaultEndTime;

/**
 * Created by zhangyong on 2015/5/15.
 * 即时通信 页面
 */
@Controller
@RequestMapping("im")
public class IMController {
    @Autowired
    private IMService service;

    private String product = "HX";
    String begin = defaultBeginTime;
    String end = defaultEndTime;

    /**
     * 发送失败
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("fail")
    public String fail(ModelMap modelMap, HttpServletRequest request) {
        String pro = RequestUtil.getString(request, "p");
        if (pro != null) {
            product = pro;
        }
        // 失败率-机型
        List<Map<String, Object>> data1 = service.getSuccessByPhone(product, begin, end);
        List<String> phones = service.getPhonesByProduct(product, begin, end);

        // 失败率-渠道
        List<Map<String, Object>> data2 = service.getSuccessByChannel(product, begin, end);
        List<String> channels = service.getChannelsByProduct(product, begin, end);

        // 失败率-消息类型
        List<Map<String, Object>> data3 = service.getSuccessByMestype(product, begin, end);
        List<String> msgTypes = service.getMestypsByProduct(product, begin, end);


        modelMap.addAttribute("data1", data1);
        modelMap.addAttribute("phones", phones);

        modelMap.addAttribute("data2", data2);
        modelMap.addAttribute("channles", channels);

        modelMap.addAttribute("data3", data3);
        modelMap.addAttribute("msgTypes", msgTypes);

        modelMap.addAttribute("product", product);
        return "im_fail";
    }


    /**
     * 丢包
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("lost")
    public String lost(ModelMap modelMap, HttpServletRequest request) {
        String pro = RequestUtil.getString(request, "p");
        if (pro != null) {
            product = pro;
        }

        // 丢包率-机型
        List<Map<String, Object>> data1 = service.getDiuDataByPhone(product, begin, end);
        List<String> phones = service.getPhonesByProduct(product, begin, end);

        // 丢包率-渠道
        List<Map<String, Object>> data2 = service.getDiuDataByChannel(product, begin, end);
        List<String> channels = service.getChannelsByProduct(product, begin, end);

        // 丢包率-消息类型
        List<Map<String, Object>> data3 = service.getDiuDataByType(product, begin, end);
        List<String> msgTypes = service.getMestypsByProduct(product, begin, end);

        modelMap.addAttribute("data1", data1);
        modelMap.addAttribute("phones", phones);

        modelMap.addAttribute("data2", data2);
        modelMap.addAttribute("channles", channels);

        modelMap.addAttribute("data3", data3);
        modelMap.addAttribute("msgTypes", msgTypes);

        modelMap.addAttribute("product", product);

//        System.out.println(data1);
//        System.out.println(data2);
//        System.out.println(data3);

//        System.out.println("phones:" + phones);
//        System.out.println("channels:" + channels);
//        System.out.println("type:" + msgTypes);
        return "im_lost";
    }


    /**
     * 延时
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("delay")
    public String delay(ModelMap modelMap, HttpServletRequest request) {
        String pro = RequestUtil.getString(request, "p");
        if (pro != null) {
            product = pro;
        }


        return "im_delay";
    }

}
