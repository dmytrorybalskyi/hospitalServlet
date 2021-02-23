package org.example.model.dao.jdbs;

import org.example.model.dao.AccountDAO;
import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.entity.Account;
import org.example.model.entity.Roles;
import org.example.model.mapper.AccountMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCAccountDAO implements AccountDAO {
    private Connection connection;
    private AccountMapper accountMapper = new AccountMapper();

    public JDBCAccountDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Account create(Account account) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO account (login, password, role) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getLogin());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getRole().name());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            ;
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

    }

    @Override
    public Account findByLogin(String login) {
        String query = "SELECT * FROM account WHERE login = (?)";
        Account account = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                account = accountMapper.extractFromResultSet(rs);
            }
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(rs);
            close(preparedStatement);
            close();
        }
        return null;
    }

    @Override
    public Account finById(int id) {
        String query = "SELECT * FROM account WHERE id = ?";
        Account account = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "" + id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                account = accountMapper.extractFromResultSet(rs);
            }
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(rs);
            close(preparedStatement);
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public boolean update(Account entity) {
        return true;
    }

    @Override
    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }
}
