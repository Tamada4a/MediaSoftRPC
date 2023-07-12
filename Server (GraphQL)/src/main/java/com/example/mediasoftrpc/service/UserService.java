package com.example.mediasoftrpc.service;


import com.example.mediasoftrpc.dao.entity.User;
import com.example.mediasoftrpc.dao.repository.UserRepository;
import com.example.mediasoftrpc.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(final String name, final String surname) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);

        return userRepository.save(newUser);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public List<User> getUsers(final int count) {
        return userRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }


    public User getUser(final int id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException("Неизвестный пользователь", HttpStatus.NOT_FOUND));
    }
}
