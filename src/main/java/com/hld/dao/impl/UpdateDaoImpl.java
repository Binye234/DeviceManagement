package com.hld.dao.impl;

import com.hld.dao.UpdateDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/12 22:52
 */
public class UpdateDaoImpl implements UpdateDao {
    @Override
    public int Update(String sql, Object... arg) {
        Connection con = DataStatic.getConnection();
        if (con == null) {
            return 0;
        }
        try {
            PreparedStatement statement=con.prepareStatement(sql);
            int i=1;
            for (Object o:arg){
                statement.setObject(i,o);
                i++;
            }
            int count= statement.executeUpdate();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
