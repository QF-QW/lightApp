package com.QW.servlet;

import cn.yiban.open.Authorize;
import com.QW.dao.BaseDao;
import com.QW.dao.UserDao.UserDao;
import com.QW.dao.UserDao.UserDaoImpl;
import com.QW.pojo.User;
import com.QW.service.LoginServer.LoginServerImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class LoginServlet extends HttpServlet {
    String appKey;
    String appSecret;
    String callbackurl;

    /*配置加载*/ {
        Properties properties = new Properties();
        /*通过类加载获取数据*/
        InputStream resourceAsStream = BaseDao.class.getClassLoader().getResourceAsStream("Yb.properties");


        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        appKey = (String) properties.get("appKey");
        appSecret = (String) properties.get("appSecret");
        callbackurl = (String) properties.get("callbackurl");
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        Authorize au = new Authorize(appKey, appSecret);
        String code = req.getParameter("code");

        /*查看是否为第一次登录*/
        if (code == null || code.isEmpty()) {
            String url = au.forwardurl(callbackurl, "test", Authorize.DISPLAY_TAG_T.WEB);
            resp.sendRedirect(url);
        } else { /*重定向之后*/

            if (req.getSession().getAttribute("user") != null) {
                /*为带code二次请求*/
                System.out.println("已经登录请求二次登录");
                resp.sendRedirect("/lightapp/static/html/home.html");
            } else {
                /*获取了token*/
                System.out.println(code+""+callbackurl);
                String token = au.querytoken(code, callbackurl);

                /*获取参数对象*/
                LoginServerImpl loginServer = new LoginServerImpl();
                Object[] loginInfo = loginServer.getLoginInfo(token);

                /*另外存储id*/
                int id = (Integer) loginInfo[1];

                /*数据更新*/
                UserDao userDao = new UserDaoImpl();
                if (userDao.isExist(id)) {
                    Object[] values = new Object[9];
                    System.arraycopy(loginInfo, 2, values, 0, 8);
                    values[8] = id;
                    userDao.setUserInfo(values);

                } else {
                    /*删除令牌存入*/
                    Object[] values = new Object[13];
                    System.arraycopy(loginInfo, 1, values, 0, 9);

                    /*增添现在的时间*/
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateTime = simpleDateFormat.format(date);
                    values[9] = 0;
                    values[10] = 0;
                    values[11] = dateTime;

                    /*创建空json课表*/
                    JSONArray course = new JSONArray();
                    String s1 = course.toString();

                    values[12] = s1;

                    /*存入*/
                    userDao.createUserInfo(values);
                }

                JSONObject tokenInfo = (JSONObject) JSONObject.parse(token);
                token = (String) tokenInfo.get("access_token");

                User user = loginServer.getUser(token, id);
                /*注册成功*/
                /*共用的话.....,直接存储位置不行吗*/
                this.getServletContext().setAttribute(String.valueOf(id), user);
                req.getSession().setAttribute("user", user);

                System.out.println("跳转");
                resp.sendRedirect("/lightapp/static/html/home.html");
            }

        }
    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        doGet(req, resp);
    }

}
