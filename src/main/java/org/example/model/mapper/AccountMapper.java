package org.example.model.mapper;

import org.example.model.entity.Account;
import org.example.model.entity.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AccountMapper implements ObjectMapper<Account> {
    @Override
    public Account extractFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account(rs.getInt("id"));
        account.setLogin(rs.getString("login"));
        account.setPassword(rs.getString("password"));
        account.setRole(Roles.valueOf(rs.getString("role")));
        return account;
    }

    @Override
    public Account makeUnique(Map<Integer, Account> cache, Account teacher) {
        return null;
    }
}
