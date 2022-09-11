package com.QW.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        /*通过类加载获取数据*/
        InputStream resourceAsStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");


        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * 获取数据连接
     * */
    public static Connection getConnection(){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    /**
     * 数据库公共查询
     * @param statement : 提交对象;
     * @param params : 存放数组对象，用于直接获取多个参数进行提交 ;
     * */
    public static ResultSet executeQuery(PreparedStatement statement,Object[] params) throws SQLException {

        for(int i = 0;i < params.length;i++){
            /*存储数据*/
            statement.setObject(i+1,params[i]);
        }

        return statement.executeQuery();
    }

    /**
     * 数据库公共修改类
     * */
    public static int executeUpdate(PreparedStatement statement,Object[] params) throws SQLException{
        for(int i = 0;i < params.length;i++){
            /*存储数据*/
            statement.setObject(i+1,params[i]);
        }

        return statement.executeUpdate();
    }


    /**数据清除
     * @param connection:连接对象;
     * @param preparedStatement : 释放的提交对象
     * @param resultSet : 返回的结果对象
     * */
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet ){
        boolean flag = true;

        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

}
