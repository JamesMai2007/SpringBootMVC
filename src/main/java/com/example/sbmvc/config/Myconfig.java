package com.example.sbmvc.config;

import com.example.sbmvc.config.pojo.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "biz")
@Validated
public class Myconfig {
    @NotNull
    private List<String> roles;
    private Map<String,String> userMap;
    private List<User> userList;
    private String defaultName;

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
