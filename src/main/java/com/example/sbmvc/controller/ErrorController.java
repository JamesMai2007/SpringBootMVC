package com.example.sbmvc.controller;

import com.example.sbmvc.config.Myconfig;
import com.example.sbmvc.config.exception.ServiceException;
import com.example.sbmvc.service.UserService;
import com.example.sbmvc.vo.Rdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {

    /*@RequestMapping(value = "/error" , consumes = "application/json")
    public Object errorForJson() throws ServiceException {
        return new Rdata(500 , "发生错误");
    }*/

    //@RequestMapping(value = "/error")
    public Object error(@RequestHeader("Accept") String accept) throws ServiceException {
        if(accept.equalsIgnoreCase("application/json"))
            return new Rdata(500 , "发生错误");

        return "redirect:/view/error/error.html";
    }
}
