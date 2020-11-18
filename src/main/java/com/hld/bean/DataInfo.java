package com.hld.bean;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/16 22:30
 */
public class DataInfo implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String userName;

    private String reason;

    private String date;
}
