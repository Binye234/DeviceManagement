package com.hld.service.impl;

import com.hld.bean.User;
import com.hld.bean.UserInfo;
import com.hld.dao.impl.DeleteDaoImpl;
import com.hld.dao.impl.InsertDaoImpl;
import com.hld.dao.impl.SelectDaoImpl;
import com.hld.dao.impl.UpdateDaoImpl;
import com.hld.service.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 15:03
 */
public class UserServiceImpl implements UserService {
    @Override
    public boolean changPwd(int id, String passwd) {
        String sql = "update hld_user set password = ? where id = ?";
        int count = new UpdateDaoImpl().Update(sql, passwd, id);
        if (count == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(String login_name, String user_name, String password) {
        String sql="insert into hld_user values (null,?,?,?,0)";
        boolean flag=new InsertDaoImpl().insert(sql,login_name,user_name,password);
        return false;
    }
    @Override
    public List<UserInfo> getUsers(){
        String sql="SELECT id,login_name,user_name FROM hld_user where admin_flag = 0";
        List list= new SelectDaoImpl().select(sql) ;
        ResultSet resultSet=(ResultSet)list.get(1);
        if(resultSet==null){
            return null;
        }
        List<UserInfo> result=new ArrayList<>();
        try {
            while (resultSet.next()){
                UserInfo info=new UserInfo();
                info.setId(resultSet.getInt(1));
                info.setLogin_name(resultSet.getString(2));
                info.setUser_name(resultSet.getString(3));
                result.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ((Connection)list.get(0)).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteUser(int id) {
        String sql="delete from hld_user where id= ?";
        boolean flag=new DeleteDaoImpl().Delete(sql,id);
        return flag;
    }
}
