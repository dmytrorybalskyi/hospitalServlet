package org.example.filters;

import org.example.model.entity.Account;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getRequestURI().replaceFirst("/","").replaceAll("/.*", "");
        Account account = (Account) req.getSession().getAttribute("account");
        if (account == null || !path.equals(account.getRole().name())) {
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req,resp);
        }
        filterChain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
