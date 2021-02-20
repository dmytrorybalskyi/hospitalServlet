package org.example.service;

import org.example.model.dao.AccountDAO;
import org.example.model.dao.DAOFactory;
import org.example.model.entity.Account;

public class AccountService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public Account findByLogin(String login){
       AccountDAO accountDAO = daoFactory.createAccountDAO();
       return  accountDAO.findByLogin(login);
    }
}
