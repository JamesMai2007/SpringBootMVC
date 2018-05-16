package com.example.sbmvc.util;

import com.alibaba.fastjson.JSONObject;
import com.example.sbmvc.config.exception.handler.ServiceExceptionHandler;
import com.example.sbmvc.vo.Rdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class ResponseEntityWrapper implements ResponseBodyAdvice<Object> {

    private Logger log = LoggerFactory.getLogger(ResponseEntityWrapper.class);

    private final Set<MediaType> jsonType = new HashSet<MediaType>();
    public ResponseEntityWrapper(){
        jsonType.add(MediaType.APPLICATION_JSON);
        jsonType.add(MediaType.APPLICATION_JSON_UTF8);
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object data, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        log.info("======beforeBodyWrite "+data);


        boolean isRest = false;

        log.info("======method  "+methodParameter.getMethod().getName());
        for(Annotation an : methodParameter.getMethodAnnotations()){
            if(an.annotationType() == ResponseBody.class){

                //log.info(an.annotationType());
                isRest = true;
            }
        }

        log.info("======class  "+methodParameter.getDeclaringClass().getName());
        for(Annotation an : methodParameter.getDeclaringClass().getAnnotations()){
            if(an.annotationType() == RestController.class){

                //log.info(an.annotationType());

                isRest = true;
            }
        }

        //isRest = false;

        if(!jsonType.contains(mediaType) && !isRest)
        {
            return data;
        }

        if(data == null || !(data instanceof Rdata))
        {
            Object rdata = new Rdata(0,data);

            if(data instanceof String)  //由于字符串返回时会把Rdata强制转为String类，所以这里需要把Rdata变成JSON字符串
                return JSONObject.toJSONString(rdata);
            return rdata;
        }

        return data;


    }
}
