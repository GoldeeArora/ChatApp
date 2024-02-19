package com.goldee.websocket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldee.websocket.payloads.UserDto2;
import com.goldee.websocket.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto2>> getAllUsers() {

        return new ResponseEntity<List<UserDto2>>(this.userService.getAllusers(), HttpStatus.OK);
    }
}
