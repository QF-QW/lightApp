package com.QW.service.ActiveListServer;

import com.QW.pojo.ActiveList;
import com.QW.pojo.Societies;
import com.alibaba.fastjson.JSONObject;

public interface ActiveListServer {

    public void createActiveList(ActiveList activeList);

    public JSONObject getActiveList(Integer pageNo);

    public boolean searchJoinServer(int activeId,int userId);

    public int joinServer(int activeId,int userId);

}
