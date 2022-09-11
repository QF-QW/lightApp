package com.QW.dao.UserDao;

import com.QW.pojo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public interface UserDao {

    /**
     * 存储长期存储对象
     * 存储对象8 【有id，所以存储9对象】
     * 会调用isExist反流,以此来判断调用创造还是存储
     * @param values : 保存存储数据的对象数组,id存储到最后一位
     * @return :成功返回 1 | 失败为 -1
     * */
    public int setUserInfo(Object[] values);


    /**
     * 主动查询数据库中用户的对象
     *
     * @param id : 用户id
     * @return : 用户对象
     * */
    public User getUserInfo(int id);


    /**
     * 判断是否存在改用户
     *
     * @param id : 用户的id
     * @return 存在返回 true | 如果查询错误、不存在，返回 false
     * */
    public boolean isExist(int id);


    /**
     * 主要是第一次使用时候触发
     * 存储对象 13
     *
     * @param values :存储用户数据的对象数组
     * @return 成功返回 1 | 失败为 -1
     * */
    public int createUserInfo(Object[] values);

    /**
     * 课表更新
     *
     * @param id : 用户id
     * @param result : 用户的课程数据
     *
     * @return : 成功返回1 | 失败为-1
     * */
    public int flushCourse(int id , JSONArray result);

    public int addCourse(int activeId,int userId);

}
