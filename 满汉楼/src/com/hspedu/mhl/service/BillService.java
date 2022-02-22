package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.BillDAO;
import com.hspedu.mhl.dao.MultiTableDAO;
import com.hspedu.mhl.domain.Bill;
import com.hspedu.mhl.domain.MultiTableBean;
import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @author 郑道炫
 * @version 1.0
 */
public class BillService {
    private BillDAO billDAO = new BillDAO();
   //创建MenuService实例
    private MenuService menuService = new MenuService();
    //创建DiningTableServie实例
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO mutiTabelDao = new MultiTableDAO();
     //编写点餐的方法
    //1.生成菜单
    //2.需要更新对应餐桌的状态
     public boolean orderMenu(int menuid,int nums,int diningtableid) throws SQLException {

    //生成一个账单号
    //UUID随机字符串
    String billid = UUID.randomUUID().toString();

    //将账单生成到bill表
         int i = billDAO.dmlSQL("insert into bill values(null,?,?,?,?,?,now(),'未结账')", billid, menuid, nums, menuService.getMenuById(menuid).getPrice()*nums, diningtableid);
     if (i<=0){
         return false;
     }

         //需要更新对应餐桌的状态
      return  diningTableService.updateDiningtablestate(diningtableid, "就餐中");


     }
     //返回所有的账单，提供给View调用
      public List<Bill>list() throws SQLException {
          return billDAO.queryMulti("select*from bill",Bill.class);
    }//返回所有的菜单并带有菜品名
    public List<MultiTableBean>list2() throws SQLException {
        return mutiTabelDao.queryMulti("SELECT bill.*,NAME,price " +
                "FROM bill,menu " +
                "WHERE bill.menuid = menu.id",MultiTableBean.class);
    }


     //查看某个餐桌是否有未结账的账单
    public boolean hasPayBillDiningTableId(int dinningTableId) throws SQLException {
                Bill bill = (Bill) billDAO.querySingle("  SELECT *FROM bill WHERE   diningtableid = ? AND state = '未结账'   LIMIT 0,1",Bill.class,dinningTableId);
               return bill !=null;
    }
    //完成结账【如果餐桌存在，并且该餐桌有未结账的张单】
    public boolean payBill(int diningTableid, String payMode) throws SQLException {

        //如果这里使用事务的话，需要用Threadlocal来解决，框架中比如mybatis提供了事务支持
         //1.修改bill表
        int update = billDAO.dmlSQL("update bill set state=? where diningtableid=? and state = '未结账'", payMode, diningTableid);
        if (update <= 0){
            return false;
        }
        //2.修改diningtable表
        //注意：不要直接在这里操作，而应该调用DiningTableService 方法,体现各司其职
        //如果交完钱，diningtableService里的id对应的状态为空

        if (!diningTableService.updateDiningTableToFree(diningTableid,"空")){
            return false;
        }

        return true;
    }


}
