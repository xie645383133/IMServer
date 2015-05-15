package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangyong on 2015/5/15.
 * 即时通信 页面
 */
@Controller
@RequestMapping("im")
public class IMController {
    @Autowired
    private IMJdbcTemplate template;


    @RequestMapping("total")
    public void total() {
        template.getRecordsNumByProdect("2015-05-11 00:00:00", "2015-05-12 23:59:59");
    }
}
