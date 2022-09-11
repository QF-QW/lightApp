package com.QW.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    /*临时*/
    /*令牌*/
    private String token;

    /*长期存储*/
    /*id:主要识别方式*/
    private int id;
    /*真实名字*/
    private String realName;
    /*昵称*/
    private String nickName;
    /*性别*/
    private int sex;
    /*网薪*/
    private float money;
    /*头像url*/
    private String headUrl;
    /*易班注册时间*/
    private String yiBanRegtime;
    /*学校id*/
    private int schoolId;
    /*学校姓名*/
    private String schoolName;

    /*自定义*/
    /*成功完成的活动数*/
    private int activeCount;
    /*发言的次数*/
    private int speak_count;
    /*来到我们应用的时间*/
    private String createTime;
    /*解析的课程Json字符串对象*/
    private JSONArray course;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        sex = sex;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getYiBanRegtime() {
        return yiBanRegtime;
    }

    public void setYiBanRegtime(String yiBanRegtime) {
        this.yiBanRegtime = yiBanRegtime;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getSpeak_count() {
        return speak_count;
    }

    public void setSpeak_count(int speak_count) {
        this.speak_count = speak_count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public JSONArray getCourse() {
        return course;
    }

    public void setCourse(JSONArray course) {
        this.course = course;
    }


    public JSONObject getUserJson(){
        JSONObject userJson = (JSONObject)new JSONObject();
        userJson.put("id",id);
        userJson.put("realName",realName);
        userJson.put("nickName",nickName);
        userJson.put("sex",sex);
        userJson.put("money",money);
        userJson.put("headUrl",headUrl);
        userJson.put("yiBanRegtime",yiBanRegtime);
        userJson.put("schoolId",schoolId);
        userJson.put("schoolName",schoolName);

        userJson.put("activeCount",activeCount);
        userJson.put("speak_count",speak_count);
        userJson.put("createTime",createTime);
        userJson.put("course",course);

        return userJson;

    }

    public static void main(String[] args) {
        JSONArray objects = new JSONArray();
        objects.add("你好世界");

        JSONArray parse = (JSONArray)JSONArray.parse(objects.toString());

        System.out.println(parse.get(0));
    }

}
