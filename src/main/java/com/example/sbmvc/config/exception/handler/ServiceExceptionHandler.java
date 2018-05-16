package com.example.sbmvc.config.exception.handler;

import com.example.sbmvc.config.exception.ServiceException;
import com.example.sbmvc.vo.Rdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Rdata> serviceExceptionHandler(HttpServletRequest request, Throwable ex){

        //ex.printStackTrace();
        log.error("catch error",ex);

        if(ex instanceof  ServiceException)
        {
            ServiceException se = (ServiceException)ex;
            return new ResponseEntity<>(new Rdata(se.getCode(), se.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new Rdata(status.value(), ex.getMessage()), status);
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
