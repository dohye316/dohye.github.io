package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.DiningTableDAO;
import com.hspedu.mhl.domain.DiningTable;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 郑道炫
 * @version 1.0
 */
public class DiningTableService {//业务层
    // 定义一个DiningTableDAO对象
    private DiningTableDAO diningTableDAO = new DiningTableDAO();
    //返回所有餐桌的信息
    public List<DiningTable> List() throws SQLException {
        List<DiningTable> list = diningTableDAO.queryMulti("select * from diningTable",DiningTable.class);
        return list;
    }
    //根据id，查询对应的餐桌DiningTable对象
    //如果返回null，表示id编号对应的餐桌不存在
    public DiningTable getDiningTableByid(int id) throws SQLException {
       //老韩小技巧：把sql语句放在查询分析器去测试一下。
        return diningTableDAO.querySingle("select*from diningtable where id = ?",DiningTable.class,id);
    }
 //如果餐桌可以预定，调用方法，对其状态进行更新（包括预定人的名字和电话）
public boolean orgerDiningTable(int id,String orderName,String orderTel) throws SQLException {
    int i = diningTableDAO.dmlSQL("update diningTable set state = '已经预定',orderName = ?,orderTel=? where  id=?", orderName, orderTel,id);
    return i >0;

}

    //需要提供一个更新 餐桌状态的方法
    public boolean updateDiningtablestate(int id,String state) throws SQLException {
        int i = diningTableDAO.dmlSQL("update diningTable set state=? where id=?", state, id);
        return i>0;

    }
    //将指定餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id,String state) throws SQLException {
        int tablefree = diningTableDAO.dmlSQL("update diningtable set state = ?,ordername ='',ordertel=''where id=?", state, id);
            return tablefree >0;
    }
}