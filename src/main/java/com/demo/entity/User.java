package com.demo.entity;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String mobile;
}
