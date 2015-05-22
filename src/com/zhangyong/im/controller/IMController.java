package com.zhangyong.im.controller;

import com.zhangyong.im.service.IMService;
import com.zhangyong.im.util.RequestUtil;
import com.zhangyong.im.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
        // 默认产品
        String pro = RequestUtil.getString(request, "p");
        if (pro != null) {
            product = pro;
        }

        // 默认时间
        String b = RequestUtil.getString(request, "begin");
        String e = RequestUtil.getString(request, "end");
        String begin = defaultBeginTime;
        String end = defaultEndTime;
        if (StringUtils.endGtBegin(b, e)) {
            begin = StringUtils.addBeginHms(b);
            end = StringUtils.addEndHms(e);
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
        modelMap.addAttribute("begin", begin);
        modelMap.addAttribute("end", end);
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

        // 默认时间
        String b = RequestUtil.getString(request, "begin");
        String e = RequestUtil.getString(request, "end");
        String begin = defaultBeginTime;
        String end = defaultEndTime;
        if (StringUtils.endGtBegin(b, e)) {
            begin = StringUtils.addBeginHms(b);
            end = StringUtils.addEndHms(e);
        }


        // 丢包率-机型
        List<Map<String, Object>> data1 = service.getLostDataByPhone(product, begin, end);
        List<String> phones = service.getPhonesByProduct(product, begin, end);

        // 丢包率-渠道
        List<Map<String, Object>> data2 = service.getLostDataByChannel(product, begin, end);
        List<String> channels = service.getChannelsByProduct(product, begin, end);

        // 丢包率-消息类型
        List<Map<String, Object>> data3 = service.getLostDataByType(product, begin, end);
        List<String> msgTypes = service.getMestypsByProduct(product, begin, end);

        modelMap.addAttribute("data1", data1);
        modelMap.addAttribute("phones", phones);

        modelMap.addAttribute("data2", data2);
        modelMap.addAttribute("channles", channels);

        modelMap.addAttribute("data3", data3);
        modelMap.addAttribute("msgTypes", msgTypes);

        modelMap.addAttribute("product", product);
        modelMap.addAttribute("begin", begin);
        modelMap.addAttribute("end", end);

        return "im_lost";
    }


    /**
     * 延时
     * @param request
     * @return
     */
    @RequestMapping("delay")
    public String delay(ModelMap modelMap, HttpServletRequest request) {
        String pro = RequestUtil.getString(request, "p");
        if (pro != null) {
            product = pro;
        }

        // 默认时间
        String b = RequestUtil.getString(request, "begin");
        String e = RequestUtil.getString(request, "end");
        String begin = defaultBeginTime;
        String end = defaultEndTime;
        if (StringUtils.endGtBegin(b, e)) {
            begin = StringUtils.addBeginHms(b);
            end = StringUtils.addEndHms(e);
        }

        // 机型
        List<Map<String, Object>> data1 = service.getDelayDataByPhone(product, begin, end);
        List<String> phones = service.getPhonesByProduct(product, begin, end);

        // 渠道
        List<Map<String, Object>> data2 = service.getDelayDataByChannel(product, begin, end);
        List<String> channels = service.getChannelsByProduct(product, begin, end);

        // 消息类型
        List<Map<String, Object>> data3 = service.getDelayDataByMsgType(product, begin, end);
        List<String> msgTypes = service.getMestypsByProduct(product, begin, end);

        modelMap.addAttribute("product", product);

        modelMap.addAttribute("data1", data1);
        modelMap.addAttribute("phones", phones);

        modelMap.addAttribute("data2", data2);
        modelMap.addAttribute("channels", channels);

        modelMap.addAttribute("data3", data3);
        modelMap.addAttribute("msgTypes", msgTypes);
        modelMap.addAttribute("begin", begin);
        modelMap.addAttribute("end", end);

        return "im_delay";
    }

}
