package com.hld.servlet;

import com.alibaba.fastjson.JSONObject;
import com.hld.bean.User;
import com.hld.bean.UserInfo;
import com.hld.dao.impl.DeleteDaoImpl;
import com.hld.service.UserService;
import com.hld.service.impl.UserServiceImpl;
import com.hld.utils.MyMD5Util;
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

public class UserServlet extends BaseServlet {
    public void changPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("password");
        pwd = MyMD5Util.encrypt(pwd);
        UserService service = new UserServiceImpl();
        int id = ((User) request.getSession().getAttribute("user")).getId();
        boolean flag = service.changPwd(id, pwd);
        if (flag) {
            //返回文本数据
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("密码修改成功".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } else {
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("密码修改失败".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }
    }

    public void layout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String login_name = request.getParameter("login_name");
        String user_name = request.getParameter("user_name");
        String passwd = request.getParameter("password");

        if(login_name==null||login_name.equals("")||user_name==null||user_name.equals("")||passwd==null||passwd.equals("")){
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("用户添加失败".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }else {
            login_name = XssFilter.filterWord(login_name);
            user_name = XssFilter.filterWord(user_name);
            passwd = MyMD5Util.encrypt(passwd);
            Object o = request.getSession().getAttribute("user");
            User user = (User) o;
            if (user.getAdmin_flag() == 1) {
                new UserServiceImpl().add(login_name, user_name, passwd);
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("用户添加成功".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            } else {
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("无添加用户权限".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }
        }

    }

    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        User user=(User)(session.getAttribute("user"));
        if(user.getAdmin_flag()==0){
            return;
        }
        List<UserInfo> users=new UserServiceImpl().getUsers();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wirte = null;
        JSONObject json = new JSONObject();
        try {
            wirte = response.getWriter();
                json.put("total", users.size());
                json.put("rows", users);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            wirte.print(json);
            wirte.flush();
            wirte.close();
        }
    }

    public void resetpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session=request.getSession();
        User user=(User)(session.getAttribute("user"));
        String id_str=request.getParameter("reset-password-id");
        String passwd=request.getParameter("reset-password");
        if(id_str==null||id_str.equals("")||passwd==null||passwd.equals("")){
            return;
        }
        int id=Integer.parseInt(id_str);
        passwd=MyMD5Util.encrypt(passwd);
        if(user.getAdmin_flag()==1){
            boolean flag=new UserServiceImpl().changPwd(id,passwd);
            if(flag){
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("重置密码成功".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }else {
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("重置密码失败".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }
        }else {
            response.setContentType("text/plain;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("无重置用户密码权限".getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            HttpSession session=request.getSession();
            User user=(User)(session.getAttribute("user"));
            String id_str=request.getParameter("deleteId");
            if(id_str==null||id_str.equals("")){
                return;
            }
            int id=Integer.parseInt(id_str);
            if(user.getAdmin_flag()==1){
                boolean flag=new UserServiceImpl().deleteUser(id);
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("删除用户成功".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }else {
                response.setContentType("text/plain;charset=utf-8");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("无删除用户权限".getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
            }

    }
}

