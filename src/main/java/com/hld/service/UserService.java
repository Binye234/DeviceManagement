package com.hld.service;

import com.hld.bean.UserInfo;

import java.util.List;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 14:12
 */
public interface UserService {
    public boolean changPwd(int id,String passwd);

    public boolean add(String login_name,String user_name,String password);

    public List<UserInfo> getUsers();

    public boolean deleteUser(int id);
}
