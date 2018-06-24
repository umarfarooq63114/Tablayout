package com.codingdemos.tablayout.Model;

public class User {
    String UserName,password;

    public User(String userName, String password) {
        UserName = "user";
        this.password = "1234";
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return password;
    }
}
