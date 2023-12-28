package com.youtube.jwttutorial.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    private String userName;
    private String userFirstName;

    private String userLastName;
    private String passWord;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
    joinColumns = {
            @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
            @JoinColumn(name="ROLE_ID")
    })
    private Set<Role> role;

    public User(String username, String userFirstName, String userLastName, String encodePW) {
        this.userName=username;
        this.userFirstName=userFirstName;
        this.userLastName=userLastName;
        this.passWord=encodePW;
    }

    public User() {

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
