package org.example.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path =  req.getRequestURI();
        Integer id = Integer.valueOf(path.replaceAll(".*/",""));
        req.getSession().setAttribute("id",id);
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
