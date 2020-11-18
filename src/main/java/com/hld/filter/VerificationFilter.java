package com.hld.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerificationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        chain.doFilter(req, resp);
        HttpServletRequest request=(HttpServletRequest)req;
        String uri=request.getRequestURI();
        if(uri.contains("login.jsp")||uri.contains("loginservlet")||uri.contains("static")||uri.contains("404.jsp")||uri.contains("500.jsp")){
            chain.doFilter(req, resp);
        }else {
            HttpSession session=request.getSession();
            Object user=session.getAttribute("user");
            if(user==null){
                request.getRequestDispatcher("login.jsp").forward(req,resp);
            }else {
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
