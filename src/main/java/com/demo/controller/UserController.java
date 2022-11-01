package com.demo.controller;

import com.demo.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import com.demo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        String loginQuery = "Select username, password from app_users where username = ?";
        User fetchedUser = this.jdbcTemplate.queryForObject(loginQuery, new Object[]{user.getUsername()}, new UserRowMapper());
        if (fetchedUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(!fetchedUser.getPassword().equals(user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("app_users");


        Map<String,Object> params = new HashMap<>();
        params.put("user_id",user.getUserId());
        params.put("username",user.getUsername());
        params.put("password",user.getPassword());
        params.put("email",user.getEmail());
        params.put("mobile",user.getMobile());

        insert.execute(params);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
