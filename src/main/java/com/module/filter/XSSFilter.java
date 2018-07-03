package com.module.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS攻击过滤
 */
@Component
public class XSSFilter implements Filter {
//    @Resource(name = "serviceFactory")
//    private ServiceFactory serviceFactory;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) req), res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
