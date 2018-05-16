package com.example.sbmvc.util;

import com.alibaba.fastjson.JSON;
import com.example.sbmvc.vo.Rdata;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by James.Mai
 * on 2018-05-14 16:09
 * web请求登陆、过滤器验证错误的处理器
 */
public class SecurityFailureHandler extends SimpleUrlAuthenticationFailureHandler{
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String accept = httpServletRequest.getHeader("Accept");
        if(StringUtils.hasText(accept)){
            if(accept.toLowerCase().contains("application/json")) //如果是json请求就返回json
            {
                Rdata rdata = new Rdata(HttpStatus.INTERNAL_SERVER_ERROR.value() , e.getMessage());

                String jsonStr = JSON.toJSONString(rdata);
                httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getWriter().write(jsonStr);
            }
            else
            {   //否则按默认处理，返回到失败的failureUrl
                super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }
        }
    }
}
