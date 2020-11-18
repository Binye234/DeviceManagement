package com.hld.service;

import com.hld.bean.DataInfo;
import com.hld.bean.UserInfo;

import java.util.List;

public interface ManageService {
    public List<DataInfo> getData(int user_id);

    public boolean deleteInfo(int id,int user_id);

    public boolean addInfo(int user_id,String user_name,String reason,String begin_time);

    public boolean isBegin_time(String begin_time);

    public List<DataInfo> getAllData();
}
