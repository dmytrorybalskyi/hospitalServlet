package org.example.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public interface GenericDAO<T> extends AutoCloseable{
    T create (T entity);
    T findByLogin(String login);
    T finById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
    void close();
    void close(ResultSet resultSet);
    void close(PreparedStatement preparedStatement);
}
