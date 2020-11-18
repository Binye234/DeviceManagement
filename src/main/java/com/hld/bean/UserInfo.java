package com.hld.bean;

import java.io.Serializable;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/14 22:11
 */
public class UserInfo implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    private int id;
    private String login_name;
    private String user_name;

}
