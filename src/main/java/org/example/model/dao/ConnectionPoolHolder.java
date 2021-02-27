package org.example.model.dao;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:mysql://localhost:3306/hospitalservlet?serverTimezone=Europe/Kiev");
                    ds.setUsername("root");
                    ds.setPassword("Xeraxera1997");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection(){
        try {
            return  ConnectionPoolHolder.getDataSource().getConnection();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
        return null;
    }

}
