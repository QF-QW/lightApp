package com.QW.dao.SocietiesDao;

import com.QW.dao.BaseDao;
import com.QW.pojo.Societies;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SocietiesDaoImpl implements SocietiesDao{
    @Override
    public void createSocietiesDao(Object[] value) {
        Connection connection = BaseDao.getConnection();


        String sql = "insert into societies (id,create_id, societies_name, introduction, room_coordinate, head_url) value (?,?,?,?,?,?)";

        try {

            ResultSet resultSet = connection.prepareStatement("select max(id) as id from societies").executeQuery();
            resultSet.next();
            value[0] = resultSet.getInt("Id")+1;

            PreparedStatement statement = connection.prepareStatement(sql);
            BaseDao.executeUpdate(statement,value);

            PreparedStatement statement1 = connection.prepareStatement("insert into manage value (?,?,?)");
            Object[] objects = {value[0], value[1], 0};
            BaseDao.executeUpdate(statement1,objects);

            BaseDao.closeResource(null,statement,resultSet);
            BaseDao.closeResource(null,statement1,null);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }



    }

    @Override
    public JSONArray searchSocietiesListDao(Integer pageNo, Integer pageSize) {
        Connection connection = BaseDao.getConnection();
        String sql = "select * from societies join user on societies.create_id = user.id limit ?,?";
        JSONArray result = new JSONArray();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            Object[] objects = {(pageNo - 1) * pageSize, pageSize};
            ResultSet resultSet = BaseDao.executeQuery(statement, objects);



            Societies societies;
            while(resultSet.next()){
                societies = new Societies();
                societies.setSocietiesId(resultSet.getInt("id"));
                societies.setCreateId(resultSet.getInt("create_id"));
                societies.setSocietiesName(resultSet.getString("societies_name"));
                societies.setIntroduction(resultSet.getString("introduction"));
                societies.setRoomCoordinate(resultSet.getString("room_coordinate"));
                societies.setHeaderUrl(resultSet.getString("head_url"));
                societies.setCreateHeadUrl(resultSet.getString("header_url"));
                societies.setCreateName(resultSet.getString("nick_name"));

                result.add(societies);
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
    public boolean searchJoin(Object[] value) {
        Connection connection = BaseDao.getConnection();
        String sql = "select * from manage where societies_id=? and user_id=?";

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
        String sql = "insert into manage value (?,?,?)";

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
