package com.hld.service.impl;

import com.hld.bean.DataInfo;
import com.hld.bean.UserInfo;
import com.hld.dao.impl.DeleteDaoImpl;
import com.hld.dao.impl.InsertDaoImpl;
import com.hld.dao.impl.SelectDaoImpl;
import com.hld.service.ManageService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/16 22:34
 */
public class ManageServiceImpl implements ManageService {
    @Override
    public List<DataInfo> getData(int user_id) {
        String sql = "SELECT id,user_name,reason,begin_time FROM hld_manage where user_id = ? order by begin_time desc limit 50";
        List list = new SelectDaoImpl().select(sql, user_id);
        ResultSet resultSet = (ResultSet) list.get(1);
        if (resultSet == null) {
            return null;
        }
        List<DataInfo> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                DataInfo info = new DataInfo();
                info.setId(resultSet.getInt(1));
                info.setUserName(resultSet.getString(2));
                info.setReason(resultSet.getString(3));
                info.setDate(resultSet.getString(4));
                result.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ((Connection) list.get(0)).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteInfo(int id, int user_id) {
        String sql = "delete from hld_manage where id= ? and user_id =?";
        boolean flag = new DeleteDaoImpl().Delete(sql, id, user_id);
        return flag;
    }

    @Override
    public boolean addInfo(int user_id, String user_name, String reason, String begin_time) {
        String sql = "INSERT INTO hld_manage VALUES(NULL,?,?,?,?)";
        boolean flag = new InsertDaoImpl().insert(sql, user_id, user_name, reason, begin_time);
        return flag;
    }

    @Override
    public boolean isBegin_time(String begin_time) {
        String sql = "SELECT id FROM hld_manage WHERE begin_time = ?";
        List list = new SelectDaoImpl().select(sql, begin_time);
        ResultSet resultSet = (ResultSet) list.get(1);
        if (resultSet == null) {
            return false;
        }
        try {
            resultSet.next();
            int a = resultSet.getInt(1);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                ((Connection) list.get(0)).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<DataInfo> getAllData() {
        String sql = "SELECT id,user_name,reason,begin_time FROM hld_manage  order by begin_time desc limit 50";
        List list = new SelectDaoImpl().select(sql);
        ResultSet resultSet = (ResultSet) list.get(1);
        if (resultSet == null) {
            return null;
        }
        List<DataInfo> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                DataInfo info = new DataInfo();
                info.setId(resultSet.getInt(1));
                info.setUserName(resultSet.getString(2));
                info.setReason(resultSet.getString(3));
                info.setDate(resultSet.getString(4));
                result.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ((Connection) list.get(0)).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
