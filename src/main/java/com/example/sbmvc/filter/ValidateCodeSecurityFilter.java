package com.example.sbmvc.filter;

import com.example.sbmvc.config.exception.SecurityException;
import com.example.sbmvc.util.SecurityFailureHandler;
import com.example.sbmvc.validatecode.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码校验过滤器
 * 继承一次请求过滤器，保证一次请求只处理一次，由于不同的容器调用会有不同处理，可能会调用多次filter
 */
public class ValidateCodeSecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeSecurityFilter.class);

    private ValidateCode validateCode;

    private String[] matchPaths;

    private String filterName;

    private SecurityFailureHandler securityFailureHandler;

    /**
     *
     * @param validateCode  验证码，可能是图片或短信
     * @param paths         需要验证过滤的路径
     */
    public ValidateCodeSecurityFilter(ValidateCode validateCode , String... paths){
        this.validateCode = validateCode;
        this.filterName = validateCode.getValidateType();
        this.matchPaths = paths;
    }

    public void setSecurityFailureHandler(SecurityFailureHandler securityFailureHandler) {
        this.securityFailureHandler = securityFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String reqUrl = httpServletRequest.getRequestURL().toString();

        //判断请求路径是否需要过滤的

        boolean hasMatch = false;
        for(String path : matchPaths)
        {
            if(reqUrl.endsWith(path)){
                hasMatch = true;
                break;
            }
        }

        log.debug("{} match req url {} , result {}" , this.filterName , reqUrl , hasMatch );

        if(hasMatch)
        {
            try {
                //处理请求，如果失败就抛错
                processRequest(httpServletRequest);
            }catch (SecurityException e){
                e.printStackTrace();
                securityFailureHandler.onAuthenticationFailure(httpServletRequest , httpServletResponse , e);
                return ;
            }
        }

        //继续下一个过滤器
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void processRequest(HttpServletRequest httpServletRequest) throws SecurityException{

        HttpSession session = httpServletRequest.getSession();
        String captcha = httpServletRequest.getParameter(this.validateCode.getValidateType());

        if(!StringUtils.hasText(captcha)){
            throw new SecurityException(100 , "验证码不能为空");
        }

        if(validateCode.isExpired(session)){
            throw new SecurityException(101 , "验证码已过期");
        }

        if(!validateCode.compareCode(session , captcha)){
            throw new SecurityException(102 , "验证码不正确");
        }

        validateCode.clear(session);

    }
}
