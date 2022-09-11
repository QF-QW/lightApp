package com.QW.servlet;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.yiban.open.common.Friend;
import com.QW.pojo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class YBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if("getFriend".equals(req.getParameter("type"))){
            User user = (User)req.getSession().getAttribute("user");

            Friend friend = new Friend(user.getToken());
            JSONObject friendList = (JSONObject)JSONObject.parse(friend.list(1, 15));
            friendList = friendList.getJSONObject("info");

            JSONArray list = friendList.getJSONArray("list");
            resp.getWriter().println(list);
        }else if("sendApply".equals(req.getParameter("type"))){
            User user = (User)req.getSession().getAttribute("user");

            HashMap<String, Object> param = new HashMap<>();
            param.put("access_token",user.getToken());
            param.put("to_yb_uid",req.getParameter("applyId"));
            param.put("content",req.getParameter("content"));
            HttpResponse execute = HttpRequest.post("https://openapi.yiban.cn/friend/apply").form(param).execute();
            resp.getWriter().println(execute.body());

        }else{
            resp.sendRedirect("/lightapp/static/html/home.html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
