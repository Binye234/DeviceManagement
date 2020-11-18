package com.hld.dao.impl;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
/**
 * @description:druid模块使用
 * @author: boood
 * @time: 2020/11/10 22:21
 */
 public  class DataStatic {

     static private DataSource dataSource;

     static {
         Properties properties = new Properties();
         InputStream inputStream = DataStatic.class.getClassLoader().getResourceAsStream("druid.properties");
         try {
             properties.load(inputStream);
         }catch (IOException e){
             e.printStackTrace();
         }
         try {
             DataStatic.dataSource= DruidDataSourceFactory.createDataSource(properties); //使用工厂类生成连接池
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     static public Connection getConnection(){
         try {
             return DataStatic.dataSource.getConnection();
         } catch (SQLException e) {
             e.printStackTrace();
             return null;
         }
     }
}
