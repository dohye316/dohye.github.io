package com.hspedu.mhl.domain;

import java.util.Date;

/**
 * @author 郑道炫
 * @version 1.0
 */
public class Bill {
    private Integer id;
    private String billid;
    private  Integer menuid;
    private Integer nums;
    private Double money;
    private Integer diningtableid;
    private Date billDate;
    private  String state;
    public Bill(){

    }
    public Bill(Integer id, String billid, Integer menuid, Integer nums, Double money, Integer diningtableid, Date billDate, String state) {
        this.id = id;
        this.billid = billid;
        this.menuid = menuid;
        this.nums = nums;
        this.money = money;
        this.diningtableid = diningtableid;
        this.billDate = billDate;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getDiningtableid() {
        return diningtableid;
    }

    public void setDiningtableid(Integer diningtableid) {
        this.diningtableid = diningtableid;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return  id +"\t\t\t"+ menuid + "\t\t"+nums + "\t\t\t"+money +"\t"+ diningtableid +"\t" + billDate +"\t"+ state;

    }
}
