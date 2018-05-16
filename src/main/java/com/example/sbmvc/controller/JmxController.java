package com.example.sbmvc.controller;

import com.example.sbmvc.config.Myconfig;
import com.example.sbmvc.config.exception.ServiceException;
import com.example.sbmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 暴露endpoint能使用jmx或http修改配置信息
 */
@RestController
//@Endpoint(id="jmx-ctrl")
@RestControllerEndpoint(id="jmx-ctrl") //这样设置下面的方法全部会作为endpoint暴露
public class JmxController {

    @Autowired
    private Myconfig myconfig;

    //@ReadOperation
    @GetMapping("/jmx/getJson")
    public Object getJson() throws ServiceException {

        return myconfig.getUserList();
    }

    //@ReadOperation
    @GetMapping("/jmx/myname")
    public Object getName() throws ServiceException {

        return myconfig.getDefaultName();
    }

    //@WriteOperation
    @PostMapping("/jmx/setname")
    public boolean setname(String name) throws ServiceException {
        if(StringUtils.hasText(name))
        {
            myconfig.setDefaultName(name);
            return true;
        }
        else
            return false;
    }
}
