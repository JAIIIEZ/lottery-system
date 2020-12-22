package com.spring.controller;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.dto.UserDto;
import com.spring.exception.UserAlreadyExistException;


public class UserControllerTest
{
    @Autowired
    private UserController userController;


    @Test(expected = UserAlreadyExistException.class)
    public void givenUserRegistered_whenDuplicatedRegister_thenCorrect()
    {
        final String email = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(email);


    }

    private UserDto createUserDto(final String username) {
        final UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("SecretPassword");
        userDto.setPasswordConfirm("SecretPassword");
        userDto.setFirstName("Merve");
        userDto.setLastName("Kaygisiz");
        return userDto;
    }
}
