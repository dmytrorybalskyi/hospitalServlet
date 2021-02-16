package org.example.model.dao;

import org.example.model.entity.Account;

public interface AccountDAO extends GenericDAO<Account> {
    Account findByLogin(String login);
}
