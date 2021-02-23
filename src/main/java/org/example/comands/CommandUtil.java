package org.example.comands;

import org.example.model.entity.Account;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class CommandUtil {
    static boolean checkUserIsLogged(HttpServletRequest request, String login) {
        HashSet<Account> loggedUsers = (HashSet<Account>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if (loggedUsers.stream().map(Account::getLogin).anyMatch(login::equals)) {
            return true;
        }
        return false;
    }
}
