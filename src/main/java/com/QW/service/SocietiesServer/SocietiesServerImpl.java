package com.QW.service.SocietiesServer;

import com.QW.dao.SocietiesDao.SocietiesDaoImpl;
import com.QW.pojo.Societies;
import com.QW.servlet.Init;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

public class SocietiesServerImpl implements SocietiesServer{
    @Override
    public void createSocieties(Societies societies) {

        Object[] value = new Object[6];
        value[1] = societies.getCreateId();
        value[2] = societies.getSocietiesName();
        value[3] = societies.getIntroduction();
        value[4] = societies.getRoomCoordinate();
        value[5] = societies.getHeaderUrl();


        new SocietiesDaoImpl().createSocietiesDao(value);
    }

    @Override
    public JSONObject getSocietiesList(Integer pageNo) {
        JSONObject result = new JSONObject();
        SocietiesDaoImpl societiesDao = new SocietiesDaoImpl();
        JSONArray jsonArray = societiesDao.searchSocietiesListDao(pageNo, Init.pageSize);

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
    public boolean searchJoinServer(int societiesId, int userId) {
        SocietiesDaoImpl societiesDao = new SocietiesDaoImpl();
        Object[] objects = {societiesId,userId};
        return societiesDao.searchJoin(objects);
    }

    @Override
    public int joinServer(int societiesId, int userId) {
        Object[] values = {societiesId,userId,1};


        SocietiesDaoImpl societiesDao = new SocietiesDaoImpl();

        return societiesDao.joinInDao(values);
    }
}
