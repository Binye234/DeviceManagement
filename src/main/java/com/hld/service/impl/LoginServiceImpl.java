package com.hld.service.impl;

import com.hld.bean.User;
import com.hld.dao.impl.SelectDaoImpl;
import com.hld.service.LoginService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 9:49
 */
public class LoginServiceImpl implements LoginService {
    @Override
    public User login(String login_name,String passwd) {
        String sql="select id,login_name,user_name,admin_flag from hld_user where login_name = ? and password =?";
        List list= new SelectDaoImpl().select(sql,login_name,passwd) ;
        ResultSet resultSet=(ResultSet)list.get(1);
        if(resultSet==null){
            return null;
        }
        User user=new User();
        try {
            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setLogin_name(resultSet.getString(2));
                user.setUser_name(resultSet.getString(3));
                user.setAdmin_flag(resultSet.getInt(4));
            }else {
                user=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                ( (Connection)list.get(0)).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
