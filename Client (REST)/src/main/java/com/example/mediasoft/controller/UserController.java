package com.example.mediasoft.controller;


import com.example.mediasoft.dto.CreateNewUserDTO;
import com.example.mediasoft.dto.UserDTO;
import com.example.mediasoft.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class UserController {

    private final UserService userService;


    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/createUser")
    public UserDTO createUser(@RequestBody CreateNewUserDTO newUser) {
        return userService.createUser(newUser);
    }


    @GetMapping(value = "/getAllUsers")
    public ArrayList<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping(value = "/getUsers")
    public ArrayList<UserDTO> getUsers(int count) {
        return userService.getUsers(count);
    }


    @GetMapping(value = "/getUser")
    public UserDTO getUser(int id) {
        return userService.getUser(id);
    }
}
