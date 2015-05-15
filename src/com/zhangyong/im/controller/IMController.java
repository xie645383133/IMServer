package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

/**
 * Created by zhangyong on 2015/5/15.
 */
@Controller
@RequestMapping("im")
public class IMController {
    @Autowired
    private IMJdbcTemplate template;

    @RequestMapping("total")
    public void total() {
        template.getTotalNum();
        System.out.println("done");
    }
}
