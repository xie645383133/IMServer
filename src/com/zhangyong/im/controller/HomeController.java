package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public Model im(Model model) {
        String begin = defaultBeginTime;
        String end = defaultEndTime;
        List<Map<String, Object>> recordsNumByProdect = imDao.getRecordsNumByProdect(begin, end);

        List<Map<String, Object>> sendsByProductPhone = imDao.getSendsByProductPhone(begin, end);
        Map<String, Set<String>> names = new HashMap<>();
        Set<String> phones = new HashSet<>();
        for (Map<String, Object> map : sendsByProductPhone) {
            String prod = String.valueOf(map.get("product"));
            String phon = String.valueOf(map.get("phoneCaption"));
            int total = Integer.valueOf(map.get("totalNum").toString());
            int success = imDao.getSuccessByProductPhone(prod, phon, begin, end);
            map.put("success", success);
            map.put("fail", total - success < 0 ? 0 : total - success);
            phones.add(phon);

            Set<String> set = names.get(prod);
            if (set == null) {
                set = new HashSet<>();
                names.put(prod, set);
            }
            set.add(phon);
        }


        model.addAttribute("proNum", recordsNumByProdect);
        model.addAttribute("prodPhoneNum", sendsByProductPhone);
        model.addAttribute("names", names);
        model.addAttribute("phones", phones);
        System.out.println(names);
        return model;
    }

    @RequestMapping("push")
    public void push() {


    }
}
