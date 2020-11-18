package com.hld.service;


import com.hld.bean.User;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 9:41
 */
public interface LoginService {
    public User login(String login_name,String passwd);
}
