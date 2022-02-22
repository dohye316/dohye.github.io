package com.hspedu.mhl.dao;

import com.hspedu.mhl.utils.JDBCUtilityByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 郑道炫
 * @version 1.0
 * 开发BasicDAO，是其他DAO的父类
 */
public class BasicDAO<T> {
    //加一个泛型指定具体类型
    private  QueryRunner queryRunner = new QueryRunner();
    //开发通用的增删改查方法dml方法
    //针对任意的表
    public int dmlSQL(String sql,Object... parameters) throws SQLException {
        Connection connection = null;
        try {
            Connection connection1 = JDBCUtilityByDruid.getConnection();
            connection = connection1;
            int update = queryRunner.update(connection, sql, parameters);
            return update;
        } catch (SQLException throwables) {
          throw new RuntimeException(throwables);
        } finally {
            JDBCUtilityByDruid.close(null,null,connection);

        }
        //返回多个对象(即查询的结果是多行)，针对任意表
    }

    /**
     *
     * @param sql  sql 语句， 可以有？
     * @param clazz  传入 一个类的Class对象
     * @param parameters 传入 ？ 的具体的值，可以是多个
     * @return 根据Actor.class 返回对应的ArrayList 集合
     */
       public List<T>queryMulti(String sql,Class<T> clazz,Object... parameters) throws SQLException {
           Connection connection = null;
           try {
               Connection connection1 = JDBCUtilityByDruid.getConnection();
               connection = connection1;
               List<T> query = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
               return query;

           } catch (SQLException throwables) {
               throw new RuntimeException(throwables);
           } finally {
               JDBCUtilityByDruid.close(null,null,connection);

           }

        }

         //查询单行结果的通用方法
    public T querySingle(String sql, Class<T>clazz, Object... parameters) throws SQLException {
        Connection connection = null;
        try {
            Connection connection1 = JDBCUtilityByDruid.getConnection();
            connection = connection1;
  return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), parameters);


        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        } finally {
            JDBCUtilityByDruid.close(null,null,connection);

        }
    }

    //查询单行单例的方法，即返回单值的方法
    public Object queryScalar(String sql,Object... parameters) throws SQLException {
           Connection connection = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            return queryRunner.query(connection,sql,new ScalarHandler(),parameters);
        } catch (SQLException throwables) {
          throw new RuntimeException(throwables);
        } finally {
            JDBCUtilityByDruid.close(null,null,connection);
        }

    }
}
