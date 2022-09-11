package com.QW.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class GetPageServlet extends HttpServlet {

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        String page = req.getParameter("page");
        String xPjax = req.getHeader("X-PJAX");
        resp.setHeader("content-type","text/html; Charset=utf-8");
        System.out.println(xPjax);

        /*判断是不是直接发的请求*/
        if (page == null || "".equals(page)) {
            resp.sendRedirect("/lightapp/static/html/home.html");
            /*判断是不是pjax的请求*/
        }else{
            if ("true".equals(xPjax)){

                /*pjax请求*/

                switch (page){
                    case "home" : {
                        req.getRequestDispatcher("/static/html/homePjax.html").forward(req,resp);
                    }break;
                    case "course" :{
                        req.getRequestDispatcher("/static/html/coursePjax.html").forward(req,resp);
                    }break;
                    case "activeList":{
                        req.getRequestDispatcher("/static/html/activePjax.html").forward(req,resp);
                    }break;
                    default:
                        resp.sendRedirect("/lightapp/static/html/home.html");
                }
            }else{

                /*带参数请求*/
                switch (page){
                    case "home" : {
                        resp.sendRedirect("/lightapp/static/html/home.html");
                    }break;
                    case "course" : {
                        resp.sendRedirect("/lightapp/static/html/course.html");
                    }break;
                    case "activeList" : {
                        resp.sendRedirect("/lightapp/static/html/activeList.html");
                    }break;
                    default:{
                        resp.sendRedirect("/lightapp/static/html/home.html");
                    }
                }

            }
        }




    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
         doGet(req, resp);
    }
}
