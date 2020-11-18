package com.hld.servlet;

import com.hld.bean.User;
import com.hld.service.impl.LoginServiceImpl;
import com.hld.utils.MyMD5Util;
import com.hld.utils.XssFilter;

import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String username=request.getParameter("username");
        username= XssFilter.filterWord(username);
        String password=request.getParameter("password");
        password= MyMD5Util.encrypt(password);
        User user=new LoginServiceImpl().login(username,password);
        if(user==null){
            request.setAttribute("error","用户名或密码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else {
            HttpSession session= request.getSession();
            session.setAttribute("user",user);
            if(user.getAdmin_flag()==0){
                response.sendRedirect("/user/index.jsp");
            }else {
                response.sendRedirect("/admin/index.jsp");
            }
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
