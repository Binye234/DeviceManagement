package com.hld.bean;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 9:42
 */
public class User {
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

    public int getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(int admin_flag) {
        this.admin_flag = admin_flag;
    }

    private int id;
    private String login_name;
    private String user_name;
    private int admin_flag;
}
