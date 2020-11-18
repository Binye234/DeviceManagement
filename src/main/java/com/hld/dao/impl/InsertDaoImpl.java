package com.hld.dao.impl;

import com.hld.dao.InsertDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/12 22:48
 */
public class InsertDaoImpl implements InsertDao {
    @Override
    public boolean insert(String sql, Object... arg) {
        Connection con = DataStatic.getConnection();
        if (con == null) {
            return false;
        }
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            int i=1;
            for (Object o:arg){
                statement.setObject(i,o);
                i++;
            }
            boolean flag= statement.execute();
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
