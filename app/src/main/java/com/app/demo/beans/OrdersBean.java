package com.app.demo.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;




public class OrdersBean extends DataSupport implements Serializable {

    public String mTime;
    public String user_id;
    public String user_name;
    public String orderRemark;
    public String zhifu;

    private String goods_id;
    private String goods_name;
    private double goods_price;

    private int goods_pic;
    private String goods_type;
    public String remark;







    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }



    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }



    public int getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(int goods_pic) {
        this.goods_pic = goods_pic;
    }
}
