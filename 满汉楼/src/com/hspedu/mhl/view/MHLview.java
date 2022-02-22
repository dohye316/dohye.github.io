package com.hspedu.mhl.view;

import com.hspedu.mhl.domain.*;
import com.hspedu.mhl.service.BillService;
import com.hspedu.mhl.service.DiningTableService;
import com.hspedu.mhl.service.EmployeeService;
import com.hspedu.mhl.service.MenuService;
import com.hspedu.mhl.utils.Utility;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 郑道炫
 * @version 1.0
 */


public class MHLview {
    //显示主菜单
     private boolean answer=true;
     private String key ="";
     //定义EmployeeService 属性
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();
    public static void main(String[] args) throws SQLException {
        MHLview mhLview = new MHLview();
        mhLview.mainMenu();
       //完成结账
    //
    }  public void payBill() throws SQLException {
        System.out.println("==========结账服务===========");
        System.out.println("请选择要结账的餐桌编号（-1退出）：");
        int diningtableid = Utility.readInt();
        if (diningtableid == -1){
            System.out.println("============取消结账===========");
            return;
        }
        //验证餐桌是否存在
        DiningTable diningTableByid = diningTableService.getDiningTableByid(diningtableid);
        if (diningTableByid == null){
            System.out.println("===============结账的餐桌不存在===============");
           return;
        }
        //验证餐桌是否有需要结账的菜单
        if (!billService.hasPayBillDiningTableId(diningtableid)){
            System.out.println("================结账的餐桌不存在===========");
        return;
        }

        System.out.println("请选择结账方式（现金/支付宝/微信）回车表示退出（-1退出）：");
        String paymode = Utility.readString(20, "");
        //说明如果回车，就是返回""
        if ("".equals(paymode)){
            System.out.println("================输入错误取消结账===============");
         return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y'){
            //调用billservice
          if (billService.payBill(diningtableid,paymode)) {
              System.out.println("完成结账");
          }else {
              System.out.println("结账失败");
          }

        }else {

            System.out.println("==========取消结账============");
        }
    }
    //显示账单信息
    public void listBill() throws SQLException {
        System.out.println("显示账单");
        List<MultiTableBean> multiTableBeans = billService.list2();

        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t状态\t\t菜品名\t\t价格");
        for (MultiTableBean multiTableBean :multiTableBeans) {
            System.out.println(multiTableBean);
        }
        System.out.println("显示完毕");

    }
    //完成点餐
    public void orderMenu() throws SQLException {
        System.out.println("===============点餐服务===========");
        System.out.println("输入点餐的桌号(-1退出)");
        int orderDiningTableid = Utility.readInt();
        if (orderDiningTableid == -1){
            System.out.println("取消点餐");
              return;
        }
        System.out.println("输入点餐的菜品号(-1退出)");
        int orderMenuid = Utility.readInt();
        if (orderMenuid == -1){
            System.out.println("取消点餐");
            return;
        }
        System.out.println("输入点餐的菜品量(-1退出)");
        int orderNums = Utility.readInt();
        if (orderNums == -1){
            System.out.println("取消点餐");
        }
        //验证餐桌号是否存在
        DiningTable diningTableByid = diningTableService.getDiningTableByid(orderDiningTableid);
        if (diningTableByid == null){
            System.out.println("餐桌号不存在");
            return;
        }
        //验证菜品编号
        Menu menuById = menuService.getMenuById(orderMenuid);
        if (menuById==null){
            System.out.println("菜品号不存在");
            return;
        }
        //点餐
        if (billService.orderMenu(orderMenuid,orderNums,orderDiningTableid)) {
            System.out.println("点餐成功");

        }else{
            System.out.println("点餐失败");
        }


    }
    //显示菜品
    public void listMenu() throws SQLException {
        List<Menu> list = menuService.list();
        System.out.println("菜品编号\t菜品名\t\t类别\t\t价格");
        for (Menu menu :list) {

            System.out.println(menu);
        }

    }
    //完成订桌
    public void orderDiningTable() throws SQLException {
        System.out.println("===========预定餐桌==========");
        System.out.println("请选择要预定的餐桌编号（-1退出）");
        int orderId = Utility.readInt();
        if (orderId == -1){
            System.out.println("==========取消预定套餐=========");
            diningTableService.updateDiningtablestate(orderId,"");
            return;
        }
        //该方法得到的是Y或者N

        char c = Utility.readConfirmSelection();
        if (c== 'Y'){//要预定
            //根据orderId返回对应DiningTable对象，如果为null，说明该对象不存在
            DiningTable diningTableByid = diningTableService.getDiningTableByid(orderId);
            if (diningTableByid == null){
                System.out.println("你预定的餐桌不存在");
                return;
            }
            //判断该餐桌的状态是否“空‘状态
            if (!("空".equals(diningTableByid.getState()))){
                System.out.println("============预定餐桌已被预定=========");
                return;
            }
            System.out.println("预定人的名字：");
            String orderName = Utility.readString(50);
            System.out.println("预定人的电话：");
            String orderTel = Utility.readString(50);



            //这时说明可以真的预定，更新餐桌状态
           if( diningTableService.orgerDiningTable(orderId,orderName,orderTel)){
               System.out.println("预定成功");
            }else {
               System.out.println("预定失败");}
        }else{
            System.out.println("==========取消订餐==========");
        }
    }
    public void mainMenu() throws SQLException {
       while(answer){

            System.out.println("================满汉楼============");
            System.out.println("\t\t1.登录满汉楼");
            System.out.println("\t\t2.退出满汉楼");
            System.out.println("\t\t退出满汉楼系统（选0）");
            System.out.println("\t\t请输入你的选择:");
           key = Utility.readString(10);
           System.out.println(key);

            switch (key){
                case "1":
                    System.out.println("输入员工号");
                    String empid = Utility.readString(50);
                    System.out.println("输入密码");
                    String pwd = Utility.readString(50);
                    Employee employee= employeeService.getEmployeeByIdAndPwd(empid, pwd);
                    if (employee !=null){ //到数据库判断
                        System.out.println();

                        System.out.println("==========登录成功["+employee.getName()+"]========");
                        //显示二级菜单,这里二级菜单也是循环操作
                        System.out.println("=========满汉楼二级菜单========");
                        secondeMenu();

                    }else{
                        System.out.println("===========登录失败=========");
                    }
                    System.out.println("登录满汉楼");


                    break;
                case "2":
                    answer=false;
                break;
                default:
                    System.out.println("您的输入有误");

            }
        }

    }public void diningTablelist() throws SQLException {
        List<DiningTable> list = diningTableService.List();
        System.out.println("\n餐桌编号\t\t餐桌状态\t\t预定人名字\t\t预定人电话");
        for (DiningTable diningTable :list) {
            System.out.println(diningTable.getId()+"\t\t\t"+diningTable.getState()+"\t\t\t"+diningTable.getOrderName()+"\t\t\t"+diningTable.getOrderTel());
        }
    }
    

    public void secondeMenu() throws SQLException {
     do {  System.out.println("============满汉楼二级菜单===========");
        System.out.println("1.显示餐桌状态");
        System.out.println("2.预定餐桌");
        System.out.println("3.显示所有菜品");
        System.out.println("4.点餐服务");
        System.out.println("5.查看账单");
        System.out.println("6.结账");
        System.out.println("9.退出满汉楼");
         key = Utility.readString(10);
         System.out.println(key);
         switch (key){
             case "1":
                 System.out.println("1.显示餐桌状态");
                 diningTablelist();
                 break;
             case "2":
                 System.out.println("2.预定餐桌");
                 orderDiningTable();
                 break;
             case "3":
                 System.out.println("3.显示所有菜品");
                 listMenu();
                 break;
             case "4":
                 System.out.println("4.点餐服务");
               orderMenu();
                 break;
             case "5":
                 System.out.println("5.查看账单");
                 listBill();
                 break;
             case "6":
                 System.out.println("6.结账");
                 payBill();
                 break;
             case "9":
                 answer=false;
                 System.out.println("退出满汉楼");
                 break;
         }
      }while(answer);
    }

}
