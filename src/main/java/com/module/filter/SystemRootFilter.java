package com.module.filter;

import com.module.config.tool_config.ToolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 获取系统url
 */
@Component
public class SystemRootFilter implements Filter {
//    @Resource(name = "serviceFactory")
//    private ServiceFactory serviceFactory;

    @Autowired
    ToolConfig toolConfig;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        /*if(serviceFactory.getAuthService().getServerRootUrl() == null ){
            HttpServletRequest request = (HttpServletRequest)req;
            String rootUrl = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
            rootUrl = rootUrl.replace("mapi/serverRootUrl","");
            serviceFactory.getAuthService().setServerRootUrl(rootUrl);
            XLog.info(rootUrl,SystemRootFilter.class);
        }*/

        HttpRequestWapper httpRequestWapper = new HttpRequestWapper((HttpServletRequest) req,toolConfig);
        req=httpRequestWapper;

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

}
