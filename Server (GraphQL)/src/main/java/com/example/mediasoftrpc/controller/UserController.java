package com.example.mediasoftrpc.controller;


import com.example.mediasoftrpc.dao.entity.User;
import com.example.mediasoftrpc.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class UserController {

    private final UserService userService;


    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @QueryMapping(value = "getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @QueryMapping(value = "getUsers")
    public List<User> getUsers(@Argument int count){
        return userService.getUsers(count);
    }


    @QueryMapping(value = "getUser")
    public User getUser(@Argument int id){
        return userService.getUser(id);
    }


    @MutationMapping(value = "createUser")
    public User createUser(@Argument String name, @Argument String surname){
        return userService.createUser(name, surname);
    }
}
