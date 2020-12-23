package com.spring.service;

import com.spring.dto.UserDto;
import com.spring.model.User;

public interface UserService {

    User findByUsername(String username);

    User createUser(UserDto userDto);
}
