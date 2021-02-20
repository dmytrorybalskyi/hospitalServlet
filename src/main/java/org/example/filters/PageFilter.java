package org.example.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path =  req.getRequestURI();
        String page = path.replaceAll(".*/admin/page=","");
        if(page.equals("/admin/admin")){
            req.getSession().setAttribute("page", 0);
        }else {
            Integer pageNumber = Integer.valueOf(page);
            req.getSession().setAttribute("page",pageNumber);
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}

