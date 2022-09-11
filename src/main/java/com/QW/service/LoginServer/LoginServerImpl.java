package com.QW.service.LoginServer;

import cn.yiban.open.common.User;
import com.QW.dao.UserDao.UserDaoImpl;
import com.alibaba.fastjson.JSONObject;

public class LoginServerImpl implements LoginSever{
    @Override
    public JSONObject getJsonObject(String jsonString) {
        return (JSONObject)JSONObject.parse(jsonString.trim());
    }

    @Override
    public  Object[] getLoginInfo(String token) {
        Object[] result = new Object[10];
        /*token和id还有存储时间*/
        JSONObject tokenInfo = getJsonObject(token);
        token = (String) tokenInfo.get("access_token");

//        {"status":"success",
//        "info":{
//        "yb_usernick":"嘻嘻哈哈",
//        "yb_userhead":"http://img02.fs.yiban.cn/46637422/avatar/user/200",
//        "yb_regtime":"2020-11-17 14:55:27",
//        "yb_schoolid":"34173",
//        "yb_username":"嘻嘻哈哈",
//        "yb_userid":"46637422",
//        "yb_schoolname":"广东药科大学",
//        "yb_birthday":"0000-00-00",
//        "yb_money":"42",
//        "yb_exp":"40",
//        "yb_sex":"M"}}
        /*从易班获取元素*/
        User user = new User(token);
        JSONObject userInfo = getJsonObject(user.me()).getJSONObject("info");

        result[0] = token;
        result[1] = Integer.parseInt((String) userInfo.get("yb_userid"));                      /*转数字*/
        result[2] = userInfo.get("yb_username");
        result[3] = userInfo.get("yb_usernick");
        result[4] ="M".equals(userInfo.get("yb_sex")) ? 1 : 0 ;                      /*转数字*/
        result[5] = Float.parseFloat((String) userInfo.get("yb_money")) ;                    /*转数字*/
        result[6] = userInfo.get("yb_userhead");
        result[7] = userInfo.get("yb_regtime");
        result[8] =Integer.parseInt((String) userInfo.get("yb_schoolid")) ;                 /*转数字*/
        result[9] = userInfo.get("yb_schoolname");
        return result;
    }

    public com.QW.pojo.User getUser(String token, int id){
        UserDaoImpl userDao = new UserDaoImpl();

        com.QW.pojo.User user = userDao.getUserInfo(id);


        user.setToken(token);

        return user;
    }
}
