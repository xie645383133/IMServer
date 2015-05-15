package com.zhangyong.im.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhangyong on 2015/5/14.
 */
public class ConnectionUtil {
    public static Connection getConnection() {

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://116.255.255.131:30300;databaseName=testdb";
        String userName = "test_db1";                               // 用户名
        String userPwd = "5AFC795E-D301-42D9-9D3C-91D24C08";        // 密码

        Connection dbConn = null;
        try {
            Class.forName(driverName).newInstance();
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("成功连接数据库！");
        } catch (Exception e) {
            System.out.println("获取连接出错");
            e.printStackTrace();
        } finally {
            try {
                if (dbConn != null)
                    dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ConnectionUtil getConn = new ConnectionUtil();
        getConn.getConnection();

    }
}
