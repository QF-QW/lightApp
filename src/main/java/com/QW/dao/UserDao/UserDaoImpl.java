package com.QW.dao.UserDao;

import com.QW.dao.BaseDao;
import com.QW.pojo.ActiveList;
import com.QW.pojo.Course;
import com.QW.pojo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    @Override
    public int setUserInfo(Object[] values) {
        Connection connection = BaseDao.getConnection();
        String sql = "update user set real_name=?,nick_name=?,sex=?,money=?,header_url=?,yiBan_regtime=?,school_id=?,school_name=? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int result = BaseDao.executeUpdate(statement, values);
            BaseDao.closeResource(connection, statement, null);

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BaseDao.closeResource(connection, null, null);
        return -1;

    }

    @Override
    public User getUserInfo(int id) {
        Connection connection = BaseDao.getConnection();
        String sql = "select * from user where id = ?";

        User user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = BaseDao.executeQuery(statement, new Object[]{id});

            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setRealName(resultSet.getString("real_name"));
            user.setNickName(resultSet.getString("nick_name"));
            user.setSex(resultSet.getInt("sex"));
            user.setMoney(resultSet.getFloat("money"));
            user.setHeadUrl(resultSet.getString("header_url"));
            user.setYiBanRegtime(resultSet.getString("yiBan_regtime"));
            user.setSchoolId(resultSet.getInt("school_id"));
            user.setSchoolName(resultSet.getString("school_name"));
            user.setActiveCount(resultSet.getInt("active_count"));
            user.setSpeak_count(resultSet.getInt("speak_count"));
            user.setCreateTime(resultSet.getString("create_time"));

            /*将存入的Json数据字符串转换成Json数组*/
            String courseString = resultSet.getString("course");
            JSONArray course = null;
            if (!"".equals(courseString)) {
                System.out.println("触发");
                course = (JSONArray) JSONArray.parse(courseString);
            }


            user.setCourse(course);

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BaseDao.closeResource(connection, null, null);
        return user;
    }

    @Override
    public boolean isExist(int id) {
        Connection connection = BaseDao.getConnection();
        String sql = "select * from user where id = ?";

        /*读取成功*/
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = BaseDao.executeQuery(statement, new Object[]{id});

            boolean result = resultSet.next();
            BaseDao.closeResource(connection, statement, resultSet);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*读取失败*/
        BaseDao.closeResource(connection, null, null);
        return false;
    }

    @Override
    public int createUserInfo(Object[] values) {
        Connection connection = BaseDao.getConnection();
        String sql = "insert into user value (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int result = BaseDao.executeUpdate(statement, values);

            BaseDao.closeResource(connection, statement, null);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BaseDao.closeResource(connection, null, null);
        return -1;
    }

    @Override
    public int flushCourse(int id, JSONArray getResult) {
        Connection connection = BaseDao.getConnection();


        String courseString = getResult.toString();

        String sql = "update user set course = ? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int result = BaseDao.executeUpdate(statement, new Object[]{courseString, id});

            BaseDao.closeResource(connection, statement, null);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public int addCourse(int activeId, int userId) {
        Connection connection = BaseDao.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("select * from activity join user on activity.create_id = user.id where activity.id=?");
            Object[] objects = {activeId};
            ResultSet resultSet = BaseDao.executeQuery(statement, objects);

            /*查找课表更新对象*/
            Course course = new Course();
            while (resultSet.next()) {
                course.setClassName(resultSet.getString("class_name"));
                course.setCourseName(resultSet.getString("active_name"));
                course.setDay(resultSet.getInt("day"));
                course.setDayTime((JSONArray) JSONArray.parse(resultSet.getString("day_time")));
                course.setDayTimeSimple((JSONObject) JSONObject.parse(resultSet.getString("day_time_simple")));
                course.setWeek((JSONArray) JSONArray.parse(resultSet.getString("week")));
                course.setTeacherName(resultSet.getString("nick_name"));
                course.setActiveId(resultSet.getInt("activity.id"));
            }

            User userInfo = new UserDaoImpl().getUserInfo(userId);
            JSONArray jsonArray = userInfo.getCourse();
            jsonArray.add(course);

            BaseDao.closeResource(null,statement,resultSet);
            System.out.println(jsonArray);
            return flushCourse(userId, jsonArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return -1;
    }

    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.addCourse(6,46637422);

    }
}
