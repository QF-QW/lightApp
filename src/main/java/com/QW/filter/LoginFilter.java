package com.QW.filter;

import cn.hutool.http.Header;
import jakarta.servlet.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements javax.servlet.Filter {
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws javax.servlet.ServletException {

    }

    @Override
    public void doFilter(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, javax.servlet.ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        javax.servlet.http.HttpServletResponse resp = (HttpServletResponse) servletResponse;


        Object user = req.getSession().getAttribute("user");

        if(user==null){
            req.getRequestDispatcher("/Login.do").forward(req,resp);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
