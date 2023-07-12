package com.example.mediasoft.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreateNewUserDTO {
    private String name;

    private String surname;
}
