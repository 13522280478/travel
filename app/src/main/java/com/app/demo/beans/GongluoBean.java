package com.app.demo.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;


public class GongluoBean extends DataSupport implements Serializable  {

    public String ids;

    public int pic;
    public String name;
    public String content;
    public String user_id;

    public String like;
    public int num;
    public double price;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
