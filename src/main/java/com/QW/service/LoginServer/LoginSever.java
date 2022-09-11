package com.QW.service.LoginServer;

import com.QW.pojo.User;
import com.alibaba.fastjson.JSONObject;

public interface LoginSever {
    /**
     * 通过字符串解析出Json
     * @param jsonString : json的字符串
     * @return : json的对象
     * */
    public JSONObject getJsonObject(String jsonString);

    /**
     * 获取从易班得到的对象
     *
     * @param token : 令牌
     * @return : 返回包括所有元素的对象数组
     * */

    public Object[] getLoginInfo(String token);

    /**
     * 获取本地的元素,数据库的是13个对象，加上token总共14个对象
     *
     * @param token : 易班令牌
     * @param id : 用户id
     * @return : user对象
     * */
    public User getUser(String token, int id);

}
