package com.QW.service.GetCourseServer;

import java.util.HashSet;

public interface GetCourseServer {
    /**
     * 1.获取原始课表数据，哈希表存储了所有的课程字符串还未使用"\n"剪切
     * 2.课程日期添加
     * 3.未排课室替换
     * @param userCode :账号
     * @param passWord :密码
     * @return :返回存储数据的HashSet
     * */

    public HashSet<String> getCourseSource(String userCode,String passWord);

    /**
     * 单个行数据处理，获取了日期时间
     * @param courseTime : 单条存储了星期、日期的混合原始数据
     * @return :课程当日的时间数组，存储两个数组，第一个是开始时间，第二个是结束时间
     * */
    public int[][] getDayTime(String courseTime);

    /**
     * 单个行数据处理，获取了星期时间
     * @param courseTime : 单条存储了星期、日期的混合原始数据
     * @return :课程当日的时间数组，存储嵌套数组，返回多个数组，数组单个数据的为一周，多个数据的为区间
     * */
    public int[][] getWeekTime(String courseTime);


}
