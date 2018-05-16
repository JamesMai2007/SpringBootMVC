package com.example.sbmvc.service;

import com.example.sbmvc.config.pojo.User;
import com.example.sbmvc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    UserDao userDao;

    public List<User> list()
    {
        return null;
    }

    public String getName()
    {
        return "Admin";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.sbmvc.pojo.User user = userDao.findByAccount(username);
        if(user == null)
            throw new UsernameNotFoundException("user not found");
        return user;
    }
}
