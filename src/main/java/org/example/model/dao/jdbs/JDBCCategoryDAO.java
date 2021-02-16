package org.example.model.dao.jdbs;


import org.example.model.dao.CategoryDAO;
import org.example.model.entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCCategoryDAO implements CategoryDAO {
    private Connection connection;

    public JDBCCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Category create(Category entity) {
        return null;
    }

    @Override
    public Category finById(int id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> result = new LinkedList<>();
        String query = "SELECT * FROM category";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Category category = new Category(resultSet.getInt(1));
                    category.setName(resultSet.getString(2));
                    result.add(category);
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return result;
    }

    @Override
    public boolean update(Category entity) {
        return  true;
    }


    @Override
    public void close() {
        try {
            if (connection != null)
                this.connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
