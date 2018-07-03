package com.module.filter;


import com.module.config.tool_config.ToolConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chc
 * @create 2017-11-01 10:51
 **/
public class HttpRequestWapper extends HttpServletRequestWrapper {

    @Setter
    ToolConfig toolConfig;
//    @Setter
//    private ServiceFactory serviceFactory;
    @Getter
    private Map<String,Object> paramsMap;
    public HttpRequestWapper(HttpServletRequest request, ToolConfig toolfig) {
        super(request);

//        setServiceFactory(sf);
        setToolConfig(toolfig);

        String skey = request.getParameter(toolConfig.getTokenName());
        String params = request.getParameter(toolConfig.getCryptParamsName());

        if(skey!=null && params!=null && toolConfig.cryptBool){
            //解密
//            params=serviceFactory.getCryptService().deCrypt(skey,params);
//            paramsMap = FastJsonUtils.jsonToBean(params, Map.class);
            request.setAttribute("params",paramsMap);
        }

        if(paramsMap==null){
            paramsMap = new HashMap<>();
        }


    }

    @Override
    public String[] getParameterValues(String name) {
        //域名
        String serverName = super.getServerName();
        Object params = super.getAttribute("params");
        //授权码数据
        String tokenValue = super.getParameter(toolConfig.getTokenName());
        String[] strs = super.getParameterValues(name);
        if(paramsMap.get(name)!=null){
                String value = null;
            if(paramsMap.get(name)!=null){
                value=paramsMap.get(name).toString();
            }
            strs = new String[]{value};
        }else if(
                //swagger访问
                serverName.equals(toolConfig.getSwaggerIp())
                // 参数名是授权的token名
                || name.equals(toolConfig.getTokenName())
                 //是否需要加密
                || !toolConfig.cryptBool
                 //本次请求是否有授权码
                || StringUtils.isEmpty(tokenValue)) {

        }else {
            strs=null;
        }
        return strs;
    }


}
