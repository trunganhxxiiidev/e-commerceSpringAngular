package com.youtube.jwttutorial.payload.response;

import java.util.List;

public class UserInfoResponse {
    private String username;

    private String userFirstName;
    private String userLastName;
    private List<String> roles;

    public UserInfoResponse(String username, String userFirstName,String userLastName, List<String> roles) {
        this.username = username;
        this.userFirstName=userFirstName;
        this.userLastName=userLastName;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
