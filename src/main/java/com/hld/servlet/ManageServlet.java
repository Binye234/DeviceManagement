package com.hld.servlet;

import com.alibaba.fastjson.JSONObject;
import com.hld.bean.DataInfo;
import com.hld.bean.User;
import com.hld.service.ManageService;
import com.hld.service.impl.ManageServiceImpl;
import com.hld.service.impl.UserServiceImpl;
import com.hld.utils.XssFilter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageServlet extends BaseServlet {
    public void getInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("user"));
        int id = user.getId();
        List<DataInfo> datas = new ManageServiceImpl().getData(id);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wirte = null;
        JSONObject json = new JSONObject();
        try {
            wirte = response.getWriter();
            json.put("total", datas.size());
            json.put("rows", datas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wirte.print(json);
            wirte.flush();
            wirte.close();
        }
    }

    public void deleteInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("user"));
        String id_str = request.getParameter("deleteId");
        if (id_str == null || id_str.equals("")) {
            return;
        }
        int id = Integer.parseInt(id_str);
        int user_id = user.getId();
        boolean flag = new ManageServiceImpl().deleteInfo(id,user_id);
        response.setContentType("text/plain;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("删除信息成功".getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }

    public void addInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("user"));
        String reason= request.getParameter("reason");
        String begin_time=request.getParameter("begin_time");
        if (reason == null || reason.equals("")||begin_time == null || begin_time.equals("")) {
            return;
        }
        int user_id = user.getId();
        String user_name=user.getUser_name();
        reason= XssFilter.filterWord(reason);
        begin_time=XssFilter.filterWord(begin_time);
        if(new ManageServiceImpl().isBegin_time(begin_time)) {
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("日期已经有人登记".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();

        }else {
            boolean flag = new ManageServiceImpl().addInfo(user_id,user_name,reason,begin_time);
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("信息登记成功".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }
    }


    public void getAllData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DataInfo> datas = new ManageServiceImpl().getAllData();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wirte = null;
        JSONObject json = new JSONObject();
        try {
            wirte = response.getWriter();
            json.put("total", datas.size());
            json.put("rows", datas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wirte.print(json);
            wirte.flush();
            wirte.close();
        }
    }
}

