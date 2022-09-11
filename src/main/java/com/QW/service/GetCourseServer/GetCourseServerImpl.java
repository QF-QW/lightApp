package com.QW.service.GetCourseServer;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GetCourseServerImpl implements GetCourseServer {
    @Override
    public HashSet<String> getCourseSource(String userCode, String passWord) {
        /*页面获取*/
        HttpResponse execute = HttpRequest.get("https://cas.gdaib.edu.cn/lyuapServer/login").execute();
        String body = execute.body();

        /*页面解析*/
        String lt = ReUtil.findAll(" name=\"lt\" value=\"(.*?)\" />", body, 1).get(0);
        String execution = ReUtil.findAll(" name=\"execution\" value=\"(.*?)\" />", body, 1).get(0);

        /*参数存储*/
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username",userCode);
        loginParam.put("warn","true");
        loginParam.put("lt",lt);
        loginParam.put("execution",execution);
        loginParam.put("_eventId","submit");
        loginParam.put("submit","登录");
        loginParam.put("password",passWord);


        /*重定向链*/
        HttpResponse execute1 = HttpRequest.post("https://cas.gdaib.edu.cn/lyuapServer/login").form(loginParam).execute();
        HttpResponse execute2 = HttpRequest.get(execute1.header("Location")).execute();
        HttpResponse execute3 = HttpRequest.get(execute2.header("Location")).execute();
        HttpResponse execute4 = HttpRequest.get(execute3.header("Location")).execute();
        HttpResponse execute5 = HttpRequest.get(execute4.header("Location")).execute();
        HttpResponse execute6 = HttpRequest.get(execute5.header("Location")).execute();
        HttpResponse execute7 = HttpRequest.get("http://jwxt.gdaib.edu.cn/jsxsd/framework/xsMain.jsp").execute();
        HttpResponse execute8 = HttpRequest.get("https://cas.gdaib.edu.cn/lyuapServer/login?service=http%3A%2F%2Fjwxt.gdaib.edu.cn%2Fjsxsd%2Fframework%2FxsMain.jsp").execute();
        HttpResponse execute9 = HttpRequest.get(execute8.header("Location")).execute();
        HttpResponse execute10 = HttpRequest.get(execute9.header("Location")).execute();
        HttpResponse execute11 = HttpRequest.get(execute10.header("Location")).execute();
//        System.out.println(execute11);

        /*获取课表*/

        HashMap<String, Object> courseParam = new HashMap<>();

        courseParam.put("jx0404id",null);
        courseParam.put("cj0701id",null);
        courseParam.put("zc",null);
        courseParam.put("demo","课表暂未公布，不能查看课表!");
        courseParam.put("xnxq01id","2021-2022-2");
        courseParam.put("sfFD","1");




        String courseBody = HttpRequest.post("http://jwxt.gdaib.edu.cn/jsxsd/xskb/xskb_list.do").form(courseParam).header(Header.HOST,"jwxt.gdaib.edu.cn").header(Header.CONTENT_LENGTH,"183").header(Header.CONTENT_TYPE,"application/x-www-form-urlencoded").execute().body();
//        System.out.println(courseBody);

        Document courseDom = Jsoup.parse(courseBody);

        Elements elements = courseDom.getElementsByClass("kbcontent");
        HashSet<String> hashCourseValue = new HashSet<>();

        for (Element element : elements){
            String webString = element.text().replace("&nbsp", "").trim();

            if (!"".equals(webString)) {

//                System.out.println(element.ownText() + element.getElementsByTag("font").text());

                String weekDay = element.attr("id");
                String day = String.valueOf(weekDay.charAt(weekDay.lastIndexOf("-") - 1));
                String[] values = webString.split("---------------------");

                for(int i = 0;i< values.length;i++){
                    values[i] =day+" "+ values[i].trim();
                }


                hashCourseValue.addAll(Arrays.asList(values));
            }

        }

        return hashCourseValue;
    }

//    String s = " 7,11-17(单周)[06-07节]";
    @Override
    public int[][] getDayTime(String courseTime) {
        courseTime =  courseTime.substring(courseTime.indexOf("["));
        String startTimeStr = courseTime.substring(courseTime.indexOf("[") + 1, courseTime.indexOf("-"));
        String endTimeStr = courseTime.substring(courseTime.lastIndexOf("-") + 1, courseTime.indexOf("节"));
        int[] startTime = new int[2];
        int[] endTime = new int[2];

        switch (startTimeStr){
            case "01" : {
                startTime = new int[]{8,30};
            }break;
            case "03" : {
                startTime = new int[]{10,0};
            }break;
            case "05" :{
                startTime = new int[]{11,30};
            }break;
            case "06":{
                startTime = new int[]{14,0};
            }break;
            case "08":{
                startTime = new int[]{15,30};
            }break;
            case "10":{
                startTime = new int[]{18,30};
            }break;
            case "12":{
                startTime = new int[]{20,0};
            }break;
        }

        switch (endTimeStr){
            case "01" : {
                endTime = new int[]{9,10};
            }break;
            case "02" : {
                endTime = new int[]{9,50};
            }break;
            case "03" : {
                endTime = new int[]{10,40};
            }break;
            case "04" : {
                endTime = new int[]{11,20};
            }break;
            case "05" :{
                endTime = new int[]{12,10};
            }break;
            case "06":{
                endTime = new int[]{14,40};
            }break;
            case "07":{
                endTime = new int[]{15,20};
            }break;
            case "08":{
                endTime = new int[]{16,10};
            }break;
            case "09":{
                endTime = new int[]{16,50};
            }break;
            case "10":{
                endTime = new int[]{19,10};
            }break;
            case "11":{
                endTime = new int[]{19,50};
            }break;
            case "12":{
                endTime = new int[]{20,40};
            }break;
            case "13":{
                endTime = new int[]{21,20};
            }break;
        }

        return new int[][]{startTime,endTime};
    }

    @Override
    public int[][] getWeekTime(String courseTime) {

        String week = courseTime.substring(0,courseTime.indexOf("("));
        String[] weekArray =week.split(",");
//        [6-8,11,13-17]

        /*定义存储数组*/
        int[][] weekTime = new int[weekArray.length][];

        /*数据解析*/
        for(int i = 0;i<weekArray.length;i++){  /*处理多个数据*/

//            逐次判断是否为区间
            int num = weekArray[i].indexOf("-");
            if (num==-1){
                /*为一周时候转换为单数据数组*/
                weekTime[i]= new int[]{Integer.parseInt(weekArray[i].trim())};
            }else{
                String[] weekArraySplit = weekArray[i].split("-");
                /*处理单个数据*/
                weekTime[i]= new int[]{Integer.parseInt(weekArraySplit[0].trim()),Integer.parseInt(weekArraySplit[1].trim())};

            }
        }

        return weekTime;
    }
}
