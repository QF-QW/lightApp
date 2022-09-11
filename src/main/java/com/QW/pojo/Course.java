package com.QW.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Course {
    private int activeId;
    private String className;
    private String courseName;
    private int day;
    private JSONObject dayTimeSimple;
    private JSONArray dayTime;
    private String teacherName;
    private JSONArray week;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public JSONObject getDayTimeSimple() {
        return dayTimeSimple;
    }

    public void setDayTimeSimple(JSONObject dayTimeSimple) {
        this.dayTimeSimple = dayTimeSimple;
    }

    public JSONArray getDayTime() {
        return dayTime;
    }

    public void setDayTime(JSONArray dayTime) {
        this.dayTime = dayTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public JSONArray getWeek() {
        return week;
    }

    public void setWeek(JSONArray week) {
        this.week = week;
    }

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }
}
