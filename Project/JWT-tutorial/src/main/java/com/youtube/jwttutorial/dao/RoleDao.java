package com.youtube.jwttutorial.dao;

import com.youtube.jwttutorial.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role,String> {
    Optional<Role> findByRoleName(String roleName);
}
