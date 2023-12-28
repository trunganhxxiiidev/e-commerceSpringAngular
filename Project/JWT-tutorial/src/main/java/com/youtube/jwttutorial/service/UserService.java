package com.youtube.jwttutorial.service;

import com.youtube.jwttutorial.dao.RoleDao;
import com.youtube.jwttutorial.dao.UserDao;
import com.youtube.jwttutorial.entity.Role;
import com.youtube.jwttutorial.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setPassWord("admin@pass");
        adminUser.setUserFirstName("Thomas");
        adminUser.setUserLastName("Brother");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);
    }
        public User registerNewUser(User user){
        return userDao.save(user);
    }
}
