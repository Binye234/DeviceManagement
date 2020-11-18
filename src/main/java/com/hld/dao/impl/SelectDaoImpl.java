package com.hld.dao.impl;

import com.hld.dao.SelectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/12 22:50
 */
public class SelectDaoImpl implements SelectDao {
    @Override
    public List select(String sql, Object... arg) {
        Connection con = DataStatic.getConnection();
        if (con == null) {
            return null;
        }
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            int i = 1;
            for (Object o : arg) {
                statement.setObject(i, o);
                i++;
            }
            ResultSet resultSet = statement.executeQuery();
            List list = new ArrayList();
            list.add(con);
            list.add(resultSet);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

