package com.QW.dao.SocietiesDao;

import com.QW.pojo.Societies;
import com.alibaba.fastjson.JSONArray;

public interface SocietiesDao {

    /**
     * 创建社团，通过获取信息的数组自动创建Societies
     * @param value : 存储所有信息的数组
     *
     * */
    public void createSocietiesDao(Object[] value);


    /**
     * 搜索列表
     *
     * @param pageNo :页面编号，从1开始
     * @param pageSize : 获取页面数量
     *
     * @return : JSONArray ,后面通过判断长度判断是否可以继续查询
     * */
    public JSONArray searchSocietiesListDao(Integer pageNo, Integer pageSize);

    /**
     * 查询该用户是否加入社团
     * */

    public boolean searchJoin(Object[] value);

    /**
     * 社团绑定
     * */

    public int joinInDao(Object[] value);
}
