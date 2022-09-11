package com.QW.service.SocietiesServer;

import com.QW.pojo.Societies;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SocietiesServer  {
    /**
     * 从请求中获取用户提交信息，之后创建社团对象存入数据库
     *
     * @param societies : 处理之后的社团申请对象（不存在id）
     */
    public void createSocieties(Societies societies);

    /**
     * 返回所有社团的列表
     *
     *
     * */

    public JSONObject getSocietiesList(Integer pageNo);

    public boolean searchJoinServer(int societiesId,int userId);

    public int joinServer(int societiesId,int userId);

}
