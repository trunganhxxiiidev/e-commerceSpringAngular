package com.youtube.jwttutorial.service;

import com.youtube.jwttutorial.dao.RoleDao;
import com.youtube.jwttutorial.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role){
       return roleDao.save(role);
    }
}
