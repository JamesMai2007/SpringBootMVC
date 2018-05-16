package com.example.sbmvc.controller;

import com.example.sbmvc.config.Myconfig;
import com.example.sbmvc.config.exception.ServiceException;
import com.example.sbmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private Myconfig myconfig;

    @Autowired
    private UserService userService;

    @GetMapping("/getJson")
    @ResponseBody
    public Object getJson() throws ServiceException {
        //System.out.println(myconfig.getUserList());

        return myconfig.getUserList();
    }

    @GetMapping("/getJsonForError")
    @ResponseBody
    public Object getJsonForError() throws ServiceException {
        //System.out.println(myconfig.getUserList());
        if(1==1)
            throw new ServiceException(100,"service exception");

        return myconfig.getUserList();
    }

    @GetMapping("/test.html")
    public String test_html(Model model) throws ServiceException {


        model.addAttribute("sayText" , "Who are you!!");

        return "view/test.html";
    }

    @GetMapping(value = "/getUser" ,  produces = {"application/json" })
    @ResponseBody
    public Object getUser(){

        return userService.getName();
    }
}
