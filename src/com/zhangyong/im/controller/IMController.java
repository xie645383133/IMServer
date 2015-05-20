package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import com.zhangyong.im.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2015/5/15.
 * 即时通信 页面
 */
@Controller
@RequestMapping("im")
public class IMController {
    @Autowired
    private IMJdbcTemplate template;
    private List<String> products;
    @RequestMapping("total")
    public void total(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("total");

//        String begin = RequestUtil.getString(request, "begin", IMJdbcTemplate.defaultBeginTime);
//        String end = RequestUtil.getString(request, "end", IMJdbcTemplate.defaultEndTime);
//
//        List<Map<String, Object>> recordsNumByProdect = template.getRecordsNumByProdect(begin, end);
    }
}
