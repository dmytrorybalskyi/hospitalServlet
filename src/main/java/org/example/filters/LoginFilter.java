package org.example.filters;


import org.example.model.entity.Account;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Account account = (Account) req.getSession().getAttribute("account");
        if (account != null) {
            HashSet<Account> loggedUsers = (HashSet<Account>) req.getSession()
                    .getServletContext().getAttribute("loggedUsers");
            loggedUsers.remove(account);
            req.getSession().setAttribute("account", null);
            req.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
