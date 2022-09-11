package com.QW.service.ActiveListServer;

import com.QW.dao.ActiveListDao.ActiveListDaoImpl;
import com.QW.dao.UserDao.UserDaoImpl;
import com.QW.pojo.ActiveList;
import com.QW.servlet.Init;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ActiveListServerImpl implements ActiveListServer{
    @Override
    public void createActiveList(ActiveList activeList) {
        Object[] values = new Object[12];

        values[1] = activeList.getCreateId();
        values[2] = activeList.getActiveName();
        values[3] = activeList.getIntroduction();
        values[4] = activeList.getClassName();
        values[5] = activeList.getCoordinate();
        values[6] = activeList.getDayTime();
        values[7] = activeList.getDayTimeSimple();
        values[8] = activeList.getDay();
        values[9] = activeList.getWeek();
        values[10] = activeList.getPeople();
        values[11] = activeList.getHeadUrl();

        ActiveListDaoImpl activeListDao = new ActiveListDaoImpl();
        activeListDao.createActive(values);

    }

    @Override
    public JSONObject getActiveList(Integer pageNo) {
        JSONObject result = new JSONObject();
        ActiveListDaoImpl activeListDao = new ActiveListDaoImpl();
        JSONArray jsonArray = activeListDao.searchActiveListDao(pageNo, Init.pageSize);

        if(jsonArray.size()==Init.pageSize){
            result.put("message","full");
            result.put("value",jsonArray);

        }else if(jsonArray.size()<Init.pageSize && jsonArray.size()>0 ){
            result.put("message","end");
            result.put("value",jsonArray);
        }else{
            result.put("message","null");
            result.put("value",jsonArray);
        }
        return result;
    }

    @Override
    public boolean searchJoinServer(int activeId, int userId) {
        ActiveListDaoImpl activeListDao = new ActiveListDaoImpl();
        Object[] objects = {activeId,userId};
        return activeListDao.searchJoin(objects);
    }

    @Override
    public int joinServer(int activeId, int userId) {
        Object[] values = {activeId,userId};
        new UserDaoImpl().addCourse(activeId, userId);

        ActiveListDaoImpl activeListDao = new ActiveListDaoImpl();

        return activeListDao.joinInDao(values);
    }
}
