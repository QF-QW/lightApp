package com.QW.servlet;

import com.QW.pojo.User;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class GetUserJsonServlet extends HttpServlet {

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");

        if(user==null){
            resp.getWriter().println(new JSONObject().put("state","error"));
        }else{
            resp.getWriter().println(user.getUserJson());
        }
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        doGet(req, resp);
    }

}
