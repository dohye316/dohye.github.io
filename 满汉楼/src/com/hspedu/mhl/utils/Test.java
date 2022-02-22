package com.hspedu.mhl.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 郑道炫
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        //测试Utility工具
     do{   System.out.println("请输入");
        int i = Utility.readInt();
        System.out.println(i);
        //测试一下JDBCUtilsByDruid
        Connection connection = JDBCUtilityByDruid.getConnection();
        System.out.println(connection);}while(true);
    }
}
