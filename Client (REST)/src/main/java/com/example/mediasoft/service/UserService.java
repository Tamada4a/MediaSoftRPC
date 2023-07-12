package com.example.mediasoft.service;


import com.example.mediasoft.dto.CreateNewUserDTO;
import com.example.mediasoft.dto.UserDTO;
import com.example.mediasoft.exception.AppException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public class UserService {


    public UserDTO createUser(CreateNewUserDTO newUser) {
        String name = newUser.getName();
        String surname = newUser.getSurname();

        String mutation = (
                "{ \"query\": \"mutation UserCreationMutation($nameArg: String!, $surnameArg: String!) { createUser(name: $nameArg, surname: $surnameArg) { id } }\"," +
                        "\"variables\": {\"nameArg\": \"%s\", \"surnameArg\": \"%s\"} }"
        ).formatted(name, surname);

        HashMap<String, Object> response = getResponse(mutation);

        if (response == null)
            throw new AppException("Ответ не заполнен", HttpStatus.BAD_REQUEST);

        return MResponseToDTO(response, name, surname);
    }


    public ArrayList<UserDTO> getAllUsers() {
        String query = "{ \"query\": \"query { getAllUsers { id, name, surname } }\" }";
        HashMap<String, Object> response = getResponse(query);

        if (response == null)
            throw new AppException("Ответ не заполнен", HttpStatus.BAD_REQUEST);

        return getUsersListByKey(response, "getAllUsers");
    }


    public ArrayList<UserDTO> getUsers(int count) {
        String query = "{ \"query\": \"query { getUsers(count: %d) { id, name, surname } }\" }".formatted(count);
        HashMap<String, Object> response = getResponse(query);

        if (response == null)
            throw new AppException("Ответ не заполнен", HttpStatus.BAD_REQUEST);

        return getUsersListByKey(response, "getUsers");
    }


    public UserDTO getUser(int id) {
        String query = "{ \"query\": \"query { getUser(id: %d) { name, surname } }\" }".formatted(id);
        HashMap<String, Object> response = getResponse(query);

        if (response == null)
            throw new AppException("Ответ не заполнен", HttpStatus.BAD_REQUEST);

        return QResponseToDTO(response, id);
    }


    private HashMap<String, Object> getResponse(String query) {
        String URL = "http://localhost:8090/graphql";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new RestTemplate().postForObject(URL, new HttpEntity<>(query, headers), HashMap.class);
    }


    private ArrayList<UserDTO> getUsersListByKey(HashMap<String, Object> response, String key) {
        HashMap<String, Object> data = (HashMap<String, Object>) response.get("data");

        return (ArrayList<UserDTO>) data.get(key);
    }


    private UserDTO QResponseToDTO(HashMap<String, Object> response, int id) {
        HashMap<String, Object> data = (HashMap<String, Object>) response.get("data");
        HashMap<String, String> userInfo = (HashMap<String, String>) data.get("getUser");

        return new UserDTO(id, userInfo.get("name"), userInfo.get("surname"));
    }


    private UserDTO MResponseToDTO(HashMap<String, Object> response, String name, String surname) {
        HashMap<String, Object> data = (HashMap<String, Object>) response.get("data");
        HashMap<String, String> userInfo = (HashMap<String, String>) data.get("createUser");

        return new UserDTO(Integer.parseInt(userInfo.get("id")), name, surname);
    }
}
