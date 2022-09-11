package com.QW.servlet;

import com.QW.dao.UserDao.UserDaoImpl;
import com.QW.pojo.User;
import com.QW.service.GetCourseServer.GetCourseServer;
import com.QW.service.GetCourseServer.GetCourseServerImpl;
import com.QW.service.LoginServer.LoginServerImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GetCourseServlet extends HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {

        /*如果是第一次请求就是直接获取*/
        if(!"".equals(req.getParameter("reflush"))){
            String code = req.getParameter("userCode");
            String password = req.getParameter("passWord");

            GetCourseServer getCourseServer = new GetCourseServerImpl();
            HashSet<String> courseSource = getCourseServer.getCourseSource(code, password);

            /*数据缺少处理*/
            String[] strings = new String[5];
            strings[4] = "未排教室";


//        ArrayList<JSONObject> result = new ArrayList<>();
            JSONArray result = new JSONArray();
            for (String value : courseSource) {
                String[] courseInfo = value.split(" ");
                System.out.println(Arrays.toString(courseInfo));

                if(courseInfo[2].contains("I")){
                    String[] temp = new String[5];
                    temp[0] = courseInfo[0];
                    temp[1] = courseInfo[1]+courseInfo[2];
                    temp[2] = courseInfo[3];
                    temp[3] = courseInfo[4];
                    temp[4] = courseInfo[5];

                    courseInfo = temp;
                }

                if(courseInfo.length!=5){
                    System.arraycopy(courseInfo, 0, strings, 0, 4);
                    courseInfo = strings;
                }

                /*[3, 植物与植物生理, 汤慧敏, 6-8,11,13-17(周)[03-04-05节], 教C306]*/
                /*[3, 植物与植物生理,I, 汤慧敏, 6-8,11,13-17(周)[03-04-05节], 教C306]*/


                String courseTime = courseInfo[3];

//            System.out.println("星期："+courseInfo[0]);
//            System.out.println("课程："+courseInfo[1]);
//            System.out.println("姓名："+courseInfo[2]);
//            System.out.println("教室："+courseInfo[4]);
//            System.out.println("Week:"+Arrays.deepToString(getCourseServer.getWeekTime(courseTime)));
//            System.out.println("Day:"+Arrays.deepToString(getCourseServer.getDayTime(courseTime)));
                int[][] dayTime = getCourseServer.getDayTime(courseTime);
                /*Day改为开始时间 top 、 height吧*/

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("day",Integer.parseInt(courseInfo[0]));
                jsonObject.put("courseName",courseInfo[1]);
                jsonObject.put("teacherName",courseInfo[2]);
                jsonObject.put("className",courseInfo[4]);
                jsonObject.put("week",getCourseServer.getWeekTime(courseTime));
                jsonObject.put("dayTime",dayTime);

                /*8:30开始*/
                int top = ((dayTime[0][0]-8)*60 + (dayTime[0][1]-30))*2;
                int height = ((dayTime[1][0] - dayTime[0][0]) * 60 + (dayTime[1][1] - dayTime[0][1]))*2-5;

                JSONObject dtJson = new JSONObject();
                dtJson.put("top",top);
                dtJson.put("height",height);
                jsonObject.put("dayTimeSimple",dtJson);

                result.add(jsonObject);

            }
            System.out.println("存储完毕");

            /*获取用户信息进行存储*/
            User user = (User)req.getSession().getAttribute("user");
            String token = user.getToken();
            int id = user.getId();

            /*开始存储*/
            UserDaoImpl userDao = new UserDaoImpl();


            userDao.flushCourse(id,result);

            /*存储完毕进行跳转*/
            LoginServerImpl loginServer = new LoginServerImpl();
            User newUser = loginServer.getUser(token, id);
            /*注册成功*/
            /*共用的话.....,直接存储位置不行吗*/
            this.getServletContext().setAttribute(String.valueOf(id), newUser);
            req.getSession().setAttribute("user", newUser);

            resp.sendRedirect("/lightapp/static/html/course.html");

        }else{
            UserDaoImpl userDao = new UserDaoImpl();
            int id = ((User)req.getSession().getAttribute("user")).getId();
            JSONArray jsonArray = (JSONArray) JSONArray.parse(req.getParameter("course"));
            userDao.flushCourse(id,jsonArray);
        }

    }

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        doGet(req, resp);
    }
}
