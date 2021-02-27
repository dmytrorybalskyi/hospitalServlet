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
        String hello = "hello";
        String language = req.getParameter("lang");
        if (language == null&&req.getSession().getAttribute("lang")==null) {
            req.getSession().setAttribute("lang", "en");
        }else if(language!=null&&!req.getSession().getAttribute("lang").equals(language)){
            req.getSession().setAttribute("lang", language);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
