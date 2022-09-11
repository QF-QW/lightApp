package com.QW.dao.ActiveListDao;

import com.QW.dao.BaseDao;
import com.QW.dao.UserDao.UserDaoImpl;
import com.QW.pojo.ActiveList;
import com.QW.pojo.Societies;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveListDaoImpl implements ActiveListDao {
    @Override
    public Integer createActive(Object[] value) {
        Connection connection = BaseDao.getConnection();

        String sql = "insert into activity (id,create_id, active_name, introduction, class_name, coordinate, day_time, day_time_simple, day, week, people, head_url) value (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            ResultSet resultSet = connection.prepareStatement("select max(id) as id from activity").executeQuery();
            resultSet.next();
            value[0] = resultSet.getInt("Id")+1;



            PreparedStatement statement = connection.prepareStatement(sql);
            int i = BaseDao.executeUpdate(statement, value);
            BaseDao.closeResource(null,statement,null);

            UserDaoImpl userDao = new UserDaoImpl();
            userDao.addCourse((int)value[0], (int)value[1]);

            PreparedStatement statement1 = connection.prepareStatement("insert into active_map value (?,?)");
            BaseDao.executeUpdate(statement1, new Object[]{value[0], value[1]});

            return i;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return -1;
    }

    @Override
    public JSONArray searchActiveListDao(Integer pageNo, Integer pageSize) {
        Connection connection = BaseDao.getConnection();
        String sql = "select * from activity join user on create_id=user.id limit ?,?";
        JSONArray result = new JSONArray();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            Object[] objects = {(pageNo - 1) * pageSize, pageSize};
            ResultSet resultSet = BaseDao.executeQuery(statement, objects);



            ActiveList activeList;
            while(resultSet.next()){
                activeList = new ActiveList();

                activeList.setId(resultSet.getInt("id"));
                activeList.setCreateId(resultSet.getInt("create_id"));
                activeList.setActiveName(resultSet.getString("active_name"));
                activeList.setCoordinate(resultSet.getString("coordinate"));
                activeList.setDayTime(resultSet.getString("day_time"));
                activeList.setDayTimeSimple(resultSet.getString("day_time_simple"));
                activeList.setDay(resultSet.getInt("day"));
                activeList.setWeek(resultSet.getString("week"));
                activeList.setPeople(resultSet.getInt("people"));
                activeList.setIntroduction(resultSet.getString("introduction"));
                activeList.setClassName(resultSet.getString("class_name"));
                activeList.setHeadUrl(resultSet.getString("head_url"));
                activeList.setCreateHeadUrl(resultSet.getString("header_url"));
                activeList.setCrateName(resultSet.getString("nick_name"));

                result.add(activeList);
            }

            BaseDao.closeResource(connection,statement,resultSet);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return result;
    }

    @Override
    public boolean searchJoin(Object[] value){
        Connection connection = BaseDao.getConnection();
        String sql = "select * from active_map where active_id=? and user_id=?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = BaseDao.executeQuery(statement, value);

            if (resultSet.next()){

                BaseDao.closeResource(null,statement,resultSet);

                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return false;
    }

    @Override
    public int joinInDao(Object[] value) {
        Connection connection = BaseDao.getConnection();
        String sql = "insert into active_map value (?,?)";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            int i = BaseDao.executeUpdate(statement, value);

            BaseDao.closeResource(null,statement,null);

            return i;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return -1;
    }
}

