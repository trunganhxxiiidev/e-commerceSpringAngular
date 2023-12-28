package com.youtube.jwttutorial.dao;

import com.youtube.jwttutorial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String> {
    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

}
