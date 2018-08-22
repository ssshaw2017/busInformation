package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HiveConn {
    // private static String driverName =
    // "org.apache.hadoop.hive.jdbc.HiveDriver";
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";// 驱动名
    private static String url = "jdbc:hive2://10.1.40.191:10000/tcps";// 使用数据库lzl，用户名为hduser，密码为iti@240
    private static String user = "hduser";
    private static String password = "iti@240";

    // 获取数据库连接
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 寻找驱动类drivername
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(url, user, password);
        // 检验是否连上hive数据库
        ResultSet res = conn.createStatement().executeQuery("show tables");
        while (res.next()) {
            System.out.println(res.getString(1));
        }
        return conn;
    }

    // 释放数据库连接
    public static void releaseConn(Connection ct, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ct != null) {
            try {
                ct.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}