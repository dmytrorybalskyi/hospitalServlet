package org.example.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String language = req.getParameter("lang");
        if(language==null){
            req.getSession().setAttribute("lang","en");
        }
        req.getSession().setAttribute("lang",language);
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
