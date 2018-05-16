package com.example.sbmvc.dao;

import com.example.sbmvc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByAccount(String account);
}
