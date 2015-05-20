package com.zhangyong.im.controller;

import com.zhangyong.im.db.IMJdbcTemplate;
import com.zhangyong.im.util.StringUtils;
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

        // [{'product':'xx', 'phoneCaption':'xx', total:xx, success:xx, fail:xx},{}]
        for (Map<String, Object> map : sendsByProductPhone) {
            String prod = String.valueOf(map.get("product"));
            String phon = String.valueOf(map.get("phoneCaption"));
            int total = Integer.valueOf(map.get("totalNum").toString());
            int success = imDao.getSuccessByProductPhone(prod, phon, begin, end);
            map.put("success", success);
            map.put("fail", total - success < 0 ? 0 : total - success);
        }


        Map<String, List<Map<String, Object>>> data = new HashMap<>();
        Map<String, Set<String>> phones = new HashMap<>();
        Map<String, Object> suc = null;
        Map<String, Object> fil = null;
        List<Integer> sucList = null;
        List<Integer> filList = null;

        for (Map<String, Object> map : sendsByProductPhone) {
            String prod = String.valueOf(map.get("product"));
            String phon = String.valueOf(map.get("phoneCaption"));
            List<Map<String, Object>> v = data.get(prod);
            if (v == null) {
                v = new ArrayList<>();
            }
            data.put(prod, v);

            Set<String> pphone = phones.get(prod);
            if (pphone == null) {
                pphone = new HashSet<>();
            }
            phones.put(prod, pphone);



            suc = new HashMap<>();
            fil = new HashMap<>();
            sucList = new ArrayList<>();
            filList = new ArrayList<>();

            suc.put("name", phon + "-成功");
            suc.put("data", sucList);
            fil.put("name", phon + "-失败");
            fil.put("data", filList);
            for (Map<String, Object> map2 : sendsByProductPhone) {
                String prod2 = String.valueOf(map2.get("product"));
                String phon2 = String.valueOf(map2.get("phoneCaption"));
                if (!prod.equals(prod2)) {
                    continue;
                }
                pphone.add(phon2);
                System.out.println("prod2:" + prod2 + ", phon2:" + phon2);
                sucList.add(StringUtils.getInt(String.valueOf(map2.get("success"))));
                filList.add(StringUtils.getInt(String.valueOf(map2.get("fail"))));
            }
            v.add(suc);
            v.add(fil);
        }

        model.addAttribute("proNum", recordsNumByProdect);
        model.addAttribute("data", data);
        model.addAttribute("phones", phones);

        System.out.println(data);
        return model;
    }

    @RequestMapping("push")
    public void push() {


    }
}
