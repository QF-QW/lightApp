package com.QW.dao.ActiveListDao;

import com.alibaba.fastjson.JSONArray;

public interface ActiveListDao {

    /**
     *初始活动创建
     *
     * @param value : 存储的数据数组
     * */
    public Integer createActive(Object[] value);

    /*活动查询*/

    public JSONArray searchActiveListDao(Integer pageNo, Integer pageSize);

    public boolean searchJoin(Object[] value);

    public int joinInDao(Object[] value);
}
