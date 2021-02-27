package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.logging.Logger;

public class LoginCommand implements Command {
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }
        System.out.println("Login: " + login + " Password: " + password);

        if (CommandUtil.checkUserIsLogged(request, login)) {
            return "/error.jsp";
        }

        Account account = accountService.findByLogin(login);
        if (account == null || !account.getPassword().equals(password)) {
            return "/login.jsp";
        }

        HashSet<Account> loggedUsers = (HashSet<Account>) request.getSession()
                .getServletContext().getAttribute("loggedUsers");
        loggedUsers.add(account);
        request.getSession().setAttribute("account", account);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        return "redirect:" + account.getRole().name() + "/" + account.getRole().name();
    }

}

