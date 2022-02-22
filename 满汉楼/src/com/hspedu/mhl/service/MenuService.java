package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.MenuDAO;
import com.hspedu.mhl.domain.Menu;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 郑道炫
 * @version 1.0
 */
public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();
    //返回所有的菜品，返回给界面使用
    public List<Menu> list() throws SQLException {
        List list = menuDAO.queryMulti("select*from menu", Menu.class);
        return list;
    }
    //需要方法，根据id，返回menu对象
    public Menu getMenuById(int id) throws SQLException {
       return  (Menu) menuDAO.querySingle("select * from menu where id = ?", Menu.class, id);


    }
}
