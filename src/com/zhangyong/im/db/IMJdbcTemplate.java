package com.zhangyong.im.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.jar.Attributes;


/**
 * Created by zhangyong on 2015/5/14.
 */
@Repository
public class IMJdbcTemplate{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String TABLE_NAME = "hxData";
    public void getTotalNum() {
        String sql = "select count(*) total from hxData";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        sqlRowSet.next();
        int total = sqlRowSet.getInt("total");
        System.out.println("total:" + total);
    }
}
