package org.example.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public interface GenericDAO<T> extends AutoCloseable{
    T create (T entity) throws SQLException;
    T finById(int id);
    List<T> findAll();
    boolean update(T entity);
    void close();
    void close(ResultSet resultSet);
    void close(PreparedStatement preparedStatement);
}
